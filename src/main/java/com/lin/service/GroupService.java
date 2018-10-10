package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.AddressBannedRepository;
import com.lin.repository.AddressColAuxiliaryRepository;
import com.lin.repository.AddressCollectionRepository;
import com.lin.util.Result;
import com.lin.util.XmlReqAndRes;
import com.lin.vo.AddressCollectionVo;
import com.lin.vo.AutoCollectionVo;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.*;


/**
 *
 * 通讯录
 * @author lwz
 * @date 2018.10.10
 *
 */
@Service
public class GroupService extends AbstractService<AddressCollection,String>{

	private static final String String = null;

	@Autowired
	public GroupService(AddressCollectionRepository addressCollectionRepository) {
		super(addressCollectionRepository);
	}

	@Value("${application.ADDB_DK}")
	private String addressBookDKUrl;
	@Value("${application.CUST_IMG}")
	private String custIMG;
	@Value("${application.pic_HttpIP}")
	private String picHttpIp;

	@Resource
	private AddressBannedRepository addressBannedRepository;

	@Resource
	private AddressCollectionRepository addressCollectionRepository;

	@Resource
	private AddressColAuxiliaryRepository addressColAuxiliaryRepository;

	@Autowired
	private EntityManager entityManager;

	private JPAQueryFactory queryFactory;

	@PostConstruct
	public void init() {
		queryFactory = new JPAQueryFactory(entityManager);
	}


	/**
	 * 角色信息查询
	 * @param result
	 * @param loginID
	 * @param queryType
	 */
	public void selectRoleDept(Result result, String loginID, String queryType){

	}

	/**
	 * 统计当前人数查询
	 * @param result
	 * @param loginID
	 * @param queryType
	 * @param roleList
	 * @param deptList
	 * @param userList
	 * @param groupID
	 */
	public void selectGroupCount(Result result, String loginID, String queryType, String roleList, String deptList, String userList, String groupID){

	}

	/**
	 * 新分组保存功能
	 * @param result
	 * @param loginID
	 * @param queryType
	 * @param roleList
	 * @param deptList
	 * @param userList
	 * @param groupID
	 * @param groupName
	 * @param groupDesc
	 */
	public void saveGroupCount(Result result, String loginID, String queryType, String roleList, String deptList, String userList, String groupID,String groupName,String groupDesc){

	}












	@Override
	public long deleteByIds(String... ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AddressCollection> findByIds(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
