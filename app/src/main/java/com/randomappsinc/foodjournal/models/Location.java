package com.randomappsinc.foodjournal.models;

import android.content.Context;

import com.randomappsinc.foodjournal.R;
import com.randomappsinc.foodjournal.persistence.models.LocationDO;
import com.randomappsinc.foodjournal.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private int mId;
    private String mName = "";
    private String mAddress = "";
    private boolean mIsCurrentLocation;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public boolean isCurrentLocation() {
        return mIsCurrentLocation;
    }

    public void setIsCurrentLocation(boolean isCurrentLocation) {
        mIsCurrentLocation = isCurrentLocation;
    }

    public String[] getLocationOptions() {
        Context context = MyApplication.getAppContext();
        List<String> options = new ArrayList<>();
        if (!mIsCurrentLocation) {
            options.add(context.getString(R.string.set_as_current));
        }
        options.add(context.getString(R.string.edit_name));
        options.add(context.getString(R.string.edit_address));
        options.add(context.getString(R.string.delete_location));
        return options.toArray(new String[options.size()]);
    }

    public LocationDO toLocationDO() {
        LocationDO locationDO = new LocationDO();
        locationDO.setId(mId);
        locationDO.setName(mName);
        locationDO.setAddress(mAddress);
        locationDO.setIsCurrentLocation(mIsCurrentLocation);
        return locationDO;
    }
}