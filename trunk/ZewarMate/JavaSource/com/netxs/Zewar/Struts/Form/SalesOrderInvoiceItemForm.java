package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;
import com.netxs.Zewar.Struts.Form.SalesOrderInvoiceGemForm;

public class SalesOrderInvoiceItemForm extends ValidatorActionForm {

	private boolean insertable;
	private String salesInvoiceItemId;
	private String salesOrderItemId;
	private String itemId;
	private String itemName;
	private String inventoryMetalItemEntryIdIn;
	private Vector companyGem = new Vector();
	private Vector companyMetal = new Vector();
	//private Vector companyMetalItem = new Vector();
	private Vector customerGem = new Vector();
	
	
	public SalesOrderInvoiceItemForm() {
	}

	
	public boolean isInsertable() {
		return insertable;
	}
	public void setInsertable(boolean insertable) {
		this.insertable = insertable;
	}
	
	// Company Gem Item
	public SalesOrderInvoiceGemForm getCompanyGem(int index) {
		if (this.companyGem.size() < index + 1)
			((Vector) this.companyGem).setSize(index + 1);
		if (this.companyGem.get(index) == null) {
			this.companyGem.set(index, new SalesOrderInvoiceGemForm());
		}
		return (SalesOrderInvoiceGemForm) this.companyGem.get(index);
	}

	public void setCompanyGem(SalesOrderInvoiceGemForm obj) {
		this.companyGem.add(obj);
	}

	public void setCompanyGem(Vector obj) {
		this.companyGem = obj;
	}

	public Vector getCompanyGemList() {
		return this.companyGem;
	}
	
	// Company Metal
	public SalesOrderInvoiceMetalForm getCompanyMetal(int index) {
		if (this.companyMetal.size() < index + 1)
			((Vector) this.companyMetal).setSize(index + 1);
		if (this.companyMetal.get(index) == null) {
			this.companyMetal.set(index, new SalesOrderInvoiceMetalForm());
		}
		return (SalesOrderInvoiceMetalForm) this.companyMetal.get(index);
	}

	public void setCompanyMetal(SalesOrderInvoiceMetalForm obj) {
		this.companyMetal.add(obj);
	}

	public void setCompanyMetal(Vector obj) {
		this.companyMetal = obj;
	}

	public Vector getCompanyMetalList() {
		return this.companyMetal;
	}

	public String getSalesInvoiceItemId() {
		return salesInvoiceItemId;
	}

	public void setSalesInvoiceItemId(String salesInvoiceItemId) {
		this.salesInvoiceItemId = salesInvoiceItemId;
	}
	
	/* Company Metal Part
	public SalesOrderInvoiceMetalForm getCompanyMetalItem(int index) {
		if (this.companyMetalItem.size() < index + 1)
			((Vector) this.companyMetalItem).setSize(index + 1);
		if (this.companyMetalItem.get(index) == null) {
			this.companyMetalItem.set(index, new SalesOrderInvoiceMetalForm());
		}
		return (SalesOrderInvoiceMetalForm) this.companyMetalItem.get(index);
	}

	public void setCompanyMetalItem(SalesOrderInvoiceMetalForm obj) {
		this.companyMetalItem.add(obj);
	}

	public void setCompanyMetalItem(Vector obj) {
		this.companyMetalItem = obj;
	}

	public Collection getCompanyMetalItem() {
		return this.companyMetalItem;
	}*/
	
	// Customer Gem Item
	public SalesOrderInvoiceGemForm getCustomerGem(int index) {
		if (this.customerGem.size() < index + 1)
			((Vector) this.customerGem).setSize(index + 1);
		if (this.customerGem.get(index) == null) {
			this.customerGem.set(index, new SalesOrderInvoiceGemForm());
		}
		return (SalesOrderInvoiceGemForm) this.customerGem.get(index);
	}

	public void setCustomerGem(SalesOrderInvoiceGemForm obj) {
		this.customerGem.add(obj);
	}

	public void setCustomerGem(Vector obj) {
		this.customerGem = obj;
	}

	public Vector getCustomerGemList() {
		return this.customerGem;
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

	public String getSalesOrderItemId() {
		return salesOrderItemId;
	}

	public void setSalesOrderItemId(String salesOrderItemId) {
		this.salesOrderItemId = salesOrderItemId;
	}


	public String getInventoryMetalItemEntryIdIn() {
		return inventoryMetalItemEntryIdIn;
	}


	public void setInventoryMetalItemEntryIdIn(String inventoryMetalItemEntryIdIn) {
		this.inventoryMetalItemEntryIdIn = inventoryMetalItemEntryIdIn;
	}
	

}