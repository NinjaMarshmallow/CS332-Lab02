/**
 * This is the Server version of Test that creates the
 * LightSystem for Layer 1 Communication with Clients. 
 * The BitDisplays allows for sending data over the light system.
 */
public class Test {
	public static void main(String args[]) {
		runTests();
		LightSystem system = new LightSystem();
		//LightDisplay d1 = new LightDisplay(new LightPanel());
		//BitDisplay bd = new BitDisplay(new BitHandler());
		Layer2Display l2d1 = new Layer2Display(new L2Handler(1, 1));
		Layer2Display l2d2 = new Layer2Display(new L2Handler(2, 1));
		Layer2Display l2d3 = new Layer2Display(new L2Handler(1, 0));
	}

	private static void runTests() {
		testL2Frame();
	}
	/**
	 * Tests the L2Frame Class and its functions
	 */
	private static void testL2Frame() {
		try {
			testToBinary();
			testToString();
			testCalculateCheckSum();
			System.out.println("L2Frame Passed.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Tests the toBinary function of L2Frame
	 * @throws Exception when a test is failed
	 */
	private static void testToBinary() throws Exception {
		Integer five = 5, seven = 7, fifteen = 15;
		Integer length = 4;
		String bitString = L2Frame.toBinary(five, length);

		if(!bitString.equals("0101")) {
			throw new Exception("Test Failed: L2Frame.toBinary(): Integer 5 != '0101'");	
		}
		bitString = L2Frame.toBinary(seven, length);
		if(!bitString.equals("0111")) {
			throw new Exception("Test Failed: L2Frame.toBinary(): Integer 7 != '0111'");
		}
		bitString = L2Frame.toBinary(fifteen, length);
		if(!bitString.equals("1111")) {
			throw new Exception("Test Failed: L2Frame.toBinary(): Integer 15 != '1111'");
		}
		
		try {
			bitString = L2Frame.toBinary(8, 3);
			throw new Exception("Test Failed: L2Frame.toBinary(): The Number given is too large for the length");
		} catch(IllegalArgumentException e) {
		}
		System.out.println("All Tests Passed for L2Frame.toBinary().");
		
		
	}
	/**
	 * Tests the toString function of L2Frame
	 * @throws Exception when a test is failed
	 */
	private static void testToString() throws Exception {
		L2Frame frame = new L2Frame(7, 3, 0, 0, "010101010101");
		String seven = L2Frame.toBinary(7, 4);
		String three = L2Frame.toBinary(3, 4);
		String type = L2Frame.toBinary(0, 2);
		String vlan = L2Frame.toBinary(0, 2);
		String payload = "010101010101";
		String payloadSize = L2Frame.toBinary(payload.length(), 8);
		String checksum = L2Frame.toBinary(1, 1);
		String bitString = frame.toString();

		if(!bitString.equals("0" + seven + three + type + vlan + payloadSize + checksum + payload)) {
			throw new Exception("Test Failed: toString(): The String is not a valid frame.");
		}
		System.out.println("All Tests Passed for L2Frame.toString().");
		
	}

	/**
	 * Tests the calculateCheckSum function of L2Frame
	 * @throws Exception when a test is failed
	 */
	private static void testCalculateCheckSum() throws Exception {
		L2Frame frame = new L2Frame(7, 3, 0, 0, "010101010111"); // 13 1's
		if(L2Frame.calculateCheckSum(frame.toStringWithOutCheckSum()) == 1) throw new Exception("Test Failed: calculateCheckSum(): The Checksum is not valid. It is 1 when there are already even 1's.");
		
		L2Frame frame2 = new L2Frame(7, 3, 0, 0, "010101010101"); // 14 1's
		if(L2Frame.calculateCheckSum(frame2.toStringWithOutCheckSum()) == 0) throw new Exception("Test Failed: calculateCheckSum(): The Checksum is not valid. It is 0 when there are an odd number of 1's.");
		
		L2Frame frame3 = new L2Frame(0, 0, 0, 0, ""); // 0 1's
		if(L2Frame.calculateCheckSum(frame3.toStringWithOutCheckSum()) == 1) throw new Exception("Test Failed: calculateCheckSum(): The Checksum is not valid. It is 1 when there are zero 1's.");
		
		System.out.println("All Tests Passed for L2Frame.calculateCheckSum().");
	}
}
