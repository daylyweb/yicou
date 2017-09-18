package com.daylyweb.yicou.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daylyweb.yicou.beans.GoodBean;
import com.daylyweb.yicou.service.GoodsService;
import com.daylyweb.yicou.util.GetGoodsCallable;
import com.opensymphony.xwork2.ActionSupport;
import org.json.*;

public class ApiAction extends ActionSupport{
	private GoodsService goodsService;
	private int area=8;
	private int shop=4;
	private String result;
	private ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
	
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	public void returnGoods()
	{
		String sqlStr;
		if(shop==4 && area==8){ sqlStr="select * from goods group by name"; }
		else if(shop==4 && area!=8){ sqlStr="select * from goods where area="+area;}
		else if(shop!=4 && area==8){ sqlStr="select * from goods where shop="+shop;}
		else { sqlStr="select * from goods where shop="+shop+" and area="+area; }
		JSONObject jo= new JSONObject();
		ArrayList<GoodBean> result = goodsService.queryBySql(sqlStr);
		if(result!=null)
		{
			jo.put("status", "success");
			int i=0;
			while(i<result.size())
			{
				GoodBean good=(GoodBean) result.get(i);
				JSONObject jotemp= new JSONObject();
				jotemp.put("id", good.getId());
				jotemp.put("name", good.getName());
				jotemp.put("price", good.getPrice());
				jotemp.put("sales", good.getSales());
				jotemp.put("url", good.getUrl());
				jotemp.put("imgurl", good.getImgurl());
				jotemp.put("area", good.getArea());
				jotemp.put("shop", good.getShop());
				jo.append("data", jotemp);
				i++;
			}

		}
		else
		{
			jo.put("status", "fail");
			jo.put("msg", "商品获取失败");
		}
		try {
			 HttpServletResponse response = ServletActionContext.getResponse();
			 response.setCharacterEncoding("UTF-8");
			 response.setContentType("application/json");
			 response.addHeader("Access-Control-Allow-Origin","*");
			 response.addHeader("Access-Control-Allow-Methods","GET");
			 response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			 response.getWriter().write(jo.toString());
			 
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void getGoods()
	{
		goodsService.deleteAll();
		Date date1 = new Date();
		GetGoodsCallable ggc = (GetGoodsCallable) cxt.getBean("getGoodsCallable");
		ggc.setShop(1);
		FutureTask<Object> ft = new FutureTask<Object>(ggc);
		Thread th = new Thread(ft);
		
		GetGoodsCallable ggc1 = (GetGoodsCallable) cxt.getBean("getGoodsCallable");
		ggc1.setShop(190);
		FutureTask<Object> ft1 = new FutureTask<Object>(ggc1);
		Thread th1 = new Thread(ft1);
		
		GetGoodsCallable ggc2 = (GetGoodsCallable) cxt.getBean("getGoodsCallable");
		ggc2.setShop(13);
		FutureTask<Object> ft2 = new FutureTask<Object>(ggc2);
		Thread th2 = new Thread(ft2);

		
		th.start();
		th1.start();
		th2.start();

		 JSONObject jo= new JSONObject();
		 try {
			if((Boolean) ft.get() && (Boolean) ft1.get() && (Boolean) ft2.get())
			 {
				Date date2 = new Date();
				System.out.println("用时:"+(date2.getTime()-date1.getTime())+"ms");
				jo.put("status", "success");
				jo.put("msg", "商品更新成功！");
			 }
			 else
			 {
				 jo.put("status", "fail");
				 jo.put("msg", "商品更新失败！");
			 }
		} catch (JSONException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		 try {
			 HttpServletResponse response = ServletActionContext.getResponse();
			 response.setCharacterEncoding("UTF-8");
			 response.setContentType("application/json");
			 response.addHeader("Access-Control-Allow-Origin","*");
			 response.addHeader("Access-Control-Allow-Methods","GET");
			 response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			 response.getWriter().write(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
