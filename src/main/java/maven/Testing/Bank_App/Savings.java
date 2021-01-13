package maven.Testing.Bank_App;

public class Savings extends Account{
    
    Savings(int account_Number, double initial_Deposit){
        super(account_Number);
        this.Set_Balance(initial_Deposit);
    }
   
    @Override
    public Account_Type getAccount_Type() {
        return Account_Type.Savings;
    }
}
