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

    }

    public void setListener(Layer2Listener l) {
		layer2Listener = l;
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