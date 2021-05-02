import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Message implements Serializable {

    public void deserialize(Message message) throws IOException, ClassNotFoundException{
        try (FileInputStream fis = new FileInputStream("temp.out");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
             Message message1 = (Message) ois.readObject();
        }
    }
}
