package com.mov.mac.glko.http.protocol;

import java.util.List;

import com.mov.mac.glko.bean.UserInfo;

public class UserProtocol extends BaseProtocol<List<UserInfo>>{

	@Override
	protected String getKey() {
		return null;
	}

	@Override
	protected List<UserInfo> parseFromJson(String json) {
		return null;
	}

}
