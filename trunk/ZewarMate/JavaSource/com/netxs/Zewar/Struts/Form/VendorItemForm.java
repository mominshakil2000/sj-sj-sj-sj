package com.netxs.Zewar.Struts.Form;

import org.apache.struts.action.*;

public class VendorItemForm extends ActionForm {

	private boolean insertable;

	private String agreedWastage;
	
	private String agreedWastageUnitId;

	private String itemId;

	private String itemName;

	public VendorItemForm() {
		super();
		this.itemId = new String();
		this.agreedWastage = new String();
		this.itemName = new String();
	}

	public String getAgreedWastage() {
		return agreedWastage;
	}

	public void setAgreedWastage(String wastageAgreed) {
		this.agreedWastage = wastageAgreed;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public boolean isInsertable() {
		return insertable;
	}

	public void setInsertable(boolean inserStatus) {
		this.insertable = inserStatus;
	}

	public String getAgreedWastageUnitId() {
		return agreedWastageUnitId;
	}

	public void setAgreedWastageUnitId(String agreedWastageUnitId) {
		this.agreedWastageUnitId = agreedWastageUnitId;
	}
}