/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import java.util.ArrayList;
import Resources.RoomDescription;
/**
 *
 * @author Luc
 */
public class Room implements java.io.Serializable{
    String roomNumber; 
    private ArrayList<Reserve> reservations = new ArrayList<Reserve>();
    private int reservationCount; 
    private int Features; 
    private double Price; 
    RoomDescription describe = null;
    
    /**
     * 
     * @param number
     * @param features
     * @param price 
     */
    public Room(String number, int features, double price)
    {
        describe = new RoomDescription(features);
        Features = describe.getFeatures(); 
        roomNumber = number;
        Price = price;
        reservationCount = 0; 
        //paid = true;
        //occupied = false; 
    }
    
    /**
     * 
     * @param number
     * @param features
     * @param price
     * @return 
     */
    public static Room makeRoom(String number, int features, double price)
    {
            Room temp = new Room(number, features, price);
            return temp;
    }
    
    /**
     * 
     * @param price 
     */
    public void setPrice(double price)
    {
            Price = price;
    }
    
    /**
     * 
     * @return 
     */
    public String describeRoom()//Returns a string description of the current room
    {   
        return describe.describeRoom();
    }

    /**
     * Get the int value representation for the room.
     * @return int
     */
    public int getFeatures()
    {
        return Features;
    }
    
    /**
     * 
     * @param search
     * @return 
     */
    public boolean isMatch(int search)
    {
        boolean match = ((search & Features) == search);
        return match;
            
    }
    
    /**
     * 
     * @return 
     */
    public String getRoomNum()
    {
        return roomNumber; 
    }
    /**
     * 
     * @return 
     */
    //public Integer getRoom()
    //{
      //  return Integer.getInteger(roomNumber); 
    //}
    
    /**
     * 
     * @return 
     */
    public double getPrice()
    {
        return Price; 
    }
    
    /**
     * 
     * @param reservation
     * @return 
     */
    public double getPriceForDuration(Reserve reservation)//returns how much a stay wll cost
    {
        return Math.round(Price * reservation.getLengthOfStay()*100)/100;
    }
    
    /**
     * 
     * @return 
     */
    public String toString()
    {
        String allData = "Room: "+roomNumber +" Price: " + Price +" Room Details: " + this.describeRoom() ;
        return allData; 
    }
    
    /**
     * 
     * @param temp
     * @return 
     */
    public boolean checkReservations(Reserve temp)//check if reservation willl work with an eisting reservation
    {
        if(temp != null)
            for(Reserve x : reservations)
            {
                //System.out.println("!x.isFree(temp) = "+!x.isFree(temp));
                if(!temp.isFree(x))
                    return false;
            }
        
            
        return true;
    }
    
    public ArrayList<Reserve> allReservations()
    {
        return reservations;
    }
    /**
     * 
     * @param temp
     * @return 
     */
    public boolean addReservation(Reserve temp)//add a reservation to the list
    {
        //String result = "Reservation was not added: Room is unavailable at that time.";
        boolean result = false;
        reservationCount++; 
        if(checkReservations(temp))
        {
            temp.setReserveID(reservationCount);
            reservations.add(temp);
            //result = "Reservation added";
            result = true;
        }
        return result; 
    }
    
    /**
     * 
     * @param userID
     * @return 
     */
    public ArrayList<Reserve> getReservationsWithUser(String userID)//get all the reservations a certain user has
    {
        ArrayList<Reserve> matches = new ArrayList<Reserve>();
        for(int x = 0; x<reservations.size(); x++)
        {
            
            if(reservations.get(x).getUser().equals(userID))
                matches.add(reservations.get(x));
                
        }
        return matches; 
        
    }
    
    /**
     * 
     * @param userID
     * @param reservation
     * @return 
     */
    public String removeReservationWithUser(String userID, String reservation)//removes the reservation of a verterin user
    {
        String result = "Reservation for that user was not found";
        for(int x = 0; x<reservations.size(); x++)
        {
            
            if(reservations.get(x).getUser().equals(userID) && reservations.get(x).getUser().equals(userID))
            {
                result = "Reservation: " + reservation + " by User: " + userID + " has been removed";
            }
        }
        
        return result;
    }
    
    public void setRoomNumber(String temp)
    {
        this.roomNumber = temp;
    }
    
    public void setRoomInfo(int temp)
    {
        this.Features = temp;
    }
}
