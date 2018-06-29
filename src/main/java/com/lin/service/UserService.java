package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.UserRepository;
import com.lin.vo.UserDetailsVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.Assert;

/**
 * 功能概要：UserService接口类
 * 
 * @author 
 * @since   
 */
@Service("userService1")
public class UserService extends AbstractService<User,String> {

	@Autowired
	public UserService(UserRepository userRepository){
		super(userRepository);
	}

	@Deprecated
	@Override
	public long deleteByIds(String... strings) {
		return 0;
	}

	@Override
	public List<User> findByIds(String... strings) {
		Assert.isNull(strings,"主键列表不能为空");
		QUser user = QUser.user;
		JPAQueryFactory query = jpaQueryFactory();
		return query.select(user).from(user).where(user.userID.in(strings)).fetch();
	}

	/**
	 * 根据人员ID查询个人详情
	 * @param userID
	 * @return
	 */
	public UserDetailsVo selectUserDetails(String loginID , String userID){
		QUser user = QUser.user;
		QUserNewAssist uass = QUserNewAssist.userNewAssist;
		QPositionDsl posDsl = QPositionDsl.positionDsl;
		QOrganizationDsl organ = QOrganizationDsl.organizationDsl;
		QAddressCollection coll = QAddressCollection.addressCollection;
		QAddressBanned ban = QAddressBanned.addressBanned;
		QContext context = QContext.context;
		QUserContext userContext = QUserContext.userContext;
		QField field = QField.field;
		QUserField userField = QUserField.userField;

        UserDetailsVo userDetailsDsl =  jpaQueryFactory().select(Projections.bean(UserDetailsVo.class,
				user.userID,
				user.userName,
				new CaseBuilder().when(uass.portrait_url.eq("").or(uass.portrait_url.isNull())).then(user.userPic).otherwise(uass.portrait_url).as("userPic"),
				user.organizationID,
				organ.organizationName,
				user.post.as("postID"),
				posDsl.posName.as("postName"),
				user.phone,
				user.email.as("email"),
				user.address.as("address"),
				user.context.as("contexts"),
				user.field.as("fields"),
				uass.install.as("install")
		)).from(user)
				.leftJoin(uass)
				.on(user.userID.eq(uass.userid))
				.leftJoin(posDsl)
				.on(posDsl.posId.eq(user.post))
				.leftJoin(organ)
				.on(user.organizationID.eq(organ.organizationID))
				.where(user.userID.eq(userID)).fetchOne();
        if (null == userDetailsDsl){
			return null;
		}
        //// 是否可以查看能力详情    ability     1 是  0 否
        List ability = entityManager.createNativeQuery("select appuser.F_O_bannedsay(?,?) from dual")
                .setParameter(1, loginID).setParameter(2, userID)
                .getResultList();
        if(null != ability && 0 < ability.size() ) {
            userDetailsDsl.setAbility(ability.get(0).toString());
        }else {
            userDetailsDsl.setAbility("0");
        }
        //// 是否可以禁言 noTalk 1 是 0 否
        List noTalk = entityManager.createNativeQuery("select count(1) from appuser.address_user u where u.dep_id = '626' and u.user_id = ?")
                .setParameter(1, loginID)
                .getResultList();
        if(null != noTalk && 0 < noTalk.size() ) {
            userDetailsDsl.setNotalk(noTalk.get(0).toString());
        }else {
            userDetailsDsl.setNotalk("0");
        }
        ////是否被收藏    collection  1 是 0 否
        Long collection = jpaQueryFactory().select(coll.count()).from(coll)
                .where(coll.collectionLoginId.eq(Integer.parseInt(loginID)).and(coll.collectionUserId.eq(Integer.parseInt(userID))).and(coll.type.eq(1)
                        .and(coll.collectionType.eq(1)))).fetchOne();
        userDetailsDsl.setCollection(collection+"");
        ////禁言状态       talkStatus 1 是 0 否
        Long talkStatus = jpaQueryFactory().select(ban.count()).from(ban)
                .where(ban.bannedSayUserId.eq(Integer.parseInt(userID)).and(ban.bannedSayType.eq(1)).and(ban.type.eq(1))).fetchOne();
        userDetailsDsl.setTalkstatus(talkStatus+"");
		List contextVo = entityManager
				.createNativeQuery("select ac.work_id contextID, ac.work_name context, decode(t.wid, '', '2', '1') flag " +
										"  from appuser.address_work_content ac " +
										"  left join (select ac.work_id wid " +
										"               from appuser.address_work_content ac " +
										"               left join appuser.address_user_work aw " +
										"                 on aw.work_id = ac.work_id " +
										"              where aw.user_id = ? ) t " +
										"    on t.wid = ac.work_id")
				.setParameter(1, loginID)
				.getResultList();
		
		// entityManager.createNativeQuery  返回类型为  List<Object[]>  进行转型为 List<ContextVo>
		List<ContextVo> listVo = new ArrayList<ContextVo>();
		ContextVo contextVo2 = null;
		for (Object object : contextVo) {
			Object[] cell = (Object[])object;
			contextVo2 = new ContextVo();
			contextVo2.setContextID(cell[0].toString());
			contextVo2.setContext(cell[1].toString());
			contextVo2.setFlag(cell[2].toString());
			listVo.add(contextVo2);
		}
		userDetailsDsl.setContext(listVo);
		List fieldVo = entityManager
				.createNativeQuery("select tu.ter_id fieldID, tu.ter_name field, decode(t.wid, '', '2', '1') flag" +
						"  from appuser.address_territory tu" +
						"  left join (select t.ter_id  wid     " +
						"            from appuser.address_territory t" +
						"            left join appuser.address_user_territory atu" +
						"              on atu.ter_id = t.ter_id" +
						"             where atu.user_id = ? ) t" +
						"    on t.wid = tu.ter_id")
				.setParameter(1, loginID)
				.getResultList();
		
		// entityManager.createNativeQuery  返回类型为  List<Object[]>  进行转型为List<FieldVo>
				List<FieldVo> listField = new ArrayList<FieldVo>();
				FieldVo fieldVo2 = null;
				for (Object object : fieldVo) {
					Object[] cell = (Object[])object;
					fieldVo2 = new FieldVo();
					fieldVo2.setFieldID(cell[0].toString());
					fieldVo2.setField(cell[1].toString());
					fieldVo2.setFlag(cell[2].toString());
					listField.add(fieldVo2);
				}
		
		userDetailsDsl.setField(listField);
        return  userDetailsDsl;
	}
}
