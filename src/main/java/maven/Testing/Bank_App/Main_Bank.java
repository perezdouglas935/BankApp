package maven.Testing.Bank_App;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;


public class Main_Bank implements Serializable {

    private Database database = new Database();

    Customer_Info Open_Account(String First_Name, String Last_Name, String SSN, Account_Type type, Double balance) {
        int accountId = database.AddAccount(First_Name, Last_Name, SSN, type, balance);
        Customer_Info Customer_Info = database.GetAccount_Type(accountId);
        return Customer_Info;
    }
    
    boolean closeAccount(int accountId) {
        return database.DeleteAccount_Type(accountId);
    }

    Customer_Info getCustomer_Info(int accountId) {
        return database.GetAccount_Type(accountId);
    }

    ArrayList<Customer_Info> getCustomer_Infos() {
        return database.GetAllAccount_Types();
    }

    void withdraw(int accountId, double amount) throws Insufficient_Funds_Exception {
        Customer_Info Customer_Info = getCustomer_Info(accountId);
        double transaction_Fee = gettransaction_Fee(Customer_Info.getAccount().getAccount_Type());
        if (amount + transaction_Fee > Customer_Info.getAccount().getBalance()) {
            throw new Insufficient_Funds_Exception();
        }
        double newBalance = Customer_Info.getAccount().getBalance() - (amount + transaction_Fee);
        database.UpdateAccount_Type(accountId, newBalance);
    }

    void deposit(int accountId, double amount) throws Invalid_Amount_Exception {
        Customer_Info Customer_Info = getCustomer_Info(accountId);
        if (amount <= 0) {
            throw new Invalid_Amount_Exception();
        }
        double interest = checkInterest(Customer_Info.getAccount().getBalance(), amount);
        double amountToDeposit = amount + (amount * interest);
        database.UpdateAccount_Type(accountId, Customer_Info.getAccount().getBalance() + amountToDeposit);
    }

    public double checkInterest(double balance, double amount) {
        double interest = 0;
        if (balance + amount > 10000) {
            interest = 0.05;
        } else {
            interest = 0.02;
        }
        return interest;
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    double gettransaction_Fee(Account_Type Account_Type) {
        double transaction_Fee = 0;
        switch(Account_Type){
            case Checking:
                transaction_Fee = 5;
                break;
            case Savings:
                transaction_Fee = 5;
                break;
            case Undefined:
            default:
                transaction_Fee = 0;
        }
        return transaction_Fee;
    }

}