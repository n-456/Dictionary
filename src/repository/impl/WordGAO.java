package repository.impl;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;

import models.Dictionary;
import models.Word;
import repository.WordRepository;


public class WordGAO implements WordRepository {
    private String filePath;
//    private final String FILE_JSON_PATH = "resource/dictionary.json";

    public WordGAO(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void loadFromFile(Dictionary dictionary) {
        Gson defaultGson = new Gson();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(
                        TypeOfWord.class,
                        (JsonDeserializer<TypeOfWord>) (json, type, context) ->
                                parseTypeOfWord(json, defaultGson)
                )
                .create();

        try (JsonReader reader = new JsonReader(new FileReader(filePath))) {
            reader.beginArray();

            while (reader.hasNext()) {
                Word word = gson.fromJson(reader, Word.class);
                dictionary.insertWord(word);
            }

            reader.endArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TypeOfWord parseTypeOfWord(JsonElement json, Gson gson) {
        if (!json.isJsonObject()) {
            return gson.fromJson(json, TypeOfWord.class);
        }
        JsonObject obj = json.getAsJsonObject();

        // Trường hợp "typeOfWord" lồng nhau
        if (obj.has("typeOfWord")
                && obj.get("typeOfWord").isJsonObject()) {
            return gson.fromJson(
                    obj.get("typeOfWord"),
                    TypeOfWord.class
            );
        }

        // Trường hợp "typeOfWord" không lồng nhau
        return gson.fromJson(json, TypeOfWord.class);
    }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
