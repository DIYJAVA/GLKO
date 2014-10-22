package com.mov.mac.glko.handler;


public class MyHandler {
	Mylooper mLooper;
	
	public MyHandler() throws Exception{
		ThreadLocal local = new ThreadLocal();
		if(local.get() == null){
			throw new Exception("没有创建looper");
		}
		Mylooper looper = (Mylooper) local.get();
		mLooper = looper;
	}
	
	public MyHandler(Mylooper looper){
		mLooper = looper;
	}

	public void sendMessage(MyMessage msg){
		msg.myHandler = this;
		mLooper.put(msg);
	}
	
	public void handleMessage(MyMessage msg){
	}
}
