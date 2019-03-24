package com.example.asus.workercrowdsource;

public class Contractor {

    private String Name;
    private String ContactNo;
    private String City;
    private String PPLink;

    public Contractor(){

    }

    public Contractor(String name, String contact, String salary, String location, String image) {
        this.Name = name;
        this.ContactNo = contact;
        this.City = location;
        this.PPLink = image;
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
}
