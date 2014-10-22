package com.mov.mac.glko.bean;

import java.util.ArrayList;
import java.util.List;

import com.mov.mac.glko.ui.holder.BaseHolder;

public class BaseInfo {
	private List<BaseHolder<BaseInfo>> holders = 
			new ArrayList<BaseHolder<BaseInfo>>();
	
	public void bindHolder(BaseHolder<BaseInfo>holder){
		synchronized (holders) {
			if(!holders.contains(holder)){
				holders.add(holder);
			}
		}
	}
	
	public void unBindHolder(BaseHolder<BaseInfo> hodler){
		synchronized (holders) {
			if(holders.contains(holders)){
				holders.remove(holders);
			}
		}
	}
	
	protected void  onDataChanged(){
		synchronized (holders) {
			for (BaseHolder<BaseInfo> holder:holders) {
				holder.refreshView();
			}
		}
	}
	
}
