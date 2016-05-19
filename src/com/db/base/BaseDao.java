package com.db.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.db.utils.C3P0Utils;


/**
 * 
 * @author Revolver2015年12月15日
 *
 * @param <T>
 */
public class BaseDao<T> {
	private QueryRunner qr = new QueryRunner();
	private Class<T> type;
	
	/**
	 * 获取子类泛型的类型
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BaseDao() {
		// TODO Auto-generated constructor stub
		Class clzz = this.getClass();
		ParameterizedType pt = (ParameterizedType) clzz.getGenericSuperclass();
		Type[] types = pt.getActualTypeArguments();
		this.type = (Class<T>) types[0];
	}
	
	/**
	 * 操作数据库的方法,增删改操作
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql,Object ...params ){
		int count=0;
		
		Connection conn = C3P0Utils.getConnection();
		
		try {
			count = qr.update(conn, sql, params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		   throw new RuntimeException(e);
		}
		return count;
	}
	
	/**
	 * 查询数据库中的单条信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public T queryBean(String sql,Object ... params ){
		T t = null;
		
		Connection conn = C3P0Utils.getConnection();
		
		try {
			t = qr.query(conn, sql, new BeanHandler<T>(type), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			 throw new RuntimeException(e);
		}
		
		return t;
	}
	
	/**
	 * 查询数据库表中所有信息
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<T> queryListBean(String sql,Object ... params ){
		List<T> list = null;
		
		Connection conn = C3P0Utils.getConnection();
		
		try {
			list = qr.query(conn, sql, new BeanListHandler<T>(type), params);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			 throw new RuntimeException(e);
		}
		
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getTotalBean(String sql , Object ... params){
		
		//定义一个变量
		Object obj = null;
		
		//获取数据库连接
		Connection conn = C3P0Utils.getConnection();
		
		//执行查询数据库
		try {
			obj = qr.query(conn, sql, new ScalarHandler(), params);
		} catch (SQLException e) {
			//e.printStackTrace();
			 throw new RuntimeException(e);
		}
		
		return obj;
	}
	
	/**
	 * 批处理
	 * @param sql
	 * @param params
	 */
	public void bath(String sql, Object[][] params){
		Connection conn = C3P0Utils.getConnection();
		try {
			qr.batch(conn, sql, params);
		} catch (SQLException e) {
			//e.printStackTrace();
			 throw new RuntimeException(e);
		}
	}
}
