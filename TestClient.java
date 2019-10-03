/**
 * This is the Client version of Test that connects this machine
 * to a server light system using the given ip address
 */
public class TestClient {
	public static void main(String args[]) {
        // Connect to Jon's Computer with the IP Address and Default Port
        String ipAddr = "192.168.0.25";
		LightDisplay d2 = new LightDisplay(new LightPanel(ipAddr, LightSystem.DEFAULT_PORT));
		BitDisplay bd = new BitDisplay(new BitHandler(ipAddr, LightSystem.DEFAULT_PORT));
	}
}
