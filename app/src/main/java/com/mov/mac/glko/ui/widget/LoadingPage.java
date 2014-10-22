package com.mov.mac.glko.ui.widget;

import com.mov.mac.glko.R;
import com.mov.mac.glko.util.UIUtils;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class LoadingPage extends FrameLayout {
	private static final int STATE_UNLOADED = 0;// 未知状态
	private static final int STATE_LOADING = 1;// 加载状态
	private static final int STATE_ERROR = 3;// 加载完毕，但是出错状态
	private static final int STATE_EMPTY = 4;// 加载完毕，但是没有数据状态
	private static final int STATE_SUCCEED = 5;// 加载成功

	private View mLoadingView;//加载时显示的View
	private View mErrorView;//加载出错显示的View
	private View mEmptyView;//加载没有数据显示的View
	private View mSucceedView;//加载成功显示的View
	private int mState;//当前的状态，显示需要根据该状态判断
	
	public LoadingPage(Context context) {
		super(context);
		init();
	}

	private void init() {
		setBackgroundColor(UIUtils.getColor(R.color.bg_page));//设置背景
		mState = STATE_UNLOADED;//初始化状态
		
		//创建对应的View,并添加到布局中
		mLoadingView = createLoadingView();
		if(null != mLoadingView){
			addView(mLoadingView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		mErrorView = createErrorView();
		if(null != mErrorView){
			addView(mErrorView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		mEmptyView = createEmptyView();
		if(null != mEmptyView){
			addView(mEmptyView,new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		
		//显示对应的View
		showPageSafe();
	}
	
	/** 线程安全的方法 */
	private void showPageSafe() {
		UIUtils.runInMainThread(new Runnable() {
			@Override
			public void run() {
				showPage();
			}
		});
	}
	
	private void showPage(){
		
	}

	private View createEmptyView() {
		return null;
	}

	private View createErrorView() {
		return null;
	}

	private View createLoadingView() {
		return null;
	}

}
