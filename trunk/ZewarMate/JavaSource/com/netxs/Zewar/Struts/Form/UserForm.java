package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class UserForm extends ValidatorActionForm {

	private String methodToCall = "unspecified";

	private String roleId;

	private String userId;

	private String nameTitle;

	private String firstName;

	private String middleName;

	private String lastName;

	private String description;

	private String loginName;

	private String loginPassword;

	private String confirmPassword;

	private char hasFormInitialized;

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String employeeDescription) {
		this.description = employeeDescription;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String employeeId) {
		this.roleId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getNameTitle() {
		return nameTitle;
	}

	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMethodToCall() {
		return methodToCall;
	}

	public void setMethodToCall(String methodToCall) {
		this.methodToCall = methodToCall;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}
}