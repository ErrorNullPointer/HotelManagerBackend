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
        if(hotel == null)//checking if we were able to load our hotel, if not it makes one
        {
            System.out.println("Hotel not found, building new hotel");
            hotel = new Hotel();
            hotel.AddUser(AdminUser.makeUser("Admin", "sudoer", "sudo"));
            hotel.logIn("sudoer", "sudo");// need to log in as an admin in order to add rooms
            
            for(int y = 0, x = 0; x<30; x++)///If out hotel is messed up or does not exist this makes one
            {
                y=x%17; ///This bit of codd here just makes a bunch of random features for each room, and markes each non soming room as such
                if(((x & 32) == 32) &&  ((x & 1) == 1))
                    y = (y & ~1);
                else if (((y & 32) != 32) &&  ((y & 1) != 1))
                    y = (y | 32);
                hotel.AddRoom(Room.makeRoom(x, y , (1+x)*13.50));
            }
            hotel.logOff();// log off as admin
        }
        
        Scanner kb = new Scanner(System.in); // allows us to grab keyboad info
        
        // used as input vars
        String current = "";  
        String outputMessage = "";
        String user, userName, password, result, start, end; 
        int roomnumber,features;
        double price;
        boolean paid; 
        
        SimpleDateFormat dateParse=new SimpleDateFormat("dd-MM-yyyy");  //used to help pares dates into text
        
        while(!current.equals("1"))
        {
            //All of our options during runtime   
            // I just went down the list on the paper her gave use. // The backend has more abilities than the interface shows. I just saw this interface as more of a proof of concept
            System.out.println("What would you like to do? \n 1) Quit 2) Log in 3) Log Off 4) Make User 5) Display my information");
            System.out.println(" 6) Search Rooms 7)Add Room 8) Set existing room Price");
            System.out.println(" 9) Make reservation 10) Check in guest 11) Check out guest");
            System.out.println(" 12) Pay for room");
            current = kb.nextLine();//gets the kb input
            // hotel.logIn("sudoer", "sudo");  ///uncoment line in order to auto log in
            switch (current)  // switch statemnet based on the keyboard input 
            {
                //No real logic is done here other then taking steps with the hotel. Most of the actions are happening in the hotel
                case "1":
                 outputMessage = "Saving...." + hotel.saveState() + "\nExiting..."; // on exit save all our data
                 break;
                case "2"://log in
                    System.out.println("What is your username?");
                    user = kb.nextLine();
                    System.out.println("What is your password?");
                    password = kb.nextLine();
                    if(hotel.logIn(user, password))
                        outputMessage = "Log in worked";
                    else 
                        outputMessage = "Log in did not work. Check username and password. ";
                    break;
                case "3"://log out
                    if(hotel.logOff())
                        outputMessage = "Log off worked";
                    else 
                        outputMessage = "Log off did not work.";
                    break;
                case "4"://make user
                    System.out.println("What is your Name?");
                    user = kb.nextLine();
                    System.out.println("What is your desired UserName?");
                    userName = kb.nextLine();
                    System.out.println("What is your password?");
                    password = kb.nextLine();
                    outputMessage = hotel.AddUser(UserBase.makeUser(user, userName, password));
                    break;
                case "5"://display my current info
                    outputMessage = hotel.currentUser() + " \nReservations currents user: " + hotel.reservationsForCurrentUser();
                    break;
                case "6"://search rooms
                    System.out.println(" What would you like to seach for? Give me a number.\n If you want to search for any type of room Select 0,\n else add the following number to the total to search for that feature.\n smoke = 1, doubleBed = 2,suite = 4, handicap = 8,twobeds = 16, nonsmokeing = 32");
                    int searchValue = 0;
                    try {
                        searchValue = Integer.parseInt(kb.nextLine());
                        outputMessage = "Searching for a room with: " + Room.describeARoom(searchValue);
                        System.out.println(outputMessage);
                        System.out.println("What is your requested start date?(Format: dd-MM-yyyy)");
                        start = kb.nextLine();
                        Date s = dateParse.parse(start);
                        System.out.println("What is your requested end date?(Format: dd-MM-yyyy)");
                        end = kb.nextLine();
                        Date e = dateParse.parse(end);
                        outputMessage = hotel.searchAvailableRooms(searchValue, Reserve.makeReserve(s, e, null , -1, false)).toString();
                    } catch (NumberFormatException nfe) {
                        System.out.println();
                        outputMessage = ("Search not understood");
                    }catch (ParseException e) {
                       System.out.println(e.getMessage());
                    }
                    break;
                case "7": // add a room
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
                case "8": //set the rooms price
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
                case "9": // make a reservation
                    try {
                        System.out.println("What is your start date?(Format: dd-MM-yyyy)");
                        start = kb.nextLine();
                        Date s = dateParse.parse(start);
                        System.out.println("What is your end date?(Format: dd-MM-yyyy)");
                        end = kb.nextLine();
                        Date e = dateParse.parse(end);
                        if(Reserve.isVaildReservation(s,e))
                        {
                            System.out.println("What is your the Room requested?");
                            roomnumber = Integer.parseInt(kb.nextLine());
                            System.out.println("Would you like to pay now?");
                            if(kb.nextLine().equals("y"))
                                paid = true;
                            else
                                paid = false;
                            outputMessage = (hotel.makeReservation(s,e,roomnumber,paid));
                        }
                        else 
                            outputMessage = "Error: Your reservation Ends before it starts. ";

                    } catch (ParseException e) {
                       System.out.println(e.getMessage());
                    }

                    break;
                case "10":
                    System.out.println("What is the user ID for the user you want to check in?");
                    user = kb.nextLine();
                    System.out.println("What is the reservation ID?");
                    password = kb.nextLine();
                    outputMessage = hotel.checkInReservation(user, password);
                    break;
                case "11":
                    System.out.println("What is the user ID for the user you want to check out?");
                    user = kb.nextLine();
                    System.out.println("What is the reservation ID?");
                    password = kb.nextLine();
                    outputMessage = hotel.checkOutReservation(user, password);
                    break;
                case "12":
                    System.out.println("What is the UserId for the room you want to pay for");
                    user = kb.nextLine();
                    System.out.println("What is the reservation ID?");
                    password = kb.nextLine();
                    outputMessage = hotel.payReservation(user, password);
                    break;
                default:
                    outputMessage = ("Invalid input: " + current);
            }
            System.out.println(outputMessage);
        }
        
        
        
        
        
    }
    
}
