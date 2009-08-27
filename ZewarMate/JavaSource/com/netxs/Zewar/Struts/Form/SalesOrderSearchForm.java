package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class SalesOrderSearchForm extends ValidatorActionForm {
	private char hasFormInitialized;

	private String orderByCustomerId;

	private String orderItemId;

	private String salesOrderId;

	private String salesOrderProcessStatusId;

	private String salesOrderProcessTypeId;

	private String salesOrderStatusId;

	public SalesOrderSearchForm() {
		this.salesOrderId = "0";
		this.orderByCustomerId = "0";
		this.orderItemId = "0";
		this.salesOrderProcessTypeId = "";
		this.salesOrderProcessStatusId = "";
		this.hasFormInitialized = 'N';
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public String getOrderByCustomerId() {
		return orderByCustomerId;
	}

	public String getOrderItemId() {
		return orderItemId;
	}

	public String getSalesOrderId() {
		return salesOrderId;
	}

	public String getSalesOrderProcessStatusId() {
		return salesOrderProcessStatusId;
	}

	public String getSalesOrderProcessTypeId() {
		return salesOrderProcessTypeId;
	}

	public String getSalesOrderStatusId() {
		return salesOrderStatusId;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public void setOrderByCustomerId(String orderByCustomerId) {
		this.orderByCustomerId = orderByCustomerId;
	}

	public void setOrderItemId(String orderItemId) {
		this.orderItemId = orderItemId;
	}

	public void setSalesOrderId(String salesOrderId) {
		this.salesOrderId = salesOrderId;
	}

	public void setSalesOrderProcessId(String processId) {
		this.salesOrderProcessTypeId = processId;
	}

	public void setSalesOrderProcessStatusId(String processStatus) {
		this.salesOrderProcessStatusId = processStatus;
	}

	public void setSalesOrderProcessTypeId(String salesOrderProcessTypeId) {
		this.salesOrderProcessTypeId = salesOrderProcessTypeId;
	}

	public void setSalesOrderStatusId(String salesOrderStatusId) {
		this.salesOrderStatusId = salesOrderStatusId;
	}
}