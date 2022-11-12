import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import com.alibaba.fastjson.JSONObject;

/**
 * webSocket
 * @author leifengsang
 */
public class MagicWebSocketClient extends WebSocketClient {

	ApiService apiService;

	public MagicWebSocketClient(String serverUri, ApiService apiService) throws URISyntaxException {
		super(new URI(serverUri));

		this.apiService = apiService;
	}

	public void onClose(int arg0, String arg1, boolean arg2) {
		if (arg2) {
			magiReconnect();
		} else {
			//TODO log
		}

	}

	public void onError(Exception e) {
		//TODO log
		e.printStackTrace();
	}

	public void onMessage(String result) {
		apiService.processCallBack(JSONObject.parseObject(result));
	}

	public void onOpen(ServerHandshake e) {

	}

	private void magiReconnect() {
		SingleThreadPool.getInstance().scheduledThreadPool().submit(new Runnable() {

			public void run() {
				try {
					boolean result = reconnectBlocking();
					if (result) {
						//TODO send reconnect massage
					} else {
						//TODO log
					}
				} catch (InterruptedException e) {
					//TODO log
					e.printStackTrace();
				}
			}
		});
	}

}
