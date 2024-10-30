package com.anahorn.fukusizer;

public class Clothing {

    //private variables
    int _id;
    String _dept;
    String _clothing;
    String _size;

    // Empty constructor
    public Clothing(){

    }
    // constructor
    public Clothing(int id, String dept, String clothing, String size){
        this._id = id;
        this._dept = dept;
        this._clothing = clothing;
        this._size = size;
    }

    // constructor
    public Clothing(String dept, String clothing, String size){
        this._dept = dept;
        this._clothing = clothing;
        this._size = size;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting dept
    public String getDept(){
        return this._dept;
    }

    // setting dept
    public void setDept(String dept){
        this._dept = dept;
    }

    // getting clothing type
    public String getClothing(){
        return this._clothing;
    }

    // setting clothing type
    public void setClothing(String clothing){
        this._clothing = clothing;
    }

    // getting list of sizes
    public String getSizes(){
        return this._size;
    }

    // setting list of sizes
    public void setSize(String size){
        this._size = size;
    }
}
