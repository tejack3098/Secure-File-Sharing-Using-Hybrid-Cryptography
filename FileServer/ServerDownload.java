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
 
public class ServerDownload extends Thread {
    public static final int PORT = 3333;
    public static final int BUFFER_SIZE = 500;
 
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
 
            while (true) {
                Socket s = serverSocket.accept();
                sendFile(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    /*
    Second method
    private void sendFile(Socket sock) throws Exception {
        File myFile=null;
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        myFile=new File(inputFromClient.readLine());
        //myFile=new File("Keys\\tejas2.txt");
        //byte[] mybytearray = new byte[(int) myFile.length()];
        byte[] mybytearray = new byte[BUFFER_SIZE];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        OutputStream os = sock.getOutputStream();

        while(bis.read(mybytearray)>0){
            os.write(mybytearray);
            os.flush();
        }
        sock.close();
        System.out.println("File Sent Successfully");
    }*/
    private void sendFile(Socket sock) throws Exception {
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        String fileName=inputFromClient.readLine();
        byte[] mybytearray = new byte[BUFFER_SIZE];
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        OutputStream os = sock.getOutputStream();
        int bytesRead = 0;
        bytesRead = raf.read(mybytearray, 0, mybytearray.length);
        while(bytesRead!=-1){
             os.write(mybytearray, 0, bytesRead);
             //System.out.print("mybytearray "+mybytearray);
             bytesRead = raf.read(mybytearray, 0, mybytearray.length);
        }
        sock.close();
        System.out.println("File Sent Successfully");
    }

    /*

    private void sendFile(Socket sock) throws Exception {
        File myFile=null;
        BufferedReader inputFromClient = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        myFile=new File(inputFromClient.readLine());
        byte[] mybytearray = new byte[(int) myFile.length()];
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
        bis.read(mybytearray, 0, mybytearray.length);
        OutputStream os = sock.getOutputStream();
        os.write(mybytearray, 0, mybytearray.length);
        os.flush();
        sock.close();
        System.out.println("File Sent Successfully");
    }

    */
 
    public static void throwException(String message) throws Exception {
        throw new Exception(message);
    }
 
    public static void main(String[] args) {
        String encryptionFolderName, decryptionFolderName, keysFolderName, digitalSinatureFolderName;
                
        encryptionFolderName="encryption";
        // decryptionFolderName="decryption";
        // keysFolderName="Keys";
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
        new ServerDownload().start();
    }
}  
