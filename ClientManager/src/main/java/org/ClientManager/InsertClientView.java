package org.ClientManager;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InsertClientView extends JFrame {
    //Insert button interface
    private JTextField mailField;
    private JTextField passwordField;
    private JTextField addressField;
    private JTextField cityField;
    private JTextField districtField;
    private JButton insert;
    private JButton returnMain;

    public InsertClientView() {
        super("Insert Client");
        this.setLayout(new BoxLayout(this.getContentPane(), 1));
        this.setDefaultCloseOperation(2);
        this.setResizable(false);
        this.addElements();
    }

    public void run() {
        //frame starter
        this.setSize(300, 100);
        this.pack();
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
    }

    public void clearFields() {
        //i think i don't need to explain this area lol
        this.mailField.setText("");
        this.passwordField.setText("");
        this.addressField.setText("");
        this.cityField.setText("");
        this.districtField.setText("");
    }

    public void addInsertListener(ActionListener e) {
        this.insert.addActionListener(e);
    }

    public void addCancelListener(ActionListener e) {
        this.returnMain.addActionListener(e);
    }

    public String getMailField() {
        return this.mailField.getText();
    }

    public String getPasswordField() {
        return this.passwordField.getText();
    }

    public String getAddressField() {
        return this.addressField.getText();
    }

    public String getCity() {
        return this.cityField.getText();
    }

    public String getDistrict() {
        return this.districtField.getText();
    }


    private void addElements() {
        this.add(new JLabel("Add a Client to the Database"));
        this.add(this.firstRow());
        this.add(this.secondRow());
        this.add(this.thirdRow());
        this.add(this.buttonRow());
    }

    private JPanel firstRow() {
        JPanel firstRow = new JPanel();
        this.mailField = new JTextField();
        this.mailField.setColumns(20);
        this.passwordField = new JTextField();
        this.passwordField.setColumns(20);
        firstRow.add(new JLabel("mail: "));
        firstRow.add(this.mailField);
        firstRow.add(new JLabel("password: "));
        firstRow.add(this.passwordField);
        return firstRow;
    }

    private JPanel secondRow() {
        JPanel secondRow = new JPanel();
        this.addressField = new JTextField(50);
        this.cityField = new JTextField(10);
        secondRow.add(new JLabel("Address: "));
        secondRow.add(this.addressField);
        secondRow.add(new JLabel("city: "));
        secondRow.add(this.cityField);
        return secondRow;
    }

    private JPanel thirdRow() {
        JPanel thirdRow = new JPanel();
        this.districtField = new JTextField(10);
        thirdRow.add(new JLabel("district: "));
        thirdRow.add(this.districtField);
        return thirdRow;
    }

    private JPanel buttonRow() {
        JPanel buttonRow = new JPanel();
        this.insert = new JButton("Insert Client");
        this.returnMain = new JButton("Cancel");
        buttonRow.add(this.insert);
        buttonRow.add(this.returnMain);
        return buttonRow;
    }
}
