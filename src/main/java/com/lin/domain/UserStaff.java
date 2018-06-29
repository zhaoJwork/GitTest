package com.lin.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 是指与中国电信存在雇佣关系的个人
 * @author liudongdong
 * @date 2018年6月22日
 *
 */
@ApiModel(value = "userStaff", description = "是指与中国电信存在雇佣关系的个人")
@Entity
@Table(name = "staff",schema="jtuser")
public class UserStaff implements Serializable {

	@ApiModelProperty(value = "员工标识")
	@Id
	@Column(name = "STAFF_ID")
//	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_ADDRESS_BANNED")
//	@SequenceGenerator(name="SEQ_ADDRESS_BANNED", sequenceName="seq_app_addresslist")
	private Integer staffID;
	
	@ApiModelProperty(value = "员工的主键")
	@Column(name = "STAFF_CODE")
	private String staffCode;
	
	@ApiModelProperty(value = "组织标识外键")
	@Column(name = "ORG_ID")
	private Integer orgID;
	
	@ApiModelProperty(value = "记录参与人唯一标识，作为主键")
	@Column(name = "PARTY_ID")
	private Integer partyID;
	
	@ApiModelProperty(value = "员工名称")
	@Column(name = "STAFF_NAME")
	private String staffName;
	
	@ApiModelProperty(value = "员工的详细说明")
	@Column(name = "STAFF_DESC")
	private String staffDesc;
	
	@ApiModelProperty(value = "记录员工状态，如有效、无效等")
	@Column(name = "STATUS_CD")
	private String statusCD;
	
	@ApiModelProperty(value = "状态变更的时间")
	@Column(name = "STATUS_DATE")
	private Date statusDate;
	
	@ApiModelProperty(value = "记录员工创建时间")
	@Column(name = "CREATE_DATE")
	private Date createDate;
	
	@ApiModelProperty(value = "记录创建人员ID")
	@Column(name = "CREATE_USER")
	private String createUser;
	
	@ApiModelProperty(value = "记录修改时间")
	@Column(name = "UPDATE_DATE")
	private Date updateDate;
	
	@ApiModelProperty(value = "记录修改人员ID")
	@Column(name = "UPDATE_USER")
	private Integer updateUser;
	
	@ApiModelProperty(value = "员工类型：外部和内部")
	@Column(name = "STAFF_TYPE")
	private String staffType;
	
	@ApiModelProperty(value = "人资系统ID")
	@Column(name = "HR_PERSONID")
	private String hrPersonID;
	
	@ApiModelProperty(value = "备注")
	@Column(name = "REMAIKS")
	private String remaiks;
	
	@ApiModelProperty(value = "上岗时间")
	@Column(name = "INDUCTION_START_TIME")
	private Date inductionStartTime;
	
	@ApiModelProperty(value = "离岗时间")
	@Column(name = "INDUCTION_END_TIME")
	private Date inductionEndTime;
	
	@ApiModelProperty(value = "邮编")
	@Column(name = "POST_CERTIFICATION")
	private String postCertification;
	
	@ApiModelProperty(value = "兼职/专职")
	@Column(name = "INDUCTION_POST_TYPE")
	private String inductioinPostType;
	
	@ApiModelProperty(value = "部门")
	@Column(name = "DEPARTMENT_ID")
	private Integer departmentID;
	
	@ApiModelProperty(value = "国籍")
	@Column(name = "NATIONALITY")
	private String nationality;
	
	@ApiModelProperty(value = "职位名称")
	@Column(name = "POSITION_NAME")
	private String positionName;
	
	@ApiModelProperty(value = "是否一站员工")
	@Column(name = "IFYIZHAN")
	private Integer ifYiZhan;
	
	@ApiModelProperty(value = "备注")
	@Column(name = "COMMENTS")
	private String comments;

	public Integer getStaffID() {
		return staffID;
	}

	public void setStaffID(Integer staffID) {
		this.staffID = staffID;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public Integer getOrgID() {
		return orgID;
	}

	public void setOrgID(Integer orgID) {
		this.orgID = orgID;
	}

	public Integer getPartyID() {
		return partyID;
	}

	public void setPartyID(Integer partyID) {
		this.partyID = partyID;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffDesc() {
		return staffDesc;
	}

	public void setStaffDesc(String staffDesc) {
		this.staffDesc = staffDesc;
	}

	public String getStatusCD() {
		return statusCD;
	}

	public void setStatusCD(String statusCD) {
		this.statusCD = statusCD;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public String getStaffType() {
		return staffType;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public String getHrPersonID() {
		return hrPersonID;
	}

	public void setHrPersonID(String hrPersonID) {
		this.hrPersonID = hrPersonID;
	}

	public String getRemaiks() {
		return remaiks;
	}

	public void setRemaiks(String remaiks) {
		this.remaiks = remaiks;
	}

	public Date getInductionStartTime() {
		return inductionStartTime;
	}

	public void setInductionStartTime(Date inductionStartTime) {
		this.inductionStartTime = inductionStartTime;
	}

	public Date getInductionEndTime() {
		return inductionEndTime;
	}

	public void setInductionEndTime(Date inductionEndTime) {
		this.inductionEndTime = inductionEndTime;
	}

	public String getPostCertification() {
		return postCertification;
	}

	public void setPostCertification(String postCertification) {
		this.postCertification = postCertification;
	}

	public String getInductioinPostType() {
		return inductioinPostType;
	}

	public void setInductioinPostType(String inductioinPostType) {
		this.inductioinPostType = inductioinPostType;
	}

	public Integer getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(Integer departmentID) {
		this.departmentID = departmentID;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public Integer getIfYiZhan() {
		return ifYiZhan;
	}

	public void setIfYiZhan(Integer ifYiZhan) {
		this.ifYiZhan = ifYiZhan;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "UserStaff [staffID=" + staffID + ", staffCode=" + staffCode + ", orgID=" + orgID + ", partyID="
				+ partyID + ", staffName=" + staffName + ", staffDesc=" + staffDesc + ", statusCD=" + statusCD
				+ ", statusDate=" + statusDate + ", createDate=" + createDate + ", createUser=" + createUser
				+ ", updateDate=" + updateDate + ", updateUser=" + updateUser + ", staffType=" + staffType
				+ ", hrPersonID=" + hrPersonID + ", remaiks=" + remaiks + ", inductionStartTime=" + inductionStartTime
				+ ", inductionEndTime=" + inductionEndTime + ", postCertification=" + postCertification
				+ ", inductioinPostType=" + inductioinPostType + ", departmentID=" + departmentID + ", nationality="
				+ nationality + ", positionName=" + positionName + ", ifYiZhan=" + ifYiZhan + ", comments=" + comments
				+ "]";
	}
	
}
