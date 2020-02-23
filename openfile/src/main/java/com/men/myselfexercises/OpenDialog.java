package com.men.myselfexercises;



import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OpenDialog {
    public static String sRoot = "/";
    public static String sPath = "..";
    public static String kongPath = "";
    public static String sParent ="..";
    private static String path = sRoot;
    private static int iconId;
    private static ArrayList<Map<String, Object> > mapList;



    public static AlertDialog createDialog(Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setView(new FileSelectView(ctx));
        AlertDialog dialog = builder.create();
        dialog.setTitle("目录");
        return dialog ;
    }

     static class FileSelectView  extends ListView implements AdapterView.OnItemClickListener {



         public FileSelectView (Context context) {
            this(context,null);
        }
        public FileSelectView (Context context, AttributeSet attrs) {
            this(context, attrs,0);
        }
        public FileSelectView (Context context, AttributeSet attrs, int defStyleAttr) {
            this(context, attrs, defStyleAttr,0);
        }
        public FileSelectView (Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            setOnItemClickListener(this);
            onReflashDialog();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String nm = (String) mapList.get(position).get("name");
            String ph = (String) mapList.get(position).get("path");
            if (nm.equals(sRoot) || nm.equals(sParent)) {
                String parentPath = new File(ph).getParent();
                if (parentPath != null) {
                    path = parentPath;
                } else {
                    path = sRoot;
                }
            } else {
                File fh = new File(ph);
                if (fh.isFile()) {

                } else if(fh.isDirectory()) {
                    path=ph;
                }
            }

            onReflashDialog();
        }

        public  void onReflashDialog() {
            File file =null;
            file = new File(path);
            File[] files = file.listFiles();

            if (mapList == null) {
                mapList = new ArrayList<>(files.length);
            } else {
                mapList.clear();
            }


            if (!path.equals(sRoot)) {
                Map<String, Object> map = new HashMap();
                map.put("name",sRoot);
                map.put("path", sRoot);
                map.put("icon", null);
                mapList.add(map);

                map = new HashMap();
                map.put("name", sParent);
                map.put("path", path);
                map.put("icon", null);
                mapList.add(map);
            }

           ArrayList<Map<String,Object>> flist  = new ArrayList<>();
           ArrayList<Map<String,Object>> dlist  = new ArrayList<>();
            for (File myfile:files ) {
                if (myfile.isDirectory()&&myfile.listFiles()!=null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("name", myfile.getName());
                    map.put("path", myfile.getPath());
                    map.put("icon", iconId);
                    dlist.add(map);
                } else if (myfile.isFile()){
                    Map<String, Object> map = new HashMap<String, Object>();
                    String suffix = getSuffix(file.getName());
                    map.put("name", myfile.getName());
                    map.put("path", myfile.getPath());
                    map.put("icon", ".wav");
                    flist.add(map);
                }
            }

            mapList.addAll(dlist);
            mapList.addAll(flist);
            SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),
                    mapList,
                    R.layout.list_file_item,
                    new String[]{"name", "path", "icon"},
                    new int[]{R.id.item_name,R.id.item_path,R.id.item_icon}
            );
            this.setAdapter(simpleAdapter);
    }

    }

    private static String getSuffix(String name) {
        int index = name.lastIndexOf(".");
        return name.substring(index + 1);
    }
}
