import java.awt.event.*;
import javax.swing.*;
/**
 * BitDisplay creates a JFrame window on screen that holds a 
 * text field that allows the user to enter in a string of 1's 
 * and 0's as bits to be carried out by the LightSystem. There 
 * is also a text display field where incoming bits are shown.
 * This text field also displays the word Collision! if there
 * is a collision of signals.
 */
public class BitDisplay implements ActionListener, BitListener {
	private BitHandler handler;
	private JTextField receiveField;
	private JTextField sendField;

	public BitDisplay(BitHandler handler) {
		this.handler = handler;

		JFrame frame = new JFrame(handler.toString());
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		receiveField = new JTextField(20);
		receiveField.setEditable(false);
		frame.getContentPane().add(receiveField);

		sendField = new JTextField(20);
		sendField.addActionListener(this);
		frame.getContentPane().add(sendField);

		frame.pack();
		frame.setVisible(true);
		handler.setListener(this);
	}
	/**
	* @param e : ActionEvent
	* This function is called when the user presses enter after
	* typing bits as a string into the input text field to send
	* the bits over the LightSystem to the other machine.
	*/
	public void actionPerformed(ActionEvent e) {
		new Thread() {
			public void run() {
				try {
					handler.broadcast(sendField.getText());
				}
				catch (CollisionException e) {
					receiveField.setText("Collision!");
				}
				// System.out.println("actionPerformed: done sending " + sendField.getText());
			}
		}.start();
		sendField.selectAll();
	}
	/**
	 * bitsReceieved sets the display text field to 
	 * the incoming bits
	 */
	public void bitsReceived(BitHandler h, String bits) {
		receiveField.setText(bits);
	}
}