package com.netxs.Zewar.Struts.Form;

public class SalesOrderProcessMetalItemUsedForm {

	private boolean insertable;

	private String salesOrderProcessMetalItemUsedId;
	
	private String itemIdMetal;
	
	private String itemIdJewellery;
	
	private String issueItemWeight;

	private String weightUnitId;
	
	private String issueItemNetWeight;
	
	private String returnWasteQuantity;
	
	private String returnItemWastageUnitId;

	private String issueItemWastageRate;
	
	private String issueItemWastageUnitId;
	
	private String inventoryMetalItemEntryIdOut;

	public SalesOrderProcessMetalItemUsedForm() {
		this.salesOrderProcessMetalItemUsedId = "0";
		this.itemIdMetal = "0";
		this.itemIdJewellery = "0";
		this.issueItemWeight = "0.0";
		this.issueItemNetWeight = "0.0";
		this.weightUnitId = "0";
		this.returnWasteQuantity = "0.000";
		this.returnItemWastageUnitId = "0";
		this.issueItemWastageRate = "0.0";
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public String getIssueItemWeight() {
		return issueItemWeight;
	}

	public void setIssueItemWeight(String issueweight) {
		this.issueItemWeight = issueweight;
	}

	public String getItemIdJewellery() {
		return itemIdJewellery;
	}

	public void setItemIdJewellery(String itemIdJewellery) {
		this.itemIdJewellery = itemIdJewellery;
	}

	public String getItemIdMetal() {
		return itemIdMetal;
	}

	public void setItemIdMetal(String itemIdMetal) {
		this.itemIdMetal = itemIdMetal;
	}

	public String getIssueItemWastageRate() {
		return issueItemWastageRate;
	}

	public void setIssueItemWastageRate(String metalItemWastageRate) {
		this.issueItemWastageRate = metalItemWastageRate;
	}

	public String getIssueItemNetWeight() {
		return issueItemNetWeight;
	}

	public void setIssueItemNetWeight(String returnweight) {
		this.issueItemNetWeight = returnweight;
	}

	public String getSalesOrderProcessMetalItemUsedId() {
		return salesOrderProcessMetalItemUsedId;
	}

	public void setSalesOrderProcessMetalItemUsedId(
			String salesOrderProcessItemMetalPartChargeCustomerId) {
		this.salesOrderProcessMetalItemUsedId = salesOrderProcessItemMetalPartChargeCustomerId;
	}

	public String getReturnWasteQuantity() {
		return returnWasteQuantity;
	}

	public void setReturnWasteQuantity(String wastageQuantity) {
		this.returnWasteQuantity = wastageQuantity;
	}

	public String getReturnItemWastageUnitId() {
		return returnItemWastageUnitId;
	}

	public void setReturnItemWastageUnitId(String wastageUnitId) {
		this.returnItemWastageUnitId = wastageUnitId;
	}

	public String getWeightUnitId() {
		return weightUnitId;
	}

	public void setWeightUnitId(String weightUnitId) {
		this.weightUnitId = weightUnitId;
	}

	public String getIssueItemWastageUnitId() {
		return issueItemWastageUnitId;
	}

	public void setIssueItemWastageUnitId(String metalItemWastageUnitId) {
		this.issueItemWastageUnitId = metalItemWastageUnitId;
	}

	public String getInventoryMetalItemEntryIdOut() {
		return inventoryMetalItemEntryIdOut;
	}

	public void setInventoryMetalItemEntryIdOut(String inventoryMetalItemEntryIdOut) {
		this.inventoryMetalItemEntryIdOut = inventoryMetalItemEntryIdOut;
	}

}