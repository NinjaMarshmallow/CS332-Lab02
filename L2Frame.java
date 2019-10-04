public class L2Frame {

    private final static String BCAST_ADDR = "1111";
    private final static int DEST_LENGTH = 4;
    private final static int SRC_LENGTH = 4;
    private final static int TYPE_LENGTH = 2;
    private final static int VLAN_LENGTH = 2;
    private final static int PAYLOAD_SIZE_LENGTH = 8;
    


    private Integer dest, source, type, vlanid, checksum, payloadSize;
    private String payload;

    public L2Frame(Integer dest, Integer source, Integer type, Integer vlandid, String payload) {
        this.dest = dest;
        this.source = source;
        this.type = type;
        this.vlanid = vlandid;
        this.payload = payload;
        this.payloadSize = payload.length();
    }

    public static String toBinary(int number, Integer length) {
        String result = "";
        for(int i = 0; i < length; i++) {
            result = new Integer(number % 2).toString() + result;
            number /= 2;   
        }
        return result;
    }

    public String toString() {
        String result = "0";
        result += toBinary(this.dest, L2Frame.DEST_LENGTH);
        result += toBinary(this.source, L2Frame.SRC_LENGTH);
        result += toBinary(this.type, L2Frame.TYPE_LENGTH);
        result += toBinary(this.vlanid, L2Frame.VLAN_LENGTH);
        result += toBinary(this.payloadSize, L2Frame.PAYLOAD_SIZE_LENGTH);
        result += this.payload;
        return result;
    }

    public Integer getDest() {
        return dest;
    }

    public void setDest(Integer dest) {
        this.dest = dest;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVlanid() {
        return vlanid;
    }

    public void setVlanid(Integer vlanid) {
        this.vlanid = vlanid;
    }

    public Integer getChecksum() {
        return checksum;
    }

    public void setChecksum(Integer checksum) {
        this.checksum = checksum;
    }

    public Integer getPayloadSize() {
        return payloadSize;
    }

    public void setPayloadSize(Integer payloadSize) {
        this.payloadSize = payloadSize;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
    

}