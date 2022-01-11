package com.mik.mikmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.mik.mikmanager.Common.AppInfo;
import com.mik.mikmanager.Common.CallbackBundle;
import com.mik.mikmanager.Common.FragmentListen;
import com.mik.mikmanager.Common.OpenFileDialog;
import com.mik.mikmanager.Common.PackageItem;
import com.mik.mikmanager.ui.addpackage.DumpFragment;
import com.mik.mikmanager.ui.addpackage.OtherFragment;
import com.mik.mikmanager.ui.addpackage.RomInjectFragment;
import com.mik.mikmanager.ui.addpackage.RomLogFragment;
import com.mik.mikmanager.ui.addpackage.WorkAppFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EditPackageActivity extends FragmentActivity implements FragmentListen {

    enum OpenDirType{
        OpenFridaJs,
        OpenWhite,
        OpenGadget,
        OpenGadgetArm64,
        OpenSo,
        OpenDex,
    }

    public AppInfo curAppInfo=null;
    private ArrayList<Fragment> fragments = new ArrayList<>();

    public static final String [] sTitle = new String[]{"应用","脱壳","打桩","辅助","注入"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    public String opdata="";
    public List<String> packageNames=new ArrayList<>();

    PackageItem packageData=null;

    WorkAppFragment workAppFragment=WorkAppFragment.newInstance();
    DumpFragment dumpFragment=DumpFragment.newInstance(sTitle[1]);
    RomLogFragment romLogFragment=RomLogFragment.newInstance();
    OtherFragment otherFragment=OtherFragment.newInstance();
    RomInjectFragment injectFragment=RomInjectFragment.newInstance();

    private boolean initWorkApp=false;
    private boolean initDump=false;
    private boolean initRomLog=false;
    private boolean initOther=false;
    private boolean initInject=false;

    private void initView(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();

        fragments.add(workAppFragment);
        fragments.add(dumpFragment);
        fragments.add(romLogFragment);
        fragments.add(otherFragment);
        fragments.add(injectFragment);
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return sTitle[position];
            }
        };
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_package);
        initView();
        opdata=this.getIntent().getStringExtra("op");
        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            packageData= (PackageItem) bundle.getParcelable("packageData");
        }

        String names=this.getIntent().getStringExtra("packageNames");
        if(names!=null){
            packageNames= Arrays.asList(names.split(","));
        }
    }

//    static private int openDialogId = 0;
    static private OpenDirType openType;
    @Override
    protected Dialog onCreateDialog(int id) {
        Map<String, Integer> images = new HashMap<String, Integer>();
        // 下面几句设置各文件类型的图标， 需要你先把图标添加到资源文件夹
        images.put(OpenFileDialog.sRoot, R.drawable.filedialog_root);	// 根目录图标
        images.put(OpenFileDialog.sParent, R.drawable.filedialog_folder_up);	//返回上一层的图标
        images.put(OpenFileDialog.sFolder, R.drawable.filedialog_folder);	//文件夹图标
        images.put("js", R.drawable.filedialog_file);
        images.put("txt", R.drawable.filedialog_file);	//wav文件图标
        images.put("so", R.drawable.filedialog_file);	//wav文件图标
        images.put(OpenFileDialog.sEmpty, R.drawable.filedialog_root);
        if(openType==OpenDirType.OpenFridaJs){
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            otherFragment.txtJsPath.setText(filepath);
                        }
                    },
                    ".js;",
                    images);
            return dialog;
        }
        if(openType==OpenDirType.OpenWhite){
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            dumpFragment.txtWhitePath.setText(filepath);
                        }
                    },
                    ".txt;",
                    images);
            return dialog;
        }
        if(openType==OpenDirType.OpenGadget){
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            otherFragment.txtGadgetPath.setText(filepath);
                        }
                    },
                    ".so;",
                    images);
            return dialog;
        }
        if(openType==OpenDirType.OpenGadgetArm64){
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            otherFragment.txtGadgetArm64Path.setText(filepath);
                        }
                    },
                    ".so;",
                    images);
            return dialog;
        }
        if(openType==OpenDirType.OpenSo){
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            if(injectFragment.txtSoList.getText().toString().isEmpty()){
                                injectFragment.txtSoList.setText(filepath);
                            }else{
                                injectFragment.txtSoList.setText(injectFragment.txtSoList.getText().toString()+"\n"+filepath);
                            }
                        }
                    },
                    ".so;",
                    images);
            return dialog;
        }
        if(openType==OpenDirType.OpenDex){
            Dialog dialog = OpenFileDialog.createDialog(id, this, "打开文件", new CallbackBundle() {
                        @Override
                        public void callback(Bundle bundle) {
                            String filepath = bundle.getString("path");
                            if(injectFragment.txtDexList.getText().toString().isEmpty()){
                                injectFragment.txtDexList.setText(filepath);
                            }else{
                                injectFragment.txtDexList.setText(injectFragment.txtDexList.getText().toString()+"\n"+filepath);
                            }
                        }
                    },
                    ".dex;",
                    images);
            return dialog;
        }
        return null;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if(bundle!=null){
                AppInfo appInfo = (AppInfo)bundle.getParcelable("appData");
                if(appInfo!=null){
                    workAppFragment.txtAppName.setText(appInfo.getAppName());
                    workAppFragment.txtPackageName.setText(appInfo.getPackageName());
                    curAppInfo=appInfo;
                }
            }
        }
    }

    @Override
    public void onWorkAppAttach() {
        initWorkApp=true;
        workAppFragment.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workAppFragment.txtPackageName.getText().toString().length()<=0){
                    AlertDialog.Builder ab=new AlertDialog.Builder(EditPackageActivity.this);  //(普通消息框)
                    ab.setTitle("提示");  //设置标题
                    ab.setMessage("请选择应用");//设置消息内容
                    ab.show();//显示弹出框
                    return;
                }
                if(packageNames.contains(workAppFragment.txtPackageName.getText().toString())){
                    AlertDialog.Builder ab=new AlertDialog.Builder(EditPackageActivity.this);  //(普通消息框)
                    ab.setTitle("提示");  //设置标题
                    ab.setMessage("已添加过该项目,请直接编辑或者删除后再重新添加");//设置消息内容
                    ab.show();//显示弹出框
                    return;
                }
                PackageItem item=new PackageItem();
                if(packageData!=null){
                    item=packageData;
                }
                item.packageName=workAppFragment.txtPackageName.getText().toString();
                item.appName=workAppFragment.txtAppName.getText().toString();
                if(initDump){
                    item.breakClass=dumpFragment.txtBreakClass.getText().toString();
                    item.isTuoke=dumpFragment.swTuoke.isChecked();
                    item.isDeep=dumpFragment.swDeep.isChecked();
                    item.whiteClass=dumpFragment.txtWhiteClass.getText().toString();
                    item.whitePath=dumpFragment.txtWhitePath.getText().toString().trim().replace("\n","");
                }

                if(initOther){
                    item.sleepNativeMethod=otherFragment.txtSleepNativeMethod.getText().toString();
                    item.traceMethod=otherFragment.txtSmaliTrace.getText().toString();
                    item.fridaJsPath=otherFragment.txtJsPath.getText().toString().trim().replace("\n","");
                    item.port=Integer.valueOf(otherFragment.txtPort.getText().toString().trim()).intValue();
                    item.gadgetPath=otherFragment.txtGadgetPath.getText().toString();
                    item.gadgetArm64Path=otherFragment.txtGadgetArm64Path.getText().toString();

                }
                if(initInject){
                    item.soPath=injectFragment.txtSoList.getText().toString();
                    item.dexPath=injectFragment.txtDexList.getText().toString();
                    item.isDobby=injectFragment.swInjectDobby.isChecked();
                    item.dexClassName=injectFragment.txtDexClassName.getText().toString();
                }
                if(initRomLog){
                    item.isInvokePrint=romLogFragment.swInvokePrint.isChecked();
                    item.isRegisterNativePrint=romLogFragment.swRegisterNativePrint.isChecked();
                    item.isJNIMethodPrint=romLogFragment.swJNIMethodPrint.isChecked();
                }

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("packageData", item);
                intent.putExtras(bundle);
                setResult(1,intent);
                finish();
            }
        });

        workAppFragment.btnSelectPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditPackageActivity.this , AppsActivity.class);
                startActivityForResult(i,1);
            }
        });

        if(opdata!=null){
            if(opdata.equals("add")){
                //添加新项目
            }else if(opdata.equals("edit")){
                if(packageData!=null){
                    workAppFragment.txtPackageName.setText(packageData.packageName);
                    workAppFragment.txtAppName.setText(packageData.appName);
                }
            }
        }
    }


    @Override
    public void onDumpAttach() {
        initDump=true;
        dumpFragment.btnSelectWhitePath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openType=OpenDirType.OpenWhite;
                showDialog(openType.ordinal());
            }
        });

        if(opdata!=null&&opdata.equals("edit")){
            dumpFragment.txtBreakClass.setText(packageData.breakClass);
            dumpFragment.swDeep.setChecked(packageData.isDeep);
            dumpFragment.swTuoke.setChecked(packageData.isTuoke);
            dumpFragment.txtWhiteClass.setText(packageData.whiteClass);
            dumpFragment.txtWhitePath.setText(packageData.whitePath);
        }
    }

    @Override
    public void onOtherAttach() {
        initOther=true;
        otherFragment.btnSelectFridaJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openType=OpenDirType.OpenFridaJs;
                showDialog(openType.ordinal());
            }
        });
        otherFragment.btnSelectGadget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openType=OpenDirType.OpenGadget;
                showDialog(openType.ordinal());
            }
        });

        otherFragment.btnSelectGadgetArm64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openType=OpenDirType.OpenGadgetArm64;
                showDialog(openType.ordinal());
            }
        });

        otherFragment.btnListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherFragment.txtJsPath.setText("listen");
            }
        });

        otherFragment.btnListenWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherFragment.txtJsPath.setText("listen_wait");
            }
        });

        if(opdata!=null&&opdata.equals("edit")){
            otherFragment.txtSmaliTrace.setText(packageData.traceMethod);
            otherFragment.txtSleepNativeMethod.setText(packageData.sleepNativeMethod);
            otherFragment.txtJsPath.setText(packageData.fridaJsPath);
            otherFragment.txtPort.setText(packageData.port+"");
            otherFragment.txtGadgetPath.setText(packageData.gadgetPath);
            otherFragment.txtGadgetArm64Path.setText(packageData.gadgetArm64Path);
        }
    }

    @Override
    public void onRomLogAttach() {
        initRomLog=true;
        if(opdata!=null&&opdata.equals("edit")){
            romLogFragment.swInvokePrint.setChecked(packageData.isInvokePrint);
            romLogFragment.swRegisterNativePrint.setChecked(packageData.isRegisterNativePrint);
            romLogFragment.swJNIMethodPrint.setChecked(packageData.isJNIMethodPrint);
        }
    }

    @Override
    public void onRomInjectAttach() {
        initInject=true;
        injectFragment.btnSelectSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openType=OpenDirType.OpenSo;
                showDialog(openType.ordinal());
            }
        });

        injectFragment.btnSelectDex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openType=OpenDirType.OpenDex;
                showDialog(openType.ordinal());
            }
        });

        injectFragment.btnClearSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                injectFragment.txtSoList.setText("");
            }
        });

        injectFragment.btnClearDex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                injectFragment.txtDexList.setText("");
            }
        });

        if(opdata!=null&&opdata.equals("edit")){
            Log.e("mik","onRomInjectAttach");
            injectFragment.txtSoList.setText(packageData.soPath);
            injectFragment.txtDexList.setText(packageData.dexPath);
            injectFragment.swInjectDobby.setChecked(packageData.isDobby);
            injectFragment.txtDexClassName.setText(packageData.dexClassName);
        }
    }
}
