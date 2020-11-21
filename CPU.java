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

    //The time it takes to write 1 MB
    int delay;
    /**
       Constructor for the CPU class
       @param numCores the number of cores that this should have (currently not supported)
       @param cacheSize the size of the cache of this
       @param cacheDelay, the time it takes for this to write 1 MB
     **/
    public CPU(int numCores, int cacheSize, int cacheDelay){
     cores = numCores;
     cache1 = new byte[cacheSize];
     cache1Size = cacheSize;
     delay = cacheDelay;
     cache1Last = -1; //We hold the index of the last item in the array
    }
    /**
       Reads the data in this' cache
       (Essentially just returns cache1)
       there should be no delay as this is stored on RAM, and the cache
       in a real system will be much faster

       @return the size of the cache read
     **/
    public int read(){
	try{
	    sleep(delay*(cache1Size/memSim.MB));
	}
	catch(Exception e){}
	return cache1Size;
    }
    /**
       Writes input into the cache.
       If input is bigger, then we will iteratively write to the cache,
       and the cache1 will hold the last cache1Size bytes.

       There will also not be any delay (other than overhead) as these are the
       fastest memory locations.

       @param numBytes, the number of bytes to be written to the cache
       @return 1 if the method is done, 0 if error occurs
     **/
    public int write(int numBytes){
	try{
	    sleep(delay*(cache1Size/memSim.MB));
	}
	catch(Exception e){}
	for(int i = 0; i<numBytes;i++){
	    add((byte)0);
	}
	return 1;
    }
    /**
       Writes one byte to this, at a current pointer so we will always
       have the x most current bytes
     **/
    private void add(byte input){
	cache1Last++;
	
	if(cache1Last == cache1Size){cache1Last = 0;}
	cache1[cache1Last] = input;
    }
    /**
       Deletes data from this, by moving the tail pointer forward

       @param numBytes the number of bytes to be deleted, if greater than 
       cache1Size, then only cache1Size bytes are deleted
     **/
    public void delete(int numBytes){
	//First some error checking
	if(numBytes>cache1Size){numBytes=cache1Size;}
	if(numBytes<0){numBytes=0;}

	for(int i=0; i<numBytes;i++){
	    cache1Last++;
	}
	try{
	    sleep(delay*(cache1Size/memSim.MB));
	}
	catch(Exception e){}
    }
    /**

     **/
    @Override
    public void run(){
        while(!memSim.done){
            
    }
    }
}
