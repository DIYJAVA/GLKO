package com.mov.mac.glko.handler;

import android.os.SystemClock;
import android.test.AndroidTestCase;

public class Test extends AndroidTestCase{
	
	public void  handler() throws Exception{
		Mylooper.prepare();
		final MyHandler handler =new MyHandler(){
			@Override
			public void handleMessage(MyMessage msg) {
				switch (msg.what){
				case 1:
					System.out.println("屏幕被触摸");
					break;
				case 2:
					System.out.println("访问网络");
					break;
				case 3:
					System.out.println("电话打进了");
					break;
				case 4:
					System.out.println("访问网络结束");
					break;
				default:
					break;
				}
			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(5000);
					MyMessage msg = new MyMessage();
					msg.what = 1;
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(5000);
					MyMessage msg = new MyMessage();
					msg.what = 2;
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(5000);
					MyMessage msg = new MyMessage();
					msg.what = 3;
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(5000);
					MyMessage msg = new MyMessage();
					msg.what = 4;
				}
			}
		}).start();
		
		Mylooper.loop();
		
	}
}
