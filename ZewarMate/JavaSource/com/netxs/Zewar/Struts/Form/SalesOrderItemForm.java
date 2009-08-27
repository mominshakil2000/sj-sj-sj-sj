package com.netxs.Zewar.Struts.Form;

import java.util.Collection;
import java.util.Vector;
import com.netxs.Zewar.Struts.Form.SalesOrderItemEstimatedMetalForm;
import com.netxs.Zewar.Struts.Form.SalesOrderItemEstimatedGemForm;

public class SalesOrderItemForm {

	private boolean insertable;

	private String salesOrderItemId;
	
	private String jewelleryItemId;

	private Vector orderItemInfo = new Vector();

	public SalesOrderItemForm() {
		this.salesOrderItemId = "0";
		this.jewelleryItemId = "0";
	}

	public String getJewelleryItemId() {
		return jewelleryItemId;
	}

	public void setJewelleryItemId(String itemId) {
		this.jewelleryItemId = itemId;
	}

	public String getSalesOrderItemId() {
		return salesOrderItemId;
	}

	public void setSalesOrderItemId(String salesOrderCartItemId) {
		this.salesOrderItemId = salesOrderCartItemId;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}


	// Sales Order Item Info
	public SalesOrderItemInfoForm getOrderItemInfo(int index) {
		if (this.orderItemInfo.size() < index + 1)
			((Vector) this.orderItemInfo).setSize(index + 1);

		if (this.orderItemInfo.get(index) == null) {
			this.orderItemInfo.set(index, new SalesOrderItemInfoForm());
		}
		return (SalesOrderItemInfoForm) this.orderItemInfo.get(index);
	}

	public void setOrderItemInfo(SalesOrderItemInfoForm obj) {
		this.orderItemInfo.add(obj);
	}

	public void setOrderItemInfo(Vector obj) {
		this.orderItemInfo = obj;
	}

	public Collection getOrderItemInfoList() {
		return this.orderItemInfo;
	}
}