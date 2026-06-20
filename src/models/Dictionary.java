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

    // SEARCH SUBSTRING WORD
    public List<Word> searchSubstringWord(String subString) {
        List<Word> resultList = new ArrayList<>();
        if (subString == null)
            return null;
        searchSubstringWordRec(subString, this.root, resultList);
        return resultList;
    }
    private void searchSubstringWordRec(String subString, Node root, List<Word> resutlList) {
        if (root == null) {
            return;
        }

        String currentWordName = root.getWord().getName().toLowerCase();
        if (currentWordName.contains(subString.toLowerCase())) {
            resutlList.add(root.getWord());
        }

        searchSubstringWordRec(subString, root.getLeft(), resutlList);
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

        if (keyWord.toLowerCase().compareTo(root.getWord().getName().toLowerCase()) == 0) {
            return root;
        }

        if (keyWord.toLowerCase().compareTo(root.getWord().getName().toLowerCase()) < 0) {
            return searchRec(keyWord, root.getLeft());
        }

        return searchRec(keyWord, root.getRight());
    }

    // INSERT WORD
    public void insertWord(Word word) {
        if (word == null)
            return;
        this.root = insertRec(word, this.root);
    }
    private Node insertRec(Word word, Node root) {
        if (root == null) {
            word.setName(word.getName().toLowerCase());
            return new Node(word);
        }

        if (word.getName().toLowerCase().compareTo(root.getWord().getName().toLowerCase()) < 0) {
            root.setLeft(insertRec(word, root.getLeft()));
        }
        else if (word.getName().toLowerCase().compareTo(root.getWord().getName().toLowerCase()) > 0) {
            root.setRight(insertRec(word, root.getRight()));
        }

        return root;
    }
}
