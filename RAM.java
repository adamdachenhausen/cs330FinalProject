/*
This is a simulation of Main System Memory aka Random Access Memory
 */
class RAM extends Thread{
    //The size in bytes of this
    int size;

    //The spaceLeft in this
    int spaceLeft;

    //The array to hold our data
    dataBlock[] data;

    //The time it takes to write 1 MB
    private int delay;

    /**
       Constructor for RAM object
       @param size the size of this
       @param ramDelay the time it take to write 1 MB
     **/
    public RAM(int size, int ramDelay){
        this.size = size;
        this.spaceLeft = size;
	delay = ramDelay;

        data = new dataBlock[size/dataBlock.WIDTH];
    }

    /**
    Writes and input byte array to this
    @param input the number of bytes to write
    @return 1, if write is successful, 0 if not (ie not enough space left)
     **/
    public int write(int input){
        //First let's check that we have enough space
        if(input > spaceLeft){return 0;}

        //Now let's reserve that space, and write our data
        spaceLeft = spaceLeft - input;

	try{
	    sleep(delay*(input/memSim.MB));
	}
	catch(Exception e){}
	
        //Now we call our helper method
        return add(input/dataBlock.WIDTH);

    }

    private int add(int numBlocks){
        int count = 0;

        //First we want to be able to loop through the entire data array
        for(int i=0;i<data.length;i++){
            //Then we check if each one is open
            if(data[i] == null){
                count = 1;
                //Then we see if we have a hole big enough
                while(data[count] == null && count != numBlocks){
                    count++;
                }
                //Then we see if the hole is big enough
                if(count == numBlocks){
                    //Then we can add blocks starting at i, which has to be special
                    data[i] = new dataBlock(true,numBlocks);
                    count--;
                    i++;
                    while(count!=0){
                        data[i] = new dataBlock();
                        count--;
                        i++;
                    }
                    //Since we aded the chunk, we can just return
                    return 1;
                }
                //If we got here, that means we don't have a hole big enough, so let's reset count
                count = 0;
            }
        }
        return 0;
    }

    /**
    Reads data from this, and returns it
    Precondition the index is a datablock with .start = true
    @param the index of the starting array index
    @return the size of the chunk read, or -1 if error
     **/
    public int read(int index){
        //Loop through the data until we hit a null block or
        //If we have two blocks back-to-back, then the start flag
        //will be high, so we can stop reading our dataBlock
        if(data!=null){
	    if(data[index]!=null){
		int output = data[index].size;
		try{
		    sleep(delay*(output/memSim.MB));
		}
		catch(Exception e){}
		while(index<data.length && data[index]!=null){
		    index++;  
		    
		    //Now we check the next position if it is a starter, and we can exit
		    if(data[index]!=null && data[index].start==true){
			break;
		    }
		}
		return output;
	    }
	}
	return -1;
    }
	
	/**
    Deletes data from this
    Precondition the index is a datablock with .start = true
    @param the index of the starting point to delete from
     **/
    public void delete(int index){
        //Loop through the data until we hit a null block
        //If we have two blocks back-to-back, then the start flag
        //will be high, so we can stop reading our dataBlock
        if(data!=null){
	    if(data[index]!=null){
		try{
		    sleep(delay*(data[index].size/memSim.MB));
		  }
		catch(Exception e){}
	    }
            while(index<data.length && data[index]!=null){
                //Delete the current block
                data[index]=null;

                //Then increment and free space
                index++;
                spaceLeft+=dataBlock.WIDTH;
            }
        }
    }

    /**
    Prints all the chunks(multiple dataBlocks) of data for the user to see what is on the disk.
    Useful for selecting what data to read, write and delete.
     **/
    public void print(){
        System.out.println("");
        System.out.println("****Data on this RAM Stick****");

        //First loop through the whole array
        if(data!=null){
            for(int i=0;i<data.length;i++){
                //Then if we encounter a dataBlock that is a start, print it
                if(data[i]!=null && data[i].start == true){
                    System.out.println(i+": "+data[i].size*dataBlock.WIDTH+" bytes big");
                }
            }
        }
        System.out.println("*******************************");
    }

    /**

     **/
    @Override
    public void run(){
        while(!memSim.done){
            //System.out.print("In Ram");
        }
    }
}
