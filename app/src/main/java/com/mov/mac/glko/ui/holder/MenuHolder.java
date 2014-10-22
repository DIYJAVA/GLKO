package com.mov.mac.glko.ui.holder;

import java.util.List;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gp001.R;
import com.example.gp001.bean.UserInfo;
import com.example.gp001.http.image.ImageLoader;
import com.example.gp001.http.protocol.UserProtocol;
import com.example.gp001.manager.ThreadManager;
import com.example.gp001.util.UIUtils;

public class MenuHolder extends BaseHolder<UserInfo> implements OnClickListener {
	private RelativeLayout mHomeLayout, mSettingLayout, mThemeLayout,
			mScansLayout, mFeedbackLayout, mUpdatesLayout, mAboutLayout,
			mExitLayout, mPhotoLayout;
	private ImageView mPhoto;
	private TextView mTvUserName, mTvUserEmail;
	private UserInfo mInfo;

	@Override
	protected View initView() {
		View view = UIUtils.inflate(R.layout.menu_list);
		mPhotoLayout = (RelativeLayout) view.findViewById(R.id.photo_layout);
		mPhotoLayout.setOnClickListener(this);

		mHomeLayout = (RelativeLayout) view.findViewById(R.id.home_layout);
		mHomeLayout.setOnClickListener(this);

		mSettingLayout = (RelativeLayout) view
				.findViewById(R.id.setting_layout);
		mSettingLayout.setOnClickListener(this);

		mThemeLayout = (RelativeLayout) view.findViewById(R.id.theme_layout);
		mThemeLayout.setOnClickListener(this);

		mScansLayout = (RelativeLayout) view.findViewById(R.id.scans_layout);
		mScansLayout.setOnClickListener(this);

		mFeedbackLayout = (RelativeLayout) view
				.findViewById(R.id.feedback_layout);
		mFeedbackLayout.setOnClickListener(this);

		mUpdatesLayout = (RelativeLayout) view
				.findViewById(R.id.updates_layout);
		mUpdatesLayout.setOnClickListener(this);

		mAboutLayout = (RelativeLayout) view.findViewById(R.id.about_layout);
		mAboutLayout.setOnClickListener(this);

		mExitLayout = (RelativeLayout) view.findViewById(R.id.exit_layout);
		mExitLayout.setOnClickListener(this);

		mPhoto = (ImageView) view.findViewById(R.id.image_photo);
		mTvUserName = (TextView) view.findViewById(R.id.user_name);
		mTvUserEmail = (TextView) view.findViewById(R.id.user_email);

		return view;
	}

	@Override
	public void refreshView() {
		if (mInfo != null) {
			ImageLoader.load(mPhoto, mInfo.getUrl());
			mTvUserName.setText(mInfo.getName());
			mTvUserEmail.setText(mInfo.getEmail());
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_layout:
			UIUtils.showToastSafe(R.string.tv_home);
			break;
		case R.id.setting_layout:
			UIUtils.showToastSafe(R.string.tv_setting);
			break;
		case R.id.theme_layout:
			UIUtils.showToastSafe(R.string.tv_theme);
			break;
		case R.id.scans_layout:
			UIUtils.showToastSafe(R.string.tv_scans);
			break;
		case R.id.feedback_layout:
			UIUtils.showToastSafe(R.string.tv_feedback);
			break;
		case R.id.updates_layout:
			UIUtils.showToastSafe(R.string.tv_updates);
			break;
		case R.id.about_layout:
			UIUtils.showToastSafe(R.string.tv_about);
			break;
		case R.id.exit_layout:
			UIUtils.showToastSafe(R.string.tv_exit);
			break;
		case R.id.photo_layout:
			ThreadManager.getLongPool().execute(new Runnable() {
				@Override
				public void run() {
					try {
						UserProtocol protocol = new UserProtocol();
						List<UserInfo> loadInfo = protocol.load(0);
						if (loadInfo != null && loadInfo.size() > 0) {
							mInfo = loadInfo.get(0);
							UIUtils.runInMainThread(new Runnable() {
								@Override
								public void run() {
									refreshView();
								}
							});
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
			break;
		default:
			break;
		}
	}

}