package com.lin.controller;

import com.lin.domain.OrganizationDsl;
import com.lin.service.OrganizationServiceDslImpl;
import com.lin.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  dsl 新方法
 *  @author  lwz 2018.6.11
 */


@Controller
@RequestMapping("/communicationdsl")
public class CommunicationDslController {

    @Autowired
    private OrganizationServiceDslImpl organizationServiceDslImpl;

    /**
     * 查询组织部门
     * @param req
     * @param organ  当前登录人
     * @return
     */
    @GetMapping("organizationlist")
    @ResponseBody
    public Result organizationlist(HttpServletRequest req, OrganizationDsl organ) {
        Result result = new Result();
        try {
            List<OrganizationDsl> list = organizationServiceDslImpl.getOrganizationByDsl(organ);
            Map<String, Object> orgtreeMap = new LinkedHashMap<String, Object>();
            orgtreeMap.put("organizationList", list);
            result.setRespCode("1");
            result.setRespDesc("正常返回数据");
            result.setRespMsg(orgtreeMap);
        } catch (Exception e) {
            e.printStackTrace();
            ////log.setExpError(e.toString());
            result.setRespCode("2");
            result.setRespDesc("失败");
            result.setRespMsg("");
        }
        return result;
    }

}
