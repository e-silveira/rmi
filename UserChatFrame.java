import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UserChatFrame extends JFrame {

    public static final int ROOM_LIST_WIDTH = 800;
    public static final int ROOM_LIST_HEIGHT = 200;
    public static final int BUTTON_WIDTH = 100;
    public static final int BUTTON_HEIGHT = 50;

    UserChatFrame(Consumer<String> createRoom, Supplier<ArrayList<String>> getRooms) {

        JList<String> roomList = new JList<String>();

        JScrollPane roomListScroll = new JScrollPane(roomList);
        roomListScroll.setBounds(0, 0, ROOM_LIST_WIDTH, ROOM_LIST_HEIGHT);

        JButton createButton = new JButton("Criar");
        createButton.setBounds(0 * BUTTON_WIDTH, ROOM_LIST_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        createButton.addActionListener((event) -> {
            String roomName = JOptionPane.showInputDialog("Qual o nome da sala?");
            createRoom.accept(roomName);
        });

        JButton searchButton = new JButton("Procurar");
        searchButton.setBounds(1 * BUTTON_WIDTH, ROOM_LIST_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        searchButton.addActionListener((event) -> {
            String[] rooms = getRooms.get().toArray(new String[0]);
            roomList.setListData(rooms);
        });

        this.setTitle("Chat");
        this.setSize(800, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        this.add(roomListScroll);
        this.add(createButton);
        this.add(searchButton);
    }
}
