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
 
public class ServerVerify extends Thread {
    public static final int PORT = 3334;
 
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
 
            while (true) {
                Socket s = serverSocket.accept();
                verify(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     private void verify(Socket sock) throws Exception {
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String userName=inputFromClient.readLine();
        String password=inputFromClient.readLine();
        //System.out.println(userName+" "+password);
        BufferedReader keys_br = new BufferedReader(new FileReader(new File("credentials.txt")));
        String credentials=keys_br.readLine();
        String [] data;
        while(credentials!=null){
            data=credentials.split("!");
            if(data[0].equals(userName)&&data[1].equals(password)){
                PrintWriter outputToServer = new PrintWriter(sock.getOutputStream(), true);
                outputToServer.println(1);
                sock.close();
                //System.out.println(1);
                return;
            }
            credentials=keys_br.readLine();
        }        
        PrintWriter outputToServer = new PrintWriter(sock.getOutputStream(), true);
        outputToServer.println(0);
        
        sock.close();
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
        new ServerVerify().start();
    }
}  
