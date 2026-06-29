package views;

import models.TypeOfWord;
import models.Word;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class HomeScreen extends JPanel {
    private JTable table;                                                 // Bảng tổng quan hiển thị các word
    private DefaultTableModel tableModel;
    private JButton btnSort, btnAdd, btnEdit, btnDelete, btnSearchScreen; // Các nút chức năng


    public HomeScreen() {
        setBackground(new Color(220, 224, 230));
        setLayout(new BorderLayout(10, 10));


        // 1. Khu vực bảng tổng quan
        String[] columns = {"KEY OF WORD", "MEANINGS OF WORD"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ngăn chặn chỉnh sửa trực tiếp trên ô
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Chỉ cho phép chọn 1 dòng tại 1 thời điểm
        table.getTableHeader().setReorderingAllowed(false);          // Không được thay đổi thứ tự cột

        JScrollPane scrollPane = new JScrollPane(table);

        // Căn chỉnh bảng trên giao diện
        JPanel pnlCenter = new JPanel(new BorderLayout());
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(25, 30, 10, 30));
        pnlCenter.setOpaque(false);
        pnlCenter.add(scrollPane, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);


        // 2. Khu vực các nút chức năng
        JPanel pnlBottom = new JPanel(new GridBagLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 30, 25, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        btnSort = new JButton("A-Z / Z-A");
        btnSearchScreen = new JButton("Search Screen");
        btnAdd = new JButton("Add");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");

        // Đặt kích thước cho các nút
        Dimension btnSize = new Dimension(95, 30);
        btnAdd.setPreferredSize(btnSize);
        btnEdit.setPreferredSize(btnSize);
        btnDelete.setPreferredSize(btnSize);
        btnSearchScreen.setPreferredSize(new Dimension(130, 30));
        btnSort.setPreferredSize(new Dimension(90, 30));

        // Nhóm các nút bên trái
        gbc.gridx = 0; gbc.weightx = 1.0; gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        JPanel leftGroup = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftGroup.setOpaque(false);
        leftGroup.add(btnSearchScreen);
        leftGroup.add(btnSort);
        pnlBottom.add(leftGroup, gbc);

        // Nhóm các nút bên phải
        gbc.weightx = 0; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 1; pnlBottom.add(btnAdd, gbc);
        gbc.gridx = 2; pnlBottom.add(btnEdit, gbc);
        gbc.gridx = 3; pnlBottom.add(btnDelete, gbc);

        add(pnlBottom, BorderLayout.SOUTH);
    }


    /**
     * Làm mới dữ liệu của bảng khi có thay đổi
     */
    public void updateTable(List<Word> wordList) {
        // Lấy model của bảng để thao tác dữ liệu
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Xóa dữ liệu cũ đang hiển thị trên bảng
        model.setRowCount(0);

        // Duyệt qua từng word để đưa vào bảng
        for (Word word : wordList) {
            String keyOfWord = word.getKeyOfWord();

            // Nối các meaning lại với nhau, cách nhau bởi dấu ";"
            StringBuilder meaningsBuilder = new StringBuilder();
            if (word.getTypeOfWordList() != null) {
                for (TypeOfWord typeOfWord : word.getTypeOfWordList()) {
                    meaningsBuilder.append(typeOfWord.getMeaning()).append("; ");
                }
            }

            // Bỏ ký tự "; " dư thừa ở cuối chuỗi
            String meaningsOfWord = meaningsBuilder.toString();
            if (meaningsOfWord.endsWith("; ")) {
                meaningsOfWord = meaningsOfWord.substring(0, meaningsOfWord.length() - 2);
            }

            // Tạo và thêm 1 dòng dữ liệu mới vào bảng
            Object[] rowData = new Object[] { keyOfWord, meaningsOfWord };
            model.addRow(rowData);
        }
    }


    /**
     * Lấy giá trị keyOfWord của dòng đang được chọn trên bảng
     */
    public String getSelectedKeyOfWord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            return (String) table.getValueAt(selectedRow, 0);
        }
        return null;
    }


    /**
     * Thay đổi quyền của các nút
     */
    public void setEnable(boolean enable) {
        btnSearchScreen.setEnabled(enable);
        btnSort.setEnabled(enable);
        btnEdit.setEnabled(enable);
        btnDelete.setEnabled(enable);
    }


    /**
     * Hiển thị hộp thoại thông báo thông tin chung
     */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }


    /**
     * Đăng ký bộ lắng nghe sự kiện từ lớp Controller
     */
    public void addSortListener(ActionListener l) { btnSort.addActionListener(l); }

    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }

    public void addEditListener(ActionListener l) { btnEdit.addActionListener(l); }

    public void addDeleteListener(ActionListener l) { btnDelete.addActionListener(l); }

    public void addSearchScreenListener(ActionListener l) { btnSearchScreen.addActionListener(l); }

    public void addDetailListener(ActionListener listener) {
        if (table != null) {
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        listener.actionPerformed(null);
                    }
                }
            });
        }
    }
}