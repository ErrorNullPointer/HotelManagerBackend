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
public class AdminUser extends UserBase {
    private AdminUser(String name, String guid, String password)
    {
        setName(name);
        setGUID(guid);
        setPassword(password); 
        setType("Admin");
    }
    /**
    * Generate a new administrator user.
    * 
    * @param  name Name of the user.
    * @param  guid Username of the user
    * @param  password Administrator password.
    * @return A new instance of an admin user
    */
    public static AdminUser makeUser(String name, String guid, String password)
    {
        AdminUser admin = new AdminUser(name,guid,password);
        return admin;
    }
     
    
    
}
