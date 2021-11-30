package com.mik.mikmanager.Common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.mik.mikmanager.R;

import java.util.List;

public class PackageAdapter extends ArrayAdapter {
    private final int resourceId;
    private List<PackageItem> packageList;
    private DeleteCallback Callback;

    public void setData(List<PackageItem> data) {
        this.packageList=data;
    }
    public interface DeleteCallback {
        void deletePosition(int saveposition);
    }
    public void setDeleteCallback(DeleteCallback  Callback ){
        this.Callback=Callback;
    }

    public PackageAdapter(Context context, int textViewResourceId, List<PackageItem> objects) {
        super(context, textViewResourceId, objects);
        packageList=(List<PackageItem>)objects;
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackageItem packageData = (PackageItem) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView appIcon = (ImageView) view.findViewById(R.id.app_icon);//获取该布局内的图片视图
        TextView txtPackageName = (TextView) view.findViewById(R.id.txtPackageName);//获取该布局内的文本视图
        Switch chkEnabled = (Switch) view.findViewById(R.id.chkEnabled);
        ImageButton btnRemove=(ImageButton) view.findViewById(R.id.btnRemove);
        PackageManager pm=this.getContext().getApplicationContext().getPackageManager();
        try {
            PackageInfo packgeInfo = pm.getPackageInfo(packageData.packageName, PackageManager.GET_CONFIGURATIONS);
            appIcon.setBackground(packgeInfo.applicationInfo.loadIcon(pm));//为图片视图设置图片资源
            txtPackageName.setText(packageData.packageName);//为文本视图设置文本内容
            chkEnabled.setChecked(packageData.enabled);
            chkEnabled.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PackageItem packageData = (PackageItem) getItem(position);
                    packageData.enabled=chkEnabled.isChecked();
                    FileHelper.SaveMikromConfig(packageList);
                }
            });
            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Callback.deletePosition(position);
                }
            });

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return view;
    }
}
