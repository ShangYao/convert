package com.jinanlongen.sparrow.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

public class Httptest {
  public static Boolean post(byte[] file, String postUrl) {
    boolean result = false;
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    try {
      httpClient = HttpClients.createDefault(); // 设置http请求的路径
      // HttpPost httpPost = new HttpPost("http://192.168.200.71:8888/test/tt.jpg");
      HttpPost httpPost = new HttpPost(postUrl);
      httpPost.setHeader(new BasicHeader("Accept-Language", "zh-cn")); // 把文件转换成流对象FileBody
      // FileBody bin = new FileBody(file);
      @SuppressWarnings("deprecation")
      HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8"))
          // .addPart("file", bin)
          .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
          // .addBinaryBody(URLEncoder.encode("filename"), file)
          .addBinaryBody(URLEncoder.encode("filename"), file).build();
      httpPost.setEntity(reqEntity); // 发起请求 并返回请求的响应
      response = httpClient.execute(httpPost);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static boolean delete(String deleteUrl) {
    boolean result = false;
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse response = null;
    try {
      httpClient = HttpClients.createDefault(); // 设置http请求的路径
      HttpDelete delete = new HttpDelete(deleteUrl);
      // delete.setHeader(new BasicHeader("Accept-Language", "zh-cn")); // 把文件转换成流对象FileBody
      response = httpClient.execute(delete);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (response != null) {
          response.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

}
