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
    private String Room;
    private boolean Paid;
    private boolean CheckedIn;
    
    public Date getStart()
    {
        return Start;
    }
    public String getUser()
    {
        return GUID;
    }
     public void setReserveID(int id)
    {
        ReserveID  = "" + id; 
    }
     public String getReserveID()
    {
        return ReserveID;
    }
    public Date getEnd()
    {
        return End;
    }
    public static Reserve makeReserve(Date s, Date e, String guid, String room, boolean paid)
    {
        Reserve result = new Reserve( s,  e,  guid,  room,  paid);
        return result;
    }
    public Reserve(Date s, Date e, String guid, String room, boolean paid)
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
    public int getLengthOfStay()
    {
        return (int)((Start.getTime() - End.getTime()) / (1000*60*60*24));
    }
    public String toString()
    {
        SimpleDateFormat dateParse = new SimpleDateFormat("dd-MM-yyyy");
        return "\n\t\t(" + "Room Number: " + Room + " Reserved by: " + GUID + " Reservation ID: " + ReserveID + " Start Date: " + dateParse.format(Start) + " End Date: " + dateParse.format(End) + " Checked in: " + CheckedIn + " Paid: " + Paid + " ) ";
    }
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
    public static boolean isVaildReservation(Date s, Date e) //makes sure the reservation has s before e
    {
        if(s.before(e) || s.equals(e))
            return true;
        else 
            return false;
    
    }
    
    
}
