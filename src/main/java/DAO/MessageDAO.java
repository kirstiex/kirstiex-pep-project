package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {


    public Message getMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getString("message_text"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message insertMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here. When inserting, you only need to define the departure_city and arrival_city
            //values (two columns total!)
            String sql = "INSERT INTO message(message_id, message_text) values (?,?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1,message.message_id);
            preparedStatement.setString(2,message.message_text);

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getMessage_id(), message.getMessage_text());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){

        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM Message;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getString("message_text"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }


}