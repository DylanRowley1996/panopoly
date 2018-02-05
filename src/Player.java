import java.util.ArrayList;


//Test class used to test features of Overview.java
public class Player {

    private String name = "";
    private int netWorth = 0;
    private String[] properties = new String[5];
    private String[] monopolies = new String[5];
    private String[] mortgages = new String[5];

    public Player(String name,int netWorth, String[] properties, String[] monopolies, String[] mortgages){
        this.name = name;
        this.netWorth = netWorth;
        this.properties = properties;
        this.monopolies = monopolies;
        this.mortgages = mortgages;
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
}

