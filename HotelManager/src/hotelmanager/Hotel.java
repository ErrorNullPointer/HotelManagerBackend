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
import Resources.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author Luc
 */
public class Hotel implements java.io.Serializable{// this allows us to save all our data
    //ArrayList<Room> rooms = new ArrayList<Room>();//hols are the rooms
    RoomList rooms = new RoomList();
    ArrayList<UserBase> users = new ArrayList<UserBase>();//holds all users 
    private UserBase current = null; // the user currently logged in to the system
    private ReservationList reserveList = new ReservationList(rooms);
    private static Hotel instance = new Hotel();
    int roomsListLength = 0;
    /**
     * Default constructor
     */
    private Hotel()
    {
        
    }
    
    public static Hotel getInstance()
    {
        Hotel temp = null;//loadState();
        if(temp == null)
        {
            if(instance == null)
            {
                instance = new Hotel();
            }
        }
        else
        {
            instance = temp;
        }
        
        return instance;
    }
    
    public boolean AddRoom(Room temp)
    {
        return rooms.AddRoom(temp);
    }
    
    /**
     * Set Room Price.
     * @param room
     * @param price
     * @return 
     */
    public String setRoomPrice(String room, double price)//finds the rooms then sets the price
    {
        String result = "Unable to locate room requested"; 
        if(current != null && current.getType().equals("Admin"))
        {
            for(int x = 0; x < rooms.size(); x++)
            {
                Room Temp = rooms.getRoom(x);
                if(Temp.getRoomNum().equals(room))
                {
                    price = Math.round(price * 100.0) / 100.0;// rounds the value 2 decimal places
                    Temp.setPrice(price);
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
      * Print Users and rooms to string.
      * @return 
      */
    public String toString()
    {
        String allData = "";
        allData += "USERS: \n " + users.toString() + "\n";
        allData += "ROOMS: \n " + rooms.toString() + "\n";

        return allData; 
    }
    
    public ArrayList getReservations()
    {
     return reserveList.list;   
    }
    /**
     * 
     * @param number
     * @return 
     */
    private int roomExist(String number)// checks if the rooms exists
    {
//        int location = -1;
//        for(int x = 0; x<rooms.size(); x++)
//        {
//            if(rooms.get(x).getRoom().equals(number))
//            {
//                location = x;
//                break;
//            }
//        }
        Iterator room = rooms.it();
        int location = 0;
        while(room.hasNext())
        {
            Room temp = (Room)room.next();
            if(temp.roomNumber.equals(number))
            {
                break;
            }
            location++;
        }
        return location;
    }
    
    /**
     * Delete Room
     * @param number
     * @return 
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
     * Current User name.
     * @return 
     */
    public String currentUser()// displays info about who is logged in
    {
        if(current!=null)
            return current.toString();
        else return "You have not logged in";
    }
    
    /**
     * 
     * @param search
     * @return 
     */
    public ArrayList<Room> searchRooms(int search)
    {
        ArrayList<Room> matches = new ArrayList<Room>();
        Iterator room = rooms.it();
        while(room.hasNext())
        //for(Room temp : rooms)
        {
            Room temp = (Room)room.next();
            if(temp.isMatch(search))
            {
                matches.add(temp);
            }
        }
        return matches;
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList searchAllRooms()
    {
        return rooms.rooms;
    }
    
    /**
     * Search Available Rooms.
     * @param search
     * @param request
     * @return 
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
     * Save State of Hotel.
     * @return 
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
        System.out.println(result);
        return result;
    }
    
    /**
     * Load Saved state
     * @return 
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
        //instance = savedHotel;
        return savedHotel;
    }
    
    /**
     * Find All User Reservations
     * @param guid
     * @return 
     */
    public ArrayList<Reserve> findAllUserReservations(String guid)//return a list of all the reservations for a given user
    {
        ArrayList<Reserve> matches = new ArrayList<Reserve>();
        //for(Room room : rooms)
        Iterator room = rooms.it();
        while(room.hasNext())
        {
            Room next = (Room)room.next();
            matches.addAll(next.getReservationsWithUser(guid));
        }
        return matches;
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    public Room getRoom(int index)
    {
        return rooms.getRoom(index);
    }
    
    /**
     * 
     * @return 
     */
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
    
    /**
     * 
     * @param guid
     * @param reservatinId
     * @return 
     */
    public boolean adminDeleteReservation(String guid, String reservatinId)//allos you to delete a reservetion that is not attached to your guid
    {   
        return reserveList.remove(guid, reservatinId);
    }
    
    /**
     * 
     * @param guid
     * @param reservatinId
     * @return 
     */
    public String checkInReservation(String guid, String reservatinId)//check a user in
    {
        String result = "Item Not found";
        //if(current!= null && current.getType().equals("Admin"))
        //{
            ArrayList<Reserve> userReservations = findAllUserReservations(guid);
            for(int x = 0; x < userReservations.size(); x++)
            {
                if(userReservations.get(x).getReserveID().equals(reservatinId))
                {
                    userReservations.get(x).checkIn();
                    result = " User has been checked in";
                }
            }
        //}
        //else 
        //        result = " You do not have permisson for this action.";
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
        //if(current.getType().equals("Admin"))
        //{
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
        //}
        //else 
        //       result = " You do not have permisson for this action.";
        return result;
        
    }
    public boolean makeReservation(Date s, Date e, String room, boolean paid, String ID)//make a reservation
    {
        boolean result = false;
        //if(current != null)
        //{
            for(int x = 0; x < rooms.size(); x++)// check each room until you find the one you are looking for, then add a reservation
            {
                Room Temp = rooms.getRoom(x);
                if(Temp.getRoomNum().equals(room))
                {
                    Reserve temp = Reserve.makeReserve(s, e, ID, room, paid);
                    if(Temp.addReservation(temp))
                    {
                        reserveList.addReservation(temp);
                        result = true;
                    }
                    break;
                }
                //else false
            }
        //}

        return result;
    }
    
    
}
