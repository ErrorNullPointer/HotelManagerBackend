/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Luc
 */
public class Hotel implements java.io.Serializable{// this allows us to save all our data
    ArrayList<Room> rooms = new ArrayList<Room>();//holds all the rooms
    ArrayList<UserBase> users = new ArrayList<UserBase>();//holds all users 
    private UserBase current = null; // the user currently logged in to the system
    /**
     * Default constructor.
     */
    public Hotel()
    {
        
    }
    /**
     * Add a room to this hotel.
     * @param temp The Room object to add.
     * @return Message indicating if the Room was successfully added.
     */
    public String AddRoom(Room temp)
    {
        String result = "You do not have rights for this";
        if(current != null && current.getType().equals("Admin"))
        {
            if(roomExist(temp.getRoom())==-1 )// if the room does not exist add it
            {
                rooms.add(temp);
                result = "Room has been added";

            }
            else 
                result = "Room already exists";
        }
        
        return result;
    }
    /**
     * Set the price of a room in this hotel.
     * @param room Room number of the room whose price is tho be set.
     * @param price New price of the Room.
     * @return Message indicating if the price was changed successfully.
     */
    public String setRoomPrice(int room, double price)//finds the rooms then sets the price
    {
        String result = "Unable to locate room requested"; 
        if(current != null && current.getType().equals("Admin"))
        {
            for(int x = 0; x < rooms.size(); x++)
            {
                if(rooms.get(x).getRoomInt() == room)
                {
                    price = Math.round(price * 100.0) / 100.0;// rounds the value 2 decimal places
                    rooms.get(x).setPrice(price);
                    result = "Room number " + room + " has been changed to " + price + "";
                    break;
                }
            }
        }
        else 
            result = "You do not have rights for this";
        return result; 
    }
    /**
     * Add a user to the hotel.
     * @param temp User to add.
     * @return Message indicating if the user was added successfully.
     */
    public String AddUser(UserBase temp)//add a user to the system
    {
        if(userExist(temp.getGUID())==-1)//checks if the user alreay exists
        {
            users.add(temp);
            return "User has been added";
        }
        return "Unable to add user. Username already taken.";
    }
    /**
     * Change the password of a user in the hotel.
     * @param oldPass User input to check against the current password.
     * @param newPass New password for the user.
     * @param user User whose password is to be changed.
     * @return Message indicating if the password was changed.
     */
    public String changeUserPassword(String oldPass, String newPass, UserBase user)//change the password of the user
    {
        return user.changePassword(oldPass, newPass, current);
    }
    /**
     * Log in a user to the hotel system.
     * @param user User ID to log in.
     * @param password User input for the password.
     * @return True if the login was successful, false if it was not.
     */
    public boolean logIn(String user, String password)// goes user to user chacking for correct match, if match not found return false
    {
        boolean result = false;
        logOff(); 
        for(UserBase temp : users)//goes though the list of users  to find a match thensets the matching user to the current user
        {
            if(temp.logIn(user, password))
            {
                result = true;
                current = temp; 
                break;
            }
        }
        return result;
    }
    /**
     * Log the user out of the system.
     * @return If the user was successfully logged in.
     */
    public boolean logOff()
    {
        boolean result = false;
        current = null; 
        if(current == null)
            result = true;
        return result;
    }
    /**
     * Generate a string representation of this hotel object.
     * @return A string representation of this hotel object listing all the users and rooms.
     */
    public String toString()
    {
        String allData = "";
        allData += "USERS: \n " + users.toString() + "\n";
        allData += "ROOMS: \n " + rooms.toString() + "\n";

        return allData; 
    }
    private int userExist(String guid)//cchecks if the user exists
    {
        int location = -1;
        for(int x = 0; x<users.size(); x++)
        {
            if(users.get(x).getGUID().equals(guid))
            {
                location = x;
                break;
            }
        }
        return location;
    }
    private int roomExist(String number)// checks if the rooms exists
    {
        int location = -1;
        for(int x = 0; x<rooms.size(); x++)
        {
            if(rooms.get(x).getRoom().equals(number))
            {
                location = x;
                break;
            }
        }
        return location;
    }
    /**
     * Remove a user from this system.
     * @param guid User ID to delete.
     * @return Message indicating if the user was deleted successfully.
     */
    public String deleteUser(String guid)// romves the user
    {
        String result = "You do not have permssion to do this";
        int index = -1;
        if(current != null)
        {
            if(UserBase.checkPermissions(current).equals("Admin"))
            {
                index = userExist(guid);
                if(index > -1)
                {
                    users.remove(index);
                    result = "Delete finished";
                }
                else result = "User not found"; 
            }
        }
        else result = "You have not logged in yet";
        return result;
    }
    /**
     * Remove a user from the system.
     * @param number Room number to delete.
     * @return A message indicating if the room was removed.
     */
    public String deleteRoom(String number)//delete a room
    {
        String result = "You do not have permssion to do this";
        int index = -1;
        if(current != null)
        {
            if(UserBase.checkPermissions(current).equals("Admin"))
            {
                index = roomExist(number);
                if(index > -1)
                {
                    rooms.remove(index);
                    result = "Delete finished";
                }
                else result = "Room not found"; 
            }
        }
        else result = "You have not logged in yet";
        return result;
    }
    /**
     * Return a string representation of the current user.
     * @return A string representation of the current user.
     */
    public String currentUser()// displays info about who is logged in
    {
        if(current!=null)
            return current.toString();
        else return "You have not logged in";
    }
    /**
     * Search the rooms for an avaliable room with the specified features.
     * @param search List of features requested.
     * @return A list of rooms that match the requested features.
     */
    public ArrayList<Room> searchRooms(int search)
    {
        ArrayList<Room> matches = new ArrayList<Room>();
        for(Room temp : rooms)
        {
            if(temp.isMatch(search))
            {
                matches.add(temp);
            }
        }
        return matches;
    }
    /**
     * Look for a room that has the same features and is open in a specified time frame.
     * @param search List of features requested.
     * @param request Reservation that holds the requested time frame.
     * @return List of rooms that match the requested information.
     */
    public ArrayList<Room> searchAvailableRooms(int search, Reserve request)//look for a room that has the same features and is open when you want it
    {
        ArrayList<Room> matches = new ArrayList<Room>();
        for(Room temp : searchRooms(search))
        {
            if(temp.checkReservations(request) && request != null)
            {
                //System.out.println(temp.checkReservations(request));
                matches.add(temp);
            }
            else if (temp.checkReservations(request))
                matches.add(temp);
            
        }
        return matches;
    }
    /**
     * Write the hotel data to a serialized file.
     * @return Message indicating whether writing the data was successful.
     */
    public String saveState()//save the stae of the system
    {
        String result = "";
        try {
            FileOutputStream fileOut = new FileOutputStream("hotelData.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            result = ("Serialized data is saved in hotelData.ser");
        } catch (IOException i) {
            System.out.println(i.getMessage());
        }
        return result;
    }
    /**
     * Create a Hotel from a serialized file.
     * @return A Hotel object generated from a serialized file.
     */
    public static Hotel loadState()//load the state
    {
        Hotel savedHotel = null; 
        try {
            FileInputStream fileIn = new FileInputStream("hotelData.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            savedHotel = (Hotel) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println(i.getMessage());
        } catch (ClassNotFoundException c) {
            System.out.println("Hotel not found");
        }
        return savedHotel;
    }
    /**
     * Find all the reservations for a given user.
     * @param guid User whose reservations are needed.
     * @return All the reservations associated with the provided user ID.
     */
    public ArrayList<Reserve> findAllUserReservations(String guid)//return a list of all the reservations for a given user
    {
        ArrayList<Reserve> matches = new ArrayList<Reserve>();
        for(Room room : rooms)
        {
            matches.addAll(room.getReservationsWithUser(guid));
        }
        return matches;
    }
    /**
     * Get all the reservations for the logged=in user.
     * @return All the reservations associated with the logged in user.
     */
    public ArrayList<Reserve> reservationsForCurrentUser()//returns all the reservations fot the user logged in
    {
        if(current != null)
            return findAllUserReservations(current.getGUID());
        else return null; 
    }
    //public String deleteUserReservation()
    /**
     * Delete a specified reservation.
     * @param reservatinId ID of the reservation to delete.
     * @return Message indicating if the reservation was deleted.
     */
    public String deleteReservation(String reservatinId)//delete a reservation
    {
        String result = "Item Not found";
        ArrayList<Reserve> userReservations = findAllUserReservations(current.getGUID());
        for(int x = 0; x < userReservations.size(); x++)
        {
            if(userReservations.get(x).getReserveID().equals(reservatinId))
            {
                userReservations.remove(x);
                result = "Item deleted";
            }
        }
        return result;
        
    }
    /**
     * Delete reservation as an administrator.
     * @param guid ID of the user who holds the reservation to delete.
     * @param reservatinId ID of the reservation to delete.
     * @return Message indicating if the reservation was deleted.
     */
    public String adminDeleteReservation(String guid, String reservatinId)//allos you to delete a reservetion that is not attached to your guid
    {
        String result = "Item Not found";
        ArrayList<Reserve> userReservations = findAllUserReservations(guid);
        for(int x = 0; x < userReservations.size(); x++)
        {
            if(userReservations.get(x).getReserveID().equals(reservatinId))
            {
                userReservations.remove(x);
                result = "Item deleted";
            }
        }
        return result;
        
    }
    /**
     * Check a user in to a reservation.
     * @param guid User to check in.
     * @param reservatinId Reservation ID to check in to.
     * @return Message indicating if the check in was successful.
     */
    public String checkInReservation(String guid, String reservatinId)//check a user in
    {
        String result = "Item Not found";
        if(current.getType().equals("Admin"))
        {
            ArrayList<Reserve> userReservations = findAllUserReservations(guid);
            for(int x = 0; x < userReservations.size(); x++)
            {
                if(userReservations.get(x).getReserveID().equals(reservatinId))
                {
                    userReservations.get(x).checkIn();
                    result = " User has been checked in";
                }
            }
        }
        else 
                result = " You do not have permisson for this action.";
        return result;
        
    }
    /**
     * Attempt to pay for a reservation.
     * @param guid ID of the user being paid for.
     * @param reservatinId ID of the reservation being payed for.
     * @return String indicating if the payment attempt was successful.
     */
    public String payReservation(String guid, String reservatinId)//pay for a reservation
    {
        String result = "Item Not found";
        ArrayList<Reserve> userReservations = findAllUserReservations(guid);
            for(int x = 0; x < userReservations.size(); x++)
            {
                if(userReservations.get(x).getReserveID().equals(reservatinId))
                {
                    userReservations.get(x).payForRoom();
                    result = " User has been checked in";
                }
            }
        
       
        return result;
        
    }
    /**
     * Check a user out of a reservation
     * @param guid ID of the user to check out.
     * @param reservationId ID of the reservation to end.
     * @return String indicating if the checkout attempt was successful.
     */
    public String checkOutReservation(String guid, String reservationId)//check out or a reservation
    {
        String result = "Item Not found";
        if(current.getType().equals("Admin"))
        {
            ArrayList<Reserve> userReservations = findAllUserReservations(guid);
            for(int x = 0; x < userReservations.size(); x++)
            {
                if(userReservations.get(x).getReserveID().equals(reservationId))
                {
                    result = userReservations.get(x).checkOut();
                    //for(int y=0; y<rooms.size();y++)
                      //  result += "  :  " +rooms.get(y).removeReservationWithUser(guid,reservationId);
                    break;
                }
            }
        }
        else 
                result = " You do not have permisson for this action.";
        return result;
        
    }
    /**
     * Make a reservation with the specified information.
     * @param s Starting date of the reservation.
     * @param e Ending date of the reservation.
     * @param room Room number of the reserved room.
     * @param paid If the reservation was paid for.
     * @return String indicating if the reservation was made.
     */
    public String makeReservation(Date s, Date e, int room, boolean paid)//make a reservation
    {
        String result = "Unable to make reservation";
        if(current != null)
        {
            for(int x = 0; x < rooms.size(); x++)// check each room until you find the one you are looking for, then add a reservation
            {
                if(rooms.get(x).getRoomInt() == room)
                {
                    Reserve temp = Reserve.makeReserve(s, e, current.getGUID(), room, paid);
                    result = rooms.get(x).addReservation(temp);
                    break;
                }
                else
                    result = ": unable to find room";
            }
        }
        else
            result += ": You have not logged in";
        return result;
    }
    
    
}
