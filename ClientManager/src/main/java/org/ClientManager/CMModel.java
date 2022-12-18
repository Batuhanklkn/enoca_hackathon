package org.ClientManager;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class CMModel {
    public Connection jdbc_connection;
    public PreparedStatement pStatement;
    public String customerTable = "Client";
    public String databaseName = "consumerdb";
    public String connectionInfo;
    public String login;
    public String password;
    public String dataFile;

    public CMModel() {
        this.connectionInfo = "jdbc:mysql://localhost/" + this.databaseName + "?verifyServerCertificate=false&useSSL=true";
        this.login = "root";
        this.password = "1234";
        this.dataFile = "clients.txt";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.jdbc_connection = DriverManager.getConnection(this.connectionInfo, this.login, this.password);
            this.createTable();
            this.fillTable();
        } catch (SQLException var2) {
            var2.printStackTrace();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public ArrayList<Client> getSearchResults(String searchCriteria, String searchQuery) {
        String sql = "SELECT * FROM " + this.customerTable + " WHERE " + searchCriteria + "=?";
        ArrayList<Client> searchResults = new ArrayList();

        try {
            this.pStatement = this.jdbc_connection.prepareStatement(sql);
            this.pStatement.setString(1, searchQuery);
            ResultSet rs = this.pStatement.executeQuery();

            while(rs.next()) {
                Client toAdd = new Client(rs.getString("mail"), rs.getString("password"), rs.getString("address"), rs.getString("city"), rs.getString("district"));
                toAdd.setId(rs.getInt("id"));
                searchResults.add(toAdd);
            }

            rs.close();
        } catch (SQLException var7) {
            var7.printStackTrace();
        }

        return searchResults;
    }

    public void addClient(Client client) {
        String sql = "INSERT INTO " + this.customerTable + " VALUES (null, ?, ?, ?, ?, ?);";
        try {
            this.pStatement = this.jdbc_connection.prepareStatement(sql, 1);
            this.pStatement.setString(1, client.getMail());
            this.pStatement.setString(2, client.getPassword());
            this.pStatement.setString(3, client.getAddress());
            this.pStatement.setString(4, client.getCity());
            this.pStatement.setString(5, client.getDistrict());
            this.pStatement.executeUpdate();
            ResultSet generatedID = this.pStatement.getGeneratedKeys();
            if (generatedID.next()) {
                client.setId(generatedID.getInt(1));
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    public void updateClient(int id, Client client) {
        String sql = "UPDATE " + this.customerTable + " SET mail = ?, password = ?, address = ?, city = ?, district = ? WHERE id = ?;";

        try {
            this.pStatement = this.jdbc_connection.prepareStatement(sql);
            this.pStatement.setString(1, client.getMail());
            this.pStatement.setString(2, client.getPassword());
            this.pStatement.setString(3, client.getAddress());
            this.pStatement.setString(4, client.getCity());
            this.pStatement.setString(5, client.getDistrict());
            this.pStatement.setInt(6, id);
            this.pStatement.executeUpdate();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }

    }

    public void deleteClient(int id) {
        String sql = "DELETE FROM " + this.customerTable + " WHERE id = ?;";

        try {
            this.pStatement = this.jdbc_connection.prepareStatement(sql);
            this.pStatement.setInt(1, id);
            this.pStatement.executeUpdate();
        } catch (SQLException var4) {
            var4.printStackTrace();
        }

    }

    private void createTable() {
        //############THIS PART OF THE CODE IS THE PLACE WHERE IM TOTALLY DONE WITH 2 RELEATED DB TABLES. SO CHANGING THEM INTO ONE;
        //Need to learn more about sql and database management. And database design fundamentals.
        String sql = "CREATE TABLE IF NOT EXISTS " + this.customerTable + " (id INT AUTO_INCREMENT, mail VARCHAR(60) NOT NULL UNIQUE, password VARCHAR(60) NOT NULL," +
        " address VARCHAR(60), city VARCHAR(60), district VARCHAR(60), PRIMARY KEY (id))";
        try {
            this.pStatement = this.jdbc_connection.prepareStatement(sql);
            this.pStatement.executeUpdate();
        } catch (SQLException var3) {
            var3.printStackTrace();
        }

    }

    private void fillTable() {
        //a table created randomly via vim text-editor help. i love vim, because it's powerfull and gives hacker vibes :)
        try {
            this.pStatement = this.jdbc_connection.prepareStatement("SELECT * FROM " + this.customerTable);
            ResultSet rs = this.pStatement.executeQuery();
            if (rs.next()) {
                return;
            }

            Scanner sc = new Scanner(new FileReader(this.dataFile));

            while(sc.hasNext()) {
                String[] clientInfo = sc.nextLine().split(";");
                this.addClient(new Client(clientInfo[0], clientInfo[1], clientInfo[2], clientInfo[3], clientInfo[4]));
            }

            sc.close();
        } catch (FileNotFoundException var4) {
            System.err.println("File " + this.dataFile + " Not Found!");
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
