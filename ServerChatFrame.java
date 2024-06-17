import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.util.ArrayList;

class ServerChatFrame extends JFrame {

    JList<String> roomList;

    ServerChatFrame(ServerChat server) {
        this.roomList = new JList<String>();

        JScrollPane roomListScroll = new JScrollPane(roomList);
        roomListScroll.setBounds(0, 0, 800, 200);

        JButton closeButton = new JButton("Fechar Sala Selecionada");
        closeButton.setBounds(0, 200, 250, 50);
        closeButton.addActionListener((event) -> {
            // Fecha a sala selecionada.
            String roomName = roomList.getSelectedValue();
            // Se não houver nenhuma sala selecionada, não faz nada.
            if (roomName == null) {
                // Mostra uma mensagem de erro.
                JOptionPane.showMessageDialog(this, "Selecione uma sala para fechar.");
            } else {
                try {
                    // Fecha a sala.
                    server.closeRoom(roomName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.setTitle("Chat Server");
        this.setSize(800, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        this.add(roomListScroll);
        this.add(closeButton);
    }

    public void updateRoomList(ArrayList<String> roomList) {
        // Atualiza a lista de salas.
        this.roomList.setListData(roomList.toArray(new String[0]));
    }
}