package com.example.iit2014094.autotaskerapp.models;

/**
 * Created by iit2014094 on 3/24/2017.
 */

public class WifiLocations {

    //private variables
    int _id;
    String _name;
    String _mac_address;
    String _isSilent;
    String _isAutoSms;

    // Empty constructor
    public WifiLocations(){

    }
    // constructor
    public WifiLocations(int id, String name, String mac_address,String isSilent,String isAutoSms){
        this._id = id;
        this._name = name;
        this._mac_address = mac_address;
        this._isSilent = isSilent;
        this._isAutoSms = isAutoSms;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getMacAddress(){
        return this._mac_address;
    }

    // setting phone number
    public void setMacAddress(String mac_address){
        this._mac_address = mac_address;
    }

    public String getIsSilent(){
        return this._isSilent;
    }

    public void setIsSilent(String isSilent){
        this._isSilent = isSilent;
    }

    public String getAutoSms(){
        return this._isAutoSms;
    }

    public void setIsAutoSms(String isAutoSms){
        this._isAutoSms = isAutoSms;
    }
}

