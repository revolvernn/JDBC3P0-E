package com.db.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mchange.v2.c3p0.ComboPooledDataSource;
/**
 * 
 * @author REVOLVER  2016年5月19日
 *
 */
@SuppressWarnings("unused")
public class C3P0Utils {
	private static ComboPooledDataSource cpds = new ComboPooledDataSource("mysql-config");
	private static ComboPooledDataSource orcl = new ComboPooledDataSource("oracle-config");
	private static Map<Thread,Connection> map = new ConcurrentHashMap<Thread, Connection>();
	
	/**
	 * 获取数据库连接对象
	 * @return
	 */
	public static Connection getConnection(){
	
		Connection conn = map.get(Thread.currentThread());
		
		if(conn == null){
			try {
				conn = cpds.getConnection();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(Thread.currentThread(), conn);
		}
		
		return conn;
	}
	/**
	 * 释放数据库连接
	 * @param conn
	 */
	public static void remove(){
		Connection conn = map.get(Thread.currentThread());
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		map.remove(Thread.currentThread());
	}
}
