package com.code.fitbase.room.products;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Prizes")
public class Prizes {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Code")
    String code;

    @ColumnInfo(name = "ValidFrom")
    String validFrom;

    @ColumnInfo(name = "ExpirationDate")
    String expirationDate;

    @ColumnInfo(name = "ProductOwner")
    String productOwner;

    @ColumnInfo(name = "ProductName")
    String productName;

    @ColumnInfo(name = "ProductImage")
    String productImage;

    @ColumnInfo(name = "PointsNeeded")
    String pointsNeeded;

    @ColumnInfo(name = "Availability")
    String availability;

    public Prizes(@NonNull String code, String validFrom, String expirationDate, String productOwner, String productName, String productImage, String pointsNeeded, String availability) {
        this.code = code;
        this.validFrom = validFrom;
        this.expirationDate = expirationDate;
        this.productOwner = productOwner;
        this.productName = productName;
        this.productImage = productImage;
        this.pointsNeeded = pointsNeeded;
        this.availability = availability;
    }

    @NonNull
    public String getCode() {
        return code;
    }

    public void setCode(@NonNull String code) {
        this.code = code;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getPointsNeeded() {
        return pointsNeeded;
    }

    public void setPointsNeeded(String pointsNeeded) {
        this.pointsNeeded = pointsNeeded;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
