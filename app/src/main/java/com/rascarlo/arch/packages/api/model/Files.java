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

public class Files implements Parcelable {
    @SerializedName("repo")
    @Expose
    private String repo;

    @SerializedName("dir_count")
    @Expose
    private String dirCount;

    @SerializedName("pkgname")
    @Expose
    private String pkgname;

    @SerializedName("files_last_update")
    @Expose
    private String filesLastUpdate;

    @SerializedName("pkg_last_update")
    @Expose
    private String pkgLastUpdate;

    @SerializedName("arch")
    @Expose
    private String arch;

    @SerializedName("files")
    @Expose
    public List<String> files = null;

    @SerializedName("files_count")
    @Expose
    private String filesCount;

    public String getRepo() {
        return repo;
    }

    public String getDirCount() {
        return dirCount;
    }

    public String getPkgname() {
        return pkgname;
    }

    public String getFilesLastUpdate() {
        return filesLastUpdate;
    }

    public String getPkgLastUpdate() {
        return pkgLastUpdate;
    }

    public String getArch() {
        return arch;
    }

    public List<String> getFiles() {
        return files;
    }

    public String getFilesCount() {
        return filesCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.repo);
        dest.writeString(this.dirCount);
        dest.writeString(this.pkgname);
        dest.writeString(this.filesLastUpdate);
        dest.writeString(this.pkgLastUpdate);
        dest.writeString(this.arch);
        dest.writeStringList(this.files);
        dest.writeString(this.filesCount);
    }

    public Files() {
    }

    protected Files(Parcel in) {
        this.repo = in.readString();
        this.dirCount = in.readString();
        this.pkgname = in.readString();
        this.filesLastUpdate = in.readString();
        this.pkgLastUpdate = in.readString();
        this.arch = in.readString();
        this.files = in.createStringArrayList();
        this.filesCount = in.readString();
    }

    public static final Parcelable.Creator<Files> CREATOR = new Parcelable.Creator<Files>() {
        @Override
        public Files createFromParcel(Parcel source) {
            return new Files(source);
        }

        @Override
        public Files[] newArray(int size) {
            return new Files[size];
        }
    };
}
