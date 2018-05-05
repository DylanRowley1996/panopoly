package panopoly;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;
import javax.imageio.ImageIO;
import interfaces.Locatable;
import interfaces.Playable;
import locations.PrivateProperty;
import locations.PropertyGroup;


public class Player implements Playable {

    private String name = "";
    private int netWorth = 0;
    private ArrayList<PrivateProperty> properties = new ArrayList<PrivateProperty>();
    private ArrayList<PropertyGroup> monopolies = new ArrayList<PropertyGroup>();
    private ArrayList<PropertyGroup> mortgages = new ArrayList<PropertyGroup>();
    private int numImprovements = 0;
    private int getOutOfJailCard = 0;
    
    private String pathToCharacterIcon = "";
    private Locatable location = null;
    private boolean hasRolled = false;
    private boolean mustDeclareBankruptcy = false;
    
    private BufferedImage characterIcon;
    
    public Player(String name,String pathToCharacterIcon) throws IOException,NullPointerException{
    	this.name = name;
        this.pathToCharacterIcon = pathToCharacterIcon;
    }

    public String getName(){
        return name;
    }
    
    public BufferedImage getIcon() {
    	return characterIcon;
    }
    
    public void setIcon() throws IOException {
        characterIcon = ImageIO.read(new File(this.pathToCharacterIcon));
    }
    
    public boolean mustDeclareBankruptcy(){
    	return mustDeclareBankruptcy;
    }
    
    public boolean hasRolled() {
    	return hasRolled;
    }
    
    public void setRolled(boolean roll) {
    	hasRolled = roll;
    }
    
    public void setDeclareBankruptcy(boolean bankruptyStatus){
    	mustDeclareBankruptcy = bankruptyStatus;
    }
    
    public int getNetWorth() {
        return netWorth;
    }
    
    //Property Actions
    public void buyProperty(PrivateProperty target) {
     	this.addToBalance(target.getPrice());
     	target.setOwner(this);
    	properties.add(target);
    }
    
    //Used for Auctioning
  	public void addProperty(PrivateProperty property){
     	property.setOwner(this);
  		properties.add(property);
  	}

    public ArrayList<PrivateProperty> getProperties() {
        return properties;
    }
    
    public ArrayList<PropertyGroup> getMonopolies(){
    	return monopolies;
    }
    
    public ArrayList<PropertyGroup> getMortgages(){
    	return mortgages;
    }
    
    public String getPathForImageIcon(){
    	return this.pathToCharacterIcon;
    }
    
    public void setPathForImageIcon(String pathToIcon){
    	this.pathToCharacterIcon = pathToIcon;
    }
    
    public Locatable getLocation() {
    	return location;
    }

	@Override
	public String getIdentifier() {
		return name;
	}

	public Locatable getNextLoc() {
		return location.getNextLoc();
	}

	public Locatable getPrev() {
		 return location.getPrevLoc();
	}
	public void setLocation(Locatable loc) {
		location = loc;
	}
	
	public void addToBalance(int amount){
		netWorth += amount;
	}
	
	public void deductFromBalance(int amount){
		netWorth -= amount;
	}
	
	public void addImprovements(int amount) {
		numImprovements += amount;
	}
	
	public void removeImprovements(int amount) {
		numImprovements -= amount;
	}
	
	public int getNumImprovements() {
		return numImprovements;
	}
	
	public void setName(String filepath){
		name = FilenameUtils.getBaseName(filepath);	
	}
	
	public boolean hasJailCard() {
		return getOutOfJailCard>0;
	}
	
	public void addJailCard() {
		getOutOfJailCard++;
	}
	
	public void useJailCard() {
		getOutOfJailCard--;
	}
	
}

