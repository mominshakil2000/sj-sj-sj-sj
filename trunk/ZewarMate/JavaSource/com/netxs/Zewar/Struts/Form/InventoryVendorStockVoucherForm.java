package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;

public class InventoryVendorStockVoucherForm extends ValidatorActionForm {

	private String inventoryVendorStockVoucherId;

	private String entryDate;

	private String inventoryAccountIdVendor; 
	
	private String inventoryAccountIdCompany;

	private String voucherPrefix;
	
	private String voucherPostfix;
	
	private String comments;

	private Vector voucherItemMetal = new Vector();

	private String removeInventoryVendorStockVoucherItemIds;
	
	private String removeInventoryMetalEntryIdsIn;
	
	private String removeInventoryMetalEntryIdsOut;
	
	private char hasFormInitialized;

	
	public InventoryVendorStockVoucherForm(){
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments.replaceAll("\r\n","\\\\r");
	}
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	public char getHasFormInitialized() {
		return hasFormInitialized;
	}
	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getInventoryVendorStockVoucherId() {
		return inventoryVendorStockVoucherId;
	}
	public void setInventoryVendorStockVoucherId(
			String inventoryVendorStockVoucherId) {
		this.inventoryVendorStockVoucherId = inventoryVendorStockVoucherId;
	}
	public String getVoucherPrefix() {
		return voucherPrefix;
	}
	public void setVoucherPrefix(String voucherType) {
		this.voucherPrefix = voucherType;
	}
	
	//Metal voucher items
	public Collection getVoucherItemMetalList() {
		return voucherItemMetal;
	}
	

	public void setVoucherItemMetal(Vector listObj) {
		this.voucherItemMetal = listObj;
	}

	public void setVoucherItemMetal(InventoryVendorStockVoucherItemForm obj) {
		this.voucherItemMetal.add(obj);
	}

	public InventoryVendorStockVoucherItemForm getVoucherItemMetal(int index) {

		if (this.voucherItemMetal.size() < index + 1)
			((Vector) this.voucherItemMetal).setSize(index + 1);

		if (this.voucherItemMetal.get(index) == null) {
			this.voucherItemMetal.set(index, new InventoryVendorStockVoucherItemForm());
		}
		return (InventoryVendorStockVoucherItemForm) this.voucherItemMetal.get(index);
	}
	
	public String getVoucherPostfix() {
		return voucherPostfix;
	}
	public void setVoucherPostfix(String voucherPostfix) {
		this.voucherPostfix = voucherPostfix;
	}
	public String getInventoryAccountIdCompany() {
		return inventoryAccountIdCompany;
	}
	public void setInventoryAccountIdCompany(String inventoryAccountIdCredit) {
		this.inventoryAccountIdCompany = inventoryAccountIdCredit;
	}
	public String getInventoryAccountIdVendor() {
		return inventoryAccountIdVendor;
	}
	public void setInventoryAccountIdVendor(String inventoryAccountIdDebit) {
		this.inventoryAccountIdVendor = inventoryAccountIdDebit;
	}

	public String getRemoveInventoryMetalEntryIdsIn() {
		return removeInventoryMetalEntryIdsIn;
	}

	public void setRemoveInventoryMetalEntryIdsIn(
			String removeInventoryMetalEntryIdsIn) {
		this.removeInventoryMetalEntryIdsIn = removeInventoryMetalEntryIdsIn;
	}

	public String getRemoveInventoryMetalEntryIdsOut() {
		return removeInventoryMetalEntryIdsOut;
	}

	public void setRemoveInventoryMetalEntryIdsOut(
			String removeInventoryMetalEntryIdsOut) {
		this.removeInventoryMetalEntryIdsOut = removeInventoryMetalEntryIdsOut;
	}

	public String getRemoveInventoryVendorStockVoucherItemIds() {
		return removeInventoryVendorStockVoucherItemIds;
	}

	public void setRemoveInventoryVendorStockVoucherItemIds(
			String removeInventoryVendorStockVoucherItemIds) {
		this.removeInventoryVendorStockVoucherItemIds = removeInventoryVendorStockVoucherItemIds;
	}
}