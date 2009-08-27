package com.netxs.Zewar.Struts.Form;

public class SalesOrderAdvanceMetalForm {

	private boolean insertable;

	private String salesOrderAdvanceMetalId;

	private String itemId;
	
	private String itemName;

	private String itemWeight;

	private String itemWeightUnitId;
	
	private String itemWeightUnit;
	
	private String itemRate;

	private String itemWastageRate;

	private String itemWastageRateUnitId;

	private String itemWastageUnit;
	
	private String itemNetWeight;

	private String itemValue;

	private String comments;

	private String keepInInvenory;
	
	private String entryDate;
	private String ledgerEntryId;
	private String inventoryMetalEntryIdIn;
	private String inventoryPurchaseVoucherItemId;
	private String inventoryPurchaseVoucherId;
	private boolean insertablePurchaseItem;	
	
	public SalesOrderAdvanceMetalForm() {
		this.salesOrderAdvanceMetalId = "0";
		this.itemId = "0";
		this.itemWeight = "0.0";
		this.itemWeightUnitId = "0";
		this.itemRate = "0.0";
		this.itemWastageRate = "0";
		this.itemWastageRateUnitId = "0";
		this.comments = "";
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemNetWeight() {
		return itemNetWeight;
	}

	public void setItemNetWeight(String netWeight) {
		this.itemNetWeight = netWeight;
	}

	public String getItemRate() {
		return itemRate;
	}

	public void setItemRate(String rate) {
		this.itemRate = rate;
	}

	public String getSalesOrderAdvanceMetalId() {
		return salesOrderAdvanceMetalId;
	}

	public void setSalesOrderAdvanceMetalId(String salesOrderAdvanceMetalId) {
		this.salesOrderAdvanceMetalId = salesOrderAdvanceMetalId;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String value) {
		this.itemValue = value;
	}

	public String getItemWastageRate() {
		return itemWastageRate;
	}

	public void setItemWastageRate(String wastageRate) {
		this.itemWastageRate = wastageRate;
	}

	public String getItemWastageRateUnitId() {
		return itemWastageRateUnitId;
	}

	public void setItemWastageRateUnitId(String wastageUnitId) {
		this.itemWastageRateUnitId = wastageUnitId;
	}

	public String getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(String weight) {
		this.itemWeight = weight;
	}

	public String getItemWeightUnitId() {
		return itemWeightUnitId;
	}

	public void setItemWeightUnitId(String weightUnitId) {
		this.itemWeightUnitId = weightUnitId;
	}

	public String getKeepInInvenory() {
		return keepInInvenory;
	}

	public void setKeepInInvenory(String keepInInvenory) {
		this.keepInInvenory = keepInInvenory;
	}

	public String getItemWastageUnit() {
		return itemWastageUnit;
	}

	public void setItemWastageUnit(String wastageUnit) {
		this.itemWastageUnit = wastageUnit;
	}

	public String getItemWeightUnit() {
		return itemWeightUnit;
	}

	public void setItemWeightUnit(String weightUnit) {
		this.itemWeightUnit = weightUnit;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getInventoryMetalEntryIdIn() {
		return inventoryMetalEntryIdIn;
	}

	public void setInventoryMetalEntryIdIn(String inventoryItemEntryIdIn) {
		this.inventoryMetalEntryIdIn = inventoryItemEntryIdIn;
	}

	public String getInventoryPurchaseVoucherItemId() {
		return inventoryPurchaseVoucherItemId;
	}

	public void setInventoryPurchaseVoucherItemId(
			String inventoryPurchaseVoucherItemId) {
		this.inventoryPurchaseVoucherItemId = inventoryPurchaseVoucherItemId;
	}

	public String getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
	
	public boolean isInsertablePurchaseItem() {
		return insertablePurchaseItem;
	}

	public void setInsertablePurchaseItem(boolean insertableInventoryItem) {
		this.insertablePurchaseItem = insertableInventoryItem;
	}

	public String getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(String entryId) {
		this.entryDate = entryId;
	}

	public String getInventoryPurchaseVoucherId() {
		return inventoryPurchaseVoucherId;
	}

	public void setInventoryPurchaseVoucherId(String inventoryPurchaseVoucherId) {
		this.inventoryPurchaseVoucherId = inventoryPurchaseVoucherId;
	}
}