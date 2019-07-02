package utils;

import config.PropertyConfig;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public class MailUtils {
    public static void receiveMail() throws Exception {
        Properties props = new Properties();

//        props.put("mail.transport.protocol", "smtp");//指定邮件发送协议  只接受邮件是可以不要写的
        props.put("mail.store.protocol", "pop3");    //指定邮件接收协议
//        props.put("mail.smtp.class", "com.sun.mail.smtp.SMTPTransport");//指定支持SMTP协议的Transport具体类，允许由第三方提供
//        props.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");    //指定支持IMAP协议的Store具体类，允许由第三方提供
        //以上这4个可以全部写上
//        props.put("mail.pop.host", "smtp.qq.com");//指定采用SMTP协议的邮件发送服务器的IP地址或主机名
        props.put("mail.pop3.host", "mail.coderbbs.top");//指定采用SMTP协议的邮件发送服务器的IP地址或主机名
//        props.put("mail.smtp.auth", "true");//指定采用SMTP协议的邮件发送服务器的IP地址或主机名

        //注意你的服务器的地址
        //网易一共有这三个地址   pop.163.com  imap.163.com  stmp.163.com

        Session session = Session.getInstance(props);// 设置环境信息
        session.setDebug(true);
        Store store = session.getStore("pop3");//指定接收邮件协议

//        store.connect("pop.163.com", "***@163.com", "*********");
        //            (邮箱的服务器地址)   账号  密码
        store.connect(PropertyConfig.Mail_User, PropertyConfig.Mail_Pass);
        //imap
//        store.connect( Mail_QQ_User, Mail_QQ_Pass_Imap);
        //pop3
//        store.connect( Mail_QQ_User, Mail_QQ_Pass_Pop);

        //获得名为默认"inbox"的邮件夹当你自己有定义其他的邮件夹也可以写上去
        Folder folder = store.getFolder("inbox");

        //打开邮件夹
        folder.open(Folder.READ_ONLY);//它是一个邮件文件夹类。Folder类有两个常见的属性，READ_ONLY表示只读，READ_WRITE表示其内容可读可写

        //获得邮件夹中的邮件数目
        System.out.println("你一共有:" + folder.getMessageCount() + " 封邮件");
        //获得邮件夹中的未读邮件数目

        System.out.println("你一共有:" + folder.getUnreadMessageCount() + " 未读邮件");
        System.out.println("你一共删除了 " + folder.getDeletedMessageCount() + " 封邮件");

        for (int i = 1; i <= folder.getMessageCount(); i++) {

            Message msg = folder.getMessage(i);
            System.out.println("========================================");
            //获得邮件的发送者、主题和正文
            if (msg.getFrom()[0].toString().contains("<")) {
                System.out.println("邮件来自:" + msg.getFrom()[0].toString().substring(msg.getFrom()[0].toString().lastIndexOf("<") + 1, msg.getFrom()[0].toString().lastIndexOf(">")));
            } else {
                System.out.println("邮件来自:" + msg.getFrom()[0]);
            }
            System.out.println("邮件主题:" + msg.getSubject());
            System.out.println("发送日期:" + msg.getSentDate());
            String type = msg.getContentType().toString().substring(0, msg.getContentType().toString().indexOf(";"));
            System.out.println("邮件类型:" + type);
            System.out.println("邮件内容:" + msg.getContentType().toString());//multipart  当文件类型为multipart/* 时不能正确显示
            if (type.equals("text/html")) {
                //请你根据文件的类型来更改文件的解析方式  text/html  multipart/alternative表示复合类型
            }
            InternetAddress[] address = (InternetAddress[]) msg.getFrom();
            String from = address[0].getAddress();//这个是发邮件人的地址
            if (from == null) {
                from = "";
            }
            String personal = address[0].getPersonal();//这个是发邮件的人的姓名
            if (personal == null) {
                personal = "";
            }
            String fromaddr = personal + "<" + from + ">";
            System.out.println("邮件作者：" + fromaddr);
            System.out.println("========================================\r\n");
        }

    }
}
