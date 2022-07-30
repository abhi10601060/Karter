package com.example.karter;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String name;
    private String Address;
    private String zipCode;
    private String email;
    private String contactNo;

    public Address() {
    }

    protected Address(Parcel in) {
        name = in.readString();
        Address = in.readString();
        zipCode = in.readString();
        email = in.readString();
        contactNo = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(Address);
        parcel.writeString(zipCode);
        parcel.writeString(email);
        parcel.writeString(contactNo);
    }
}
