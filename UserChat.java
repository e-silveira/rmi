import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class UserChat extends UnicastRemoteObject implements IUserChat {

    private Registry registry;
    private IServerChat serverChat;
    private UserChatFrame userChatFrame;
    private String userName;
    private IRoomChat room;

    public void createRoom(String roomName) {
        try {
            serverChat.createRoom(roomName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRooms() {
        try {
            return serverChat.getRooms();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void connectRoom(String roomName) {
        try {
            // Pesquisa o objeto no registro.
            room = (IRoomChat) registry.lookup(roomName);

            // Se conecta ao chat.
            room.joinRoom(this.userName, this);

            // Limpa o chat.
            this.userChatFrame.cleanChat();
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                JOptionPane.showMessageDialog(userChatFrame, "Seleciona uma sala.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            room.sendMsg(this.userName, msg);
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                JOptionPane.showMessageDialog(userChatFrame, "Você não está em nenhuma sala.");
            } else {
                e.printStackTrace();
            }
        }
    }

    public void leaveRoom() {
        try {
            room.leaveRoom(this.userName);
            this.room = null;
            this.userChatFrame.cleanChat();
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                JOptionPane.showMessageDialog(userChatFrame, "Você não está em nenhuma sala.");
            } else {
                e.printStackTrace();
            }
        }
    }

    UserChat(String address) throws RemoteException {
        registry = LocateRegistry.getRegistry(address, 2020);

        try {
            serverChat = (IServerChat) registry.lookup("Servidor");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.askValidUserName();
        this.userChatFrame = new UserChatFrame(this);
    }

    public void deliverMsg(String senderName, String msg) throws RemoteException {
        if (msg.equals("Sala fechada pelo servidor.")){
            // Remove a referência ao objeto remoto.
            this.room = null;
        }
        this.userChatFrame.appendMessageToChat(senderName, msg);
    }

    private void askValidUserName() {
        String name;
        do {
            name = JOptionPane.showInputDialog("Qual o nome de usuário?");
        } while (name == null || name.equals(""));

        this.userName = name;
    }

    public static void main(String[] args) {
        try {
            new UserChat(args[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
