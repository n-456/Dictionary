package repository.impl;

import exception.DatabaseException;
import models.Word;
import repository.WordRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class WordDAO implements WordRepository {
    private final String filePath;
    private final Gson gson;

    public WordDAO(String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public List<Word> loadAllWords() {
        File file = new File(this.filePath);
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(file)) {
                java.lang.reflect.Type listType = new TypeToken<ArrayList<Word>>() {
                }.getType();
                List<Word> words = gson.fromJson(reader, listType);
                return words != null ? words : new ArrayList<>();
            }
        } catch (IOException e) {
            throw new DatabaseException("Hệ thống từ chối quyền truy cập hoặc lỗi đọc file!", e);
        }
    }
}