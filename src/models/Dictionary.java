package models;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private Node root;
    private int count;

    // Constructor
    public Dictionary(){}
    public Dictionary(Node root) {
        this.root = root;
        this.count = 0;
    }

    // Getter, setter
    private Node getRoot() {
        return root;
    }
    private void setRoot(Node root) {
        this.root = root;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Lấy danh sách word theo thứ tự A - Z
     */
    public List<Word> getInOrderWords() {
        List<Word> list = new ArrayList<>();
        collectInOrder(this.root, list);
        return list;
    }

    private void collectInOrder(Node root, List<Word> list) {
        if (root == null) {
            return;
        }

        collectInOrder(root.getLeft(), list);
        if (root.getWord() != null) {
            list.add(root.getWord());
        }
        collectInOrder(root.getRight(), list);
    }


    /**
     * Lấy danh sách word theo thứ tự Z - A
     */
    public List<Word> getReverseInOrderWords() {
        List<Word> list = new ArrayList<>();
        collectReverseInOrder(this.root, list);
        return list;
    }

    private void collectReverseInOrder(Node root, List<Word> list) {
        if (root == null) {
            return;
        }

        collectReverseInOrder(root.getRight(), list);
        if (root.getWord() != null) {
            list.add(root.getWord());
        }
        collectReverseInOrder(root.getLeft(), list);
    }


    /**
     * Tìm word bằng substring
     */
    public List<Word> searchSubstringWord(String subString) {
        if (subString == null || subString.trim().isEmpty()) {
            return null;
        }
        List<Word> resultList = new ArrayList<>();
        searchSubstringWordRec(subString.trim().toLowerCase(), this.root, resultList);
        return resultList;
    }

    private void searchSubstringWordRec(String subString, Node root, List<Word> resutlList) {
        if (root == null) {
            return;
        }

        searchSubstringWordRec(subString, root.getLeft(), resutlList);

        if (root.getWord() != null && root.getWord().getKeyOfWord() != null) {
            if (root.getWord().getKeyOfWord().contains(subString.toLowerCase())) {
                resutlList.add(root.getWord());
            }
        }

        searchSubstringWordRec(subString, root.getRight(), resutlList);
    }

    /**
     * Tìm word
     */
    public Word searchWord(String keyOfWord) {
        if (keyOfWord == null || keyOfWord.trim().isEmpty()) {
            return null;
        }
        Node node = searchRec(keyOfWord.trim(), this.root);
        return (node != null) ? node.getWord() : null;
    }

    private Node searchRec(String keyOfWord, Node root) {
        if (root == null) {
            return null;
        }

        String keyWordLower = keyOfWord.toLowerCase();
        String rootWordLower = root.getWord().getKeyOfWord().toLowerCase();

        int cmp = keyWordLower.compareTo(rootWordLower);
        if (cmp == 0) {
            return root;
        }

        if (cmp < 0) {
            return searchRec(keyOfWord, root.getLeft());
        }

        return searchRec(keyOfWord, root.getRight());
    }


    /**
     * Thêm word
     */
    public boolean addWord(Word word) {
        if (word == null || word.getKeyOfWord() == null || word.getKeyOfWord().trim().isEmpty()) {
            return false;
        }
        int countBeforeAdd = this.count;
        this.root = addRec(word, this.root);
        return (countBeforeAdd != this.count);
    }

    private Node addRec(Word word, Node root) {
        if (root == null) {
            this.count ++;
            return new Node(word);
        }

        if (root.getWord() == null || root.getWord().getKeyOfWord() == null) {
            root.setWord(word);
            this.count++;
            return root;
        }

        String keyWordLower = word.getKeyOfWord().toLowerCase();
        String rootWordLower = root.getWord().getKeyOfWord().toLowerCase();

        int cmp = keyWordLower.compareTo(rootWordLower);
        if (cmp < 0) {
            root.setLeft(addRec(word, root.getLeft()));
        } else if (cmp > 0) {
            root.setRight(addRec(word, root.getRight()));
        }

        return root;
    }


    /**
     * Sửa word
     */
    public boolean editWord(String keyOfWord, List<TypeOfWord> typeOfWordList) {
        if (keyOfWord == null || typeOfWordList == null) {
            return false;
        }
        Node resultNode = searchRec(keyOfWord, this.root);
        if (resultNode == null || resultNode.getWord() == null) {
            return false;
        }
        Word word = resultNode.getWord();
        word.setTypeOfWordList(new ArrayList<>(typeOfWordList));
        return true;
    }


    /**
     * Xoá word
     */
    public boolean deleteWord(String keyOfWord) {
        if (keyOfWord == null || keyOfWord.trim().isEmpty()) {
            return false;
        }
        int countBeforeDelete = this.count;
        this.root = deleteRec(keyOfWord.trim(), this.root);
        return (countBeforeDelete != this.count);
    }

    private Node deleteRec(String keyOfWord, Node root) {
        if (root == null) {
            return null;
        }

        int compare = keyOfWord.compareToIgnoreCase(root.getWord().getKeyOfWord());
        if (compare < 0) {
            root.setLeft(deleteRec(keyOfWord, root.getLeft()));
        } else if (compare > 0) {
            root.setRight(deleteRec(keyOfWord, root.getRight()));
        } else {
            this.count--;

            if (root.getLeft() == null) {
                return root.getRight();
            }
            if (root.getRight() == null) {
                return root.getLeft();
            }

            Word minWord = getMinWord(root.getRight());
            if (minWord != null) {
                root.setWord(minWord);
                root.setRight(deleteMin(root.getRight()));
            } else {
                return root.getLeft();
            }
        }

        return root;
    }

    private Word getMinWord(Node root) {
        if (root == null) {
            return null;
        }
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root.getWord();
    }

    private Node deleteMin(Node root) {
        if (root == null) {
            return null;
        }
        if (root.getLeft() == null) {
            return root.getRight();
        }
        root.setLeft(deleteMin(root.getLeft()));
        return root;
    }


    /**
     * Thêm danh sách word (dùng để dựng cây)
     */
    public void addAllWord(List<Word> wordList) {
        if (wordList == null || wordList.isEmpty()) {
            return;
        }
        for (Word word : wordList) {
            addWord(word);
        }
    }

}
