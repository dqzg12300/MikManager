package com.mik.mikmanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.mik.mikmanager.Common.ConfigUtil;
import com.mik.mikmanager.Common.FileHelper;
import com.mik.mikmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private void InitDirectory(){
        FileHelper.writeTxtToFile("该目录存放配置相关的内容\nmikrom.config:保存用户对应用的处理选项\nbreakClass.config:通用的脱壳拉黑类列表","/sdcard/mikrom/config/","README");
        FileHelper.writeTxtToFile("该目录存放脱壳的结果，脱壳成功会生成对应的包名目录\n<size>_classlist.txt:类列表\n<size>_classlist_execute.txt:execute的触发时机获取的类列表\n<size>_dexfile.dex:脱壳结果\n<size>_deep_dexfile.dex:更深调用的脱壳结果\n<size>_dexfile_repair.dex:修复后的脱壳结果","/sdcard/mikrom/dump/","README");
        FileHelper.writeTxtToFile("该目录存放持久化frida脚本","/sdcard/mikrom/js/","README");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //权限已成功申请
                    InitDirectory();
                }else{
                    //用户拒绝授权
                    Toast.makeText(this, "无法获取SD卡读写权限", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_apps, R.id.nav_global_setting, R.id.nav_js_manager,R.id.nav_readme,R.id.nav_abort)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        int permission_write= ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read=ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permission_write!= PackageManager.PERMISSION_GRANTED
                || permission_read!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "正在请求权限", Toast.LENGTH_SHORT).show();
            //申请权限，特征码自定义为1，可在回调时进行相关判断
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},1);
        }else{
            InitDirectory();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, SettingActivity.class);
//                startActivity(intent);
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}