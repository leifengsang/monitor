import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * �̳߳�
 * @author leifengsang
 */
public class SingleThreadPool {

	private static class LazyHolder {
		private static final SingleThreadPool INSTANCE = new SingleThreadPool();
	}

	private SingleThreadPool() {
		ses = Executors.newScheduledThreadPool(1);//���������߳�
	}

	public static final SingleThreadPool getInstance() {
		return LazyHolder.INSTANCE;
	}

	private ScheduledExecutorService ses;

	public ScheduledExecutorService scheduledThreadPool() {
		return ses;
	}

	// �ر������߳�
	public void close() {
		ses.shutdownNow();
	}

}
