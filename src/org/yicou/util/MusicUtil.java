package org.yicou.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.util.HtmlUtils; 
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.yicou.beans.MusicBean;


public class MusicUtil {
	ArrayList<MusicBean> musics = new ArrayList<MusicBean>();
	ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public ArrayList<MusicBean> MusicSearch(String keyword)
	{
		try {
			int page=1;
			boolean flag=true;
			String apiurl = "http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=1&n=100&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&p={page}&catZhida=0&remoteplace=sizer.newclient.next_song&w="+java.net.URLEncoder.encode(keyword,"UTF-8");
			Date date= new Date();
			date.getTime();
			while(flag)
			{
				URL url = new URL(apiurl.replace("{page}", String.valueOf(page)));
				URLConnection con = url.openConnection();
				con.setConnectTimeout(6000);
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
				String Line="",line="";
				while((line=in.readLine())!= null){Line=Line+line;}
				//System.out.println(Line);
				JSONObject jo = new JSONObject(Line);
				jo = jo.getJSONObject("data");
				jo = jo.getJSONObject("song");
				JSONArray ja = jo.getJSONArray("list");
				if(ja.length()<30) {flag=false;}
				for(int i=0,h=ja.length();i<h;i++)
				{
					JSONObject jotemp = (JSONObject) ja.get(i);
					String f = jotemp.getString("f");
					String [] farr = f.split("\\|");
					if(farr.length<20){break;}
					String songName = StringEscapeUtils.unescapeHtml(HtmlUtils.htmlUnescape(farr[1]));
					String singer = StringEscapeUtils.unescapeHtml(HtmlUtils.htmlUnescape(farr[3]));
					String albumName = StringEscapeUtils.unescapeHtml(HtmlUtils.htmlUnescape(farr[5]));
					String albummid = farr[farr.length-3];
					//String albumImg = "http://imgcache.qq.com/music/photo/mid_album_90/"+albummid.charAt(albummid.length()-2)+"/"+albummid.charAt(albummid.length()-1)+"/"+albummid+".jpg";    小专辑封面 90*90
					String albumImg = "https://y.gtimg.cn/music/photo_new/T002R500x500M000"+albummid+".jpg";
					String infoUrl = "http://data.music.qq.com/playsong.html?songid="+farr[0];
					String m4aUrl = "http://ws.stream.qqmusic.qq.com/"+farr[0]+".m4a?fromtag=46";
					String lrc = "http://music.qq.com/miniportal/static/lyric/"+(Integer.parseInt(farr[0])%100)+"/"+farr[0]+".xml";
					String songid=farr[0];
					String songmid = farr[21];
					//System.out.println("歌名："+songName+" 歌手："+singer+" 专辑封面："+albumImg+" 歌曲信息:"+infoUrl+" 歌曲文件直链："+m4aUrl+"专辑名："+albumName);
					MusicBean musicBean = (MusicBean) cxt.getBean("musicBean");
					musicBean.setSongName(songName);
					musicBean.setAlbumImg(albumImg);
					musicBean.setAlbumName(albumName);
					musicBean.setInfoUrl(infoUrl);
					musicBean.setM4aUrl(m4aUrl);
					musicBean.setSinger(singer);
					musicBean.setLrc(lrc);
					musicBean.setSongid(songid);
					musicBean.setSongmid(songmid);
					musics.add(musicBean);
				}
				page++;
			}
			Date newdate= new Date();
			System.out.println("搜索用时："+String.valueOf(newdate.getTime()-date.getTime())+"ms");
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return musics;
	}
	
	public ArrayList<MusicBean> MusicGet()
	{
		try {
			URL url = new URL("https://c.y.qq.com/qzone/fcg-bin/fcg_ucc_getcdinfo_byids_cp.fcg?type=1&json=1&utf8=1&onlysong=1&nosign=1&song_begin=0&song_num=300&ctx=1&disstid=2214656804&_=1496129641051&g_tk=845066600&jsonpCallback=getSonglistCallback&loginUin=925673945&hostUin=0&format=jsonp&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq&needNewCode=0");
			URLConnection con = url.openConnection();
			con.setConnectTimeout(6000);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String Line="",line="";
			while((line=in.readLine() )!= null){Line=Line+line;}
			Line=Line.substring(20, Line.length()-1);
			JSONObject jo = new JSONObject(Line);
			JSONArray ja = jo.getJSONArray("songlist");
			for(int i=0,h=ja.length();i<h;i++)
			{
				jo = (JSONObject) ja.get(i);
				String songName = jo.getString("songname");
				JSONArray jatemp= jo.getJSONArray("singer");
				String singertemp="";
				for(int a=0;a<jatemp.length();a++)
				{
					
					if(a==0)
					{
						
						singertemp = ((JSONObject)jatemp.get(a)).getString("name");
					}
					else
					{
						singertemp=singertemp+";"+((JSONObject)jatemp.get(a)).getString("name");
					}
				}
				int songid = jo.getInt("songid");
				String singer = singertemp;
				String songmid = jo.getString("songmid");
				//String m4aUrl = "http://ws.stream.qqmusic.qq.com/C100"+jo.getString("songmid")+".m4a?vkey=&amp;guid=&amp;fromtag=7";
				String m4aUrl = "http://ws.stream.qqmusic.qq.com/"+songid+".m4a?fromtag=46";
				String albummid = jo.getString("albummid");
				//String albumImg = "http://imgcache.qq.com/music/photo/mid_album_90/"+albummid.charAt(albummid.length()-2)+"/"+albummid.charAt(albummid.length()-1)+"/"+albummid+".jpg";
				String albumImg = "https://y.gtimg.cn/music/photo_new/T002R500x500M000"+albummid+".jpg";
				String albumName= jo.getString("albumname");
				String infoUrl = "http://data.music.qq.com/playsong.html?songid="+songid;
				String lrc = "http://music.qq.com/miniportal/static/lyric/"+(songid%100)+"/"+songid+".xml";
				//System.out.println("歌名："+songName+" 歌手："+singer+" 专辑封面："+albumImg+" 歌曲信息:"+infoUrl+" 歌曲文件直链："+m4aUrl+"专辑名："+albumName);
				MusicBean musicBean = (MusicBean) cxt.getBean("musicBean");
				musicBean.setSongName(songName);
				musicBean.setAlbumImg(albumImg);
				musicBean.setAlbumName(albumName);
				musicBean.setInfoUrl(infoUrl);
				musicBean.setM4aUrl(m4aUrl);
				musicBean.setSinger(singer);
				musicBean.setLrc(lrc);
				musicBean.setSongid(String.valueOf(songid));
				musicBean.setSongmid(songmid);
				musics.add(musicBean);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return musics;
	
	}
	
	public String getLrc(String Url)
	{
		InputStream iStream = null;
	    String result = null;
	    try {
	      URL url = new URL(Url);
	      URLConnection connection = url.openConnection();
	      iStream = connection.getInputStream();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(
	          iStream, "GB2312"));
	      StringBuffer sb = new StringBuffer();
	      String line = null,separator=System.getProperty("line.separator");
	      while ((line = reader.readLine()) != null) {
	        sb.append(line + separator);
	      }
	      iStream.close();
	      result = sb.toString().replace("]]></lyric>", "");
	    } catch (Exception e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	      result=null;
	    }
	    return result;
	  }
	
	public String getLrcJson(String songid,String songmid){
		InputStream iStream = null;
	    String result = null;
	    String referer="https://c.y.qq.com/v8/playsong.html?songmid="+songmid+"&ADTAG=myqq&from=myqq";
	    String Url="https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric.fcg?nobase64=1&musicid="+songid+"&songtype=0";
	    try {
	      URL url = new URL(Url);
	      URLConnection connection = url.openConnection();
	      connection.addRequestProperty("referer", referer);
	      iStream = connection.getInputStream();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(
	          iStream, "UTF-8"));
	      StringBuffer sb = new StringBuffer();
	      String line = null;
	      while ((line = reader.readLine()) != null) {
	        sb.append(line);
	      }
	      iStream.close();
	      result = sb.toString();
	      result=result.substring(result.indexOf("(")+1, result.length()-1);
	      //result=StringEscapeUtils.unescapeHtml(result);
	    } catch (Exception e) {
	      e.printStackTrace();
	      return null;
	    }
	    return result;
	}
	/*public static void main(String[] aaa){
		String result = new MusicUtil().getLrcJson("202976231","000CK5xN3yZDJt");
		//System.out.println(result);
		JSONObject jo= new JSONObject(result);
		String lrc = StringEscapeUtils.unescapeHtml(jo.getString("lyric"));
		System.out.println(lrc);
		Pattern pattern =Pattern.compile("\\[\\d+:\\d+\\.\\d+\\].*");
		Matcher matcher = pattern.matcher(lrc);
		JSONObject jo2 = new JSONObject();
		jo.put("status", "success");
		float time;
		while(matcher.find())
		{
			String[] str=matcher.group().split("\\]",2);
			if(str.length==2)
			{
				str[0] = str[0].replaceFirst("\\[", "");
				time= Float.parseFloat(str[0].substring(0, 2))*60+Float.parseFloat(str[0].substring(3));
				JSONObject jotemp=new JSONObject();
				jotemp.put("time", time);
				jotemp.put("lrc", str[1]);
				jo2.append("data", jotemp);
			}
			else
			{
				continue;
			}
			
		}
		System.out.println(jo2.toString());
	}*/
/*	public static String tr(String str){ 
		Pattern pattern = Pattern.compile("\\&\\#(\\d+)"); 
	    StringBuilder sb = new StringBuilder(); 
	    Matcher m =pattern.matcher(str); 
	    while (m.find()){ 
	      sb.append((char)Integer.valueOf(m.group(1)).intValue()); 
	    } 
	    return sb.toString(); 
	  } */
}
