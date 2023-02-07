package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    //no-args constructor for creating a new AccountService with a new AccountDAO
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    //Constructor for a AccountService when a AccountDAO is provided
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    
    //add new account to database
    //if username not blank and username does not exist
    //if password at least 4 characters long 
    public Account addAccount(Account account){
        String user = account.getUsername();
        String password = account.getPassword();

        if(user.isBlank() || password.length() < 4){
            return null;
        } 
        else if(accountDAO.getAccountByUsername(user) != null){
            return null;
        } else{
           return accountDAO.insertAccount(account);
    }
}

    public Account findAccount(Account account){
        return accountDAO.findAccount(account);
        

    }
}