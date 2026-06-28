package controllers;

import exception.ExceptionHandler;
import exception.ValidationException;
import models.Dictionary;
import models.Word;
import views.DictionaryView;

import javax.swing.*;

public class DictionaryController {
    private Dictionary dictionary;
    private DictionaryView dictionaryView;


    public DictionaryController() {
        dictionary = new Dictionary();
        dictionaryView = new DictionaryView();


        dictionaryView.addAddListener(e -> {
            try {
                String wordName = dictionaryView.getTxtInput().getText();

                // 1. Kiểm tra đầu vào chủ động (Validation)
                if (wordName == null || wordName.trim().isEmpty()) {
                    throw new ValidationException("Từ khóa cần thêm không được để trống!");
                }

                // 2. Thực hiện nghiệp vụ an toàn
                Word word = new Word(wordName.trim(), null);
                dictionary.addWord(word);

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

        dictionaryView.addSearchListener(e -> {
            try {
                String keyWord = dictionaryView.getTxtInput().getText();
                Word result = dictionary.searchWord(keyWord);
                System.out.println("=== Từ cần tìm ===");
                System.out.println(result);
            } catch (Exception ex) {
                // Log lỗi hệ thống
                ExceptionHandler.log(ex);

                // Hiển thị lỗi thân thiện dựa trên ngữ cảnh View
                String friendlyMessage = ExceptionHandler.getFriendlyMessage(ex);
                JOptionPane.showMessageDialog(dictionaryView, friendlyMessage, "Lỗi xảy ra", JOptionPane.ERROR_MESSAGE);
            }
        });

        dictionaryView.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DictionaryController());
    }
}
