/**
 * 
 */
package com.propn.golf.rest.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 * @author Administrator
 *
 */
public class Client {

	private static final int BUFFER_SIZE = 16 * 1024;

	/**
	 * post请求 ，超时默认10秒
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */

	public static String post(String url, Map<String, String> params)
			throws IOException {
		return post(url, params, 10);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @param timeout
	 *            超时时间，秒
	 * @return
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> params,
			int timeout, Object... objects) throws IOException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionTimeToLive(timeout, TimeUnit.SECONDS);

		HttpClient httpclient = httpClientBuilder.build();
		// 100-Continue handshake,HTTP/1.1 protocol
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);
		String retVal = "";
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					formparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					HTTP.UTF_8);
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(entity);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			if (objects == null || objects.length == 0) {
				retVal = new String(httpclient.execute(httppost,
						responseHandler).getBytes(HTTP.ISO_8859_1), HTTP.UTF_8);
			} else {
				retVal = new String(httpclient.execute(httppost,
						responseHandler).getBytes(), HTTP.UTF_8);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static String get(String url, Map<String, String> params)
			throws IOException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpClient httpclient = httpClientBuilder.build();
		httpclient.getParams().setIntParameter("http.socket.timeout", 100000);
		String retVal = "";
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, HTTP.UTF_8);
			if (paramstr == null || paramstr.length() == 0) {

			} else {
				url = url + "?" + paramstr;
			}

			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			retVal = httpclient.execute(httpget, responseHandler);
		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * 异步get
	 * 
	 * @param url
	 * @param params
	 * @throws Exception
	 */
	public void asynGet(String url, Map<String, String> params)
			throws Exception {
		/*
		 * HttpClientConnection conn = null; try { URI uri = new URI(url);
		 * String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
		 * String host = uri.getHost() == null ? "localhost" : uri.getHost();
		 * int port = uri.getPort(); if (port == -1) { if
		 * (scheme.equalsIgnoreCase("http")) { port = 80; } else if
		 * (scheme.equalsIgnoreCase("https")) { port = 443; } }
		 * 
		 * conn = new HttpClientConnection(host, port); GetRequest request =
		 * null; if (params == null) { request = new GetRequest(url); } else {
		 * org.xlightweb.NameValuePair[] nv = new
		 * org.xlightweb.NameValuePair[params.size()]; int i = 0; for
		 * (Map.Entry<String, String> param : params.entrySet()) { nv[i] = new
		 * org.xlightweb.NameValuePair(param.getKey(),
		 * URLEncoder.encode(param.getValue(), "UTF-8")); i ++; } request = new
		 * GetRequest(url, nv); } conn.send(request); } catch (Exception e) {
		 * throw e; } finally { conn.close(); }
		 */
	}

	/**
	 * 得到访问url的返回状态(200正常)
	 * 
	 * @param url
	 *            :访问地址
	 * @param timeOut
	 *            :设置超时时间秒
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static int getPostRetStatu(String url, Integer timeOut) {
		HttpResponse response = null;
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

			HttpClient httpclient = httpClientBuilder.build();
			httpclient.getParams().setIntParameter("http.socket.timeout",
					timeOut * 1000);
			httpclient.getParams().setBooleanParameter(
					"http.protocol.expect-continue", false);
			/**
			 * 此处使用get请求,因为一些网站屏蔽了post请求eg:baidu
			 */
			// HttpPost httpost = new HttpPost(url);
			HttpGet httpget = new HttpGet(url);
			response = httpclient.execute(httpget);
		} catch (Exception e) {
			// e.printStackTrace();
			return 404;
		}
		return response.getStatusLine().getStatusCode();
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return 文件
	 * @throws IOException
	 */
	public static File get(String url, Map<String, String> params,
			String fileName) throws IOException {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		HttpClient httpclient = httpClientBuilder.build();
		httpclient.getParams().setIntParameter("http.socket.timeout", 100000);
		File file = new File(fileName);
		InputStream in = null;
		OutputStream out = null;
		try {
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			if (params != null) {
				for (Map.Entry<String, String> param : params.entrySet()) {
					qparams.add(new BasicNameValuePair(param.getKey(), param
							.getValue()));
				}
			}
			String paramstr = URLEncodedUtils.format(qparams, HTTP.UTF_8);
			if (paramstr == null || paramstr.length() == 0) {

			} else {
				url = url + "?" + paramstr;
			}

			HttpGet httpget = new HttpGet(url);
			HttpResponse httpResponse = httpclient.execute(httpget);
			in = httpResponse.getEntity().getContent();
			if (in == null) {
				return null;
			}
			out = new BufferedOutputStream(new FileOutputStream(file),
					BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
			httpclient.getConnectionManager().shutdown();
		}
		return file;
	}

	/**
	 * @Description: post请求，支持文件上传
	 * @param url
	 * @param params
	 * @param timeout
	 * @param file
	 * @return
	 * @throws IOException
	 * @author Alvin.zengqi
	 * @date 2011-11-17 下午06:14:16
	 */
	public static String postFile(String url, Map<String, String> params,
			int timeout, File file) throws IOException {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpClient httpclient = httpClientBuilder.build();
		httpclient.getParams().setIntParameter("http.socket.timeout",
				timeout * 1000);
		httpclient.getParams().setBooleanParameter(
				"http.protocol.expect-continue", false);

		String retVal = "";
		try {

			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder
					.create();

			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(multipartEntityBuilder.build());
			for (Map.Entry<String, String> param : params.entrySet()) {
				multipartEntityBuilder.addTextBody(param.getKey(),
						param.getValue());
			}
			if (file != null && file.exists()) {
				multipartEntityBuilder.addBinaryBody("file", file);
			}

			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			retVal = new String(httpclient.execute(httppost, responseHandler)
					.getBytes(HTTP.ISO_8859_1), HTTP.UTF_8);

		} catch (IOException e) {
			throw e;
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return retVal;
	}

	/**
	 * @Description:获取参数路径
	 * @param params
	 * @return
	 * @author Alvin.zengqi
	 * @date 2011-6-27 下午08:55:16
	 */
	public static String getParamsPath(Map<String, String> params) {
		String url = "";
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// String paramstr = "";
		if (params != null) {
			for (Map.Entry<String, String> param : params.entrySet()) {
				qparams.add(new BasicNameValuePair(param.getKey(), param
						.getValue()));
				// paramstr = paramstr+param.getKey()+"="+param.getValue()+"&";
			}
			// paramstr = paramstr + "randomCode="+new Date().toString();
		}
		String paramstr = URLEncodedUtils.format(qparams, HTTP.UTF_8);
		if (paramstr == null || paramstr.length() == 0) {

		} else {
			url = url + "?" + paramstr;
		}

		return url;
	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		/*
		 * System.out.println("begin.."); int retStatu =
		 * HttpUtils.getPostRetStatu
		 * ("http://localhost:8088/marketing/backStageLogin.jsp", 10); int
		 * numOut = 5; int i = 0; if(retStatu!=200){ if(++i==numOut){
		 * System.out.println("调用发邮件/短信的接口"); } }
		 */
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "曾琪");
		params.put("age", "10");
		System.out.println(getParamsPath(params));
	}

}
