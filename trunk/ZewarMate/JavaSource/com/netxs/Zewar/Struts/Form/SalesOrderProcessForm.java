package com.netxs.Zewar.Struts.Form;

import java.util.Collection;
import java.util.Vector;

import org.apache.struts.validator.ValidatorActionForm;

public class SalesOrderProcessForm extends ValidatorActionForm {

	private boolean insertable;
	
	private char insertableSubSectionRow;
	private String subSectionRowId;
	
	private String salesOrderProcessId;

	private String salesOrderId;
	
	private String salesOrderItemId;
	
	private String jewelleryItemId;
	
	private String vendorLedgerAccountId;

	private String salesOrderProcessTypeId;

	private String processStartDate;

	private String processEndDate;

	private String salesOrderProcessStatusId;

	private char hasFormInitialized;
	
	private String lumsumLabour;

	private String wastageRate;

	private String wastageRateUnitId;

	private String bodyMetalItemId;
	
	private String bodyMetalWeightUnitId;
	
	private String issueBodyWeight;

	private String returnBodyWeight;

	private String returnBodyPieces;
	
	private String comments;

	private String vendorDeliveryDate;
	
	private String actualDeliveryDate;
	
	private String labourLedgerEntryId;

	private Vector vendorGemIssue = new Vector();
	
	private Vector vendorGemReturn= new Vector();
	
	private Vector gemLabour = new Vector();
	
	private Vector metalUsed = new Vector();
	
	private Vector metalItemUsed = new Vector();
	
	private String deleteVendorGemIssueIds ;
	private String deleteVendorGemReturnIds ;
	private String deleteGemLabourIds ;
	private String deleteMetalUsedIds ;
	private String deleteMetalItemUsedIds ;

	public SalesOrderProcessForm() {
		this.salesOrderProcessId = "0";
		this.salesOrderId = "0";

		this.salesOrderProcessTypeId = "0";
		//		this.processStartDate = "";
		//		this.processEndDate = "";
		//		this.salesOrderProcessStatusId = ' ';
		this.salesOrderProcessId = "0";
		this.hasFormInitialized = 'N';
		this.deleteVendorGemIssueIds="0" ;
		this.deleteVendorGemReturnIds="0";
		this.deleteGemLabourIds="0";
		this.deleteMetalUsedIds="0";
		this.deleteMetalItemUsedIds="0";
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getProcessEndDate() {
		return processEndDate;
	}

	public void setProcessEndDate(String processEndDate) {
		this.processEndDate = processEndDate;
	}

	public String getProcessStartDate() {
		return processStartDate;
	}

	public void setProcessStartDate(String processStartDate) {
		this.processStartDate = processStartDate;
	}

	public String getSalesOrderProcessStatusId() {
		return salesOrderProcessStatusId;
	}

	public void setSalesOrderProcessStatusId(String processStatus) {
		this.salesOrderProcessStatusId = processStatus;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public String getSalesOrderProcessId() {
		return salesOrderProcessId;
	}

	public void setSalesOrderProcessId(String salesOrderProcessId) {
		this.salesOrderProcessId = salesOrderProcessId;
	}

	

	public String getSalesOrderProcessTypeId() {
		return salesOrderProcessTypeId;
	}

	public void setSalesOrderProcessTypeId(String processTypeId) {
		this.salesOrderProcessTypeId = processTypeId;
	}

	public String getSalesOrderItemId() {
		return salesOrderItemId;
	}

	public void setSalesOrderItemId(String salesOrderItemId) {
		this.salesOrderItemId = salesOrderItemId;
	}

	public String getVendorLedgerAccountId() {
		return vendorLedgerAccountId;
	}

	public void setVendorLedgerAccountId(String vendorLedgerAccountId) {
		this.vendorLedgerAccountId = vendorLedgerAccountId;
	}

	public String getActualDeliveryDate() {
		return actualDeliveryDate;
	}

	public void setActualDeliveryDate(String actualDeliveryDate) {
		this.actualDeliveryDate = actualDeliveryDate;
	}

	public String getBodyMetalItemId() {
		return bodyMetalItemId;
	}

	public void setBodyMetalItemId(String bodyMetalItemId) {
		this.bodyMetalItemId = bodyMetalItemId;
	}

	public String getBodyMetalWeightUnitId() {
		return bodyMetalWeightUnitId;
	}

	public void setBodyMetalWeightUnitId(String bodyMetalWeightUnitId) {
		this.bodyMetalWeightUnitId = bodyMetalWeightUnitId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIssueBodyWeight() {
		return issueBodyWeight;
	}

	public void setIssueBodyWeight(String issueBodyWeight) {
		this.issueBodyWeight = issueBodyWeight;
	}

	public String getLabourLedgerEntryId() {
		return labourLedgerEntryId;
	}

	public void setLabourLedgerEntryId(String labourLedgerEntryId) {
		this.labourLedgerEntryId = labourLedgerEntryId;
	}

	public String getLumsumLabour() {
		return lumsumLabour;
	}

	public void setLumsumLabour(String lumsumLabour) {
		this.lumsumLabour = lumsumLabour;
	}

	public String getReturnBodyPieces() {
		return returnBodyPieces;
	}

	public void setReturnBodyPieces(String returnBodyPieces) {
		this.returnBodyPieces = returnBodyPieces;
	}

	public String getReturnBodyWeight() {
		return returnBodyWeight;
	}

	public void setReturnBodyWeight(String returnBodyWeight) {
		this.returnBodyWeight = returnBodyWeight;
	}

	public String getVendorDeliveryDate() {
		return vendorDeliveryDate;
	}

	public void setVendorDeliveryDate(String vendorDeliveryDate) {
		this.vendorDeliveryDate = vendorDeliveryDate;
	}

	public String getWastageRate() {
		return wastageRate;
	}

	public void setWastageRate(String wastageRate) {
		this.wastageRate = wastageRate;
	}

	public String getWastageRateUnitId() {
		return wastageRateUnitId;
	}

	public void setWastageRateUnitId(String wastageRateUnitId) {
		this.wastageRateUnitId = wastageRateUnitId;
	}
	
	
	// Vendor Gem Issue
	public SalesOrderProcessGemIssueReturnForm getVendorGemIssue(int index) {
		if (this.vendorGemIssue.size() < index + 1)
			((Vector) this.vendorGemIssue).setSize(index + 1);

		if (this.vendorGemIssue.get(index) == null) {
			this.vendorGemIssue.set(index,
					new SalesOrderProcessGemIssueReturnForm());
		}
		return (SalesOrderProcessGemIssueReturnForm) this.vendorGemIssue
				.get(index);
	}

	public void setVendorGemIssue(SalesOrderProcessGemIssueReturnForm obj) {
		this.vendorGemIssue.add(obj);
	}

	public void setVendorGemIssue(Vector obj) {
		this.vendorGemIssue = obj;
	}

	public Vector getVendorGemIssueList() {
		return this.vendorGemIssue;
	}

	// Vendor Gem Return
	public SalesOrderProcessGemIssueReturnForm getVendorGemReturn(int index) {
		if (this.vendorGemReturn.size() < index + 1)
			((Vector) this.vendorGemReturn).setSize(index + 1);

		if (this.vendorGemReturn.get(index) == null) {
			this.vendorGemReturn.set(index,
					new SalesOrderProcessGemIssueReturnForm());
		}
		return (SalesOrderProcessGemIssueReturnForm) this.vendorGemReturn
				.get(index);
	}

	public void setVendorGemReturn(SalesOrderProcessGemIssueReturnForm obj) {
		this.vendorGemReturn.add(obj);
	}

	public void setVendorGemReturn(Vector obj) {
		this.vendorGemReturn = obj;
	}

	public Vector getVendorGemReturnList() {
		return this.vendorGemReturn;
	}


	// Item's Gem Labour
	public SalesOrderProcessGemLabourForm getGemLabour(int index) {
		if (this.gemLabour.size() < index + 1)
			((Vector) this.gemLabour).setSize(index + 1);

		if (this.gemLabour.get(index) == null) {
			this.gemLabour.set(index,
					new SalesOrderProcessGemLabourForm());
		}
		return (SalesOrderProcessGemLabourForm) this.gemLabour
				.get(index);
	}

	public void setGemLabour(SalesOrderProcessGemLabourForm obj) {
		this.gemLabour.add(obj);
	}

	public void setGemLabour(Vector obj) {
		this.gemLabour = obj;
	}

	public Vector getGemLabourList() {
		return this.gemLabour;
	}

	// Item's Metal Used
	public SalesOrderProcessMetalUsedForm getMetalUsed(int index) {
		if (this.metalUsed.size() < index + 1)
			((Vector) this.metalUsed).setSize(index + 1);

		if (this.metalUsed.get(index) == null) {
			this.metalUsed.set(index,
					new SalesOrderProcessMetalUsedForm());
		}
		return (SalesOrderProcessMetalUsedForm) this.metalUsed
				.get(index);
	}

	public void setMetalUsed(SalesOrderProcessMetalUsedForm obj) {
		this.metalUsed.add(obj);
	}

	public void setMetalUsed(Vector obj) {
		this.metalUsed = obj;
	}

	public Vector getMetalUsedList() {
		return this.metalUsed;
	}

	// Metal Item Used
	public SalesOrderProcessMetalItemUsedForm getMetalItemUsed(int index) {
		if (this.metalItemUsed.size() < index + 1)
			((Vector) this.metalItemUsed).setSize(index + 1);

		if (this.metalItemUsed.get(index) == null) {
			this.metalItemUsed.set(index,
					new SalesOrderProcessMetalItemUsedForm());
		}
		return (SalesOrderProcessMetalItemUsedForm) this.metalItemUsed.get(index);
	}

	public void setMetalItemUsed(SalesOrderProcessMetalItemUsedForm obj) {
		this.metalItemUsed.add(obj);
	}

	public void setMetalItemUsed(Vector obj) {
		this.metalItemUsed = obj;
	}

	public Vector getMetalItemUsedList() {
		return this.metalItemUsed;
	}

	public String getJewelleryItemId() {
		return jewelleryItemId;
	}

	public void setJewelleryItemId(String jewelleryItemId) {
		this.jewelleryItemId = jewelleryItemId;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	public char getInsertableSubSectionRow() {
		return insertableSubSectionRow;
	}

	public void setInsertableSubSectionRow(char insertableSubSectionRow) {
		this.insertableSubSectionRow = insertableSubSectionRow;
	}

	public String getSubSectionRowId() {
		return subSectionRowId;
	}

	public void setSubSectionRowId(String subSectionRowId) {
		this.subSectionRowId = subSectionRowId;
	}

	public String getDeleteGemLabourIds() {
		return deleteGemLabourIds;
	}

	public void setDeleteGemLabourIds(String deleteGemLabourIds) {
		this.deleteGemLabourIds = deleteGemLabourIds;
	}

	public String getDeleteMetalItemUsedIds() {
		return deleteMetalItemUsedIds;
	}

	public void setDeleteMetalItemUsedIds(String deleteMetalItemUsedIds) {
		this.deleteMetalItemUsedIds = deleteMetalItemUsedIds;
	}

	public String getDeleteMetalUsedIds() {
		return deleteMetalUsedIds;
	}

	public void setDeleteMetalUsedIds(String deleteMetalUsedIds) {
		this.deleteMetalUsedIds = deleteMetalUsedIds;
	}

	public String getDeleteVendorGemIssueIds() {
		return deleteVendorGemIssueIds;
	}

	public void setDeleteVendorGemIssueIds(String deleteVendorGemIssueIds) {
		this.deleteVendorGemIssueIds = deleteVendorGemIssueIds;
	}

	public String getDeleteVendorGemReturnIds() {
		return deleteVendorGemReturnIds;
	}

	public void setDeleteVendorGemReturnIds(String deleteVendorGemReturnIds) {
		this.deleteVendorGemReturnIds = deleteVendorGemReturnIds;
	}

	
	
}