package maven.Testing.Bank_App;

import java.io.Serializable;

public class Customer_Info  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7251065726098126746L;
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
                "First Name: " + getFirst_Name() + "\n" + 
                "Last Name: " + getLast_Name() +  "\n" + 
                "SSN: " + getSsn() +  "\n" + 
                account;
    }
    
    public String basic_Info(){
        return " Account Number: " + account.getaccount_Number() + " - Name: " + getFirst_Name() + " " + getLast_Name();
    }
    
    Account getAccount(){
        return account;
    }

    public String getFirst_Name() {
        return first_Name;
    }

    public String getLast_Name() {
        return last_Name;
    }

    public String getSsn() {
        return ssn;
    }
    
}