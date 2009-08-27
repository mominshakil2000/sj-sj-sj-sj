package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;


public class SalesOrderInvoiceMetalForm extends ValidatorActionForm {


	private boolean insertable;
	private String salesInvoiceItemMetalUsedId;
	private String itemId;
	private String itemName;
	private String weight;
	private String weightUnitId;
	private String weightUnit;
	private String wastageRate;
	private String wastageUnitId;
	private String wastageUnit;
	private String netWeight;
	private String rate;
	private String value;
	
	public SalesOrderInvoiceMetalForm() {
	}

	public boolean isInsertable() {
		return insertable;
	}
	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}
	public String getValue() {
		return value;
	}

	public void setValue(String amount) {
		this.value = amount;
	}


	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String metalWeight) {
		this.weight = metalWeight;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getWastageRate() {
		return wastageRate;
	}

	public void setWastageRate(String wastage) {
		this.wastageRate = wastage;
	}

	public String getWastageUnit() {
		return wastageUnit;
	}

	public void setWastageUnit(String wastageUnit) {
		this.wastageUnit = wastageUnit;
	}

	public String getWastageUnitId() {
		return wastageUnitId;
	}

	public void setWastageUnitId(String wastageUnitId) {
		this.wastageUnitId = wastageUnitId;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getWeightUnitId() {
		return weightUnitId;
	}

	public void setWeightUnitId(String weightUnitId) {
		this.weightUnitId = weightUnitId;
	}

	public String getSalesInvoiceItemMetalUsedId() {
		return salesInvoiceItemMetalUsedId;
	}

	public void setSalesInvoiceItemMetalUsedId(String salesInvoiceItemMetalUsedId) {
		this.salesInvoiceItemMetalUsedId = salesInvoiceItemMetalUsedId;
	}

}