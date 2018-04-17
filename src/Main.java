import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Main {
    public static void main(String[] args) throws InvalidFormatException, IOException {
  
    	MCQ mcq = new MCQ();
    	
    	for(int i=0;i<100;i++){
        	System.out.println(mcq.createAddressAndTalkingPointQuestion());
        //	System.out.println(mcq.createVehicleQuestion());
    	}
    	
    	



    }
}
    

