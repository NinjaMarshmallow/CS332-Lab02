/** BitListener is an interface with the method
 *  bitsReceived, which takes a BitHandler and a bitstring
 */
public interface BitListener {
    void bitsReceived(BitHandler handler, String bits);
}