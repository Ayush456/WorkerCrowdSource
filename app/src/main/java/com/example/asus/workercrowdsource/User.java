package com.example.asus.workercrowdsource;
import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{
    String Name,ContactNo,Addr,City,Pincode,Role,PPLink,UserName,Email;
    public User() {
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public User(String Name, String ContactNo, String Address, String City, String Pincode, String Role, String PPLink
    , String UserName){
        this.Name  = Name;
        this.ContactNo = ContactNo;
        this.Addr = Address;


        this.City = City;
        this.Pincode =Pincode;
        this.Role = Role;
        this.PPLink = PPLink;
        this.UserName = UserName;
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

    public String getAddr() {
        return Addr;
    }

    public void setAddr(String addr) {
        Addr = addr;
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

    public String getPPLink() {
        return PPLink;
    }

    public void setPPLink(String PPLink) {
        this.PPLink = PPLink;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
