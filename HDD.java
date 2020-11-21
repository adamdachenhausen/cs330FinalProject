
/*
This is a simulation of a Hard Drive Disk
 */
class HDD extends Thread{
    //The overall size of this
    int size;

    //The spaceLeft on this
    int spaceLeft;

    //Our array of data
    private dataBlock[] data;

    /**
    Sets up a hard drive to be added to the system.

    @param size, the size in bytes of this
     **/
    public HDD(int size){
        this.size=size;
        this.spaceLeft=size;
        int totalBlocks;
        if(size == 0){totalBlocks=0;}
        else if(size<dataBlock.WIDTH){totalBlocks = 1;}
        else{
            totalBlocks = size/dataBlock.WIDTH;
            if(size%dataBlock.WIDTH!=0){totalBlocks++;}
        }
        data = new dataBlock[totalBlocks];
    }

    /**
    Writes a given set of bytes to this.

    @param numBytes, the number of bytes to write to this

    @return 1 if the write was successful, 0 if not (ie not enough space)
     **/
    public int write(int numBytes)
    {
        //First let's check if we have enough raw space
        if(numBytes > spaceLeft){return 0;}

        //Now let's reserve our space and then write our data
        spaceLeft = spaceLeft - numBytes;

        //Now we call our helper method
        int totalBlocks;
        if(numBytes == 0){totalBlocks=0;}
        else if(numBytes<dataBlock.WIDTH){totalBlocks = 1;}
        else{
            totalBlocks = numBytes/dataBlock.WIDTH;
            if(numBytes%dataBlock.WIDTH!=0){totalBlocks++;}
        }
        return add(totalBlocks);
    }

    /**
    FIFO add method. Finds a chunk of memory at least numBlocks big, and stores
    numBlocks there.

    @param numBlocks the number of blocks that need to be added to this
    @return 1, if successful add, otherwise 0
     **/
    private int add(int numBlocks){
        int count = 0;

        //First we want to be able to loop through the entire data array
        for(int i=0;i<data.length;i++){
            //Then we check if each one is open
            if(data[i] == null){
                //Then we see if we have a hole big enough
                while(count != numBlocks && data[count] == null ){
                    count++;    
                }
                //Then we see if the hole is big enough
                if(count == numBlocks){
                    //Then we can add blocks starting at i, which has to be special
                    data[i]=new dataBlock(true,numBlocks);
                    count--;
                    i++;
                    while(count!=0){
                        data[i] = new dataBlock();
                        count--;
                        i++;
                    }
                    //Since we added the chunk, we can just return
                    return 1;
                }
                //If we got here, that means we don't have a hole big enough, so let's reset count

                count = i;

            }
        }
        return 0;

    }

    /**
    Reads data from this
    Precondition: index is a dataBlock where .start is true.
    @param index The index of the array where the data block starts
    @return the size of the chunk read, otherwise -1
     **/
    public int read(int index){

        //Loop through the data until we hit a null block or
        //If we have two blocks back-to-back, then the start flag
        //will be high, so we can stop reading our dataBlock
        if(data!=null){
            if(data[index]!=null){
                int output = data[index].size;
                while(index<data.length && data[index]!=null){
                    index++;  

                    try{
                        sleep(2);
                    }
                    catch(Exception e){

                    }

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
    Precondition: the index is a dataBlock where .start is true
    @param index, the start index of the block of data to delete.
     **/
    public void delete(int index){
        //Loop through the data until we hit a null block or
        //If we have two blocks back-to-back, then the start flag
        //will be high, so we can stop reading our dataBlock
        if(data!=null){
            while(index<data.length && data[index]!=null){
                //Delete the current block
                data[index] = null;

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
        System.out.println("****Data on this hard drive****");

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
        //First we wait a bit to emulate the hard drive getting up to speed

        //Then we wait for instructions
        while(!memSim.done){
            //System.out.println("In HDD");

        }
    }
}
