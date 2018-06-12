package com.lin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ideal.wheel.common.AbstractService;
import com.lin.domain.AddressInfLogDsl;
import com.lin.domain.QAddressInfLogDsl;
import com.lin.repository.AddressInfLogRepository;
import com.lin.util.JsonUtil;
import com.lin.util.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;


/**
 *	日志
 * @author lwz
 * @date 2017年8月20日
 */
@Service("addressInfLogServiceDsl")
public class AddressInfLogServiceDslImpl extends AbstractService<AddressInfLogDsl,String> {

	@Autowired
	private AddressInfLogRepository repository;

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}

	@Autowired
	public AddressInfLogServiceDslImpl(AddressInfLogRepository repository){
		super(repository);
	}

	@Override
	public long deleteByIds(String... strings) {
		return 0;
	}


	@Override
	public List<AddressInfLogDsl> findByIds(String... strings) {
		QAddressInfLogDsl inflog = QAddressInfLogDsl.addressInfLogDsl;
		JPAQueryFactory query = jpaQueryFactory();
		return query.select(inflog).from(inflog).where(inflog.rowID.in(strings)).fetch();
	}

	/**
	 * 保存日志信息
	 * @param log
	 * @param respJson
	 */
	public void saveAddressInfLog(AddressInfLogDsl log, Result respJson) {
		String Json = null;
		try {
			if(null == respJson){
				Json ="test";
			}else {
				Json = JsonUtil.toJson(respJson);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		log.setRespJson(Json);
		log.setEndDate(Calendar.getInstance().getTime());
		if (null == log.getExpError() || log.getExpError().equals("") ) {
			log.setExpError("");
		}
		repository.save(log);
	}

	/**
	 * 生成日志实体
	 * @param req
	 * @param infName
	 * @return
	 */
	public AddressInfLogDsl getInfLog(HttpServletRequest req, String infName) {
		AddressInfLogDsl log = new AddressInfLogDsl();
		log.setAddName(infName);
		log.setCreateDate(Calendar.getInstance().getTime());
		if(null == req ){
			log.setReqJson("test");
		}else{
			log.setReqJson(req.getRequestURL().toString() + "?" + req.getQueryString());
		}
		return log;
	}



}
