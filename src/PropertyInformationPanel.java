import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class PropertyInformationPanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel propertyInformation = new JPanel();
    private JSplitPane propertyCard = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private JTextPane propertyName = new JTextPane();
    private JTextArea propertyPrices = new JTextArea("Prices will go here.\n\n\n\n\n\n\n");
 
    //Change parameter to Property property when class is created and implemented
    public PropertyInformationPanel(){

        //Set up header of property card.
        StyledDocument doc = propertyName.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        propertyName.setBackground(Color.decode("#E53C00"));
        propertyName.setText("Property Name");
        propertyName.setEditable(false);

        //Set properties of JTextArea displaying properties price info then add to JPanel.
        propertyPrices.setLineWrap(true);
        propertyPrices.setEditable(false);
        propertyInformation.setLayout(new BorderLayout());
        propertyInformation.add(propertyPrices);

        //Top compononent is name of Property.
        propertyCard.setTopComponent(propertyName);
        //Bottom component is the properties pricing.
        propertyCard.setBottomComponent(propertyInformation);

        //Add fully completed property card and buttons to JFrame
        add(propertyCard);
     
        setVisible(true);
    }
}
