package cn.edu.zucc.booklib.control;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanOrder;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewOrdersManager {

	public List<BeanOrder> orderQuery(int orderid) throws BaseException {
		Connection conn = null;
		List<BeanOrder> ans = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select OrderID, productname, quantity, unitprice, orderdate\n" +
					"from vieworderdetail";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanOrder t = new BeanOrder();
				t.setOrderid(rs.getInt(1));
				t.setProductName(rs.getString(2));
				t.setQuantity(rs.getInt(3));
				t.setUnitPrice(rs.getDouble(4));
				t.setOrderDate(rs.getDate(5));
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BaseException("ERROR");
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ans;
	}

	public Map<String, Double> getProductTotalFee(String productName) throws BaseException {
		Map<String, Double> ans = new HashMap<>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select OrderID, productname, quantity, unitprice, orderdate\n" +
					"from vieworderdetail\n" +
					"where ProductName = ?";
			java.sql.PreparedStatement pst  = conn.prepareStatement(sql);
			pst.setString(1,productName);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				String key=rs.getString(2);
				double value=rs.getInt(3)*rs.getDouble(4);
				ans.put(key,value);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null){
				try{
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ans;
	}

	public List<BeanOrder> getMaxOrderedProductName() throws BaseException{
		List<BeanOrder> ans = new ArrayList<>();
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "select OrderID, productname, quantity, unitprice, orderdate\n" +
					"from vieworderdetail\n" +
					"where Quantity = (\n" +
					"    select max(Quantity)\n" +
					"    from vieworderdetail\n" +
					")";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			while(rs.next()){
				BeanOrder t = new BeanOrder();
				t.setOrderid(rs.getInt(1));
				t.setProductName(rs.getString(2));
				t.setQuantity(rs.getInt(3));
				t.setUnitPrice(rs.getDouble(4));
				t.setOrderDate(rs.getDate(5));
				ans.add(t);
			}
			pst.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null){
				try{
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return ans;
	}

	public static void main(String[] args) {

	}
}
