import controllers.DictionaryController;
import exception.ExceptionHandler;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class Main1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    DictionaryController appController = new DictionaryController("resource/dictionary.json");
                    appController.start();
                } catch (Exception e) {
                    ExceptionHandler.log(e);

                    String errorMsg = ExceptionHandler.getFriendlyMessage(e);
                    JOptionPane.showMessageDialog(
                            null,
                            errorMsg + "\nỨng dụng sẽ tự động đóng.",
                            "Lỗi Khởi Động",
                            JOptionPane.ERROR_MESSAGE
                    );

                    System.exit(1);
                }
            }
        });
    }
}