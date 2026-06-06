public class Dictionary {
    private Node root;

    public Dictionary(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    //----THÊM NODE
   public void insert(Node node) {
        // Nếu cây rỗng, nút mới chính là root
        if (root == null) {
            root = new Node(node);
            return;
        }

        Node current = root;
        Node parent = null;

        while (true) {
            parent = current;
            // Đi sang trái
            if (node.getWord().getName().compareTo(current.getWord().getName())<0) {
                current = current.getLeft();
                if (current == null) {
                    parent.setLeft(node);
                    return;
                }
            }
            // Đi sang phải
            else if (node.getWord().getName().compareTo(current.getWord().getName())>0) {
                current = current.getRight();
                if (current == null) {
                    parent.setRight(node);
                    return;
                }
            }
            // Nếu giá trị đã tồn tại, bỏ qua (BST thường không chứa giá trị trùng)
            else {
                return;
            }
        }
    }

    //------XOÁ NODE
    // Hàm gọi từ bên ngoài
    public void delete(String name) {
        root = deleteRecursive(root, name);
    }

    // Hàm đệ quy xử lý logic xóa
    private Node deleteRecursive(Node current, String name) {
        // Trường hợp gốc: Cây rỗng hoặc không tìm thấy nút cần xóa
        if (current == null) {
            return null;
        }

        // 1. Duyệt cây để tìm nút cần xóa
        if (name.compareTo(current.getWord().getName()) < 0) {
            current.setLeft(deleteRecursive(current.getLeft(), name));
        } else if (name.compareTo(current.getWord().getName()) > 0) {
            current.setRight(deleteRecursive(current.getRight(), name));
        }
        // 2. Đã tìm thấy nút cần xóa (value == current.value)
        else {
            // TRƯỜNG HỢP 1 & 2: Nút có 1 con hoặc không có con nào
            if (current.getLeft() == null) {
                return current.getRight(); // Nếu không có con trái, trả về con phải (có thể là null)
            } else if (current.getRight() == null) {
                return current.getLeft();  // Nếu không có con phải, trả về con trái
            }

            // TRƯỜNG HỢP 3: Nút có cả 2 con
            // Tìm nút nhỏ nhất bên nhánh phải (In-order Successor)
            current.getWord().setName(findMinValue(current.getRight()));

            // Xóa nút nhỏ nhất đó ở nhánh phải vì đã đem giá trị của nó lên thay thế
            current.setRight(deleteRecursive(current.getRight(), current.getWord().getName()));
        }

        return current;
    }

    // Hàm phụ trợ tìm giá trị nhỏ nhất của một nhánh cây
    private String findMinValue(Node node) {
        String minValue = node.getWord().getName();
        while (node.getLeft() != null) {
            minValue = node.getWord().getName();
            node = node.getLeft();
        }
        return minValue;
    }

    //------- DUYET TRUNG TU
    // Hàm gọi từ bên ngoài
    public void inOrder() {
        inOrderRecursive(root);
    }

    // Hàm đệ quy xử lý logic
    private void inOrderRecursive(Node node) {
        if (node != null) {
            // 1. Đi hết sang cây con bên trái
            inOrderRecursive(node.getLeft());

            // 2. Xử lý/In giá trị của nút hiện tại
            System.out.println(node.getWord().getName());

            // 3. Đi sang cây con bên phải
            inOrderRecursive(node.getRight());
        }
    }

    //-----------TÌM NODE

        // Hàm gọi từ bên ngoài, trả về Node nếu tìm thấy, hoặc null nếu không thấy
        public Node search(String name) {
            return searchRecursive(root, name);
        }

        // Hàm đệ quy xử lý logic
        private Node searchRecursive(Node current, String name) {
            // Trường hợp gốc: nếu cây rỗng hoặc đã tìm thấy nút có giá trị bằng key
            if (current == null || current.getWord().getName() == name) {
                return current;
            }

            // Nếu giá trị cần tìm nhỏ hơn nút hiện tại -> rẽ sang trái
            if (name.compareTo(current.getWord().getName()) < 0) {
                return searchRecursive(current.getLeft(), name);
            }

            // Nếu giá trị cần tìm lớn hơn nút hiện tại -> rẽ sang phải
            return searchRecursive(current.getRight(), name);
   }
}
