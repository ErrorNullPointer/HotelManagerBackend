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
    int roomNumber; 
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
    
    private Room(int number, int features, double price)
    {
        Features = features; 
        roomNumber = number;
        Price = price;
        reservationCount = 0; 
        //paid = true;
        //occupied = false; 
    }
    /**
     * Generates an instance of a Room.
     * @param number Room Number of the Room to generate.
     * @param features Bitflags that indicate the features of the room.
     * @param price Price of the room.
     * @return The generated Room object.
     */
    public static Room makeRoom(int number, int features, double price)
    {
            Room temp = new Room(number, features, price);
            return temp;
    }
    /**
     * Set the price of this Room object.
     * @param price New price for this room.
     */
    public void setPrice(double price)
    {
            Price = price;
    }
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
     * Returns a string representation of an array of bitflags
     * representing features of a room.
     * 
     * @param temp Integer consisting of bitflags that indicate features to be described.
     * @return Returns a description of the features room
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
     * See if this room has the features in a search query.
     * 
     * @param search Array of bitflags that indicate desired features of a room.
     * @return Returns a boolean indicating if the desired features are contained in this room.
     */
    public boolean isMatch(int search)
    {
        boolean match = ((search & Features) == search);
        return match;
            
    }
    /**
     * Get the room number as a string.
     * @return This rooms number represented a sa string.
     */ 
    public String getRoom()
    {
        return "" + roomNumber; 
    }
    /**
     * Get the room number of this room.
     * @return  This rooms number.
     */
    public int getRoomInt()
    {
        return roomNumber; 
    }
    /**
     * Return the price of this room.
     * @return The price of this price.
     */
    public double getPrice()
    {
        return Price; 
    }
    /**
     * Get how much a stay will cost with a given reservation.
     * @param reservation Reservation whose cost is to be calculated.
     * @return Price of the stay.
     */
    public double getPriceForDuration(Reserve reservation)//returns how much a stay wll cost
    {
        return Math.round(Price * reservation.getLengthOfStay()*100)/100;
    }
    /**
     * Generates a string representation of this Reservation.
     * @return A string that lists room number, 
     * price, Reservations, and Room features.
     */
    public String toString()
    {
        String allData = "\n\tRoomNumber = " + roomNumber +" Price: " + Price + " Reservations: " + reservations.toString() + " \n\t\tRoom Details: " + describeRoom() ;
        return allData; 
    }
    /**
     * Check if a given reservation conflicts with existing reservations.
     * 
     * @param temp New reservation to check.
     * @return A Boolean indicating if the reservation does not conflict.
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
    /**
     * Add a reservation to the list.
     * 
     * @param temp Reservation to add.
     * @return Result of the operation to add a reservation.
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
     * Get all the reservations a certain user has in this room.
     * 
     * @param userID ID of the user whose reservations are being queried.
     * @return ArrayList of all the reservations for a particular user.
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
     * Removes the reservation of a specified user.
     * 
     * @param userID ID of the user whose reservations are to be erased
     * @param reservation ID of the reservation to be removed,
     * @return Result of the removal operation.
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
    
    
}
