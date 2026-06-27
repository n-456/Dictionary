package repository;

import models.Node;
import models.Dictionary;

public interface WordRepository {
    public void loadFromFile(Dictionary dictionary);
}
