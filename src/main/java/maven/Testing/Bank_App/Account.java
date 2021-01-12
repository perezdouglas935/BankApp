package maven.Testing.Bank_App;

import java.io.Serializable;

public abstract class Account  implements Serializable{
    private double balance = 0;
    private int account_Number;
    
    Account(int account_Number){
        this.account_Number = account_Number;
    }
      
    public abstract Account_Type getAccount_Type();
    
    @Override
    public String toString(){
        return "Account Type: " + getAccount_Type().name() + " Account\n" +
                "Account Number: " + this.getaccount_Number() + "\n" +
                "Balance: " + this.getBalance() + "\n";
    }
    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public final void Set_Balance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the account_Number
     */
    public int getaccount_Number() {
        return account_Number;
    }
}
