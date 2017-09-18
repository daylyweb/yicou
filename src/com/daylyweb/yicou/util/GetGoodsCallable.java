package com.daylyweb.yicou.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.daylyweb.yicou.beans.GoodBean;
import com.daylyweb.yicou.service.*;
public class GetGoodsCallable implements Callable<Object> {
	private int shop;
	private GoodsService goodsService;
	private GoodBean goodBean;
 	boolean flag;
	int myshop;
	
	public void setGoodsService(GoodsService goodsService)
	{
		this.goodsService=goodsService;
	}
	public void setGoodBean(GoodBean goodBean) {
		this.goodBean = goodBean;
	}
	public void setShop(int shop)
	{
		this.shop=shop;
	}

	public Object call() throws Exception {
		return getGoods(shop);
	}
	
	public boolean getGoods(int shop)
	{
		goodBean.setGoods(new ArrayList());
		//转成自定义商城号  1京东  2天猫 3一号店
		goodBean.getGoods().clear();
		switch(shop)
		{
			case 1:myshop=1;break;
			case 190:myshop=2;break;
			case 13:myshop=3;break;
			default:return false;
		}
		String urlstr;
		int page=1;
		for(int area=1;area<=7;area++)
		{
			flag=true;
			while(flag)
			{
				
				urlstr="http://tool2.manmanbuy.com/coudan.aspx?tab=1&site="+shop+"&area="+area+"&price=1&cat=0&sort=1&PageID=+"+page;
				if(getInfo(urlstr,area)){page++;}
				else{return false;}
			}
			page=1;
		}
		goodsService.insertGoods(goodBean.getGoods());
		return true;
	}
	
	public boolean getInfo(String urlstr,int area)
	{
		URL url = null;
		URLConnection con = null;
		try {
			try {
				url = new URL(urlstr);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.out.println("url异常");
				return false;
			}
		String line,Line = null;
		con = url.openConnection();
		con.setConnectTimeout(10000);
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:21.0) Gecko/20100101 Firefox/21.0");
		con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "gb2312"));
		while((line=in.readLine() )!= null){Line=Line+line;}
		String biaodashi="<li class=\"newitem\">.*?</li>";
		Pattern pattern = Pattern.compile(biaodashi);
		Matcher mt=pattern.matcher(Line);
		String GoodURL,GoodName,ImgUrl,GoodSales;
		Float GoodPrice;
		
		if(!mt.find()){this.flag=false;}
		while(mt.find())
		{
			String pipei=mt.group();
			GoodURL=toGB2312(pipei.substring(pipei.indexOf("tourl=")+6, pipei.indexOf("\" target=")));
			GoodName=toGB2312(pipei.substring(pipei.indexOf("title=\"")+7, pipei.indexOf("\"><img")));
			ImgUrl=toGB2312(pipei.substring(pipei.indexOf("src=\"")+5,pipei.indexOf("\" alt=")));
			GoodSales=toGB2312(pipei.substring(pipei.indexOf("已有<span>")+8, pipei.indexOf("</span>条评价")));
			if(pipei.indexOf("最少买")!=-1){GoodPrice=Float.valueOf(pipei.substring(pipei.indexOf("</em>")+5, pipei.indexOf("<span")));}
			else{GoodPrice=Float.valueOf(pipei.substring(pipei.indexOf("</em>")+5, pipei.indexOf(" </div>",pipei.indexOf("</em>")+5)));}
			GoodBean good = new GoodBean(GoodName,GoodPrice,Integer.parseInt(GoodSales),GoodURL,ImgUrl,area,myshop);
			//dao.insert(good);
			goodBean.getGoods().add(good);
		}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("输入异常");
			return false;
		}
		return true;
	}
	
	public String toGB2312(String str)
	{
		String returnstr=null;
		try {
			returnstr= new String(str.getBytes(),"GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		return returnstr;
	}
}
