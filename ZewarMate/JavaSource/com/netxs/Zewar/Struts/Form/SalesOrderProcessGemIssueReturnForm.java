package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class SalesOrderProcessGemIssueReturnForm extends
		ValidatorActionForm {

	private char hasFormInitialized;
	
	private boolean insertable;

	private String salesOrderProcessGemIssueReturnId;

	private String salesOrderProcessId;
	
	private String transactionDate;

	private String itemStockType;

	private String itemId;

	private String weight;

	private String weightUnitId;

	private String quantity;

	public SalesOrderProcessGemIssueReturnForm() {
		this.hasFormInitialized = 'N';
		this.salesOrderProcessGemIssueReturnId = "0";
		this.salesOrderProcessId = "0";
		this.weight = "0.0";
		this.weightUnitId = "0";
		this.quantity = "0";
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getSalesOrderProcessId() {
		return salesOrderProcessId;
	}

	public void setSalesOrderProcessId(String salesOrderProcessId) {
		this.salesOrderProcessId = salesOrderProcessId;
	}

	public String getSalesOrderProcessGemIssueReturnId() {
		return salesOrderProcessGemIssueReturnId;
	}

	public void setSalesOrderProcessGemIssueReturnId(
			String salesOrderProcessItemGemIssueId) {
		this.salesOrderProcessGemIssueReturnId = salesOrderProcessItemGemIssueId;
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

	public String getItemStockType() {
		return itemStockType;
	}

	public void setItemStockType(String itemStockType) {
		this.itemStockType = itemStockType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}
}