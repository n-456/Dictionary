import exception.DatabaseException;
import exception.ExceptionHandler;
import models.Dictionary;
import models.TypeOfWord;
import models.Word;
import repository.WordRepository;
import repository.impl.WordDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        // Áp dụng DIP: Khai báo qua Interface WordRepository
        WordRepository wordRepository = new WordDAO("resource/dictionary.json");

        System.out.println("--- ĐANG TẢI DỮ LIỆU TỪ FILE ---");
        try {
            List<Word> words = wordRepository.loadAllWords();
            for (Word word : words) {
                dictionary.addWord(word);
            }
            System.out.println("Tải thành công!\n");
        } catch (DatabaseException e) {
            ExceptionHandler.log(e);
            System.err.println("Lỗi khởi chạy: " + e.getMessage());
            System.err.println("Ứng dụng sẽ hoạt động không có dữ liệu ban đầu.");
        }

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("========== MENU TỪ ĐIỂN ==========");
            System.out.println("1. In danh sách từ (In-Order)");
            System.out.println("2. In ngược danh sách từ (Reverse In-Order)");
            System.out.println("3. Thêm từ mới");
            System.out.println("4. Tìm kiếm từ chính xác");
            System.out.println("5. Tìm kiếm từ theo chuỗi con");
            System.out.println("6. Sửa thông tin từ");
            System.out.println("7. Xoá từ");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn (0-7): ");

            while (!scanner.hasNextInt()) {
                System.out.print("Cần nhập một số hợp lệ: ");
                scanner.next();
            }
            choice = scanner.nextInt();
            scanner.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    System.out.println("===== IN-ORDER =====");
                    List<Word> sortedWords = dictionary.getInOrderWords();
                    for (Word w : sortedWords) {
                        System.out.println(w);
                    }
                    break;

                case 2:
                    System.out.println("===== REVERSE IN-ORDER =====");
                    List<Word> sortedWords2 = dictionary.getInOrderWords();
                    for (Word w : sortedWords2) {
                        System.out.println(w);
                    }
                    break;

                case 3:
                    System.out.println("===== THÊM TỪ MỚI =====");
                    System.out.print("Nhập từ muốn thêm: ");
                    String targetWord = scanner.nextLine().trim();

                    if (targetWord.isEmpty()) {
                        System.out.println("Từ không được trống");
                        break;
                    }

                    // Khởi tạo một danh sách để chứa nhiều TypeOfWord
                    List<TypeOfWord> listTypes = new ArrayList<>();
                    String tiepTuc;
                    int count = 1;

                    // Vòng lặp cho phép nhập nhiều loại từ / nghĩa khác nhau
                    do {
                        System.out.println("\n--- Nhập thông tin nghĩa thứ " + count + " ---");
                        System.out.print("Nhập loại từ (Ví dụ: Noun, Verb, Adj...): ");
                        String type = scanner.nextLine().trim();
                        System.out.print("Nhập nghĩa tiếng việt: ");
                        String meaning = scanner.nextLine().trim();
                        System.out.print("Nhập ví dụ: ");
                        String explanation = scanner.nextLine().trim();

                        // Thêm nghĩa vừa nhập vào danh sách định sẵn
                        TypeOfWord typeOfWord = new TypeOfWord(meaning, type, explanation);
                        listTypes.add(typeOfWord);

                        // Hỏi người dùng có muốn nhập tiếp nghĩa, từ loại khác cho từ này không
                        System.out.print("Nhập thêm nghĩa khác cho từ '" + targetWord + "' ? (Y/N): ");
                        tiepTuc = scanner.nextLine().trim();
                        count++;
                    } while (tiepTuc.equalsIgnoreCase("y")); // Lặp lại nếu gõ 'y' hoặc 'Y'

                    // Sau khi nhập xong hết các nghĩa, tiến hành tạo Word và chèn vào từ điển
                    Word wordInsert = new Word(targetWord, listTypes);
                    if (dictionary.addWord(wordInsert)) {
                        System.out.println("\n=> Đã thêm từ \"" + targetWord + " vào từ điển");
                    } else {
                        System.out.println("\n=> Từ \"" + targetWord + " đã có trong từ điển");
                    }
                    break;

                case 4:
                    System.out.println("===== TÌM KIẾM TỪ CHÍNH XÁC =====");
                    System.out.print("Nhập từ cần tìm: ");
                    String searchKey = scanner.nextLine().trim();

                    Word resultSearch = dictionary.searchWord(searchKey);
                    if (resultSearch == null) {
                        System.out.println("=> Không tìm thấy từ \"" + searchKey + "\"");
                    } else {
                        System.out.println("=> Kết quả tìm thấy: " + resultSearch);
                    }
                    break;

                case 5:
                    System.out.println("===== TÌM KIẾM THEO CHUỖI CON =====");
                    System.out.print("Nhập chuỗi ký tự cần tìm: ");
                    String substring = scanner.nextLine().trim();

                    List<Word> words = dictionary.searchSubstringWord(substring);
                    if (words == null || words.isEmpty()) {
                        System.out.println("=> Không tìm thấy từ chứa chuỗi \"" + substring + "\"");
                    } else {
                        System.out.println("=> Các từ chứa chuỗi \"" + substring + "\":");
                        for (Word word : words) {
                            System.out.println("   - " + word);
                        }
                    }
                    break;

                case 6:
                    System.out.println("===== SỬA THÔNG TIN TỪ =====");
                    System.out.print("Nhập từ khóa cần sửa: ");
                    String editKey = scanner.nextLine().trim();

                    if (editKey.isEmpty()) {
                        System.out.println("Từ khóa không được trống.");
                        break;
                    }

                    Word existWord = dictionary.searchWord(editKey);
                    if (existWord == null) {
                        System.out.println("=> Không tìm thấy từ \"" + editKey + "\" trong từ điển để sửa.");
                        break;
                    }

                    // Khởi tạo một danh sách để chứa nhiều TypeOfWord
                    List<TypeOfWord> newTypesList = new ArrayList<>();
                    String next;
                    int editCount = 1;

                    // Vòng lặp cho phép nhập nhiều loại từ / nghĩa khác nhau
                    do {
                        System.out.println("\n--- Nhập thông tin nghĩa mới thứ " + editCount + " ---");
                        System.out.print("Nhập loại từ (Ví dụ: Noun, Verb, Adj...): ");
                        String type = scanner.nextLine().trim();
                        System.out.print("Nhập nghĩa tiếng việt: ");
                        String meaning = scanner.nextLine().trim();
                        System.out.print("Nhập ví dụ: ");
                        String explanation = scanner.nextLine().trim();

                        // Thêm nghĩa vừa nhập vào danh sách định sẵn
                        TypeOfWord newTypeOfWord = new TypeOfWord(meaning, type, explanation);
                        newTypesList.add(newTypeOfWord);

                        // Hỏi người dùng có muốn nhập tiếp nghĩa, từ loại khác cho từ này không
                        System.out.print("Nhập thêm nghĩa khác cho từ '" + editKey + "' ? (Y/N): ");
                        next = scanner.nextLine().trim();
                        editCount++;
                    } while (next.equalsIgnoreCase("y"));

                    // Sau khi nhập xong hết các nghĩa, tiến hành sửa từ
                    if (dictionary.editWord(editKey, newTypesList)) {
                        System.out.println("\n=> Đã sửa thành công từ \"" + editKey + "\".");
                    } else {
                        System.out.println("\n=> Sửa thất bại.");
                    }
                    break;

                case 7:
                    System.out.println("===== XOÁ TỪ =====");
                    System.out.print("Nhập từ khóa cần xoá: ");
                    String deleteKey = scanner.nextLine().trim();

                    if (deleteKey.isEmpty()) {
                        System.out.println("Từ khóa không được trống.");
                        break;
                    }

                    if (dictionary.deleteWord(deleteKey)) {
                        System.out.println("=> Đã xoá từ \"" + deleteKey + "\"");
                    } else {
                        System.out.println("=> Từ không có trong từ điển");
                    }

                case 0:
                    System.out.println("Đã thoát");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ! Lựa chọn từ 0 đến 6.");
            }
            System.out.println("\n-----------------------------------\n");
        } while (choice != 0);

        scanner.close();
    }
}