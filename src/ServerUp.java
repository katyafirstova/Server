import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUp {

    static final Logger LOG = LoggerFactory.getLogger(ServerUp.class);
    static final String LOCALHOST = "localhost";
    static final int PORT = 9023;

    public void run() {
        try {
            DatagramChannel server = DatagramChannel.open();
            server.configureBlocking(false);
            InetSocketAddress iAdd = new InetSocketAddress("localhost", PORT);
            server.bind(iAdd);
            while (true) {
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                SocketAddress remoteAdd = server.receive(buffer);
                if (remoteAdd != null) {
                    buffer.flip();
                    int limits = buffer.limit();
                    byte[] bytes = new byte[limits];
                    buffer.get(bytes, 0, limits);
                    LOG.info(buffer.toString());





//                 CommandCollection cmd = message.getCommand();
//                    Worker worker = message.getWorker();

                }
            }


        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());

        }
    }

    public static void main(String[] args) {
        ServerUp server = new ServerUp();
        server.run();
    }

//    public void deserialize(Message message) throws IOException, ClassNotFoundException{
//        try (ByteArrayInputStream;
//             ObjectInputStream ois = new ObjectInputStream(fis)) {
//            Message message1 = (Message) ois.readObject();
//        }
//    }

}


