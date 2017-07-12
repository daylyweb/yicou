package org.yicou.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts2.ServletActionContext;
import org.json.JSONObject;
import org.yicou.beans.MusicBean;
import org.yicou.util.MusicUtil;

import com.opensymphony.xwork2.ActionSupport;

public class MusicAction extends ActionSupport{
	private String keyword;
	private String url;
	private String songid;
	private String songmid;
	private MusicBean musicBean;
	private MusicUtil musicUtil;
	private ArrayList musics;
	public String getKeyword() {
		return keyword;
	}
	public MusicBean getMusicBean() {
		return musicBean;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public void setMusicBean(MusicBean musicBean) {
		this.musicBean = musicBean;
	}
	
	public String Music()
	{
		this.musics = musicUtil.MusicGet();
		return SUCCESS;
	}
	
	public void MusicSearch()
	{
		 HttpServletResponse response = ServletActionContext.getResponse();
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("application/json");
		 response.addHeader("Access-Control-Allow-Origin","*");
		 response.addHeader("Access-Control-Allow-Methods","GET");
		 response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		 ArrayList musics = musicUtil.MusicSearch(keyword);
		 JSONObject jo= new JSONObject();
		 if(musics==null)
		 {
			 jo.put("status", "fail");
			 jo.put("msg", "歌曲搜索失败");
		 }
		 else
		 {
			 jo.put("status", "success");
			 for(int i=0,h=musics.size();i<h;i++)
			 {
				 MusicBean mb = (MusicBean) musics.get(i);
				 JSONObject jotemp=new JSONObject();
				 jotemp.put("songName", mb.getSongName());
				 jotemp.put("singer", mb.getSinger());
				 jotemp.put("m4aUrl", mb.getM4aUrl());
				 jotemp.put("albumImg", mb.getAlbumImg());
				 jotemp.put("albumName", mb.getAlbumName());
				 jotemp.put("infoUrl", mb.getInfoUrl());
				 jotemp.put("lrc", mb.getLrc());
				 jotemp.put("songid", mb.getSongid());
				 jotemp.put("songmid", mb.getSongmid());
				 jo.append("data", jotemp);
			 }
		 }
		 try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void MusicCommend()
	{
		HttpServletResponse response = ServletActionContext.getResponse();
		 response.setCharacterEncoding("UTF-8");
		 response.setContentType("application/json");
		 response.addHeader("Access-Control-Allow-Origin","*");
		 response.addHeader("Access-Control-Allow-Methods","GET");
		 response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		 ArrayList musics = musicUtil.MusicGet();
		 //ArrayList musics = musicUtil.MusicGet();
		 JSONObject jo= new JSONObject();
		 if(musics==null)
		 {
			 jo.put("status", "fail");
			 jo.put("msg", "推荐歌曲载入失败");
		 }
		 else
		 {
			 jo.put("status", "success");
			 for(int i=0,h=musics.size();i<h;i++)
			 {
				 MusicBean mb = (MusicBean) musics.get(i);
				 JSONObject jotemp=new JSONObject();
				 jotemp.put("songName", mb.getSongName());
				 jotemp.put("singer", mb.getSinger());
				 jotemp.put("m4aUrl", mb.getM4aUrl());
				 jotemp.put("albumImg", mb.getAlbumImg());
				 jotemp.put("albumName", mb.getAlbumName());
				 jotemp.put("infoUrl", mb.getInfoUrl());
				 jotemp.put("lrc", mb.getLrc());
				 jotemp.put("songid", mb.getSongid());
				 jotemp.put("songmid", mb.getSongmid());
				 jo.append("data", jotemp);
			 }
		 }
		 try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getLrc(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin","*");
		response.addHeader("Access-Control-Allow-Methods","GET");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		JSONObject jo= new JSONObject();
		String result = musicUtil.getLrc(url);
		float time;
		if(result!=null)
		{
			Pattern pattern =Pattern.compile("\\[\\d+:\\d+\\.\\d+\\].*[^\\]\\]]");
			Matcher matcher = pattern.matcher(result);
			jo.put("status", "success");
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
					jo.append("data", jotemp);
				}
				else
				{
					continue;
				}
				
			}
		}
		else
		{
			jo.put("status", "fail");
			jo.put("msg", "暂无歌词！");
		}
		try {
			response.getWriter().write(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getLrcJson(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin","*");
		response.addHeader("Access-Control-Allow-Methods","GET");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		String result = musicUtil.getLrcJson(songid, songmid);
		//System.out.println(result);
		JSONObject jo = new JSONObject();
		 if(result==null)
		 {
			 jo.put("status", "fail");
			 jo.put("msg", "暂无歌词！");
		 }
		 else
		 {
			 JSONObject jo1= new JSONObject(result);
			 String lrc = StringEscapeUtils.unescapeHtml(jo1.getString("lyric"));
			 //System.out.println(lrc);
			 Pattern pattern =Pattern.compile("\\[\\d+:\\d+(\\.|:)\\d+\\].*");
			 Matcher matcher = pattern.matcher(lrc);
			 jo.put("status", "success");
			 float time;
			 while(matcher.find())
		 	 {
				String[] str=matcher.group().split("\\]",2);
				if(str.length==2)
				{
					str[0] = str[0].replaceFirst("\\[", "");
					time= Float.parseFloat(str[0].substring(0, 2))*60+Float.parseFloat(str[0].substring(3).replaceFirst(":", "."));
					JSONObject jotemp=new JSONObject();
					jotemp.put("time", time);
					jotemp.put("lrc", str[1]);
					jo.append("data", jotemp);
				}
				else
				{
					continue;
				}		
			 }
		 }
		 try {
				response.getWriter().write(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setMusicUtil(MusicUtil musicUtil) {
		this.musicUtil = musicUtil;
	}
	public ArrayList getMusics() {
		return musics;
	}
	public void setMusics(ArrayList musics) {
		this.musics = musics;
	}
	public String getSongid() {
		return songid;
	}
	public void setSongid(String songid) {
		this.songid = songid;
	}
	public String getSongmid() {
		return songmid;
	}
	public void setSongmid(String songmid) {
		this.songmid = songmid;
	}
}
