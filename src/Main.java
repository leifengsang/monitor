import java.util.concurrent.TimeUnit;

/**
 * 启动类
 * @author leifengsang
 */
public class Main {

	public static void main(String[] args) {
		boolean succ = Model.getInstance().load();
		if (!succ) {
			//TODO log
			return;
		}

		//启动服务
		ApiService apiService = new ApiService(Model.getInstance().getApiPath());
		ProcessMonitor processMonitor = new ProcessMonitor(apiService);
		//每秒检测一次进程
		SingleThreadPool.getInstance().scheduledThreadPool().scheduleAtFixedRate(processMonitor, 0, 1,
				TimeUnit.SECONDS);
	}
}
