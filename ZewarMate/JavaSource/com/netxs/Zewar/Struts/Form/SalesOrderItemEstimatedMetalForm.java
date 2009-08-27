package com.netxs.Zewar.Struts.Form;

public class SalesOrderItemEstimatedMetalForm {

	private boolean insertable;

	private String salesOrderItemInfoEstimatedMetalId;

	private String itemId;

	private String itemWeight;

	private String itemWeightUnitId;

	private String itemRate;

	private String itemWastageRate;

	private String itemWastageRateUnitId;

	private String itemNetWeight;

	private String itemValue;

	private String comments;

	public SalesOrderItemEstimatedMetalForm() {
		this.salesOrderItemInfoEstimatedMetalId = "0";
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

	public String getSalesOrderItemInfoEstimatedMetalId() {
		return salesOrderItemInfoEstimatedMetalId;
	}

	public void setSalesOrderItemInfoEstimatedMetalId(
			String salesOrderAdvanceMetalId) {
		this.salesOrderItemInfoEstimatedMetalId = salesOrderAdvanceMetalId;
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
}