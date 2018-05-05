package panopoly;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ButtonPanel extends JPanel
{
    
	private static final long serialVersionUID = 1L;
	ImageIcon imageIcon = new ImageIcon("gameImages/diceImage.png"); // load the image to a imageIcon
	Image image1 = imageIcon.getImage();
	Image newimg = image1.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
	ImageIcon imageIcon1 = new ImageIcon(newimg); 
	private JButton rollButton = new JButton(imageIcon1);
	private JButton buyButton = new JButton("BUY");
    private JButton auctionButton = new JButton("AUCTION");
    private JButton sellButton = new JButton("SELL");
    private JButton collectRentButton = new JButton("COLLECT RENT");
    private JButton mortgageButton = new JButton("MORTGAGE");
    private JButton redeemMortgageButton = new JButton("REDEEM MORTGAGE");
    private JButton tradeButton = new JButton("TRADE");
    private JButton overviewButton = new JButton("OVERVIEW");
    private JButton assetsButton = new JButton("ASSETS");
    private JButton finishTurnButton = new JButton("FINISH TURN");
    
    public ButtonPanel()
    {
    	 setLayout(new GridLayout(5,2));
    	 setMaximumSize(new Dimension(5,5));
         add(rollButton);
         add(sellButton);
         add(buyButton);
         add(auctionButton);
         add(collectRentButton);
         add(mortgageButton);
         add(redeemMortgageButton);         
         add(tradeButton);
         add(overviewButton);
         //add(assetsButton);
         add(finishTurnButton);
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

	public JButton getCollectRentButton() {
		return collectRentButton;
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

	public JButton getAssetsButton() {
		return assetsButton;
	}
	
	public JButton getFinishTurnButton(){
		return finishTurnButton;
	}
	
}