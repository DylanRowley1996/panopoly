import java.util.ArrayList;

import interfaces.Locatable;
import interfaces.Playable;
import locations.NamedLocation;


//Test class used to test features of Overview.java
public class Player implements Playable {

    private String name = "";
    private int netWorth = 0;
    private String[] properties = new String[5];
    private String[] monopolies = new String[5];
    private String[] mortgages = new String[5];
    private String pathToCharacterIcon = "";
    private NamedLocation location = null;

    public Player(String name,int netWorth, String[] properties, String[] monopolies, String[] mortgages, String pathToIcon){
        this.name = name;
        this.netWorth = netWorth;
        this.properties = properties;
        this.monopolies = monopolies;
        this.mortgages = mortgages;
        this.pathToCharacterIcon = pathToIcon;
    }
    
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

    public String[] getProperties() {
        return properties;
    }

    public String[] getMonopolies() {
        return monopolies;
    }

    public String[] getMortgages() {
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
}

