package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;


public class SalesOrderCancellationReceiptGemForm extends ValidatorActionForm {

	private String salesOrderCancellationReceiptAdvanceGemId;
	private String itemId;
	private String itemName;
	private String keepInInvenory;
	private String quantity;
	private String netWeight;
	private String weight;
	private String rate;
	private String value;
	private String weightUnitId;
	private String weightUnit;
	private String inventoryPurchaseVoucherItemId;
	private String inventoryGemsEntryIdIn;
	private String inventoryGemsEntryIdOut;
	private String ledgerEntryId;
	
	public String getLedgerEntryId() {
		return ledgerEntryId;
	}

	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}

	public String getInventoryGemsEntryIdIn() {
		return inventoryGemsEntryIdIn;
	}

	public void setInventoryGemsEntryIdIn(String inventoryGemsEntryId) {
		this.inventoryGemsEntryIdIn = inventoryGemsEntryId;
	}

	public String getInventoryPurchaseVoucherItemId() {
		return inventoryPurchaseVoucherItemId;
	}

	public void setInventoryPurchaseVoucherItemId(
			String inventoryPurchaseVoucherItemId) {
		this.inventoryPurchaseVoucherItemId = inventoryPurchaseVoucherItemId;
	}

	public SalesOrderCancellationReceiptGemForm() {
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

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String netWeightUnit) {
		this.weightUnit = netWeightUnit;
	}

	public String getWeightUnitId() {
		return weightUnitId;
	}

	public void setWeightUnitId(String netWeightUnitId) {
		this.weightUnitId = netWeightUnitId;
	}

	public String getKeepInInvenory() {
		return keepInInvenory;
	}

	public void setKeepInInvenory(String keepCustomerGemInInvenory) {
		this.keepInInvenory = keepCustomerGemInInvenory;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSalesOrderCancellationReceiptAdvanceGemId() {
		return salesOrderCancellationReceiptAdvanceGemId;
	}

	public void setSalesOrderCancellationReceiptAdvanceGemId(
			String salesOrderCancellationReceiptAdvanceGemId) {
		this.salesOrderCancellationReceiptAdvanceGemId = salesOrderCancellationReceiptAdvanceGemId;
	}

	public String getInventoryGemsEntryIdOut() {
		return inventoryGemsEntryIdOut;
	}

	public void setInventoryGemsEntryIdOut(String inventoryGemsEntryIdOut) {
		this.inventoryGemsEntryIdOut = inventoryGemsEntryIdOut;
	}


}