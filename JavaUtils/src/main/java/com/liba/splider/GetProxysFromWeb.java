package com.liba.splider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.liba.db.JdbcUtils;
import com.liba.entity.Proxys;
/**
 * 从网站上获取代理IP
 * @author liba
 *
 */
public class GetProxysFromWeb {

	public static void main(String[] args) {
		// 获取代理IP
				Document doc = JsoupUtils.getdocFromurl("https://www.kuaidaili.com/free/inha/");
				Elements table = doc.select("tbody");
				System.out.println(table.toString());
				System.out.println("==========================================================");
				/*
				 * </table> <tr> <td data-title="IP">120.69.82.110</td> <td
				 * data-title="PORT">44693</td> <td data-title="匿名度">高匿名</td> <td
				 * data-title="类型">HTTP</td> <td data-title="位置">新疆维吾尔自治区石河子市 电信</td>
				 * <td data-title="响应速度">0.7秒</td> <td data-title="最后验证时间">2018-11-15
				 * 02:31:14</td> </tr> </table>
				 */
				List<Proxys> list = new ArrayList<Proxys>();
				Elements tr = table.select("tr");
				for (int i = 0; i < tr.size(); i++) {
					Elements td = tr.get(i).select("td");
					Proxys px = new Proxys();
					for (int j = 0; j < 2; j++) { // 只取前两个字段 td.size()
						if (j == 0)
							px.setIp(td.get(j).text());
						else
							px.setPort(Integer.valueOf(td.get(j).text()));
					}
					list.add(px);
				}
				System.out.println("获取到网站的代理有：" + list.toString());
				// 存储IP
				try {
					// Class.forName("com.mysql.jdbc.Driver");
					// conn =
					// DriverManager.getConnection("jdbc:mysql://192.168.0.200:3306/xxx",
					// "root", "root");
					Connection conn = JdbcUtils.getConnection();
					String sql = "insert into ipproxys (ip,port) value (?,?)";
					PreparedStatement prest = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					conn.setAutoCommit(false);

					for (int i = 0; i <list.size(); i++) {
						prest.setString(1, list.get(i).getIp());
						prest.setInt(2, list.get(i).getPort());
						prest.addBatch();
					}
					prest.executeBatch();
					conn.commit();
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
	}

}
