package com.mov.mac.glko.bean;

import com.mov.mac.glko.manager.DownloadManager;
import com.mov.mac.glko.util.FileUtils;

public class DownloadInfo {
	private long id;//
	private String appName;//app��������
	private long appSize = 0 ;//app��size
	private long currentSize = 0;//��ǰ��size
	private long donwloadState = 0 ;//���ص�״̬
	private String url;//���صĵ�ַ
	private String path;//����·��
	
	public static DownloadInfo clone(AppInfo info){
		DownloadInfo downloadInfo = new DownloadInfo();
		downloadInfo.id = info.getId();
		downloadInfo.appName = info.getName();
		downloadInfo.appSize = info.getSize();
		downloadInfo.currentSize = 0;
		downloadInfo.donwloadState = DownloadManager.STATE_NONE;
		downloadInfo.url = info.getDownloadUrl();
		downloadInfo.path = FileUtils.getDownloadDir() + downloadInfo.appName + ".apk";
		return downloadInfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public long getAppSize() {
		return appSize;
	}

	public void setAppSize(long appSize) {
		this.appSize = appSize;
	}

	public long getCurrentSize() {
		return currentSize;
	}

	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}

	public long getDonwloadState() {
		return donwloadState;
	}

	public void setDonwloadState(long donwloadState) {
		this.donwloadState = donwloadState;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
}
