package com.netxs.Zewar.Struts.Form;

public class SalesOrderProcessMetalUsedForm {

	private boolean insertable;

	private String salesOrderProcessMetalUsedId;

	private String itemId;

	private String weight;
	
	private String weightUnitId;

	private String wastageRate;

	private String wastageQuantity;
	
	private String wastageUnitId;

	private String netWeight;
	
	private String inventoryMetalEntryIdOut;

	public SalesOrderProcessMetalUsedForm() {
		this.salesOrderProcessMetalUsedId = "0";
		this.itemId = "0";
		this.weight = "0.0";
		this.weightUnitId = "0";
		this.wastageRate = "0";
		this.wastageQuantity = "0.000";
		this.wastageUnitId = "0";
		this.netWeight = "0.0";
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

	public String getSalesOrderProcessMetalUsedId() {
		return salesOrderProcessMetalUsedId;
	}

	public void setSalesOrderProcessMetalUsedId(
			String salesOrderProcessItemMetalUsedId) {
		this.salesOrderProcessMetalUsedId = salesOrderProcessItemMetalUsedId;
	}

	public String getWastageRate() {
		return wastageRate;
	}

	public void setWastageRate(String wastageRate) {
		this.wastageRate = wastageRate;
	}

	public String getWastageUnitId() {
		return wastageUnitId;
	}

	public void setWastageUnitId(String wastageUnitId) {
		this.wastageUnitId = wastageUnitId;
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

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getWastageQuantity() {
		return wastageQuantity;
	}

	public void setWastageQuantity(String wastageQuantity) {
		this.wastageQuantity = wastageQuantity;
	}

	public String getInventoryMetalEntryIdOut() {
		return inventoryMetalEntryIdOut;
	}

	public void setInventoryMetalEntryIdOut(String inventoryMetalEntryIdOut) {
		this.inventoryMetalEntryIdOut = inventoryMetalEntryIdOut;
	}
}