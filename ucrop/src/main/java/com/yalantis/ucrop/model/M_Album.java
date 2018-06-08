package com.yalantis.ucrop.model;

public class M_Album {
	public M_Album() {
		super();
	}

	private String albumName;
	private String coverImgPath;
	private int totalCount;
	private boolean isSelected;

	public M_Album(String albumName, String coverImgPath, int totalCount, boolean isSelected) {
		super();
		this.albumName = albumName;
		this.coverImgPath = coverImgPath;
		this.totalCount = totalCount;
		this.isSelected = isSelected;
	}

	public String getAlbumName() {
		return albumName;
	}

	public String getCoverImgPath() {
		return coverImgPath;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public boolean getIsSelected() {
		return isSelected;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public void setCoverImgPath(String coverImgPath) {
		this.coverImgPath = coverImgPath;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public String toString() {
		return "M_Album{" +
				"albumName='" + albumName + '\'' + "coverImgPath='" + coverImgPath + '\'' + "totalCount='" + totalCount + '\'' + "isSelected='" + isSelected + '\'' + '}';
	}
}