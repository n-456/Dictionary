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
    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root = root;
    }

    // PRE-ORDER
    public void preOrder(Node root) {
        if (root != null) {
            System.out.println(root.getWord().getName());
            preOrder(root.getLeft());
            preOrder(root.getRight());
        }
    }

    // IN-ORDER
    public void inOrder(Node root) {
        if (root != null) {
            inOrder(root.getLeft());
            System.out.println(root.getWord().getName());
            inOrder(root.getRight());
        }
    }

    // REVERSE IN-ORDER
    public void reverseInOrder(Node root) {
        if (root != null) {
            reverseInOrder(root.getRight());
            System.out.println(root.getWord().getName());
            reverseInOrder(root.getLeft());
        }
    }

    // POST-ORDER
    public void postOrder(Node root) {
        if (root != null) {
            postOrder(root.getLeft());
            postOrder(root.getRight());
            System.out.println(root.getWord().getName());
        }
    }

    // SEARCH SUBSTRING WORD
    public List<Word> searchSubstringWord(String subString) {
        List<Word> resultList = new ArrayList<>();
        searchSubstringWordRec(subString.toLowerCase(), this.root, resultList);
        return resultList;
    }
    private void searchSubstringWordRec(String subString, Node root, List<Word> resutlList) {
        if (root == null) {
            return;
        }

        String currentWordName = root.getWord().getName();
        if (currentWordName.contains(subString)) {
            resutlList.add(root.getWord());
        }

        searchSubstringWordRec(subString, root.getLeft(), resutlList);
        searchSubstringWordRec(subString, root.getRight(), resutlList);
    }

    // SEARCH WORD
    public Word searchWord(String keyWord) {
        Node node = searchRec(keyWord.toLowerCase(), this.root);
        return (node != null) ? node.getWord() : null;
    }
    private Node searchRec(String keyWord, Node root) {
        if (root == null) {
            return null;
        }

        if (keyWord.compareTo(root.getWord().getName()) == 0) {
            return root;
        }

        if (keyWord.compareTo(root.getWord().getName()) < 0) {
            return searchRec(keyWord, root.getLeft());
        }

        return searchRec(keyWord, root.getRight());
    }

    // INSERT WORD
    public void insertWord(Word word) {
        this.root = insertRec(word, this.root);
    }
    private Node insertRec(Word word, Node root) {
        if (root == null) {
            return new Node(word);
        }

        if (word.getName().compareTo(root.getWord().getName()) < 0) {
            root.setLeft(insertRec(word, root.getLeft()));
        }
        else if (word.getName().compareTo(root.getWord().getName()) > 0) {
            root.setRight(insertRec(word, root.getRight()));
        }

        return root;
    }
}
