package com.mov.mac.glko.http.image;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.mov.mac.glko.manager.ThreadManager;
import com.mov.mac.glko.manager.ThreadManager.ThreadPoolProxy;
import com.mov.mac.glko.util.DrawableUtils;
import com.mov.mac.glko.util.StringUtils;
import com.mov.mac.glko.util.SystemUtils;

public class ImageLoader {
	/*图片下载的线程名称*/
	public static final String THREAD_POOL_NAME = "IMAGE_THREAD_POOL";
	/*图片缓存最大数量*/
	public static final int MAX_DRAWABLE_COUNT = 100;
	/*图片的key缓存*/
	private static ConcurrentLinkedQueue<String> mKeyCache = new 
			ConcurrentLinkedQueue<String>();
	/*图片的缓存*/
	private static Map<String,Drawable> mDrawableCache = new ConcurrentHashMap<String, Drawable>();
	private static BitmapFactory.Options mOptions = new 
			BitmapFactory.Options();
	/*图片下载的线程池*/
	private static ThreadPoolProxy mThreadPool = ThreadManager.getSinglePool(THREAD_POOL_NAME);
	/*用于记录图片下载的任务，以便取消*/
	private static ConcurrentHashMap<String, Runnable> mMapRunnable =new ConcurrentHashMap<String, Runnable>();
	/*图片的总大小*/
	private static long mTotalSize;
	
	static{
		mOptions.inDither = false;//设置为false,将不考虑图片的抖动 值，这会减少图片的内存占用
		mOptions.inPurgeable = true;//设置为true,表示允许系统在内存不足时，删除bitmap数组
		mOptions.inInputShareable = true;//和inPurgeable配合使用，如果inPurgeable是FALSE，那么该值被忽略，表示是否对bitmap的数组进行共享
	}
	
	/*加载图片*/
	public static void load(ImageView view,String url){
		if(view == null || StringUtils.isEmpty(url)){
			return ;
		}
		view.setTag(url);//把控件和图片的url进行绑定，因为加载是一个耗时的，等加载完毕了需要判定该控件是否和该url匹配
		Drawable drawable = loadFromMemory(url);//从内存中加载
	}

	/*从内存中加载*/
	private static Drawable loadFromMemory(String url) {
		Drawable drawable = mDrawableCache.get(url);
		if(drawable != null){
			//从内存中获取到了，需要重新放到内存队列的最后，以便满足LRC
			//一般缓存算法有两种，第一是LFU，按使用次数来判定删除优先级，使用次数最少的最先删除
			//还有一个就是LRC，就是按最后使用时间来判定删除优先级，最后使用时间越早的最先删除
			addDrawableToMemory(url,drawable);
		}
		return drawable;
	}

	/*添加到内存*/
	private static void addDrawableToMemory(String url, Drawable drawable) {
		mKeyCache.remove(url);
		mDrawableCache.remove(url);
		//如果大于等于100张，或者图片的总大小大于应用总内存的四分之一先删除前面的
		while(mKeyCache.size() >= MAX_DRAWABLE_COUNT || mTotalSize >= SystemUtils.getOneAppMaxMemory()/4){
			String firstUrl = mKeyCache.remove();
			Drawable remove = mDrawableCache.remove(firstUrl);
			mTotalSize -= DrawableUtils.getDrawableSize(remove);
		}
		mKeyCache.add(url);//添加
		mDrawableCache.put(url, drawable);
		mTotalSize += DrawableUtils.getDrawableSize(drawable);
	}
}
