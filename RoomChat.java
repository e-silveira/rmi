import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

public class RoomChat extends UnicastRemoteObject implements IRoomChat {

    RoomChat() throws RemoteException {
    }
    
    private Map<String, IUserChat> userList;

    public void sendMsg(String usrName, String msg) throws RemoteException {
    }

    public void joinRoom(String usrName, IUserChat user) throws RemoteException {
    }

    public void leaveRoom(String usrName) throws RemoteException {
    }

    public void closeRoom() throws RemoteException {
    }

    public String getRoomName() throws RemoteException {
        return null;
    }
}
