package repository;

import models.Dictionary;

public interface WordRepository {
    public void loadFromFile(Dictionary dictionary);
}
