/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;
import GUI.*;
import javax.swing.JOptionPane;

/**
 *
 * @author PikeMobile
 */
public class GuestUser extends UserFeatures{
    
    public void getUserTable()
    {
        
    }
    
    /**
     * Set a room's price
     * @param Room
     * @param price
     */
    public void setPrice(/*Room, int price*/)
    {
        
    }
    
    /**
     * Add a discount to a customer's price.
     * 
     */
    public void addDiscount()
    {
        
    }
    
    /**
     * Produce a form to Create a new room.
     */
    public void createRoom()
    {
        //RoomOptions roomCreator = new RoomOptions(null, true);
        //roomCreator.setVisible(true);
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
    }
    
    /**
     * Update a current Room's features.
     * @param room 
     */
    public void updateRoom(Room room)
    {
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
    }
}
