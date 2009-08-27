package com.netxs.Zewar.Struts.Form;


import org.apache.struts.validator.ValidatorActionForm;


public class SalesOrderInvoiceGemForm extends ValidatorActionForm {

	private boolean insertable;
	private String salesInvoiceItemGemUsedId;
	private String itemId;
	private String itemName;
	private String weight;
	private String weightUnit;
	private String weightUnitId;
	private String quantity;
	private String netWeight;
	private String rate;
	private String value;
	private String valueCalculateBy;
	private String keepInInvenory;
	private String inventoryGemEntryIdOut;
	
	
	public SalesOrderInvoiceGemForm() {
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

	public String getValueCalculateBy() {
		return valueCalculateBy;
	}

	public void setValueCalculateBy(String valueCalculateBy) {
		this.valueCalculateBy = valueCalculateBy;
	}

	public String getKeepInInvenory() {
		return keepInInvenory;
	}

	public void setKeepInInvenory(String keepCustomerGemInInvenory) {
		this.keepInInvenory = keepCustomerGemInInvenory;
	}

	public String getSalesInvoiceItemGemUsedId() {
		return salesInvoiceItemGemUsedId;
	}

	public void setSalesInvoiceItemGemUsedId(String salesInvoiceItemGemUsedId) {
		this.salesInvoiceItemGemUsedId = salesInvoiceItemGemUsedId;
	}

	public String getInventoryGemEntryIdOut() {
		return inventoryGemEntryIdOut;
	}

	public void setInventoryGemEntryIdOut(String inventoryGemEntryIdOut) {
		this.inventoryGemEntryIdOut = inventoryGemEntryIdOut;
	}

}