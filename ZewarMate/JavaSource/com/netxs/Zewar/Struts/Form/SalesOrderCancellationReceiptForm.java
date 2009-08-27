package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;

public class SalesOrderCancellationReceiptForm extends ValidatorActionForm {

	private char hasFormInitialized;
	private String salesOrderCancellationReceiptId;
	private String voucherPrefix;
	private String voucherPostfix;
	private String salesOrderId;
	private String salesOrderTrackingId;
	private String customerLedgerAccountId;
	private String cancellationReceiptDate;
	private String cancellationCharges;
	private String cancellationChargesLedgerEntryId;
	private String remarks;
	private Vector advanceMetal = new Vector();
	private Vector unusedAdvanceGem = new Vector();
	private Vector usedAdvanceGem = new Vector();
	private String cashVoucherId;
	private String cashVoucherEntryId;
	private String purchaseLedgerEntryId;
	private String advanceGemInventoryPurchaseVoucherId;
	
	public SalesOrderCancellationReceiptForm() {
	}


	public String getCustomerLedgerAccountId() {
		return customerLedgerAccountId;
	}

	public void setCustomerLedgerAccountId(String customerId) {
		this.customerLedgerAccountId = customerId;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getCancellationReceiptDate() {
		return cancellationReceiptDate;
	}

	public void setCancellationReceiptDate(String jobReceiptDate) {
		this.cancellationReceiptDate = jobReceiptDate;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	
	
	// Receipt Advance Metal
	public SalesOrderCancellationReceiptMetalForm getAdvanceMetal(int index) {
		if (this.advanceMetal.size() < index + 1)
			((Vector) this.advanceMetal).setSize(index + 1);
		if (this.advanceMetal.get(index) == null) {
			this.advanceMetal.set(index, new SalesOrderCancellationReceiptMetalForm());
		}
		return (SalesOrderCancellationReceiptMetalForm) this.advanceMetal.get(index);
	}

	public void setAdvanceMetal(SalesOrderCancellationReceiptMetalForm obj) {
		this.advanceMetal.add(obj);
	}

	public void setAdvanceMetal(Vector obj) {
		this.advanceMetal = obj;
	}

	public Collection getAdvanceMetalList() {
		return this.advanceMetal;
	}
	
	// Receipt Advance Gems
	public SalesOrderCancellationReceiptGemForm getUnusedAdvanceGem(int index) {
		if (this.unusedAdvanceGem.size() < index + 1)
			((Vector) this.unusedAdvanceGem).setSize(index + 1);
		if (this.unusedAdvanceGem.get(index) == null) {
			this.unusedAdvanceGem.set(index, new SalesOrderCancellationReceiptGemForm());
		}
		return (SalesOrderCancellationReceiptGemForm) this.unusedAdvanceGem.get(index);
	}

	public void setUnusedAdvanceGem(SalesOrderCancellationReceiptGemForm obj) {
		this.unusedAdvanceGem.add(obj);
	}

	public void setUnusedAdvanceGem(Vector obj) {
		this.unusedAdvanceGem = obj;
	}

	public Collection getUnusedAdvanceGemList() {
		return this.unusedAdvanceGem;
	}


	// Receipt Used Advance Gems
	public SalesOrderCancellationReceiptGemForm getUsedAdvanceGem(int index) {
		if (this.usedAdvanceGem.size() < index + 1)
			((Vector) this.usedAdvanceGem).setSize(index + 1);
		if (this.usedAdvanceGem.get(index) == null) {
			this.usedAdvanceGem.set(index, new SalesOrderCancellationReceiptGemForm());
		}
		return (SalesOrderCancellationReceiptGemForm) this.usedAdvanceGem.get(index);
	}

	public void setUsedAdvanceGem(SalesOrderCancellationReceiptGemForm obj) {
		this.usedAdvanceGem.add(obj);
	}

	public void setUsedAdvanceGem(Vector obj) {
		this.usedAdvanceGem = obj;
	}

	public Collection getUsedAdvanceGemList() {
		return this.usedAdvanceGem;
	}

	public String getRemarks() {
		return remarks;
	}


	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public String getCancellationCharges() {
		return cancellationCharges;
	}


	public void setCancellationCharges(String cancellationCharges) {
		this.cancellationCharges = cancellationCharges;
	}


	public String getVoucherPostfix() {
		return voucherPostfix;
	}


	public void setVoucherPostfix(String voucherPostfix) {
		this.voucherPostfix = voucherPostfix;
	}


	public String getVoucherPrefix() {
		return voucherPrefix;
	}


	public void setVoucherPrefix(String voucherPrefix) {
		this.voucherPrefix = voucherPrefix;
	}


	public String getAdvanceGemInventoryPurchaseVoucherId() {
		return advanceGemInventoryPurchaseVoucherId;
	}


	public void setAdvanceGemInventoryPurchaseVoucherId(
			String advanceGemInventoryPurchaseVoucherId) {
		this.advanceGemInventoryPurchaseVoucherId = advanceGemInventoryPurchaseVoucherId;
	}


	public String getSalesOrderCancellationReceiptId() {
		return salesOrderCancellationReceiptId;
	}


	public void setSalesOrderCancellationReceiptId(
			String salesOrderCancellationReceiptId) {
		this.salesOrderCancellationReceiptId = salesOrderCancellationReceiptId;
	}


	public String getSalesOrderTrackingId() {
		return salesOrderTrackingId;
	}


	public void setSalesOrderTrackingId(String orderTrackingId) {
		this.salesOrderTrackingId = orderTrackingId;
	}


	public String getCancellationChargesLedgerEntryId() {
		return cancellationChargesLedgerEntryId;
	}


	public void setCancellationChargesLedgerEntryId(
			String cancellationChargesLedgerEntryId) {
		this.cancellationChargesLedgerEntryId = cancellationChargesLedgerEntryId;
	}


	public String getCashVoucherEntryId() {
		return cashVoucherEntryId;
	}


	public void setCashVoucherEntryId(String cashVoucherEntryId) {
		this.cashVoucherEntryId = cashVoucherEntryId;
	}


	public String getCashVoucherId() {
		return cashVoucherId;
	}


	public void setCashVoucherId(String cashVoucherId) {
		this.cashVoucherId = cashVoucherId;
	}


	public String getPurchaseLedgerEntryId() {
		return purchaseLedgerEntryId;
	}


	public void setPurchaseLedgerEntryId(String ledgerEntryId) {
		this.purchaseLedgerEntryId = ledgerEntryId;
	}

}