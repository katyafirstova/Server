import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import core.WorkerCollection;
import model.CommandCollection;
import model.Message;
import model.User;
import model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerUp {

    static final Logger LOG = LoggerFactory.getLogger(ServerUp.class);
    static final String LOCALHOST = "localhost";
    static final int PORT_SERVER = 9023;
    static final int PORT_CLIENT = 43245;
    private final String serverAddr;


    WorkerCollection collection = new WorkerCollection();

    public ServerUp(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public ServerUp() {
        this.serverAddr = LOCALHOST;
        initCollection();
    }

    private void initCollection() {
        this.collection.load();
    }


    public void run() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 20);
            DatagramChannel server = DatagramChannel.open();
            server.configureBlocking(true);
            InetSocketAddress iAdd = new InetSocketAddress(LOCALHOST, PORT_SERVER);
            server.bind(iAdd);
            while (true) {
                buffer.clear();
                SocketAddress client = server.receive(buffer);
                if (client != null) {
                    buffer.flip();
                    Runnable processor = new Runnable() {

                        private ByteBuffer buf;
                        private InetSocketAddress cln;

                        @Override
                        public void run() {
                            LOG.info("Receive buffer: " + buf.toString());
                            processRequest(this.buf, this.cln);
                        }

                        public Runnable init(ByteBuffer buf, InetSocketAddress cln) {
                            this.buf = buf;
                            this.cln = cln;
                            return this;
                        }
                    }.init(buffer, (InetSocketAddress) client);
                    Thread thread = new Thread(processor);
                    thread.start();
                }
            }
        } catch (IOException e) {
            LOG.info(e.getLocalizedMessage());

        } catch (Exception e) {
            LOG.info(e.getLocalizedMessage());
        }
    }
    private synchronized void processRequest(ByteBuffer buffer, InetSocketAddress client) {
        try {
            Message message = deserialize(buffer);
            if (message != null) {
                CommandCollection cmd = message.getCollection();
                Long currentUserId = -1l;
                if(message.getWorker() != null) {
                    currentUserId =  message.getWorker().getUserId();
                }
                if (cmd != null) {
                    LOG.debug(cmd.getCommand());
                    switch (cmd) {
                        case INSERT:
                            collection.insert(message.getWorker());
                            break;

                        case SHOW:
                            Message clientMessageShow = new Message(CommandCollection.SHOW, collection.getWorkers());
                            sendMessage(serialize(clientMessageShow), client);
                            break;

                        case INFO:
                            Message clientMessageInfo = new Message(
                                    CommandCollection.INFO,
                                    collection.getInitData(),
                                    collection.getWorkers()
                            );
                            sendMessage(serialize(clientMessageInfo), client);
                            break;

                        case REMOVE_KEY:
                            collection.removeKey(message.getKey(), currentUserId);
                            break;

                        case CLEAR:
                            collection.clear(currentUserId);
                            break;

                        case REMOVE_GREATER:
                            collection.removeGreater(message.getSalary(), currentUserId);
                            break;

                        case REMOVE_LOWER:
                            collection.removeLower(message.getSalary(), currentUserId);
                            break;

                        case REMOVE_ALL_BY_END_DATE:
                            collection.removeAllByEndDate(message.getDate(), currentUserId);
                            break;

                        case REMOVE_ALL_BY_START_DATE:
                            collection.removeAnyByStartDate(message.getStartDate(), currentUserId);
                            break;

                        case PRINT_FIELD_DESCENDING_END_DATE:
                            Message clientMessageDate = new Message(
                                    CommandCollection.PRINT_FIELD_DESCENDING_END_DATE,
                                    collection.getWorkers()
                            );
                            sendMessage(serialize(clientMessageDate), client);
                            break;

                    }
                }
            }
        } catch (Exception e) {
            LOG.debug(e.getLocalizedMessage());
        }
    }

    private void sendMessage(ByteBuffer buffer, InetSocketAddress clientAddr) throws IOException {
        String clientHost = clientAddr.getHostName();
        if (buffer != null) {
            DatagramChannel client = null;
            client = DatagramChannel.open();
            client.bind(null);
            InetSocketAddress serverAddress = new InetSocketAddress(clientHost, PORT_CLIENT);
            client.send(buffer, serverAddress);
            client.close();
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
            LOG.debug(e.getMessage());
            e.printStackTrace();
        }
        return message;
    }

    public ByteBuffer serialize(Message message) {
        ByteBuffer buffer = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(message);
            buffer = ByteBuffer.wrap(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public ByteBuffer receiveMessage() throws IOException {
        DatagramChannel server = DatagramChannel.open();
        server.configureBlocking(true);
        InetSocketAddress iAdd = new InetSocketAddress(this.serverAddr, PORT_CLIENT);
        server.bind(iAdd);
        ByteBuffer in = ByteBuffer.allocate(81920);
        server.receive(in);
        server.close();
        return in;
    }
}

