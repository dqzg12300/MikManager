package com.mik.mikmanager.Common;

public interface FragmentListen{
    public void onDumpAttach();
    public void onWorkAppAttach();
    public void onOtherAttach();
    public void onRomLogAttach();
    public void onRomInjectAttach();
    public void onIORediectAttach();
}