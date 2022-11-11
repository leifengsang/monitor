import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author leifengsang
 */
public class Main {

	/**
	 * 配置文件地址
	 */
	public static final String CONFIG_PATH = "config.json";

	public static void main(String[] args) {
		JSONObject configJson = getConfigJson();
		if (configJson == null) {
			//TODO alert
			return;
		}
	}

	/**
	 * 读取配置文件
	 * @return
	 */
	public static JSONObject getConfigJson() {
		BufferedReader reader = null;
		String readJson = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				readJson += tempString;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			JSONObject jsonObject = JSONObject.parseObject(readJson);
			return jsonObject;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}