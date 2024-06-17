import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class RoomChat implements IRoomChat {

    private String roomName;
    private Map<String, IUserChat> userList;

    RoomChat(String roomName) throws RemoteException {
        this.roomName = roomName;
        userList = new HashMap<String, IUserChat>();
    }

    public void sendMsg(String usrName, String msg) throws RemoteException {
        // Envia a mensagem para cada um dos usuários no HashMap.
        for (Map.Entry<String, IUserChat> entry : userList.entrySet()) {
            entry.getValue().deliverMsg(usrName, msg);
        }
    }

    public void joinRoom(String usrName, IUserChat user) throws RemoteException {
        // Deveríamos tratar alguns casos extremos aqui,
        // Como usuários com o mesmo nome.
        userList.put(usrName, user);
    }

    public void leaveRoom(String usrName) throws RemoteException {
        // Remove usuário do HashMap.
        userList.remove(usrName);
    }

    public void closeRoom() throws RemoteException {
        // Envia a mensagem para cada um dos usuários no HashMap.
        for (Map.Entry<String, IUserChat> entry : userList.entrySet()) {
            entry.getValue().deliverMsg(this.roomName, "Sala fechada pelo servidor.");
        }

        // Limpa o HashMap.
        userList.clear();
    }

    public String getRoomName() throws RemoteException {
        return this.roomName;
    }
}
