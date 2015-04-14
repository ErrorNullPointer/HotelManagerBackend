/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import Resources.Iterator;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author PikeMobile
 */
public class RoomList implements java.io.Serializable{
    public ArrayList<Room> rooms = new ArrayList<Room>();//hols are the rooms
    public DefaultListModel list = new DefaultListModel();
    
    public RoomList(){
        
    }
    
    public int size()
    {
        return rooms.size();
    }
    
    public boolean remove(int index)
    {
        rooms.remove(index);
        list.removeElementAt(index);
        return true;
    }
    
    public Room getRoom(int index)
    {
        return rooms.get(index);
    }
    /**
     * Add unique room to RoomList.
     * @param temp
     * @return 
     */
    public boolean AddRoom(Room temp)
    {
        for(int i =0; i<rooms.size();i++)
        {
            if(rooms.get(i).getRoomNum().equals(temp.getRoomNum()))
            {
               return false;
            }
        }
        rooms.add(temp);
        list.addElement(temp.toString());
        System.out.println("Added " + temp.toString());
        return true;
    }
    
    /**
     * Inserts the given room into the list. 
     * Replaces the current room if there is conflicting room numbers.
     * @return 
     */
public void  insertRoom(Room temp)
{
    for(int i =0; i<rooms.size();i++)
        {
            if(rooms.get(i).getRoomNum().equals(temp.getRoomNum()))
            {
               rooms.remove(i);
               rooms.add(i, temp);
               list.add(i, temp.toString());
               return;
            }
        }
    rooms.add(temp);
    list.addElement(temp.toString());
}
    /**
     * 
     */
    private class RoomIterator implements Iterator
    {
        int i = 0;
        ArrayList<Room> rooms ;//= new ArrayList<Room>();
        
        public RoomIterator(ArrayList<Room> room)
        {
            rooms = room;
            //i = rooms.size();
        }
        
        @Override
        public boolean hasNext()
        {
            if(i<rooms.size())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        @Override
        public Room next()
        {
            try{
            return rooms.get(i++);
            }
            catch(IndexOutOfBoundsException e)
            {
                return null;
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    public Iterator it()
    {
        return new RoomIterator(rooms);
    }
}
