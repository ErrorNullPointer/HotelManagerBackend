/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import java.util.ArrayList;

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
    // set values for features, to add one just double the privous number
    private static int smoke = 1;
    private static int doubleBed = 2;
    private static int suite = 4;
    private static int handicap = 8;
    private static int twobeds = 16; 
    private static int nonsmokeing = 32; 
    
    /**
     * 
     * @param number
     * @param features
     * @param price 
     */
    public Room(String number, int features, double price)
    {
        Features = features; 
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
    private String describeRoom()//Returns a string description of the current room
    {
        
        String des = "";
        /*
        int smoke = 1;
        int doubleBed = 2;
        int suite = 4;
        int handicap = 8;
        int twobeds = 16; 
                */        
        if ((Features & smoke) == smoke)
            des += "Smokeing, ";
        else 
            des += "Non Smokeing, ";
        if ((Features & doubleBed) == doubleBed)
            des += "Double Bed, ";
        if ((Features & suite) == suite)
            des += "Suite, ";
        if ((Features & handicap) == handicap)
            des += "Handicap, ";
        if ((Features & twobeds) == twobeds)
            des += "Has two beds, ";
        return des; 
    }
    
    /**
     * 
     * @param temp
     * @return 
     */
    public static String describeARoom(int temp)//Returns a string description, however you can pass in any number
    {
        
        String des = "";
        /*
        int smoke = 1;
        int doubleBed = 2;
        int suite = 4;
        int handicap = 8;
        int twobeds = 16; 
                */        
        if ((temp & smoke) == smoke)
            des += "Smokeing, ";
        if ((temp & doubleBed) == doubleBed)
            des += "Double Bed, ";
        if ((temp & suite) == suite)
            des += "Suite, ";
        if ((temp & handicap) == handicap)
            des += "Handicap, ";
        if ((temp & twobeds) == twobeds)
            des += "Has two beds, ";
        if ((temp & nonsmokeing) == nonsmokeing)
            des += "Non-Smoking, ";
        
        return des; 
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
        String allData = roomNumber ;//+" Price: " + Price +" \n\t\tRoom Details: " + describeRoom() ;
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
    public String addReservation(Reserve temp)//add a reservation to the list
    {
        String result = "Reservation was not added: Room is unavailable at that time.";
        reservationCount++; 
        if(checkReservations(temp))
        {
            temp.setReserveID(reservationCount);
            reservations.add(temp);
            result = "Reservation added";
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
