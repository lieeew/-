package com.leikooo.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import com.alibaba.fastjson2.JSONObject;
import java.io.IOException;


/**
 * @author <a href="https://github.com/lieeew">leikooo</a>
 * @Description
 */
@Slf4j
public class HttpClientUtils {
    public static JSONObject execute(String url, HttpMethod method) {
        HttpRequestBase http = null;
        try {
            HttpClient client = HttpClients.createDefault();
            if (method == HttpMethod.GET) {
                http = new HttpGet(url);
            } else {
                http = new HttpPost(url);
            }
            HttpEntity entity = client.execute(http).getEntity();
            return JSONObject.parseObject(EntityUtils.toString(entity));
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (http != null) {
                http.releaseConnection();
            }
        }
        return null;
    }
}