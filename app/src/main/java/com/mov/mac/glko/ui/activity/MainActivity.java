package com.mov.mac.glko.ui.activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.mov.mac.glko.R;
import com.mov.mac.glko.ui.holder.MainPagerAdapter;
import com.mov.mac.glko.ui.holder.MenuHolder;
import com.mov.mac.glko.util.UIUtils;

public class MainActivity extends BaseActivity {
	private static final String TAG = "MainActivity";
	//主页面
	private DrawerLayout mDrawerLayout;
	private ViewPager mPager;
	private MainPagerAdapter mAdapter;
	private ActionBar mActionBar;
	private ActionBarDrawerToggle mDrawerToggle;
	//菜单
	private FrameLayout mDrawer;
	private MenuHolder mMenuHolder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_main);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.ic_drawer_shadow, GravityCompat.START);
		mDrawerLayout.setDrawerListener(new DemoDrawerListener());//设置drawer的开关监听
		//菜单
		mDrawer = (FrameLayout) findViewById(R.id.start_drawer);
		mMenuHolder = new MenuHolder();
		mDrawer.addView(mMenuHolder.getRootView());
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mAdapter = new MainPagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mAdapter);
	};
	
	@Override
	protected void initActionBar() {
		mActionBar =getActionBar();
		mActionBar.setTitle(getString(R.string.app_name));
		mActionBar.setDisplayHomeAsUpEnabled(true);//设置显示左侧按钮
		mActionBar.setHomeButtonEnabled(true);//设置左侧按钮可点
		//初始化开关，并和drawer关联
		mDrawerToggle = new ActionBarDrawerToggle(
				this, mDrawerLayout,  R.drawable.ic_drawer,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();//该方法会自动和actionBar关联
	}
	
	/*drawer的监听*/
	private class DemoDrawerListener implements DrawerLayout.DrawerListener{

		@Override
		public void onDrawerClosed(View drawerView) {
			Log.i(TAG, "onDrawerClosed");
			mDrawerToggle.onDrawerClosed(drawerView);//需要把开关也变为关闭
		}

		@Override
		public void onDrawerOpened(View drawerView) {//打开drawer
			Log.i(TAG, "onDrawerOpened");
			mDrawerToggle.onDrawerOpened(drawerView);//需要把开关也变为打开
		}

		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {// drawer滑动的回调
			mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
		}

		@Override
		public void onDrawerStateChanged(int newState) {// drawer状态改变的回调
			mDrawerToggle.onDrawerStateChanged(newState);
		}
		
	}
	
	/*ViewPager的适配器*/
	public class MainPagerAdapter extends FragmentPagerAdapter{
		private String[] mTabTitle;

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
			mTabTitle = UIUtils.getStringArray(R.array.tab_names);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			return FragmentFactory.createFragment(position);
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabTitle[position];
		}

		@Override
		public int getCount() {
			return mTabTitle.length;
		}
		
	}
	/*菜单键点击的事件处理*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//在4.0以前，android通过菜单键来增加选项，在4.0后，提倡actionBar，所以菜单键增加的按钮可以显示到actionBar上，这里也能处理ActionBar上的菜单键事件
		return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
	}
}
