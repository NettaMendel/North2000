package boundary;

import java.awt.EventQueue;

public class North2000 {

    /**
     * Unified main entry point for the North2000 system.
     * Launches the main menu.
     * 
     * @param args application arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainMenu frame = new MainMenu();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
