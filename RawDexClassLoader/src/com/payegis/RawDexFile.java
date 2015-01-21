
package com.payegis;

public class RawDexFile {
    private long mCookie;
    private String mDexName;

    private RawDexFile(byte[] rawDex, String dexName, int flags) {
        mCookie = openRawDexFile(rawDex, dexName, 0);
        mDexName = dexName;
    }

    public Class loadClass(String name, ClassLoader loader) {
        String slashName = name.replace('.', '/');
        return loadClassBinaryName(slashName, loader);
    }

    // 加载一个类名形如com/hello/world的类
    public Class loadClassBinaryName(String name, ClassLoader loader) {
        return defineClass(name, loader, mCookie);
    }

    static public RawDexFile loadDex(byte[] rawDex, String dexName, int flags) {
        return new RawDexFile(rawDex, dexName, flags);
    }
    
    private static Class defineClass(String name, ClassLoader loader, long cookie) {
        Class result = null;
        try {
            result = defineClassNative(name, loader, cookie);
        } catch (ClassNotFoundException | NoClassDefFoundError e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void close() {
        closeDexFile(mCookie);
    }
    
    public String[] getLoadedClassName()
    {
        return getClassNameList(mCookie);
    }

    static {
        System.loadLibrary("egis");
    }

    private static native long openRawDexFile(byte[] rawDex, String dexName, int flags);

    private static native void closeDexFile(long cookie);

    private static native Class<?> defineClassNative(String name, ClassLoader loader, long cookie)
            throws ClassNotFoundException, NoClassDefFoundError;

    private static native String[] getClassNameList(long cookie);
}
