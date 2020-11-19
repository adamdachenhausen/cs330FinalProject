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

    public RAM(int size){
        this.size = size;
        this.spaceLeft = size;

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
        spaceLeft =- input;

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
                //and skip those values by adding (count - 1) and i
                count -= 1;
                i += count;
                count = 0;
            }
        }
        return 0;
    }

    /**
    Reads data from this, and returns it
    Precondition the index is a datablock with .start = true
    @param the index of the starting array index
     **/
    public void read(int index){
        //Loop through the data until we hit a null block or
        //If we have two blocks back-to-back, then the start flag
        //will be high, so we can stop reading our dataBlock
        while(data[index]!=null || data[index].start==true){
            index++;
        }
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
            while(data[index]!=null || data[index].start==true){
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
                    System.out.println(i+": "+data[i].size+" bytes big");
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
