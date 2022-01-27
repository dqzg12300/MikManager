package com.mik.mikmanager.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.mik.mikmanager.Common.ConfigUtil;
import com.mik.mikmanager.Common.FileHelper;
import com.mik.mikmanager.Common.PackageAdapter;
import com.mik.mikmanager.Common.PackageItem;
import com.mik.mikmanager.Common.ServiceUtils;
import com.mik.mikmanager.EditPackageActivity;
import com.mik.mikmanager.R;

import com.mik.mikmanager.databinding.FragmentHomeBinding;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PackageAdapter.DeleteCallback {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView ;
    private TextView textView;
    private List<PackageItem> packageList = new ArrayList<PackageItem>();
    private static int curPos=-1;
    private PackageAdapter adapter;
    private static String TAG="MikManager";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView= (ListView) binding.listView;
        listView.setOnItemClickListener(new MyOnItemClickListener());

        textView = binding.textHome;

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(root.getContext(), EditPackageActivity.class);
                i.putExtra("op","add");
                i.putExtra("packageNames",getPackageListName());
                startActivityForResult(i,1);
            }
        });

        adapter = new PackageAdapter(binding.getRoot().getContext(), R.layout.package_item, packageList);
        adapter.setDeleteCallback(this);
        listView.setAdapter(adapter);
        initData(root.getContext());
        return root;
    }

    //获取当前选择处理的所有应用
    public String getPackageListName(){
        StringBuilder sb=new StringBuilder();
        for(PackageItem item : packageList){
            sb.append(item.packageName);
            sb.append(",");
        }
        return sb.toString();
    }

    //设置每个元素的点击事件
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PackageItem item =  (PackageItem) parent.getItemAtPosition(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("packageData",item);
            Intent i = new Intent(binding.getRoot().getContext(), EditPackageActivity.class);
            i.putExtra("op","edit");
            i.putExtras(bundle);
            startActivityForResult(i,1);
        }
    }

    //更新界面
    private void updateUi(){
        adapter.setData(packageList);
        adapter.notifyDataSetInvalidated();
    }
    //删除元素
    @Override
    public void deletePosition(int saveposition) {
        packageList.remove(saveposition);
        FileHelper.SaveMikromConfig(packageList);
        updateUi();
    }

    //获取所有安装的应用名称
    public List<String> getInstallPackageNames(){
        final PackageManager packageManager =binding.getRoot().getContext().getPackageManager();;
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName;
    }

    //初始化数据
    private void initData(Context context){
        String res= null;
        try {
            res = ServiceUtils.getiMikRom().readFile(ConfigUtil.configPath);
            Log.e(ConfigUtil.TAG,"readConfig data:"+res);
        } catch (RemoteException e) {
            Log.e(ConfigUtil.TAG,"readConfig err:"+e.getMessage());
            return;
        }

        if(res!=null&&res.length()>0){
            textView.setText("");
            Gson gson=new Gson();
            List<LinkedTreeMap> tmpList= gson.fromJson(res,packageList.getClass());
            List<String>pNames=getInstallPackageNames();
            for(LinkedTreeMap item :tmpList){
                PackageItem data=new PackageItem();
                data.packageName=item.get("packageName").toString();
                if(!pNames.contains(data.packageName)){
                    continue;
                }
                data.appName=item.get("appName").toString();
                data.sleepNativeMethod=item.get("sleepNativeMethod")==null?"":item.get("sleepNativeMethod").toString();
                data.breakClass=item.get("breakClass")==null?"":item.get("breakClass").toString();
                data.traceMethod=item.get("traceMethod")==null?"":item.get("traceMethod").toString();
                data.isTuoke=item.get("isTuoke")==null?false:(boolean)item.get("isTuoke");
                data.isDeep=item.get("isDeep")==null?false:(boolean)item.get("isDeep");
                data.isInvokePrint=item.get("isInvokePrint")==null?false:(boolean)item.get("isInvokePrint");
                data.isRegisterNativePrint=item.get("isRegisterNativePrint")==null?false:(boolean)item.get("isRegisterNativePrint");
                data.isJNIMethodPrint=item.get("isJNIMethodPrint")==null?false:(boolean)item.get("isJNIMethodPrint");
                data.fridaJsPath=item.get("fridaJsPath")==null?"":item.get("fridaJsPath").toString();
                data.whiteClass=item.get("whiteClass")==null?"":item.get("whiteClass").toString();
                data.whitePath=item.get("whitePath")==null?"":item.get("whitePath").toString();
                data.enabled=item.get("enabled")==null?false:(boolean)item.get("enabled");
                data.port=item.get("port")==null?27042:Double.valueOf(item.get("port").toString()).intValue();
                data.gadgetPath=item.get("gadgetPath")==null?"":item.get("gadgetPath").toString();
                data.gadgetArm64Path=item.get("gadgetArm64Path")==null?"":item.get("gadgetArm64Path").toString();
                data.soPath=item.get("soPath")==null?"":item.get("soPath").toString();
                data.isDobby=item.get("isDobby")==null?false:(boolean)item.get("isDobby");
                data.dexPath=item.get("dexPath")==null?"":item.get("dexPath").toString();
                data.dexClassName=item.get("dexClassName")==null?"":item.get("dexClassName").toString();
                packageList.add(data);
            }
            updateUi();
        }
    }

    //编辑项目后的返回
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //添加的回调
        if (requestCode == 1 && resultCode == 1) {
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                PackageItem appInfo = (PackageItem)bundle.getParcelable("packageData");
                if(appInfo!=null){
                    PackageManager pm=binding.getRoot().getContext().getPackageManager();
                    for(PackageItem item : packageList){
                        if(item.packageName.equals(appInfo.packageName)){
                            packageList.remove(item);
                            break;
                        }
                    }
                    appInfo.enabled=true;
                    packageList.add(appInfo);
                    updateUi();
                    FileHelper.SaveMikromConfig(packageList);
                }
            }
        }
        //删除的回调
        if (requestCode == 1 && resultCode == 2){
            String packageName=data.getStringExtra("packageName");
            if(packageName!=null){
                for(PackageItem item : packageList){
                    if(item.packageName.equals(packageName)){
                        packageList.remove(item);
                        updateUi();
                        FileHelper.SaveMikromConfig(packageList);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}