import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.ArrayUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 数据集合
 * @author leifengsang
 */
public class Model {

	/**
	 * 配置文件路径
	 */
	public static final String CONFIG_PATH = "config.json";

	/**
	 * 表情
	 */
	public static final int EXP_NORMAL = 1;
	public static final int EXP_CTROLLER = 2;

	private static Model instance;

	public static Model getInstance() {
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	/**
	 * api路径
	 */
	private String apiPath;

	/**
	 * 普通表情名称
	 */
	private String normalExp;

	/**
	 * 手柄表情名称
	 */
	private String ctrollerExp;

	/**
	 * modelId
	 */
	private int modelId;

	/**
	 * 需要切成手柄状态的进程列表
	 */
	private String[] processList;

	/**
	 * 当前表情
	 */
	private int currentExp;

	public String getApiPath() {
		return apiPath;
	}

	public String getNormalExp() {
		return normalExp;
	}

	public String getCtrollerExp() {
		return ctrollerExp;
	}

	public int getModelId() {
		return modelId;
	}

	public String[] getProcessList() {
		return processList;
	}

	public int getCurrentExp() {
		return currentExp;
	}

	public void setCurrentExp(int currentExp) {
		this.currentExp = currentExp;
	}

	/**
	 * 加载配置
	 * return success
	 */
	public boolean load() {
		JSONObject json = getConfigJson();
		if (json == null) {
			return false;
		}

		this.modelId = json.getIntValue("modelId");
		this.apiPath = json.getString("path");
		this.normalExp = json.getString("normalExp");
		this.ctrollerExp = json.getString("ctrollerExp");
		JSONArray jsonArray = json.getJSONArray("process");
		String processList[] = null;
		for (int i = 0; i < jsonArray.size(); i++) {
			String process = (String) jsonArray.get(i);
			processList = (String[]) ArrayUtils.add(processList, process);
		}
		this.processList = processList;
		return true;
	}

	/**
	 * 读取配置文件
	 * @return
	 */
	public JSONObject getConfigJson() {
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

	/**
	 * 获得表情名称
	 * @param exp
	 * @return
	 */
	public String getExpName(int exp) {
		switch (exp) {
		case EXP_NORMAL:
			return normalExp;
		case EXP_CTROLLER:
			return ctrollerExp;
		default:
			return "";
		}
	}
}
