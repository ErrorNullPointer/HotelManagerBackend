/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;
import java.text.SimpleDateFormat;
import java.util.Date; 

/**
 *
 * @author Luc
 */
public class Reserve implements java.io.Serializable{
    private Date Start, End;
    private String GUID, ReserveID;
    private int Room;
    private boolean Paid;
    private boolean CheckedIn;
    /**
     * Get the start date of the reservation.
     * @return The starting date of the reservation.
     */
    public Date getStart()
    {
        return Start;
    }
    /**
     * Get the user who holds this reservation.
     * @return The user who holds this reservation.
     */
    public String getUser()
    {
        return GUID;
    }
    /**
     * Set the ID of this reservation.
     * @param id The identification number to use.
     */
     public void setReserveID(int id)
    {
        ReserveID  = "" + id; 
    }
     /**
      * Return the ID of this reservation.
      * @return The ID of this reservation. 
      */
     public String getReserveID()
    {
        return ReserveID;
    }
     /**
      * Get the ending date of this reservation.
      * @return The ending date of this reservation.
      */
    public Date getEnd()
    {
        return End;
    }
    /**
     * Generate a new instance of a Reserve object.
     * @param s Starting date of the reservation.
     * @param e Ending date of the reservation.
     * @param guid ID of the user who made the reservation.
     * @param room Room number this reservation is for.
     * @param paid Indicates if the reservation has been paid for.
     * @return A new Reserve instance.
     */
    public static Reserve makeReserve(Date s, Date e, String guid, int room, boolean paid)
    {
        Reserve result = new Reserve( s,  e,  guid,  room,  paid);
        return result;
    }
    /**
     * Constructor
     * @param s Starting date of the reservation.
     * @param e Ending date of the reservation.
     * @param guid ID of the user who made the reservation.
     * @param room Room number this reservation is for.
     * @param paid Indicates if the reservation has been paid for.
     */
    public Reserve(Date s, Date e, String guid, int room, boolean paid)
    {
        if(isVaildReservation(s,e))//checks to see if start is before end, if not it switches the dates
        {
            Start = s;
            End = e;
        }
        else 
        {
            Start = e;
            End = s;
        }
        GUID = guid;
        Room = room;
        Paid = paid;
        CheckedIn = false;
        ReserveID = GUID + room + Start + End; 
    }
    /**
     * Check a user into this room if it is not already occupied.
     * @return a message indicating whether the operation was succesful.
     */
    public String checkIn()
    {
        String result = "Check in did not work";
        if(!CheckedIn)
            {
                CheckedIn = true; 
                result = "Checked in " + GUID + " into room " + Room;
            }
            else 
                result += ": Room is already occupied.";
        
        
        return result;
    }
    /**
     * Attempt to check a user out of this reservation if they have paid.
     * @return Message indicating if the checkout was successful.
     */
    public String checkOut()
    {
        String result = "Check out did not work";
        if(CheckedIn)
            {
                if(Paid)
                {
                    CheckedIn = false;
                    result = "Checked out " + GUID + " out of room " + Room;
                }
                else 
                    result += ": Room is not paid for.";
                
            }
            else 
                result += ": Room is not occupied.";
        
       
        return result;
    }
    /**
     * Mark the room as paid if it is not already paid for.
     * @return A message indicating whether or not the payment was successful.
     */
    public String payForRoom()
    {
        String result = "Payment did not work";
       if(!Paid)
            {
                Paid =true;
                result = "Payment complete";
            }
            else 
                result += ": Room is already paid for.";
        
       
        return result;
    }
    /**
     * Return the difference between the start and end dates.
     * @return The difference between the start and end dates.
     */
    public int getLengthOfStay()
    {
        return (int)((Start.getTime() - End.getTime()) / (1000*60*60*24));
    }
    /**
     * Generate a string representation of this reservation.
     * @return A string representation of this reservation listing room number, user. reservation, and start and end dates.
     */
    public String toString()
    {
        SimpleDateFormat dateParse = new SimpleDateFormat("dd-MM-yyyy");
        return "\n\t\t(" + "Room Number: " + Room + " Reserved by: " + GUID + " Reservation ID: " + ReserveID + " Start Date: " + dateParse.format(Start) + " End Date: " + dateParse.format(End) + " Checked in: " + CheckedIn + " Paid: " + Paid + " ) ";
    }
    /**
     * Determine if this reservation conflicts with another.
     * @param existing Reservation to compare this one to.
     * @return Returns true if the two reservations do not conflict, false if they do.
     */
    public boolean isFree(Reserve existing)
    {
        // does some logice to figure our of two reservations are compatable.  
        boolean isAfter = (Start.after(existing.getEnd()));
        boolean isBefore = (End.before(existing.getStart()));
        boolean notStartBetween = Start.before(existing.getStart());
        boolean notEndBetween = End.after(existing.getEnd());
        boolean startBeforeEndAfter = Start.before(existing.getStart()) && End.after(existing.getEnd());
        /*
        System.out.println("Start=" + Start);
        System.out.println("End=" + End);
        System.out.println("Existing Start=" + existing.getStart());
        System.out.println("Exisitng End=" + existing.getEnd());
        System.out.println("!startBeforeEndAfter=" + !startBeforeEndAfter);
        System.out.println("isAfter=" + isAfter);
        System.out.println("isBefore=" + isBefore );
        System.out.println("notEndBetween=" + notEndBetween);
        System.out.println("notStartBetween=" + notStartBetween);
        */
        
        if((isAfter || isBefore || notEndBetween || notStartBetween) && !startBeforeEndAfter)
            return true;
        else
            return false;
    }
    /**
     * Check if a reservation is valid.
     * @param s Start date of the reservation.
     * @param e End date of the reservation.
     * @return True if s is before e, false if it is not.
     */
    public static boolean isVaildReservation(Date s, Date e) //makes sure the reservation has s before e
    {
        if(s.before(e) || s.equals(e))
            return true;
        else 
            return false;
    
    }
    
    
}
