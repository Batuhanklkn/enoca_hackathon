package org.ClientManager;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CMController {
    private CMFront theCMSView;
    private InsertClientView theInsertView;
    private CMModel theModel;
    private InputVerify verifyInput;

    public CMController(CMFront cmsv, InsertClientView icv, CMModel cmsm) {
        this.theCMSView = cmsv;
        this.theInsertView = icv;
        this.theModel = cmsm;
        this.verifyInput = new InputVerify(this.theCMSView);
        this.addListeners();
    }

    public void run() {
        this.theCMSView.run();
    }

    private void searchDB(String criteria, String query) {
        boolean validQuery;
        switch (criteria) {
            case "id":
                validQuery = !this.verifyInput.isInvalidID(query);
                break;
            case "mail":
                validQuery = !this.verifyInput.isInvalidMailPass("placeholder", query);
                break;
            case "city":
                validQuery = !this.verifyInput.isInvalidCity(query);
                break;
            default:
                validQuery = false;
        }

        if (validQuery) {
            ArrayList<Client> searchResults = this.theModel.getSearchResults(criteria, query);
            if (searchResults != null) {
                this.theCMSView.refreshResults(searchResults);
            }
        }

    }

    private boolean invalidClient(String mail, String password, String address, String city, String district) {
        return this.verifyInput.isInvalidMailPass(mail, password) || this.verifyInput.isInvalidAddress(address) || this.verifyInput.isInvalidDistrict(city) || this.verifyInput.isInvalidCity(district);
    }

    private void addListeners() {
        this.theCMSView.addDeleteListener(new deleteListener());
        this.theCMSView.addClearSearchListener(new clearSearchListener());
        this.theCMSView.addNewClientListener(new addClientListener());
        this.theCMSView.addResultListListener(new resultDetailsListener());
        this.theCMSView.addSaveListener(new saveListener());
        this.theCMSView.addSearchListener(new searchListener());
        this.theInsertView.addInsertListener(new insertListener());
        this.theInsertView.addCancelListener(new cancelListener());
    }

    private class deleteListener implements ActionListener {
        private deleteListener() {
        }

        public void actionPerformed(ActionEvent e) {
            if (CMController.this.theCMSView.getSelectedClient() == null) {
                CMController.this.theCMSView.errorMessage(CMController.this.theCMSView, "No Client Selected.");
            } else {
                int input = JOptionPane.showConfirmDialog((Component)null, "Do you want to delete the selected client?", "CONFIRMATION REQUIRED", 1, 0);
                if (input == 0) {
                    int idToDelete = CMController.this.theCMSView.getSelectedClient().getId();
                    CMController.this.theModel.deleteClient(idToDelete);
                    CMController.this.theCMSView.successMessage(CMController.this.theInsertView, "Successfully deleted Client (ID: " + idToDelete + ")");
                    this.removeResult();
                    CMController.this.theCMSView.clearClientDetails();
                }

            }
        }

        private void removeResult() {
            ArrayList<Client> newResults = CMController.this.theCMSView.getCurrResults();
            newResults.remove(CMController.this.theCMSView.getSelectedClient());
            CMController.this.theCMSView.refreshResults(newResults);
        }
    }

    private class clearSearchListener implements ActionListener {
        private clearSearchListener() {
        }

        public void actionPerformed(ActionEvent e) {
            CMController.this.theCMSView.clearSearch();
        }
    }

    private class addClientListener implements ActionListener {
        private addClientListener() {
        }

        public void actionPerformed(ActionEvent e) {
            CMController.this.theInsertView.clearFields();
            CMController.this.theInsertView.run();
        }
    }

    private class resultDetailsListener implements ListSelectionListener {
        private resultDetailsListener() {
        }

        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Client selected = CMController.this.theCMSView.getSelectedClient();
                CMController.this.theCMSView.populateClientDetails(selected);
            }

        }
    }

    private class saveListener implements ActionListener {
        private saveListener() {
        }

        public void actionPerformed(ActionEvent e) {
            if (CMController.this.theCMSView.getSelectedClient() == null) {
                CMController.this.theCMSView.errorMessage(CMController.this.theCMSView, "No Client Selected.");
            } else {
                String newMail = CMController.this.theCMSView.getMailField();
                String newPassword = CMController.this.theCMSView.getPasswordField();
                String newAddress = CMController.this.theCMSView.getAddressTextArea();
                String newCity = CMController.this.theCMSView.getCityField();
                String newDistrict = CMController.this.theCMSView.getDistrictField();
                //String newClientType = CMController.this.theCMSView.getClientTypeCombo();
                if (!CMController.this.invalidClient(newMail, newPassword, newAddress, newDistrict, newCity)) {
                    Client clientToUpdate = CMController.this.theCMSView.getSelectedClient();
                    clientToUpdate.updateInfo(newMail, newPassword, newAddress, newCity, newDistrict);
                    CMController.this.theModel.updateClient(clientToUpdate.getId(), clientToUpdate);
                    CMController.this.theCMSView.successMessage(CMController.this.theInsertView, "Successfully updated Client (ID: " + clientToUpdate.getId() + ")");
                }
            }
        }
    }

    private class searchListener implements ActionListener {
        private searchListener() {
        }

        public void actionPerformed(ActionEvent e) {
            String searchCriteria = CMController.this.theCMSView.getSearchCriteria();
            String query = CMController.this.theCMSView.getSearchQuery();
            CMController.this.searchDB(searchCriteria, query);
        }
    }

    private class insertListener implements ActionListener {
        private insertListener() {
        }

        public void actionPerformed(ActionEvent e) {
            String mail = CMController.this.theInsertView.getMailField();
            String password = CMController.this.theInsertView.getPasswordField();
            String address = CMController.this.theInsertView.getAddressField();
            String city = CMController.this.theInsertView.getCity();
            String district = CMController.this.theInsertView.getDistrict();
            if (CMController.this.invalidClient(mail, password, address, district, city)) {
                CMController.this.theInsertView.run();
            } else {
                Client clientToAdd = new Client(mail, password, address, city, district);
                CMController.this.theModel.addClient(clientToAdd);
                CMController.this.theCMSView.successMessage(CMController.this.theInsertView, "Client added to the Database successfully!");
                CMController.this.theInsertView.dispose();
            }
        }
    }

    private class cancelListener implements ActionListener {
        private cancelListener() {
        }

        public void actionPerformed(ActionEvent e) {
            CMController.this.theInsertView.dispose();
        }
    }
}
