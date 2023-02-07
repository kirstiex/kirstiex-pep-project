package DAO;
import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;


public class AccountDAO{

    //add account
    public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account(username, password) values (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int)pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }         
            } catch(SQLException e){
            System.out.println(e.getMessage());   
        } return null;
    }

    public Account findAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT account_id, username, password FROM account WHERE username = ? AND password = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account accounts = new Account(rs.getInt("account_id"),rs.getString("username"), rs.getString("password"));
                return accounts;
            }        
        } catch(SQLException e){
            System.out.println(e.getMessage());   
    } return null;
}

    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("account_id");
                String user = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new Account(id, user, password);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}   
