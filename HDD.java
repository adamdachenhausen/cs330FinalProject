
/*
  This is a simulation of a Hard Drive Disk
 */
class HDD extends Thread{
    //The overall size of this
    int size;

    //The spaceLeft on this
    int spaceLeft;

    //Our array of data
    private byte[] data;

    //The index of the last element added
    private int dataLast;
    
    /**
       Sets up a hard drive to be added to the system.

       @param size, the size in bytes of this
     **/
    public HDD(int size){
	this.size=size;
	data = new byte[size];
	dataLast=-1;
    }
    /**
       Writes a given set of bytes to this.

       @param input, an array of bytes to write to this

       @return 1 if the write was successful
     **/
    public int write(byte[] input)
    {
	for(int i = 0; i<input.length;i++){
	    add(input[i]);
	}
	return 1;
    }
    /**
       Reads data from this

       @return a byte array of the data read from this
     **/
    public byte[] read(){
	return data;
    }
    /**
       Adds one byte to this at the current pointer
       @param input the byte to add to this
    **/
    private void add(byte input){
	dataLast++;

	if(dataLast == size){dataLast = 0;}
	data[dataLast] = input;
    }
    /**
       Deletes data from this

       
     **/
    public void delete(){

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
