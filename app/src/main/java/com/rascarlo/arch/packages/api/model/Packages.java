/*
 *     Copyright (C) 2018 rascarlo
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
    private String version;

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

    public String getVersion() {
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
        dest.writeString(this.version);
        dest.writeInt(this.limit);
        dest.writeByte(this.valid ? (byte) 1 : (byte) 0);
    }

    public Packages() {
    }

    private Packages(Parcel in) {
        this.numPages = in.readInt();
        this.results = in.createTypedArrayList(Result.CREATOR);
        this.page = in.readInt();
        this.version = in.readString();
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