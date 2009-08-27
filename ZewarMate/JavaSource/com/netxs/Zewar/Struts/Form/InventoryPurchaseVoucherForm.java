package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;

public class InventoryPurchaseVoucherForm extends ValidatorActionForm {

	private String inventoryPurchaseVoucherId;

	private String itemGroupId;
	
	private String voucherPrefix;
	
	private String voucherPostfix;
	
	private String inventoryAccountIdCompany;

	private String entryDate;

	private String comments;
	
	private String inventoryItemEntryIdIn;
	
	private String jewelleryItemId;
	
	private String jewelleryValue;
	
	private String jewelleryWeight;
	
	private String ledgerEntryId;

	private String inventoryPurchaseVoucherJewelleryMetalId;
	
	private String metalItemId;
	
	private String metalWeight;
	
	private String metalWeightUnitId;
	
	private String metalWeightUnit;
	
	private String metalWastageRate;
	
	private String metalWastageUnitId;
	
	private String metalNetWeight;
	
	private String metalRate;
	
	private String metalValue;
	
	private String removeInventoryPurchaseVoucherItemIds;
	
	private String removeInventoryPurchaseVoucherJewelleryGemIds;

	private String removeInventoryItemEntryIdsIn;
	
	private String removeLedgerEntryIds;

	private String removeCashVoucherIds;
	
	private String removeCashVoucherEntryIds;
	
	private Vector voucherItem = new Vector();

	private Vector jewelleryGem = new Vector();
	
	private char hasFormInitialized;

	private String cashVoucherId;
	
	private String cashVoucherEntryId;

	public InventoryPurchaseVoucherForm(){
		this.hasFormInitialized='N';	 
	}
	

	//voucher items
	public Collection getVoucherItemList() {
		return voucherItem;
	}

	public void setVoucherItem(Vector listObj) {
		this.voucherItem = listObj;
	}

	public void setVoucherItem(InventoryPurchaseVoucherItemForm obj) {
		this.voucherItem.add(obj);
	}

	public InventoryPurchaseVoucherItemForm getVoucherItem(int index) {

		if (this.voucherItem.size() < index + 1)
			((Vector) this.voucherItem).setSize(index + 1);

		if (this.voucherItem.get(index) == null) {
			this.voucherItem.set(index, new InventoryPurchaseVoucherItemForm());
		}
		return (InventoryPurchaseVoucherItemForm) this.voucherItem.get(index);
	}


	//	Jewellery Gems
	public Vector getVoucherJewelleryGemList() {
		return jewelleryGem;
	}

	public void setVoucherJewelleryGem(Vector listObj) {
		this.jewelleryGem = listObj;
	}

	public void setVoucherJewelleryGem(InventoryPurchaseVoucherJewelleryGemForm obj) {
		this.jewelleryGem.add(obj);
	}

	public InventoryPurchaseVoucherJewelleryGemForm getVoucherJewelleryGem(int index) {

		if (this.jewelleryGem.size() < index + 1)
			((Vector) this.jewelleryGem).setSize(index + 1);

		if (this.jewelleryGem.get(index) == null) {
			this.jewelleryGem.set(index, new InventoryPurchaseVoucherJewelleryGemForm());
		}
		return (InventoryPurchaseVoucherJewelleryGemForm) this.jewelleryGem.get(index);
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


	public String getInventoryAccountIdCompany() {
		return inventoryAccountIdCompany;
	}


	public void setInventoryAccountIdCompany(String inventoryAccountIdCompany) {
		this.inventoryAccountIdCompany = inventoryAccountIdCompany;
	}


	public String getInventoryPurchaseVoucherId() {
		return inventoryPurchaseVoucherId;
	}


	public void setInventoryPurchaseVoucherId(String inventoryPurchaseVoucherId) {
		this.inventoryPurchaseVoucherId = inventoryPurchaseVoucherId;
	}


	public String getRemoveInventoryItemEntryIdsIn() {
		return removeInventoryItemEntryIdsIn;
	}


	public void setRemoveInventoryItemEntryIdsIn(
			String removeInventoryItemEntryIdsIn) {
		this.removeInventoryItemEntryIdsIn = removeInventoryItemEntryIdsIn;
	}


	public String getRemoveInventoryPurchaseVoucherItemIds() {
		return removeInventoryPurchaseVoucherItemIds;
	}


	public void setRemoveInventoryPurchaseVoucherItemIds(
			String removeInventoryPurchaseVoucherItemIds) {
		this.removeInventoryPurchaseVoucherItemIds = removeInventoryPurchaseVoucherItemIds;
	}


	public String getVoucherPostfix() {
		return voucherPostfix;
	}


	public void setVoucherPostfix(String voucherPostfix) {
		this.voucherPostfix = voucherPostfix;
	}


	public String getVoucherPrefix() {
		return voucherPrefix;
	}


	public void setVoucherPrefix(String voucherPrefix) {
		this.voucherPrefix = voucherPrefix;
	}


	public String getItemGroupId() {
		return itemGroupId;
	}


	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}


	public String getJewelleryItemId() {
		return jewelleryItemId;
	}


	public void setJewelleryItemId(String jewelleryItemId) {
		this.jewelleryItemId = jewelleryItemId;
	}


	public String getMetalItemId() {
		return metalItemId;
	}


	public void setMetalItemId(String metalItemId) {
		this.metalItemId = metalItemId;
	}


	public String getMetalNetWeight() {
		return metalNetWeight;
	}


	public void setMetalNetWeight(String metalNetWeight) {
		this.metalNetWeight = metalNetWeight;
	}


	public String getMetalRate() {
		return metalRate;
	}


	public void setMetalRate(String metalRate) {
		this.metalRate = metalRate;
	}


	public String getMetalValue() {
		return metalValue;
	}


	public void setMetalValue(String metalValue) {
		this.metalValue = metalValue;
	}


	public String getMetalWastageRate() {
		return metalWastageRate;
	}


	public void setMetalWastageRate(String metalWastageRate) {
		this.metalWastageRate = metalWastageRate;
	}


	public String getMetalWastageUnitId() {
		return metalWastageUnitId;
	}


	public void setMetalWastageUnitId(String metalWastageUnitId) {
		this.metalWastageUnitId = metalWastageUnitId;
	}


	public String getMetalWeight() {
		return metalWeight;
	}


	public void setMetalWeight(String metalWeight) {
		this.metalWeight = metalWeight;
	}


	public String getMetalWeightUnitId() {
		return metalWeightUnitId;
	}


	public void setMetalWeightUnitId(String metalWeightUnitId) {
		this.metalWeightUnitId = metalWeightUnitId;
	}


	public String getMetalWeightUnit() {
		return metalWeightUnit;
	}


	public void setMetalWeightUnit(String metalWeightUnit) {
		this.metalWeightUnit = metalWeightUnit;
	}


	public String getJewelleryValue() {
		return jewelleryValue;
	}


	public void setJewelleryValue(String jewelleryValue) {
		this.jewelleryValue = jewelleryValue;
	}


	public String getLedgerEntryId() {
		return ledgerEntryId;
	}


	public void setLedgerEntryId(String ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}


	public String getJewelleryWeight() {
		return jewelleryWeight;
	}


	public void setJewelleryWeight(String jewelleryWeight) {
		this.jewelleryWeight = jewelleryWeight;
	}


	public String getInventoryItemEntryIdIn() {
		return inventoryItemEntryIdIn;
	}


	public void setInventoryItemEntryIdIn(String inventoryItemEntryIdIn) {
		this.inventoryItemEntryIdIn = inventoryItemEntryIdIn;
	}


	public String getRemoveInventoryPurchaseVoucherJewelleryGemIds() {
		return removeInventoryPurchaseVoucherJewelleryGemIds;
	}


	public void setRemoveInventoryPurchaseVoucherJewelleryGemIds(
			String removeInventoryPurchaseVoucherJewelleryGemIds) {
		this.removeInventoryPurchaseVoucherJewelleryGemIds = removeInventoryPurchaseVoucherJewelleryGemIds;
	}


	public String getInventoryPurchaseVoucherJewelleryMetalId() {
		return inventoryPurchaseVoucherJewelleryMetalId;
	}


	public void setInventoryPurchaseVoucherJewelleryMetalId(
			String inventoryPurchaseVoucherJewelleryMetalId) {
		this.inventoryPurchaseVoucherJewelleryMetalId = inventoryPurchaseVoucherJewelleryMetalId;
	}


	public String getCashVoucherEntryId() {
		return cashVoucherEntryId;
	}


	public void setCashVoucherEntryId(String cashVoucherEntryId) {
		this.cashVoucherEntryId = cashVoucherEntryId;
	}


	public String getCashVoucherId() {
		return cashVoucherId;
	}


	public void setCashVoucherId(String cashVoucherId) {
		this.cashVoucherId = cashVoucherId;
	}


	public String getRemoveCashVoucherEntryIds() {
		return removeCashVoucherEntryIds;
	}


	public void setRemoveCashVoucherEntryIds(String removeCashVoucherEntryIds) {
		this.removeCashVoucherEntryIds = removeCashVoucherEntryIds;
	}


	public String getRemoveCashVoucherIds() {
		return removeCashVoucherIds;
	}


	public void setRemoveCashVoucherIds(String removeCashVoucherIds) {
		this.removeCashVoucherIds = removeCashVoucherIds;
	}


	public String getRemoveLedgerEntryIds() {
		return removeLedgerEntryIds;
	}


	public void setRemoveLedgerEntryIds(String removeLedgerEntryIds) {
		this.removeLedgerEntryIds = removeLedgerEntryIds;
	}
	
}