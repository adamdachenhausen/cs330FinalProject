/*
  This is a simulation of a Hard Drive Disk
 */
class HDD{
    //The overall size of this
    int size;

    //The spaceLeft on this
    int spaceLeft;

    //The number of platters that equally divide the size
    int numPlatters;

    //Our array of platters
    platter[] platters;
    /**
       Sets up a hard drive to be added to the system.

       @param size, the size in bytes of this
       @param numPlatters, the number of single sided platters that this contains.
     **/
    public void initializeHDD(int size, int numPlatters){
	this.size=size;
	this.numPlatters=numPlatters;

	// Now we are going to make the virtual hard drive
	platters = new platter[numPlatters];
	
	for(int i=0; i<numPlatters; i++){
	    platters[i] = new platter(size/numPlatters);
	}
    }
}
