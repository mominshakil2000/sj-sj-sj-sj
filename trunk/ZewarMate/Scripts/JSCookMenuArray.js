var myMenu =
[
	[null,'Administration','#','_self','',
		[null,'Customer','#','_self','',
			[null,'New Customer','../Customer/CreateProfileInit.do','_self',''],
			[null,'List Customer','../Customer/ListCustomer.do','_self','']
		],
		[null,'Items','#','_self','',
			[null,'New Item','../Item/CreateItemInit.do','_self',''],
			[null,'List Item','../Item/ListItem.do','_self',''],
		  //[null,'New Item Group','../Item/CreateItemGroupInit.do','_self',''],
			[null,'List Item Group','../Item/ListItemGroup.do','_self','']
		],
		[null,'Users','#','_self','',
			[null,'New User','../User/CreateProfileInit.do','_self',''],
			[null,'List User','../User/ListUser.do','_self','']
		],
		[null,'Vendors','#','_self','',
			[null,'New Vendor','../Vendor/CreateVendorProfileInit.do','_self',''],
			[null,'List Vendor','../Vendor/ListVendor.do','_self',''],
			[null,'List Vendor Group','../Vendor/ListVendorGroup.do','_self','']
		]
		
	],
	[null,'Sales','#','_self','',
		[null,'New Sales Order','../SalesOrder/CreateOrderCart.do','_self',''],
		[null,'List Sales Order','../SalesOrder/ListOrder.do','_self','']

	],
	[null,'Accounts','#','_self','',
		[null,'Ledger Account','#','_self','',
			[null,'New Ledger','../Ledger/CreateAccountInit.do','_self',''],
			[null,'Chart of Account','../Ledger/ListAccount.do','_self','']
		],
		[null,'Cash Book','#','_self','',
			[null,'New Cash Voucher','../CashBook/CreateVoucher.do','_self',''],
			[null,'Update Cash Voucher','../CashBook/UpdateVoucher.do','_self','']
		],
		[null,'General Journal','#','_self','',
			[null,'New General Entry','../GeneralJournal/CreateVoucher.do','_self',''],
			[null,'Update General Entry','../GeneralJournal/UpdateVoucher.do','_self','']
		],
		[null,'Zakaat Calculator','../Tools/ZakaatCalculator.do','_self','']		
	],
	[null,'Inventory','#','_self','',
		[null,'Purchase','#','_self','',
			[null,'New Metal Voucher','../Inventory/CreatePurchaseMetalVoucher.do','_self',''],
			[null,'Update Metal Voucher','../Inventory/UpdatePurchaseMetalVoucher.do','_self',''],
			[null,'New Gem Voucher','../Inventory/CreatePurchaseGemVoucher.do','_self',''],
			[null,'Update Gem Voucher','../Inventory/UpdatePurchaseGemVoucher.do','_self',''],
			[null,'New Jewellery Voucher','../Inventory/CreatePurchaseJewelleryVoucher.do','_self',''],
			[null,'Update Jewellery Voucher','../Inventory/UpdatePurchaseJewelleryVoucher.do','_self','']
		],
		[null,'Sales','#','_self','',
			[null,'New Metal Voucher','../Inventory/CreateSalesMetalVoucher.do','_self',''],
			[null,'Update Metal Voucher','../Inventory/UpdateSalesMetalVoucher.do','_self',''],
			[null,'New Gem Voucher','../Inventory/CreateSalesGemVoucher.do','_self',''],
			[null,'Update Gem Voucher','../Inventory/UpdateSalesGemVoucher.do','_self',''],
			[null,'New Jewellery Voucher','../Inventory/CreateSalesJewelleryVoucher.do','_self',''],
			[null,'Update Jewellery Voucher','../Inventory/UpdateSalesJewelleryVoucher.do','_self','']
		],
		[null,'Vendor Stock','#','_self','',
			[null,'New Metal Issue/Return Voucher','../Inventory/CreateVendorStockVoucher.do','_self',''],
			[null,'Update Metal Issue/Return Voucher','../Inventory/UpdateVendorStockVoucher.do','_self','']
		]
	
	],
	[null,'Reports','#','_self','',
		[null,'Sales','#','_self','',
			[null,'Credit Sales','../SalesOrder/CreditSalesReport.do','_self',''],
			[null,'Sales Invoice','../SalesOrder/SalesInvoiceReport.do','_self',''],
			[null,'Item Issued For Setting','../SalesOrder/OrderItemIssuedSettingReport.do','_self',''],
			[null,'Item Received After Setting','../SalesOrder/OrderItemReceivedSettingReport.do','_self',''],
		],		
		[null,'Accounts','#','_self','',
			[null,'Ledger','../Ledger/ViewCriteria.do','_self',''],
			[null,'Cash Book','../CashBook/ViewCriteria.do','_self',''],
			[null,'General Journal','../GeneralJournal/ViewCriteria.do','_self',''],
			[null,'Trial Balance','../Ledger/TBViewCriteria.do','_self','']
		],		
		[null,'Inventory','#','_self','',
//			[null,'Purchase Vouchers','../Inventory/PurchaseReport.do','_self',''],
			[null,'Stock Issue To Karigar','../Inventory/VendorStockIssueReport.do','_self',''],
			[null,'Stock Return From Karigar','../Inventory/VendorStockReturnReport.do','_self',''],
			[null,'Stock Received From Karigar','../Inventory/VendorStockReceivedReport.do','_self',''],
			[null,'Karigar Statement','../Inventory/VendorStatementReport.do','_self',''],
			[null,'Stock Ready Item','../Inventory/StockReadyItemReport.do','_self',''],
			[null,'Stock Position','../Inventory/StockPositionReport.do','_self','']
		]
	],

//	[null,'Tools','#','_self','',
//		[null,'Update Database','../Tools/SynDatabase.do','_self',''],
//	],
	[null,'Log Out','../System/Logout.do','_self','']
	
];
