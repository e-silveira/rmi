import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ServerChat extends UnicastRemoteObject implements IServerChat {

    private ArrayList<String> roomList;

    public ServerChat() throws RemoteException {
        super();
        this.roomList = new ArrayList<String>();
    }

    public ArrayList<String> getRooms() throws RemoteException {
        return this.roomList;
    }

    public void createRoom(String roomName) throws RemoteException {
        this.roomList.add(roomName);
    }

    public static void main(String[] args) {
        try {
            ServerChat serverChat = new ServerChat();
            // Registry registry = LocateRegistry.createRegistry(2020);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
