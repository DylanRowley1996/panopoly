package panopoly;
import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel
{
    
	private static final long serialVersionUID = 1L;
	private JButton rollButton = new JButton("ROLL");
	private JButton buyButton = new JButton("BUY");
    private JButton auctionButton = new JButton("AUCTION");
    private JButton sellButton = new JButton("SELL");
    private JButton bankruptyButton = new JButton("BANKRUPT");
    private JButton mortgageButton = new JButton("MORTGAGE");
    private JButton redeemMortgageButton = new JButton("REDEEM MORTGAGE");
    private JButton tradeButton = new JButton("TRADE");
    private JButton overviewButton = new JButton("OVERVIEW");
    private JButton quitGameButton = new JButton("QUIT GAME");
    private JButton finishTurnButton = new JButton("FINISH TURN");
    
    public ButtonPanel()
    {
    	
    	setLayout(new GridBagLayout());
    	setMaximumSize(new Dimension(5,5));
    	GridBagConstraints c = new GridBagConstraints();
    	c.weightx = .5;
        c.weighty = .5;
    	c.fill = GridBagConstraints.BOTH;
    	
    	//First Row
    	c.gridwidth = 1;
    	c.gridx = 0;
    	c.gridy = 0;
    	add(buyButton, c);
    	
    	c.gridx = 1;
    	c.gridy = 0;
    	add(sellButton, c);
    	
    	c.gridx = 2;
    	c.gridy = 0;
    	add(auctionButton, c);
    	
    	c.gridx = 3;
    	c.gridy = 0;
    	add(tradeButton, c);
    	
    	//Second row
    	c.gridwidth = 2;
		c.gridx = 0;
    	c.gridy = 1;
    	add(mortgageButton, c);
    	
    	c.gridx = 2;
    	c.gridy = 1;
    	add(redeemMortgageButton, c);
    	
    	c.gridwidth = 2;
    	c.gridx = 2;
    	c.gridy = 1;
    	add(bankruptyButton, c);
    	
    	//Third Row
    	c.gridwidth = 2;
     	c.gridx = 0;
    	c.gridy = 2;
    	add(quitGameButton, c);
    	
    	c.gridx = 2;
    	c.gridy = 2;
    	add(finishTurnButton, c);
    	
    	//Fourth Row
    	c.gridwidth = 4;
     	c.gridx = 0;
    	c.gridy = 3;
    	add(overviewButton, c);
    	
    	//Fifth row
    	c.gridx = 0;
    	c.gridy = 4;
    	add(rollButton, c);
    	
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JButton getRollButton() {
		return rollButton;
	}

	public JButton getBuyButton() {
		return buyButton;
	}

	public JButton getAuctionButton() {
		return auctionButton;
	}

	public JButton getSellButton() {
		return sellButton;
	}

	public JButton getBankruptyButton() {
		return bankruptyButton;
	}

	public JButton getMortgageButton() {
		return mortgageButton;
	}

	public JButton getRedeemMortgageButton() {
		return redeemMortgageButton;
	}

	public JButton getTradeButton() {
		return tradeButton;
	}

	public JButton getOverviewButton() {
		return overviewButton;
	}

	public JButton getQuitGameButton() {
		return quitGameButton;
	}
	
	public JButton getFinishTurnButton(){
		return finishTurnButton;
	}
	
}