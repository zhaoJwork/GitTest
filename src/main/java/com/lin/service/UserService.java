package com.lin.service;

import com.ideal.wheel.common.AbstractService;
import com.lin.domain.*;
import com.lin.repository.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	@Override
	public long deleteByIds(String... strings) {
		return 0;
	}

	@Override
	public List<User> findByIds(String... strings) {
		QUser user = QUser.user;
		JPAQueryFactory query = jpaQueryFactory();

		return query.select(user).from(user).where(user.userID.in(strings)).fetch();
	}

	/**
	 * 根据人员ID查询个人详情
	 * @param userID
	 * @return
	 */
	public UserDetailsDsl selectUserDetails(String loginID ,String userID){
		QUser user = QUser.user;
		QUserNewAssistDsl uass = QUserNewAssistDsl.userNewAssistDsl;
		QPositionDsl posDsl = QPositionDsl.positionDsl;
		QOrganizationDsl organ = QOrganizationDsl.organizationDsl;
		QAddressCollection coll = QAddressCollection.addressCollection;
		QAddressBanned ban = QAddressBanned.addressBanned;
        UserDetailsDsl userDetailsDsl =  jpaQueryFactory().select(Projections.bean(UserDetailsDsl.class,
				user.userID,
				user.userName,
				new CaseBuilder().when(uass.portrait_url.eq("").or(uass.portrait_url.isNull())).then(user.userPic).otherwise(uass.portrait_url).as("userPic"),
				user.organizationID,
				organ.organizationName,
				user.post.as("postID"),
				posDsl.posName.as("postName"),
				user.phone.as("phone"),
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
                .leftJoin(coll)
				.where(user.userID.eq(userID)).fetchOne();
        //// 是否可以查看能力详情    ability     1 是  0 否
        List ability = entityManager.createNativeQuery("select appuser.F_O_bannedsay(?,?) from dual")
                .setParameter(1, loginID).setParameter(2, userID)
                .getResultList();
        if(null != ability && 0 < ability.size() ) {
            userDetailsDsl.setAbility(ability.get(0).toString());
        }else {
            userDetailsDsl.setAbility("0");
        }
        //// 是否可以禁言 notalk 1 是 0 否
        List notalk = entityManager.createNativeQuery("select count(1) from appuser.address_user u where u.dep_id = '626' and u.user_id = ?")
                .setParameter(1, loginID)
                .getResultList();
        if(null != notalk && 0 < notalk.size() ) {
            userDetailsDsl.setNotalk(notalk.get(0).toString());
        }else {
            userDetailsDsl.setNotalk("0");
        }
        ////是否被收藏    collection  1 是 0 否
        Long collection = jpaQueryFactory().select(coll.count()).from(coll)
                .where(coll.collectionLoginId.eq(Integer.parseInt(loginID)).and(coll.collectionUserId.eq(Integer.parseInt(userID))).and(coll.type.eq(1)
                        .and(coll.collectionType.eq(1).and(coll.source.eq(1))))).fetchOne();
        userDetailsDsl.setCollection(collection+"");
        ////禁言状态       talkstatus 1 是 0 否
        Long talkstatus = jpaQueryFactory().select(ban.count()).from(ban)
                .where(ban.bannedSayUserId.eq(Integer.parseInt(userID)).and(ban.bannedSayType.eq(1)).and(coll.type.eq(1))).fetchOne();
        userDetailsDsl.setTalkstatus(talkstatus+"");
        return  userDetailsDsl;
	}
}
