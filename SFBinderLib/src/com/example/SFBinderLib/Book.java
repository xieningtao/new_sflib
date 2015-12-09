package com.example.SFBinderLib;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xieningtao on 15-12-5.
 */
public class Book implements Parcelable {

    private String mName;

    private long mNumber;

    public Book(String name, long number) {
        this.mName = name;
        this.mNumber = number;
    }

    protected Book(Parcel in) {
        mName = in.readString();
        mNumber = in.readLong();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(mName);
        parcel.writeLong(mNumber);
    }

    @Override
    public String toString() {
        return "Book{" +
                "mName='" + mName + '\'' +
                ", mNumber=" + mNumber +
                '}';
    }
}
