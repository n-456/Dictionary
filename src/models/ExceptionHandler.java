package models;

public class ExceptionHandler {
    public static void handle(Exception e) {
        if (e instanceof NullPointerException) {
            System.err.println("Lỗi Dữ Liệu: Đối tượng bị rỗng (Null Pointer).");
        }
        else {
            System.err.println("[Lỗi Không Xác Định]: " + e.getMessage());
        }
    }
}
