package com.example.asus.workercrowdsource;

public class WorkerDetails {
    private String Address,City,Pincode,Role,Username,Name,PPLink,ContactNo,Job1,Job2,Job3;

    public WorkerDetails(){

    }

    public WorkerDetails(String address, String city, String pincode, String role, String username, String name, String PPLink, String contactNo, String job1, String job2, String job3) {
        Address = address;
        City = city;
        Pincode = pincode;
        Role = role;
        Username = username;
        Name = name;
        this.PPLink = PPLink;
        ContactNo = contactNo;
        Job1 = job1;
        Job2 = job2;
        Job3 = job3;
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

    public void setPPLink(String PPLink) {
        this.PPLink = PPLink;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getJob1() {
        return Job1;
    }

    public void setJob1(String job1) {
        Job1 = job1;
    }

    public String getJob2() {
        return Job2;
    }

    public void setJob2(String job2) {
        Job2 = job2;
    }

    public String getJob3() {
        return Job3;
    }

    public void setJob3(String job3) {
        Job3 = job3;
    }
}
