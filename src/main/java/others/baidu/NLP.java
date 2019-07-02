package others.baidu;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

import java.util.HashMap;

import static config.PropertyConfig.*;


public class NLP {
    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(Baidu_APP_ID, Baidu_API_KEY, Baidu_SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();

        // 调用接口
//        String text = "百度是一家高科技公司";
        //语义分析
//        JSONObject res = client.lexer(text, null);
        //语法分析
//        JSONObject res = client.lexerCustom(text, null);


        // 词义相似度
//        options.put("mode", 0);
//        String word1 = "北京";
//        String word2 = "京城";
//        JSONObject res = client.wordSimEmbedding(word1, word2, options);

        //短文本相似度
        options.put("models", "CNN");
        String text1 = "万达普惠借钱靠谱吗";
        String text2 = "万达普惠合法吗?";
        JSONObject res = client.simnet(text1, text2, options);



        System.out.println(res.toString(2));
    }


}
