package panopoly;
import java.util.ArrayList;

import interfaces.Locatable;
import interfaces.Playable;
import locations.NamedLocation;
import locations.PropertyGroup;


public class Player implements Playable {

    private String name = "";
    private int netWorth = 0;
    private ArrayList<NamedLocation> properties = new ArrayList<NamedLocation>();
    private ArrayList<PropertyGroup> monopolies = new ArrayList<PropertyGroup>();
    private ArrayList<PropertyGroup> mortgages = new ArrayList<PropertyGroup>();
    
    private String pathToCharacterIcon = "";
    private NamedLocation location = null;
    
    public Player(String name,String pathToCharacterIcon){
    	this.name = name;
        this.pathToCharacterIcon = pathToCharacterIcon;
    }

    public String getName(){
        return name;
    }

    public int getNetWorth() {
        return netWorth;
    }
    public void buyProperty(NamedLocation target) {
    	if(!properties.contains(target)) {
        	properties.add(target);
    	}  	
    }

    public ArrayList<NamedLocation> getProperties() {
        return properties;
    }

//    public ArrayList<NamedLocation> getMortgages() {
//    	mortgages.clear();
//    	for(NamedLocation currLocation:properties) {
//    		if(currLocation.isMortgaged) {
//    			mortgages.add(currLocation);
//    		}
//    	}
//        return mortgages;
//    }
//    
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
}

