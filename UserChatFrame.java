import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UserChatFrame extends JFrame {

    private JTextArea chat;

    public void cleanChat() {
        this.chat.setText("");
    }

    public void appendMessageToChat(String senderName, String msg) {
        this.chat.append(senderName + ": " + msg + "\n");
    }

    UserChatFrame(UserChat user) {

        JList<String> roomList = new JList<String>();

        JScrollPane roomListScroll = new JScrollPane(roomList);
        roomListScroll.setBounds(0, 0, 800, 200);

        JButton createButton = new JButton("Criar");
        createButton.setBounds(0, 200, 100, 50);
        createButton.addActionListener((event) -> {
            String roomName = JOptionPane.showInputDialog("Qual o nome da sala?");
            user.createRoom(roomName);
        });

        JButton searchButton = new JButton("Procurar");
        searchButton.setBounds(100, 200, 100, 50);
        searchButton.addActionListener((event) -> {
            String[] rooms = user.getRooms().toArray(new String[0]);
            roomList.setListData(rooms);
        });

        JButton connectButton = new JButton("Conectar");
        connectButton.setBounds(200, 200, 100, 50);
        connectButton.addActionListener((event) -> {
            user.connectRoom(roomList.getSelectedValue());
        });

        chat = new JTextArea();
        chat.setEditable(false);
        chat.setBounds(0, 250, 800, 200);

        JTextField input = new JTextField();
        input.setBounds(0, 450, 800, 20);
        input.addActionListener((event) -> {
            try {
                user.sendMsg(input.getText());
                input.setText("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        JButton leaveButton = new JButton("Sair");
        leaveButton.setBounds(0, 470, 100, 50);
        leaveButton.addActionListener((event) -> {
            user.leaveRoom();
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
