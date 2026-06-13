package repository.impl;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;

import models.Dictionary;
import models.Word;
import repository.WordRepository;


public class WordGAO implements WordRepository {
    private final String FILE_JSON_PATH = "resource/dictionary.json";

    @Override
    public void loadFromFile(Dictionary dictionary) {
        Gson gson = new Gson();

        try (JsonReader reader = new JsonReader(new FileReader(FILE_JSON_PATH))) {
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
