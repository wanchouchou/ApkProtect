## RawDexClassLoader简介
---
#### 简介
RawDexClassLoader是自定义封装的ClassLoader，使用方式与DexClassLoader基本类似，不过可以将内存中Dex文件的映射加载进虚拟机。

#### 结构
- src目录，为Java层源代码。其中`DemoActivity.java`为测试用例，与assets下的dex文件配合使用。
- jni目录，为Native本地代码，用于编译动态链接库，源代码后续会放出。
- assets目录，为测试用的dex文件。代码如下：

```Java
public class TestClassLoader {
    private static int a;
    
    public TestClassLoader() {
        // TODO Auto-generated constructor stub
    }
    
    public static void setValue(int value){
        a = value;
    } 
    
    public static int getValue(){
        return a;
    }
}
```

- libs目录，为需要链接的动态链接库。

#### 使用
本项目为Android工程，git clone到本地之后直接导入IDE，作为Android应用运行，如果执行成功的话会在Activity中弹出相应的Toast。
如果需要将源码集成到自己的工程中，可以将动态链接库放到自己工程的libs目录下，将除`DemoActivity.java`中的代码拷贝至自己的工程中，当然除此之外还可以将此工程导出成Jar包进行使用。
#### 更新
2015-1-21：
- 创建项目，更新Java层代码和动态链接库。
