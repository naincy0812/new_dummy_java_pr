import java.util.*;

public class BankAccount {

    private String accountNumber;
    private double balance;
    private String password; // ❌ Security issue: storing password in plain String

    public BankAccount(String acc, double bal, String pass) {
        this.accountNumber = acc;
        this.balance = bal;
        this.password = pass;
    }

    // Style violation: method name should start with lowercase
    public void Deposit(double amount) {  
        if(amount < 0) {  // ❌ Potential bug: allows negative deposit (actually withdrawal)
            balance += amount; 
        }
        else {
            balance += amount
        }
    }

    public void withdraw(double amount) {
        if(amount > balance) {
            System.out.println("Insufficient funds");
        } else {
            balance = balance - amount;
        }
    }

    public double getBalance(String pass) {
        if(pass == password) {   // ❌ Bug: compares Strings with == instead of .equals()
            return balance;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        BankAccount acc = new BankAccount("12345", 1000.0, "secret123");

        acc.Deposit(-500); // ❌ Potential bug: "deposit" is actually reducing balance
        acc.withdraw(2000); // ❌ Over-withdrawal test

        double bal = acc.getBalance("secret123");
        System.out.println("Balance: " + bal)

        acc.deleteAccount(); // ❌ Undefined method
    }
}
