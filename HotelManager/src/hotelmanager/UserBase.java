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
    private String Name;
    private String GUID;
    private String Password;
    private String Type;
    private UserBase(String name, String guid, String password)// this is priviate so only can only make a use when using this class
    {
        Name = name;
        GUID = guid;
        Password = password; 
        Type = "normal";
    }
    public UserBase() // this is so AdminUser Has a public constructor to inherrit
    {
    }
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
    public void setName(String name)
    {
        Name = name;
    }
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
    public void setGUID(String guid)
    {
        GUID = guid;
    }
    public void setPassword(String password)
    {
        Password = password;
    }
    public void setType(String type)
    {
        Type = type;
    }
    public static UserBase makeUser(String name, String guid, String password)
    {
        UserBase temp = new UserBase(name, guid, password);
        return temp; 
    }
    public UserBase returnUser()
    {
        UserBase temp = new UserBase(this.Name, this.Type);
        return temp; 
    }
    public boolean logIn(String guid, String password)
    {
        if(guid.equals(GUID))
            if(password.equals(Password))
                return true;
        return false; 
    }
    public String toString()
    {
        if(Name != null)
            return  "\n\t(Name: " + Name + " GUID: " + GUID + " Password: " + Password + " Type: " + Type+ ")";     
        else
            return  "\n\t(" + " GUID: " + " Type: " + Type+ ")"; 
    }
    public String getGUID()
    {
        return GUID; 
    }
    public static String checkPermissions(UserBase current)
    {
        return current.Type;
    }
    
}
