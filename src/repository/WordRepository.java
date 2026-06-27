package repository;

import models.Dictionary;
import models.Word;

import java.util.List;

public interface WordRepository {
    public List<Word> loadAllWords();
}
