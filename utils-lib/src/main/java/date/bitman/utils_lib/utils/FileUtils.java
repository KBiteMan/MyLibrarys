package date.bitman.utils_lib.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import date.bitman.utils_lib.InitJar;

public class FileUtils {
	private static String mRoot;
	public static void init(String root){
		mRoot = root;
	}

	/**
	 * 获取内存卡下存储路径
	 */
	public static String getRootPath(String dirName) {
		String root = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String path = root + "/" + mRoot + "/" + dirName + "/";
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return path;
	}

	/** Bitmap保存为本地文件 */
	public static void write2File(Bitmap bitmap, String fileName) {
		File file = new File(fileName+".jpg");
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将assets文件复制到sd卡
	 * @param isCover 是否覆盖sd卡中文件
	 * @param source assets中文件名
	 * @param dest 目标路径
	 */
	public static void cpAssets2SD(boolean isCover, String source, String dest) {
		File file = new File(dest);
		if (isCover || (!isCover && !file.exists())) {
			InputStream is = null;
			FileOutputStream fos = null;
			Application application = null;
			try {
				application = InitJar.getInstance().getApplication();
				is = application.getAssets().open(source);
				String path = dest;
				fos = new FileOutputStream(path);
				byte[] buffer = new byte[1024];
				int size = 0;
				while ((size = is.read(buffer, 0, 1024)) >= 0) {
					fos.write(buffer, 0, size);
				}
				application.deleteFile(source);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
