import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;

/**
 * 系统托盘
 * @author leifengsang
 */
public class MagicTrayIcon {

	private TrayIcon trayIcon;

	public MagicTrayIcon() throws AWTException {
		SystemTray systemTray = SystemTray.getSystemTray();
		Image image = Toolkit.getDefaultToolkit().createImage("res/icon.png");
		trayIcon = new TrayIcon(image, "processMonitor");
		trayIcon.setImageAutoSize(true);
		PopupMenu popupMenu = new PopupMenu();
		trayIcon.setPopupMenu(popupMenu);
		systemTray.add(trayIcon);
	}

	public void addMenuItem(String label, ActionListener actionListener) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.addActionListener(actionListener);
		trayIcon.getPopupMenu().add(menuItem);
	}

	/**
	 * 显示一条托盘信息
	 * @param content
	 */
	public void displayMassage(String content) {
		trayIcon.displayMessage("processMonitor", content, MessageType.INFO);
	}
}
