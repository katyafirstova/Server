import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserUtlis {
    static final Logger LOG = LoggerFactory.getLogger(UserUtlis.class);

    private void getAndCheckHashedPassword(String hashedPassword) throws IOException {
        ServerUp server = new ServerUp();
        LOG.info("Received from client:" + server.receiveMessage().toString());
        


    }
}