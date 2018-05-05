package panopoly;
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
    private JTextArea propertyInfo = new JTextArea();
 
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
        propertyName.setMaximumSize(new Dimension(130, 40));
        propertyName.setPreferredSize(new Dimension(130,40));
        propertyName.setStyledDocument(doc);

        //Set properties of JTextArea displaying properties price info then add to JPanel.
        propertyInfo.setLineWrap(true);
        propertyInfo.setWrapStyleWord(true);
        propertyInfo.setEditable(false);
        propertyInfo.setPreferredSize(new Dimension(150,200));
        propertyInformation.setLayout(new BorderLayout());
        propertyInformation.add(propertyInfo);

        //Top compononent is name of Property.
        propertyCard.setTopComponent(propertyName);
        //propertyInformation.setPreferredSize(new Dimension(20,20));
        //Bottom component is the properties pricing.
        propertyCard.setBottomComponent(propertyInformation);
        //propertyCard.setPreferredSize(new Dimension(150, 200));

        //Add fully completed property card and buttons to JFrame
        add(propertyCard);
        setPreferredSize(new Dimension(150,250));

        setVisible(true);
    }
    
    public JTextPane getPropNamePane() {
    	return propertyName;
    }
    
    public JTextArea getPropInfo() {
    	return propertyInfo;
    }
}
