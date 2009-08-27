package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;

public class ItemGroupForm extends ValidatorActionForm {

	private String itemGroupId;

	private String itemGroupName;

	private String itemGroupDescription;

	private char hasFormInitialized;

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getItemGroupDescription() {
		return itemGroupDescription;
	}

	public void setItemGroupDescription(String itemGroupDescription) {
		this.itemGroupDescription = itemGroupDescription;
	}

	public String getItemGroupId() {
		return itemGroupId;
	}

	public void setItemGroupId(String itemGroupId) {
		this.itemGroupId = itemGroupId;
	}

	public String getItemGroupName() {
		return itemGroupName;
	}

	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}
}