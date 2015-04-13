/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;
import Resources.*;
import GUI.UserLogin;
/**
 *
 * @author Troy Gilmore
 */
public class UserInformation {
    enum AccountType {Customer, Employee, Admin};
    protected String firstName = null;
    protected String lastName = null;
    protected String accountType = null;
    //private String username = null;
    protected String password = null;
    protected String emailAddress = null;
    protected boolean loggedin;
    protected UserFeatures feature = null;
    protected double AccountBalance = 0.0;
    
    /**
     * Default Contructor
     * 
     * @param FirstName
     * @param LastName
     * @param Password
     * @param EmailAddress
     * @param AccountType 
     */
    public UserInformation()
    {
        
    }
    
    /**
     * UserInformation Constuctor.
     * @param FirstName
     * @param LastName
     * @param Password
     * @param EmailAddress
     * @param AccountType 
     */
    public UserInformation(String FirstName, String LastName, 
            String Password, String EmailAddress, String AccountType)
    {
        this.SetFirstName(FirstName);
        this.SetLastName(LastName);
        //this.SetUsername(Username); 
        this.SetPassword(Password); 
        this.SetEmailAddress(EmailAddress);
        this.SetAccountType(AccountType);
        
        if(AccountType.equals("Customer"))
        {
            feature = new CustomerUser();
        }
        else if(AccountType.equals("Admin"))
        {
            feature = new AdminUser();
        }
        else if(AccountType.equals("Employee"))
        {
            feature = new EmployeeUser();
        }
        else
        {
            feature = new GuestUser();
        }
        
        loggedin = false;
    }
    
    /**
     * Updates the User's password.
     * This checks attempts to update the password in the database.
     * if fails it restores old password and returns false.
     * @param Field String
     * @return boolean
     */
    public boolean updatePassword(String Field)
    {
        String temp = password;
        password = Field;
        SQLiteJDBC database = SQLiteJDBC.getInstance();
        if(!database.updatePassword(this)) // faild to update database
        {
            //set the password back to previous
            password = temp;
            return false;
        }
        
        return true;
    }
    
    public boolean isLoggedIn()
    {
        return loggedin;
    }
    
    public void logout()
    {
        feature.dispose();
        UserLogin that = UserLogin.getInstance();
        that.setVisible(true);
        loggedin = false;
    }
    
    public boolean login(String name, String Password)
    {
        SQLiteJDBC database = SQLiteJDBC.getInstance();
        //System.out.println("Database password: " + database.getPassword(this));
        if(emailAddress.equals(name) && Password.equals(database.getPassword(this))/*password.equals(Password)*/)
        {
            feature.execute(this);
            return loggedin = true;
        }
        return loggedin = false;
    }
    
    public void SetFirstName(String FirstName)
    {
        firstName = FirstName;
    }
    
    public void SetLastName(String LastName)
    {
        lastName = LastName;
    }
    
    public void SetAccountType(String AccountType)
    {
        accountType = AccountType;
    }
    
    /*
    public void SetUsername(String Username)
    {
        username = Username;
    }
    */
    
    public void SetPassword(String Password)
    {
        password = Password;
    }
    
    public void SetEmailAddress(String EmailAddress)
    {
        emailAddress = EmailAddress;
    }
    
    public String GetFirstName()
    {
        return firstName;
    }
    
    public String GetLastName()
    {
        return lastName;
    }
    
    public String GetAccountType()
    {
        return accountType;
    }
    
    /*
    public String GetUsername()
    {
        return username;
    }
    */
    
    public String GetPassword()
    {
        return password;
    }
    
    public String GetEmailAddress()
    {
        return emailAddress;
    }
    
    public void UserTable()
    {
        feature.getUserTable();
    }
    
    public double accountBalance()
    {
        //do some calculations here
        return AccountBalance;
    }
    
    /**
     * Call the user's feature of createRoom()
     */
    public void createRoom()
    {
        feature.createRoom();
    }
    
    /**
     * Update and existing room's features
     * @param room 
     */
    public void updateRoom(Room room)
    {
        feature.updateRoom(room);
    }
    
    /**
     * Create a new User Account
     */
    public void createUser()
    {
        feature.createUser();
    }
    
    public boolean checkIn(UserInformation temp)
    {
        return feature.checkIn(temp);
    }
    
    public boolean checkOut(UserInformation temp)
    {
        return feature.checkOut(temp);
    }
}
