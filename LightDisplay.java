import java.awt.event.*;
import javax.swing.*;
/**
 * LightDisplay is a object that displays a visual lightbulb to the screen 
 * representing the flow of signals across the wire. This class contains
 * the front end logic for how the interface operates. It takes a LightPanel
 * object as a parameter and visualizes it.
 */
public class LightDisplay extends Thread implements ActionListener {
	private LightPanel panel;
	private ImageIcon lightOffIcon;
	private ImageIcon lightOnIcon;
	private JLabel lightLabel;

	/**
	 * LightDisplay(panel) is a constructor for a LightDisplay.
	 * It creates a visualization for a LightPanel.
	 * @param panel  the LightPanel connected to the LightDisplay
	 */
	public LightDisplay(LightPanel panel) {
		this.panel = panel;

		JFrame frame = new JFrame();
		frame.setTitle(panel.toString());

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.LINE_AXIS));

		lightOffIcon = new ImageIcon("lightoff.gif");
		lightOnIcon = new ImageIcon("lighton.gif");

		lightLabel = new JLabel(lightOffIcon);
		lightLabel.setBorder(BorderFactory.createEtchedBorder());
		frame.getContentPane().add(lightLabel);

		JButton onButton = new JButton("ON");
		onButton.setMnemonic(KeyEvent.VK_N);
		onButton.setActionCommand("on");
		onButton.addActionListener(this);
		frame.getContentPane().add(onButton);

		JButton offButton = new JButton("OFF");
		offButton.setMnemonic(KeyEvent.VK_F);
		offButton.setActionCommand("off");
		offButton.addActionListener(this);
		frame.getContentPane().add(offButton);

		frame.pack();
		frame.setVisible(true);

		start();
	}

	/**
	* @param e : ActionEvent
	* This function is called when any button is activated by
	* the user. The buttons are differentiated by their 
	* ActionCommand string.
	*/
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("on"))
			panel.switchOn();
		else
			panel.switchOff();
	}

	/**
	* run() is called when this.start() is called as a result of this 
	* class extending Thread. This starts the program loop that keeps 
	* the window open.
	*/
	public void run() {
		while (true) {
			if (panel.isOn())
				lightLabel.setIcon(lightOnIcon);
			else
				lightLabel.setIcon(lightOffIcon);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
	}
}