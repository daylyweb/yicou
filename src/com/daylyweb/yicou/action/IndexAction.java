package com.daylyweb.yicou.action;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daylyweb.yicou.beans.GoodBean;
import com.daylyweb.yicou.service.GoodsService;
import com.opensymphony.xwork2.ActionSupport;

public class IndexAction extends ActionSupport{
	private int shop=4;
	private int area=8;
	private String shopStr="全部";
	private String areaStr="全部地区";
	private String sqlStr="select * from goods group by name";
	private List goods;
	ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public int getShop() {
		return shop;
	}
	public int getArea() {
		return area;
	}
	public void setShop(int shop) {
		this.shop = shop;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public List getGoods() {
		return goods;
	}
	public void setGoods(List goods) {
		this.goods = goods;
	}
	public String getShopStr() {
		return shopStr;
	}
	public String getAreaStr() {
		return areaStr;
	}
	public void setShopStr(String shopStr) {
		this.shopStr = shopStr;
	}
	public void setAreaStr(String areaStr) {
		this.areaStr = areaStr;
	}

	public String execute() throws Exception
	{
    	switch(shop)
    	{
    		case 1: shopStr = "京东"; break;
    		case 2: shopStr = "天猫";	break;
    		case 3: shopStr = "一号店";break;
    		default:shopStr = "全部";
    	}
    	switch(area)
    	{
    		case 1: areaStr="华北（北京、山西等）";break;
    		case 2: areaStr="华东（山东、浙江等）";break;
    		case 3: areaStr="华南（广东、广西等）";break;
    		case 4: areaStr="华中（湖北、河南等）";break;
    		case 5: areaStr="西南（四川、云南等）";break;
    		case 6: areaStr="东北（辽宁、吉林等）";break;
    		case 7: areaStr="西北（宁夏、新疆等）";break;
    		default:areaStr = "全部地区";
    	}
    	
    	if(shop==4 && area==8){ sqlStr="select * from goods group by name"; }
    	else if(shop==4 && area!=8){ sqlStr="select * from goods where area="+area;}
    	else if(shop!=4 && area==8){ sqlStr="select * from goods where shop="+shop;}
    	else { sqlStr="select * from goods where area="+area+" and shop="+shop; }
    	this.goods= (List)(((GoodsService)cxt.getBean("goodsService")).queryBySql(sqlStr));
	    return SUCCESS;
	}
}
