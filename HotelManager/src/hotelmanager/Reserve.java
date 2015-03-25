/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;
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
    
    public Date getStart()
    {
        return Start;
    }
    public Date getEnd()
    {
        return End;
    }
    public static Reserve makeReserve(Date s, Date e, String guid, int room, boolean paid)
    {
        Reserve result = new Reserve( s,  e,  guid,  room,  paid);
        return result;
    }
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
    public String checkIn(UserBase current)
    {
        String result = "Check in did not work";
        if(current.getType().equals("Admin"))
        {
            if(!CheckedIn)
            {
                CheckedIn = true; 
                result = "Checked in " + GUID + " into room " + Room;
            }
            else 
                result += ": Room is already occupied.";
        }
        else 
                result += ": You do not have permisson for this action.";
        return result;
    }
    public String checkOut(UserBase current)
    {
        String result = "Check out did not work";
        if(current.getType().equals("Admin"))
        {
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
        }
        else 
                result += ": You do not have permisson for this action.";
        return result;
    }
    public String payForRoom(UserBase current)
    {
        String result = "Payment did not work";
        if(current.getType().equals("Admin"))
        {
            if(!Paid)
            {
                Paid =true;
                result = "Payment complete";
            }
            else 
                result += ": Room is already paid for.";
        }
        else 
                result += ": You do not have permisson for this action.";
        return result;
    }
    public int getLengthOfStay()
    {
        return (int)((Start.getTime() - End.getTime()) / (1000*60*60*24));
    }
    public String toString()
    {
        return "(" + "Room Number: " + Room + " Reserved by: " + GUID + " Start Date: " + Start + " End Date: " + End + " ) ";
    }
    public boolean isFree(Reserve existing)
    {
        
        boolean isAfter = (Start.after(existing.getEnd()));
        boolean isBefore = (End.before(existing.getStart()));
        boolean notStartBetween = !(Start.after(existing.getStart()) && Start.before(existing.getEnd()));
        boolean notEndBetween = !(End.after(existing.getStart()) && End.before(existing.getEnd()));
        //System.out.println(isAfter || isBefore || notEndBetween || notStartBetween);
        if(isAfter || isBefore || notEndBetween || notStartBetween)
            return true;
        else
            return false;
    }
    public boolean isVaildReservation(Date s, Date e)
    {
        if(s.before(e) || s.equals(e))
            return true;
        else 
            return false;
    
    }
    
    
}
