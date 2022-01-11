# MikManager
### 简介

MikManager是一个rom逆向工具的管理界面。该软件用于对接MikRom来实现rom层面的逆向工具。仅仅提供界面化操作管理，并将用户需求保存为json数据，由MikRom解析后进行相应的执行，并导出结果到对应的目录。

### 配套ROM
> https://github.com/dqzg12300/MikRom

### 提示
> 如果需要使用frida-gadget挂载脚本或者是脱壳的功能,需要打开目标应用的sdcard权限，否则会因为没有访问权限无法正常执行。

### 目录说明

`/sdcard/mikrom/dump`该目录存放脱壳的结果，脱壳成功会生成对应的包名目录

`/sdcard/mikrom/dump/<size>_classlist.txt`:脱壳应用的类列表

`/sdcard/mikrom/dump/<size>_classlist_execute.txt`execute的触发时机获取的类列表

`/sdcard/mikrom/dump/<size>_dexfile.dex`脱壳结果

`/sdcard/mikrom/dump/<size>_deep_dexfile.dex`:更深调用的脱壳结果

`/sdcard/mikrom/dump/<size>_dexfile_repair.dex`修复后的脱壳结果

`/sdcard/mikrom/js/`该目录存放持久化frida脚本

### 功能

> * 内核修改过反调试
> * 开启硬件断点
> * 自动弹出USB调试
> * 脱壳（黑名单、白名单过滤、更深的主动调用链）
> * ROM打桩（ArtMethod调用、RegisterNative调用、JNI函数调用）
> * frida持久化（支持listen,wait,script三种模式）
> * 反调试（通过sleep目标函数，再附加进程来过掉起始的反调试）
> * trace java函数（smali指令的trace）
> * 内置dobby注入
> * 支持自行切换frida-gadget版本
> * 注入so
> * 注入dex（实现对应的接口触发调用。目前仅测试使用）

### 附录

该项目仅为个人练手作品，非商业项目。开源仅供学习，请勿用于非法用途。

### 感谢

> [FridaManager](https://github.com/hanbinglengyue/FridaManager)
>
> [FART](https://github.com/hanbinglengyue/FART)

### 界面展示

![](./mikmanager.gif)

