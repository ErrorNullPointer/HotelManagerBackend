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
public class EmployeeUser extends UserFeatures{
    

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
}
