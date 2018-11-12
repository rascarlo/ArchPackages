package com.rascarlo.arch.packages.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Packages implements Parcelable {

    @SerializedName("num_pages")
    @Expose
    private String numPages;

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("version")
    @Expose
    private String version;

    @SerializedName("limit")
    @Expose
    private String limit;

    @SerializedName("valid")
    @Expose
    private boolean valid;

    public String getNumPages() {
        return numPages;
    }

    public List<Result> getResults() {
        return results;
    }

    public String getPage() {
        return page;
    }

    public String getVersion() {
        return version;
    }

    public String getLimit() {
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
        dest.writeString(this.numPages);
        dest.writeTypedList(this.results);
        dest.writeString(this.page);
        dest.writeString(this.version);
        dest.writeString(this.limit);
        dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
    }

    public Packages() {
    }

    private Packages(Parcel in) {
        this.numPages = in.readString();
        this.results = in.createTypedArrayList(Result.CREATOR);
        this.page = in.readString();
        this.version = in.readString();
        this.limit = in.readString();
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