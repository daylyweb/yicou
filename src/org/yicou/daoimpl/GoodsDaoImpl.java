package org.yicou.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.yicou.beans.GoodBean;
import org.yicou.dao.GoodsDao;
import org.yicou.util.JDBC;

public class GoodsDaoImpl implements GoodsDao {
	
	public boolean insert(GoodBean good) {
		boolean flag=false;
		try {
			Connection con=JDBC.getConnection();
			PreparedStatement ps=con.prepareStatement("insert into goods (name,price,sales,url,imgurl,area,shop) values(?,?,?,?,?,?,?)");
			ps.setString(1, good.getName());
			ps.setFloat(2, good.getPrice());
			ps.setInt(3, good.getSales());
			ps.setString(4, good.getUrl());
			ps.setString(5, good.getImgurl());
			ps.setInt(6,good.getArea());
			ps.setInt(7, good.getShop());
			int row=ps.executeUpdate();
			ps.close();
			JDBC.closeConnection();
			if(row==1){flag= true;}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库操作失败！");
			flag =false;
		}
		return flag;
	}

	public boolean update(GoodBean good) {
		return false;
	}

	public boolean deleteById(int id) {
		return false;
	}

	public boolean deleteAll() {
		try {
			Connection con=JDBC.getConnection();
			con.prepareStatement("truncate table goods").executeUpdate(); //清除自增id
			con.prepareStatement("delete from goods").executeUpdate();
			JDBC.closeConnection();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<GoodBean> queryByArea(int area) {
		return queryBySql("select * from goods where area="+area);
	}

	public ArrayList<GoodBean> queryBySql(String sqlstr) {
		ArrayList<GoodBean> goods= new ArrayList<GoodBean>();
		ResultSet rs;
		try {
			Connection con=JDBC.getConnection();
			rs=con.createStatement().executeQuery(sqlstr);
			while(rs.next())
			{
				GoodBean good= new GoodBean(rs.getInt(1),rs.getString(2),rs.getFloat(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8));
				goods.add(good);
			}
			rs.close();
			JDBC.closeConnection();
			if(goods.size()==0){goods=null;}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("数据库查询失败！");
			goods=null;
		}
		return goods;
	}

	@Override
	public boolean insertGoods(ArrayList<GoodBean> goods) {
		Connection con =JDBC.getConnection();
		try {
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("insert into goods values(?,?,?,?,?,?,?)");
			for(int i=0;i<goods.size();i++)
			{
					GoodBean good = goods.get(i);
					ps.setString(1, good.getName());
					ps.setFloat(2, good.getPrice());
					ps.setInt(3, good.getSales());
					ps.setString(4, good.getUrl());
					ps.setString(5, good.getImgurl());
					ps.setInt(6,good.getArea());
					ps.setInt(7, good.getShop());
					ps.addBatch();
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			JDBC.closeConnection();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
			
		}

	}

}
