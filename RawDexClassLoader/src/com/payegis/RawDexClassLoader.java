
package com.payegis;

import java.io.File;

public class RawDexClassLoader extends ClassLoader {
    private final ClassLoader definingContext;
    private final RawDexFile mDex;
    private final String mRawLibPath;
    private String[] mLibPaths;

    public RawDexClassLoader(byte[] rawDex, String dexName, String libraryPath, ClassLoader parent) {
        super(parent);
        mDex = loadRawDex(rawDex, dexName);
        mRawLibPath = libraryPath;
        definingContext = parent;
        setNativeLibrary(libraryPath);
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        // TODO Auto-generated method stub
        Class clazz = null;
        if (mDex != null) {
            String slashName = className.replace('.', '/');
            clazz = mDex.loadClass(slashName, this);
        }

        return clazz;
    }

    public String findLibrary(String libraryName) {
        String fileName = System.mapLibraryName(libraryName);
        for (int i = 0; i < mLibPaths.length; i++) {
            String pathName = mLibPaths[i] + fileName;
            File test = new File(pathName);

            if (test.exists()) {
                return pathName;
            }
        }
        return null;
    }
    
    public String[] getLoadedClasses() {
        return mDex.getLoadedClassName();
    }
    
    @SuppressWarnings("unused")
    private RawDexFile loadRawDex(byte[] rawDex, String dexName) {
        return RawDexFile.loadDex(rawDex, dexName, 0);
    }

    private void setNativeLibrary(String libraryPath) {

        String pathList = System.getProperty("java.library.path", ".");
        String pathSep = System.getProperty("path.separator", ":");
        String fileSep = System.getProperty("file.separator", "/");
        
        if (mRawLibPath != null) {
            if (pathList.length() > 0) {
                pathList += pathSep + mRawLibPath;
            }
            else {
                pathList = mRawLibPath;
            }
        }

        mLibPaths = pathList.split(pathSep);

        // Add a '/' to the end so we don't have to do the property lookup
        // and concatenation later.
        for (int i = 0; i < mLibPaths.length; i++) {
            if (!mLibPaths[i].endsWith(fileSep)) {
                mLibPaths[i] += fileSep;
            }
        }
    }
    
}
