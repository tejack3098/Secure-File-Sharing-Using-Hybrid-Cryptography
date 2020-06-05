import java.io.RandomAccessFile;  
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
 
public class ServerRegister extends Thread {
    public static final int PORT = 3335;
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
 
            while (true) {
                Socket s = serverSocket.accept();
                register(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     private void register(Socket sock) throws Exception {
        try{
            BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            String userName=inputFromClient.readLine();
            String password=inputFromClient.readLine();
            String credentials=userName+"!"+password;
            BufferedWriter out = new BufferedWriter(new FileWriter("credentials.txt", true)); 
            out.write(credentials+"\n"); 
            out.close(); 
            PrintWriter outputToClient = new PrintWriter(sock.getOutputStream(), true);
            outputToClient.println(1);
            sock.close();
        }
        catch (Exception e){
            PrintWriter outputToClient = new PrintWriter(sock.getOutputStream(), true);
            outputToClient.println(0);
            sock.close();
        }
            
    }
    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }
 
    public static void main(String[] args) {
        String encryptionFolderName, decryptionFolderName, keysFolderName, digitalSinatureFolderName;
                
        encryptionFolderName="encryption";
        decryptionFolderName="decryption";
        keysFolderName="Keys";
        digitalSinatureFolderName="Digital_Signature";
        if(!new File(encryptionFolderName).isDirectory()){
            new File(encryptionFolderName).mkdirs();
        }
        if(!new File(decryptionFolderName).isDirectory()){
            new File(decryptionFolderName).mkdirs();
        }
        if(!new File(keysFolderName).isDirectory()){
            new File(keysFolderName).mkdirs();
        }
        if(!new File(digitalSinatureFolderName).isDirectory()){
            new File(digitalSinatureFolderName).mkdirs();
        }
        new ServerRegister().start();
    }
}  
