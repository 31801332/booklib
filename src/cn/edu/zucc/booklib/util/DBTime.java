package cn.edu.zucc.booklib.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class DBTime {

	public Date getTime() throws DbException {
		Connection conn = null;
		java.sql.Date dt = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "select now()";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()){
				dt=rs.getDate(1);
			}
			pst.close();
			rs.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new DbException(e);
		}finally {
			if(conn!=null){
				try{
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return dt;
	}
}
