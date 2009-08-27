package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class LedgerAccountForm extends ValidatorActionForm {

	private String ledgerAccountId;

	private String ledgerAccountTypeId;

	private String accountActive;

	private String accountDescriptionLevel;

	private String parentLedgerAccountId;

	private String accountCreateDate;

	private String title;

	private String description;

	private String accountCodePrefix;

	private String accountCodePostfix;

	private String openingBalance;

	private String entryDebitCredit;

	private byte isInventoryAccount;

	private char hasFormInitialized;

	private String parentLedgerAccountCodePrefix;

	public LedgerAccountForm() {
		this.hasFormInitialized = 'N';
		this.ledgerAccountTypeId = "";
		this.accountActive = "N";
		this.accountDescriptionLevel = "1";
		this.parentLedgerAccountId = "";
		this.openingBalance = "0.0";
		this.entryDebitCredit = "D";
	}

	public String getAccountActive() {
		return accountActive;
	}

	public void setAccountActive(String accountActive) {
		this.accountActive = accountActive;
	}

	public String getAccountCodePostfix() {
		return accountCodePostfix;
	}

	public void setAccountCodePostfix(String accountCodePostfix) {
		this.accountCodePostfix = accountCodePostfix;
	}

	public String getAccountCodePrefix() {
		return accountCodePrefix;
	}

	public void setAccountCodePrefix(String accountCodePrefix) {
		this.accountCodePrefix = accountCodePrefix;
	}

	public String getAccountCreateDate() {
		return accountCreateDate;
	}

	public void setAccountCreateDate(String accountCreateDate) {
		this.accountCreateDate = accountCreateDate;
	}

	public String getAccountDescriptionLevel() {
		return accountDescriptionLevel;
	}

	public void setAccountDescriptionLevel(String accountLevel) {
		this.accountDescriptionLevel = accountLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLedgerAccountId() {
		return ledgerAccountId;
	}

	public void setLedgerAccountId(String ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}

	public String getLedgerAccountTypeId() {
		return ledgerAccountTypeId;
	}

	public void setLedgerAccountTypeId(String ledgerAccountTypeId) {
		this.ledgerAccountTypeId = ledgerAccountTypeId;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getEntryDebitCredit() {
		return entryDebitCredit;
	}

	public void setEntryDebitCredit(String openingBalanceType) {
		this.entryDebitCredit = openingBalanceType;
	}

	public String getParentLedgerAccountId() {
		return parentLedgerAccountId;
	}

	public void setParentLedgerAccountId(String parentLedgerAccountId) {
		this.parentLedgerAccountId = parentLedgerAccountId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getParentLedgerAccountCodePrefix() {
		return parentLedgerAccountCodePrefix;
	}

	public void setParentLedgerAccountCodePrefix(
			String parentLedgerAccountCodePrefix) {
		this.parentLedgerAccountCodePrefix = parentLedgerAccountCodePrefix;
	}

	public String getIsInventoryAccount() {
		return Byte.toString(isInventoryAccount);
	}

	public void setIsInventoryAccount(String isInventoryAccount) {
		this.isInventoryAccount = Byte.parseByte(isInventoryAccount);
	}
}