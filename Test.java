/**
 * This is the Server version of Test that creates the
 * LightSystem for Layer 1 Communication with Clients. 
 * The BitDisplays allows for sending data over the light system.
 */
public class Test {
	public static void main(String args[]) {
		runTests();
		LightSystem system = new LightSystem();
		LightDisplay d1 = new LightDisplay(new LightPanel());
		//BitDisplay bd = new BitDisplay(new BitHandler());
		Layer2Display l2d1 = new Layer2Display(new L2Handler(1));
		Layer2Display l2d2 = new Layer2Display(new L2Handler(2));
	}

	private static void runTests() {
		testL2Frame();
	}

	private static void testL2Frame() {
		try {
			testToBinary();
			testToString();
			System.out.println("L2Frame Passed.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

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
		System.out.println("All Tests Passed for L2Frame.toBinary().");
		
		
	}

	private static void testToString() throws Exception {
		L2Frame frame = new L2Frame(7, 3, 0, 0, "010101010101");
		String seven = L2Frame.toBinary(7, 4);
		String three = L2Frame.toBinary(3, 4);
		String type = L2Frame.toBinary(0, 2);
		String vlan = L2Frame.toBinary(0, 2);
		String payload = "010101010101";
		String payloadSize = L2Frame.toBinary(payload.length(), 8);
		String bitString = frame.toString();

		if(!bitString.equals("0" + seven + three + type + vlan + payloadSize + payload)) {
			throw new Exception("Test Failed: toString(): The String is not a valid frame.");
		}
		System.out.println("All Tests Passed for L2Frame.toString().");
		
	}
}
