
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
    public int write(byte[] input)
    {
	for(int i = 0; i<input.length;i++){
	    add(input[i]);
	}
	return 1;
    }
    public byte[] read(){
	return data;
    }
    private void add(byte input){
	dataLast++;

	if(dataLast == size){dataLast = 0;}
	data[dataLast] = input;
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
