public class L2Handler {
    
    private BitHandler handler;
    private Layer2Listener layer2Listener;
    private Integer macAddr;

    public L2Handler(Integer macAddr) {
        this.handler = new BitHandler();
        this.handler.setListener(this);
        this.macAddr = macAddr;
    }

    public L2Handler(String host, int port, Integer macAddr) {
        this.handler = new BitHandler(host, port);
        this.handler.setListener(this);
        this.macAddr = macAddr;
    }

    public String toString() {
        return macAddr.toString();
    }

}