package com.example.asus.workercrowdsource;

public class Contractor {
    private String Name;
    private String ContactNo;
    private String Address;
    private String City;
    private String PPLink;
    private String Username;

    public Contractor(){

    }

    public Contractor(String name, String contactNo, String address, String city, String PPLink, String username) {
        Name = name;
        ContactNo = contactNo;
        Address = address;
        City = city;
        this.PPLink = PPLink;
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getPPLink() {
        return PPLink;
    }

    public void setPPLink(String PPLink) {
        this.PPLink = PPLink;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }
}
