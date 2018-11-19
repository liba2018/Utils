package com.liba.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.liba.entity.Proxys;

public class HttpConnectionUtil {
	 public String sendPostRequest(String url,Map<String,String> parameters){  
	        // Post请求的url，与get不同的是不需要带参数  
	        String result="";
	        try {

	        URL postUrl = new URL(url);
	        
	       
	        // 加入代理start

	        /*Properties systemProperties =System.getProperties();
	        systemProperties.setProperty("http.proxyHost",proxyHost);

	        systemProperties.setProperty("http.proxyPort",String.valueOf(proxyPort));*/

	        // 加入代理end

	        // 打开连接  
	        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();       
	        // 设置是否向connection输出，因为这个是post请求，参数要放在  
	        // http正文内，因此需要设为true  
	        connection.setDoOutput(true);  
	        // Read from the connection. Default is true.  
	        connection.setDoInput(true);  
	        // 默认是 GET方式  
	        connection.setRequestMethod("POST");        
	        // Post 请求不能使用缓存  
	        connection.setUseCaches(false);    
	           //设置本次连接是否自动重定向   
	        connection.setInstanceFollowRedirects(true);        
	        // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的  
	        // 意思是正文是urlencoded编码过的form参数  
	        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");  
	        // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，  
	        // 要注意的是connection.getOutputStream会隐含的进行connect。  
	        connection.connect();  
	        DataOutputStream out = new DataOutputStream(connection  
	                .getOutputStream());  
	        // 正文，正文内容其实跟get的URL中 '? '后的参数字符串一致  
	        String content = "";  
	        // DataOutputStream.writeBytes将字符串中的16位的unicode字符以8位的字符形式写到流里面  
	        if(null != parameters && parameters.size()>0){
	            for(Map.Entry<String,String> entry : parameters.entrySet()){
	                   content +="&"+entry.getKey()+"="+URLEncoder.encode(entry.getValue(), "utf-8");               
	            }
	    }
	        out.writeBytes(content.replaceFirst("&", ""));  
	        //流用完记得关  

	        out.flush(); 

	       //去掉代理start
			/*
			 * System.getProperties().remove("http.proxyHost");
			 * 
			 * System.getProperties().remove("http.proxyPort");
			 */
	       //去掉代理end

	        out.close();  
	        //获取响应  
	        //获取请求的资源  
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));  
	        result =br.readLine();
	        StringBuffer sb =new StringBuffer();
	        while(result != null){
	        	sb.append(result);
	        	result=br.readLine();
	        }
	        br.close();  
	        //该干的都干完了,记得把连接断了  
	        connection.disconnect();  
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	             return null;
	        } 
	        return result;
	  }  
	 public Proxy getProxy(){
		 
		 String ip="47.93.18.195";
         int port=80;
         //
         List<Proxys> list =new ArrayList<Proxys>();
         
         // 创建代理服务器  
         InetSocketAddress addr = new InetSocketAddress(ip,port);  
         Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
		 return proxy;
	 }
}
