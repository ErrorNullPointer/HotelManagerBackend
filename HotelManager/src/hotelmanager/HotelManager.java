/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Luc
 */
public class HotelManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Make Hotel
      
        Hotel hotel = Hotel.loadState();
        if(hotel == null)
        {
            System.out.println("Hotel not found, building new hotel");
            hotel = new Hotel();
            hotel.AddUser(AdminUser.makeUser("Admin", "sudoer", "sudo"));
            hotel.logIn("sudoer", "sudo");
            
            for(int y = 0, x = 0; x<30; x++)
            {
                y=x%17; 
                if(((x & 32) == 32) &&  ((x & 1) == 1))
                    y = (y & ~1);
                else if (((y & 32) != 32) &&  ((y & 1) != 1))
                    y = (y | 32);
                hotel.AddRoom(Room.makeRoom(x, y , (1+x)*13.50));
            }
            hotel.logOff();
        }
        
        Scanner kb = new Scanner(System.in);
        String current = ""; 
        String outputMessage = "";
        String user, userName, password, result, start, end;
        int roomnumber,features;
        double price;
        SimpleDateFormat dateParse=new SimpleDateFormat("yyyy MM dd");
        boolean paid; 
        while(!current.equals("1"))
        {
            
            System.out.println("What would you like to do? \n 1) Quit 2) Log in 3) Log Off 4) Make User 5) Display my information");
            System.out.println(" 6) Search Rooms 7)Add Room 8) Set existing room Price");
            System.out.println(" 9) Make reservation");
            current = kb.nextLine();
            
            switch (current) 
            {
                case "1":
                 outputMessage = "Saving...." + hotel.saveState() + "\nExiting...";
                 break;
                case "2":
                    System.out.println("What is your username?");
                    user = kb.nextLine();
                    System.out.println("What is your password?");
                    password = kb.nextLine();
                    if(hotel.logIn(user, password))
                        outputMessage = "Log in worked";
                    else 
                        outputMessage = "Log in did not work. Check username and password. ";
                    break;
                case "3":
                    if(hotel.logOff())
                        outputMessage = "Log off worked";
                    else 
                        outputMessage = "Log off did not work.";
                    break;
                case "4":
                    System.out.println("What is your Name?");
                    user = kb.nextLine();
                    System.out.println("What is your desired UserName?");
                    userName = kb.nextLine();
                    System.out.println("What is your password?");
                    password = kb.nextLine();
                    outputMessage = hotel.AddUser(UserBase.makeUser(user, userName, password));
                    break;
                case "5":
                    outputMessage = hotel.currentUser();
                    break;
                case "6":
                    System.out.println(" What would you like to seach for? Give me a number.\n If you want to search for any type of room Select 0,\n else add the following number to the total to search for that feature.\n smoke = 1, doubleBed = 2,suite = 4, handicap = 8,twobeds = 16, nonsmokeing = 32");
                    int searchValue = 0;
                    try {
                        searchValue = Integer.parseInt(kb.nextLine());
                        outputMessage = "Searching for a room with: " + Room.describeARoom(searchValue);
                        System.out.println(outputMessage);
                        outputMessage = hotel.searchAvailableRooms(searchValue, null).toString();
                    } catch (NumberFormatException nfe) {
                        System.out.println();
                        outputMessage = ("Search not understood");
                    }
                    break;
                case "7":
                    try {
                        System.out.println("What is your the Room number?");
                        roomnumber = Integer.parseInt(kb.nextLine());
                        System.out.println("What are the room features?");
                        features = Integer.parseInt(kb.nextLine());
                        System.out.println("What is price?");
                        price = Double.parseDouble(kb.nextLine());
                        outputMessage = hotel.AddRoom(Room.makeRoom(roomnumber, features, price));
                    } catch (NumberFormatException nfe) {
                        outputMessage = ("Unable to add room: incorrect data given.");
                    }
                    break;
                case "8":
                    try {
                        System.out.println("What is your the Room number?");
                        roomnumber = Integer.parseInt(kb.nextLine());
                        System.out.println("What is the new price?");
                        price = Double.parseDouble(kb.nextLine());
                        outputMessage = hotel.setRoomPrice(roomnumber, price);
                    } catch (NumberFormatException nfe) {
                        outputMessage = ("Unable to add room: incorrect data given.");
                    }
                    
                    break;
                case "9":
                    try {
                        System.out.println("What is your start date?");
                        start = kb.nextLine();
                        Date s = dateParse.parse(start);
                        System.out.println("What is your end date?");
                        end = kb.nextLine();
                        Date e = dateParse.parse(end);
                        System.out.println("What is your the Room requested?");
                        roomnumber = Integer.parseInt(kb.nextLine());
                        System.out.println("Would you like to pay now?");
                        if(kb.nextLine().equals("y"))
                            paid = true;
                        else
                            paid = false;
                            
                        System.out.println(hotel.makeReservation(s,e,roomnumber,paid));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    break;
                case "10":
                    break;
                default:
                    outputMessage = ("Invalid input: " + current);
            }
            System.out.println(outputMessage);
        }
        
        
        
        
        
    }
    
}
