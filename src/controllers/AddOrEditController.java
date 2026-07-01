package controllers;

import exception.ExceptionHandler;
import exception.ValidationException;
import models.Dictionary;
import models.Word;
import models.TypeOfWord;
import views.AddOrEditScreen;


public class AddOrEditController {
    private DictionaryController dictionaryController;
    private AddOrEditScreen addOrEditScreen;
    private Dictionary dictionary;

    private String currentKeyOfWord;    // Biến lưu keyOfWord đang được thêm hoặc sửa
    private boolean isEditMode;         // Biến quy định chế độ: true = chế độ chỉnh sửa, false = chế độ thêm


    public AddOrEditController(DictionaryController dictionaryController, AddOrEditScreen addOrEditScreen, Dictionary dictionary, String targetKeyOfWord, boolean isEditMode) {
        this.dictionaryController = dictionaryController;
        this.addOrEditScreen = addOrEditScreen;
        this.dictionary = dictionary;
        this.currentKeyOfWord = targetKeyOfWord;
        this.isEditMode = isEditMode;


        // Xoá toàn bộ dữ liệu cũ trên giao diện
        this.addOrEditScreen.clearAll();


        // Chọn chế độ sửa hoặc chế độ thêm
        try {
            if (this.isEditMode && currentKeyOfWord != null) {
                // Chế độ sửa
                Word editWord = this.dictionary.searchWord(currentKeyOfWord);
                if (editWord == null) {
                    throw new ValidationException("Không tìm thấy thông tin của '" + currentKeyOfWord + "' để chỉnh sửa!");
                }

                this.addOrEditScreen.setKeyOfWord(editWord.getKeyOfWord());
                this.addOrEditScreen.setKeyOfWordEditable(false);

                if (editWord.getTypeOfWordList() != null) {
                    for (TypeOfWord typeOfWord : editWord.getTypeOfWordList()) {
                        this.addOrEditScreen.addBlock(typeOfWord.getMeaning(), typeOfWord.getPartOfSpeech(), typeOfWord.getExample());
                    }
                } else {
                    this.addOrEditScreen.addBlock("", "", "");
                }
            } else {
                // Chế độ thêm: Mở khóa ô nhập keyOfWord và tạo sẵn 1 khối nhập typeOfWord mặc định
                this.addOrEditScreen.setKeyOfWordEditable(true);
                this.addOrEditScreen.addBlock("", "", "");
            }
        } catch (Exception ex) {
            addOrEditScreen.showWarning(ExceptionHandler.getFriendlyMessage(ex));
            this.dictionaryController.switchScreen("HOME_SCREEN", null, false);
        }


        // Xử lý sự kiện
        this.addOrEditScreen.addNewTypeListener(e -> {
            this.addOrEditScreen.addBlock("", "", "");
        });

        this.addOrEditScreen.addCancelListener(e -> {
            this.dictionaryController.switchScreen("HOME_SCREEN", null, false);
        });

        this.addOrEditScreen.addSaveListener(e -> {

        });
    }
}