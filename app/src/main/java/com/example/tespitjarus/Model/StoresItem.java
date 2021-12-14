package com.example.tespitjarus.Model;

import com.google.gson.annotations.SerializedName;

public class StoresItem{

	@SerializedName("store_id")
	private String storeId;

	@SerializedName("store_code")
	private String storeCode;

	@SerializedName("channel_name")
	private String channelName;

	@SerializedName("area_name")
	private String areaName;

	@SerializedName("address")
	private String address;

	@SerializedName("dc_name")
	private String dcName;

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("region_id")
	private String regionId;

	@SerializedName("area_id")
	private String areaId;

	@SerializedName("account_id")
	private String accountId;

	@SerializedName("dc_id")
	private String dcId;

	@SerializedName("subchannel_id")
	private String subchannelId;

	@SerializedName("account_name")
	private String accountName;

	@SerializedName("store_name")
	private String storeName;

	@SerializedName("subchannel_name")
	private String subchannelName;

	@SerializedName("region_name")
	private String regionName;

	@SerializedName("channel_id")
	private String channelId;

	@SerializedName("longitude")
	private String longitude;

	public void setStoreId(String storeId){
		this.storeId = storeId;
	}

	public String getStoreId(){
		return storeId;
	}

	public void setStoreCode(String storeCode){
		this.storeCode = storeCode;
	}

	public String getStoreCode(){
		return storeCode;
	}

	public void setChannelName(String channelName){
		this.channelName = channelName;
	}

	public String getChannelName(){
		return channelName;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return areaName;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setDcName(String dcName){
		this.dcName = dcName;
	}

	public String getDcName(){
		return dcName;
	}

	public void setLatitude(String latitude){
		this.latitude = latitude;
	}

	public String getLatitude(){
		return latitude;
	}

	public void setRegionId(String regionId){
		this.regionId = regionId;
	}

	public String getRegionId(){
		return regionId;
	}

	public void setAreaId(String areaId){
		this.areaId = areaId;
	}

	public String getAreaId(){
		return areaId;
	}

	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	public String getAccountId(){
		return accountId;
	}

	public void setDcId(String dcId){
		this.dcId = dcId;
	}

	public String getDcId(){
		return dcId;
	}

	public void setSubchannelId(String subchannelId){
		this.subchannelId = subchannelId;
	}

	public String getSubchannelId(){
		return subchannelId;
	}

	public void setAccountName(String accountName){
		this.accountName = accountName;
	}

	public String getAccountName(){
		return accountName;
	}

	public void setStoreName(String storeName){
		this.storeName = storeName;
	}

	public String getStoreName(){
		return storeName;
	}

	public void setSubchannelName(String subchannelName){
		this.subchannelName = subchannelName;
	}

	public String getSubchannelName(){
		return subchannelName;
	}

	public void setRegionName(String regionName){
		this.regionName = regionName;
	}

	public String getRegionName(){
		return regionName;
	}

	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return channelId;
	}

	public void setLongitude(String longitude){
		this.longitude = longitude;
	}

	public String getLongitude(){
		return longitude;
	}
}