package controllers;

import exception.ExceptionHandler;
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
            String wordName = dictionaryView.getTxtInput().getText();
            Word word = new Word(wordName, null);
            insert(word);
        });

        dictionaryView.addSearchListener(e -> {
            String keyWord = dictionaryView.getTxtInput().getText();
            search(keyWord);
        });

        dictionaryView.setVisible(true);
    }


    public void search(String keyWord) {
        try {
            Word result = dictionary.searchWord(keyWord);
            System.out.println("=== Từ cần tìm ===");
            System.out.println(result);
        } catch (Exception e) {
            ExceptionHandler.handle(e);
        }
    }

    public void insert(Word word) {
        try {
            dictionary.insertWord(word);
        } catch (Exception e) {
            ExceptionHandler.handle(e);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DictionaryController());
    }
}
