import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

            // Função chamada quando o botão "Criar" for clicado.
            Consumer<String> createRoom = (roomName) -> {
                try {
                    serverChat.createRoom(roomName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            // Função chamada quando o botão "Procurar" for clicado.
            Supplier<ArrayList<String>> getRooms = () -> {
                try {
                    return serverChat.getRooms();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            };

            new UserChatFrame(createRoom, getRooms);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
