# CODE REVIEW: HỆ THỐNG XỬ LÝ NGOẠI LỆ (EXCEPTION HANDLING)

**Người thực hiện**: Senior Developer
**Dự án**: Từ điển (Dictionary)
**Mục tiêu**: Đánh giá kiến trúc xử lý ngoại lệ, phân loại mức độ nghiêm trọng và đề xuất phương án cải tiến chuẩn dự án.

---

## I. BẢNG TỔNG HỢP CÁC PHẦN CẦN FIX THEO MỨC ĐỘ NGHIÊM TRỌNG (SEVERITY)

| ID | Vấn đề | File ảnh hưởng | Mức độ (Severity) | Trạng thái |
| :--- | :--- | :--- | :--- | :--- |
| **01** | Nuốt ngoại lệ (Exception Swallowing) tại Data Layer | [WordGAO.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/repository/impl/WordGAO.java#L40-L42) | 🔴 **Critical** | Cần sửa ngay |
| **02** | Lọt ngoại lệ NullPointerException ra khỏi khối try-catch của Controller | [DictionaryController.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/controllers/DictionaryController.java#L20-L24) | 🔴 **Critical** | Cần sửa ngay |
| **03** | Ghép cứng Swing UI Popup (`JOptionPane`) vào Core Exception Handler | [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java#L26) | 🟠 **High** | Cần cấu trúc lại |
| **04** | Sử dụng `NullPointerException` sai mục đích (làm Validation) | [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java#L12-L15) | 🟠 **High** | Cần sửa logic |
| **05** | Thiếu hệ thống Custom Exception định nghĩa nghiệp vụ | Toàn bộ Project | 🟡 **Medium** | Khuyến nghị thêm |
| **06** | In log thô bằng `System.err` và `printStackTrace()` | [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java#L22-L23) | 🟢 **Low** | Khuyến nghị cải tiến |

---

## II. CHI TIẾT ĐÁNH GIÁ, RỦI RO & ĐỀ XUẤT CÁCH SỬA (DETAILED FINDINGS & FIXES)

### 1. [Critical] Nuốt ngoại lệ tại Data Layer
* **Vị trí**: Lớp [WordGAO.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/repository/impl/WordGAO.java) dòng 40 - 42.
* **Mô tả**: Khi gặp `IOException` (chẳng hạn không tìm thấy file `resource/dictionary.json` hoặc file bị hỏng JSON), phương thức `loadFromFile` chỉ đơn giản là in lỗi ra console rồi kết thúc bình thường.
* **Rủi ro**: Lớp gọi khởi chạy [Main.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/Main.java#L18-L19) vẫn in thông báo `"Tải thành công!"` mặc dù bộ nhớ từ điển lúc đó rỗng. Điều này lừa dối người dùng cuối và gây khó khăn lớn cho việc gỡ lỗi (debugging).
* **Đề xuất cách sửa**: Xóa khối `try-catch` tự nuốt lỗi trong `WordGAO.java`. Khai báo `throws IOException` ở phương thức hoặc gói nó vào `DatabaseException` rồi ném ra ngoài để caller (Controller/Main) chủ động xử lý và hiển thị thông báo lỗi.
  *(Xem chi tiết code sửa đổi tại **Mục III - Bước 3**)*

### 2. [Critical] Lọt ngoại lệ NullPointerException làm treo GUI Thread
* **Vị trí**: Lớp [DictionaryController.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/controllers/DictionaryController.java) dòng 20 - 24.
* **Mô tả**:
  ```java
  dictionaryView.addAddListener(e -> {
      String wordName = dictionaryView.getTxtInput().getText();
      Word word = new Word(wordName, null); // LỖI NÉM RA TẠI ĐÂY!
      insert(word); // Nơi bọc try-catch
  });
  ```
  Nếu ô nhập liệu trống hoặc `wordName` bằng `null`, Constructor của [Word.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/models/Word.java#L10) sẽ thực hiện `name.toLowerCase()`, lập tức ném ra `NullPointerException`.
* **Rủi ro**: Vì NPE xảy ra ngoài khối `try` của hàm `insert`, lỗi sẽ phát tán thẳng lên Swing Event Dispatch Thread (EDT). Trình xử lý lỗi trung tâm [ExceptionHandler](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java) không nhận được ngoại lệ này. GUI có thể bị đơ hoặc không phản hồi mà người dùng không nhận được bất kỳ cảnh báo popup nào.
* **Đề xuất cách sửa**: Chuyển luồng khởi tạo `new Word` vào bên trong khối `try-catch` của sự kiện. Tiến hành kiểm tra dữ liệu đầu vào (Validation) trước khi khởi tạo đối tượng để ngăn chặn NPE phát sinh ngầm.
  *(Xem chi tiết code sửa đổi tại **Mục III - Bước 4**)*

### 3. [High] Ghép cứng Swing UI Component vào ExceptionHandler chung
* **Vị trí**: Lớp [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java) dòng 26.
* **Mô tả**: `ExceptionHandler.handle(Exception e)` gọi trực tiếp `JOptionPane.showMessageDialog`.
* **Rủi ro**: 
  1. Khi chạy ứng dụng ở chế độ CLI ([Main.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/Main.java)), nếu có Exception được định tuyến đến đây, ứng dụng CLI sẽ cố dựng một cửa sổ đồ họa. Trên môi trường headless (như server hoặc terminal thuần), Java sẽ ném ra `HeadlessException` và crash toàn bộ ứng dụng.
  2. Vi phạm nguyên tắc phân tách trách nhiệm (Separation of Concerns). Khối xử lý ngoại lệ trung tâm không nên biết về cách thức biểu diễn UI (GUI hay Console).
* **Đề xuất cách sửa**: Tách biệt hoàn toàn phần UI đồ họa ra khỏi `ExceptionHandler.java`. Class này chỉ làm nhiệm vụ ghi log kỹ thuật và chuyển dịch thông điệp lỗi thành chữ thân thiện (`getFriendlyMessage`). Việc hiện dialog sẽ do Controller và View phụ trách.
  *(Xem chi tiết cấu trúc tại **Mục III - Bước 2**)*

### 4. [High] Dùng NullPointerException để Validate logic nghiệp vụ
* **Vị trí**: Lớp [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java) dòng 12 - 15.
* **Mô tả**: Check `e instanceof NullPointerException` để gán nhãn *"Lỗi Dữ Liệu Rỗng"*.
* **Rủi ro**: NPE thường là chỉ báo của một lỗi lập trình nghiêm trọng (chưa khởi tạo biến, gọi phương thức trên đối tượng null). Việc coi NPE là một "cảnh báo dữ liệu rỗng" thông thường sẽ che giấu đi các lỗi logic thực tế trong code của lập trình viên. Dữ liệu rỗng từ phía Client phải được bắt bằng kiểm tra dữ liệu chủ động (Validation) trước khi xử lý sâu hơn.
* **Đề xuất cách sửa**: Xây dựng bước Validation dữ liệu đầu vào ở tầng Controller và chủ động ném `ValidationException` khi dữ liệu trống. Không dùng NPE để làm validation.
  *(Xem chi tiết tại **Mục III - Bước 1 & Bước 4**)*

### 5. [Medium] Thiếu hệ thống Custom Exception định nghĩa nghiệp vụ
* **Vị trí**: Toàn bộ dự án.
* **Mô tả**: Ứng dụng hiện đang dùng các Exception mặc định của Java (`NullPointerException`, `IllegalArgumentException`) để biểu diễn các lỗi logic nghiệp vụ từ điển.
* **Rủi ro**: Không phân biệt được lỗi hệ thống/bug của nhà phát triển (ví dụ: lỗi trỏ null) với lỗi do người dùng nhập sai quy tắc.
* **Đề xuất cách sửa**: Tạo một Exception cha là `DictionaryException` và các Exception con tương ứng như `ValidationException` (lỗi nhập liệu), `DatabaseException` (lỗi đọc file).
  *(Xem chi tiết tại **Mục III - Bước 1**)*

### 6. [Low] In log thô bằng System.err và printStackTrace()
* **Vị trí**: Lớp [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java) dòng 22 - 23.
* **Mô tả**: Ghi nhận vết lỗi trực tiếp ra luồng stderr của hệ thống.
* **Rủi ro**: Khó cấu hình bật/tắt log theo môi trường chạy, khó ghi log tự động ra file và không lọc được cấp độ log (INFO, WARN, ERROR).
* **Đề xuất cách sửa**: Chuyển sang sử dụng Java Logging API (`java.util.logging.Logger`) hoặc các framework Logger chuẩn công nghiệp (như SLF4J, Log4j).
  *(Xem chi tiết tại **Mục III - Bước 2**)*

---

## III. PHƯƠNG ÁN TÁI CẤU TRÚC THEO CHUẨN SENIOR (REFACTORING PROPOSAL)

### Bước 1: Tạo các Custom Exception rõ ràng
Tạo lớp cha chung cho các Exception nghiệp vụ của dự án:
```java
// File: src/exception/DictionaryException.java
package exception;

public class DictionaryException extends RuntimeException {
    public DictionaryException(String message) {
        super(message);
    }
    public DictionaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

Và các Exception con:
```java
// File: src/exception/ValidationException.java
package exception;

public class ValidationException extends DictionaryException {
    public ValidationException(String message) {
        super(message);
    }
}

// File: src/exception/DatabaseException.java
package exception;

public class DatabaseException extends DictionaryException {
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

### Bước 2: Tách biệt hiển thị lỗi (UI-Agnostic Exception Handling)
Cải tiến [ExceptionHandler.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/exception/ExceptionHandler.java) tách biệt phần ghi log và phần thông báo UI:

```java
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
```

### Bước 3: Sửa lại Data Layer ném lỗi thay vì nuốt lỗi
Sửa lại phương thức [loadFromFile](file:///d:/Students%20Reports/Ngoc/Dictionary/src/repository/impl/WordGAO.java#L22):

```java
// Trong WordGAO.java
@Override
public void loadFromFile(Dictionary dictionary) {
    Gson gson = new Gson();
    try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
        reader.beginArray();
        while (reader.hasNext()) {
            Word word = gson.fromJson(reader, Word.class);
            dictionary.insertWord(word);
        }
        reader.endArray();
    } catch (IOException e) {
        // Gói ngoại lệ kỹ thuật thành ngoại lệ nghiệp vụ của hệ thống
        throw new DatabaseException("Không thể tải cơ sở dữ liệu từ đường dẫn: " + filePath, e);
    }
}
```

### Bước 4: Sửa luồng bắt lỗi tại Controller (Đảm bảo an toàn EDT)
Bọc khối `try-catch` toàn bộ hành động của Listener và thực hiện validation trước khi tạo thực thể [Word](file:///d:/Students%20Reports/Ngoc/Dictionary/src/models/Word.java).

```java
// Trong DictionaryController.java
dictionaryView.addAddListener(e -> {
    try {
        String wordName = dictionaryView.getTxtInput().getText();
        
        // 1. Kiểm tra đầu vào chủ động (Validation)
        if (wordName == null || wordName.trim().isEmpty()) {
            throw new ValidationException("Từ khóa cần thêm không được để trống!");
        }

        // 2. Thực hiện nghiệp vụ an toàn
        Word word = new Word(wordName.trim(), null);
        dictionary.insertWord(word);

        // 3. Thông báo thành công trực tiếp tại View context
        JOptionPane.showMessageDialog(dictionaryView, "Thêm từ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        // Log lỗi hệ thống
        ExceptionHandler.log(ex);
        
        // Hiển thị lỗi thân thiện dựa trên ngữ cảnh View
        String friendlyMessage = ExceptionHandler.getFriendlyMessage(ex);
        JOptionPane.showMessageDialog(dictionaryView, friendlyMessage, "Lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
    }
});
```

Và tại [Main.java](file:///d:/Students%20Reports/Ngoc/Dictionary/src/Main.java) (Chế độ CLI):
```java
// Trong Main.java
try {
    System.out.println("--- ĐANG TẢI DỮ LIỆU TỪ FILE ---");
    wordGAO.loadFromFile(dictionary);
    System.out.println("Tải thành công!\n");
} catch (DatabaseException e) {
    ExceptionHandler.log(e);
    System.err.println("Lỗi khởi chạy: " + e.getMessage());
    System.err.println("Ứng dụng sẽ hoạt động không có dữ liệu ban đầu.");
}
```
