/*
  Represents a block of data on a HDD.
  
 */
class dataBlock{

    //The size of this block of data in bytes
    public static int WIDTH = 1024;

    //A flag if this is the start of a chunk (many blocks) of data
    boolean start;

    //The size of the chunk (multiple blocks) of data that this starts
    int size;
    
    /**
       Constructor for a generic dataBlock
       
    **/
    public dataBlock(){
	start = false;
    }
    /**
       Constructor for a starter dataBlock for a chunk of data
       @param start, set to true if this is the head of a series of blocks
       @param size the size of this chunk that this heads (including this)
     **/
    public dataBlock(boolean start, int size){
	this.start=start;
	this.size=size;
    }
}
