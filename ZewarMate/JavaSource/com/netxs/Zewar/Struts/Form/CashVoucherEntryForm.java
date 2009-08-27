/*
 * Created on Sep 27, 2004
 */
package com.netxs.Zewar.Struts.Form;

public class CashVoucherEntryForm { 

	private boolean insertable; 

	private String voucherEntryId;

	private String ledgerEntryId;

	private String ledgerAccountId;

	private String ledgerAccountTitle;

	private String amount;

	private String narration;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public String getLedgerAccountId() {
		return ledgerAccountId;
	}

	public void setLedgerAccountId(String ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}

	public String getLedgerAccountTitle() {
		return ledgerAccountTitle;
	}

	public void setLedgerAccountTitle(String ledgerAccountTitle) {
		this.ledgerAccountTitle = ledgerAccountTitle;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration.replaceAll("\r\n","\\\\r");
	}

	public String getVoucherEntryId() {
		return voucherEntryId;
	}

	public void setVoucherEntryId(String voucherEntryId) {
		this.voucherEntryId = voucherEntryId;
	}

	public String getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
}