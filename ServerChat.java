import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class ServerChat implements IServerChat {
    private static Registry registry;
    private ArrayList<String> roomList;
    private ServerChatFrame serverChatFrame;

    public ServerChat() throws RemoteException {
        this.roomList = new ArrayList<String>();
        this.serverChatFrame = new ServerChatFrame(this);
    }

    public ArrayList<String> getRooms() throws RemoteException {
        return this.roomList;
    }

    public void createRoom(String roomName) throws RemoteException {
        this.roomList.add(roomName);
        try {
            IRoomChat roomChat = new RoomChat(roomName);
            IRoomChat stub = (IRoomChat) UnicastRemoteObject.exportObject(roomChat, 0);
            registry.rebind(roomName, stub);

            this.serverChatFrame.updateRoomList(this.roomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeRoom(String roomName) throws RemoteException {
        this.roomList.remove(roomName);
        try {
            IRoomChat roomChat = (IRoomChat) registry.lookup(roomName);
            roomChat.closeRoom();
            registry.unbind(roomName);

            this.serverChatFrame.updateRoomList(this.roomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "192.168.232.202");
            IServerChat serverChat = new ServerChat();
            IServerChat stub = (IServerChat) UnicastRemoteObject.exportObject(serverChat, 0);
            registry = LocateRegistry.createRegistry(2020);
            registry.rebind("Servidor", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
