package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static config.PropertyConfig.*;

public class DBHelper {

    private volatile static Connection conn;
    public static PreparedStatement pst;

    private DBHelper() {
    }

    public static Connection getConnection() {
        if (null == conn) {
            synchronized (DBHelper.class) {
                if (null == conn) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");//指定连接类型
                        conn = DriverManager.getConnection(Mysql_Url, Mysql_User, Mysql_Pwd);//获取连接

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conn;
    }

    public static PreparedStatement getPst(String sql) {
        try {
            pst = getConnection().prepareStatement(sql);//准备执行语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pst;
    }

    public static void close() {
        try {
            conn.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    static String sql = null;
//    static DBHelper db1 = null;
//    static ResultSet ret = null;
//    public static void main(String[] args) {
////        sql = "select * from mail_code";//SQL语句
//        sql = "insert into mail_code (user,code) values(1,2)";//SQL语句
//        db1 = new DBHelper(sql);//创建DBHelper对象
//
//        try {
//            db1.pst.execute();//执行语句，得到结果集
////            while (ret.next()) {
////                String uid = ret.getString(1);
////                String ufname = ret.getString(2);
////                String ulname = ret.getString(3);
////                String udate = ret.getString(4);
////                System.out.println(uid + "\t" + ufname + "\t" + ulname + "\t" + udate );
////            }//显示数据
////            ret.close();
//            db1.close();//关闭连接
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
