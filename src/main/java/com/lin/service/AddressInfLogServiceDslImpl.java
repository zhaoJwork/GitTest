package com.lin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ideal.wheel.common.AbstractService;
import com.lin.domain.AddressInfLogBean;
import com.lin.domain.QAddressInfLogBean;
import com.lin.repository.AddressInfLogRepository;
import com.lin.util.JsonUtil;
import com.lin.util.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 *
 * @author lwz
 * @date 2017年8月20日
 */
@Service("addressInfLogServiceDsl")
public class AddressInfLogServiceDslImpl extends AbstractService<AddressInfLogBean,String> {

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
	public List<AddressInfLogBean> findByIds(String... strings) {
		QAddressInfLogBean inflog = QAddressInfLogBean.addressInfLogBean;
		JPAQueryFactory query = jpaQueryFactory();
		return query.select(inflog).from(inflog).where(inflog.rowID.in(strings)).fetch();
	}

	public void saveAddressInfLog(AddressInfLogBean log, Result respJson) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		String Json = null;
		try {
			Json = JsonUtil.toJson(respJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		log.setRespJson(Json);
		log.setEndDate(str);
		//log.setRowID(utilDao.getSeqAppAddressInfLog());
		if (null == log.getExpError()) {
			log.setExpError("");
		}
		//addressInfLogDao.saveAddressInfLog(log);
	}





/*	@Override
	public AddressInfLogBean getAddressInfLog(HttpServletRequest req, String infName) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		AddressInfLogBean log = new AddressInfLogBean();
		log.setAddName(infName);
		log.setCreateDate(str);
		String reqJson = req.getRequestURL().toString() + "?" + req.getQueryString();
		log.setReqJson(reqJson);
		return log;
	}*/



}
