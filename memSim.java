import java.util.ArrayList;
import java.util.Scanner;

/*
  This is the main file for the memSim project.

  It starts up the CPU, RAM, HDD, and any other accessories specified by the user

  Authors: Pat Baumgardner, Adam Dachenhausen, Shah Syed
 */

class memSim{
    //An array to hold all the hard drives in the system
    HDD[] hardDrives;

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
    public memSim(int numHDD, int hDDSize, int numHDDPlatters, int numRam, int rAMSize, int cacheSize){
	//Let's first initialize the number of harddrives, and their size
	hardDrives = new hardDrives[numHDD];

	for(int i = 0; i < numHDD; i++){
	    hardDrives[i] = new HDD(hDDSize, numHDDPlatters);
	}

	//Now let's initialize our RAM
	rams = new RAM[numRam];

	for(int i = 0; i < numRam; i++){
	    rams[i] = new RAM(rAMSize);
	}

	//Now let's initialize our cacheSize
	cpu = new CPU(1, cacheSize);
    }
    
    /**
       Prompts the user for specifics about the system.
       Then initializes the system and returns it

       @return a memSim object specified by the user's requests
     **/
    private static memSim promptUser(){
	Scanner scnr = new Scanner(System.in);

	//Let's ask the user for system parameters
	System.out.println("How many Hard Drives would you like?");
	String input = scnr.nextLine();
	int numHDD = Integer.parseInt(input);

	System.out.println("How many platters should each hard drive have?");
	input = scnr.nextLine();
	int numPlatters = Integer.parseInt(input);

	System.out.println("And how big (in bytes) should each one be?");
	input = scnr.nextLine();
	int hDDSize = Integer.parseInt(input);

	System.out.println("How many RAM sticks would you like?");
	input = scnr.nextLine();
	int numRAM = Integer.parseInt(input);

	System.out.println("And how big (in bytes) should each one be?");
	input = scnr.nextLine();
	int rAMSize = Integer.parseInt(input);

	System.out.println("Finally, how big (in bytes) would you like your CPU cache to be?");
	input = scnr.nextLine();
	int cacheSize = Integer.parseInt(input);

	//And finally initialize the systemx
	memSim sim = new memSim(numHDD,hDDSize,numPlatters,numRam,rAMSize,cacheSize);
	
	return sim;
    }
}
