package com.netxs.Zewar.Struts.Form;

public class InventoryPurchaseVoucherItemForm {

	private boolean insertable;
	private String inventoryPurchaseVoucherItemId;
	private String inventoryItemEntryIdIn;
	private String ledgerEntryId;
	private String itemId;
	private String quantity;
	private String weight;
	private String weightUnitId; 
	private String rate;	
	private String itemValueCalculateBy;
	private String itemValue;
	private String otherCharges;
	
	public boolean isInsertable() {
		return insertable;
	}
	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}
	public String getInventoryItemEntryIdIn() {
		return inventoryItemEntryIdIn;
	}
	public void setInventoryItemEntryIdIn(String inventoryItemEntryIdIn) {
		this.inventoryItemEntryIdIn = inventoryItemEntryIdIn;
	}
	public String getInventoryPurchaseVoucherItemId() {
		return inventoryPurchaseVoucherItemId;
	}
	public void setInventoryPurchaseVoucherItemId(
			String inventoryPurchaseVoucherItemId) {
		this.inventoryPurchaseVoucherItemId = inventoryPurchaseVoucherItemId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getItemValueCalculateBy() {
		return itemValueCalculateBy;
	}
	public void setItemValueCalculateBy(String itemValueCalculateBy) {
		this.itemValueCalculateBy = itemValueCalculateBy;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getWeightUnitId() {
		return weightUnitId;
	}
	public void setWeightUnitId(String weightUnitId) {
		this.weightUnitId = weightUnitId;
	}
	public String getLedgerEntryId() {
		return ledgerEntryId;
	}
	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
	public String getOtherCharges() {
		return otherCharges;
	}
	public void setOtherCharges(String otherChages) {
		this.otherCharges = otherChages;
	}
}