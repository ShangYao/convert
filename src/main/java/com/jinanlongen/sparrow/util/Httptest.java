package com.jinanlongen.sparrow.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

public class Httptest {
	public static void post2() {
		CloseableHttpClient httpClient1 = null;
		CloseableHttpResponse response1 = null;
		try {
			httpClient1 = HttpClients.createDefault(); // 设置http请求的路径
			HttpPost httpPost = new HttpPost("http://192.168.200.71:8888/test/tt.jpg");
			httpPost.setHeader(new BasicHeader("Accept-Language", "zh-cn")); // 把文件转换成流对象FileBody
			File file = new File("D:\\1.jpg");
			// FileBody bin = new FileBody(file);
			@SuppressWarnings("deprecation")
			HttpEntity reqEntity = MultipartEntityBuilder.create().setCharset(Charset.forName("UTF-8"))
					// .addPart("file", bin)
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addBinaryBody(URLEncoder.encode("filename"), file)
					.build();
			httpPost.setEntity(reqEntity); // 发起请求 并返回请求的响应
			System.out.println(httpPost.toString());
			response1 = httpClient1.execute(httpPost);
			// ObjectMapper mapper = new ObjectMapper();

			// WeedfsFile weedfile = mapper.readValue(response1.getEntity().getContent(),
			// WeedfsFile.class);
			// System.out.println(weedfile.getFileName());
			System.out.println("The response value of token:" + response1.getFirstHeader("token")); // 获取响应对象
			// System.out.println(weedfile.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response1 != null) {
					response1.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (httpClient1 != null) {
					httpClient1.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void post(String url, String fileName) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpPost.
			HttpPost httpPost = new HttpPost(url);
			InputStream in = new FileInputStream(fileName);
			HttpEntity entity = MultipartEntityBuilder.create().addBinaryBody(fileName, in).build();
			httpPost.setEntity(entity);
			System.out.println("executing request " + httpPost.getURI());
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				System.out.println("--------------------------------------");
				// 打印响应状态
				System.out.println(response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度
					System.out.println("Response content length: " + entity.getContentLength());
					// 打印响应内容
					System.out.println("Response content: " + EntityUtils.toString(entity));
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}