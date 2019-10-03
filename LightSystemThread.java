import java.io.*;

/**
 * LightSystemThread allows the LightSystem to connect
 * to multiple clients as the LightSystem spins up a new
 * LightSystemThread for every client that connects to it.
 * This ensures that the server will be always able to listen
 * for new connections.
 */
public class LightSystemThread extends Thread {
	private LightSystem system;
	private BufferedReader in;

	public LightSystemThread(LightSystem system, BufferedReader in) {
		this.system = system;
		this.in = in;
	}

	public void run() {
		try {
			String line = in.readLine();
			while (line != null) {
				if (line.equals(LightSystem.HIGH))
					system.switchOn();
				else
					system.switchOff();
				line = in.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("LightPanel disconnected");
		}
	}
}