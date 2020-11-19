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
        memSim sim = initialUserPrompt();

        while(!done){
            sim.promptTree();
        }
    }

    /**
    A constructor for the memSim object
     **/
    public memSim(int numHDD, int hDDSize, int numRam, int rAMSize, int cacheSize){
        //First, we need to allow everything to know that we are starting up, aka not done
        done = false;

        //Let's initialize the number of harddrives, and their size
        hardDrives = new HDD[numHDD];

        for(int i = 0; i < numHDD; i++){
            hardDrives[i] = new HDD(hDDSize);
            hardDrives[i].start();
        }

        //Now let's initialize our RAM
        rams = new RAM[numRam];

        for(int i = 0; i < numRam; i++){
            rams[i] = new RAM(rAMSize);
            rams[i].start();
        }

        //Now let's initialize our cacheSize
        cpu = new CPU(1, cacheSize);
        cpu.start();
    }

    /**
    Prompts the user for specifics about the system.
    Then initializes the system and returns it

    @return a memSim object specified by the user's requests
     **/
    private static memSim initialUserPrompt(){
        Scanner scnr = new Scanner(System.in);

	//Let's first ask the user if they want to make their own system, or load one
	System.out.println("Would you like to load a system, or configure your own?");
	System.out.println("Type \u001B[33mLOAD\u001B[0m or \u001B[33mNEW\u001B[0m: ");
	String choice = scnr.next();
	if(choice.equalsIgnoreCase("Load")){
	    //Firt we display the choices that the user can choose from
	    System.out.println("\u001B[35mSystem 1:\u001B[0m");
	    System.out.println("\t2 HDDs with size: 10240 bytes each");
	    System.out.println("\t2 Sticks of RAM with size: 5120 bytes each");
	    System.out.println("\t CPU Cache size: 1024 bytes");
	    System.out.println("\tThis system has 3 partitions of data written to HDD 1, each 1024 bytes,");
	    System.out.println("\tand 2 partitions of data written to HDD 2, each 2048 bytes,");
	    System.out.println("\tand 1 partition written to each RAM stick, 2048 bytes big,");
	    System.out.println("\tand there is nothing in the cache.");
	    
	    System.out.println("\u001B[35mSystem 2:\u001B[0m");
	    System.out.println("\t1 HDD with size: 102400 bytes");
	    System.out.println("\t1 Stick of RAM with size: 51200 bytes");
	    System.out.println("\t CPU Cache size: 2048 bytes");
	    System.out.println("\tThis system has 10 partitions of 1024 bytes, and 5 of 2048 bytes written to the hard drive,");
	    System.out.println("\tand 3 partitions of 1024 bytes, and 1 of 2048 bytes written to the RAM,");
	    System.out.println("\tand there is nothing in the cache.");
	    
	    System.out.println("\u001B[35mSystem 3:\u001B[0m");
	    System.out.println("\t1 HDD with size: 1024000 bytes");
	    System.out.println("\t1 Stick of RAM with size: 51200 bytes");
	    System.out.println("\t CPU Cache size: 2048 bytes");
	    System.out.println("\tThere is no data written to any of the components in this system.");
	    //Now we prompt the user to load a default system
	    System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
	    int loadSelect = scnr.nextInt();
	    if(loadSelect == 2){
		//First initialize an empty memSim
		memSim finger = new memSim(1,102400,1,51200,2048);

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
		return new memSim(1024000,1024000,1,51200,2048);
	    }
	    else{
		//If we didn't understand, but the user wants to load, then we will load 1,
		//or if the user just wants to load 1

		//First initialize an empty memSim
		memSim finger = new memSim(2,102400,2,51200,1024);

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
		    System.out.println("\u001B[31mCould not understand your choice, reverting to creating a new system\u001B[0m");
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
	    
	    System.out.println("Finally, how big (in bytes) would you like your CPU cache to be?");
	    input = scnr.nextLine();
	    int cacheSize = Integer.parseInt(input);
	    
	    //Let's print the system config
	    System.out.println();
	    System.out.println("***********System Config**********");
	    System.out.println(numHDD+" number of hard drives, each "+hDDSize+" bytes big.");
	    System.out.println(numRAM+" sticks of RAM, each "+rAMSize+" bytes big.");
	    System.out.println("CPU cache is " + cacheSize + " bytes big.");
	    System.out.println("**********************************");
	    System.out.println();

	
	    //And finally initialize the system
	    return new memSim(numHDD,hDDSize,numRAM,rAMSize,cacheSize);
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
        System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");

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
            System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");

            int userSubSelect1 = scnr.nextInt();
	    int userSubSelect2 = userSubSelect1;
	    int dataSelect1 = -1;
	    int dataSelect2 = -1;

	    if(userSubSelect1 == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int hddSelect = scnr.nextInt();
		hddSelect--;

		//Now we have to prompt the user to pick a chunk of data
		hardDrives[hddSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
		dataSelect1 = scnr.nextInt();

            }
            else if(userSubSelect1 == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		
		//Now we have to prompt the user to pick a chunk of data
		rams[ramSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
		dataSelect1 = scnr.nextInt();

            }
            else if(userSubSelect1 == 3){
		dataSelect1 = 1;
            }
	    
	    //Now we prompt where the user wants to put this data
	    while(userSubSelect1 == userSubSelect2){
	    System.out.println("Where would you like to read the data from?");
	    System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");

	    userSubSelect2 = scnr.nextInt();

	    if(userSubSelect2 == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int hddSelect = scnr.nextInt();
		hddSelect--;

		//Now we have to prompt the user to pick a chunk of data
		hardDrives[hddSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
		dataSelect2 = scnr.nextInt();

            }
            else if(userSubSelect2 == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		
		//Now we have to prompt the user to pick a chunk of data
		rams[ramSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
		dataSelect2 = scnr.nextInt();

            }
            else if(userSubSelect2 == 3){
		dataSelect2 = 1;
            }
	    
	    //We make sure that the user didn't try to move from A to A
	    if(userSubSelect1==userSubSelect2 && dataSelect1==dataSelect2){
		System.out.println("\u001B[41mError, you cannot move data from one place to the same place. Please try again\u001B[0m");
	    }
	    }
	    
            start = System.currentTimeMillis();
            //Here we are going to process where the user wants to move the data from and to
	    
	    
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
            System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int hddSelect = scnr.nextInt();
		hddSelect--;

		//Now we have to prompt the user to pick a chunk of data
		hardDrives[hddSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
		int dataSelect = scnr.nextInt();

		//Then do the action
		start = System.currentTimeMillis();

                hardDrives[hddSelect].read(dataSelect);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this read operation of Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

		//Now we have to prompt the user to pick a chunk of data
		rams[ramSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
		int dataSelect = scnr.nextInt();

		//Then do the action
                rams[ramSelect].read(dataSelect);

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		ramSelect++;
                System.out.println("The time it took to do this read operation of RAM["+ramSelect+"] " + diff + " milliseconds");

            }
            else if(userSubSelect == 3){
                start = System.currentTimeMillis();

                cpu.read();

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
            System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int hddSelect = scnr.nextInt();
		hddSelect--;

		start = System.currentTimeMillis();

		System.out.print("\u001B[33mHow many bytes would you like to write?\u001B[0m");
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
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

		System.out.println("How many bytes would you like to write?");
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

		System.out.print("\u001B[33mHow many bytes would you like to write?\u001B[0");
		int in = scnr.nextInt();

		cpu.write(new byte[in]);
		
                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
                System.out.println("The time it took to do this write operation of the CPU Cache was: " + diff + " milliseconds");

            }
            else{
                System.out.println("Your choice was not understood. Please only enter a number in the valid range");
            }
        }
	else if(input == 4){
	    //We delete data
	    //Now we are going to ask the user where they want to read the data from
            System.out.println("Where would you like to delete the data from?");
            System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int hddSelect = scnr.nextInt();
		hddSelect--;
		start = System.currentTimeMillis();


		//Now we have to prompt the user to pick a chunk of data
		hardDrives[hddSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
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
                System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		
		//Now we have to prompt the user to pick a chunk of data
		rams[ramSelect].print();
		System.out.print("\u001B[33mPlease select one of the above partitions\u001B[0m");
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
	        System.out.print("\u001B[33mPlease enter the number of your selection: \u001B[0m");
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
	    System.out.println("\u001B[32mSimulator exited successfully\u001B[0m");
        }
        else{
            //We didn't understand the instruction, so let's just tell the user, and we will return
            //Then our parent will just recall us.
            System.out.println("\u001B[41mYour choice was not understood. Please only enter a number in the valid range\u001B[0m");
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
        System.out.println("help");
        System.out.println("exit");
        System.out.println("For help with a specific command type [command]");
	System.out.println("Type 'q' to quit the help menu");
	Scanner scnr = new Scanner(System.in);
	String input = scnr.next();
	while(!input.equalsIgnoreCase("q")){
	    //First display the info requested
	    System.out.print("\u001B[36m");
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
		System.out.println("Deletes data from the specified drive");
	    }
	    else if(input.equalsIgnoreCase("help")){
		System.out.println("Displays the help message");
	    }
	    else if(input.equalsIgnoreCase("exit")){
		System.out.println("Exits the simulator");
	    }
	    else{
		System.out.println("\u001B[31mCommand not found");
	    }
	    System.out.print("\u001B[0m");
	    //Then wait for new input
	    input = scnr.next();
	    
	}
        System.out.println();
        System.out.println();
        return;
    }
}
