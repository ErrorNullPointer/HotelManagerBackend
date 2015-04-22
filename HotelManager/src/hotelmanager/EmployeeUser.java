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
public class EmployeeUser extends UserFeatures{
    
    private EmployeeMainWindow main = null;
    /**
     * Check in the desired User to the selected Room.
     * @param user 
     * @param Room
     */
    public boolean checkIn(UserInformation user/*, Room*/)
    {
        return false;
    }
    
    /**
     * Check out the desired User from the selected Room.
     * @param user 
     * @param Room
     */
    public boolean checkOut(UserInformation user/*, Room*/)
    {
        return false;
    }
    
    /**
     * Produce a form to Create a new room.
     */
    public void createRoom()
    {
        RoomOptions roomCreator = new RoomOptions(null, true);
        roomCreator.setVisible(true);
    }
    
    /**
     * Update a current Room's features.
     * @param room 
     */
    public void updateRoom(Room room)
    {
        RoomOptions roomCreator = new RoomOptions(null, true, room);
        roomCreator.setVisible(true);
    }
    
     /**
     * Produce a dialog to create a new User Account.
     */
    @Override
    public void createUser()
    {
        CreateUser createUserForm = new CreateUser(null, true);
        createUserForm.lockAccountType();
        createUserForm.setVisible(true);
    }
    
    @Override
    /**
     * Produce Main Application Window
     * @param user 
     */
    public void execute(UserInformation user)
    {
        try{
        main = new EmployeeMainWindow(user);
        main.setVisible(true);
        main.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE);
        }
        catch(NullPointerException e)
        {
            
        }
    }
    
    @Override
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
}
