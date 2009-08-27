	function nothing(){}
	
	function GetParentByTagName(obj, tag_name) {
		var parent_node = obj.parentNode;
		if(parent_node.tagName == tag_name.toUpperCase()) {
			return parent_node;
		} else if(parent_node.parentNode) {
			return GetParentByTagName(parent_node, tag_name);
		}
	};


	function  createSelect(id, name, lookup, value) {
		var oSelect = document.createElement("SELECT");
		oSelect.id = id;
		oSelect.name = name;
		var oOption;
		selectIndex=0;
		var index=0;
		for (index=0; index < lookup.length; index++)	{
			oOption = document.createElement("OPTION")
			oOption.value = lookup[index][0];
			oOption.text = lookup[index][1];
			selectIndex = ((lookup[index][0]==value) ? (index) : (selectIndex));
			oSelect.add(oOption);
		}
		oSelect.options[selectIndex].selected=true;
		return oSelect;
		
	}
	
	function  setControlSelect(oSelect, pValue) {
		if (oSelect == null) {
			alert("Object Form Control Select is empty");
			return false;
		}
		var oOption = oSelect.options;
		for (i=0; i < oOption.length; i++)	{
			oOption[i].selected = (oOption[i].value==pValue) ? true : false;
		}
	}
	
	function GetElementsById(oCollection, vId) {
		var oCollectionLocal = new Array();
		var j = 0;
		for (var i=0; i < oCollection.length; i++) {
			if (oCollection.item(i).id == vId) {
				oCollectionLocal[j] = oCollection.item(i);
				j = j + 1;
			}
		}
		return oCollectionLocal;
	}
	
	function checkforblanks() {
		for (var i=0; i < arguments.length; i=i+2){
			if (! arguments[i].value){
			   alert("Information missing : Please specify " + arguments[i+1] + ".");
			   arguments[i].focus();
			   return false;
		 	}
		}
		return true;
	}
	
	
	function GetXmlHttpObject(handler) { 
		var objXmlHttp=null

		if (navigator.userAgent.indexOf("Opera")>=0) {
			alert("This example doesn't work in Opera") 
			return 
		}
		if (navigator.userAgent.indexOf("MSIE")>=0)	{ 
			var strName="Msxml2.XMLHTTP"
			if (navigator.appVersion.indexOf("MSIE 5.5")>=0){
				strName="Microsoft.XMLHTTP"
			} 
			try	{ 
				objXmlHttp=new ActiveXObject(strName)
				objXmlHttp.onreadystatechange=handler 
				return objXmlHttp
			} 
			catch(e) { 
				alert("Error. Scripting for ActiveX might be disabled") 
				return 
			} 
		} 
		if (navigator.userAgent.indexOf("Mozilla")>=0){
			objXmlHttp=new XMLHttpRequest()
			objXmlHttp.onload=handler
			objXmlHttp.onerror=handler 
			return objXmlHttp
		}
	} 

	function loadXML(oXmlSource) {
		//load xml file
		// code for IE
		if (window.ActiveXObject) {
			oXmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			oXmlDoc.async=false;
			oXmlDoc.load(oXmlSource);
		} else if (document.implementation && document.implementation.createDocument) { // code for Mozilla, etc. 		
			oXmlDoc= document.implementation.createDocument("","",null);
			oXmlDoc.load(oXmlSource);
		} else {
			alert('Your browser cannot handle this script');
		}
		return oXmlDoc;
	}