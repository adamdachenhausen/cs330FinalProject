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
	System.out.println(numHDD+" number of hard drives, each "+hddSize+" bytes big.");
	System.out.println(numRAM+" sticks of RAM, each "+rAMSize+" bytes big.");
	System.out.println("CPU cache is " + cacheSize + " bytes big.");
	System.out.println("**********************************");
	System.out.println();

	    
        //And finally initialize the system
        return new memSim(numHDD,hDDSize,numRAM,rAMSize,cacheSize);
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
        System.out.println("4.\tHelp");
        System.out.println("5.\tExit");
        System.out.print("Please enter the number of your selection: ");

        //Then wait for their response and tokenize it
        Scanner scnr = new Scanner(System.in);
        int input = scnr.nextInt();

        //Used for determining how long a command took to run
        long start;
        long end;
        long diff;

        //Process the response
        if(input == 1){
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
            System.out.print("Please enter the number of your selection: ");

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("Please enter the number of your selection: ");
                int hddSelect = scnr.nextInt();
		hddSelect--;
		start = System.currentTimeMillis();

                hardDrives[hddSelect].read();

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this read operation of Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print("Please enter the number of your selection: ");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

                rams[ramSelect].read();

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
            System.out.println("Where would you like to write the data from?");
            System.out.println("1.\tHard Drive");
            System.out.println("2.\tRAM");
            System.out.println("3.\tCPU Cache");
            System.out.print("Please enter the number of your selection: ");

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("Please enter the number of your selection: ");
                int hddSelect = scnr.nextInt();
		hddSelect--;

		start = System.currentTimeMillis();

		System.out.println("How many bytes would you like to write?");
		int in = scnr.nextInt();

		hardDrives[hddSelect].write(new byte[in]);
		
                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this write operation of the Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print("Please enter the number of your selection: ");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

		System.out.println("How many bytes would you like to write?");
		int in = scnr.nextInt();

		rams[ramSelect].write(new byte[in]);
		
                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		ramSelect++;
                System.out.println("The time it took to do this write operation of the RAM["+ramSelect+"] was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 3){    
                start = System.currentTimeMillis();

		System.out.println("How many bytes would you like to write?");
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
            System.out.print("Please enter the number of your selection: ");

            int userSubSelect = scnr.nextInt();

            if(userSubSelect == 1){
                System.out.println("Please choose a drive from 1 - " + hardDrives.length);
                System.out.print("Please enter the number of your selection: ");
                int hddSelect = scnr.nextInt();
		hddSelect--;
		start = System.currentTimeMillis();

                hardDrives[hddSelect].delete();

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		hddSelect++;
                System.out.println("The time it took to do this delete operation of Hard Drive #"+hddSelect+" was: " + diff + " milliseconds");

            }
            else if(userSubSelect == 2){
                System.out.println("Please choose a stick of ram from 1 - " + rams.length);
                System.out.print("Please enter the number of your selection: ");
                int ramSelect = scnr.nextInt();
		ramSelect--;
		start = System.currentTimeMillis();

                rams[ramSelect].delete();

                //Then we can report the statistics
                end = System.currentTimeMillis();

                diff = end - start;
		ramSelect++;
                System.out.println("The time it took to do this delete operation of RAM["+ramSelect+"] " + diff + " milliseconds");

            }
            else if(userSubSelect == 3){
		System.out.println("How many bytes would you like to delete?");
		System.out.print("Please enter the number of your selection: ");
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
	    
        }
        else if(input == 6){
	    //We exit the simulator
            done = true;
        }
        else{
            //We didn't understand the instruction, so let's just tell the user, and we will return
            //Then our parent will just recall us.
            System.out.println("Your choice was not understood. Please only enter a number in the valid range");
            displayHelp("");
        }

        return;
    }

    /**
    A helper method to display a user help menu
     **/
    private static void displayHelp(String input){
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
        System.out.println("For help with a specific command type 'help [command]'");
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
        else if(input.equalsIgnoreCase("help")){
            System.out.println("Displays the help message");
        }
        else if(input.equalsIgnoreCase("exit")){
            System.out.println("Exits the simulator");
        }
        else{
            System.out.println("Command not found");
        }
        System.out.println();
        System.out.println();
        return;
    }
}
