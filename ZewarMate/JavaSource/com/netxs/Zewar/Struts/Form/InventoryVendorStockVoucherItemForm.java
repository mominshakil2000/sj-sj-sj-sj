/*
 * Created on Sep 27, 2004
 */
package com.netxs.Zewar.Struts.Form;

public class InventoryVendorStockVoucherItemForm {

	private boolean insertable;

	private String inventoryVendorStockVoucherItemId;

	private String issueItemId;

	private String actualItemId;
	
	private String issueWeight;

	private String alloyRate;

	private String alloyWastageUnitId;

	private String actualWeight;
	
	private String comments;
	
	private String inventoryMetalEntryIdIn;
	
	private String inventoryMetalEntryIdOut;

	public InventoryVendorStockVoucherItemForm() {

	}


	public String getActualItemId() {
		return actualItemId;
	}
	public void setActualItemId(String actualItemId) {
		this.actualItemId = actualItemId;
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
	public String getInventoryVendorStockVoucherItemId() {
		return inventoryVendorStockVoucherItemId;
	}
	public void setInventoryVendorStockVoucherItemId(
			String inventoryVendorStockVoucherItemId) {
		this.inventoryVendorStockVoucherItemId = inventoryVendorStockVoucherItemId;
	}
	public String getIssueItemId() {
		return issueItemId;
	}
	public void setIssueItemId(String issueItemId) {
		this.issueItemId = issueItemId;
	}
	public String getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(String netWeight) {
		this.actualWeight = netWeight;
	}
	public String getAlloyRate() {
		return alloyRate;
	}
	public void setAlloyRate(String wastageRate) {
		this.alloyRate = wastageRate;
	}
	public String getAlloyWastageUnitId() {
		return alloyWastageUnitId;
	}
	public void setAlloyWastageUnitId(String wastageUnitId) {
		this.alloyWastageUnitId = wastageUnitId;
	}
	public String getIssueWeight() {
		return issueWeight;
	}
	public void setIssueWeight(String weight) {
		this.issueWeight = weight;
	}


	public String getInventoryMetalEntryIdIn() {
		return inventoryMetalEntryIdIn;
	}


	public void setInventoryMetalEntryIdIn(String inventoryMetalEntryIdIn) {
		this.inventoryMetalEntryIdIn = inventoryMetalEntryIdIn;
	}


	public String getInventoryMetalEntryIdOut() {
		return inventoryMetalEntryIdOut;
	}


	public void setInventoryMetalEntryIdOut(String inventoryMetalEntryIdOut) {
		this.inventoryMetalEntryIdOut = inventoryMetalEntryIdOut;
	}
}