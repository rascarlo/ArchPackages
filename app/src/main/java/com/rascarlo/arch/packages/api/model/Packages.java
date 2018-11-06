package com.rascarlo.arch.packages.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Packages implements Parcelable {

    @SerializedName("num_pages")
    @Expose
    private int numPages;

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("version")
    @Expose
    private int version;

    @SerializedName("limit")
    @Expose
    private int limit;

    @SerializedName("valid")
    @Expose
    private boolean valid;

    public int getNumPages() {
        return numPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public int getPage() {
        return page;
    }

    public int getVersion() {
        return version;
    }

    public int getLimit() {
        return limit;
    }

    public boolean isValid() {
        return valid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.numPages);
        dest.writeTypedList(this.results);
        dest.writeInt(this.page);
        dest.writeInt(this.version);
        dest.writeInt(this.limit);
        dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
    }

    public Packages() {
    }

    private Packages(Parcel in) {
        this.numPages = in.readInt();
        this.results = in.createTypedArrayList(Result.CREATOR);
        this.page = in.readInt();
        this.version = in.readInt();
        this.limit = in.readInt();
        this.valid = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Packages> CREATOR = new Parcelable.Creator<Packages>() {
        @Override
        public Packages createFromParcel(Parcel source) {
            return new Packages(source);
        }

        @Override
        public Packages[] newArray(int size) {
            return new Packages[size];
        }
    };
}