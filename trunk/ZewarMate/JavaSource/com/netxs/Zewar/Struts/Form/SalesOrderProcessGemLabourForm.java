package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class SalesOrderProcessGemLabourForm extends ValidatorActionForm {

	private char hasFormInitialized;
	
	private boolean insertable;

	private String salesOrderProcessGemLabourId;

	private String salesOrderProcessId;

	private String settingRate;

	private String estimatedQuantity;

	private String estimatedTotalLabour;

	private String actualQuantity;

	private String actualTotalLabour;

	public SalesOrderProcessGemLabourForm() {
		this.hasFormInitialized = 'N';
		this.salesOrderProcessGemLabourId = "0";
		this.salesOrderProcessId = "0";
		this.settingRate = "0.0";
		this.estimatedQuantity = "0";
		this.estimatedTotalLabour = "0.0";
		this.actualQuantity = "0";
		this.actualTotalLabour = "0.0";
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getEstimatedQuantity() {
		return estimatedQuantity;
	}

	public void setEstimatedQuantity(String quantity) {
		this.estimatedQuantity = quantity;
	}

	public String getSalesOrderProcessId() {
		return salesOrderProcessId;
	}

	public void setSalesOrderProcessId(String salesOrderProcessId) {
		this.salesOrderProcessId = salesOrderProcessId;
	}

	public String getSalesOrderProcessGemLabourId() {
		return salesOrderProcessGemLabourId;
	}

	public void setSalesOrderProcessGemLabourId(
			String salesOrderProcessItemGemLabourId) {
		this.salesOrderProcessGemLabourId = salesOrderProcessItemGemLabourId;
	}


	public String getSettingRate() {
		return settingRate;
	}

	public void setSettingRate(String settingRate) {
		this.settingRate = settingRate;
	}

	public String getEstimatedTotalLabour() {
		return estimatedTotalLabour;
	}

	public void setEstimatedTotalLabour(String totalLabour) {
		this.estimatedTotalLabour = totalLabour;
	}

	public String getActualQuantity() {
		return actualQuantity;
	}

	public void setActualQuantity(String actualQuantity) {
		this.actualQuantity = actualQuantity;
	}

	public String getActualTotalLabour() {
		return actualTotalLabour;
	}

	public void setActualTotalLabour(String actualTotalLabour) {
		this.actualTotalLabour = actualTotalLabour;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}
}