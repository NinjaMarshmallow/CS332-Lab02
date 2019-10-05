/** L2Handler is the handler for an L2Frame.
 * It sends L2Frames to layer 1 and creates L2Frames
 * when bits are received from layer 1.
 * Written by Jon Ellis and Jason Klaassen for CS332
 */
public class L2Handler implements BitListener {
    
    private BitHandler handler;
    private Layer2Listener layer2Listener;
    private Integer macAddr;
    private Integer vlanid;

    /** An explicit constructor for L2Handler using 
     * the default values for the host and port
     * @param macAddr the address for the L2Handler
     * @param vlanid the VLAN assigned to the L2Handler
     */
    public L2Handler(Integer macAddr, Integer vlanid) {
        handler = new BitHandler();
        handler.setListener(this);
        this.macAddr = macAddr;
        this.vlanid = vlanid;
    }

    /** An explicit constructor for L2Handler using 
     * the explicit values for the host and port
     * @param host the host for the BitHandler
     * @param port the port for the BitHandler
     * @param macAddr the address for the L2Handler
     * @param vlanid the VLAN assigned to the L2Handler
     */
    public L2Handler(String host, int port, Integer macAddr, Integer vlanid) {
        handler = new BitHandler(host, port);
        handler.setListener(this);
        this.macAddr = macAddr;
        this.vlanid = vlanid;
    }

    /** toString() returns the string conversion for 
     *  the MAC address of the L2Handler
     */
    public String toString() {
        return macAddr.toString();
    }

    /** bitsReceived(handler, bits) takes a bitstring from Layer 1 
     * and creates an L2Frame if the packet is for the L2Handler,
     * otherwise it drops the packet
     * @param handler the Layer 1 BitHandler that sent the bits
     * @param bits the bitstring sent from Layer 1 to Layer 2
     */
    public void bitsReceived(BitHandler handler, String bits) {
        try {
            System.out.println("Bits Received: " + bits);
            L2Frame frame = new L2Frame(bits);
            System.out.println("Frame payload: " + frame.getPayload());
            System.out.println("Received Frame: " + frame.toString());
            if (frame.getDest() == macAddr || 
               (frame.getDest() == Integer.parseInt(L2Frame.BCAST_ADDR, 2) && frame.getSource() != macAddr) &&
               (frame.getVlanid() == 0 || frame.getVlanid() == vlanid)) {
                layer2Listener.frameReceived(L2Handler.this, frame);
            }
            else {
                // Drop packet
            }
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /** setListener(l) resgisters a Layer2Listener to the L2Handler
     * @param l the Layer2Listener to be resistered
     */
    public void setListener(Layer2Listener l) {
		this.layer2Listener = l;
    }

    /** send(frame) sends a received frame to Layer 1.
     * It waits until the handler is silent and then sends the frame
     * @param frame the frame to be sent
     */
    public void send(L2Frame frame) {
        while (!handler.isSilent()) {
            BitHandler.pause(BitHandler.HALFPERIOD);
        }
        try {
            handler.broadcast(frame.toString());
        }
        catch (CollisionException e) {
            // Should never get here
            e.printStackTrace();
        }
    }

    public Integer getMacAddr() {
        return macAddr;
    }

    public Integer getVlanid() {
        return vlanid;
    }

}