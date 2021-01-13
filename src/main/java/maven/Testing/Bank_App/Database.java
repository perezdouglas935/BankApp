package maven.Testing.Bank_App;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {
	String url = "jdbc:mysql://localhost:5432/postgres";
    String user = "user";
    String password = "password";

    private Connection connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            connection = null;
        }
        return connection;
    }

    //Add Account_Type
    int AddAccount(String first_Name, String last_Name, String ssn, Account_Type account_Type, Double balance) {
        int userId = -1;
        int account_Id = -1;
        Connection connection = connect();
        try {
            connection.setAutoCommit(false);
            //Add User
            try (PreparedStatement addUser = connection.prepareStatement("INSERT INTO Users(first_Name, last_Name, ssn) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                addUser.setString(1, first_Name);
                addUser.setString(2, last_Name);
                addUser.setString(3, ssn);
                addUser.executeUpdate();
                ResultSet addUserResults = addUser.getGeneratedKeys();
                if (addUserResults.next()) {
                    userId = addUserResults.getInt(1);
                }
            }
            //Add Account_Type
            try (PreparedStatement addAccount = connection.prepareStatement("INSERT INTO Account_Types(Type, Balance) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
                addAccount.setString(1, account_Type.name());
                addAccount.setDouble(2, balance);
                addAccount.executeUpdate();
                ResultSet addAccount_TypeResults = addAccount.getGeneratedKeys();
                if (addAccount_TypeResults.next()) {
                    account_Id = addAccount_TypeResults.getInt(1);
                }
            }
            //Link User to Account_Type
            if (userId > 0 && account_Id > 0) {
                try (PreparedStatement linkAccount_Type = connection.prepareStatement("INSERT INTO Mappings(UserId, account_Id) VALUES(?,?)")) {
                    linkAccount_Type.setInt(1, userId);
                    linkAccount_Type.setInt(2, account_Id);
                    linkAccount_Type.executeUpdate();
                }
                connection.commit();
            } else {
                connection.rollback();
            }
            //Disconnect
            connection.close();
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return account_Id;
    }
    //Read Details
    Customer_Info GetAccount_Type(int account_Id) {
        Customer_Info customer = null;
        Connection connection = connect();
        try {
            try (PreparedStatement findUser = connection.prepareStatement(
                    "SELECT first_Name,last_Name,ssn,Type,Balance "
                    + "FROM Users a JOIN Mappings b on a.ID = b.UserId "
                    + "JOIN Account_Types c on b.account_Id = c.ID "
                    + "WHERE c.ID = ?")) {
                findUser.setInt(1, account_Id);
                ResultSet findUserResults = findUser.executeQuery();
                if (findUserResults.next()) {
                    String first_Name = findUserResults.getString("first_Name");
                    String last_Name = findUserResults.getString("last_Name");
                    String ssn = findUserResults.getString("ssn");
                    String account_Type = findUserResults.getString("Type");
                    Double balance = findUserResults.getDouble("Balance");
                    Account account = null;
                    if (account_Type.equals(Account_Type.Checking.name())) {
                    	account = new Checking(account_Id, balance);
                    } else {
                        account = new Savings(account_Id, balance);
                    }
                    customer = new Customer_Info(first_Name, last_Name, ssn, account);
                }
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return customer;
    }
    //Update (Edit Account)
    boolean UpdateAccount_Type(int account_Id, Double newBalance){
        boolean success = false;
        Connection connection = connect();
        try {
            try (PreparedStatement updateBalance = connection.prepareStatement(
                    "UPDATE Account_Types SET Balance = ? WHERE ID = ?")) {
                updateBalance.setDouble(1, newBalance);
                updateBalance.setInt(2, account_Id);
                updateBalance.executeUpdate();
            }
            success = true;
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return success;
    }
    //Delete (Remove Account)
    boolean DeleteAccount_Type(int account_Id) {
        boolean success = false;
        Connection connection = connect();
        try {
            try (PreparedStatement deleteRecords = connection.prepareStatement(
                    "DELETE Users,Account_Types FROM Users "
                    + "JOIN Mappings on Users.ID = Mappings.UserId "
                    + "JOIN Account_Types on Account_Types.ID = Mappings.account_Id "
                    + "WHERE Account_Types.ID = ?")) {
                deleteRecords.setInt(1, account_Id);
                deleteRecords.executeUpdate();
            }
            success = true;
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return success;
    }

    ArrayList<Customer_Info> GetAllAccount_Types() {
        ArrayList<Customer_Info> customers = new ArrayList<Customer_Info>();
        Connection connection = connect();
        try {
            try (PreparedStatement findUser = connection.prepareStatement(
                    "SELECT account_Id,first_Name,last_Name,ssn,Type,Balance "
                    + "FROM Users a JOIN Mappings b on a.ID = b.UserId "
                    + "JOIN Account_Types c on b.account_Id = c.ID")) {
                ResultSet findUserResults = findUser.executeQuery();
                while (findUserResults.next()) {
                    String first_Name = findUserResults.getString("First Name");
                    String last_Name = findUserResults.getString("Last Name");
                    String ssn = findUserResults.getString("SSN");
                    String account_Type = findUserResults.getString("Type");
                    Double balance = findUserResults.getDouble("Balance");
                    int account_Id = findUserResults.getInt("AccountID");
                    Account account;
                    if (account_Type.equals(Account_Type.Checking.name())) {
                        account = new Checking(account_Id, balance);
                    } else {
                        account = new Savings(account_Id, balance);
                    }
                    Customer_Info customer = new Customer_Info(first_Name, last_Name, ssn, account);
                    customers.add(customer);
                }
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return customers;
    }
}
