/*
  This simulator of a Central Processing Unit with Cache
*/
class CPU extends Thread{

    //The number of cores in this
    int cores;

    //The first cache in this
    private byte[] cache1;

    //THe size of the first cache
    int cache1Size;
    /**
       Constructor for the CPU class
       @param numCores the number of cores that this should have (currently not supported)
       @param cacheSize the size of the cache of this
     **/
    public CPU(int numCores, int cacheSize){
     cores = numCores;
     cache1Size = cacheSize;
    }
    /**
       Reads the data in this' cache
       (Essentially just returns cache1)
       there should be no delay as this is stored on RAM, and the cache
       in a real system will be much faster
     **/
    public byte[] read(){
	return cache1
    }
    /**
       Writes input into the cache.
       If input is bigger, then we will recursively write to the cache,
       and the cache1 will hold the last cache1Size bytes

       @param input the data to be written to the cache
     **/
    public void write(byte[] input){
	
    }

    /**

     **/
    @Override
    public void run(){
        while(!memSim.done){
            
    }
    }
}
