package basic.jvm;

import java.io.File;
import java.lang.management.ClassLoadingMXBean;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=80m
 */
public class OOMTest {
    public static void main(String[] args) {
        try {
            URL url=new File("E:\\code").toURI().toURL();
            URL[] urls={url};
            //有关类型加载的JMX接口
            ClassLoadingMXBean loadingMXBean= ManagementFactory.getClassLoadingMXBean();
            //缓存类加载器
            List<ClassLoader> classLoaders=new ArrayList<>();
            while (true){
                ClassLoader classLoader=new URLClassLoader(urls);
                classLoaders.add(classLoader);
                classLoader.loadClass("Student");

                System.out.println("total:"+loadingMXBean.getTotalLoadedClassCount());
                System.out.println("active:"+loadingMXBean.getLoadedClassCount());
                System.out.println("unloaded:"+loadingMXBean.getUnloadedClassCount());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
