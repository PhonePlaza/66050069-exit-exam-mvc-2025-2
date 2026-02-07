import view.MainView;
import javax.swing.SwingUtilities;

/**
 * Main - Entry point ของโปรแกรม
 * ระบบติดตามข่าวลือ (Rumor Tracking System)
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    javax.swing.UIManager.setLookAndFeel(
                            javax.swing.UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                }

                MainView mainView = new MainView();
                mainView.setVisible(true);
            }
        });
    }
}
