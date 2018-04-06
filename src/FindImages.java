import com.google.gson.*;
import java.io.BufferedReader;
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


public class FindImages {
	
	private GResults results;
	private String[] queries;
	private ArrayList<String> characters;
	
	public FindImages(ArrayList<String> characters){
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
	
	
	 
    void Search()  throws MalformedURLException, URISyntaxException, IOException {
    	  String key = "AIzaSyBsLxIF8LF3t3em5FSidHZMHtMg9AmHEDQ";
    	  String cx  = "008580275858431148289:yexe3mpvnwg";
    	      	  
    	 for(int i =0;i<queries.length;i++){
   		  URL url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+"&cx="+cx+"&q="+queries[i]+"&searchType=image&fileType=jpg");
       	  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       	  conn.setRequestMethod("GET");
       	  conn.setRequestProperty("Accept", "application/json");
         // conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB;     rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
          conn.addRequestProperty("User-Agent", "Mozilla/4.0");

       	  BufferedReader br = new BufferedReader(new InputStreamReader ( ( conn.getInputStream() ) ) );
       	  results = new Gson().fromJson(br, GResults.class);
       	  //String extenstion = getFileExtension(results.getThing(0).toString());
       	  //System.out.println("Extension: "+extenstion);
       	  
       	  String destinationFile = "savedImages/"+characters.get(i)+".jpg";
       	  saveImage(results.getThing(0).toString(), destinationFile);
       	  conn.disconnect();
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
    
   //https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
    private String getFileExtension(String fileName) {
        //String name = file.getName();
        try {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
    
}