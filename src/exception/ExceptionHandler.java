package exception;

import javax.swing.JOptionPane;

public class ExceptionHandler {

    public static void handle(Exception e) {
        String title = "Lỗi Hệ Thống";
        String message = e.getMessage();
        int messageType = JOptionPane.ERROR_MESSAGE;

        if (e instanceof NullPointerException) {
            title = "Lỗi Dữ Liệu Rỗng";
            messageType = JOptionPane.WARNING_MESSAGE;
        }
        else if (e instanceof IllegalArgumentException) {
            title = "Dữ Liệu Không Hợp Lệ";
            messageType = JOptionPane.ERROR_MESSAGE;
        }

        // In log lỗi
        System.err.println("[LOG LỖI]: " + e.toString());
        e.printStackTrace();

        // Hiển thị hộp thoại popup lỗi cho người dùng xem
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}