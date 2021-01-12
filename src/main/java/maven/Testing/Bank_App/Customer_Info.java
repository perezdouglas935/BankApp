package maven.Testing.Bank_App;

import java.io.Serializable;

public class Customer_Info  implements Serializable{
    private final String first_Name;
    private final String last_Name;
    private final String ssn;
    private final Account account;

    Customer_Info(String first_Name, String last_Name, String ssn, Account account) {
        this.first_Name = first_Name;
        this.last_Name = last_Name;
        this.ssn = ssn;
        this.account = account;
    }
    
    @Override
    public String toString(){
        return "\nCustomer_Info Information\n" +
                "First Name: " + getfirst_Name() + "\n" + 
                "Last Name: " + getlast_Name() +  "\n" + 
                "SSN: " + getSsn() +  "\n" + 
                account;
    }
    
    public String basic_Info(){
        return " Account Number: " + account.getaccount_Number() + " - Name: " + getfirst_Name() + " " + getlast_Name();
    }
    
    Account getAccount(){
        return account;
    }


    public String getfirst_Name() {
        return first_Name;
    }


    public String getlast_Name() {
        return last_Name;
    }

    /**
     * @return the ssn
     */
    public String getSsn() {
        return ssn;
    }
    
}