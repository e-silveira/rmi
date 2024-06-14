import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.ArrayList;

public class UserChat extends UnicastRemoteObject implements IUserChat {

    public UserChat() throws RemoteException {
        super();
    }

    public void deliverMsg(String senderName, String msg) throws RemoteException {
    }

    public static void main(String[] args) {
            try {
                Registry registry = LocateRegistry.getRegistry(args[0], 2020);
                IServerChat serverChat = (IServerChat) registry.lookup("Servidor");
                serverChat.createRoom("Hello, world!");
                ArrayList<String> roomList = serverChat.getRooms();
                System.out.println(roomList);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
