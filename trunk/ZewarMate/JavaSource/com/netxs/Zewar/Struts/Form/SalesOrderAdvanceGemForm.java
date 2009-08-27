package com.netxs.Zewar.Struts.Form;

public class SalesOrderAdvanceGemForm {

	private boolean insertable;

	private String salesOrderAdvanceGemId;

	private String itemId;
	
	private String itemName;

	private String itemQuantity;

	private String itemWeight;

	private String itemWeightUnitId;

	private String itemWeightUnit;
	
	private String itemRate;
	
	private String itemValue;
	
	private String comments;
	
	private String keepInInvenory;

	public SalesOrderAdvanceGemForm() {
		this.salesOrderAdvanceGemId = "0";
		this.itemId = "0";
		this.itemQuantity = "0";
		this.itemWeight = "0.0";
		this.itemWeightUnitId = "0";
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

	public void setInsertable(boolean inserted) {
		this.insertable = inserted;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(String quantity) {
		this.itemQuantity = quantity;
	}

	public String getSalesOrderAdvanceGemId() {
		return salesOrderAdvanceGemId;
	}

	public void setSalesOrderAdvanceGemId(String salesOrderAdvanceGemId) {
		this.salesOrderAdvanceGemId = salesOrderAdvanceGemId;
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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemWeightUnit() {
		return itemWeightUnit;
	}

	public void setItemWeightUnit(String weightUnit) {
		this.itemWeightUnit = weightUnit;
	}

	public String getItemRate() {
		return itemRate;
	}

	public void setItemRate(String rate) {
		this.itemRate = rate;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String value) {
		this.itemValue = value;
	}
}