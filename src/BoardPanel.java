import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardPanel extends JPanel
{
    private JFrame frame;
    private JPanel control;
    private JLabel statusLabel;

    private BoardPanel()
    {
        prepareGUI();
    }

    private void prepareGUI()
    {
        frame = new JFrame("Panopoly");
        frame.setSize(1200, 100);
        frame.setResizable(false);

        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });

        control = new JPanel();
        control.setLayout(new FlowLayout());

        statusLabel = new JLabel("");
        statusLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setSize(1200,50);

        frame.add(statusLabel);
        frame.add(control);
    }

    private void showButton()
    {
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

        overviewButton.addActionListener(e ->
                statusLabel.setText("Overview button clicked.")
        );

        assetsButton.addActionListener(e ->
                statusLabel.setText("Assets button clicked.")
        );

        control.add(rollButton);
        control.add(sellButton);
        control.add(collectRentButton);
        control.add(mortgageButton);
        control.add(redeemMortgageButton);
        control.add(tradeButton);
        control.add(overviewButton);
        control.add(assetsButton);

        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        BoardPanel buttonTest = new BoardPanel();
        buttonTest.showButton();
    }
}
