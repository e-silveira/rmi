import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserChat extends UnicastRemoteObject implements IUserChat {

    public UserChat() throws RemoteException {
        super();
    }

    public void deliverMsg(String senderName, String msg) throws RemoteException {
    }

    public static void main(String[] args) {
        System.out.println("Hello, world!");
    }
}
