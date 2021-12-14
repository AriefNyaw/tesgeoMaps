package com.example.tespitjarus.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseLogin{

	@SerializedName("stores")
	private List<StoresItem> stores;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setStores(List<StoresItem> stores){
		this.stores = stores;
	}

	public List<StoresItem> getStores(){
		return stores;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}
}