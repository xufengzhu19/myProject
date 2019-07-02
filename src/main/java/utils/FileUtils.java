package utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class FileUtils {
    public static String readFile(String path) throws IOException {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        File file = new File(path);//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s = bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + " ");//用空格分开
//            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
//            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
        return str;
    }

    public static void createFile(String content, String fileName, String path) throws IOException {
        if (StringUtils.isBlank(content)) {
            return;
        }
        String name = fileName;
        if (StringUtils.isBlank(fileName)) {
            name = String.valueOf(System.currentTimeMillis());
        }
        String pathh = path;
        if (StringUtils.isBlank(path)) {
            pathh = "./";
        }
        pathh = pathh + name;
        File file = new File(pathh);
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(pathh);
        fileWriter.write(content);
        fileWriter.close();
    }

    public static void writeFile(String content, String fileName, String path) throws IOException {
        if (StringUtils.isBlank(content)) {
            return;
        }
        String name = fileName;
        if (StringUtils.isBlank(fileName)) {
            name = String.valueOf(System.currentTimeMillis());
        }
        String pathh = path;
        if (StringUtils.isBlank(path)) {
            pathh = "./";
        }
        pathh = pathh + name;
        File file = new File(pathh);
        if (!file.exists())
            file.createNewFile();
        FileWriter fileWriter = new FileWriter(pathh,true);
        fileWriter.write(content+System.getProperty("line.separator"));
        fileWriter.close();
    }
}
