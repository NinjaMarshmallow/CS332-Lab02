import java.io.*;
import java.net.*;
import java.util.*;

/**
 *  LightPanel is an object that connects a LightDisplay to the LightSystem 
 *  via a Socket. It reads signals that come over the socket and sets
 *  itself on or off depending on if the signal is high or low.
 */
public class LightPanel extends Thread {
	private static Set<Integer> idsUsed = new HashSet<>();

	private int id;
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private boolean isHigh = false;

	/**
	 * LightPanel() is the default constructor for a LightPanel
	 * calls the explicit constructor using "localhost" and the default port
	 */
	public LightPanel() {
		this("localhost", LightSystem.DEFAULT_PORT);
	}

	/**
	 * LightPanel(host, port) is an explicit constructor for a LightPanel
	 * creates a new socket on the host and port and initializes a thread
	 * which calls the run function
	 * @param host  type String, the host of the socket
	 * @param port  type int, the port to run the socket on
	 */
	public LightPanel(String host, int port) {
		do {
			id = LightSystem.getRandom().nextInt(15) + 1;
		} while (!idsUsed.add(id));

		try {
			socket = new Socket(host, port);
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			start();
		} catch (UnknownHostException e) {
			throw new RuntimeException("Invalid host:  " + host);
		} catch (IOException e) {
			throw new RuntimeException("Unable to connect to LightSystem");
		}
	}

	/**
	 * switchOn() generates a high signal for the LightSystem
	 */
	public void switchOn() {
		socketOut.println(LightSystem.HIGH);
	}

	/**
	 * switchOn() generates a low signal for the LightSystem
	 */
	public void switchOff() {
		socketOut.println(LightSystem.LOW);
	}

	/**
	 * close() closes the socket
	 */
	public void close() {
		try {
			socketOut.close();
			socketIn.close();
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * run() is the process for the thread initialized in the constructor.
	 * It reads the input stream of the socket
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			String line = socketIn.readLine();
			while (line != null) {
				if (line.equals(LightSystem.HIGH))
					isHigh = true;
				else if (line.equals(LightSystem.LOW))
					isHigh = false;
				line = socketIn.readLine();
			}
		} catch (Exception e) {
			System.out.println("LightPanel disconnected");
			throw new RuntimeException(e);
		}
	}

	/**
	 * isOn() returns true if the signal is high, false otherwise
	 * @return isHigh  type Boolean, true if the signal is high, false if not
	 */
	public boolean isOn() {
		return isHigh;
	}

	/**
	 * toString() the string conversion for a LightPanel
	 * @return '#id'  type String
	 * @see java.lang.Thread#toString()
	 */
	public String toString() {
		return "#" + id;
	}
	
	// getId() is the getter for id
	public int getID() {
		return id;
	}
}