package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;


public class SalesOrderCancellationReceiptMetalForm extends ValidatorActionForm {

	private String salesOrderCancellationReceiptAdvanceMetalId;
	private String itemId;
	private String itemName;
	private String keepInInvenory;
	private String weight;
	private String weightUnitId;
	private String weightUnit;
	private String wastageRate;
	private String wastageUnitId;
	private String wastageUnit;
	private String netWeight;
	private String rate;
	private String value;
	private String inventoryEntryId;
	private String ledgerEntryId;
	private String generalVoucherId;
	
	public String getGeneralVoucherId() {
		return generalVoucherId;
	}

	public void setGeneralVoucherId(String generalVoucherId) {
		this.generalVoucherId = generalVoucherId;
	}

	public String getInventoryEntryId() {
		return inventoryEntryId;
	}

	public void setInventoryEntryId(String inventoryEntryId) {
		this.inventoryEntryId = inventoryEntryId;
	}

	public SalesOrderCancellationReceiptMetalForm() {
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

	public void setWastageRate(String wastageRate) {
		this.wastageRate = wastageRate;
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

	public String getKeepInInvenory() {
		return keepInInvenory;
	}

	public void setKeepInInvenory(String keepInInvenory) {
		this.keepInInvenory = keepInInvenory;
	}

	public String getSalesOrderCancellationReceiptAdvanceMetalId() {
		return salesOrderCancellationReceiptAdvanceMetalId;
	}

	public void setSalesOrderCancellationReceiptAdvanceMetalId(
			String salesOrderCancellationReceiptAdvanceMetalId) {
		this.salesOrderCancellationReceiptAdvanceMetalId = salesOrderCancellationReceiptAdvanceMetalId;
	}

	public String getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

}