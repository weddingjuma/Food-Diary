package com.randomappsinc.foodjournal.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.randomappsinc.foodjournal.persistence.models.DishDO;

public class Dish implements Parcelable {

    private int mId;
    private String mUriString;
    private String mTitle;
    private int mRating;
    private String mDescription;
    private long mTimeAdded;
    private long mTimeLastUpdated;

    public Dish() {}

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUriString() {
        return mUriString;
    }

    public void setUriString(String uriString) {
        mUriString = uriString;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public long getTimeAdded() {
        return mTimeAdded;
    }

    public void setTimeAdded(long timeAdded) {
        mTimeAdded = timeAdded;
    }

    public long getTimeLastUpdated() {
        return mTimeLastUpdated;
    }

    public void setTimeLastUpdated(long timeLastUpdated) {
        mTimeLastUpdated = mTimeLastUpdated;
    }

    public DishDO toDishDO() {
        DishDO dishDO = new DishDO();
        dishDO.setId(mId);
        dishDO.setUriString(mUriString);
        dishDO.setTitle(mTitle);
        dishDO.setRating(mRating);
        dishDO.setDescription(mDescription);
        dishDO.setTimeAdded(mTimeAdded);
        dishDO.setTimeLastUpdated(mTimeLastUpdated);
        return dishDO;
    }

    protected Dish(Parcel in) {
        mId = in.readInt();
        mUriString = in.readString();
        mTitle = in.readString();
        mRating = in.readInt();
        mDescription = in.readString();
        mTimeAdded = in.readLong();
        mTimeLastUpdated = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mUriString);
        dest.writeString(mTitle);
        dest.writeInt(mRating);
        dest.writeString(mDescription);
        dest.writeLong(mTimeAdded);
        dest.writeLong(mTimeLastUpdated);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Dish> CREATOR = new Parcelable.Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };
}
