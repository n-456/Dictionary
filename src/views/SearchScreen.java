package views;

import models.TypeOfWord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchScreen extends JPanel {
    private JTextField txtKeyOfWord;            // Ô tìm kiếm, nhập vào keyOfWord để tìm word
    private JButton btnSearch, btnHomeScreen;   // Các nút chức năng


    // Khung chứa nằm ở trung tâm,
    // hỗ trợ chuyển đổi: giao diện trống khi chưa tìm và giao diện kết quả khi tìm thành công
    private JPanel pnlCenterCard;
    private CardLayout layoutCenterCard;

    // Giao diện trống khi chưa tìm kiếm
    private JPanel pnlEmpty;

    // Giao diện kết quả khi tìm kiếm thành công
    private JPanel pnlResult;
    private JLabel lblKeyOfWord;
    private JButton btnEdit, btnDelete;
    private JPanel container;


    public SearchScreen() {
        setBackground(new Color(220, 224, 230));
        setLayout(new BorderLayout(10, 10));


        // 1. Khu vực thanh tìm kiếm
        JPanel pnlTop = new JPanel(new BorderLayout(10, 0));
        pnlTop.setOpaque(false);
        pnlTop.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        txtKeyOfWord = new JTextField();
        txtKeyOfWord.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnSearch = new JButton("Search");
        btnSearch.setPreferredSize(new Dimension(90, 30));

        pnlTop.add(txtKeyOfWord, BorderLayout.CENTER);
        pnlTop.add(btnSearch, BorderLayout.EAST);
        add(pnlTop, BorderLayout.NORTH);


        // 2. Khu vực chuyển đổi giữa giao diện trống và giao diện kết quả
        layoutCenterCard = new CardLayout();
        pnlCenterCard = new JPanel(layoutCenterCard);
        pnlCenterCard.setOpaque(false);

        // 2.a Khởi tạo giao diện trống (khi chưa tìm)
        pnlEmpty = new JPanel(new GridBagLayout());
        pnlEmpty.setOpaque(false);
        JLabel lblSearchWord = new JLabel("Search word");
        lblSearchWord.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        lblSearchWord.setForeground(new Color(40, 40, 40));
        pnlEmpty.add(lblSearchWord);
        pnlCenterCard.add(pnlEmpty, "EMPTY");

        // 2.b Khởi tạo giao diện kết quả (khi tìm thành công)
        pnlResult = new JPanel(new BorderLayout(10, 10));
        pnlResult.setOpaque(false);
        pnlResult.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        JPanel resultHeader = new JPanel(new BorderLayout());
        resultHeader.setOpaque(false);

        lblKeyOfWord = new JLabel("");
        lblKeyOfWord.setFont(new Font("Segoe UI", Font.PLAIN, 26));

        JPanel btnGroup = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnGroup.setOpaque(false);
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        Dimension btnSize = new Dimension(85, 30);
        btnEdit.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);
        btnGroup.add(btnEdit);
        btnGroup.add(btnDelete);

        resultHeader.add(lblKeyOfWord, BorderLayout.CENTER);
        resultHeader.add(btnGroup, BorderLayout.EAST);
        pnlResult.add(resultHeader, BorderLayout.NORTH);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(new Color(220, 224, 230));

        JScrollPane scrollPane = new JScrollPane(container);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        pnlResult.add(scrollPane, BorderLayout.CENTER);

        pnlCenterCard.add(pnlResult, "RESULT");
        add(pnlCenterCard, BorderLayout.CENTER);


        // 3. Khu vực điều hướng
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        btnHomeScreen = new JButton("Home Screen");
        btnHomeScreen.setPreferredSize(new Dimension(130, 30));

        pnlBottom.add(btnHomeScreen);
        add(pnlBottom, BorderLayout.SOUTH);


        // 4. Đặt giao diện trống làm mặc định khi khởi chạy
        layoutCenterCard.show(pnlCenterCard, "EMPTY");
    }


    /**
     * Hiển thị giao diện trống
     */
    public void showEmptyPanel() {
        layoutCenterCard.show(pnlCenterCard, "EMPTY");
    }



    /**
     * Lấy giá trị keyOfWord được nhập vào ô tìm kiếm
     */
    public String getSearchKeyOfWord() { return txtKeyOfWord.getText().trim(); }


    /**
     * Đặt giá trị keyOfWord cho ô tìm kiếm
     */
    public void setSearchKeyOfWord(String text) { txtKeyOfWord.setText(text); }


    /**
     * Hiển thị nghĩa
     */
    public void display(String keyOfWord, List<TypeOfWord> typeOfWordList) {
        // Tạo và định dạng chuỗi văn bản
        StringBuilder sb = new StringBuilder();
        sb.append(keyOfWord).append(":\n\n");

        if (typeOfWordList != null) {
            for (int i = 0; i < typeOfWordList.size(); i++) {
                TypeOfWord type = typeOfWordList.get(i);
                sb.append("typeOfWord ").append(i + 1).append(":\n");
                sb.append("meaning: ").append(type.getMeaning()).append("\n");
                sb.append("part of speech: ").append(type.getPartOfSpeech()).append("\n");
                sb.append("example: ").append(type.getExample()).append("\n\n");
            }
        }

        // Cập nhật lên JTextArea
        container.removeAll();

        JTextArea textArea = new JTextArea();
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        textArea.setBackground(java.awt.Color.WHITE);
        container.add(textArea);
        container.add(Box.createVerticalStrut(10));

        container.revalidate();
        container.repaint();
        layoutCenterCard.show(pnlCenterCard, "RESULT");
    }



    /**
     * Hiển thị hộp thoại thông báo thông tin chung
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Đăng ký bộ lắng nghe sự kiện từ lớp Controller
     */
    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); txtKeyOfWord.addActionListener(l); }

    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }

    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }

    public void addHomeScreenListener(ActionListener l) { btnHomeScreen.addActionListener(l); }


}