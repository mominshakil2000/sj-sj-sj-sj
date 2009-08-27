package com.netxs.Zewar;

import javax.servlet.*;
import com.netxs.Zewar.Lookup.*;

public final class ContextListener implements ServletContextListener {

	private ServletContext context = null;

	public void contextInitialized(ServletContextEvent event) {
		context = event.getServletContext();
		try {
			context.setAttribute("lookupCountry", LookupCountry.getList());
			context.setAttribute("lookupCustomer", LookupCustomer.getList());
			context.setAttribute("lookupInventoryAccount", LookupInventoryAccount.getList() );
			context.setAttribute("lookupItemGem", LookupItem.getList(2));
			context.setAttribute("lookupItemMetal", LookupItem.getList(1));
			context.setAttribute("lookupItemJewellery", LookupItem.getList(3));
			context.setAttribute("lookupItemGroup", LookupItemGroup.getList());
			context.setAttribute("lookupItemKaratConversion", LookupItemKaratConversion.getList());
			context.setAttribute("lookupItemStockType", LookupItemStockType.getList());
			context.setAttribute("lookupItemValueCalculateBy", LookupItemValueCalculateBy.getList() );
			context.setAttribute("lookupLedgerAccountControl", LookupLedgerAccount.getList(1));
			context.setAttribute("lookupLedgerAccountDetail", LookupLedgerAccount.getList());
			context.setAttribute("lookupLedgerAccountCashDetail", LookupLedgerAccount.getDetailAccountsIn("'CA'"));
			context.setAttribute("lookupLedgerAccountType", LookupLedgerAccountType.getList() );
			context.setAttribute("lookupNameTitle", LookupNameTitle.getList());
			context.setAttribute("lookupSalesOrderStatus", LookupSalesOrderStatus.getList());
			context.setAttribute("lookupUserRoles", LookupUserRole.getList() );
			context.setAttribute("lookupVendor", LookupVendor.getList() );
			context.setAttribute("lookupVendorType", LookupVendorType.getList() );
			context.setAttribute("lookupSalesOrderProcessStatus", LookupSalesOrderProcessStatus.getList() );
			context.setAttribute("lookupWastageUnit", LookupWastageUnit.getList() );
			context.setAttribute("lookupWeightUnit", LookupWeightUnit.getList() );
			context.setAttribute("lookupAdvanceItemInventory", LookupAdvanceItemInventory.getList() );
			

			
		}	catch (Exception ex) {
				System.out.println("Fail to initialized lookup items: " + ex.getMessage());
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		context = event.getServletContext();

		context.removeAttribute("bookDB");

	}
}