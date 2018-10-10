package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.AddressBannedRepository;
import com.lin.repository.AddressColAuxiliaryRepository;
import com.lin.repository.AddressCollectionRepository;
import com.lin.repository.GroupRepository;
import com.lin.util.JsonObjectMapper;
import com.lin.util.Result;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.*;


/**
 *
 * 通讯录
 * @author lwz
 * @date 2018.10.10
 *
 */
@Service
public class GroupService extends AbstractService<GroupBo,String>{

	private static final String String = null;

	@Autowired
	public GroupService(GroupRepository groupRepository) {
		super(groupRepository);
	}

	@Value("${application.enterprise.createGroup}")
	private String enterpriseCreateGroup;
	@Value("${application.enterprise.inviteFriend}")
	private String enterpriseInviteFriend;
	@Value("${application.enterprise.removeMembers}")
	private String enterpriseRemoveMembers;
	@Value("${application.enterprise.exitGroup}")
	private String enterpriseExitGroup;
	@Value("${application.enterprise.dissolution}")
	private String enterpriseDissolution;
	@Value("${application.enterprise.modify}")
	private String enterpriseModify;
	@Value("${application.enterprise.queryGroup}")
	private String enterpriseQueryGroup;


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
		QPositionDsl qPositionDsl = QPositionDsl.positionDsl;
		String db = "select * from (select to_char(wm_concat(p.pos_id)) pos_id,p.pos_name,min(p.role_num) role_num from appuser.address_position p group by p.pos_name) t order by t.role_num";
		List positionList = entityManager.createNativeQuery(db).getResultList();
		for (int i = 0 ;i < positionList.size() ; i++){
			System.out.println("key:" + positionList.get(i));
		}

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
	public List<GroupBo> findByIds(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

}
