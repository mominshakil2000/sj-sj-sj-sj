package com.netxs.Zewar.Struts.Form;

import java.util.List;
import java.util.Vector;

import org.apache.struts.validator.ValidatorActionForm;

public class CashVoucherForm extends ValidatorActionForm {

	private String voucherId;

	private String voucherPrefix;

	private String voucherPostfix;

	private String ledgerAccountId;

	private String entryDate;

	private List voucherEntry = new Vector();

	private char hasFormInitialized;
	
	private String removeVoucherEntryIds;
	
	private String removeLedgerEntryIds;

	public CashVoucherForm() {
		this.voucherId = "0";
		this.voucherPrefix = "";
		this.ledgerAccountId = "0";
		this.hasFormInitialized = 'N';
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String cashVoucherCartId) {
		this.voucherId = cashVoucherCartId;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getLedgerAccountId() {
		return ledgerAccountId;
	}

	public void setLedgerAccountId(String ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String voucherDate) {
		this.entryDate = voucherDate;
	}

	public String getVoucherPrefix() {
		return voucherPrefix;
	}

	public void setVoucherPrefix(String voucherPrefix) {
		this.voucherPrefix = voucherPrefix;
	}

	public List getListVoucherEntry() {
		return voucherEntry;
	}

	public void setListVoucherEntry(List voucherEntry) {
		this.voucherEntry = voucherEntry;
	}

	public void setVoucherEntry(CashVoucherEntryForm obj) {
		this.voucherEntry.add(obj);
	}

	public CashVoucherEntryForm getVoucherEntry(int index) {

		if (this.voucherEntry.size() < index + 1)
			((Vector) this.voucherEntry).setSize(index + 1);

		if (this.voucherEntry.get(index) == null) {
			this.voucherEntry.set(index, new CashVoucherEntryForm());
		}
		return (CashVoucherEntryForm) this.voucherEntry.get(index);
	}

	public String getVoucherPostfix() {
		return voucherPostfix;
	}

	public void setVoucherPostfix(String voucherPostfix) {
		this.voucherPostfix = voucherPostfix;
	}

	public String getRemoveVoucherEntryIds() {
		return removeVoucherEntryIds;
	}

	public void setRemoveVoucherEntryIds(String removeVoucherEntryIds) {
		this.removeVoucherEntryIds = removeVoucherEntryIds;
	}

	public String getRemoveLedgerEntryIds() {
		return removeLedgerEntryIds;
	}

	public void setRemoveLedgerEntryIds(String removeLedgerEntryIds) {
		this.removeLedgerEntryIds = removeLedgerEntryIds;
	}
}