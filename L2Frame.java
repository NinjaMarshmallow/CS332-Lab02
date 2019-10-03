public class L2Frame {

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
            result += new Integer(number % 2).toString();
            number /= 2;
            
        }
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