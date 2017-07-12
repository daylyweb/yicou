package org.yicou.dao;

import java.util.ArrayList;

import org.yicou.beans.GoodBean;

public interface GoodsDao {
	public boolean insert(GoodBean good);
	public boolean insertGoods(ArrayList<GoodBean> goods);
	public boolean update(GoodBean good);
	public boolean deleteById(int id);
	public boolean deleteAll();
	public ArrayList<GoodBean> queryByArea(int area);
	public ArrayList<GoodBean> queryBySql(String sqlstr);
}
