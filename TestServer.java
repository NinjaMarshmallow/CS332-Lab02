/**
 * This is the Server version of Test that creates the
 * LightSystem for Layer 1 Communication with Clients. 
 * The BitDisplays allows for sending data over the light system.
 */
public class TestServer {
	public static void main(String args[]) {
		runTests();
		LightSystem system = new LightSystem();
		LightDisplay d1 = new LightDisplay(new LightPanel());
		BitDisplay bd = new BitDisplay(new BitHandler());
		Layer2Display l2d = new Layer2Display(new L2Handler(5));
	}

	private static void runTests() {
		testL2Frame();
		System.out.println("L2Frame Passed.");
	}

	private static void testL2Frame() {
		testToBinary();
		L2Frame frame = new L2Frame(7, 3, 0, 0, "010101010101");
	}

	private static void testToBinary() {
		Integer five = 5, seven = 7, fifteen = 15;
		Integer length = 4;
		String bitString = L2Frame.toBinary(five, length);
		if(bitString != "0101") System.err.println("Test Failed: L2Frame.toBinary(): Integer 5 != '0101'");
		bitString = L2Frame.toBinary(seven, length);
		if(bitString != "0111") System.err.println("Test Failed: L2Frame.toBinary(): Integer 5 != '0111'");
		bitString = L2Frame.toBinary(fifteen, length);
		if(bitString != "1110") System.err.println("Test Failed: L2Frame.toBinary(): Integer 5 != '1110'");
		System.out.println("All Tests Passed for toBinary().");
	}
}
