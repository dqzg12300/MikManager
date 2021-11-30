package com.mik.mikmanager.Common;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

public class AppInfo implements Parcelable {
    String appName;
    String packageName;
    Drawable drawable;
    boolean enabled;

    public AppInfo(){}

    public AppInfo(String appName){
        this.appName = appName;
    }

    public AppInfo(String appName, String packageName){
        this.appName = appName;
        this.packageName = packageName;
        this.enabled = true;
    }

    public AppInfo(String appName, String packageName, Drawable drawable){
        this.appName = appName;
        this.packageName = packageName;
        this.drawable = drawable;
        this.enabled = true;
    }

    protected AppInfo(Parcel in) {
        appName = in.readString();
        packageName = in.readString();
        enabled = in.readBoolean();
    }

    public static final Creator<AppInfo> CREATOR = new Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel in) {
            return new AppInfo(in);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };

    public String getAppName() {
        if(null == appName)
            return "";
        else
            return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        if(null == packageName)
            return "";
        else
            return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setPackageName(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appName);
        dest.writeString(packageName);
    }
}
