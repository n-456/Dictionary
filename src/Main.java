import models.Dictionary;
import models.TypeOfWord;
import models.Word;
import repository.impl.WordGAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();
        WordGAO wordGAO = new WordGAO("resource/dictionary.json");
        Scanner scanner = new Scanner(System.in);

        // Tự động load dữ liệu từ file khi khởi động
        System.out.println("--- ĐANG TẢI DỮ LIỆU TỪ FILE ---");
        wordGAO.loadFromFile(dictionary);
        System.out.println("Tải thành công!\n");

        int choice;
        do {
            System.out.println("========== MENU TỪ ĐIỂN ==========");
            System.out.println("1. In danh sách từ (In-Order)");
            System.out.println("2. In ngược danh sách từ (Reverse In-Order)");
            System.out.println("3. Thêm từ mới");
            System.out.println("4. Tìm kiếm từ chính xác");
            System.out.println("5. Tìm kiếm từ theo chuỗi con");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn (0-5): ");

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
                    dictionary.printInOrder();
                    break;

                case 2:
                    System.out.println("===== REVERSE IN-ORDER =====");
                    dictionary.printReverseInOrder();
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
                    dictionary.addWord(wordInsert);
                    System.out.println("\n=> Đã thêm từ \"" + targetWord + " vào từ điển");
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

                case 0:
                    System.out.println("Đã thoát");
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ! Lựa chọn từ 0 đến 5.");
            }
            System.out.println("\n-----------------------------------\n");
        } while (choice != 0);

        scanner.close();
    }
}