/*
  This simulator of a Central Processing Unit with Cache
*/
class CPU extends Thread{

    //The number of cores in this
    int cores;

    //The first cache in this
    private byte[] cache1;

    //The index of the last element added
    private int cache1Last;
    
    //THe size of the first cache
    int cache1Size;
    /**
       Constructor for the CPU class
       @param numCores the number of cores that this should have (currently not supported)
       @param cacheSize the size of the cache of this
     **/
    public CPU(int numCores, int cacheSize){
     cores = numCores;
     cache1 = new byte[cacheSize];
     cache1Size = cacheSize;
     cache1Last = -1; //We hold the index of the last item in the array
    }
    /**
       Reads the data in this' cache
       (Essentially just returns cache1)
       there should be no delay as this is stored on RAM, and the cache
       in a real system will be much faster
     **/
    public byte[] read(){
	return cache1;
    }
    /**
       Writes input into the cache.
       If input is bigger, then we will iteratively write to the cache,
       and the cache1 will hold the last cache1Size bytes.

       There will also not be any delay (other than overhead) as these are the
       fastest memory locations.

       @param input the data to be written to the cache
       @return 1 if the method is done, 0 if error occurs
     **/
    public int write(byte[] input){
	for(int i = 0; i<input.length;i++){
	    add(input[i]);
	}
	return 1;
    }
    private void add(byte input){
	cache1Last++;
	
	if(cache1Last == cache1Size){cache1Last = 0;}
	cache1[cache1Last] = input;
    }

    /**

     **/
    @Override
    public void run(){
        while(!memSim.done){
            
    }
    }
}
