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

    // Clear
    public void clear() {
        this.root = null;
    }


    // IN-ORDER
    public void printInOrder() {
        inOrder(getRoot());
    }
    private void inOrder(Node root) {
        if (root != null) {
            inOrder(root.getLeft());
            System.out.println(root.getWord());
            inOrder(root.getRight());
        }
    }

    // REVERSE IN-ORDER
    public void printReverseInOrder() {
        reverseInOrder(getRoot());
    }
    private void reverseInOrder(Node root) {
        if (root != null) {
            reverseInOrder(root.getRight());
            System.out.println(root.getWord());
            reverseInOrder(root.getLeft());
        }
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

    // SEARCH WORD
    public Word searchWord(String keyWord) {
        if (keyWord == null)
            return null;
        Node node = searchRec(keyWord, this.root);
        return (node != null) ? node.getWord() : null;
    }
    private Node searchRec(String keyWord, Node root) {
        if (root == null) {
            return null;
        }

        if (keyWord.compareToIgnoreCase(root.getWord().getKeyOfWord()) == 0) {
            return root;
        }

        if (keyWord.compareTo(root.getWord().getKeyOfWord().toLowerCase()) < 0) {
            return searchRec(keyWord, root.getLeft());
        }

        return searchRec(keyWord, root.getRight());
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
