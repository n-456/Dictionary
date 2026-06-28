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

        refreshTable();


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

        });

        this.homeScreen.addEditListener(e -> {

        });

        this.homeScreen.addDeleteListener(e -> {

        });
    }


    /**
     * Cập nhật bảng của giao diện homeScreen
     */
    private void refreshTable() {
        try {
            List<Word> data = this.dictionary.getInOrderWords();
            this.homeScreen.updateTable(data);

            if (data == null || data.isEmpty()) {
                throw new ValidationException("Không có từ trong từ điển.");
            }

            this.homeScreen.setEnable(true);

        } catch (Exception ex) {
            this.homeScreen.setEnable(false);
            homeScreen.showMessage(ExceptionHandler.getFriendlyMessage(ex));
        }
    }
}