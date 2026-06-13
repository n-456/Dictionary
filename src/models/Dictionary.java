package models;

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
