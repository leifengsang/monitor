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
	 * ��ʼ�����л����������
	 */
	private void init() {
		
	}

	/**
	 * ����ص�
	 * @param json
	 */
	public void processCallBack(JSONObject json) {

	}
	
	
}
