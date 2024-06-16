import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

public class UserChat extends UnicastRemoteObject implements IUserChat {

    public UserChatFrame userChatFrame;
    public String userName;

    public UserChat() throws RemoteException {
        super();
    }

    public void setUserChatFrame(UserChatFrame userChatFrame) {
        this.userChatFrame = userChatFrame;
    }

    public void deliverMsg(String senderName, String msg) throws RemoteException {
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
            UserChat user = new UserChat();

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

            // Função chamada quando o botão "Conectar" for clicado.
            Consumer<String> connectRoom = (roomName) -> {
                try {
                    // Pesquisa o objeto no registro.
                    IRoomChat room = (IRoomChat) registry.lookup(roomName);

                    // Se conecta ao chat.
                    room.joinRoom(user.userName, user);

                    // Atualiza a função que envia mensagens.
                    user.userChatFrame.setSendMsg((msg) -> {
                        try {
                            room.sendMsg(user.userName, msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    user.userChatFrame.setLeaveRoom(() -> {
                        try {
                            room.leaveRoom(user.userName);

                            // Estas funções voltam a ser nulas.
                            user.userChatFrame.unsetSendMsg();
                            user.userChatFrame.unsetLeaveRoom();
                            
                            user.userChatFrame.cleanChat();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    // Limpa o chat.
                    user.userChatFrame.cleanChat();
                
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            user.askValidUserName();
            user.setUserChatFrame(new UserChatFrame(createRoom, getRooms, connectRoom));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
