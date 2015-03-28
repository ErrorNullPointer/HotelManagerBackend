/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

/**
 *
 * @author Luc
 */
public class UserBase implements java.io.Serializable{
    /** Name of the user. */
    private String Name;
    /** Unique username of the user. */
    private String GUID;
    /** Password of the user. */
    private String Password;
    /** Permissions of the user. */
    private String Type;
    private UserBase(String name, String guid, String password)// this is priviate so only can only make a use when using this class
    {
        Name = name;
        GUID = guid;
        Password = password; 
        Type = "normal";
    }
    /**
     * Default constructor.
     */
    public UserBase() // this is so AdminUser Has a public constructor to inherrit
    {
    }
    /**
     * Accessor method for the permissions for this user.
     * @return The permissions level of this user.
     */
    public String getType()
    {
        return Type;
    }
    private UserBase(String guid, String type)//make a small version of a user, only used with current user
    {
        Name = null;
        GUID = guid;
        Password = null; 
        Type = "normal";
    }
    /**
     * Modifier method for the name of this user.
     * @param name New name for this user.
     */
    public void setName(String name)
    {
        Name = name;
    }
    /**
     * Attempts to change the password of the current user.
     * If the user is not an admin and the old password 
     * does not match, then the password is not changed.
     * 
     * 
     * @param oldPassword Original password of the user.
     * @param newPassword Password to change to.
     * @param admin Administrator user.
     * @return Result message to print.
     */
    public String changePassword(String oldPassword, String newPassword, UserBase admin)
    {
        String result = "Password was not changed";
        if (oldPassword.equals(Password) || (admin != null && admin.getType().equals("Admin")))
        {
            Password = newPassword;
            result = "Password Changed";
        }
        else 
            result += ": IncorrectPassword";
        return result; 
    }
    /**
     * Modifier method for the users unique identifier.
     * @param guid New identifier for this user.
     */
    public void setGUID(String guid)
    {
        GUID = guid;
    }
    /**
     * Set the password for this user.
     * @param password Password to change this users password to.
     */
    public void setPassword(String password)
    {
        Password = password;
    }
    /**
     * Sets the permissions for this user.
     * @param type Permissions to change this user to.
     */
    public void setType(String type)
    {
        Type = type;
    }
    /**
    * Generate a new user.
    * 
    * @param  name Name of the user.
    * @param  guid Username of the user
    * @param  password Administrator password.
    * @return A new instance of a user
    */
    public static UserBase makeUser(String name, String guid, String password)
    {
        UserBase temp = new UserBase(name, guid, password);
        return temp; 
    }
    /**
    * Return a user with the shame name as this user.
    * 
    * @return A user with the shame name as this user.
    */
    public UserBase returnUser()
    {
        UserBase temp = new UserBase(this.Name, this.Type);
        return temp; 
    }
    /**
     * Check if given login credentials match this users.
     * 
     * @param guid Unique identifier for the user.
     * @param password Password for the user.
     * @return If the login was successful.
     */
    public boolean logIn(String guid, String password)
    {
        if(guid.equals(GUID))
            if(password.equals(Password))
                return true;
        return false; 
    }
    /**
     * Get a string representation of this user
     * @return String representation of the user.
     */
    public String toString()
    {
        if(Name != null)
            return  "\n\t(Name: " + Name + " GUID: " + GUID + " Password: " + Password + " Type: " + Type+ ")";     
        else
            return  "\n\t(" + " GUID: " + " Type: " + Type+ ")"; 
    }
    /**
     * Access the unique identifier for this user.
     * 
     * @return Identifier for this user.
     */
    public String getGUID()
    {
        return GUID; 
    }
    /**
     * Accessor method for the permissions of the passed user.
     * @param current User whose permissions are to be checked.
     * @return Permissions of the current user.
     */
    public static String checkPermissions(UserBase current)
    {
        return current.Type;
    }
    
}
