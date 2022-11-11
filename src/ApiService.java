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
	 * 初始化：切换到常规表情
	 */
	private void init() {
		
	}

	/**
	 * 处理回调
	 * @param json
	 */
	public void processCallBack(JSONObject json) {

	}
	
	
}
