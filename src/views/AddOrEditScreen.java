package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AddOrEditScreen extends JPanel {
    private JTextField txtKeyOfWord;                    // Ô nhập keyOfWord
    private JPanel container;                           // Khung chứa hiển thị các block lên giao diện (block dùng để hiển thị thông tin typeOfWord)
    private List<Block> blockList;                      // Danh sách để quản lý các block đang hiển thị
    private JButton btnNewType, btnCancel, btnSave;     // Các nút chức năng


    public AddOrEditScreen() {
        setBackground(new Color(220, 224, 230));
        setLayout(new BorderLayout(10, 10));
        blockList = new ArrayList<>();


        // 1. Khu vực nhập keyOfWord
        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        pnlTop.setOpaque(false);
        JLabel lblWord = new JLabel("Key of word:");
        lblWord.setFont(new Font("Arial", Font.BOLD, 14));
        txtKeyOfWord = new JTextField(30);
        txtKeyOfWord.setPreferredSize(new Dimension(200, 28));
        pnlTop.add(lblWord);
        pnlTop.add(txtKeyOfWord);
        add(pnlTop, BorderLayout.NORTH);


        // 2. Khu vực hiển thị các block, có hỗ trợ cuộn chuột
        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(220, 224, 230));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);


        // 3. Khu vực các nút chức năng
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();

        btnNewType = new JButton("New type of word");
        btnCancel = new JButton("Cancel");
        btnSave = new JButton("Save");

        // Đặt kích thước cho các nút
        Dimension btnSize = new Dimension(100, 30);
        btnCancel.setPreferredSize(btnSize);
        btnSave.setPreferredSize(btnSize);
        btnNewType.setPreferredSize(new Dimension(140, 30));

        // Nhóm các nút bên trái
        gbc.gridx = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        bottomPanel.add(btnNewType, gbc);

        // Nhóm các nút bên phải
        JPanel rightGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightGroup.setOpaque(false);
        rightGroup.add(btnCancel);
        rightGroup.add(btnSave);

        gbc.gridx = 1; gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        bottomPanel.add(rightGroup, gbc);
        add(bottomPanel, BorderLayout.SOUTH);
    }


    /**
     * Làm mới giao diện của container khi có thay đổi
     */
    private void updateContainer() {
        container.revalidate();
        container.repaint();

        Component parent = container.getParent();
        if (parent instanceof JViewport) {
            JViewport viewport = (JViewport) parent;
            if (viewport.getParent() instanceof JScrollPane) {
                JScrollPane scrollPane = (JScrollPane) viewport.getParent();
                scrollPane.revalidate();
                scrollPane.repaint();
            }
        }
    }


    /**
     * Xoá toàn bộ dữ lệu trên màn hình
     */
    public void clearAll() {
        txtKeyOfWord.setText("");
        container.removeAll();
        blockList.clear();
        updateContainer();
    }


    /**
     * Gán giá trị keyOfWord cho ô nhập
     */
    public void setKeyOfWord(String keyOfWord) { txtKeyOfWord.setText(keyOfWord); }


    /**
     * Thay đổi quyền chỉnh sửa của ô nhập keyOfWord
     */
    public void setKeyOfWordEditable(boolean editable) { txtKeyOfWord.setEditable(editable); }


    /**
     * Thêm 1 block mới trên giao diện
     */
    public void addBlock(String meaning, String partOfSpeech, String example) {
        Block block = new Block(meaning, partOfSpeech, example);
        blockList.add(block);
        container.add(block);
        Component strut = Box.createVerticalStrut(10);
        container.add(strut);

        // Đăng ký sự kiện: xoá 1 block
        block.addDeleteBlockListener(e -> {
            blockList.remove(block);
            container.remove(block); // Xóa block đồ họa
            container.remove(strut); // Xóa luôn khoảng trống đi kèm block đó
            updateContainer();
        });

        updateContainer();
    }


    /**
     * Hiển thị hộp thoại cảnh báo lỗi nhập liệu
     */
    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }


    /**
     * Đăng ký bộ lắng nghe sự kiện từ lớp Controller
     */
    public void addNewTypeListener(ActionListener l) { btnNewType.addActionListener(l); }

    public void addCancelListener(ActionListener l) { btnCancel.addActionListener(l); }

    public void addSaveListener(ActionListener l) { btnSave.addActionListener(l); }


    /**
     * Lớp nội (inner class):
     * Là một khối đồ hoạ trên giao diện, hiển thị các thông tin typeOfWord
     * Thông tin gồm meaning (nghĩa), partOfSpeech (từ loại), example (ví dụ)
     */
    private class Block extends JPanel {
        private JTextField txtMeaning, txtPartOfSpeech, txtExample;
        private JButton btnDeleteBlock;

        public Block(String meaning, String partOfSpeech, String example) {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            setLayout(new BorderLayout(5, 5));
            setPreferredSize(new Dimension(500, 130));
            setMaximumSize(new Dimension(Short.MAX_VALUE, 130));


            // Tạo bố cục 3 hàng 2 cột cho các cặp nhãn - ô nhập liệu
            JPanel inputGrid = new JPanel(new GridLayout(3, 2, 5, 8));
            inputGrid.setOpaque(false);

            inputGrid.add(new JLabel("Meaning:"));
            txtMeaning = new JTextField(meaning); inputGrid.add(txtMeaning);

            inputGrid.add(new JLabel("Part of speech:"));
            txtPartOfSpeech = new JTextField(partOfSpeech); inputGrid.add(txtPartOfSpeech);

            inputGrid.add(new JLabel("Example:"));
            txtExample = new JTextField(example); inputGrid.add(txtExample);

            add(inputGrid, BorderLayout.CENTER);

            // Bố trí nút "Delete type of word" nằm ở khu vực góc trên cùng bên phải
            JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            topButtonPanel.setOpaque(false);
            btnDeleteBlock = new JButton("Delete type of word");
            topButtonPanel.add(btnDeleteBlock);
            add(topButtonPanel, BorderLayout.NORTH);
        }

        // Các phương thức lấy giá trị trong các ô nhập của block
        public String getMeaningValue() { return txtMeaning.getText().trim(); }
        public String getPartOfSpeechValue() { return txtPartOfSpeech.getText().trim(); }
        public String getExampleValue() { return txtExample.getText().trim(); }

        // Đăng ký bộ lắng nghe sự kiện cho nút "Delete type of word"
        public void addDeleteBlockListener(ActionListener l) { btnDeleteBlock.addActionListener(l); }
    }
}