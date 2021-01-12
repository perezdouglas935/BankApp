package maven.Testing.Bank_App;

class Invalid_Amount_Exception extends Exception {

    public Invalid_Amount_Exception() {
        super("Invalid transaction amount.");
    }
    
}
