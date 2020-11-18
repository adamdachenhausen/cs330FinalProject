/*
  This is a simulation of Main System Memory aka Random Access Memory
 */
class RAM extends Thread{
    //The size in bytes of this
    int size;
    //The array to hold our data
    byte[] data;

    //The index of the last element added
    private int ramLast;
    
    public RAM(int size){
        this.size = size;
	data = new byte[size];
	ramLast=-1;
    }
    /**
       Writes and input byte array to this
       @param input a byte array to write to this
       @return 1, if write is successful
     **/
    public int write(byte[] input){
	for(int i = 0; i<input.length;i++){
	    add(input[i]);
	}
	return 1;
    }
    /**
       Reads data from this, and returns it
       @return the data read from this, in a byte array form
     **/
    public byte[] read(){
	return data;
    }
    /**
       Adds one byte to this.
       @param input, the byte to write
     **/
    private void add(byte input){
	ramLast++;

	if(ramLast == size){ramLast = 0;}
	data[ramLast] = input;
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
        while(!memSim.done){
            //System.out.print("In Ram");
    }
    }
}
