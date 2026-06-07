package models;

public class Node {
    private Word word;
    private Node left;
    private Node right;

    public Node(Word word) {
        this.word = word;
        left = null;
        right = null;
    }
    public Node(Word word, Node left, Node right) {
        this.word = word;
        this.left = left;
        this.right = right;
    }

    public Node(Node node){
        this.word = node.word;
        this.left = node.left;
        this.right = node.right;
    }
    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
