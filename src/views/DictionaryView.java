package views;

import javax.swing.*;
import java.awt.*;

public class DictionaryView extends JFrame {
    private JPanel pnlCurrent;


    public DictionaryView() {
        setTitle("DICTIONARY APPLICATION");
        setSize(650, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    /**
     * Đổi giao diện
     */
    public void changeContentPanel(JPanel pnlNew) {
        if (pnlCurrent != null) {
            // Xoá giao diện cũ
            remove(pnlCurrent);
        }
        pnlCurrent = pnlNew;
        // Thêm giao diện mới
        add(pnlCurrent, BorderLayout.CENTER);

        // Cập nhật lại cửa sổ
        revalidate();
        repaint();
    }
}