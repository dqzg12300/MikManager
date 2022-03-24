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
    //frida监听端口
    public int port;
    //手动选择的gadget路径，用来自行切换版本
    public String gadgetPath;
    public String gadgetArm64Path;
    //注入so
    public String soPath;
    //注入dex
    public String dexPath;
    //注入dex的对应接口的类名
    public String dexClassName;
    //是否注入dobby
    public boolean isDobby;
    //文件重定向 格式为:文件A->文件B,多条用换行分割
    public String rediectFile;
    //文件重定向 格式为:文件A->文件B,多条用换行分割
    public String rediectDir;
    //拒绝访问 多条用换行分割
    public String forbids;
    //是否设置生效
    public boolean enabled;
    public boolean isBlock;
    public PackageItem(){
        packageName="";
        appName="";
        breakClass="";
        whiteClass="";
        whitePath="";
        traceMethod="";
        sleepNativeMethod="";
        fridaJsPath="";
        gadgetPath="";
        gadgetArm64Path="";
        soPath="";
        dexPath="";
        dexClassName="";
        isDobby=false;
        rediectFile="";
        rediectDir="";
        forbids="";
        isBlock=false;
    }
    protected PackageItem(Parcel in) {
        packageName = in.readString();
        appName = in.readString();
        breakClass = in.readString();
        traceMethod = in.readString();
        sleepNativeMethod = in.readString();
        fridaJsPath = in.readString();
        port=in.readInt();
        isTuoke = in.readByte() != 0;
        isDeep = in.readByte() != 0;
        isInvokePrint = in.readByte() != 0;
        isRegisterNativePrint = in.readByte() != 0;
        isJNIMethodPrint = in.readByte() != 0;
        whiteClass=in.readString();
        whitePath=in.readString();
        gadgetPath=in.readString();
        gadgetArm64Path=in.readString();
        soPath=in.readString();
        dexPath=in.readString();
        dexClassName=in.readString();
        isDobby=in.readBoolean();
        rediectFile=in.readString();
        rediectDir=in.readString();
        forbids=in.readString();
        enabled=in.readBoolean();
        isBlock=in.readBoolean();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(packageName);
        dest.writeString(appName);
        dest.writeString(breakClass);
        dest.writeString(traceMethod);
        dest.writeString(sleepNativeMethod);
        dest.writeString(fridaJsPath);
        dest.writeInt(port);
        dest.writeByte((byte) (isTuoke ? 1 : 0));
        dest.writeByte((byte) (isDeep ? 1 : 0));
        dest.writeByte((byte) (isInvokePrint ? 1 : 0));
        dest.writeByte((byte) (isRegisterNativePrint ? 1 : 0));
        dest.writeByte((byte) (isJNIMethodPrint ? 1 : 0));
        dest.writeString(whiteClass);
        dest.writeString(whitePath);
        dest.writeString(gadgetPath);
        dest.writeString(gadgetArm64Path);
        dest.writeString(soPath);
        dest.writeString(dexPath);
        dest.writeString(dexClassName);
        dest.writeByte((byte) (isDobby ? 1 : 0));
        dest.writeString(rediectFile);
        dest.writeString(rediectDir);
        dest.writeString(forbids);
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeByte((byte) (isBlock ? 1 : 0));
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
