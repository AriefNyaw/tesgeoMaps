package com.example.tespitjarus.Model.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "toko")

public class Toko {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "store_id")
    private
    String store_id;

    @ColumnInfo(name = "store_code")
    private
    String store_code;

    @ColumnInfo(name = "store_name")
    private
    String store_name;

    @ColumnInfo(name = "address")
    private
    String adrress;

    @ColumnInfo(name = "dc_id")
    private
    String dc_id;

    @ColumnInfo(name = "dc_name")
    private
    String dc_name;

    @ColumnInfo(name = "account_id")
    private
    String account_id;

    @ColumnInfo(name = "account_name")
    private
    String account_name;

    @ColumnInfo(name = "subchannel_id")
    private
    String subchannel_id;

    @ColumnInfo(name = "subchannel_name")
    private
    String subchannel_name;

    @ColumnInfo(name = "channel_id")
    private
    String channel_id;

    @ColumnInfo(name = "channel_name")
    private
    String channel_name;

    @ColumnInfo(name = "area_id")
    private
    String area_id;

    @ColumnInfo(name = "area_name")
    private
    String area_name;

    @ColumnInfo(name = "region_id")
    private
    String region_id;

    @ColumnInfo(name = "region_name")
    private
    String region_name;

    @ColumnInfo(name = "latitude")
    private
    String latitude;

    @ColumnInfo(name = "longitude")
    private
    String logintude;


    @NonNull
    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(@NonNull String store_id) {
        this.store_id = store_id;
    }

    public String getStore_code() {
        return store_code;
    }

    public void setStore_code(String store_code) {
        this.store_code = store_code;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public String getDc_id() {
        return dc_id;
    }

    public void setDc_id(String dc_id) {
        this.dc_id = dc_id;
    }

    public String getDc_name() {
        return dc_name;
    }

    public void setDc_name(String dc_name) {
        this.dc_name = dc_name;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getSubchannel_id() {
        return subchannel_id;
    }

    public void setSubchannel_id(String subchannel_id) {
        this.subchannel_id = subchannel_id;
    }

    public String getSubchannel_name() {
        return subchannel_name;
    }

    public void setSubchannel_name(String subchannel_name) {
        this.subchannel_name = subchannel_name;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogintude() {
        return logintude;
    }

    public void setLogintude(String logintude) {
        this.logintude = logintude;
    }

}
