package org.yicou.serviceimpl;

import java.util.ArrayList;

import org.yicou.beans.UserBean;
import org.yicou.dao.UserDao;
import org.yicou.service.UserService;

public class UserServiceImpl implements UserService {

	private UserDao userdao;
	@Override
	public boolean insert(UserBean user) {
		return userdao.insert(user);
	}

	@Override
	public boolean insertUsers(ArrayList<UserBean> users) {
		return userdao.insertUsers(users);
	}

	@Override
	public boolean update(UserBean user) {
		return userdao.update(user);
	}

	@Override
	public boolean deleteByUid(int uid) {
		return userdao.deleteByUid(uid);
	}

	@Override
	public boolean deleteAll() {
		return userdao.deleteAll();
	}

	@Override
	public ArrayList<UserBean> queryUsersBySql(String sqlstr) {
		return userdao.queryUsersBySql(sqlstr);
	}

	@Override
	public UserBean queryUserBySql(String sqlstr) {
		return userdao.queryUserBySql(sqlstr);
	}

	public UserDao getUserdao() {
		return userdao;
	}

	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}

	@Override
	public boolean isExistByUid(int uid) {
		return userdao.isExistByUid(uid);
	}

	@Override
	public boolean isExistByUserName(String username) {
		return userdao.isExistByUserName(username);
	}

	@Override
	public boolean isExistByEmail(String email) {
		return userdao.isExistByEmail(email);
	}
}
