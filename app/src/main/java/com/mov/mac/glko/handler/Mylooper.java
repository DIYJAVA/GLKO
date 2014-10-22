package com.mov.mac.glko.handler;

import java.util.LinkedList;


public class Mylooper {
	private static  LinkedList<MyMessage> list = new LinkedList<MyMessage>(); 
	
	public Mylooper(){
	}
	
	public static void prepare(){
		ThreadLocal local = new ThreadLocal();
		local.set(new Mylooper());
	}
	
	public static void loop() throws InterruptedException{
		while (true) {
			MyMessage msg = list.pollFirst();
			if(msg == null){
				list.wait();
			}else{
				msg.myHandler.handleMessage(msg);
			}
		}
	}
	
	public void put(MyMessage msg){
		list.addLast(msg);
		list.notify();
	}
	

}
