package cn.edu.zucc.booklib.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil2 {
	private static DBUtil2 dbPool;
	private static ComboPooledDataSource dataSource;

	static {
		dbPool = new DBUtil2();
	}

	public DBUtil2() {
		try{
			dataSource = new ComboPooledDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("0608");
			dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/booklib?useSSL=true");
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			dataSource.setInitialPoolSize(2);
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(10);
			dataSource.setMaxStatements(50);
			dataSource.setMaxIdleTime(60);
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
	}

	public final static DBUtil2 getInstance(){
		return dbPool;
	}

	public static Connection getConnection(){
		try{
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException("无法从数据库获取连接 ",e);
		}
	}

	public static void main(String[] args) {
		Connection con = null;
	}
}
