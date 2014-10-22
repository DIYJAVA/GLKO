package com.mov.mac.glko.http.protocol;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import android.os.SystemClock;

import com.mov.mac.glko.http.HttpHelper;
import com.mov.mac.glko.http.HttpHelper.HttpResult;
import com.mov.mac.glko.util.FileUtils;
import com.mov.mac.glko.util.IOUtils;
import com.mov.mac.glko.util.LogUtils;
import com.mov.mac.glko.util.StringUtils;

public abstract class BaseProtocol<Data> {
	public static final String cachePath = "";

	public Data load(int index) throws FileNotFoundException {
		SystemClock.sleep(1000);// 睡眠1秒，防止加载过快，看不到界面变化效果
		String json = null;
		// 1,从本地缓存读取数据，查看缓存时间
		json = loadFromLocal(index);
		// 如果缓存时间过期，从网络加载
		if (StringUtils.isEmpty(json)) {
			json = loadFromNet(index);
			if (json == null) {
				// 网络出错
				return null;
			} else {
				// 3,把数据保存到本地
				saveToLocal(json, index);
			}
		}
		return parseFromJson(json);
	}

	// 从网络加载
	private String loadFromNet(int index) {
		String result = null;
		HttpResult httpResult = HttpHelper.get(HttpHelper.URL + getKey()
				+ "?index=" + index + getParames());
		if(httpResult != null){
			result = httpResult.getString();
			httpResult.close();
		}
		return result;
	}

	//保存到本地
	private void saveToLocal(String json, int index) {
		String path = FileUtils.getCacheDir();
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(path + getKey() + "_" + index + getParames()));
			long time = System.currentTimeMillis() + 1000 * 60 ;//先计算出过期时间，写入第一行
			writer.write(time + "\r\n");
			writer.write(json.toCharArray());
			writer.flush();
		} catch (Exception e) {
			LogUtils.e(e);
		}finally{
			IOUtils.close(writer);
		}
	}

	// 从本地加载协议
	private String loadFromLocal(int index) {
		String path = FileUtils.getCacheDir();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(path + getKey() + index
					+ getParames()));
			String line = reader.readLine();// 第一行是时间
			Long time = Long.valueOf(line);
			if (time > System.currentTimeMillis()) {// 如果时间未过期
				StringBuffer sb = new StringBuffer();
				String result;
				while ((result = reader.readLine()) != null) {
					sb.append(result);
				}
				return sb.toString();
			}
		} catch (Exception ex) {
			LogUtils.e(ex);
		} finally {
			IOUtils.close(reader);
		}
		return null;
	}

	/* 需要增加的额外参数 */
	protected String getParames() {
		return "";
	}

	/* 该协议的访问地址 */
	protected abstract String getKey();

	protected abstract Data parseFromJson(String json);
}
