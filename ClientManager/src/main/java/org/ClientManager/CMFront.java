package org.ClientManager;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class CMFront extends JFrame {
    private JList searchResultList;
    private ArrayList<Client> currResults;
    private JScrollPane scrollSearchPane;
    private ButtonGroup searchButtonGrp;
    private JRadioButton radioID;
    private JRadioButton radioName;
    private JRadioButton radioType;
    private JButton searchButton;
    private JButton clearSearchButton;
    private JButton addClientButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JTextField clientIDField;
    private JTextField mailField;
    private JTextField passwordField;
    private JTextField cityField;
    private JTextField districtField;
    private JTextArea addressTextArea;
    private JComboBox clientTypeCombo;
    private final Character[] clientTypeChoices = new Character[]{'C', 'R'};

    public CMFront() {
        super("Client Management Screen");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.addElements();
    }

    public void run() {
        this.setSize(700, 600);
        this.setLocationRelativeTo((Component)null);
        this.setVisible(true);
    }

    public void refreshResults(ArrayList<Client> results) {
        this.currResults = results;
        this.searchResultList.setListData(this.currResults.toArray());
    }

    public ArrayList<Client> getCurrResults() {
        return this.currResults;
    }

    public void addSearchListener(ActionListener e) {
        this.searchButton.addActionListener(e);
    }

    public void addClearSearchListener(ActionListener e) {
        this.clearSearchButton.addActionListener(e);
    }

    public void addNewClientListener(ActionListener e) {
        this.addClientButton.addActionListener(e);
    }

    public void addSaveListener(ActionListener e) {
        this.saveButton.addActionListener(e);
    }

    public void addDeleteListener(ActionListener e) {
        this.deleteButton.addActionListener(e);
    }

    public void addResultListListener(ListSelectionListener e) {
        this.searchResultList.addListSelectionListener(e);
    }

    public void errorMessage(Container cont, String message) {
        JOptionPane.showMessageDialog(cont, message, "Error!", 0);
    }

    public void successMessage(Container cont, String message) {
        JOptionPane.showMessageDialog(cont, message, "Success!", 1);
    }

    public String getSearchQuery() {
        return this.searchField.getText();
    }

    public String getSearchCriteria() {
        return this.searchButtonGrp.getSelection().getActionCommand();
    }

    public void clearSearch() {
        this.searchField.setText("");
        DefaultListModel empty = new DefaultListModel();
        this.searchResultList.setModel(empty);
        this.clearClientDetails();
    }

    public Client getSelectedClient() {
        Object selection = this.searchResultList.getSelectedValue();
        return selection == null ? null : (Client)selection;
    }

    public void populateClientDetails(Client client) {
        if (client != null) {
            this.clientIDField.setText("" + client.getId());
            this.mailField.setText(client.getMail());
            this.passwordField.setText(client.getPassword());
            this.addressTextArea.setText(client.getAddress());
            this.cityField.setText(client.getCity());
            this.districtField.setText(client.getDistrict());
        }
    }

    public String getMailField() {
        return this.mailField.getText();
    }

    public String getPasswordField() {
        return this.passwordField.getText();
    }

    public String getCityField() {
        return this.cityField.getText();
    }

    public String getDistrictField() {
        return this.districtField.getText();
    }

    public String getAddressTextArea() {
        return this.addressTextArea.getText();
    }

    public void clearClientDetails() {
        this.clientIDField.setText("");
        this.mailField.setText("");
        this.passwordField.setText("");
        this.addressTextArea.setText("");
        this.cityField.setText("");
        this.districtField.setText("");
    }

    private void addElements() {
        JPanel searchPanel = this.createSearchPanel();
        JPanel resultPanel = this.createResultPanel();
        this.add(searchPanel, "West");
        this.add(resultPanel, "East");
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new GridLayout(2, 1));
        searchPanel.setPreferredSize(new Dimension(300, searchPanel.getHeight()));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Client"));
        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.setLayout(new BoxLayout(searchResultsPanel, 3));
        this.searchResultList = new JList();
        this.scrollSearchPane = new JScrollPane(this.searchResultList);
        searchResultsPanel.add(new JLabel("Search Results (Select entry to view)"));
        searchResultsPanel.add(this.scrollSearchPane);
        searchPanel.add(this.createSearchForm());
        searchPanel.add(searchResultsPanel);
        return searchPanel;
    }

    private JPanel createSearchForm() {
        JPanel searchForm = new JPanel();
        searchForm.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = 2;
        gc.insets = new Insets(5, 5, 5, 5);
        searchForm.add(new JLabel("Select Type of Search to be Performed: "), gc);
        this.searchButtonGrp = new ButtonGroup();
        this.radioID = new JRadioButton("Client ID");
        this.radioID.setSelected(true);
        this.radioID.setActionCommand("id");
        this.radioName = new JRadioButton("city");
        this.radioName.setActionCommand("city");
        this.radioType = new JRadioButton("mail");
        this.radioType.setActionCommand("mail");
        this.searchButtonGrp.add(this.radioID);
        this.searchButtonGrp.add(this.radioName);
        this.searchButtonGrp.add(this.radioType);
        gc.gridy = 1;
        searchForm.add(this.radioID, gc);
        gc.gridy = 2;
        searchForm.add(this.radioName, gc);
        gc.gridy = 3;
        searchForm.add(this.radioType, gc);
        gc.gridy = 4;
        searchForm.add(new JLabel("Enter the Search Parameter below:"), gc);
        gc.gridy = 5;
        this.searchField = new JTextField(12);
        searchForm.add(this.searchField, gc);
        gc.gridy = 6;
        gc.gridx = 0;
        this.searchButton = new JButton("Search");
        this.clearSearchButton = new JButton("Clear Search");
        searchForm.add(this.searchButton, gc);
        gc.gridy = 7;
        searchForm.add(this.clearSearchButton, gc);
        return searchForm;
    }

    private JPanel createResultPanel() {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(10, 1));
        resultPanel.setPreferredSize(new Dimension(400, resultPanel.getHeight()));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Client Information"));
        this.clientIDField = new JTextField(10);
        this.clientIDField.setEnabled(false);
        this.mailField = new JTextField(20);
        this.passwordField = new JTextField(20);
        this.addressTextArea = new JTextArea(3, 20);
        this.addressTextArea.setLineWrap(true);
        JPanel addressRow = new JPanel();
        addressRow.add(new JLabel("Address: "));
        addressRow.add(this.addressTextArea);
        this.cityField = new JTextField(10);
        this.districtField = new JTextField(20);
        this.clientTypeCombo = new JComboBox(this.clientTypeChoices);
        JPanel typeRow = new JPanel();
        this.saveButton = new JButton("Save");
        this.deleteButton = new JButton("Delete Client");
        this.addClientButton = new JButton("Add New Client");
        resultPanel.add(this.resultRow(new JLabel("Client ID: "), this.clientIDField));
        resultPanel.add(this.resultRow(new JLabel("mail: "), this.mailField));
        resultPanel.add(this.resultRow(new JLabel("password: "), this.passwordField));
        resultPanel.add(addressRow);
        resultPanel.add(this.resultRow(new JLabel("city: "), this.cityField));
        resultPanel.add(this.resultRow(new JLabel("district: "), this.districtField));
        resultPanel.add(typeRow);
        resultPanel.add(this.saveButton);
        resultPanel.add(this.deleteButton);
        resultPanel.add(this.addClientButton);
        return resultPanel;
    }

    private JPanel resultRow(JLabel l, JTextField tf) {
        JPanel panel = new JPanel();
        panel.add(l);
        panel.add(tf);
        return panel;
    }
}
