package com.daibing.httpclient;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author daibing
 * @since 2019/5/20
 */
public class HttpClientTest {

    @Test
    public void testHttpGet() throws IOException {
        // 1.创建httpclient客户端
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 2.创建httpGet请求
            HttpGet httpGet = new HttpGet("http://httpbin.org/get");
            System.out.println("Executing request " + httpGet.getRequestLine());
            // 3.创建自定义响应处理程序,ResponseHandler封装了生成响应对象的过程
            ResponseHandler<String> responseHandler = response -> {
                // 4.获取响应的状态码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    // 5.获取此响应的消息实体
                    HttpEntity entity = response.getEntity();
                    // 6.读取实体的内容并将其作为String返回
                    return entity == null ? null : EntityUtils.toString(entity, StandardCharsets.UTF_8);
                } else {
                    throw new ClientProtocolException("unexpected response status: " + statusCode);
                }
            };
            // 7.使用给定的上下文执行HTTP请求,responseHandler会自动释放连接
            String responseBody = httpClient.execute(httpGet, responseHandler);
            System.out.println("----------------------------------------------");
            System.out.println(responseBody);
        }

    }

    @Test
    public void testHttpPost() throws IOException {
        // 1.创建httpclient客户端
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 2.创建httpGet请求
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            httpPost.setEntity(new StringEntity("hello,world"));
            System.out.println("Executing request " + httpPost.getRequestLine());
            // 3.创建自定义响应处理程序,ResponseHandler封装了生成响应对象的过程
            ResponseHandler<String> responseHandler = response -> {
                // 4.获取响应的状态码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    // 5.获取此响应的消息实体
                    HttpEntity entity = response.getEntity();
                    // 6.读取实体的内容并将其作为String返回
                    return entity == null ? null : EntityUtils.toString(entity, StandardCharsets.UTF_8);
                } else {
                    throw new ClientProtocolException("unexpected response status: " + statusCode);
                }
            };
            // 7.使用给定的上下文执行HTTP请求,responseHandler会自动释放连接
            String responseBody = httpClient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------------");
            System.out.println(responseBody);
        }

    }

    @Test
    public void testHttpForm() throws IOException {
        // 1.创建httpclient客户端
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 2.NameValuePair用作HTTP消息元素的名称 - 值对参数
            List<NameValuePair> form = new ArrayList<>();
            form.add(new BasicNameValuePair("foo", "bar"));
            form.add(new BasicNameValuePair("employee", "bom"));
            // 3.UrlEncodedFormEntity由url编码对列表组成的实体
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(form, Consts.UTF_8);
            // 4.创建httpPost请求
            HttpPost httpPost = new HttpPost("http://httpbin.org/post");
            // 5.设置请求的参数实体
            httpPost.setEntity(formEntity);
            System.out.println("Executing request " + httpPost.getRequestLine());
            //6.创建ResponseHandler生成响应对象
            ResponseHandler<String> responseHandler = response -> {
                // 7.获取响应的状态码
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode >= 200 && statusCode < 300) {
                    // 8.获取响应的实体
                    HttpEntity entity = response.getEntity();
                    return entity == null ? null : EntityUtils.toString(entity, Consts.UTF_8);
                } else {
                    throw new ClientProtocolException("unexpected response status: " + statusCode);
                }
            };
            // 9.执行HTTP请求
            String responseBody = httpClient.execute(httpPost, responseHandler);
            System.out.println(responseBody);
        }

    }
}
