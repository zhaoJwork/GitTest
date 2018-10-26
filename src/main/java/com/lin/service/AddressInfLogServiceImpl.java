package com.lin.service;

import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ideal.wheel.common.AbstractService;
import com.lin.domain.AddressInfLog;
import com.lin.domain.QAddressInfLog;
import com.lin.repository.AddressInfLogRepository;
import com.lin.util.JsonUtil;
import com.lin.util.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;


/**
 *	日志
 * @author lwz
 * @date 2017年8月20日
 */
@Service("addressInfLogServiceDsl")
public class AddressInfLogServiceImpl extends AbstractService<AddressInfLog,String> {

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
	public AddressInfLogServiceImpl(AddressInfLogRepository repository){
		super(repository);
	}

	@Override
	public long deleteByIds(String... strings) {
		return 0;
	}


	@Override
	public List<AddressInfLog> findByIds(String... strings) {
		QAddressInfLog inflog = QAddressInfLog.addressInfLog;
		JPAQueryFactory query = jpaQueryFactory();
		return query.select(inflog).from(inflog).where(inflog.addName.in(strings)).fetch();
	}

	/**
	 * 保存日志信息
	 * @param log
	 * @param respJson
	 */
	public void saveAddressInfLog(AddressInfLog log, Result respJson) {
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
	public AddressInfLog getInfLog(HttpServletRequest req, String infName) {
		AddressInfLog log = new AddressInfLog();
		log.setAddName(infName);
		log.setCreateDate(Calendar.getInstance().getTime());
		log.setRowID(seqLong());
		if(null == req ){
			log.setReqJson("test");
		}else{
			String reqstr = "";
			if(req.getQueryString().indexOf("loginID") > 0 ){
				if(req.getQueryString().indexOf("crator") > 0){

				}
			}
			log.setReqJson(req.getRequestURL().toString() + "?" + req.getQueryString() + reqstr);
		}
		return log;
	}

	/**
	 * 生成日志实体
	 * @param req
	 * @param infName
	 * @param reqJson
	 * @return
	 */
	public AddressInfLog getInfLog(HttpServletRequest req, String infName,String reqJson) {
		AddressInfLog log = new AddressInfLog();
		log.setAddName(infName);
		log.setCreateDate(Calendar.getInstance().getTime());
		log.setRowID(seqLong());
		if(null == req ){
			log.setReqJson("test");
		}else{
			log.setReqJson(req.getRequestURL().toString() + "?" + reqJson);
		}
		return log;
	}

		private int seqLong (){
	  		List list = entityManager.createNativeQuery("select appuser.seq_app_addressinflog.nextval from dual").getResultList();
			return Integer.parseInt(list.get(0).toString());
		}

	public void saveError(Exception e,AddressInfLog log){
		Result result = new Result();
		result.setRespCode("2");
		result.setRespDesc("失败");
		result.setRespMsg("");
		log.setExpError(e.toString());
		saveAddressInfLog(log,result);
	}

}
