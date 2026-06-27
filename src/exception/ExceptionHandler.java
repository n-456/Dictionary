package exception;

public class ExceptionHandler {
    public static void handle(Exception e) {
        if (e instanceof NullPointerException) {
            System.out.println("Lỗi dữ liệu: Đối tượng bị rỗng");
        }
        else {
            System.out.println("Có lỗi xảy ra");
        }
    }
}
