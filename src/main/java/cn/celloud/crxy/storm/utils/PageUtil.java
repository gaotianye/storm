package cn.celloud.crxy.storm.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtil {
	static Logger logger = LoggerFactory.getLogger(PageUtil.class);
	
	static HttpClientBuilder builder = HttpClients.custom();
	public static String getContent(String url){
		//设置浏览器的信息
		builder.setUserAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
		//这个代理ip不能写死，需要从代理ip库中动态获取
		//TODO--需要从动态代理ip库中读取数据，解析出来ip和端口
		String proxy_ip = "182.90.28.52";
		int proxy_port = 80;
		HttpHost proxy = new HttpHost(proxy_ip, proxy_port);
		CloseableHttpClient client = builder/*.setProxy(proxy )*/.build();
		String content = null;
		long start_time = System.currentTimeMillis();
		
		HttpGet request = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			content = EntityUtils.toString(entity);
			//logger.info(Thread.currentThread().getId()+"页面下载成功,url是：{},耗时：{}",url,System.currentTimeMillis()-start_time);
			client.close();
		}catch(HttpHostConnectException e){
			logger.error("代理IP失效：ip:{},port:{},失败的url:{}",proxy_ip,proxy_port,url);
		}catch (Exception e) {
			logger.error("页面下载失败,url是：{}",url);
		}finally{
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

}
