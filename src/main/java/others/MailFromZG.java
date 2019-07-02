package others;

import config.DBHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MailFromZG extends FileAlterationListenerAdaptor {
    private static final Logger log = LoggerFactory.getLogger(MailFromZG.class);

    public static void main(String[] args) throws Exception {
//        createIds();
        execMonitor();
    }

    private static void execMonitor() throws Exception {
        // 监控目录
        String rootDir = "C:\\Users\\xufengzhu\\Desktop\\tmp";
        // 轮询间隔 5 秒
        long interval = TimeUnit.SECONDS.toMillis(1);
        // 创建过滤器
        IOFileFilter directories = FileFilterUtils.and(
                FileFilterUtils.directoryFileFilter(),
                HiddenFileFilter.VISIBLE);
        IOFileFilter files = FileFilterUtils.and(
                FileFilterUtils.fileFileFilter());
//                FileFilterUtils.suffixFileFilter(".txt"));
        IOFileFilter filter = FileFilterUtils.or(directories, files);
        // 使用过滤器
        FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir), filter);
        //不使用过滤器
        //FileAlterationObserver observer = new FileAlterationObserver(new File(rootDir));
        observer.addListener(new MailFromZG());
        //创建文件变化监听器
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        // 开始监控
        monitor.start();
    }

    @Override
    public void onFileCreate(File file) {
        log.info("[新建]:" + file.getAbsolutePath());
        if (file.getAbsolutePath().contains("mail")) {
            log.info("receive new mail!");
            getCode(file.getAbsolutePath());
        }
    }

    public static void getCode(String file) {
        try {
            String msgs = FileUtils.readFile(file);
            String content = HtmlParse.parse(msgs);
            String msg = QuotedPrintable.decode(content.getBytes(), "UTF-8");
            int start = msg.indexOf("验证码：") + "验证码：".length();
            String code = msg.substring(start, start + 6).trim();
            int ustart = msgs.indexOf("X-Original-To: ") + "X-Original-To: ".length();
            int uend = msgs.indexOf("Delivered-To:");
            String user = msgs.substring(ustart, uend).trim();
            log.info("parse code={},user={}", code, user);
            String sql = "update mail_code set code='" + code + "' where user='" + user + "'";//SQL语句
            log.info("update sql={}", sql);
            DBHelper.getPst(sql).executeUpdate();
//            DBHelper.close();//关闭连接
        } catch (Exception e) {
            log.error("get code error!", e);
        }
    }

    private static void createIds() throws IOException {
        Set<String> set = new HashSet<>();
        while (CollectionUtils.isEmpty(set) || set.size() < 1000) {
            set.clear();
            for (int i = 0; i < 1006; i++) {
                set.add(IdUtils.getOrderIdByUUId(6));
            }
        }
        System.out.println(set.size());
        String ids = set.toString().replace("[", "")
                .replace("]", "")
                .replace(",", " ");

        FileUtils.writeFile(ids, "user.txt", "C:\\Users\\xufengzhu\\Desktop\\");
    }
}

