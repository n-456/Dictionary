package repository.impl;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;

import models.Dictionary;
import models.Word;
import repository.WordRepository;


public class WordGAO implements WordRepository {
    private String filePath;

    public WordGAO(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void loadFromFile(Dictionary dictionary) {
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            // read "["
            reader.beginArray();

            while (reader.hasNext()) {
                // JSON to word object
                Word word = gson.fromJson(reader, Word.class);

                // Add word to dictionary
                dictionary.insertWord(word);
            }

            // read "]"
            reader.endArray();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}