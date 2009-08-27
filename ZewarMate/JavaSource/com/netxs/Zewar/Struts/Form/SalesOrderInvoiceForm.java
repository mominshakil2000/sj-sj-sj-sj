package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceGemForm;

public class SalesOrderInvoiceForm extends ValidatorActionForm {

	private char hasFormInitialized; 
	private boolean insertable;
	private String salesInvoiceId;
	private String salesOrderId;
	private String voucherPrefix;
	private String voucherPostfix;
	private String salesOrderTrackingId;
	private String customerLedgerAccountId ;
	private String remarks;
	private String invoiceDate;
	private String totalMetalValue;
	private String totalInvoiceAmount;
	private String totalGemValue;
	private String totalMaking;
	private String totalDiscount;
	private String totalAdvance;
	private String receiveable;
	private String ledgerEntryId;
	private String salesInvoiceStatus;
	private String moveInInventoryStatus;

	private Vector invoiceItem = new Vector();

	private String advanceCash;
	private Vector customerAdvanceMetal = new Vector();
	
	private Vector customerUnusedGem = new Vector(); 
	
	
	public SalesOrderInvoiceForm() {
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

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}
	
	
	
	// Invoice Item
	public SalesOrderInvoiceItemForm getInvoiceItem(int index) {
		if (this.invoiceItem.size() < index + 1)
			((Vector) this.invoiceItem).setSize(index + 1);
		if (this.invoiceItem.get(index) == null) {
			this.invoiceItem.set(index, new SalesOrderInvoiceItemForm());
		}
		return (SalesOrderInvoiceItemForm) this.invoiceItem.get(index);
	}

	public void setInvoiceItem(SalesOrderInvoiceItemForm obj) {
		this.invoiceItem.add(obj);
	}

	public void setListInvoiceItem(Vector obj) {
		this.invoiceItem = obj;
	}

	public Collection getInvoiceItemList() {
		return this.invoiceItem;
	}
	
	// Customer Metal Item
	public SalesOrderInvoiceMetalForm getCustomerAdvanceMetal(int index) {
		if (this.customerAdvanceMetal.size() < index + 1)
			((Vector) this.customerAdvanceMetal).setSize(index + 1);
		if (this.customerAdvanceMetal.get(index) == null) {
			this.customerAdvanceMetal.set(index, new SalesOrderInvoiceMetalForm());
		}
		return (SalesOrderInvoiceMetalForm) this.customerAdvanceMetal.get(index);
	}

	public void setCustomerAdvanceMetal(SalesOrderInvoiceMetalForm obj) {
		this.customerAdvanceMetal.add(obj);
	}

	public void setListCustomerAdvanceMetal(Vector obj) {
		this.customerAdvanceMetal = obj;
	}

	public Collection getCustomerAdvanceMetalList() {
		return this.customerAdvanceMetal;
	}

	
	// Customer Unused Gems
	public SalesOrderInvoiceGemForm getCustomerUnusedGem(int index) {
		if (this.customerUnusedGem.size() < index + 1)
			((Vector) this.customerUnusedGem).setSize(index + 1);
		if (this.customerUnusedGem.get(index) == null) {
			this.customerUnusedGem.set(index, new SalesOrderInvoiceGemForm());
		}
		return (SalesOrderInvoiceGemForm) this.customerUnusedGem.get(index);
	}

	public void setCustomerUnusedGem(SalesOrderInvoiceGemForm obj) {
		this.customerUnusedGem.add(obj);
	}

	public void setListCustomerUnusedGem(Vector obj) {
		this.customerUnusedGem = obj;
	}

	public Collection getCustomerUnusedGemList() {
		return this.customerUnusedGem;
	}

	

	public String getTotalGemValue() {
		return totalGemValue;
	}


	public void setTotalGemValue(String totalGemValue) {
		this.totalGemValue = totalGemValue;
	}


	public String getTotalMaking() {
		return totalMaking;
	}


	public void setTotalMaking(String totalMaking) {
		this.totalMaking = totalMaking;
	}


	public String getTotalMetalValue() {
		return totalMetalValue;
	}


	public void setTotalMetalValue(String totalMetalValue) {
		this.totalMetalValue = totalMetalValue;
	}

	public String getSalesOrderTrackingId() {
		return salesOrderTrackingId;
	}

	public void setSalesOrderTrackingId(String salesOrderTrackingId) {
		this.salesOrderTrackingId = salesOrderTrackingId;
	}

	public String getAdvanceCash() {
		return advanceCash;
	}

	public void setAdvanceCash(String advanceCash) {
		this.advanceCash = advanceCash;
	}

	public String getReceiveable() {
		return receiveable;
	}

	public void setReceiveable(String receiveable) {
		this.receiveable = receiveable;
	}

	public String getTotalAdvance() {
		return totalAdvance;
	}

	public void setTotalAdvance(String totalAdvanceCash) {
		this.totalAdvance = totalAdvanceCash;
	}

	public String getTotalInvoiceAmount() {
		return totalInvoiceAmount;
	}

	public void setTotalInvoiceAmount(String totalInvoiceAmount) {
		this.totalInvoiceAmount = totalInvoiceAmount;
	}

	public String getVoucherPostfix() {
		return voucherPostfix;
	}

	public void setVoucherPostfix(String voucherPostFix) {
		this.voucherPostfix = voucherPostFix;
	}

	public String getVoucherPrefix() {
		return voucherPrefix;
	}

	public void setVoucherPrefix(String voucherPrefix) {
		this.voucherPrefix = voucherPrefix;
	}

	public String getSalesInvoiceId() {
		return salesInvoiceId;
	}

	public void setSalesInvoiceId(String salesInvoiceId) {
		this.salesInvoiceId = salesInvoiceId;
	}

	public String getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public String getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(String totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public String getMoveInInventoryStatus() {
		return moveInInventoryStatus;
	}

	public void setMoveInInventoryStatus(String moveInInventoryStatus) {
		this.moveInInventoryStatus = moveInInventoryStatus;
	}

	public String getSalesInvoiceStatus() {
		return salesInvoiceStatus;
	}

	public void setSalesInvoiceStatus(String salesInvoiceStatus) {
		this.salesInvoiceStatus = salesInvoiceStatus;
	}
}