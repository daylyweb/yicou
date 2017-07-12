package org.yicou.serviceimpl;

import java.util.ArrayList;

import org.yicou.beans.GoodBean;
import org.yicou.dao.GoodsDao;
import org.yicou.service.GoodsService;

public class GoodsServiceImpl implements GoodsService {

	private GoodsDao goodsdao;

	public void setGoodsdao(GoodsDao goodsdao) {
		this.goodsdao = goodsdao;
	}

	@Override
	public boolean insert(GoodBean good) {
		return goodsdao.insert(good);
	}

	@Override
	public boolean insertGoods(ArrayList<GoodBean> goods) {
		return goodsdao.insertGoods(goods);
	}

	@Override
	public boolean update(GoodBean good) {
		return goodsdao.update(good);
	}

	@Override
	public boolean deleteById(int id) {
		return goodsdao.deleteById(id);
	}

	@Override
	public boolean deleteAll() {
		return goodsdao.deleteAll();
	}

	@Override
	public ArrayList<GoodBean> queryByArea(int area) {
		return goodsdao.queryByArea(area);
	}

	@Override
	public ArrayList<GoodBean> queryBySql(String sqlstr) {
		return goodsdao.queryBySql(sqlstr);
	}

}
