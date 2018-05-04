package panopoly;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

import java.awt.*;

public class HistoryLog extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int TEXT_AREA_HEIGHT = 10;
	private static final int TEXT_AREA_WIDTH = 10;
	private static final int FONT_SIZE = 14;

	private JTextArea textArea = new JTextArea(TEXT_AREA_HEIGHT, TEXT_AREA_WIDTH);
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private DefaultCaret caret = (DefaultCaret)textArea.getCaret();
	
	HistoryLog() {
		setMaximumSize(new Dimension(10, 10));
		//setBorder(LINE_END);
		textArea.setBorder(BorderFactory.createCompoundBorder(textArea.getBorder(), BorderFactory.createEmptyBorder(5,5,5,5)));
		textArea.setEditable(false);
		textArea.setFont(new Font("Serif", Font.BOLD, FONT_SIZE));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		setLayout(new BorderLayout());
		add(scrollPane);
		scrollPane.setViewportView(textArea);
		return;
	}
	
	public void displayHistory(String text) {
		textArea.append(text+"\n");
	}

	public JTextArea getTextArea(){
		return this.textArea;
	}
	
	public void refresh() {
		this.repaint();
		textArea.repaint();
	}
	
}

