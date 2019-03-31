package com.example.asus.workercrowdsource;

public class Worker {

    private String Name;
    private String Job1;
    private String ContactNo;
    private String Address;
    private String City;
    private String PPLink;


    public String getName() {
        return Name;
    }

    public String getPPLink() {
        return PPLink;
    }

    public void setPPLink(String PPLink) {
        this.PPLink = PPLink;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getJob1() {
        return Job1;
    }

    public void setJob1(String job1) {
        Job1 = job1;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Worker() {


    }

    public Worker(String name, String job1, String contactNo, String address, String city, String pplink) {
        Name = name;
        Job1 = job1;
        ContactNo = contactNo;
        Address = address;
        City = city;
        PPLink = pplink;
    }
}