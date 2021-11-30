package com.mik.mikmanager.Common;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FileHelper {

    private static String TAG = "FileHelper";

    public static String getSDPath(Context context) {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);// 判断sd卡是否存在
        if (sdCardExist) {
            if (Build.VERSION.SDK_INT >= 29) {
                //Android10之后
                sdDir = context.getExternalFilesDir(null);//获取应用所在根目录/Android/data/your.app.name/file/ 也可以根据沙盒机制传入自己想传的参数，存放在指定目录
            } else {
                sdDir = Environment.getExternalStorageDirectory();// 获取SD卡根目录
            }
        } else {
            sdDir = Environment.getRootDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }

    private static String makedir(Context context,String path) {
        String sdPath = getSDPath(context);
        String[] dirs = path.replace(sdPath, "").split("/");
        StringBuffer filePath = new StringBuffer(sdPath);
        for (String dir : dirs) {
            if (!"".equals(dir) && !dir.equals(sdPath)) {
                filePath.append("/").append(dir);
                File destDir = new File(filePath.toString());
                if (!destDir.exists()) {
                    boolean b = destDir.mkdirs();
                    if (!b) {
                        return null;
                    }
                }
            }
        }
        return filePath.toString();
    }

    public static String mkdirs(Context context,String path) {
        String sdcard = getSDPath(context);
        if (path.indexOf(getSDPath(context)) == -1) {
            path = sdcard + (path.indexOf("/") == 0 ? "" : "/") + path;
        }
        File destDir = new File(path);
        if (!destDir.exists()) {
            path = makedir(context,path);
            if (path == null) {
                return null;
            }
        }
        return path;
    }

    //在SD卡上创建一个文件夹
    public static void createSDCardDir(String path){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            // 创建一个文件夹对象，赋值为外部存储器的目录
            Log.d("FileHelper", "createSDCardDir "+path);
            File sdcardDir =Environment.getExternalStorageDirectory();
            //得到一个路径，内容是sdcard的文件夹路径和名字
            File path1 = new File(path);
            if (!path1.exists()) {
                //若不存在，创建目录，可以在应用启动的时候创建
                boolean isok=path1.mkdirs();
                Log.d("FileHelper", "createSDCardDir "+path+" "+isok);
            }
        }
    }

    public static void SaveMikromConfig(List<PackageItem> packageList){
        Gson gson = new Gson();
        String savejson=gson.toJson(packageList);
        FileHelper.deleteFile("/sdcard/mikrom/config/mikrom.confg");
        FileHelper.writeTxtToFile(savejson,"/sdcard/mikrom/config/","mikrom.config");
    }


    // 将字符串写入到文本文件中
    // 在文件末尾追加内容
    public static void writeTxtToFile(String strcontent, String filePath, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(filePath, fileName);

        String strFilePath = filePath+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\n";  // \r\n 结尾会变成 ^M
        /*
        * 不同系统，有不同的换行符号：
        在windows下的文本文件的每一行结尾，都有一个回车('\n')和换行('\r')
        在linux下的文本文件的每一行结尾，只有一个回车('\n');
        在Mac下的文本文件的每一行结尾，只有一个换行('\r');
        因此：^M出现的原因： 在linux下打开windows编辑过的文件，就会在行末尾显示^M;
        * */
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d(TAG, "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
//            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
//            raf.seek(file.length());
//            raf.write(strContent.getBytes());
//            raf.close();
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.setLength(0);

            // 写文件的位置标记,从文件开头开始,后续读取文件内容从该标记开始
            long writePosition = raf.getFilePointer();
            raf.seek(writePosition);
            raf.write(strContent.getBytes());
            raf.close();
            //
        } catch (Exception e) {
            Log.e(TAG, "Error on write File:" + e);
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            Log.d("FileHelper", "makeRootDirectory "+filePath);
            file = new File(filePath);
            if (!file.exists()) {
                boolean isok= file.mkdir();
                Log.d("FileHelper", "makeRootDirectory "+filePath+" "+isok);
            }
        } catch (Exception e) {
            Log.d("FileHelper", e+"");
        }
    }

    /**
     * 删除单个文件
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static boolean existsFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return true;
        }
        return false;
    }
    /**
     * 删除文件夹以及目录下的文件
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
               //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     *@param filePath 要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public boolean DeleteFolder(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                // 为文件时调用删除文件方法
                return deleteFile(filePath);
            } else {
                // 为目录时调用删除目录方法
                return deleteDirectory(filePath);
            }
        }
    }


    public static String ReadFileAll(String path) {
        File file = new File(path);
        StringBuilder sb=new StringBuilder();
        if (file != null && file.exists()) {
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            try {
                inputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(
                        inputStream));
                String outData;
                while((outData=bufferedReader.readLine())!=null){
                    sb.append(outData+"\n");
                }
            } catch (Throwable t) {
            } finally {
                try {
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     *读取指定 TXT,LOG 文件的内容
     * charsetName UTF8 GBK
     */
    public String getFileContent(String path,String charsetName) {
        File file = new File(path.trim());
        String content  = "";
        if (file.isDirectory() ) {	//检查此路径名的文件是否是一个目录(文件夹)
            Log.i("FileHelper", "The File doesn't not exist " +file.getName().toString()+file.getPath().toString());
        } else if (file.exists()){
            if (file.getName().endsWith(".txt") || file.getName().endsWith(".log")) {//文件格式为txt文件
                try {
                    InputStream instream = new FileInputStream(file);
                    if (instream != null) {
                        InputStreamReader inputreader = new InputStreamReader(instream, charsetName); //UTF8 GBK
                        BufferedReader buffreader = new BufferedReader(inputreader);
                        String line="";
                        //分行读取
                        while (( line = buffreader.readLine()) != null) {
                            content += line + "\n";
                        }
                        instream.close();		//关闭输入流
                    }
                }
                catch (java.io.FileNotFoundException e) {
                    Log.d(TAG, "异常："+e.getMessage());
                }
                catch (IOException e)  {
                    Log.d(TAG, e.getMessage());
                }
            }
        }
        return content ;
    }

    /** 删除文件，可以是文件或文件夹
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
            //Log.i(TAG,  "删除文件失败:" + delFile + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(delFile);
            else
               // Log.i(TAG,  "删除文件失败:" + delFile + " 是目录");
            return false;//deleteDirectory(delFile);
        }
    }

    /** 删除单个文件
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                //Log.e(TAG, "删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
                //Log.i(TAG, "删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
            //Log.i(TAG,  "删除单个文件失败：" + filePath$Name + "不存在！");
            return false;
        }
    }


    private void uploadFile2(String actionUrl,String uploadFile,String newName) {

        String end = "/r/n";
        String Hyphens = "--";
        String boundary = "*****";
        try        {
            URL url = new URL(actionUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            /* 设定传送的method=POST */
            con.setRequestMethod("POST");
            /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);
            /* 设定DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(Hyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; name=\"file1\";filename=\"" + newName + "\"" + end);
            ds.writeBytes(end);
            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
            /* 设定每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            /* 从文件读取数据到缓冲区 */
            while ((length = fStream.read(buffer)) != -1)
            {
                /* 将数据写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens + boundary + Hyphens + end);
            fStream.close();
            ds.flush();
            /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
            System.out.println("上传成功");
           // Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_LONG).show();
            ds.close();
        } catch (Exception e){
            System.out.println("上传失败" + e.getMessage());
            //Toast.makeText(MainActivity.this, "上传失败" + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
