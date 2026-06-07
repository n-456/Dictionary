//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary();

        System.out.println("=== 1. THỰC HIỆN THÊM CÁC NÚT (INSERT) ===");

        dictionary.insert(new Word("cat"));
        dictionary.insert(new Word("bed"));
        dictionary.insert(new Word("ant"));
        dictionary.insert(new Word("hen"));
        dictionary.insert(new Word("see"));
        dictionary.insert(new Word("pet"));

        System.out.println("Đã thêm thành công");




        System.out.println("\n=== 2. KIỂM TRA DUYỆT CÂY TRUNG THỨ TỰ (IN-ORDER) ===");

        System.out.print("Cây hiện tại (luôn tăng dần): ");
        dictionary.inOrder(dictionary.getRoot());




        System.out.println("\n=== 3. KIỂM TRA HÀM TÌM KIẾM (SEARCH) ===");
        String target1 = "hen";
        String target2 = "sea";

        Node searchResult1 = dictionary.search(target1,dictionary.getRoot());
        if (searchResult1 != null) {
            System.out.println("-> Tìm thấy nút mang giá trị: " + searchResult1.getWord().getName());
        } else {
            System.out.println("-> Không tìm thấy giá trị " + target1);
        }

        Node searchResult2 = dictionary.search(target2,dictionary.getRoot());
        if (searchResult2 != null) {
            System.out.println("-> Tìm thấy nút mang giá trị: " + searchResult2.getWord().getName());
        } else {
            System.out.println("-> Không tìm thấy giá trị " + target2);
        }




        System.out.println("\n=== 4. THỰC HIỆN XÓA NÚT (DELETE) ===");

        System.out.println("- Xóa nút: pen");
        dictionary.delete("cat",dictionary.getRoot());
        System.out.println("Cây sau khi xóa");
        dictionary.inOrder(dictionary.getRoot());

    }
}