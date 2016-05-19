/**
 * 
 */
package com.db.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.db.utils.C3P0Utils;
import com.db.utils.JDBCUtils;

/**
 * @author REVOLVER  2016年5月19日
 *
 */
public class DBTest {
	
	@Test
	public void testC3P0Utils(){
		
		Connection connection = C3P0Utils.getConnection();
		
		System.out.println(connection);
	}
	@Test
	public void testJDBCUtils(){
		
		Connection connection = null;
		try {
			connection = JDBCUtils.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(connection);
	}

}
