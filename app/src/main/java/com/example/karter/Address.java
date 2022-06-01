package com.example.karter;

public class Address {
    private String name;
    private String Address;
    private String zipCode;
    private String email;
    private String contactNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Address(String name, String address, String zipCode, String email, String contactNo) {
        this.name = name;
        Address = address;
        this.zipCode = zipCode;
        this.email = email;
        this.contactNo = contactNo;
    }
}
