
/**
 * ������
 * @author leifengsang
 */
public class Main {

	public static void main(String[] args) {
		boolean succ = Model.getInstance().load();
		if (!succ) {
			//TODO log
			return;
		}
		
		
	}
}
