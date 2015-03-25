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
public class Hotel implements java.io.Serializable{
    ArrayList<Room> rooms = new ArrayList<Room>();
    ArrayList<UserBase> users = new ArrayList<UserBase>();
    private UserBase current = null; 
    public Hotel()
    {
        
    }
    public String AddRoom(Room temp)
    {
        String result = "You do not have rights for this";
        if(current != null && current.getType().equals("Admin"))
        {
            if(roomExist(temp.getRoom())==-1 )
            {
                rooms.add(temp);
                result = "Room has been added";

            }
            else 
                result = "Room already exists";
        }
        
        return result;
    }
    public String setRoomPrice(int room, double price)
    {
        String result = "Unable to locate room requested"; 
        if(current != null && current.getType().equals("Admin"))
        {
            for(int x = 0; x < rooms.size(); x++)
            {
                if(rooms.get(x).getRoomInt() == room)
                {
                    price = Math.round(price * 100.0) / 100.0;
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
    public String AddUser(UserBase temp)
    {
        if(userExist(temp.getGUID())==-1)
        {
            users.add(temp);
            return "User has been added";
        }
        return "Unable to add user. Username already taken.";
    }
    public String changeUserPassword(String oldPass, String newPass, UserBase user)
    {
        return user.changePassword(oldPass, newPass, current);
    }
    public boolean logIn(String user, String password)// goes user to user chacking for correct match, if match not found return false
    {
        boolean result = false;
        logOff(); 
        for(UserBase temp : users)
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
    private int userExist(String guid)
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
    private int roomExist(String number)
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
    public String deleteUser(String guid)
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
    public String deleteRoom(String number)
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
    public String currentUser()
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
    public ArrayList<Room> searchAvailableRooms(int search, Reserve request)
    {
        ArrayList<Room> matches = new ArrayList<Room>();
        for(Room temp : searchRooms(search))
        {
            if(temp.checkReservations(request) && request != null)
            {
                matches.add(temp);
            }
            else 
                matches.add(temp);
            
        }
        return matches;
    }
    public String saveState()
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
    public static Hotel loadState()
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
    public String makeReservation(Date s, Date e, int room, boolean paid)
    {
        String result = "Unable to make reservation";
        if(current != null)
        {
            for(int x = 0; x < rooms.size(); x++)
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
