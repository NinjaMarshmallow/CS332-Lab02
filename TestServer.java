/**
 * This is the Server version of Test that creates the
 * LightSystem for Layer 1 Communication with Clients. 
 * The BitDisplays allows for sending data over the light system.
 */
public class TestServer {
	public static void main(String args[]) {
		LightSystem system = new LightSystem();
		LightDisplay d1 = new LightDisplay(new LightPanel());
		BitDisplay bd = new BitDisplay(new BitHandler());
	}
}
