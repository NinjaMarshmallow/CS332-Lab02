public class L2Handler implements BitListener {
    
    private BitHandler handler;
    private Layer2Listener layer2Listener;
    private Integer macAddr;

    public L2Handler(Integer macAddr) {
        handler = new BitHandler();
        handler.setListener(this);
        this.macAddr = macAddr;
    }

    public L2Handler(String host, int port, Integer macAddr) {
        handler = new BitHandler(host, port);
        handler.setListener(this);
        this.macAddr = macAddr;
    }

    public String toString() {
        return macAddr.toString();
    }

    public void bitsReceived(BitHandler handler, String bits) {
        try {
            System.out.println("Bits Received: " + bits);
            L2Frame frame = new L2Frame(bits);
            System.out.println("Frame payload: " + frame.getPayload());
            System.out.println("Received Frame: " + frame.toString());
            if (frame.getDest() == macAddr || (frame.getDest() == Integer.parseInt(L2Frame.BCAST_ADDR, 2) && frame.getSource() != macAddr)) {
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

    public void setListener(Layer2Listener l) {
		this.layer2Listener = l;
    }
    
    public Integer getMacAddr() {
        return macAddr;
    }

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

}