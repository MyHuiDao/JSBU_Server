package com.huidao.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import com.huidao.common.dto.HttpResponseDto;

/**
 * @author tzd
 *
 */
public class HttpUtil {
	public static HttpResponseDto postMap(String url, Map<String, String> paramsMap) {
		return postMap(url, paramsMap, null, true);
	}

	public static HttpResponseDto postMap(String url, Map<String, String> paramsMap, Map<String, String> headerMap) {
		return postMap(url, paramsMap, headerMap, true);
	}

	public static HttpResponseDto postJson(String url, String json) {
		return postText(url, json, "application/json", null, true);
	}

	public static HttpResponseDto postJson(String url, String json, Map<String, String> headerMap) {
		return postText(url, json, "application/json", headerMap, true);
	}

	public static HttpResponseDto postBody(String url, String body, Map<String, String> headerMap) {
		return postText(url, body, "text/xml", headerMap, true);
	}

	public static HttpResponseDto postBody(String url, String body) {
		return postText(url, body, "text/xml", null, true);
	}

	public static HttpResponseDto postByte(String url, String body, Map<String, String> headerMap) {
		return postByte(url, body, headerMap, true);
	}

	public static HttpResponseDto postByte(String url, String body) {
		return postByte(url, body, null, true);
	}
	public static HttpResponseDto get(String url) {
		return get(url, null, true);
	}

	public static HttpResponseDto get(String url, Map<String, String> headerMap) {
		return get(url, headerMap, true);
	}

	public static void getFile(String url, String filePath) {
		getFile(url, filePath, null);
	}

	private static HttpResponseDto postByte(String url, String text, Map<String, String> headerMap,
			boolean retrunHeaders) {
		HttpResponseDto httpResponse = new HttpResponseDto();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https")) {
			httpclient = createSSLClientDefault();
		} else {
			httpclient = HttpClients.createDefault();
		}

		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		try {
			httppost.setEntity(new ByteArrayEntity(text.getBytes()));
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httppost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					httpResponse.setBody(EntityUtils.toString(entity, "UTF-8"));
				}
				if (retrunHeaders) {
					Header[] headers = response.getAllHeaders();
					Map<String, String> responseHeadersMap = new HashMap<String, String>();
					for (int i = 0; i < headers.length; i++) {
						responseHeadersMap.put(headers[i].getName(), headers[i].getValue());
					}
					httpResponse.setHeaders(responseHeadersMap);
				}
				return httpResponse;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
		return null;
	}
	private static HttpResponseDto postMap(String url, Map<String, String> paramsMap, Map<String, String> headerMap,
			boolean retrunHeaders) {
		HttpResponseDto httpResponse = new HttpResponseDto();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https")) {
			httpclient = createSSLClientDefault();
		} else {
			httpclient = HttpClients.createDefault();
		}
		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<NameValuePair> formparams = null;
		if (paramsMap != null && paramsMap.size() > 0) {
			formparams = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httppost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					httpResponse.setBody(EntityUtils.toString(entity, "UTF-8"));
				}
				if (retrunHeaders) {
					Header[] headers = response.getAllHeaders();
					Map<String, String> responseHeadersMap = new HashMap<String, String>();
					for (int i = 0; i < headers.length; i++) {
						responseHeadersMap.put(headers[i].getName(), headers[i].getValue());
					}
					httpResponse.setHeaders(responseHeadersMap);
				}

				return httpResponse;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
		return null;
	}

	private static HttpResponseDto postText(String url, String text, String contentType, Map<String, String> headerMap,
			boolean retrunHeaders) {
		HttpResponseDto httpResponse = new HttpResponseDto();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https")) {
			httpclient = createSSLClientDefault();
		} else {
			httpclient = HttpClients.createDefault();
		}

		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		try {
			StringEntity se = new StringEntity(text, "utf-8");
			se.setContentEncoding("UTF-8");
			se.setContentType(contentType);
			httppost.setEntity(se);
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httppost.setHeader(entry.getKey(), entry.getValue());
				}
			}
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					httpResponse.setBody(EntityUtils.toString(entity, "UTF-8"));
				}
				if (retrunHeaders) {
					Header[] headers = response.getAllHeaders();
					Map<String, String> responseHeadersMap = new HashMap<String, String>();
					for (int i = 0; i < headers.length; i++) {
						responseHeadersMap.put(headers[i].getName(), headers[i].getValue());
					}
					httpResponse.setHeaders(responseHeadersMap);
				}
				return httpResponse;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
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
		return null;
	}

	private static HttpResponseDto get(String url, Map<String, String> headerMap, boolean retrunHeaders) {
		HttpResponseDto httpResponse = new HttpResponseDto();
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https")) {
			httpclient = createSSLClientDefault();
		} else {
			httpclient = HttpClients.createDefault();
		}
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					httpResponse.setBody(EntityUtils.toString(entity, "UTF-8"));
				}
				if (retrunHeaders) {
					Header[] headers = response.getAllHeaders();
					Map<String, String> responseHeadersMap = new HashMap<String, String>();
					for (int i = 0; i < headers.length; i++) {
						responseHeadersMap.put(headers[i].getName(), headers[i].getValue());
					}
					httpResponse.setHeaders(responseHeadersMap);
				}
				return httpResponse;
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
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
		return null;
	}

	/**
	 * 获取文件
	 * 
	 * @param url
	 * @param headerMap
	 * @param retrunHeaders
	 */
	public static void getFile(String url, String filePath, Map<String, String> headerMap) {
		CloseableHttpClient httpclient = null;
		if (url.startsWith("https")) {
			httpclient = createSSLClientDefault();
		} else {
			httpclient = HttpClients.createDefault();
		}
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			if (headerMap != null) {
				for (Entry<String, String> entry : headerMap.entrySet()) {
					httpget.setHeader(entry.getKey(), entry.getValue());
				}
			}
			// 执行get请求.
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					File file = new File(filePath);
					FileOutputStream output = new FileOutputStream(file);

					InputStream content = entity.getContent();

					try {
						byte b[] = new byte[1024];
						int j = 0;
						while ((j = content.read(b)) != -1) {
							output.write(b, 0, j);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						output.flush();
						output.close();
					}
				}

			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
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

	private static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	public static String getIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
}



