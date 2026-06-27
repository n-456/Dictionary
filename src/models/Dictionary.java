package models;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private Node root;

    // Constructor
    public Dictionary(){}
    public Dictionary(Node root) {
        this.root = root;
    }

    // Getter, setter
    private Node getRoot() {
        return root;
    }
    private void setRoot(Node root) {
        this.root = root;
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


    // INSERT WORD
    public void addWord(Word word) {
        if (word == null)
            return;
        this.root = addRec(word, this.root);
    }
    private Node addRec(Word word, Node root) {
        if (root == null) {
            word.setKeyOfWord(word.getKeyOfWord().toLowerCase());
            return new Node(word);
        }

        if (word.getKeyOfWord().toLowerCase().compareToIgnoreCase(root.getWord().getKeyOfWord().toLowerCase()) < 0) {
            root.setLeft(addRec(word, root.getLeft()));
        }
        else if (word.getKeyOfWord().toLowerCase().compareTo(root.getWord().getKeyOfWord().toLowerCase()) > 0) {
            root.setRight(addRec(word, root.getRight()));
        }

        return root;
    }
}
