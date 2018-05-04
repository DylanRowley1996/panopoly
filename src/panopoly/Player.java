package panopoly;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import interfaces.Locatable;
import interfaces.Ownable;
import interfaces.Playable;
import locations.NamedLocation;
import locations.PrivateProperty;
import locations.PropertyGroup;


public class Player implements Playable {

    private String name = "";
    private int netWorth = 0;
    private ArrayList<PrivateProperty> properties = new ArrayList<PrivateProperty>();
    private ArrayList<PropertyGroup> monopolies = new ArrayList<PropertyGroup>();
    private ArrayList<PropertyGroup> mortgages = new ArrayList<PropertyGroup>();
    private int numImprovements = 0;
    
    private String pathToCharacterIcon = "";
    private NamedLocation location = null;
    public boolean hasRolled = false;
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
    public void rolled() {
    	hasRolled = true;
    }
    public int getNetWorth() {
        return netWorth;
    }
    
    //Property Actions
    public void buyProperty(PrivateProperty target) {
     	this.addToBalance(target.getPrice());
    	properties.add(target);
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
    
    public NamedLocation getLocation() {
    	return location;
    }

	@Override
	public String getIdentifier() {
		return name;
	}

	public Locatable getLeft() {
		return location.getLeft();
	}

	public Locatable getRight() {
		 return location.getRight();
	}
	public void setLocation(NamedLocation l) {
		location = l;
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
}

