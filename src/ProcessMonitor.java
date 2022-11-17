import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.ArrayUtils;

/**
 * 监听进程
 * @author leifengsang
 */
public class ProcessMonitor implements Runnable {

	private ApiService apiService;

	private MagicTrayIcon trayIcon;

	public ProcessMonitor(ApiService apiService, MagicTrayIcon trayIcon) {
		super();

		this.apiService = apiService;
		this.trayIcon = trayIcon;
	}

	@Override
	public void run() {
		try {
			//获得所有运行中的进程
			Process taskProcess = Runtime.getRuntime().exec("TASKLIST /NH");
			BufferedReader reader = new BufferedReader(new InputStreamReader(taskProcess.getInputStream()));
			String line = null;
			String[] processList = Model.getInstance().getProcessList();
			boolean obsProcessExists = false;
			boolean gameProcessExists = false;
			while ((line = reader.readLine()) != null) {
				int index = line.indexOf(".exe");
				if (index == -1) {
					continue;
				}
				String process = line.substring(0, index + 4); //提取"xxx.exe"
				if (ArrayUtils.contains(processList, process)) {
					gameProcessExists = true;
				}
				if (process.equals(Model.getInstance().getObsProcess())) {
					obsProcessExists = true;
				}
			}

			//obs不在运行中，直接结束
			if (!obsProcessExists) {
				trayIcon.displayMassage("未检测到obs进程，程序结束");
				apiService.close();
			}
			apiService.checkGameProcess(gameProcessExists);
		} catch (IOException e) {
			System.out.println("监听进程失败");
			e.printStackTrace();
		}
	}
}
