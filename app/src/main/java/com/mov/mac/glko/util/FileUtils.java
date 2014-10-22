package com.mov.mac.glko.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import android.os.Environment;

public class FileUtils {
		public static final String ROOT_DIR = "root";
		public static final String DOWNLOAD_DIR = "download";
		public static final String CACHE_DIR = "cache";
		public static final String ICON_DIR = "icon";

		/** �ж�SD���Ƿ���� */
		public static boolean isSDCardAvailable() {
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				return true;
			} else {
				return false;
			}
		}

		/** ��ȡ����Ŀ¼ */
		public static String getDownloadDir() {
			return getDir(DOWNLOAD_DIR);
		}

		/** ��ȡ����Ŀ¼ */
		public static String getCacheDir() {
			return getDir(CACHE_DIR);
		}

		/** ��ȡiconĿ¼ */
		public static String getIconDir() {
			return getDir(ICON_DIR);
		}

		/** ��ȡӦ��Ŀ¼����SD������ʱ����ȡSD���ϵ�Ŀ¼����SD��������ʱ����ȡӦ�õ�cacheĿ¼ */
		public static String getDir(String name) {
			StringBuilder sb = new StringBuilder();
			if (isSDCardAvailable()) {
				sb.append(getExternalStoragePath());
			} else {
				sb.append(getCachePath());
			}
			sb.append(name);
			sb.append(File.separator);
			String path = sb.toString();
			if (createDirs(path)) {
				return path;
			} else {
				return null;
			}
		}

		/** ��ȡSD�µ�Ӧ��Ŀ¼ */
		public static String getExternalStoragePath() {
			StringBuilder sb = new StringBuilder();
			sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
			sb.append(File.separator);
			sb.append(ROOT_DIR);
			sb.append(File.separator);
			return sb.toString();
		}

		/** ��ȡӦ�õ�cacheĿ¼ */
		public static String getCachePath() {
			File f = UIUtils.getContext().getCacheDir();
			if (null == f) {
				return null;
			} else {
				return f.getAbsolutePath() + "/";
			}
		}

		/** �����ļ��� */
		public static boolean createDirs(String dirPath) {
			File file = new File(dirPath);
			if (!file.exists() || !file.isDirectory()) {
				return file.mkdirs();
			}
			return true;
		}

		/** �����ļ�������ѡ���Ƿ�ɾ��Դ�ļ� */
		public static boolean copyFile(String srcPath, String destPath, boolean deleteSrc) {
			File srcFile = new File(srcPath);
			File destFile = new File(destPath);
			return copyFile(srcFile, destFile, deleteSrc);
		}

		/** �����ļ�������ѡ���Ƿ�ɾ��Դ�ļ� */
		public static boolean copyFile(File srcFile, File destFile, boolean deleteSrc) {
			if (!srcFile.exists() || !srcFile.isFile()) {
				return false;
			}
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);
				byte[] buffer = new byte[1024];
				int i = -1;
				while ((i = in.read(buffer)) > 0) {
					out.write(buffer, 0, i);
					out.flush();
				}
				if (deleteSrc) {
					srcFile.delete();
				}
			} catch (Exception e) {
				LogUtils.e(e);
				return false;
			} finally {
				IOUtils.close(out);	
				IOUtils.close(in);
			}
			return true;
		}

		/** �ж��ļ��Ƿ��д */
		public static boolean isWriteable(String path) {
			try {
				if (StringUtils.isEmpty(path)) {
					return false;
				}
				File f = new File(path);
				return f.exists() && f.canWrite();
			} catch (Exception e) {
				LogUtils.e(e);
				return false;
			}
		}

		/** �޸��ļ���Ȩ��,����"777"�� */
		public static void chmod(String path, String mode) {
			try {
				String command = "chmod " + mode + " " + path;
				Runtime runtime = Runtime.getRuntime();
				runtime.exec(command);
			} catch (Exception e) {
				LogUtils.e(e);
			}
		}

		/**
		 * �����д���ļ�
		 * @param is       �����
		 * @param path     �ļ�·��
		 * @param recreate ����ļ����ڣ��Ƿ���Ҫɾ���ؽ�
		 * @return �Ƿ�д��ɹ�
		 */
		public static boolean writeFile(InputStream is, String path, boolean recreate) {
			boolean res = false;
			File f = new File(path);
			FileOutputStream fos = null;
			try {
				if (recreate && f.exists()) {
					f.delete();
				}
				if (!f.exists() && null != is) {
					File parentFile = new File(f.getParent());
					parentFile.mkdirs();
					int count = -1;
					byte[] buffer = new byte[1024];
					fos = new FileOutputStream(f);
					while ((count = is.read(buffer)) != -1) {
						fos.write(buffer, 0, count);
					}
					res = true;
				}
			} catch (Exception e) {
				LogUtils.e(e);
			} finally {
				IOUtils.close(fos);
				IOUtils.close(is);
			}
			return res;
		}

		/**
		 * ���ַ����д���ļ�
		 * @param content ��Ҫд����ַ�
		 * @param path    �ļ�·�����
		 * @param append  �Ƿ�����ӵ�ģʽд��
		 * @return �Ƿ�д��ɹ�
		 */
		public static boolean writeFile(byte[] content, String path, boolean append) {
			boolean res = false;
			File f = new File(path);
			RandomAccessFile raf = null;
			try {
				if (f.exists()) {
					if (!append) {
						f.delete();
						f.createNewFile();
					}
				} else {
					f.createNewFile();
				}
				if (f.canWrite()) {
					raf = new RandomAccessFile(f, "rw");
					raf.seek(raf.length());
					raf.write(content);
					res = true;
				}
			} catch (Exception e) {
				LogUtils.e(e);
			} finally {
				IOUtils.close(raf);
			}
			return res;
		}

		/**
		 * ���ַ����д���ļ�
		 * @param content ��Ҫд����ַ�
		 * @param path    �ļ�·�����
		 * @param append  �Ƿ�����ӵ�ģʽд��
		 * @return �Ƿ�д��ɹ�
		 */
		public static boolean writeFile(String content, String path, boolean append) {
			return writeFile(content.getBytes(), path, append);
		}

		/**
		 * �Ѽ�ֵ��д���ļ�
		 * @param filePath �ļ�·��
		 * @param key      ��
		 * @param value    ֵ
		 * @param comment  �ü�ֵ�Ե�ע��
		 */
		public static void writeProperties(String filePath, String key, String value, String comment) {
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
				return;
			}
			FileInputStream fis = null;
			FileOutputStream fos = null;
			File f = new File(filePath);
			try {
				if (!f.exists() || !f.isFile()) {
					f.createNewFile();
				}
				fis = new FileInputStream(f);
				Properties p = new Properties();
				p.load(fis);// �ȶ�ȡ�ļ����ٰѼ�ֵ��׷�ӵ�����
				p.setProperty(key, value);
				fos = new FileOutputStream(f);
				p.store(fos, comment);
			} catch (Exception e) {
				LogUtils.e(e);
			} finally {
				IOUtils.close(fis);
				IOUtils.close(fos);
			}
		}

		/** ���ֵ��ȡ */
		public static String readProperties(String filePath, String key, String defaultValue) {
			if (StringUtils.isEmpty(key) || StringUtils.isEmpty(filePath)) {
				return null;
			}
			String value = null;
			FileInputStream fis = null;
			File f = new File(filePath);
			try {
				if (!f.exists() || !f.isFile()) {
					f.createNewFile();
				}
				fis = new FileInputStream(f);
				Properties p = new Properties();
				p.load(fis);
				value = p.getProperty(key, defaultValue);
			} catch (IOException e) {
				LogUtils.e(e);
			} finally {
				IOUtils.close(fis);
			}
			return value;
		}

		/** ���ַ��ֵ�Ե�mapд���ļ� */
		public static void writeMap(String filePath, Map<String, String> map, boolean append, String comment) {
			if (map == null || map.size() == 0 || StringUtils.isEmpty(filePath)) {
				return;
			}
			FileInputStream fis = null;
			FileOutputStream fos = null;
			File f = new File(filePath);
			try {
				if (!f.exists() || !f.isFile()) {
					f.createNewFile();
				}
				Properties p = new Properties();
				if (append) {
					fis = new FileInputStream(f);
					p.load(fis);// �ȶ�ȡ�ļ����ٰѼ�ֵ��׷�ӵ�����
				}
				p.putAll(map);
				fos = new FileOutputStream(f);
				p.store(fos, comment);
			} catch (Exception e) {
				LogUtils.e(e);
			} finally {
				IOUtils.close(fis);
				IOUtils.close(fos);
			}
		}

		/** ���ַ��ֵ�Ե��ļ�����map */
		@SuppressWarnings({"rawtypes", "unchecked"})
		public static Map<String, String> readMap(String filePath, String defaultValue) {
			if (StringUtils.isEmpty(filePath)) {
				return null;
			}
			Map<String, String> map = null;
			FileInputStream fis = null;
			File f = new File(filePath);
			try {
				if (!f.exists() || !f.isFile()) {
					f.createNewFile();
				}
				fis = new FileInputStream(f);
				Properties p = new Properties();
				p.load(fis);
				map = new HashMap<String, String>((Map) p);// ��Ϊproperties�̳���map������ֱ��ͨ��p������һ��map
			} catch (Exception e) {
				LogUtils.e(e);
			} finally {
				IOUtils.close(fis);
			}
			return map;
		}

		/** ���� */
		public static boolean copy(String src, String des, boolean delete) {
			File file = new File(src);
			if (!file.exists()) {
				return false;
			}
			File desFile = new File(des);
			FileInputStream in = null;
			FileOutputStream out = null;
			try {
				in = new FileInputStream(file);
				out = new FileOutputStream(desFile);
				byte[] buffer = new byte[1024];
				int count = -1;
				while ((count = in.read(buffer)) != -1) {
					out.write(buffer, 0, count);
					out.flush();
				}
			} catch (Exception e) {
				LogUtils.e(e);
				return false;
			} finally {
				IOUtils.close(in);
				IOUtils.close(out);
			}
			if (delete) {
				file.delete();
			}
			return true;
		}
}
