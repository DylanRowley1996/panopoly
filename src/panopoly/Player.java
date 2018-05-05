package panopoly;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
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
    private Locatable location = null;
    public boolean hasRolled = false;
    private BufferedImage characterIcon;
    private boolean isInJail = false;
    private int turnsInJail = 0;
    
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
    public void rolled(boolean trueOrFalse) {
    	hasRolled = trueOrFalse;
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
    
    public Locatable getLocation() {
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
	public void setLocation(Locatable locatable) {
		location = locatable;
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
	public void payPlayer(Playable p, int amount) {
		addToBalance(-amount);
		((Player)p).addToBalance(amount);
	}
	public void goToJail() {
		isInJail = true;
	}
	public void spendTurnInJail() {
		turnsInJail++;
		if(turnsInJail>=3) {
			isInJail = false;
			turnsInJail = 0;
		}
	}
	public boolean getJailStatus() {
		return isInJail;
	}
	public String textInJail() {
		String output = "";
		output += "you spend a quiet night in jail.\n";
		return output;
	}
}

