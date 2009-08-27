package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;

public class SalesOrderForm extends ValidatorActionForm {

	private char hasFormInitialized;

	private boolean insertable;
	
	private String salesOrderId;

	private String salesOrderStatusId; 

	private String comments;
	
	private String cancelSalesOrderId;
	
	private char cloneType;

	private String cloneFromSalesOrderId;
	
	private String description;

	private String estimatedDeliveryDate;

	private String orderDate;

	private String customerLedgerAccountId;

	private String orderPostByUserId;

	private String salesOrderTrackingId;

	private byte orderCreated;

	private String advanceCash;
	
	private String advanceCashLedgerEntryId;
	
	private String advanceCashCashBookVoucherId;
	
	private String advanceCashCashBookVoucherEntryId;

	private Vector advanceMetal = new Vector();

	private Vector advanceGem = new Vector();

	private Vector orderItem = new Vector();
	
	private String deleteAdvanceGem;

	private String deleteAdvanceMetal;

	private String deleteOrderItem;

	private String deleteEstimatedMetal;

	private String deleteEstimatedGem;



	
	public SalesOrderForm() {
		this.salesOrderId = "0";
		this.customerLedgerAccountId = "0";
		this.orderPostByUserId = "0";
		this.orderDate = "";
		this.estimatedDeliveryDate = "";
		this.advanceCash = "0.0";
		this.description = "";
		this.comments = "";
		this.orderCreated = '0';
		this.hasFormInitialized = 'N';
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}
	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getCustomerLedgerAccountId() {
		return customerLedgerAccountId;
	}

	public void setCustomerLedgerAccountId(String orderByCustomerId) {
		this.customerLedgerAccountId = orderByCustomerId;
	}

	public String getOrderPostByUserId() {
		return orderPostByUserId;
	}

	public void setOrderPostByUserId(String orderByUserId) {
		this.orderPostByUserId = orderByUserId;
	}

	public byte getOrderCreated() {
		return orderCreated;
	}

	public void setOrderCreated(byte orderCreated) {
		this.orderCreated = orderCreated;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getSalesOrderTrackingId() {
		return salesOrderTrackingId;
	}

	public void setSalesOrderTrackingId(String orderTrackingId) {
		this.salesOrderTrackingId = orderTrackingId;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getSalesOrderStatusId() {
		return salesOrderStatusId;
	}

	public void setSalesOrderStatusId(String salesOrderStatusId) {
		this.salesOrderStatusId = salesOrderStatusId;
	}

	public String getAdvanceCash() {
		return advanceCash;
	}

	public void setAdvanceCash(String advanceCash) {
		this.advanceCash = advanceCash;
	}

	// Advance Gem Item
	public SalesOrderAdvanceGemForm getAdvanceGem(int index) {
		if (this.advanceGem.size() < index + 1)
			((Vector) this.advanceGem).setSize(index + 1);
		if (this.advanceGem.get(index) == null) {
			this.advanceGem.set(index, new SalesOrderAdvanceGemForm());
		}
		return (SalesOrderAdvanceGemForm) this.advanceGem.get(index);
	}

	public void setAdvanceGem(SalesOrderAdvanceGemForm obj) {
		this.advanceGem.add(obj);
	}

	public void setAdvanceGem(Vector obj) {
		this.advanceGem = obj;
	}

	public Collection getAdvanceGemList() {
		return this.advanceGem;
	}

	// Advance Metal Item
	public SalesOrderAdvanceMetalForm getAdvanceMetal(int index) {
		if (this.advanceMetal.size() < index + 1)
			((Vector) this.advanceMetal).setSize(index + 1);
		if (this.advanceMetal.get(index) == null)
			this.advanceMetal.set(index, new SalesOrderAdvanceMetalForm());
		return (SalesOrderAdvanceMetalForm) this.advanceMetal.get(index);
	}

	public void setAdvanceMetal(SalesOrderAdvanceMetalForm obj) {
		this.advanceMetal.add(obj);
	}

	public void setAdvanceMetal(Vector obj) {
		this.advanceMetal = obj;
	}

	public Collection getAdvanceMetalList() {
		return this.advanceMetal;
	}

	// Sales Order Item
	public SalesOrderItemForm getOrderItem(int index) {
		if (this.orderItem.size() < index + 1)
			((Vector) this.orderItem).setSize(index + 1);

		if (this.orderItem.get(index) == null) {
			this.orderItem.set(index, new SalesOrderItemForm());
		}
		return (SalesOrderItemForm) this.orderItem.get(index);
	}

	public void setOrderItem(SalesOrderItemForm obj) {
		this.orderItem.add(obj);
	}

	public void setOrderItem(Vector obj) {
		this.orderItem = obj;
	}

	public Vector getOrderItemList() {
		return this.orderItem;
	}

	// Delete List
	public String getDeleteAdvanceGem() {
		return deleteAdvanceGem;
	}

	public void setDeleteAdvanceGem(String advanceGemDeleteList) {
		this.deleteAdvanceGem = advanceGemDeleteList;
	}

	public String getDeleteAdvanceMetal() {
		return deleteAdvanceMetal;
	}

	public void setDeleteAdvanceMetal(String advanceMetalDeleteList) {
		this.deleteAdvanceMetal = advanceMetalDeleteList;
	}

	public String getDeleteOrderItem() {
		return deleteOrderItem;
	}

	public void setDeleteOrderItem(String itemDeleteList) {
		this.deleteOrderItem = itemDeleteList;
	}

	public String getDeleteEstimatedMetal() {
		return deleteEstimatedMetal;
	}

	public void setDeleteEstimatedMetal(String itemEstimationDeleteList) {
		this.deleteEstimatedMetal = itemEstimationDeleteList;
	}

	public String getDeleteEstimatedGem() {
		return deleteEstimatedGem;
	}

	public void setDeleteEstimatedGem(String itemEstimatedGemDeleteList) {
		this.deleteEstimatedGem = itemEstimatedGemDeleteList;
	}

	public char getCloneType() {
		return cloneType;
	}

	public void setCloneType(char cloneType) {
		this.cloneType = cloneType;
	}

	public String getCloneFromSalesOrderId() {
		return cloneFromSalesOrderId;
	}

	public void setCloneFromSalesOrderId(String cloneFromSalesOrderId) {
		this.cloneFromSalesOrderId = cloneFromSalesOrderId;
	}

	public String getAdvanceCashLedgerEntryId() {
		return advanceCashLedgerEntryId;
	}

	public void setAdvanceCashLedgerEntryId(String advanceCashLedgerEntryId) {
		this.advanceCashLedgerEntryId = advanceCashLedgerEntryId;
	}

	public String getAdvanceCashCashBookVoucherEntryId() {
		return advanceCashCashBookVoucherEntryId;
	}

	public void setAdvanceCashCashBookVoucherEntryId(String advanceCashVoucherEntryId) {
		this.advanceCashCashBookVoucherEntryId = advanceCashVoucherEntryId;
	}

	public String getAdvanceCashCashBookVoucherId() {
		return advanceCashCashBookVoucherId;
	}

	public void setAdvanceCashCashBookVoucherId(String advanceCashVoucherId) {
		this.advanceCashCashBookVoucherId = advanceCashVoucherId;
	}

	public String getCancelSalesOrderId() {
		return cancelSalesOrderId;
	}

	public void setCancelSalesOrderId(String cancelSalesOrderId) {
		this.cancelSalesOrderId = cancelSalesOrderId;
	}

/* -
  	public String getAdvanceMetalPurchaseVoucherId() {
		return advanceMetalPurchaseVoucherId;
	}

	public void setAdvanceMetalPurchaseVoucherId(String inventoryPurchaseVoucherId) {
		this.advanceMetalPurchaseVoucherId = inventoryPurchaseVoucherId;
	}
*/
}