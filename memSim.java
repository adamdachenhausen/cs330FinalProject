import java.util.ArrayList;
import java.util.Scanner;
/*
This is the main file for the memSim project.

It starts up a CPU (containing one level of cache), a user specified number
of RAM sticks of the same size, and a user specified number of HDDs of the same
size.

All components are threaded individually, as this offers multiple memory actions
to happen at once.

Authors: Pat Baumgardner, Adam Dachenhausen, Shah Syed
 */

class memSim extends Thread{
    //A flag to tell our components if the system is done running
    public static boolean done;

    //The value of one megabyte
    public static final int MB = 1000000;

    //An array to hold all the hard drives in the system
    HDD[] hardDrives;

    //An array to hold all the ram sticks in the system
    RAM[] rams;

    //Our cpu
    CPU cpu;
    
    //Some colored text tags
    private static String reset;
    private static String red;
    private static String green;
    private static String yellow;
    private static String blue;
    private static String purple;
    private static String cyan;

    
    /**
    Starts the simulation of our memory management.
    First prompts the user for data

     */
    public static void main(String args[]){
        //Here we will prompt the user for data
        memSim sim = initialUserPrompt();

        while(!done){
            sim.promptTree();
        }
    }

    /**
    A constructor for the memSim object
     **/
    public memSim(int numHDD, int hDDSize, int numRam, int rAMSize, int cacheSize, int cacheDelay, int ramDelay, int hddDelay){
        //First, we need to allow everything to know that we are starting up, aka not done
        done = false;

        //Let's initialize the number of harddrives, and their size
        hardDrives = new HDD[numHDD];

        for(int i = 0; i < numHDD; i++){
            hardDrives[i] = new HDD(hDDSize, hddDelay);
            hardDrives[i].start();
        }

        //Now let's initialize our RAM
        rams = new RAM[numRam];

        for(int i = 0; i < numRam; i++){
            rams[i] = new RAM(rAMSize, ramDelay);
            rams[i].start();
        }

        //Now let's initialize our cacheSize
        cpu = new CPU(1, cacheSize, cacheDelay);
       cpu.start();
    }

    /**
    Prompts the user for specifics about the system.
    Then initializes the system and returns it

    @return a memSim object specified by the user's requests
     **/
    private static memSim initialUserPrompt(){
        Scanner scnr = new Scanner(System.in);

	//First we need to ask the user if their terminal is ANSI compatible
	System.out.println("\u001B[34mIs this text blue?\u001B[0m Y/n");
	String choice = scnr.nextLine();

	if(choice.equalsIgnoreCase("y")){
	    //Since they can see the blue text, we can use our ANSI codes

	    reset = "\u001B[0m";
	    red = "\u001B[31m";
	    green = "\u001B[32m";
	    yellow = "\u001B[33m";
	    blue = "\u001B[34m";
	    purple = "\u001B[35m";
	    cyan = "\u001B[36m";
	}
	else{
	    //Otherwise they can't see the blue text, or we didn't understand
	    if(!choice.equalsIgnoreCase("n")){
		System.out.println("We didn't understand your answer. Reverting to plain text");
	    }

	    reset = "";
	    red = "";
	    green = "";
	    yellow = "";
	    blue = "";
	    purple = "";
	    cyan = "";
	}

	//Let's ask the user if they want to make their own system, or load one
	System.out.println("Would you like to load a system, or configure your own?");
	System.out.println("Type "+yellow+"LOAD"+reset+" or "+yellow+"NEW"+reset+": ");
	choice = scnr.nextLine();
	if(choice.equalsIgnoreCase("Load")){
	    //Firt we display the choices that the user can choose from
	    System.out.println(purple+"System 1:"+reset);
	    System.out.println("\t2 HDDs with size: 10240 bytes each");
	    System.out.println("\t2 Sticks of RAM with size: 5120 bytes each");
	    System.out.println("\tCPU Cache size: 1024 bytes");
	    System.out.println("\tThis system has 3 partitions of data written to HDD 1, each 1024 bytes,");
	    System.out.println("\tand 2 partitions of data written to HDD 2, each 2048 bytes,");
	    System.out.println("\tand 1 partition written to each RAM stick, 2048 bytes big,");
	    System.out.println("\tand there is nothing in the cache.");
	    
	    System.out.println(purple+"System 2:"+reset);
	    System.out.println("\t1 HDD with size: 102400 bytes");
	    System.out.println("\t1 Stick of RAM with size: 51200 bytes");
	    System.out.println("\tCPU Cache size: 2048 bytes");
	    System.out.println("\tThis system has 10 partitions of 1024 bytes, and 5 of 2048 bytes written to the hard drive,");
	    System.out.println("\tand 3 partitions of 1024 bytes, and 1 of 2048 bytes written to the RAM,");
	    System.out.println("\tand there is nothing in the cache.");
	    
	    System.out.println(purple+"System 3: "+red+"RUN AT YOUR OWN RISK"+reset);
	    System.out.println("\t1 HDD with size: 1024000 bytes");
	    System.out.println("\t1 Stick of RAM with size: 51200 bytes");
	    System.out.println("\tCPU Cache size: 2048 bytes");
	    System.out.println("\tThere is no data written to any of the components in this system.");

	    //Now we let the user know about how the delays are set up
	    System.out.println("**** Delays ****");
	    System.out.println("All systems are configured with the following");
	    System.out.println("CPU cache:\tno delay");
	    System.out.println("RAM:\tTo read/write 1 MB = 5 secs");
	    System.out.println("HDD:\tTo read/write 1 MB = 10 secs");
	    System.out.println("****************");
	    //Now we prompt the user to load a default system
	    System.out.print(yellow+"Please enter the number of your selection: "+reset);
	    int loadSelect = scnr.nextInt();
	    if(loadSelect == 2){
		//First initialize an empty memSim
		memSim finger = new memSim(1,102400,1,51200,2048,0,5,10);

		//Then write the specified data
		for(int i=0;i<10;i++){
		    finger.hardDrives[0].write(1024);
		}
		for(int i=0;i<5;i++){
		    finger.hardDrives[0].write(2048);
		}
		for(int i=0;i<3;i++){
		    finger.rams[0].write(1024);
		}
		finger.rams[0].write(2048);

		//Then return 
		return finger;
	    }
	    else if(loadSelect == 3){
		return new memSim(1024000,1024000,1,51200,2048,0,5,10);
	    }
	    else{
		//If we didn't understand, but the user wants to load, then we will load 1,
		//or if the user just wants to load 1

		//First initialize an empty memSim
		memSim finger = new memSim(2,10240,2,51200,1024,0,5,10);

		//Then write the specified data
		for(int i=0;i<3;i++){
		    finger.hardDrives[0].write(1024);
		}
		for(int i=0;i<2;i++){
		    finger.hardDrives[1].write(2048);
		}
		
		finger.rams[0].write(2048);
		
		finger.rams[1].write(2048);

		//Then return 
		return finger;
	    }
	    
	}
	else{
	    //The user wants to make a new system, or they didn't type a correct reponse

	    //If they typed a bad response, lets let them know, and continue with a new system
	    if(!choice.equalsIgnoreCase("NEW")){
		    System.out.println(red+"Could not understand your choice, reverting to creating a new system"+reset);
		}
		
	    //Let's ask the user for system parameters
	    System.out.println("How many Hard Drives would you like?");
	    String input = scnr.nextLine();
	    int numHDD = Integer.parseInt(input);
	    
	    System.out.println("And how big (in bytes) should each one be?");
	    input = scnr.nextLine();
	    int hDDSize = Integer.parseInt(input);
	    
	    System.out.println("How many RAM sticks would you like?");
	    input = scnr.nextLine();
	    int numRAM = Integer.parseInt(input);
	    
	    System.out.println("And how big (in bytes) should each one be?");
	    input = scnr.nextLine();
	    int rAMSize = Integer.parseInt(input);
	    
	    System.out.println("How big (in bytes) would you like your CPU cache to be?");
	    input = scnr.nextLine();
	    int cacheSize = Integer.parseInt(input);

	    System.out.println("How long would you like it to take in seconds, to read/write to the cache?");
	    input = scnr.nextLine();
	    int cacheDelay = Integer.parseInt(input);

	    System.out.println("How long would you like it to take in seconds, to read/write to the RAM?");
	    input = scnr.nextLine();
	    int ramDelay = Integer.parseInt(input);

	    System.out.println("Finally, how long would you like it to take in seconds, to read/write to a HDD?");
	    input = scnr.nextLine();
	    int hddDelay = Integer.parseInt(input);
	    
	    //Let's print the system config
	    System.out.println();
	    System.out.println(purple+"***********System Config**********"+reset);
	    System.out.println(numHDD+" number of hard drives, each "+hDDSize+" bytes big.");
	    System.out.println(numRAM+" sticks of RAM, each "+rAMSize+" bytes big.");
	    System.out.println("CPU cache is " + cacheSize + " bytes big.");
	    System.out.println("Cache delay is:\t"+cacheDelay+"seconds");
	    System.out.println("RAM delay is:\t"+ramDelay+"seconds");
	    System.out.println("HDD delay is:\t"+hddDelay+"seconds");
	    System.out.println(purple+"**********************************"+reset);
	    System.out.println();

	
	    //And finally initialize the system
	    return new memSim(numHDD,hDDSize,numRAM,rAMSize,cacheSize,cacheDelay,ramDelay,hddDelay);
	}
    }

    /**
    Gives the user a list of options to choose their next move with the simulator,
    and acts accordingly.

     **/
    private void promptTree(){
        //First print the user's options
        System.out.println("What would you like to do next?");
        System.out.println("1.\tMove Data");
        System.out.println("2.\tRead Data");
        System.out.println("3.\tWrite Data");
	System.out.println("4.\tDelete Data");
        System.out.println("5.\tHelp");
        System.out.println("6.\tExit");
        System.out.print(yellow+"Please enter the number of your selection: "+reset);

        //Then wait for their response and tokenize it
        Scanner scnr = new Scanner(System.in);
        int input = scnr.nextInt();

        //Used for determining how long a command took to run
        long start;
        long end;
        long diff;

        //Process the response
        if(input == 1){
	    //First let's prompt the user
	    System.out.println("Where would you like to read the data from?");
	    System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print(yellow+"Please enter the number of your selection: "+reset);

	    //userSubSelects tell us what component to access
            int userSubSelect1 = scnr.nextInt();
	    int userSubSelect2 = userSubSelect1;
	    //subSelects tell us which number of that component type
	    int subSelect1 = -1;
	    int subSelect2 = -1;
	    //dataSelects tell us what data to move on the drive
	    int dataSelect1 = -1;
	    
	    if(userSubSelect1 == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                subSelect1 = scnr.nextInt();
		subSelect1--;

		//Now we have to prompt the user to pick a chunk of data
		hardDrives[subSelect1].print();
		System.out.print(yellow+"Please select one of the above partitions: "+reset);
		dataSelect1 = scnr.nextInt();

            }
            else if(userSubSelect1 == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                subSelect1 = scnr.nextInt();
		subSelect1--;
		
		//Now we have to prompt the user to pick a chunk of data
		rams[subSelect1].print();
		System.out.print(yellow+"Please select one of the above partitions: "+reset);
		dataSelect1 = scnr.nextInt();

            }
            else if(userSubSelect1 == 3){
		dataSelect1 = 1;
		
            }
	    
	    //Now we prompt where the user wants to put this data
	    while(userSubSelect1 == userSubSelect2){
	    System.out.println("Where would you like to write the data to?");
	    System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print(yellow+"Please enter the number of your selection: "+reset);

	    userSubSelect2 = scnr.nextInt();

	    if(userSubSelect2 == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                subSelect2 = scnr.nextInt();
		subSelect2--;

            }
            else if(userSubSelect2 == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                subSelect2= scnr.nextInt();
		subSelect2--;

            }
            else if(userSubSelect2 == 3){
		
            }
	    
	    //We make sure that the user didn't try to move from A to A
	    if(userSubSelect1==userSubSelect2){
		System.out.println(red+"Error, you cannot move data from one place to the same place. Please try again"+reset);
	    }
	    }
	    
            start = System.currentTimeMillis();
            //Here we are going to process where the user wants to move the data from and to

	    //tempData is used to hold how many bytes were read, so we can write them somewhere else
	    int tempData = 0;
	    //We first determine which to access,
	    //then read, delete
	    if(userSubSelect1 == 1){
		//Hard Drive
		tempData = hardDrives[subSelect1].read(dataSelect1);
		hardDrives[subSelect1].delete(dataSelect1);
	    }
	    else if(userSubSelect1 == 2){
		//RAM
		tempData = rams[subSelect1].read(dataSelect1);
		rams[subSelect1].read(dataSelect1);
	    }
	    else{
		//CPU
		tempData = cpu.read();
		cpu.delete(tempData);
	    } 
	    //Then we determine where to write to
	    //and then we write the same number of bytes to the specified component
	    if(userSubSelect2 == 1){
		//Hard Drive
		hardDrives[subSelect2].write(tempData);
	    }
	    else if(userSubSelect2 == 2){
		//RAM
		rams[subSelect2].write(tempData);
	    }
	    else{
		//CPU
		cpu.write(tempData);
	    } 
	    
            //Then we can report the statistics
            end = System.currentTimeMillis();

            diff = end - start;
            System.out.println("The time it took to do this move operation was: " + diff + " milliseconds");
        }
        else if(input == 2){
	    //We read data
            //Now we are going to ask the user where they want to read the data from
            System.out.println("Where would you like to read the data from?");
            System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print(yellow+"Please enter the number of your selection: "+reset);

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                int hddSelect = scnr.nextInt();
		hddSelect--;

		//Now we have to prompt the user to pick a chunk of data
		hardDrives[hddSelect].print();
		System.out.print(yellow+"Please select one of the above partitions: "+reset);
		int dataSelect = scnr.nextInt();

		//Then do the action
		start = System.currentTimeMillis();

		//We don't need this return value for anything, we just need memSim to wait
                int x = hardDrives[hddSelect].read(dataSelect);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this read operation of Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

		//Now we have to prompt the user to pick a chunk of data
		rams[ramSelect].print();
		System.out.print(yellow+"Please select one of the above partitions: "+reset);
		int dataSelect = scnr.nextInt();

		//Then do the action
                int x = rams[ramSelect].read(dataSelect);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		ramSelect++;
                System.out.println("The time it took to do this read operation of RAM["+ramSelect+"] " + diff + " milliseconds");

            }
            else if(userSubSelect == 3){
                start = System.currentTimeMillis();

                //We don't need this return value for anything, we just need memSim to wait
		int x = cpu.read();

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
                System.out.println("The time it took to do this read operation of the CPU Cache was: " + diff + " milliseconds");
            }
        }
        else if(input == 3){
	    //We write data
            //Now we are going to ask the user where they want to write the data to
            System.out.println("Where would you like to write the data to?");
            System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print(yellow+"Please enter the number of your selection: "+reset);

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                int hddSelect = scnr.nextInt();
		hddSelect--;

		start = System.currentTimeMillis();

		System.out.print(yellow+"How many bytes would you like to write? "+reset);
		int in = scnr.nextInt();

		hardDrives[hddSelect].write(in);
		
                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this write operation of the Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

		System.out.println(yellow+"How many bytes would you like to write?"+reset);
		int in = scnr.nextInt();

		rams[ramSelect].write(in);
		
                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		ramSelect++;
                System.out.println("The time it took to do this write operation of the RAM["+ramSelect+"] was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 3){    
                start = System.currentTimeMillis();

		System.out.print(yellow+"How many bytes would you like to write?"+reset);
		int in = scnr.nextInt();

		cpu.write(in);
		
                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
                System.out.println("The time it took to do this write operation of the CPU Cache was: " + diff + " milliseconds");

            }
            else{
                System.out.println(red+"Your choice was not understood. Please only enter a number in the valid range"+reset);
            }
        }
	else if(input == 4){
	    //We delete data
	    //Now we are going to ask the user where they want to read the data from
            System.out.println("Where would you like to delete the data from?");
            System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print(yellow+"Please enter the number of your selection: "+reset);

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                int hddSelect = scnr.nextInt();
		hddSelect--;
		start = System.currentTimeMillis();


		//Now we have to prompt the user to pick a chunk of data
		hardDrives[hddSelect].print();
		System.out.print(yellow+"Please select one of the above partitions: "+reset);
		int dataSelect = scnr.nextInt();

		//Then do the action		
                hardDrives[hddSelect].delete(dataSelect);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this delete operation of Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print(yellow+"Please enter the number of your selection: "+reset);
                int ramSelect = scnr.nextInt();
		ramSelect--;
		
		//Now we have to prompt the user to pick a chunk of data
		rams[ramSelect].print();
		System.out.print(yellow+"Please select one of the above partitions: "+reset);
		int dataSelect = scnr.nextInt();

		//Then do the action
		start = System.currentTimeMillis();

                rams[ramSelect].delete(dataSelect);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		ramSelect++;
                System.out.println("The time it took to do this delete operation of RAM["+ramSelect+"] " + diff + " milliseconds");

            }
            else if(userSubSelect == 3){
		System.out.println("How many bytes would you like to delete?");
	        System.out.print(yellow+"Please enter the number of your selection: "+reset);
		int bytesDelete = scnr.nextInt();
		
                start = System.currentTimeMillis();

                cpu.delete(bytesDelete);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
                System.out.println("The time it took to do this delete operation of the CPU Cache was: " + diff + " milliseconds");
            }
	}
        else if(input == 5){
	    //We display the help message
	    displayHelp();
        }
        else if(input == 6){
	    //We exit the simulator
            done = true;
	    System.out.println(green+"Simulator exited successfully"+reset);
        }
        else{
            //We didn't understand the instruction, so let's just tell the user, and we will return
            //Then our parent will just recall us.
            System.out.println(red+"Your choice was not understood. Please only enter a number in the valid range."+reset);
            displayHelp();
        }

        return;
    }

    /**
    A helper method to display a user help menu
     **/
    private static void displayHelp(){
        System.out.println();
        System.out.println();
        System.out.println("Java Based Memory Latency Simulator");
        System.out.println("Developed by Pat Baumgardner, Adam Dachenhausen, Shah Syed");
        System.out.println("Supported commands: ");
        System.out.println("move");
        System.out.println("read");
        System.out.println("write");
	System.out.println("delete");
        System.out.println("help");
        System.out.println("exit");
        System.out.println("For help with a specific command type [command]");
	System.out.println("Type 'q' to quit the help menu");
	Scanner scnr = new Scanner(System.in);
	String input = scnr.next();
	while(!input.equalsIgnoreCase("q")){
	    //First display the info requested
	    System.out.print(cyan);
	    if(input == null){
		return;
	    }
	    else if(input.equalsIgnoreCase("move")){
		System.out.println("Moves data from one specified location to another");
	    }
	    else if(input.equalsIgnoreCase("read")){
		System.out.println("Reads data from specifed location");
	    }
	    else if(input.equalsIgnoreCase("write")){
		System.out.println("Writes data to specified location");
	    }
	    else if(input.equalsIgnoreCase("delete")){
		System.out.println("Deletes data from the specified component");
	    }
	    else if(input.equalsIgnoreCase("help")){
		System.out.println("Displays the help message");
	    }
	    else if(input.equalsIgnoreCase("exit")){
		System.out.println("Exits the simulator");
	    }
	    else{
		System.out.println(red+"Command not found");
	    }
	    System.out.print(reset);
	    //Then wait for new input
	    input = scnr.next();
	    
	}
        System.out.println();
        System.out.println();
        return;
    }
}
