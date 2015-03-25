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
    public static AdminUser makeUser(String name, String guid, String password)
    {
        AdminUser admin = new AdminUser(name,guid,password);
        return admin;
    }
     
    
    
}
