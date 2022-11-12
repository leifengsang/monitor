import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * �����ļ�����
 * @author leifengsang
 */
public class ConfigUtil {

	/**
	 * �����ļ���ַ
	 */
	public static final String CONFIG_PATH = "config.json";

	private static ConfigUtil instance;

	public static ConfigUtil getInstance() {
		if (instance == null) {
			instance = new ConfigUtil();
		}
		return instance;
	}

	/**
	 * api·��
	 */
	private String path;

	/**
	 * ��ͨ��������
	 */
	private String normalExp;

	/**
	 * �ֱ���������
	 */
	private String ctrollerExp;

	/**
	 * modelId ��0��ʼ
	 */
	private int modelId;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNormalExp() {
		return normalExp;
	}

	public void setNormalExp(String normalExp) {
		this.normalExp = normalExp;
	}

	public String getCtrollerExp() {
		return ctrollerExp;
	}

	public void setCtrollerExp(String ctrollerExp) {
		this.ctrollerExp = ctrollerExp;
	}

	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	/**
	 * ���������ļ�
	 * return success
	 */
	public boolean load() {
		JSONObject json = getConfigJson();
		if (json == null) {
			return false;
		}

		setModelId(json.getIntValue("modelId"));
		setPath(json.getString("path"));
		setNormalExp("normalExp");
		setCtrollerExp("ctrollerExp");
		return true;
	}

	/**
	 * ��ȡ�����ļ�
	 * @return
	 */
	public static JSONObject getConfigJson() {
		BufferedReader reader = null;
		String jsonStr = "";
		try {
			FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				jsonStr += tempString;
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
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			return jsonObject;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
