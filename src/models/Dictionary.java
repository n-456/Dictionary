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

    //----INSERT NODE
    public void insert(Word word) {
        this.root = insertRec(word, this.root);
    }
    public Node insertRec(Word word, Node root) {
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

    //----DELETE NODE
    // Hàm public gọi từ bên ngoài
    public void delete(String name) {
        // QUAN TRỌNG: Gán lại root của toàn bộ cây bằng kết quả sau khi xóa
        this.root = delete(name, this.root);
    }
    // Hàm này trả về models.Node con đã được cập nhật sau khi xóa models.Node nhỏ nhất
    private Node deleteMin(Node root) {
        if (root.getLeft() == null) {
            // Đây chính là models.Node nhỏ nhất!
            // Trả về nhánh bên phải của nó để models.Node cha nối trực tiếp vào nhánh phải này (bỏ qua models.Node hiện tại)
            return root.getRight();
        }
        // Đệ quy sâu xuống bên trái và cập nhật lại liên kết trái
        root.setLeft(deleteMin(root.getLeft()));
        return root;
    }
    // Hàm đệ quy trả về models.Node sau khi đã xóa
    public Node delete(String name, Node root) {
        if (root == null) {
            return null; // Không tìm thấy models.Node cần xóa
        }

        // 1. Điều hướng tìm models.Node cần xóa
        if (name.compareTo(root.getWord().getName()) < 0) {
            root.setLeft(delete(name, root.getLeft()));
        }
        else if (name.compareTo(root.getWord().getName()) > 0) {
            root.setRight(delete(name, root.getRight()));
        }

        // 2. Đã tìm thấy models.Node cần xóa (name == root.getWord().getName())
        else {
            // Trường hợp 1 & 2: models.Node có 1 con hoặc không có con nào (models.Node lá)
            if (root.getLeft() == null) {
                return root.getRight(); // Nếu left null, đưa nhánh right lên thay thế (hoặc null nếu cả 2 null)
            }
            if (root.getRight() == null) {
                return root.getLeft();  // Nếu right null, đưa nhánh left lên thay thế
            }

            // Trường hợp 3: models.Node có cả 2 con
            // Tìm models.Word nhỏ nhất ở nhánh bên phải để thay thế vào models.Node hiện tại
            Word minWord = getMinWord(root.getRight());
            root.setWord(minWord);

            // Xóa models.Node nhỏ nhất đó ra khỏi nhánh bên phải
            root.setRight(deleteMin(root.getRight()));
        }

        return root; // Trả về root đã cập nhật cho tầng cha
    }
    // Hàm phụ để lấy nhanh models.Word nhỏ nhất (phục vụ cho hàm delete)
    private Word getMinWord(Node root) {
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        return root.getWord();
    }

    //---- Duyệt trung tự
    public void inOrder(Node root) {
        if (root != null) {
            inOrder(root.getLeft());
            System.out.println(root.getWord().getName());
            inOrder(root.getRight());
        }
    }

    //----SEARCH NODE
    public Node search(String name, Node root) {
        if (root == null) {
                return null;
        }
        else if (name.compareTo(root.getWord().getName()) == 0) {
            return root;
        }
        else if (name.compareTo(root.getWord().getName()) < 0) {
                return search(name, root.getLeft());
        }
        else {
            return search(name, root.getRight());
        }
   }
}
