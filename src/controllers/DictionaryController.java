package controllers;

import exception.ExceptionHandler;
import models.Dictionary;
import models.Word;
import repository.WordRepository;
import repository.impl.WordDAO;
import views.AddOrEditScreen;
import views.DictionaryView;
import views.HomeScreen;
import views.SearchScreen;

import javax.swing.*;
import java.util.List;


public class DictionaryController {
    private DictionaryView dictionaryView;
    private Dictionary dictionary;
    private String filePath;


    public DictionaryController(String filePath) {
        dictionaryView = new DictionaryView();
        dictionary = new Dictionary();
        this.filePath = filePath;

        try {
            WordRepository wordRepository = new WordDAO(this.filePath);
            List<Word> wordList = wordRepository.loadAllWords();
            dictionary.addAllWord(wordList);
        } catch (Exception e) {
            ExceptionHandler.log(e);
            String errorMsg = ExceptionHandler.getFriendlyMessage(e);
            JOptionPane.showMessageDialog(null, errorMsg, "Lỗi Khởi Động", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Khởi chạy ứng dụng
     */
    public void start() {
        // Đặt homeScreen làm giao diện chính mặc định
        switchScreen("HOME_SCREEN", null, false);

        dictionaryView.setVisible(true);
    }


    /**
     * Chuyển đổi giữa các giao diện chính (homeScreen, addOrEditScreen, searchScreen)
     */
    public void switchScreen(String screenName, String targetKeyOfWord, boolean isEditMode) {
        if (screenName.equals("SEARCH_SCREEN")) {
            SearchScreen searchScreen = new SearchScreen();
            new SearchController(this, searchScreen, dictionary, targetKeyOfWord);
            dictionaryView.changeContentPanel(searchScreen);

        } else if (screenName.equals("ADDOREDIT_SCREEN")) {
            AddOrEditScreen addOrEditScreen = new AddOrEditScreen();
            new AddOrEditController(this, addOrEditScreen, dictionary, targetKeyOfWord, isEditMode);
            dictionaryView.changeContentPanel(addOrEditScreen);

        } else {
            HomeScreen homeScreen = new HomeScreen();
            new HomeController(this, homeScreen, dictionary);
            dictionaryView.changeContentPanel(homeScreen);

        }
    }
}