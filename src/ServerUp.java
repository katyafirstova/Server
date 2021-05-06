import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import core.WorkerCollection;
import model.CommandCollection;
import model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUp {

    static final Logger LOG = LoggerFactory.getLogger(ServerUp.class);
    static final String LOCALHOST = "localhost";
    static final int PORT = 9023;

    WorkerCollection collection = new WorkerCollection();

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
                    processRequest(buffer);
                }
            }
        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());

        }
    }

    private void processRequest(ByteBuffer buffer) {
        try {
            Message message = deserialize(buffer);
            if (message != null) {
                CommandCollection cmd = message.getCollection();
                if (cmd != null) {
                    LOG.debug(cmd.getCommand());
                    switch (cmd) {
                        case INSERT:
                            collection.insert(message.getWorker());


                    }
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        ServerUp server = new ServerUp();
        server.run();
    }

    public Message deserialize(ByteBuffer buffer) {
        Message message = null;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array());
             ObjectInputStream in = new ObjectInputStream(bis);) {
            message = (Message) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOG.debug(e.getLocalizedMessage());
        }
        return message;
    }
}


