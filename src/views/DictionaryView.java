package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DictionaryView extends JFrame {
    private JButton btnAdd, btnSearch;
    private JTextField txtInput;

    public DictionaryView() {
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtInput = new JTextField(15);
        btnAdd = new JButton("Add");
        btnSearch = new JButton("Search");

        JPanel panelContent = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panelContent.add(txtInput);
        panelContent.add(btnAdd);
        panelContent.add(btnSearch);
        add(panelContent);
    }

    public JTextField getTxtInput() {
        return txtInput;
    }

    public void addAddListener(ActionListener l) { btnAdd.addActionListener(l); }
    public void addSearchListener(ActionListener l) { btnSearch.addActionListener(l); }
}
