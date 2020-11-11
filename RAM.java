/*
  This is a simulation of Main System Memory aka Random Access Memory
 */
class RAM{
    //The size in bytes of this
    int size;
    //The array to hold our data
    byte[] data;
    
    public RAM(int size){
        this.size = size;
	data = new byte[size];
    }
}
