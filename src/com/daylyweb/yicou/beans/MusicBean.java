package com.daylyweb.yicou.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class MusicBean implements Serializable{
	
	private String songName; //歌名
	private String singer;  //歌手
	private String m4aUrl;  //歌曲文件直链
	private String albumImg;	//专辑封面
	private String albumName;  //专辑名
	private String infoUrl; //歌曲信息
	private String lrc;
	private String songid;
	private String songmid;
	private ArrayList musics;
	
	public MusicBean(){}
	
	public MusicBean(String songName,String singer,String m4aUrl,String albumImg,String albumName,String infoUrl,String lrc)
	{
		this.songName=songName;
		this.singer=singer;
		this.m4aUrl=m4aUrl;
		this.albumImg=albumImg;
		this.albumName=albumName;
		this.infoUrl=infoUrl;
		this.lrc=lrc;
	}
	
	public String getSongName() {
		return songName;
	}
	public String getSinger() {
		return singer;
	}
	public String getM4aUrl() {
		return m4aUrl;
	}
	public String getAlbumImg() {
		return albumImg;
	}
	public String getAlbumName() {
		return albumName;
	}
	public String getInfoUrl() {
		return infoUrl;
	}
	public ArrayList getMusics() {
		return musics;
	}
	public void setSongName(String songName) {
		this.songName = songName;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public void setM4aUrl(String m4aUrl) {
		this.m4aUrl = m4aUrl;
	}
	public void setAlbumImg(String albumImg) {
		this.albumImg = albumImg;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	public void setMusics(ArrayList musics) {
		this.musics = musics;
	}
	public String getLrc() {
		return lrc;
	}
	public void setLrc(String lrc) {
		this.lrc = lrc;
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
