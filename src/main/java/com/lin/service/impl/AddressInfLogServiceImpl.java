package com.lin.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lin.util.JsonUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lin.mapper.AddressInfLogMapper;
import com.lin.mapper.UtilMapper;
import com.lin.domain.AddressInfLogBean;
import com.lin.service.AddressInfLogServiceI;
import com.lin.util.Result;

/**
 * 
 * @author lwz
 * @date 2017年8月20日
 */
@Service("addressInfLogService")
public class AddressInfLogServiceImpl implements AddressInfLogServiceI {

	@Autowired
	private AddressInfLogMapper addressInfLogDao;
	@Autowired
	private UtilMapper utilDao;

	@Override
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
		log.setRowID(utilDao.getSeqAppAddressInfLog());
		if (null == log.getExpError()) {
			log.setExpError("");
		}
		addressInfLogDao.saveAddressInfLog(log);
	}

	@Override
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
	}
}
