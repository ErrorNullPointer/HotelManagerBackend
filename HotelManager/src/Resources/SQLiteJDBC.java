package Resources;

import java.awt.Dialog;
import java.sql.*;
import java.util.*;
import hotelmanager.UserInformation;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 * SQLiteJDBC is a class to handle data requests.
 * @author PikeMobile
 */
public class SQLiteJDBC
{
    private static Connection c = null;
    private static Statement stmt;
    private static SQLiteJDBC instance = new SQLiteJDBC(); 
    //private static ResultSet rs;
    
    /**
     * Get the instance of the SQL database.
     * @return 
     */
    public static SQLiteJDBC getInstance()
    {
        if(instance.equals(null))
        {
            instance = new SQLiteJDBC();
        }
        return instance;
    }
    
    /**
     * Create a SQLite Table for User.
     * Fields are:
     * Last Name, First Name, Password, Email address, Account Type.
     */
    public static void CreateUsersTable()
    {
        try{
         stmt = c.createStatement();
     /* String sql = "CREATE TABLE COMPANY " +
                   "(ID INT PRIMARY KEY     NOT NULL," +
                   " NAME           TEXT    NOT NULL, " + 
                   " AGE            INT     NOT NULL, " + 
                   " ADDRESS        CHAR(50), " + 
                   " SALARY         REAL)"; */
      String sql = "CREATE TABLE IF NOT EXISTS USERS " + 
              " (LASTNAME  TEXT NOT NULL, " + 
              "FIRSTNAME  TEXT    NOT NULL, " + 
              /*" USERNAME PRIMARY KEY NOT NULL, " +*/
              "PASSWORD TEXT NOT NULL, " +
              " EMAILADDRESS TEXT UNIQUE NOT NULL, " + 
              " ACCOUNTTYPE TEXT NOT NULL)";
      
      stmt.executeUpdate(sql);
      //sql = "CREATE TABLE ";
      
      //stmt.close();
      } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    }
    
    /**
     * Insert Data into the Data base.
     * @param TableName
     * @param user 
     * @return boolean
     */
    public boolean insert(String TableName,  UserInformation user)
    {
      try
      {
          
          stmt = c.createStatement();
          if(TableName.equals("USERS"))
          {
              String sql = "INSERT INTO USERS (LASTNAME,FIRSTNAME,PASSWORD,EMAILADDRESS,ACCOUNTTYPE) "
                      + "VALUES ("
                      + "'" + user.GetLastName() + "', "
                      + "'" + user.GetFirstName() + "', "
                      /*+ "'" + user.GetUsername()  + "', "*/
                      + "'" + user.GetPassword()  + "', "
                      + "'" + user.GetEmailAddress()  + "', "
                      + "'" + user.GetAccountType()  + "'); ";
              
              //System.out.println(sql);
          stmt.executeUpdate(sql);
          }
          return true;
          /*
          String sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                       "VALUES (1, 'Paul', 32, 'California', 20000.00 );"; 
          stmt.executeUpdate(sql);

          sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );"; 
          stmt.executeUpdate(sql);

          sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );"; 
          stmt.executeUpdate(sql);

          sql = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );"; 
          stmt.executeUpdate(sql);
          */
          //stmt.close();
          //c.commit();
      } 
      catch(SQLException e)
        {
            //System.out.println("\nError: Username Already Exists");
            return false;
        }
      catch ( Exception e ) 
      {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return false;
        //System.exit(0);
      }
    }
    
    /**
     * Get a User's information from the database.
     * @param Username
     * @return 
     */
    public UserInformation getUserInformation(String Username)
    {
            
        try
        {
            UserInformation user = null;
            //UserInformation user = new UserInformation();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM USERS WHERE EMAILADDRESS= '"
                    + Username + "';" );
            while ( rs.next() ) 
            {
                String lastName = rs.getString("LASTNAME");
                String  firstName = rs.getString("FIRSTNAME");
                /*String username  = rs.getString("USERNAME");*/
                String password = rs.getString("PASSWORD");
                String  address = rs.getString("EMAILADDRESS");
                String accountType = rs.getString("ACCOUNTTYPE");
//                System.out.println( "LASTNAME: " + lastName );
//                System.out.println( "FIRSTNAME: " + firstName );
//                /*System.out.println( "USERNAME: " + username );*/
//                System.out.println("PASSWORD: " + password);
//                System.out.println( "EMAILADDRESS: " + address );
//                System.out.println( "ACCOUNTTYPE: " + accountType );
//                System.out.println();
                user = new UserInformation(firstName, lastName, /*username,*/ password, address, accountType);
            return user;
            }
            return null;
        }
        catch(Exception e)
        {
            //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }

            //return null;
    }
    
    /**
     * Select all data from data base.
     * @param TableName
     * @return ResultSet
     */
    public  ResultSet SelectFromDataBase(String TableName)
    {
     try
     {
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM " + TableName+ ";" );
       return rs;
       
      } 
      catch ( Exception e ) 
      {
        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        return null;
      }
    }
    
    /**
     * 
     */
    public String getPassword(UserInformation user)
    {
        try
        {
            stmt = c.createStatement();
            String sql = "SELECT * FROM USERS WHERE EMAILADDRESS = '"
                   + user.GetEmailAddress() + "';";
            ResultSet rs = stmt.executeQuery( sql);
            return rs.getString("PASSWORD");
        }
        catch(Exception e)
        {
            return null;
        }
        
    }
    /**
     * Update the Data base row.
     * @param User
     * @return boolean
     */
    public boolean updatePassword(UserInformation user)
    {
      try
      {
          stmt = c.createStatement();
          String sql = "UPDATE USERS SET PASSWORD = '" + user.GetPassword() +
                  "' where EMAILADDRESS = '" + user.GetEmailAddress() + "';";
          stmt.executeUpdate(sql);
          //stmt.close();
          c.commit();
          return true;
      } 
      catch ( SQLException e ) 
      {
          System.out.println("Failed to change user Password.");
          System.out.println(user.GetEmailAddress() + " " + user.GetPassword());
          //return false;
          return true;
        //System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        //System.exit(0);
      }   
    }
    
    /**
     * Close the open data base.
     */
    public void close()
    {
        try
        {
            stmt.close();
            c.close();
            System.out.println("Closing Down Database.");
        } 
        catch ( Exception e ) 
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
    
    /**
     * Delete User from data base based off unique email address.
     * @param Username of user wanting to be deleted
    */
    public void DeleteUser(String Username)
    {
        try
        {
            stmt = c.createStatement();
            stmt.executeUpdate("DELETE from USERS where EMAILADDRESS='" + Username + "';");
            //c.commit();
        }
        catch(SQLException e)
        {
            System.out.println("\nError: Deleting User\n");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        catch (Exception e)
        {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);        
        }
    }
    
    /**
     * Create a DefaultTableModel to pass into jTable objects.
     * @param Table
     * @return DefaultTableModel
     * @throws SQLException 
     */
    public DefaultTableModel buildTableModel(String Table)
        throws SQLException {
        
        ResultSet rs = this.SelectFromDataBase(Table);
    ResultSetMetaData metaData = rs.getMetaData();

    // names of columns
    Vector<String> columnNames = new Vector<String>();
    int columnCount = metaData.getColumnCount();
    for (int column = 1; column <= columnCount; column++) {
        columnNames.add(metaData.getColumnName(column));
    }

    // data of the table
    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
    while (rs.next()) {
        Vector<Object> vector = new Vector<Object>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            vector.add(rs.getObject(columnIndex));
        }
        data.add(vector);
    }

    return new DefaultTableModel(data, columnNames);
}
    
    /**
     * Private Constructor.
     */
    private SQLiteJDBC()
    {
        try{
            System.out.println("Attempting to open Database...");
           Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:Users.sqlite");//Hotel.db
          c.setAutoCommit(true);
          System.out.println("Opened database successfully");
          CreateUsersTable();
        }
        catch ( Exception e ) 
        {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(0);
        }
    }

}