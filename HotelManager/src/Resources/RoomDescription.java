/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Resources;

/**
 *
 * @author PikeMobile
 */
public class RoomDescription implements java.io.Serializable{
    
    // set values for features, to add one just double the privous number
    public final int smoke = 1;
    public final int queenBed = 2;
    public final int suite = 4;
    public final int handicap = 8;
    public final int twobeds = 16; 
    //public final int nonsmokeing = 32; 
    private int Features = 0;
    
    public RoomDescription()
    {
        
    }
    
    public RoomDescription(int temp)
    {
        Features = temp;
    }
    
    public int getFeatures()
    {
      return Features;   
    }
    
    public String describeRoom()
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
        {
            des += "Smoking, ";
        }
        else 
        {
            des += "Non Smoking, ";
        }
        if ((Features & twobeds) == twobeds)
        {
            des += "two ";
        }
        else
        {
            des += "single ";
        }
        if ((Features & queenBed) == queenBed)
        {
            des += "Queen Bed, ";
        }
        else
        {
            des += "Double Bed, ";
        }
        if ((Features & suite) == suite)
        {
            des += "Suite, ";
        }
        if ((Features & handicap) == handicap)
        {
            des += "Handicap ";
        }
        
        return des;
    }
    
    public String describeARoom(int temp)
    {
        String des = "";
        if ((temp & smoke) == smoke)
        {
            des += "Smoking, ";
        }
        else
        {
            des += "Non-Smoking, ";
        }
        if ((temp & twobeds) == twobeds)
        {
            des += "two ";
        }
        else
        {
            des += "single bed ";
        }
        if ((temp & queenBed) == queenBed)
        {
            des += "Queen Bed, ";
        }
        else
        {
            des += "Double Bed, ";
        }
        if ((temp & suite) == suite)
        {
            des += "Suite, ";
        }
        if ((temp & handicap) == handicap)
        {
            des += "Handicap, ";
        }
        

        return des; 
    }
    
    
}
