import java.util.ArrayList;
import java.util.Scanner;

/*
  This is the main file for the memSim project.

  It starts up the CPU, RAM, HDD, and any other accessories specified by the user

  Authors: Pat Baumgardner, Adam Dachenhausen, Shah Syed
 */

class memSim{
    //An array to hold all the hard drives in the system
    HDD[] hardDrive;

    //An array to hold all the ram sticks in the system
    RAM[] rams;
    
    ArrayList processes = new ArrayList<process>();
    CPU cpu;

    /**
       Starts the simulation of our memory management.
       First prompts the user for data
       
     */
    public static void main(String args[]){
	//Here we will prompt the user for data
	memSim sim = promptUser();

	
    }
    /**
       A constructor for the memSim obect
     **/
    public memSim(){
	
    }
    
    /**
       Prompts the user for specifics about the system.
       Then initializes the system

       @return a memSim object specified by the user's requests
     **/
    private static memSim promptUser(){
	memSim sim = new memSim();
	Scanner scnr = new Scanner(System.in);
	
	System.out.println("How many Hard Drives would you like?");
	String input = scnr.nextLine();

	System.out.println("And how big (in bytes) should each one be?");
	input = scnr.nextLine();

	System.out.println("How many RAM sticks would you like?");
	input = scnr.nextLine();

	System.out.println("And how big (in bytes) should each one be?");
	input = scnr.nextLine();

	
	return sim;
    }
}
