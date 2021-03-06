package com.example.user.jscanner.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "countries")
public class CountryItem {

    public CountryItem(@NonNull String startwith, String country, String countrycode) {
        this.startwith = startwith;
        this.country = country;
        this.countrycode = countrycode;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "startwith")
    private String startwith;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "countrycode")
    private String countrycode;

    public String getStartwith() {
        return startwith;
    }

    public String getCountry() {
        return country;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setStartwith(String startwith) {
        this.startwith = startwith;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }
}

