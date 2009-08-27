package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class GeneralVoucherForm extends ValidatorActionForm {

	private String voucherId;

	private String voucherPrefix;

	private String voucherPostfix;

	private String ledgerAccountIdDebit;

	private String ledgerAccountIdCredit;

	private String amount;

	private String narration;

	private String entryDate;

	private String ledgerEntryId;

	private char hasFormInitialized;

	public GeneralVoucherForm() {
		this.voucherId = "0";
		this.voucherPrefix = new String();
		this.ledgerAccountIdDebit = "0";
		this.ledgerAccountIdCredit = "0";
		this.amount = "0.0";
		this.hasFormInitialized = 'N';
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String generalVoucherCartEntryId) {
		this.voucherId = generalVoucherCartEntryId;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getLedgerAccountIdCredit() {
		return ledgerAccountIdCredit;
	}

	public void setLedgerAccountIdCredit(String ledgerAccountIdCredit) {
		this.ledgerAccountIdCredit = ledgerAccountIdCredit;
	}

	public String getLedgerAccountIdDebit() {
		return ledgerAccountIdDebit;
	}

	public void setLedgerAccountIdDebit(String ledgerAccountIdDebit) {
		this.ledgerAccountIdDebit = ledgerAccountIdDebit;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getVoucherPrefix() {
		return voucherPrefix;
	}

	public void setVoucherPrefix(String voucherPrefix) {
		this.voucherPrefix = voucherPrefix;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String voucherDate) {
		this.entryDate = voucherDate;
	}

	public String getVoucherPostfix() {
		return voucherPostfix;
	}

	public void setVoucherPostfix(String voucherPostfix) {
		this.voucherPostfix = voucherPostfix;
	}

	public String getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
}