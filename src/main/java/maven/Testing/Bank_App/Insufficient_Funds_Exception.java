package maven.Testing.Bank_App;

class Insufficient_Funds_Exception extends Exception {

    public Insufficient_Funds_Exception() {
        super("Insufficient funds to complete transaction.");
    }
    
}
