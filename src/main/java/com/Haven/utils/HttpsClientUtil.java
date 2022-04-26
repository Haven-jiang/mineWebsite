package com.Haven.utils;

import com.Haven.DTO.ResponsePackDTO;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpsClientUtil {
    /**
     * 发送get请求
     * @param url 请求URL
     * @param param 请求参数 key:value url携带参数 或者无参可不填
     * @return
     */
    public static ResponsePackDTO doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String content = "";
        int status = 0;
        CloseableHttpResponse response = null;


        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);

            status = response.getStatusLine().getStatusCode();

            if (status == 200 && EntityUtils.toString(response.getEntity()).length() <= 200)
                content = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponsePackDTO(status, content);
    }

    public static ResponsePackDTO doGet(String url) {
        return doGet(url, null);
    }

    public static String doDownloadString(String fileUrl) {
        String content = "";
        HttpURLConnection httpUrl = null;
        char[] buffer = new char[1024];
        Writer writer = new StringWriter();
        int size = 0;

        try {
            URL url = new URL(fileUrl);
            //支持http特定功能
            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.connect();
            //缓存输入流,提供了一个缓存数组,每次调用read的时候会先尝试从缓存区读取数据
//            BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(httpUrl.getInputStream(), StandardCharsets.UTF_8));


            while ((size = br.read(buffer)) != -1) {
                writer.write(buffer, 0, size);
            }
            content = writer.toString();
            //记得及时释放资源
            writer.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 发送post请求
     * @param url 请求URL
     * @param param 请求参数 key:value
     * @return
     */
    public static CloseableHttpResponse doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
//            if (response.getStatusLine().getStatusCode() == 200) resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return response;
    }


    /**
     * 发送post 请求
     * @param url 请求地址
     * @param json 请求参数
     * @return
     */
    public static ResponsePackDTO doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String resultString = "";
        int status = 0;
        CloseableHttpResponse response = null;
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            if (!Objects.isNull(json)) {
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            status = response.getStatusLine().getStatusCode();
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new ResponsePackDTO(status, resultString);
    }
}