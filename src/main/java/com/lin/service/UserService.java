package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.UserRepository;
import com.lin.vo.UserDetailsVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
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
				uass.install.as("install"),
				user.quanPin,
				user.shouZiMu
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
		List<ContextVo> listVo =jpaQueryFactory().select(Projections.bean(ContextVo.class,
				context.workID.as("contextID"),
				context.workName.as("context"),
				new CaseBuilder().when(context.workID.eq(userContext.workID)).then("1").otherwise("2").as("flag")
		)).from(context).leftJoin(userContext)
				.on(context.workID.eq(userContext.workID).and(userContext.userID.eq(userID))).fetch();
		userDetailsDsl.setContext(listVo);
		List<FieldVo> listField = jpaQueryFactory().select(Projections.bean(FieldVo.class,
				field.terID.as("fieldID"),
				field.terName.as("field"),
			new CaseBuilder().when(field.terID.eq(userField.terID)).then("1").otherwise("2").as("flag")
		)).from(field).leftJoin(userField)
				.on(field.terID.eq(userField.terID).and(userField.userID.eq(userID))).fetch();
		userDetailsDsl.setField(listField);
        return  userDetailsDsl;
	}
}
