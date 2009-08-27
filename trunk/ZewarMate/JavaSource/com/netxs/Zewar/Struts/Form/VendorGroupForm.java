package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class VendorGroupForm extends ValidatorActionForm {

	private String vendorTypeId;

	private String vendorTypeName;

	private String vendorTypeDescription;

	private String accountPrefix;

	private String goldAccount;

	private String moneyAccount;

	private String gemAccount;

	private char hasFormInitialized;

	public String getAccountPrefix() {
		return accountPrefix;
	}

	public void setAccountPrefix(String accountPrefix) {
		this.accountPrefix = accountPrefix;
	}

	public String getGemAccount() {
		return gemAccount;
	}

	public void setGemAccount(String gemAccount) {
		this.gemAccount = gemAccount;
	}

	public String getGoldAccount() {
		return goldAccount;
	}

	public void setGoldAccount(String goldAccount) {
		this.goldAccount = goldAccount;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getMoneyAccount() {
		return moneyAccount;
	}

	public void setMoneyAccount(String moneyAccount) {
		this.moneyAccount = moneyAccount;
	}

	public String getVendorTypeDescription() {
		return vendorTypeDescription;
	}

	public void setVendorTypeDescription(String vendorTypeDescription) {
		this.vendorTypeDescription = vendorTypeDescription;
	}

	public String getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(String vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public String getVendorTypeName() {
		return vendorTypeName;
	}

	public void setVendorTypeName(String vendorTypeName) {
		this.vendorTypeName = vendorTypeName;
	}
}