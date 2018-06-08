package com.yalantis.ucrop.model;

import java.io.Serializable;

/**
 * Created by Dechert on 2018-02-02.
 * Company: www.chisalsoft.co
 */

public class M_Settings implements Serializable{
	private int maxImgs;
	private int maxSize;
	private int width=800;
	private int height=800;
	private boolean isNeedCrop;
	private String savePath;
	private int flashMode;
	private boolean isSingleImg;
	private boolean isNeedPreview;
	private float aspectRatioX;
	private float aspectRatioY;
	private boolean isPreviewShowNumber;

	public int getMaxImgs() {
		return maxImgs;
	}

	public void setMaxImgs(int maxImgs) {
		this.maxImgs = maxImgs;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isNeedCrop() {
		return isNeedCrop;
	}

	public void setNeedCrop(boolean needCrop) {
		isNeedCrop = needCrop;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public int getFlashMode() {
		return flashMode;
	}

	public void setFlashMode(int flashMode) {
		this.flashMode = flashMode;
	}

	public boolean isSingleImg() {
		return isSingleImg;
	}

	public void setSingleImg(boolean singleImg) {
		isSingleImg = singleImg;
	}

	public boolean isNeedPreview() {
		return isNeedPreview;
	}

	public void setNeedPreview(boolean needPreview) {
		isNeedPreview = needPreview;
	}

	public float getAspectRatioX() {
		return aspectRatioX;
	}

	public void setAspectRatioX(float aspectRatioX) {
		this.aspectRatioX = aspectRatioX;
	}

	public float getAspectRatioY() {
		return aspectRatioY;
	}

	public void setAspectRatioY(float aspectRatioY) {
		this.aspectRatioY = aspectRatioY;
	}

	public boolean isPreviewShowNumber() {
		return isPreviewShowNumber;
	}

	public void setPreviewShowNumber(boolean previewShowNumber) {
		isPreviewShowNumber = previewShowNumber;
	}
}
