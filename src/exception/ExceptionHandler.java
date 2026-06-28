package exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class.getName());

    // Chỉ thực hiện ghi log hệ thống
    public static void log(Exception e) {
        LOGGER.log(Level.SEVERE, "[LOG HỆ THỐNG]: " + e.getMessage(), e);
    }

    // Lấy thông báo lỗi thân thiện với người dùng
    public static String getFriendlyMessage(Exception e) {
        if (e instanceof ValidationException) {
            return e.getMessage(); // Ví dụ: "Từ khóa không được để trống!"
        }
        if (e instanceof DatabaseException) {
            return "Lỗi truy xuất cơ sở dữ liệu. Vui lòng kiểm tra lại file dữ liệu.";
        }
        return "Đã xảy ra lỗi hệ thống không xác định. Vui lòng liên hệ quản trị viên.";
    }
}
