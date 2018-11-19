package com.liba.splider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.liba.db.JdbcUtils;
import com.liba.entity.Proxys;

public class JsoupUtils {

	public static Document getdocFromurl(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).header("Accept", "*/*").header("Accept-Encoding", "gzip, deflate")
					.header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
					.header("Referer", "https://www.baidu.com/")
					.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
					.timeout(5000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public void saveProxys() {
		
	}

	@Test
	public void testgetdocFromurl() {
		saveProxys();
	}
}
