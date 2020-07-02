package cn.edu.zucc.booklib.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.Orders;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.DBTime;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class OrdersManager {

	public void addOrders(Orders ord) throws BaseException, SQLException {
		if(ord.getCustomerID()==null||ord.getOrderDate()==null){
			throw new BaseException("输入为空");
		}

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();

			String sql = "select count(*) from orders where OrderID = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1,ord.getOrderID());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()){
				if(rs.getInt(1)==1)
					throw new BaseException("主键重复");
			}
			rs.close();
			pst.close();

			sql = "INSERT INTO orders VALUES (?, ?, ?, ?);";
			pst = conn.prepareStatement(sql);
			pst.setInt(1,ord.getOrderID());
			pst.setString(2,ord.getCustomerID());
			pst.setInt(3,ord.getEmployeeID());
			pst.setDate(4, (Date) ord.getOrderDate());
			int line = pst.executeUpdate();
			if(line == 0)
				throw new BaseException("插入失败");
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null){
				try{
					conn.close();
				}catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	public List<Orders> searchOrdersByCID(String cid) throws BaseException{
		Connection conn = null;
		List<Orders> ans = new ArrayList<Orders>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select orderid, customerid, employeeid, orderdate from orders where CustomerID = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,cid);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				Orders p = new Orders();
				p.setOrderID(rs.getInt(1));
				p.setCustomerID(rs.getString(2));
				p.setEmployeeID(rs.getInt(3));
				p.setOrderDate(rs.getDate(4));
				ans.add(p);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) throws BaseException, SQLException {
		List<Orders> l = new ArrayList<Orders>();
		l=new OrdersManager().searchOrdersByCID("003");
		for(int i=0;i<l.size();i++){
			System.out.println(l.get(i).toString());
		}
	}
}
