package com.netxs.Zewar.Struts.Form;

public class SalesOrderItemEstimatedGemForm {

	private boolean insertable;

	private String salesOrderItemInfoEstimatedGemId;

	private String itemId;

	private String itemQuantity;

	private String itemRate;
	
	private String itemWeight;

	private String itemWeightUnitId;

	private String itemValue;
	
	private String itemValueCalculateBy;
	
	private String comments;

	public SalesOrderItemEstimatedGemForm() {
		this.salesOrderItemInfoEstimatedGemId = "0";
		this.itemId = "0";
		this.itemQuantity = "0";
		this.itemRate = "0";
		this.itemWeight = "0.0";
		this.itemWeightUnitId = "0";
		this.itemValue = "0";
		this.itemValueCalculateBy = "0";
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

	public String getSalesOrderItemInfoEstimatedGemId() {
		return salesOrderItemInfoEstimatedGemId;
	}

	public void setSalesOrderItemInfoEstimatedGemId(String salesOrderAdvanceGemId) {
		this.salesOrderItemInfoEstimatedGemId = salesOrderAdvanceGemId;
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

	public String getItemValueCalculateBy() {
		return itemValueCalculateBy;
	}

	public void setItemValueCalculateBy(String valueCalculatedBy) {
		this.itemValueCalculateBy = valueCalculatedBy;
	}
}