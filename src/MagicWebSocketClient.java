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

	private ApiService apiService;

	public MagicWebSocketClient(String serverUri, ApiService apiService) throws URISyntaxException {
		super(new URI(serverUri));

		this.apiService = apiService;
	}

	public void onClose(int arg0, String arg1, boolean arg2) {
		if (arg2) {
			magiReconnect();
		} else {
			System.out.println("wsClient关闭");
		}

	}

	public void onError(Exception e) {
		System.out.println("wsClient onError");
		e.printStackTrace();
	}

	public void onMessage(String result) {
		apiService.processCallBack(JSONObject.parseObject(result));
	}

	public void onOpen(ServerHandshake e) {
		apiService.init();
	}

	@Override
	public void send(String text) {
		System.out.println("向wsClient发送数据：" + text);
		super.send(text);
	}

	private void magiReconnect() {
		SingleThreadPool.getInstance().scheduledThreadPool().submit(new Runnable() {

			public void run() {
				try {
					boolean result = reconnectBlocking();
					if (result) {
						System.out.println("重连成功");
					} else {
						System.out.println("重连失败");
					}
				} catch (InterruptedException e) {
					System.out.println("重连失败");
					e.printStackTrace();
				}
			}
		});
	}

}
