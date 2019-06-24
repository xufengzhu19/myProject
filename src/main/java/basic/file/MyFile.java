package basic.file;

        import utils.HtmlParse;
        import utils.QuotedPrintable;

        import java.io.*;

public class MyFile {
    public static void main(String[] args) throws IOException {
        readFile();
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

    public static void readFile() throws IOException {
        File file = new File("C:\\Users\\xufengzhu\\Desktop\\1561307364.Vfd01I58045M285069.mail.coderbbs.top");//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
//            System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();;
        String content=HtmlParse.parse(str);
        String msg= QuotedPrintable.decode(content.getBytes(),"UTF-8");
        int start=msg.indexOf("验证码：")+4;
        System.out.println(msg.substring(start,start+6));
    }
}
