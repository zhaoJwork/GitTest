package com.lin.util;

import com.lin.domain.AddressInfLog;
import com.lin.service.AddressInfLogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("valUtilService")
public class ValUtil {

    @Autowired
    private AddressInfLogServiceImpl logServiceDsl;

    /**
     * 根据类型判断入参是否为空
     * @param result
     * @param log
     * @param type
     * @param st0
     * @return
     */
    public Boolean valByT(Result result,AddressInfLog log,String type,String st0) {
        boolean ret = false;
        if (type.equals("loginID")) {
            if (null == st0 || "".equals(st0)) {
                result.setRespCode("2");
                result.setRespDesc("loginID 不能为空");
                logServiceDsl.saveAddressInfLog(log, result);
                ret = true;
            }
        }
        return ret;
    }
}
