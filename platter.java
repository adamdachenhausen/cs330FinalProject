/*
  A simulation of a single sided HDD platter
 */
class platter{
    //An array to hold our data
    byte[][] data;
    
    /**
       Constructor for a platter
     **/
    public platter(int size){
	//This will have to be changed, as we have a square array, but actually, more sectors
	//are present farther from the center of the platter
	data = new byte[size][size];
    }
}
