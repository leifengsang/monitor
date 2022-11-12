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
	 * 普通表情id
	 */
	private int normalExpId;

	/**
	 * 手柄表情id
	 */
	private int ctrollerExpId;

	/**
	 * modelId
	 */
	private int modelId;

	/**
	 * 需要切成手柄状态的进程列表
	 */
	private String[] processList;

	/**
	 * obs进程
	 */
	private String obsProcess;

	/**
	 * 当前表情
	 */
	private int currentExp;

	public String getApiPath() {
		return apiPath;
	}

	public int getNormalExpId() {
		return normalExpId;
	}

	public int getCtrollerExpId() {
		return ctrollerExpId;
	}

	public int getModelId() {
		return modelId;
	}

	public String[] getProcessList() {
		return processList;
	}

	public String getObsProcess() {
		return obsProcess;
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
			System.out.println("读取配置文件失败");
			return false;
		}

		this.modelId = json.getIntValue("modelId");
		this.apiPath = json.getString("path");
		this.normalExpId = json.getIntValue("normalExpId");
		this.ctrollerExpId = json.getIntValue("ctrollerExpId");
		this.obsProcess = json.getString("obsProcess");
		JSONArray jsonArray = json.getJSONArray("process");
		String processList[] = null;
		String log = "";
		for (int i = 0; i < jsonArray.size(); i++) {
			String process = (String) jsonArray.get(i);
			processList = (String[]) ArrayUtils.add(processList, process);
			log += process + "\n";
		}
		this.processList = processList;

		System.out.println("加载配置文件成功");
		System.out.println("api端口：" + apiPath);
		System.out.println("模型id：" + modelId);
		System.out.println("监听游戏进程：" + log);
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
	 * 获得表情id
	 * @param exp
	 * @return
	 */
	public int getExpId(int exp) {
		switch (exp) {
		case EXP_NORMAL:
			return normalExpId;
		case EXP_CTROLLER:
			return ctrollerExpId;
		default:
			return -1;
		}
	}
}
