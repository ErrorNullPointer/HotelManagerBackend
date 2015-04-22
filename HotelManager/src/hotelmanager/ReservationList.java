/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import java.util.ArrayList;
import Resources.Iterator;

/**
 *
 * @author PikeMobile
 */
public class ReservationList implements java.io.Serializable{
    public ArrayList <Reserve> list = new ArrayList();
    private RoomList rooms = null;
    
    /**
     * 
     */
    public ReservationList(RoomList rooms)
    {
        this.rooms = rooms;
    }
    
    public boolean addReservation(Reserve temp)
    {
        list.add(temp);
        return true;
    }
    
    public ArrayList <Reserve> allReservations()
    {
        return list;
    }
    
    public boolean remove(String guid, String reservatinId)
    {
        //String result = "Item Not found";
        ArrayList<Reserve> userReservations = findAllUserReservations(guid);
        for(int x = 0; x < userReservations.size(); x++)
        {
            if(userReservations.get(x).getReserveID().equals(reservatinId))
            {
                list.remove(x);
                userReservations.remove(x);
                return true;
            }
        }
        return false;
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
     */
    private class ReserveIterator implements Iterator
    {
        private ArrayList <Reserve> list;
        private int i = 0;
        
        public ReserveIterator(ArrayList<Reserve> temp)
        {
            list = temp;
        }
        
        @Override
        public boolean hasNext() {
            return i<list.size();
        }

        @Override
        public Object next() {
            if(hasNext())
            {
                return list.get(i++);
            }
            //throw IndexOutOfBoundsException;
            return null;
        }
        
    }
}
