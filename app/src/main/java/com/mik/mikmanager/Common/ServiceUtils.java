package com.mik.mikmanager.Common;

import android.app.IMikRom;
import android.os.IBinder;
import android.util.Log;


import java.lang.reflect.Method;

public class ServiceUtils {
    private static IMikRom iMikRom = null;
    public static IMikRom getiMikRom() {
        if (iMikRom == null) {
            try {
                Class localClass = Class.forName("android.os.ServiceManager");
                Method getService = localClass.getMethod("getService", new Class[] {String.class});
                if(getService != null) {
                    Object objResult = getService.invoke(localClass, new Object[]{"mikrom"});
                    if (objResult != null) {
                        IBinder binder = (IBinder) objResult;
                        iMikRom = IMikRom.Stub.asInterface(binder);
                    }
                }
            } catch (Exception e) {
                Log.d("MikManager",e.getMessage());
                e.printStackTrace();
            }
        }
        return iMikRom;
    }
}
