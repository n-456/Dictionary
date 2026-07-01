package controllers;

import exception.ExceptionHandler;
import exception.ValidationException;
import models.Dictionary;
import models.Word;
import views.HomeScreen;

import java.util.List;

public class HomeController {
    private DictionaryController dictionaryController;
    private HomeScreen homeScreen;
    private Dictionary dictionary;


    public HomeController(DictionaryController dictionaryController, HomeScreen homeScreen, Dictionary dictionary) {
        this.dictionaryController = dictionaryController;
        this.homeScreen = homeScreen;
        this.dictionary = dictionary;

        refreshScreen();


        // Xử lý các sự kiện
        this.homeScreen.addDetailListener(e -> {
            String selectedWord = homeScreen.getSelectedKeyOfWord();
            if (selectedWord == null) {
                homeScreen.showMessage("Vui lòng chọn một từ trong bảng!");
                return;
            }
            dictionaryController.switchScreen("SEARCH_SCREEN", selectedWord, false);
        });

        this.homeScreen.addSearchScreenListener(e -> {
            dictionaryController.switchScreen("SEARCH_SCREEN", null, false);
        });

        this.homeScreen.addSortListener(e -> {

        });

        this.homeScreen.addAddListener(e -> {
            dictionaryController.switchScreen("ADDOREDIT_SCREEN", null, false);
        });

        this.homeScreen.addEditListener(e -> {
            try {
                String selectedWord = homeScreen.getSelectedKeyOfWord();
                if (selectedWord == null || selectedWord.trim().isEmpty()) {
                    throw new ValidationException("Vui lòng chọn một từ để sửa!");
                }
                dictionaryController.switchScreen("ADDOREDIT_SCREEN", selectedWord, true);
            } catch (Exception ex) {
                homeScreen.showMessage(ExceptionHandler.getFriendlyMessage(ex));
            }
        });

        this.homeScreen.addDeleteListener(e -> {
            try {
                String selectedWord = homeScreen.getSelectedKeyOfWord();
                if (selectedWord == null || selectedWord.trim().isEmpty())  {
                    throw new ValidationException("Vui lòng chọn một từ để xoá!");
                }
                if (!this.dictionary.deleteWord(selectedWord)) {
                    throw new ValidationException("'" + selectedWord + "' không có trong từ điển!");
                }
                refreshScreen();
                homeScreen.showMessage("Đã xóa từ thành công!");
            } catch (Exception ex) {
                homeScreen.showMessage(ExceptionHandler.getFriendlyMessage(ex));
            }
        });
    }


    /**
     * Cập nhật bảng của giao diện homeScreen
     */
    private void refreshScreen() {
        try {
            List<Word> data = this.dictionary.getInOrderWords();
            this.homeScreen.refreshTable(data);
            this.homeScreen.setEnable(true);
        } catch (Exception ex) {
            this.homeScreen.setEnable(false);
            homeScreen.showMessage(ExceptionHandler.getFriendlyMessage(ex));
        }
    }
}