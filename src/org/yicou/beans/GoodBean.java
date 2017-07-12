package org.yicou.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodBean implements Serializable{

	private int id;
	private String name;
	private Float price;
	private int sales;
	private String url;
	private String imgurl;
	private int area;
	private int shop;
	private ArrayList goods;
	
	public GoodBean() {}

	public GoodBean(int id, String name, Float price, int sales, String url,String imgurl, int area, int shop) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.sales = sales;
		this.url = url;
		this.imgurl = imgurl;
		this.area = area;
		this.shop = shop;
	}
	
	public GoodBean( String name, Float price, int sales, String url,String imgurl, int area, int shop) {
		this.name = name;
		this.price = price;
		this.sales = sales;
		this.url = url;
		this.imgurl = imgurl;
		this.area = area;
		this.shop = shop;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public int getSales() {
		return sales;
	}

	public void setSales(int sales) {
		this.sales = sales;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public int getShop() {
		return shop;
	}

	public void setShop(int shop) {
		this.shop = shop;
	}

	public ArrayList getGoods() {
		return goods;
	}

	public void setGoods(ArrayList goods) {
		this.goods = goods;
	}
}
