package org.yicou.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.yicou.beans.UserBean;
import org.yicou.dao.UserDao;
import org.yicou.util.JDBC;


public class UserDaoImpl implements UserDao {

	@Override
	public boolean insert(UserBean user) {
		boolean flag=false;
		Connection con=JDBC.getConnection();
		try {
			PreparedStatement ps = con.prepareStatement("insert into user (username,password,phone,mail,birthday,nickname,headerimg) values(?,?,?,?,?,?,?)");
			ps.setString(1,user.getUsername());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getPhone());
			ps.setString(4,user.getMail());
			ps.setDate(5,user.getBirthday());
			ps.setString(6,user.getNickname());
			ps.setString(7,user.getHeaderimg());
			if(ps.executeUpdate()==1) flag=true;
			ps.close();
			JDBC.closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public boolean insertUsers(ArrayList<UserBean> users) {
		return false;
	}

	@Override
	public boolean update(UserBean user) {
		return false;
	}

	@Override
	public boolean deleteByUid(int uid) {
		return false;
	}

	@Override
	public boolean deleteAll() {
		return false;
	}

	@Override
	public ArrayList<UserBean> queryUsersBySql(String sqlstr) {
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		ResultSet rs;
		Connection con = JDBC.getConnection();
		try {
			rs = con.createStatement().executeQuery(sqlstr);
			while(rs.next())
			{
				UserBean user = new UserBean(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getString(7),rs.getString(8));
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public UserBean queryUserBySql(String sqlstr) {
		UserBean user=null;
		ResultSet rs;
		Connection con = JDBC.getConnection();
		try{
			rs = con.createStatement().executeQuery(sqlstr);
			while(rs.next())
			{
				user = new UserBean(rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getDate(6),rs.getString(7),rs.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean isExistByUid(int uid) {
		ResultSet rs;
		boolean flag=false;
		Connection con = JDBC.getConnection();
		try{
			rs = con.createStatement().executeQuery("select * from user where uid="+uid);
			while(rs.next()){flag=true;}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isExistByUserName(String username) {
		ResultSet rs;
		boolean flag=false;
		Connection con = JDBC.getConnection();
		try{
			rs = con.createStatement().executeQuery("select * from user where username='"+username+"'");
			while(rs.next()){flag=true;}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isExistByEmail(String email) {
		ResultSet rs;
		boolean flag=false;
		Connection con = JDBC.getConnection();
		try{
			rs = con.createStatement().executeQuery("select * from user where mail='"+email+"'");
			while(rs.next()){flag=true;}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

}
