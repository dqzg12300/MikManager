package com.mik.mikmanager.Common;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Switch;

import androidx.annotation.RequiresApi;

public class PackageItem implements Parcelable {
    //应用包名
    public String packageName;
    //应用名称
    public String appName;
    //脱壳拉黑的类列表
    public String breakClass;
    //脱壳白名单列表,这里是过滤白名单。但是依然会加载对应的classloader
    public String whiteClass;
    //脱壳白名单列表的路径,也是过滤白名单。但是这里不会去加载所有classloader，可以有效的跳过对方的检测
    public String whitePath;
    //是否需要跑脱壳
    public boolean isTuoke;
    //是否需要跑更深的主动调用链
    public boolean isDeep;

    //是否打印调用函数
    public boolean isInvokePrint;
    //是否打印native函数注册
    public boolean isRegisterNativePrint;
    //是否打印JNI的函数调用
    public boolean isJNIMethodPrint;

    //java trace的函数名称
    public String traceMethod;
    //睡眠等待的native函数名称
    public String sleepNativeMethod;
    //持久化frida的js路径
    public String fridaJsPath;

    //是否设置生效
    public boolean enabled;
    public PackageItem(){

    }
    protected PackageItem(Parcel in) {
        packageName = in.readString();
        appName = in.readString();
        breakClass = in.readString();
        traceMethod = in.readString();
        sleepNativeMethod = in.readString();
        fridaJsPath = in.readString();
        isTuoke = in.readByte() != 0;
        isDeep = in.readByte() != 0;
        isInvokePrint = in.readByte() != 0;
        isRegisterNativePrint = in.readByte() != 0;
        isJNIMethodPrint = in.readByte() != 0;
        whiteClass=in.readString();
        whitePath=in.readString();
        enabled=in.readBoolean();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
        dest.writeString(appName);
        dest.writeString(breakClass);
        dest.writeString(traceMethod);
        dest.writeString(sleepNativeMethod);
        dest.writeString(fridaJsPath);
        dest.writeByte((byte) (isTuoke ? 1 : 0));
        dest.writeByte((byte) (isDeep ? 1 : 0));
        dest.writeByte((byte) (isInvokePrint ? 1 : 0));
        dest.writeByte((byte) (isRegisterNativePrint ? 1 : 0));
        dest.writeByte((byte) (isJNIMethodPrint ? 1 : 0));
        dest.writeString(whiteClass);
        dest.writeString(whitePath);
        dest.writeByte((byte) (enabled ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PackageItem> CREATOR = new Creator<PackageItem>() {
        @Override
        public PackageItem createFromParcel(Parcel in) {
            return new PackageItem(in);
        }

        @Override
        public PackageItem[] newArray(int size) {
            return new PackageItem[size];
        }
    };
}
