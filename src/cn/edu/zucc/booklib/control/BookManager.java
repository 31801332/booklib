package cn.edu.zucc.booklib.control;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.booklib.model.BeanBook;
import cn.edu.zucc.booklib.model.BeanReader;
import cn.edu.zucc.booklib.random.randBean;
import cn.edu.zucc.booklib.util.BaseException;
import cn.edu.zucc.booklib.util.BusinessException;
import cn.edu.zucc.booklib.util.DBUtil;
import cn.edu.zucc.booklib.util.DbException;

public class BookManager {
	public List<BeanBook> searchBook(String keyword, String bookState) throws BaseException {
		List<BeanBook> result = new ArrayList<BeanBook>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select b.barcode,b.bookname,b.pubid,b.price,b.state,p.publishername " +
					" from beanbook b left outer join beanpublisher p on (b.pubid=p.pubid)" +
					" where  b.state='" + bookState + "' ";
			if (keyword != null && !"".equals(keyword))
				sql += " and (b.bookname like ? or b.barcode like ?)";
			sql += " order by b.barcode";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pst.setString(1, "%" + keyword + "%");
				pst.setString(2, "%" + keyword + "%");

			}

			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanBook b = new BeanBook();
				b.setBarcode(rs.getString(1));
				b.setBookname(rs.getString(2));
				b.setPubid(rs.getString(3));
				b.setPrice(rs.getDouble(4));
				b.setState(rs.getString(5));
				b.setPubName(rs.getString(6));
				result.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;

	}

	public void createBook(BeanBook b) throws BaseException {
		if (b.getBarcode() == null || "".equals(b.getBarcode()) || b.getBarcode().length() > 20) {
			throw new BusinessException("条码必须是1-20个字");
		}
		if (b.getBookname() == null || "".equals(b.getBookname()) || b.getBookname().length() > 50) {
			throw new BusinessException("图书名称必须是1-50个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from BeanBook where barcode=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) throw new BusinessException("条码已经被占用");
			rs.close();
			pst.close();
			sql = "insert into BeanBook(barcode,bookname,pubid,price,state) values(?,?,?,?,'在库')";
			pst = conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			pst.setString(2,b.getBookname());
			pst.setString(3, b.getPubid());
			pst.setDouble(4, b.getPrice());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void modifyBook(BeanBook b) throws BaseException {
		if (b.getBookname() == null || "".equals(b.getBookname()) || b.getBookname().length() > 50) {
			throw new BusinessException("图书名称必须是1-50个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from BeanBook where barcode=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, b.getBarcode());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) throw new BusinessException("图书不存在");
			rs.close();
			pst.close();
			sql = "update BeanBook set bookname=?,pubid=?,price=?,state=? where barcode=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, b.getBookname());
			pst.setString(2, b.getPubid());
			pst.setDouble(3, b.getPrice());
			pst.setString(4, b.getState());
			pst.setString(5, b.getBarcode());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public BeanBook loadBook(String barcode) throws DbException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select b.barcode,b.bookname,b.pubid,b.price,b.state,p.publishername " +
					" from beanbook b left outer join beanpublisher p on (b.pubid=p.pubid)" +
					" where  b.barcode=? ";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, barcode);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				BeanBook b = new BeanBook();
				b.setBarcode(rs.getString(1));
				b.setBookname(rs.getString(2));
				b.setPubid(rs.getString(3));
				b.setPrice(rs.getDouble(4));
				b.setState(rs.getString(5));
				b.setPubName(rs.getString(6));
				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;
	}

	public int getBookCount(String pubid) throws BaseException {
		//返回该出版社的图书数量
		int result = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(pubid) from beanbook where pubid = " + pubid;
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public int getPublisherCount() throws BaseException {
		//要求返回图书表中出现过的出版社数量
		int result = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(distinct pubid) from beanbook";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public int getNoneBookPublisherCount() throws BaseException {
		//要求返回没用图书的出版社数量
		int result = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(distinct pubid) from beanbook where state = '已删除'";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public double getBookAvgPrice() throws BaseException {
		//要求返回图书的评价价格
		double result = 0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select avg(price) from beanbook";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public void showTop5Books() throws BaseException {
		//通过System.out.println方法，输出借阅次数最多的5本图书及其借阅次数
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select bookBarcode, count(bookBarcode)\n" +
					"from beanbooklendrecord\n" +
					"group by bookBarcode";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			System.out.println("bookBarcode:count");
			int n = 5;
			while (n > 0) {
				n--;
				if (rs.next()) {
					System.out.println(rs.getString(1) + ":" + rs.getInt(2));
				} else {
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void showTop5Publisher() throws BaseException {
		//通过System.out.println方法，输出被借阅图书次数最多的5个出版名称及其总借阅次数和被借阅过的图书次数
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(beanbooklendrecord.bookBarcode),b.barcode,b.bookname,b2.publisherName\n" +
					"from beanbooklendrecord\n" +
					"         inner join beanbook b on beanbooklendrecord.bookBarcode = b.barcode\n" +
					"         inner join beanpublisher b2 on b.pubid = b2.pubid\n" +
					"group by b.barcode\n";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			System.out.println("publishername:count");
			int n = 5;
			while (n > 0) {
				n--;
				if (rs.next()) {
					System.out.println(rs.getString(4) + ":" + rs.getInt(1));
				} else {
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}



	public static void main(String[] args) {
		long start = System.nanoTime();

		int count=1000;
		while(count!=0){
			count--;
			new randBean().randBook(1);
		}

		long end = System.nanoTime();
		System.out.println(end-start);
	}
}
