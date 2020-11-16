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
    public int write(byte[] input){
	for(int i = 0; i<input.length;i++){
	    add(input[i]);
	}
	return 1;
    }
    public byte[] read(){
	return data;
    }
    private void add(byte input){
	ramLast++;

	if(ramLast == size){ramLast = 0;}
	data[ramLast] = input;
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
