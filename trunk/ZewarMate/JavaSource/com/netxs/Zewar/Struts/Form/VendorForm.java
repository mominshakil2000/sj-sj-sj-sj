package com.netxs.Zewar.Struts.Form;

import org.apache.struts.validator.ValidatorActionForm;
import java.util.*;

public class VendorForm extends ValidatorActionForm {

	private String vendorId;

	private String nameTitle;

	private String firstName;

	private String lastName;

	private String middleName;

	private String companyName;

	private String addressLine1;

	private String addressLine2;

	private String cityName;

	private String countryId;

	private String phone1;

	private String phone2;

	private String mobile;

	private String email1;

	private String email2;

	private String[] vendorTypeId;

	private String ledgerAccountId;

	private List metalWastage = new Vector();
	
	private String bodyMakingRateSimple;
	
	private String bodyMakingRateMix;
	
	private String stoneSettingRateSimple;
	
	private String stoneSettingRateDifficult;
	
	private char hasFormInitialized;

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public char getHasFormInitialized() {
		return hasFormInitialized;
	}

	public void setHasFormInitialized(char hasFormInitialized) {
		this.hasFormInitialized = hasFormInitialized;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNameTitle() {
		return nameTitle;
	}

	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String[] getVendorTypeId() {
		return vendorTypeId;
	}

	public void setVendorTypeId(String[] vendorTypeId) {
		this.vendorTypeId = vendorTypeId;
	}

	public String getLedgerAccountId() {
		return ledgerAccountId;
	}

	public void setLedgerAccountId(String ledgerAccountId) {
		this.ledgerAccountId = ledgerAccountId;
	}

	public void setMetalWastage(List metalWastage) {
		this.metalWastage = metalWastage;
	}

	public void setMetalWastage(VendorItemForm metalWastage) {
		this.metalWastage.add(metalWastage);
	}

	public VendorItemForm getMetalWastage(int index) {

		if (this.metalWastage.size() < index + 1)
			((Vector) this.metalWastage).setSize(index + 1);

		if (this.metalWastage.get(index) == null) {
			this.metalWastage.set(index, new VendorItemForm());
		}
		return (VendorItemForm) this.metalWastage.get(index);
	}

	public Collection getMetalWastageList() {
		return this.metalWastage;
	}

	public String getBodyMakingRateMix() {
		return bodyMakingRateMix;
	}

	public void setBodyMakingRateMix(String bodyMakingRateMix) {
		this.bodyMakingRateMix = bodyMakingRateMix;
	}

	public String getBodyMakingRateSimple() {
		return bodyMakingRateSimple;
	}

	public void setBodyMakingRateSimple(String bodyMakingRateSimple) {
		this.bodyMakingRateSimple = bodyMakingRateSimple;
	}

	public String getStoneSettingRateDifficult() {
		return stoneSettingRateDifficult;
	}

	public void setStoneSettingRateDifficult(String stoneSettingRateDifficult) {
		this.stoneSettingRateDifficult = stoneSettingRateDifficult;
	}

	public String getStoneSettingRateSimple() {
		return stoneSettingRateSimple;
	}

	public void setStoneSettingRateSimple(String stoneSettingRateSimple) {
		this.stoneSettingRateSimple = stoneSettingRateSimple;
	}
}