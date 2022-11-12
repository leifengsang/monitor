import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.ArrayUtils;

/**
 * 监听进程
 * @author leifengsang
 */
public class ProcessMonitor implements Runnable {

	ApiService apiService;

	public ProcessMonitor(ApiService apiService) {
		super();

		this.apiService = apiService;
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
				String process = line.split(" ")[0]; //"只考虑xxx.exe"的进程
				if (ArrayUtils.contains(processList, process)) {
					gameProcessExists = true;
				}
				if (process.equals("obs.exe")) {
					obsProcessExists = true;
				}
			}
			
			//obs不在运行中，直接结束
			if(!obsProcessExists){
				//TODO send close massage
				apiService.close();
			}
			apiService.checkGameProcess(gameProcessExists);
		} catch (IOException e) {
			//TODO log
			e.printStackTrace();
		}
	}
}
