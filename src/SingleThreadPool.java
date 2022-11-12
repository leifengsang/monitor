import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 线程池
 * @author leifengsang
 */
public class SingleThreadPool {

	private static class LazyHolder {
		private static final SingleThreadPool INSTANCE = new SingleThreadPool();
	}

	private SingleThreadPool() {
		ses = Executors.newScheduledThreadPool(2);//一个给webSocket 一个给监听进程
	}

	public static final SingleThreadPool getInstance() {
		return LazyHolder.INSTANCE;
	}

	private ScheduledExecutorService ses;

	public ScheduledExecutorService scheduledThreadPool() {
		return ses;
	}

	// 关闭所有线程
	public void close() {
		ses.shutdownNow();
	}

}
