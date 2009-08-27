package com.netxs.Zewar.Struts.Form;

import java.util.Collection;
import java.util.Vector;
import com.netxs.Zewar.Struts.Form.SalesOrderItemEstimatedMetalForm;
import com.netxs.Zewar.Struts.Form.SalesOrderItemEstimatedGemForm;

public class SalesOrderItemInfoForm {

	private boolean insertable;

	private String salesOrderItemInfoId;
	
	private String cancelSalesOrderItemInfoId;

	private byte orderItemCancel;
	
	private String itemId;

	private String lumpSumLabourCharges;

	private String comments;

	private String bodyMakingRateTypeId;
	
	private String stoneSettingRateTypeId;
	
	private Vector estimatedMetal = new Vector();

	private Vector estimatedGem = new Vector();

	
	public SalesOrderItemInfoForm() {
		this.salesOrderItemInfoId = "0";
		this.itemId = "0";
		this.lumpSumLabourCharges = "0";
		this.comments = "";
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getLumpSumLabourCharges() {
		return lumpSumLabourCharges;
	}

	public void setLumpSumLabourCharges(String labourCharges) {
		this.lumpSumLabourCharges = labourCharges;
	}

	public String getSalesOrderItemInfoId() {
		return salesOrderItemInfoId;
	}

	public void setSalesOrderItemInfoId(String salesOrderCartItemId) {
		this.salesOrderItemInfoId = salesOrderCartItemId;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}

	// Sales Order Item Estimated Metal
	public SalesOrderItemEstimatedMetalForm getEstimatedMetal(int index) {
		if (this.estimatedMetal.size() < index + 1)
			((Vector) this.estimatedMetal).setSize(index + 1);

		if (this.estimatedMetal.get(index) == null) {
			this.estimatedMetal.set(index,
					new SalesOrderItemEstimatedMetalForm());
		}
		return (SalesOrderItemEstimatedMetalForm) this.estimatedMetal
				.get(index);
	}

	public void setEstimatedMetal(SalesOrderItemEstimatedMetalForm obj) {
		this.estimatedMetal.add(obj);
	}

	public void setEstimatedMetal(Vector obj) {
		this.estimatedMetal = obj;
	}

	public Collection getEstimatedMetalList() {
		return this.estimatedMetal;
	}

	// Sales Order Item Estimated Gem
	public SalesOrderItemEstimatedGemForm getEstimatedGem(int index) {
		if (this.estimatedGem.size() < index + 1)
			((Vector) this.estimatedGem).setSize(index + 1);

		if (this.estimatedGem.get(index) == null) {
			this.estimatedGem.set(index, new SalesOrderItemEstimatedGemForm());
		}
		return (SalesOrderItemEstimatedGemForm) this.estimatedGem.get(index);
	}

	public void setEstimatedGem(SalesOrderItemEstimatedGemForm obj) {
		this.estimatedGem.add(obj);
	}

	public void setEstimatedGem(Vector obj) {
		this.estimatedGem = obj;
	}

	public Collection getEstimatedGemList() {
		return this.estimatedGem;
	}

	public String getBodyMakingRateTypeId() {
		return bodyMakingRateTypeId;
	}

	public void setBodyMakingRateTypeId(String bodyMakingRateTypeId) {
		this.bodyMakingRateTypeId = bodyMakingRateTypeId;
	}

	public String getStoneSettingRateTypeId() {
		return stoneSettingRateTypeId;
	}

	public void setStoneSettingRateTypeId(String stoneSettingRateTypeId) {
		this.stoneSettingRateTypeId = stoneSettingRateTypeId;
	}

	public String getCancelSalesOrderItemInfoId() {
		return cancelSalesOrderItemInfoId;
	}

	public void setCancelSalesOrderItemInfoId(String cancelSalesOrderItemId) {
		this.cancelSalesOrderItemInfoId = cancelSalesOrderItemId;
	}

	public byte getOrderItemCancel() {
		return orderItemCancel;
	}

	public void setOrderItemCancel(byte orderItemCancel) {
		this.orderItemCancel = orderItemCancel;
	}

}