package panopoly;
import com.google.gson.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

/*
 *This class searches the web for images relating to character names
 */
public class FindImages {
	
	private GResults results;
	private String[] queries;
	private ArrayList<String> characters;
	private int noOfPlayers = 0;
	
	public FindImages(ArrayList<String> characters, int noOfPlayers){
		this.noOfPlayers = noOfPlayers;
		this.characters = characters;
		queries = new String[characters.size()];
		createQueries(characters);
	}
	
	//Removes spaces between characters names and replaces with '+'
	private void createQueries(ArrayList<String> characters){
		List<String> separatedNames;
		
		 for(int i=0;i<characters.size();i++){
			 
			 //Split characters names on space
        	 separatedNames =  Arrays.asList(characters.get(i).split(" "));
        	 String string = "";
        	 
        	 //Replace the space with a '+'
        	 for(int j=0;j<separatedNames.size();j++){
        		 string += separatedNames.get(j) + "+";
        	 }
        	 
        	 //Assign the newly created string to the array we'll use to query.
        	 queries[i] = string;
        	 
        	 System.out.println("Query: "+queries[i]);
         }
	}
	
	
	 
    void searchForCharacterImages()  throws MalformedURLException, URISyntaxException, IOException {
    	  String key = "AIzaSyBsLxIF8LF3t3em5FSidHZMHtMg9AmHEDQ";
    	  String cx  = "008580275858431148289:yexe3mpvnwg";
    	      	  
    	 for(int i =0;i<queries.length;i++){
    		 try{ 
    		  URL url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+"&cx="+cx+"&q="+queries[i]+"&searchType=image&fileType=jpg");
          	  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
           	  conn.setRequestMethod("GET");
           	  conn.setRequestProperty("Accept", "application/json");
              conn.addRequestProperty("User-Agent", "Mozilla/4.0");

           	  BufferedReader br = new BufferedReader(new InputStreamReader ( ( conn.getInputStream() ) ) );
           	  results = new Gson().fromJson(br, GResults.class);
           	  
           	  String destinationFile = "savedImages/"+characters.get(i)+".jpg";
           	  saveImage(results.getThing(0).toString(), destinationFile);
           	  conn.disconnect();
           }catch(Exception exception){
        	   
           }
   		
       	  
   	  }
 
    }
  
    
    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}
      
    //https://stackoverflow.com/questions/12620158/save-resized-image-java
    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int IMG_WIDTH, int IMG_HEIGHT) {
	    BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
	    Graphics2D g = resizedImage.createGraphics();
	    g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
	    g.dispose();
	    return resizedImage;
	}
    
    //Scales all images so they don't have to be constantly rescaled during the game
    public void resizeAllImages() throws IOException{
    	
    	//TODO - Change so it only loops on number of players
    	for(int i=0;i<noOfPlayers;i++){
    		 BufferedImage originalImage = ImageIO.read(new File("savedImages/"+characters.get(i)+".jpg"));//change path to where file is located
             int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

             BufferedImage resizeImageJpg = resizeImage(originalImage, type, 150, 150);
             ImageIO.write(resizeImageJpg, "jpg", new File("savedImages/"+characters.get(i)+".jpg")); //change path where you want it saved
    	}
    	
    }
    
}