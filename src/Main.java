
/**
 * ∆Ù∂Ø¿‡
 * @author leifengsang
 */
public class Main {

	public static void main(String[] args) {
		boolean succ = ConfigUtil.getInstance().load();
		if (!succ) {
			//TODO log
			return;
		}
		
		
	}
}
