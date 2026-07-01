package controllers;

import models.Dictionary;
import models.Word;
import views.SearchScreen;

public class SearchController {
    private DictionaryController dictionaryController;
    private SearchScreen searchScreen;
    private Dictionary dictionary;
    private String currentKeyOfWord;        // Biến lưu keyOfWord đang được hiển thị thông tin


    public SearchController(DictionaryController dictionaryController, SearchScreen searchScreen, Dictionary dictionary, String targetKeyOfWord) {
        this.dictionaryController = dictionaryController;
        this.searchScreen = searchScreen;
        this.dictionary = dictionary;
        this.currentKeyOfWord = targetKeyOfWord;


        // Trường hợp người dùng chọn word từ homeScreen, thì thực hiện tìm và hiển thị word được chọn
        if (currentKeyOfWord != null) {
            searchScreen.setSearchKeyOfWord(currentKeyOfWord);
            executeSearch(currentKeyOfWord);
        } else {
            // Nếu không, thì đặt giao diện trống mặc định
            searchScreen.setSearchKeyOfWord("");
            searchScreen.showEmptyPanel();
        }


        // Xử lý sự kiện
        this.searchScreen.addSearchListener(e -> {
            String query = searchScreen.getSearchKeyOfWord().trim().toLowerCase();
            if (query.isEmpty()) {
                searchScreen.showMessage("Vui lòng nhập vào ô tìm kiếm!");
                searchScreen.showEmptyPanel();
                return;
            }
            executeSearch(query);
        });

        this.searchScreen.addEditListener(e -> {
            if (currentKeyOfWord != null) {
                dictionaryController.switchScreen("ADDOREDIT_SCREEN", currentKeyOfWord, true);
            }
        });

        this.searchScreen.addDeleteListener(e -> {
            if (currentKeyOfWord == null) return;

            if (!this.dictionary.deleteWord(currentKeyOfWord)) {
                searchScreen.showMessage("'" + currentKeyOfWord + "' không có trong từ điển!");
                return;
            }
            this.currentKeyOfWord = null;
            searchScreen.setSearchKeyOfWord("");
            searchScreen.showEmptyPanel();
        });

        this.searchScreen.addHomeScreenListener(e -> {
            this.dictionaryController.switchScreen("HOME_SCREEN", null, false);
        });
    }


    /**
     * Thực hiện tìm word
     */
    private void executeSearch(String keyOfWord) {
        Word wordObj = this.dictionary.searchWord(keyOfWord);
        if (wordObj != null) {
            this.currentKeyOfWord = wordObj.getKeyOfWord();
            searchScreen.displayBlocks(this.currentKeyOfWord, wordObj.getTypeOfWordList());
        } else {
            this.currentKeyOfWord = null;
            searchScreen.showMessage("Không tìm thấy " + keyOfWord + "!");
            searchScreen.showEmptyPanel();
        }
    }
}