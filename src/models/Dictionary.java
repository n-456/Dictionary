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
