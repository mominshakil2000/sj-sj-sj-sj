package com.netxs.Zewar.Struts.Form;

public class InventoryPurchaseVoucherJewelleryGemForm {

	private boolean insertable;
	private String inventoryPurchaseVoucherJewelleryGemId;
	private String itemId;
	private String quantity;
	private String weight;
	private String weightUnitId; 
	private String rate;	
	private String itemValueCalculateBy;
	private String itemValue;
	
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
	public String getInventoryPurchaseVoucherJewelleryGemId() {
		return inventoryPurchaseVoucherJewelleryGemId;
	}
	public void setInventoryPurchaseVoucherJewelleryGemId(
			String inventoryPurchaseVoucherJewelleryGemId) {
		this.inventoryPurchaseVoucherJewelleryGemId = inventoryPurchaseVoucherJewelleryGemId;
	}
}