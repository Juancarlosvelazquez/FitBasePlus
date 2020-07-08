package com.code.fitbase.room.products;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ProductOwners")
public class ProductOwners {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    public int id;

    @ColumnInfo(name = "CompanyName")
    String companyName;

    @ColumnInfo(name = "ContactName")
    String contactName;

    @ColumnInfo(name = "ContactNumber")
    String contactNumber;

    @ColumnInfo(name = "Website")
    String website;

    @NonNull
    public int getId() {
        return id;
    }



    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public ProductOwners(String companyName, String contactName, String contactNumber, String website) {
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
        this.website = website;
    }
}
