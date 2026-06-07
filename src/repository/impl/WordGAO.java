package repository.impl;

import models.Dictionary;
import models.Node;
import models.Word;
import repository.WordRepository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WordGAO implements WordRepository {
    private final String FILE_JSON_PATH = "resource/dictionary.json";

    @Override
    public List<Word> readAll() {
        Gson gson = new Gson();
        List<Word> words = new ArrayList<>();

        // Create "reader" by "input" and pass "reader" to gson.fromJson()
        try (FileReader reader = new FileReader(FILE_JSON_PATH)){
            Type listType = new TypeToken<ArrayList<Word>>(){}.getType();
            words = gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.err.println("Cannot read words: " + e.getMessage());
            throw new RuntimeException("Cannot read words", e);
        }
        return words==null?new ArrayList<>():words;
    }

    private void inOrder2(Node root, List<Word> words) {
        if (root != null) {
            inOrder2(root.getLeft(), words);
            words.add(root.getWord());
            inOrder2(root.getRight(), words);
        }
    }

    @Override
    public void writeAll(Node root){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Word> words = new ArrayList<>();
        inOrder2(root,words);
        try (FileWriter writer = new FileWriter(FILE_JSON_PATH)){
            gson.toJson(words,writer);
            writer.close();
            System.out.println("SAVED to words.json");
        } catch (IOException e){
            System.err.println("Cannot save words: " + e.getMessage());
            throw new RuntimeException("Cannot save words", e);
        }
    }
}
