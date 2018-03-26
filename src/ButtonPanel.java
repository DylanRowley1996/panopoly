import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ButtonPanel extends JPanel
{
    
	private static final long serialVersionUID = 1L;
	private JLabel statusLabel;

    public ButtonPanel(Player[] players)
    {
    	 setLayout(new GridLayout(4,2));

         statusLabel = new JLabel("");
         statusLabel.setVerticalAlignment(SwingConstants.BOTTOM);
         statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
         
         JButton rollButton = new JButton("ROLL");
         JButton sellButton = new JButton("SELL");
         JButton collectRentButton = new JButton("COLLECT RENT");
         JButton mortgageButton = new JButton("MORTGAGE");
         JButton redeemMortgageButton = new JButton("REDEEM MORTGAGE");
         JButton tradeButton = new JButton("TRADE");
         JButton overviewButton = new JButton("OVERVIEW");
         JButton assetsButton = new JButton("ASSETS");

         rollButton.addActionListener(e ->
             statusLabel.setText("Roll button clicked")
         );

         sellButton.addActionListener(e ->
                 statusLabel.setText("Sell button clicked.")
         );

         collectRentButton.addActionListener(e ->
                 statusLabel.setText("Collect rent button clicked.")
         );

         mortgageButton.addActionListener(e ->
                 statusLabel.setText("Mortgage button clicked.")

         );

         redeemMortgageButton.addActionListener(e ->
                 statusLabel.setText("Redeem mortgage button clicked.")
         );

         tradeButton.addActionListener(e ->
                 statusLabel.setText("Trade button clicked.")
         );

         overviewButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						 SwingUtilities.invokeLater(new Runnable() {
					            @Override
					            public void run() {
					                new Overview(players).setVisible(true);
					            }
					        });

					}
				 });
        		//e ->
               //  statusLabel.setText("Overview button clicked.")
        // );

         assetsButton.addActionListener(e ->
                 statusLabel.setText("Assets button clicked.")
         );

         add(rollButton);
         add(sellButton);
         add(collectRentButton);
         add(mortgageButton);
         add(redeemMortgageButton);         
         add(tradeButton);
         add(overviewButton);
         add(assetsButton);
    }

 
    
}