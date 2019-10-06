public class L2Frame {

    // Initialize constants

    public final static String BCAST_ADDR = "1111";
    private final static int DEST_LENGTH = 4;
    private final static int SRC_LENGTH = 4;
    private final static int TYPE_LENGTH = 2;
    private final static int VLAN_LENGTH = 2;
    private final static int PAYLOAD_SIZE_LENGTH = 8;
    private final static int CHECKSUM_LENGTH = 1;
    


    private Integer dest, source, type, vlanid, checksum, payloadSize;
    private String payload;

    /**
     * Constructor for creating an L2Frame from its components described in Prof. Norman's Layer 2 Protocol
     * @param dest - Destination Mac Address as a base10 Integer
     * @param source - Source Mac Address as a base10 Integer 
     * @param type - Specify what protocol to use
     * @param vlandid - Specify what vlan the machine is sending on
     * @param payload - Data to send to the destination machine
     */
    public L2Frame(Integer dest, Integer source, Integer type, Integer vlandid, String payload) {
        this.dest = dest;
        this.source = source;
        this.type = type;
        this.vlanid = vlandid;
        this.payload = payload;
        this.payloadSize = payload.length();
        this.checksum = calculateCheckSum(toStringWithOutCheckSum());
        
    }
    /**
     * Constructor for building an L2Frame from a string of bits that fits Prof. Norman's Layer 2 Protocol
     * @param bitString - String of bits to be converted into the various fields of a frame
     */
    public L2Frame(String bitString) throws IllegalArgumentException {
        int destEndIndex = 1+L2Frame.DEST_LENGTH;
        int srcEndIndex = destEndIndex+L2Frame.SRC_LENGTH;
        int typeEndIndex = srcEndIndex+L2Frame.TYPE_LENGTH;
        int vlanidEndIndex = typeEndIndex+L2Frame.VLAN_LENGTH;
        int payloadSizeEndIndex = vlanidEndIndex+L2Frame.PAYLOAD_SIZE_LENGTH;
        int ChecksumEndIndex = payloadSizeEndIndex + 1;
        this.dest = Integer.parseInt(bitString.substring(1, destEndIndex), 2);
        this.source = Integer.parseInt(bitString.substring(destEndIndex, srcEndIndex), 2);
        this.type = Integer.parseInt(bitString.substring(srcEndIndex, typeEndIndex), 2);
        this.vlanid = Integer.parseInt(bitString.substring(typeEndIndex, vlanidEndIndex), 2);
        this.payloadSize = Integer.parseInt(bitString.substring(vlanidEndIndex, payloadSizeEndIndex), 2);
        this.checksum = Integer.parseInt(bitString.substring(payloadSizeEndIndex, ChecksumEndIndex), 2);
        int bitStringLength = bitString.length();
        int payloadEndIndex = ChecksumEndIndex + this.payloadSize;
        if(payloadEndIndex > bitStringLength || bitString.charAt(0) != '0' || calculateCheckSum(bitString) != 0) {
            throw new IllegalArgumentException("This Frame is not valid");
        }
        this.payload = bitString.substring(ChecksumEndIndex, payloadEndIndex);
    }

    /**
     * calculateCheckSum() takes a bitString and finds the # of 1's in the string
     * and chooses a checksum to make the total number of 1's even.
     * @param bitString
     * @return 1 if the # of 1's in the bitString is odd, 0 if even
     */
    public static int calculateCheckSum(String bitString) {
        int count = 0, result = 0;
        // Count the number of 1's
        for(int i = 0; i < bitString.length(); i++) {
            String character = bitString.substring(i, i + 1);
            if(character.equals("1")) {
                count += 1;
            }
            
        }
        // Set the result to make an even number of 1's including the checksum
        result = count % 2;
        return result;
    }
    /**
     * toBinary converts a gives integer into a String binary representation
     * @param number
     * @param length
     * @return A String with the binary representation of the given number padded with enough zeros
     * to fill out a string with a size of the given length
     */
    public static String toBinary(int number, Integer length) throws Exception {
        String result = "";
        if(number > (Math.pow(2, length) - 1)) {
            throw new IllegalArgumentException("The number given is too large to be displayed in that length");
        }
        for(int i = 0; i < length; i++) {
            result = Integer.valueOf(number % 2).toString() + result;
            number /= 2;   
        }
        return result;
    }

    /**
     * Converts the L2Frame object into a bitString with all fields
     * concatenated together to be send over the network as a frame
     */
    public String toString() {
        String result = "0";
        try {
            result += toBinary(this.dest, L2Frame.DEST_LENGTH);
            result += toBinary(this.source, L2Frame.SRC_LENGTH);
            result += toBinary(this.type, L2Frame.TYPE_LENGTH);
            result += toBinary(this.vlanid, L2Frame.VLAN_LENGTH);
            result += toBinary(this.payloadSize, L2Frame.PAYLOAD_SIZE_LENGTH);
            result += toBinary(this.checksum, L2Frame.CHECKSUM_LENGTH);
            result += this.payload;
        } catch(Exception e) {
            System.err.println("One of the fields did not fit into it's sized container.");
        }
        return result;
    }

    /**
     * Similar to toString, but does not override the built-in function
     * and omits the checksum field so the calculateChecksum function can
     * use this to calculate.
     * @return
     */
    public String toStringWithOutCheckSum() {
        String result = "0";
        try {
            result += toBinary(this.dest, L2Frame.DEST_LENGTH);
            result += toBinary(this.source, L2Frame.SRC_LENGTH);
            result += toBinary(this.type, L2Frame.TYPE_LENGTH);
            result += toBinary(this.vlanid, L2Frame.VLAN_LENGTH);
            result += toBinary(this.payloadSize, L2Frame.PAYLOAD_SIZE_LENGTH);
            result += this.payload;
        } catch(Exception e) {
            System.err.println("One of the fields did not fit into it's sized container.");
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