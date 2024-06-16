import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class UserChatFrame extends JFrame {

    private Consumer<String> sendMsg;
    private Runnable leaveRoom;

    private JTextArea chat;

    public void setSendMsg(Consumer<String> sendMsg) {
        this.sendMsg = sendMsg;
    }

    public void unsetSendMsg() {
        this.sendMsg = null;
    }

    public void setLeaveRoom(Runnable leaveRoom) {
        this.leaveRoom = leaveRoom;
    }

    public void unsetLeaveRoom() {
        this.leaveRoom = null;
    }

    public void cleanChat() {
        this.chat.setText("");
    }

    public void appendMessageToChat(String senderName, String msg) {
        this.chat.append(senderName + ": " + msg + "\n");
    }

    UserChatFrame(Consumer<String> createRoom, Supplier<ArrayList<String>> getRooms, Consumer<String> connectRoom) {

        JList<String> roomList = new JList<String>();

        JScrollPane roomListScroll = new JScrollPane(roomList);
        roomListScroll.setBounds(0, 0, 800, 200);

        JButton createButton = new JButton("Criar");
        createButton.setBounds(0, 200, 100, 50);
        createButton.addActionListener((event) -> {
            String roomName = JOptionPane.showInputDialog("Qual o nome da sala?");
            createRoom.accept(roomName);
        });

        JButton searchButton = new JButton("Procurar");
        searchButton.setBounds(100, 200, 100, 50);
        searchButton.addActionListener((event) -> {
            String[] rooms = getRooms.get().toArray(new String[0]);
            roomList.setListData(rooms);
        });

        JButton connectButton = new JButton("Conectar");
        connectButton.setBounds(200, 200, 100, 50);
        connectButton.addActionListener((event) -> {
            connectRoom.accept(roomList.getSelectedValue());
        });

        chat = new JTextArea();
        chat.setEditable(false);
        chat.setBounds(0, 250, 800, 200);

        JTextField input = new JTextField();
        input.setBounds(0, 450, 800, 20);
        input.addActionListener((event) -> {
            try {
                sendMsg.accept(input.getText());
                input.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JButton leaveButton = new JButton("Sair");
        leaveButton.setBounds(0, 470, 100, 50);
        leaveButton.addActionListener((event) -> {
            leaveRoom.run();
        });

        this.setTitle("Chat");
        this.setSize(800, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        this.add(roomListScroll);
        this.add(createButton);
        this.add(searchButton);
        this.add(connectButton);
        this.add(chat);
        this.add(input);
        this.add(leaveButton);
    }
}
