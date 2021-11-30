# MikManager
### 简介

MikManager是一个rom逆向工具的管理界面。该软件用于对接MikRom来实现rom层面的逆向工具。仅仅提供界面化操作管理，并将用户需求保存为json数据，由MikRom解析后进行相应的执行，并导出结果到对应的目录。

### 配套ROM
> https://github.com/dqzg12300/MikRom

### 目录说明

`/sdcard/mikrom/config`该目录存放配置相关的内容

`/sdcard/mikrom/config/mikrom.config`保存用户对应用的所有处理选项

`/sdcard/mikrom/config/breakClass.config`全局的脱壳拉黑类列表

`/sdcard/mikrom/dump`该目录存放脱壳的结果，脱壳成功会生成对应的包名目录

`/sdcard/mikrom/dump/<size>_classlist.txt`:脱壳应用的类列表

`/sdcard/mikrom/dump/<size>_classlist_execute.txt`execute的触发时机获取的类列表

`/sdcard/mikrom/dump/<size>_dexfile.dex`脱壳结果

`/sdcard/mikrom/dump/<size>_deep_dexfile.dex`:更深调用的脱壳结果

`/sdcard/mikrom/dump/<size>_dexfile_repair.dex`修复后的脱壳结果

`/sdcard/mikrom/js/`该目录存放持久化frida脚本

### 功能

> * 脱壳（黑名单、白名单过滤、更深的主动调用链）
> * ROM打桩（抓包、ArtMethod调用、RegisterNative调用、JNI函数调用）
> * frida持久化（使用frida-gadget过frida检测）
> * 反调试（通过sleep目标函数，再附加进程来过掉起始的反调试）
> * trace java函数（smali指令的trace）

### 附录

该项目仅为个人练手作品，非商业项目。开源仅供学习，请勿用于非法用途。

### 感谢

> [FridaManager](https://github.com/hanbinglengyue/FridaManager)
>
> [FART](https://github.com/hanbinglengyue/FART)

### 界面展示

![](./mikmanager.gif)

