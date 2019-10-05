import java.awt.event.*;
import javax.swing.*;

public class Layer2Display implements ActionListener, Layer2Listener {
    private L2Handler handler;
    private JTextField displayField;
    private JTextField destinationAddressField;
    private JTextField typeField;
    private JTextField vlanField;
    private JTextField payloadField;

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

    public void actionPerformed(ActionEvent e) {
        displayField.setText("Sending...");
        new Thread() {
            public void run() {
                handler.send(new L2Frame(Integer.valueOf(destinationAddressField.getText()), handler.getMacAddr(), Integer.valueOf(typeField.getText()), Integer.valueOf(vlanField.getText()), payloadField.getText()));
            }
        }.start();
    }

    public void frameReceived(L2Handler handler, L2Frame frame) {
        displayField.setText(frame.toString());
    }

}