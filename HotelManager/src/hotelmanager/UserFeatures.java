/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import GUI.*;
import java.awt.Dialog;
import javax.swing.JOptionPane;

/**
 *
 * @author PikeMobile
 */
public class UserFeatures {
    CustomerMainWindow main = null;
    
    public UserFeatures()
    {
        
    }
    
    public void getUserTable()
    {
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
    }
    
    /**
     * Check in the desired User to the selected Room.
     * @param user 
     * @param Room
     */
    public boolean checkIn(UserInformation user/*, Room*/)
    {
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
        return false;
    }
    
    /**
     * Check out the desired User from the selected Room.
     * @param user 
     * @param Room
     */
    public boolean checkOut(UserInformation user/*, Room*/)
    {
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
        return false;
    }
    
    /**
     * Set a room's price
     * @param Room
     * @param price
     */
    public void setPrice(/*Room, int price*/)
    {
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
    }
    
    /**
     * Add a discount to a customer's price.
     * 
     */
    public void addDiscount()
    {
        JOptionPane.showMessageDialog(null, "This feature is for Admin use only");
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
    
    /**
     * Produce Main Application Window
     * @param user 
     */
    public void execute(UserInformation user)
    {
        try{
        main = new CustomerMainWindow(user);
        main.setVisible(true);
        main.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        }
        catch(NullPointerException e)
        {
            
        }
    }
    
    /**
     * Dispose of Main Application Window
     */
    public void dispose()
    {
        try{
        main.dispose();
        }
        catch(NullPointerException e)
        {
            
        }
    }
    
    /**
     * Produce a dialog to create a new User Account.
     */
    public void createUser()
    {
        //CreateUser createUserForm = new CreateUser(null, true);
        //createUserForm.setVisible(true);
    }
}
