import java.awt.event.*;
import javax.swing.*;

/** Layer2Display creates a JFrame window on screen that
 * holds text fields to build and send an L2Frame
 * Written by Jon Ellis and Jason Klaassen
 */
public class Layer2Display implements ActionListener, Layer2Listener {
    private L2Handler handler;
    private JTextField displayField;
    private JTextField destinationAddressField;
    private JTextField typeField;
    private JTextField vlanField;
    private JTextField payloadField;

    /** An explicit constructor for a Layer2Display that creates
     * the JFrame window on screen and the text fields.
     * @param handler the handler used to send the defined L2Frame
     */
    public Layer2Display(L2Handler handler) {
        this.handler = handler;
        handler.setListener(this);

        JFrame frame = new JFrame(handler.toString());
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        displayField = new JTextField(20);
        displayField.setEditable(false);
        frame.getContentPane().add(displayField);

        frame.getContentPane().add(new JLabel("Destination Address:"));

        destinationAddressField = new JTextField(20);
        destinationAddressField.addActionListener(this);
        frame.getContentPane().add(destinationAddressField);

        frame.getContentPane().add(new JLabel("Type:"));

        typeField = new JTextField(20);
        typeField.addActionListener(this);
        frame.getContentPane().add(typeField);

        frame.getContentPane().add(new JLabel("VLAN Id:"));

        vlanField = new JTextField(20);
        vlanField.addActionListener(this);
        frame.getContentPane().add(vlanField);

        frame.getContentPane().add(new JLabel("Payload:"));

        payloadField = new JTextField(20);
        payloadField.addActionListener(this);
        frame.getContentPane().add(payloadField);

        frame.pack();
        frame.setVisible(true);
    }

    /** actionPerformed() sends an L2Frame when 'Enter' is pressed
     */
    public void actionPerformed(ActionEvent e) {
        displayField.setText("Sending...");
        new Thread() {
            public void run() {
                handler.send(new L2Frame(Integer.valueOf(destinationAddressField.getText()), handler.getMacAddr(), Integer.valueOf(typeField.getText()), Integer.valueOf(vlanField.getText()), payloadField.getText()));
            }
        }.start();
    }

    /** frameReceived() sets the displayField when a frame is received from 
     * Layer 2 and designated for the macAddr of handler
     */
    public void frameReceived(L2Handler handler, L2Frame frame) {
        displayField.setText(frame.toString());
    }

}