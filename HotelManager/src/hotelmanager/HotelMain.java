/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;
import Resources.SQLiteJDBC;
import hotelmanager.UserInformation;
import GUI.*;
/**
 *
 * @author PikeMobile
 */
public class HotelMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Hotel hotel = Hotel.getInstance();
        //hotel = Hotel.loadState();
        SQLiteJDBC database = SQLiteJDBC.getInstance();
            Room temp = new Room("1",5,250);
            hotel.AddRoom(temp);
            temp = new Room("2",4,250);
            hotel.AddRoom(temp);
            temp = new Room("3",3,250);
            hotel.AddRoom(temp);
        //if(hotel == null)
        //{
            UserInformation admin = new UserInformation("Hotel", "Admin", "", "Admin", "Admin");
            database.insert("USERS", admin);
        //}
        
        UserLogin login = UserLogin.getInstance();
        login.setVisible(true);
        
    }
    
}
