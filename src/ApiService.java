import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.alibaba.fastjson.JSONObject;

/**
 * service
 * @author leifengsang
 */
public class ApiService {

	/**
	 * wsClient
	 */
	private MagicWebSocketClient wsClient;

	/**
	 * 系统托盘
	 */
	private MagicTrayIcon trayIcon;

	public ApiService(String path, MagicTrayIcon trayIcon) {
		this.trayIcon = trayIcon;

		//重新加载配置文件
		trayIcon.addMenuItem("reload", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Model.getInstance().load();
			}
		});

		//退出
		trayIcon.addMenuItem("exit", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		try {
			wsClient = new MagicWebSocketClient(path, this);
			wsClient.connect();
		} catch (Exception e) {
			System.out.println("连接wsClient失败");
			e.printStackTrace();
		}
	}

	/**
	 * 初始化：切换到普通表情
	 */
	public void init() {
		trayIcon.displayMassage("初始化成功");
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

		System.out.println("切换表情，id：" + exp);

		Model.getInstance().setCurrentExp(exp);
		JSONObject json = new JSONObject();
		json.put("msg", 13300);
		json.put("msgId", 1);
		JSONObject data = new JSONObject();
		data.put("id", Model.getInstance().getModelId());
		data.put("expId", Model.getInstance().getExpId(exp));
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
		System.out.println("收到wsClient数据：" + json.toString());
	}

	public void close() {
		System.out.println("开始关闭所有程序");
		//干掉所有线程
		SingleThreadPool.getInstance().close();

		//干掉wsClient
		if (wsClient != null) {
			wsClient.close();
		}
		
		if(trayIcon != null){
			
		}

		System.exit(0);
	}
}
