package com.rascarlo.arch.packages.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Details implements Parcelable {

    @SerializedName("pkgdesc")
    @Expose
    public String pkgdesc;

    @SerializedName("depends")
    @Expose
    public List<String> depends = null;

    @SerializedName("licenses")
    @Expose
    public List<String> licenses = null;

    @SerializedName("last_update")
    @Expose
    public String lastUpdate;

    @SerializedName("build_date")
    @Expose
    public String buildDate;

    @SerializedName("compressed_size")
    @Expose
    public int compressedSize;

    @SerializedName("installed_size")
    @Expose
    public int installedSize;

    @SerializedName("filename")
    @Expose
    public String filename;

    @SerializedName("epoch")
    @Expose
    public int epoch;

    @SerializedName("provides")
    @Expose
    public List<String> provides = null;

    @SerializedName("makedepends")
    @Expose
    public List<String> makedepends = null;

    @SerializedName("checkdepends")
    @Expose
    public List<String> checkdepends = null;

    @SerializedName("repo")
    @Expose
    public String repo;

    @SerializedName("maintainers")
    @Expose
    public List<String> maintainers = null;

    @SerializedName("groups")
    @Expose
    public List<String> groups = null;

    @SerializedName("conflicts")
    @Expose
    public List<String> conflicts = null;

    @SerializedName("packager")
    @Expose
    public String packager;

    @SerializedName("arch")
    @Expose
    public String arch;

    @SerializedName("pkgver")
    @Expose
    public String pkgver;

    @SerializedName("replaces")
    @Expose
    public List<String> replaces = null;

    @SerializedName("pkgname")
    @Expose
    public String pkgname;

    @SerializedName("optdepends")
    @Expose
    public List<String> optdepends = null;

    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("pkgbase")
    @Expose
    public String pkgbase;

    @SerializedName("pkgrel")
    @Expose
    public String pkgrel;

    @SerializedName("flag_date")
    @Expose
    public String flagDate;

    public String getPkgdesc() {
        return pkgdesc;
    }

    public List<String> getDepends() {
        return depends;
    }

    public List<String> getLicenses() {
        return licenses;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public int getCompressedSize() {
        return compressedSize;
    }

    public int getInstalledSize() {
        return installedSize;
    }

    public String getFilename() {
        return filename;
    }

    public int getEpoch() {
        return epoch;
    }

    public List<String> getProvides() {
        return provides;
    }

    public List<String> getMakedepends() {
        return makedepends;
    }

    public List<String> getCheckdepends() {
        return checkdepends;
    }

    public String getRepo() {
        return repo;
    }

    public List<String> getMaintainers() {
        return maintainers;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getConflicts() {
        return conflicts;
    }

    public String getPackager() {
        return packager;
    }

    public String getArch() {
        return arch;
    }

    public String getPkgver() {
        return pkgver;
    }

    public List<String> getReplaces() {
        return replaces;
    }

    public String getPkgname() {
        return pkgname;
    }

    public List<String> getOptdepends() {
        return optdepends;
    }

    public String getUrl() {
        return url;
    }

    public String getPkgbase() {
        return pkgbase;
    }

    public String getPkgrel() {
        return pkgrel;
    }

    public String getFlagDate() {
        return flagDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pkgdesc);
        dest.writeStringList(this.depends);
        dest.writeStringList(this.licenses);
        dest.writeString(this.lastUpdate);
        dest.writeString(this.buildDate);
        dest.writeInt(this.compressedSize);
        dest.writeInt(this.installedSize);
        dest.writeString(this.filename);
        dest.writeInt(this.epoch);
        dest.writeStringList(this.provides);
        dest.writeStringList(this.makedepends);
        dest.writeStringList(this.checkdepends);
        dest.writeString(this.repo);
        dest.writeStringList(this.maintainers);
        dest.writeStringList(this.groups);
        dest.writeStringList(this.conflicts);
        dest.writeString(this.packager);
        dest.writeString(this.arch);
        dest.writeString(this.pkgver);
        dest.writeStringList(this.replaces);
        dest.writeString(this.pkgname);
        dest.writeStringList(this.optdepends);
        dest.writeString(this.url);
        dest.writeString(this.pkgbase);
        dest.writeString(this.pkgrel);
        dest.writeString(this.flagDate);
    }

    public Details() {
    }

    protected Details(Parcel in) {
        this.pkgdesc = in.readString();
        this.depends = in.createStringArrayList();
        this.licenses = in.createStringArrayList();
        this.lastUpdate = in.readString();
        this.buildDate = in.readString();
        this.compressedSize = in.readInt();
        this.installedSize = in.readInt();
        this.filename = in.readString();
        this.epoch = in.readInt();
        this.provides = in.createStringArrayList();
        this.makedepends = in.createStringArrayList();
        this.checkdepends = in.createStringArrayList();
        this.repo = in.readString();
        this.maintainers = in.createStringArrayList();
        this.groups = in.createStringArrayList();
        this.conflicts = in.createStringArrayList();
        this.packager = in.readString();
        this.arch = in.readString();
        this.pkgver = in.readString();
        this.replaces = in.createStringArrayList();
        this.pkgname = in.readString();
        this.optdepends = in.createStringArrayList();
        this.url = in.readString();
        this.pkgbase = in.readString();
        this.pkgrel = in.readString();
        this.flagDate = in.readString();
    }

    public static final Parcelable.Creator<Details> CREATOR = new Parcelable.Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel source) {
            return new Details(source);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };
}
