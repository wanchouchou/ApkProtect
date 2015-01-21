
package com.payegis;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import com.payegis.rawdexclassloader.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runTest();
    }
    
    
    public void runTest() {
        RawDexClassLoader cl = new RawDexClassLoader(getFromAssets("test.dex"), "test.dex", getApplicationInfo().dataDir + "/lib",
                getApplicationContext().getClassLoader());
        Class<?> clazz = null;
        try {
            clazz = cl.loadClass("com.android.TestClassLoader");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, "Load Class Failed!", Toast.LENGTH_LONG).show();
        }

        Toast.makeText(this, clazz.toString(), Toast.LENGTH_LONG).show();    
        
        Method mth;
        try {
            mth = clazz.getMethod("setValue", new Class[]{int.class});
            mth.invoke(null, new Object[]{5});
            
            mth = clazz.getDeclaredMethod("getValue", new Class[]{});
            int result = (int) mth.invoke(null, new Object[]{});
            Toast.makeText(this, "getValue: " + String.valueOf(result), Toast.LENGTH_LONG).show();   
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public byte[] getFromAssets(String filename) {
        byte[] buffer = null;
        try {
            AssetManager am = getAssets();
            InputStream in =  am.open(filename);
            
            int len = in.available();
            buffer = new byte[len];
            in.read(buffer);
            in.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return buffer;
    }
}
