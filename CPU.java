/*
  This simulator of a Central Processing Unit with Cache
*/
class CPU implements Runnable{

    //The number of cores in this
    int cores;

    //The first cache in this
    byte[] cache1;

    //THe size of the first cache
    int cache1Size;
    public CPU(int numCores, int cacheSize){
     cores = numCores;
     cache1Size = cacheSize;
     this.run();
    }

    /**

     **/
    @Override
    public void run(){
	System.out.println("Hello");
    }
}
