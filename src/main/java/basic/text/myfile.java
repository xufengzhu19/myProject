package basic.text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class myfile {
    public static void main(String[] args) throws IOException {
//        String content="您叫什么," +
//                "你叫什么," +
//                "提供下名字," +
//                "提供下姓名," +
//                "您的名字," +
//                "叫什么名字";
        String content="请提供您的姓名," +
                "麻烦您提供下," +
                "请问您贵姓," +
                "请问怎么称呼您," +
                "请问您叫什么名字," +
                "请问您的名字," +
                "请您提供下名字," +
                "麻烦您提供下名字";
        createTxT(content);
    }

    public static void createTxT(String content) throws IOException {
        String[] strings = content.split(",");
        for (int i=0;i<strings.length;i++) {
//            String path = "E:\\文档\\资料\\AI\\HanLP\\data\\test\\客服情感分析\\负面\\neg."+i+".txt";
            String path = "E:\\文档\\资料\\AI\\HanLP\\data\\test\\客服情感分析\\正面\\pos."+i+".txt";
            File file = new File(path);
            file.getName();
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(strings[i]);
            fileWriter.close();
        }
    }
}
