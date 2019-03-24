package com.example.asus.workercrowdsource;

public class AllUsers {

   private String Address,City,Pincode,Role,Username,Name,PPLink,ContactNo;

    public AllUsers(){

    }

    public AllUsers(String address, String city, String pincode, String role, String username, String name, String PPLink, String contactNo) {
        Address = address;
        City = city;
        Pincode = pincode;
        Role = role;
        Username = username;
        Name = name;
        this.PPLink = PPLink;
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

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPPLink() {
        return PPLink;
    }

    public void setPPLink(String PPlink) {
        this.PPLink = PPlink;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }
}
