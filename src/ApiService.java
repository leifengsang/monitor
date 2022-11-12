import com.alibaba.fastjson.JSONObject;

/**
 * service
 * @author leifengsang
 */
public class ApiService {

	MagicWebSocketClient wsClient;

	public ApiService(String path) {
		try {
			wsClient = new MagicWebSocketClient(path, this);
			wsClient.connect();
			init();
		} catch (Exception e) {
			//TODO log
			e.printStackTrace();
		}
	}

	/**
	 * 初始化：切换到普通表情
	 */
	private void init() {
		changeExp(Model.EXP_NORMAL);
	}

	/**
	 * 切换表情
	 * @param exp
	 */
	private void changeExp(int exp) {
		//表情一样，没必要切
		if (Model.getInstance().getCurrentExp() == exp) {
			return;
		}

		Model.getInstance().setCurrentExp(exp);
		JSONObject json = new JSONObject();
		json.put("msg", 13300);
		json.put("msgId", 1);
		JSONObject data = new JSONObject();
		data.put("id", Model.getInstance().getModelId());
		data.put("expId", Model.getInstance().getExpName(exp));
		json.put("data", data);
		wsClient.send(json.toJSONString());
	}

	/**
	 * 检查游戏进程是否存在 后续逻辑
	 * @param exists
	 */
	public void checkGameProcess(boolean exists) {
		int exp = exists ? Model.EXP_CTROLLER : Model.EXP_NORMAL;
		changeExp(exp);
	}

	/**
	 * 处理回调
	 * @param json
	 */
	public void processCallBack(JSONObject json) {

	}

	public void close() {
		//TODO log
		//干掉所有线程
		SingleThreadPool.getInstance().close();

		//干掉wsClient
		if (wsClient != null) {
			wsClient.close();
		}

		System.exit(0);
	}
}
