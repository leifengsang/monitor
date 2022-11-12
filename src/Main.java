import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

/**
 * 启动类
 * @author leifengsang
 */
public class Main {

	public static void main(String[] args) throws AWTException {
		MagicTrayIcon trayIcon = new MagicTrayIcon();
		boolean succ = Model.getInstance().load();
		if (!succ) {
			trayIcon.displayMassage("配置文件加载失败");
			return;
		}

		//启动服务
		ApiService apiService = new ApiService(Model.getInstance().getApiPath(), trayIcon);
		ProcessMonitor processMonitor = new ProcessMonitor(apiService, trayIcon);
		//每秒检测一次进程
		SingleThreadPool.getInstance().scheduledThreadPool().scheduleAtFixedRate(processMonitor, 0, 1,
				TimeUnit.SECONDS);
	}
}
