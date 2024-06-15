import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class UserChat extends UnicastRemoteObject implements IUserChat {
    static JButton searchButton;
    static JButton createButton;
    static JButton connectionButton;
    static JList<String> roomList;
    static IServerChat serverChat;

    public static void getRooms() {
        try {
            ArrayList<String> rooms = serverChat.getRooms();
            String[] list = new String[rooms.size()];
            list = rooms.toArray(list);
            roomList.setListData(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createRoom() {
        try {
            String name = JOptionPane.showInputDialog("Qual o nome da sala?");
            serverChat.createRoom(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UserChatFrame() {

        Font customFont = new Font(Font.MONOSPACED, Font.PLAIN, 20);

        UIManager.put("OptionPane.messageFont", customFont);
        UIManager.put("OptionPane.buttonFont", customFont);
        UIManager.put("Panel.font", customFont);
        UIManager.put("Button.font", customFont);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("TextArea.font", customFont);
        UIManager.put("ScrollPane.font", customFont);
        UIManager.put("List.font", customFont);

        JLabel label = new JLabel();
        label.setBounds(20, 20, 760, 28);
        label.setText("Selecione uma sala:");
        label.setForeground(new Color(0x222222));
        label.setBackground(new Color(0xAAAAAA));
        label.setOpaque(true);
        label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.LEFT);
        
        roomList = new JList<String>();
            
        JScrollPane roomListScroll = new JScrollPane(roomList);
        roomListScroll.setBounds(20, 48, 760, 200);

        searchButton = new JButton();
        searchButton.setBounds(20, 248, 200, 50);
        searchButton.setText("Procurar");
        searchButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        searchButton.setFocusable(false);

        createButton = new JButton();
        createButton.setBounds(220, 248, 200, 50);
        createButton.setText("Criar");
        createButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        createButton.setFocusable(false);

        connectionButton = new JButton();
        connectionButton.setBounds(420, 248, 200, 50);
        connectionButton.setText("Conectar");
        connectionButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        connectionButton.setFocusable(false);

        JFrame frame = new JFrame();

        // Altera o nome da janela.
        frame.setTitle("Chat");

        // Altera o tamanho da janela.
        frame.setSize(800, 356);

        // Sem redimensionamento.
        frame.setResizable(false);

        // Termina a aplicação quando fechar a janela.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Muda a cor de fundo da janela.
        frame.getContentPane().setBackground(new Color(0xFFFFFF));

        // Vamos posicionar tudo manualmente.
        frame.setLayout(null);

        // Torna a janela visível. 
        frame.setVisible(true);

        frame.add(label);
        frame.add(roomListScroll);
        frame.add(searchButton);
        frame.add(createButton);
        frame.add(connectionButton);
    }

    public UserChat() throws RemoteException {
        super();
    }

    public void deliverMsg(String senderName, String msg) throws RemoteException {
    }

    public static void main(String[] args) {
            UserChatFrame();
            try {
                Registry registry = LocateRegistry.getRegistry(args[0], 2020);
                serverChat = (IServerChat) registry.lookup("Servidor");

                searchButton.addActionListener(e -> getRooms());
                createButton.addActionListener(e -> createRoom());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
