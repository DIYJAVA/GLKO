package com.mov.mac.glko.ui.fragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {
	public static final int TAB_HOME = 0;
	public static final int TAB_APP = 1;
	public static final int TAB_GAME = 2;
	public static final int TAB_SUBJECT = 3;
	public static final int TAB_RECOMMEND = 4;
	public static final int TAB_CATEGORY = 5;
	public static final int TAB_TOP = 6;
	/** 记录所有的fragment，防止重复创建 */
	private static Map<Integer,BaseFragment> mFragmentMap = new HashMap<Integer,BaseFragment>();
}
