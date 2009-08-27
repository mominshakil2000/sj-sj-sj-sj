package com.netxs.Zewar.Lookup;

import java.util.*;

import com.netxs.Zewar.Lookup.LookupDO;

public class LookupAdvanceItemInventory {
	private static ArrayList list = new ArrayList();

	static {
		list.add(new LookupDO(1, "Return"));
		list.add(new LookupDO(2, "Keep Advance"));
		list.add(new LookupDO(3, "Purchase"));
	}

	public static Collection getList() {
		return list;
	}
}