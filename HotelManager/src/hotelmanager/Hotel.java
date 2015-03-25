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
    ArrayList<Room> rooms = new ArrayList<Room>();//hols are the rooms
    ArrayList<UserBase> users = new ArrayList<UserBase>();//holds all users 
    private UserBase current = null; // the user currently logged in to the system
    public Hotel()
    {
        
    }
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
    public String AddUser(UserBase temp)//add a user to the system
    {
        if(userExist(temp.getGUID())==-1)//checks if the user alreay exists
        {
            users.add(temp);
            return "User has been added";
        }
        return "Unable to add user. Username already taken.";
    }
    public String changeUserPassword(String oldPass, String newPass, UserBase user)//change the password of the user
    {
        return user.changePassword(oldPass, newPass, current);
    }
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
     public boolean logOff()
    {
        boolean result = false;
        current = null; 
        if(current == null)
            result = true;
        return result;
    }
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
    public String currentUser()// displays info about who is logged in
    {
        if(current!=null)
            return current.toString();
        else return "You have not logged in";
    }
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
    public ArrayList<Reserve> findAllUserReservations(String guid)//return a list of all the reservations for a given user
    {
        ArrayList<Reserve> matches = new ArrayList<Reserve>();
        for(Room room : rooms)
        {
            matches.addAll(room.getReservationsWithUser(guid));
        }
        return matches;
    }
    public ArrayList<Reserve> reservationsForCurrentUser()//returns all the reservations fot the user logged in
    {
        if(current != null)
            return findAllUserReservations(current.getGUID());
        else return null; 
    }
    //public String deleteUserReservation()
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
