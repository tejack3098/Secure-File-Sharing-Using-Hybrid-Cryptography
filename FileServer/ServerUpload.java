import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
 
public class ServerUpload extends Thread {
    public static final int PORT = 3332;
    public static final int BUFFER_SIZE = 500;
 
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
 
            while (true) {
                Socket s = serverSocket.accept();
                saveFile(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    private void saveFile(Socket socket) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        FileOutputStream fos = null;
        byte [] buffer = new byte[BUFFER_SIZE];
 
        // 1. Read file name.
        Object o = ois.readObject();
        String fileName="";
 
        if (o instanceof String) {
            fileName=o.toString();
            fos = new FileOutputStream(fileName);
            //fos = new FileOutputStream(new File("D:\\temp\\test123.txt"));
        } else {
            throwException("Something is wrong");
        }
 
        // 2. Read file to the end.
        Integer bytesRead = 0;
        try{
            do {
                o = ois.readObject();
     
                if (!(o instanceof Integer)) {
                    throwException("Something is wrong");
                }
     
                bytesRead = (Integer)o;

                //System.out.println("Before client read");
     
                o = ois.readObject();

                //System.out.println("After client read");
     
                if (!(o instanceof byte[])) {
                    throwException("Something is wrong");
                }
     
                buffer = (byte[])o;
     
                // 3. Write data to output file.
                //System.out.println("Before server read");

                fos.write(buffer, 0, bytesRead);

                //System.out.println("After server read");
               
            } while (bytesRead == BUFFER_SIZE);
        }
        catch(Exception e){
        
            System.out.println(e);
        }
        finally{
             
            System.out.println("File transfer success");
             
            fos.close();
     
            ois.close();
            oos.close();
        }
        
    }
 
    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }
 
    public static void main(String[] args) {
        String encryptionFolderName, decryptionFolderName, keysFolderName, digitalSinatureFolderName;
                
        encryptionFolderName="encryption";
        //decryptionFolderName="decryption";
        //keysFolderName="Keys";
        digitalSinatureFolderName="Digital_Signature";
        if(!new File(encryptionFolderName).isDirectory()){
            new File(encryptionFolderName).mkdirs();
        }
        // if(!new File(decryptionFolderName).isDirectory()){
        //     new File(decryptionFolderName).mkdirs();
        // }
        // if(!new File(keysFolderName).isDirectory()){
        //     new File(keysFolderName).mkdirs();
        // }
        if(!new File(digitalSinatureFolderName).isDirectory()){
            new File(digitalSinatureFolderName).mkdirs();
        }
        new ServerUpload().start();
    }
}  
