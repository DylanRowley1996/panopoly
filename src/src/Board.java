import javax.swing.*;
import java.awt.*;

public class Board {
    private JFrame frameOne;
    private JPanel panelOne;

    private JButton buttonOne;
    private JLabel labelOne;

    public void create(){
        gui();
    }

    public void gui(){
        int changesSoIcanCommit;
        frameOne = new JFrame("FrameOne");
        frameOne.setVisible(true);
        frameOne.setSize(getScreenWidth(),getScreenHeight());
        frameOne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panelOne = new JPanel(new GridBagLayout());
        panelOne.setSize(getScreenWidth()/2,2*(getScreenHeight()/3));
        panelOne.setBackground(new Color(192,226,202));
        frameOne.add(panelOne);

        frameOne.add(labelOne);
    }
    public int getScreenWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        return width;
    }
    public int getScreenHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) screenSize.getHeight();
        return height;
    }
}
