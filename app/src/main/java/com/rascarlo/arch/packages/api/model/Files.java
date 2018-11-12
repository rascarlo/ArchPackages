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
