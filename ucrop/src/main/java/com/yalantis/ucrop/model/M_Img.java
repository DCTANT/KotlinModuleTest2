package com.yalantis.ucrop.model;

import java.io.Serializable;

public class M_Img implements Serializable {
	/**
	 * 文件路径，绝对路径
	 */
	private String path;
	/**
	 * 图片主键（一般不使用）
	 */
	private String primaryKey;
	/**
	 * 图片的Url，一般是七牛图片url
	 */
	private String url;
	/**
	 * 图片名称（文件名）
	 */
	private String imgName;
	/**
	 * 是否选中
	 */
	private boolean isSelected;
	/**
	 * 选择后，是第几个选中的，即选择的顺序
	 */
	private int sequence;
	/**
	 * 图片类型
	 */
	private int viewType;
	/**
	 * 图片的id，在系统中储存的id
	 */
	private String id;
	/**
	 * 文件夹名称（一般不用）
	 */
	private String dirName;
	/**
	 * 文件夹id（一般不用）
	 */
	private String dirID;
	/**
	 * 是否是回显的图片
	 */
	private boolean isEcho;
	/**
	 * 图片标题（服务器中需要时使用，一般不用）
	 */
	private String title;

	public static final int PIC=0;//图片类型
	public static final int ADD=1;//增加图片按钮
	public static final int EMPTY=4;//空（不常用）



	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean selected) {
		isSelected = selected;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getDirID() {
		return dirID;
	}

	public void setDirID(String dirID) {
		this.dirID = dirID;
	}

	public boolean isEcho() {
		return isEcho;
	}

	public void setEcho(boolean echo) {
		isEcho = echo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	@Override
	public String toString() {
		return "M_Img{" +
				"path='" + path + '\'' +
				", primaryKey='" + primaryKey + '\'' +
				", url='" + url + '\'' +
				", imgName='" + imgName + '\'' +
				", isSelected=" + isSelected +
				", sequence=" + sequence +
				", viewType=" + viewType +
				", id='" + id + '\'' +
				", dirName='" + dirName + '\'' +
				", dirID='" + dirID + '\'' +
				", isEcho=" + isEcho +
				", title='" + title + '\'' +
				'}';
	}
}