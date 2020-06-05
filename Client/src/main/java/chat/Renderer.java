/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Renderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

    static int prev=-1;
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       
    
        ImgsNText is = (ImgsNText) value;
        setIcon(is.getImg());
        setText(is.getName());
        
        
        if(isSelected){
            if(prev!=index && is.getType().equals("file") && (!(is.getName().endsWith(" Downloaded Successfully"))) && (!(is.getName().endsWith("resend")))){
                String fileName=is.getName();
                String [] secretKeyList=is.getSecretKeyList();
                int indexOfDot=fileName.indexOf('.');
                String extension=fileName.substring(indexOfDot, fileName.length());
                fileName=fileName.substring(0,indexOfDot);
                int i;
                int numberOfFiles=is.getNumberOfFiles();//3;//
                System.out.println("Renderer Numbeof files"+numberOfFiles);
                
                
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
                
                
                
                for(i=1;i<=numberOfFiles;i++){
                    String splitFileName="encryption\\"+fileName+"_"+i+extension;
                    try {
                        downloadFile(splitFileName);
                    } catch (Exception ex) {
                        Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    downloadFile("Digital_Signature\\"+fileName+".txt");
                    downloadFile("Keys\\"+fileName+".pub");
                    //downloadFile("Keys\\"+fileName+".txt");
                    try{
                        numberOfFiles=new ControllerFile().startFile(fileName,extension,2,secretKeyList, numberOfFiles);
                    }catch(Exception ex){
                        numberOfFiles=-1;
                        System.out.println(ex);
                    }                    
                    if(numberOfFiles==-1){
                        System.out.println("Signature Authentication Failed");
                        is.setName(fileName+extension+" File might have beeen manipulated by 3rd person ask Sender to resend");
                        is.setImg(new ImageIcon("C:\\Users\\tejas\\Desktop\\ChatAppImages\\error.png"));
                    }else{
                        System.out.println("File Downloaded Successfully");
                        is.setName(fileName+extension+" Downloaded Successfully");
                        is.setImg(new ImageIcon("C:\\Users\\tejas\\Desktop\\ChatAppImages\\done.png"));
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            prev=index;
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        if(is.getAllign()=="send"){
            setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }else{
            setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        }
        setEnabled(true);
        setFont(list.getFont());
        
        return this;
    }
    
    /* old
    private void downloadFile(String fileName_to_download)throws Exception{
        Socket sock = new Socket("localhost", 3333);
        PrintWriter outputToServer = new PrintWriter(sock.getOutputStream(), true);
//        outputToServer.println("encryption\\"+fileName_to_download);//specify the filename here
        outputToServer.println(fileName_to_download);//specify the filename here
        byte[] mybytearray = new byte[1024];
        InputStream is = sock.getInputStream();
//        FileOutputStream fos = new FileOutputStream("decryption\\"+fileName_to_download);//give name that u want to give to saved file
        FileOutputStream fos = new FileOutputStream(fileName_to_download);//give name that u want to give to saved file
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        //int bytesRead = 0;
        while(is.read(mybytearray)>0){
            bos.write(mybytearray);
        }
        bos.close();
        sock.close();
    }*/     
        private void downloadFile(String fileName_to_download)throws Exception{
            Socket sock = new Socket("localhost", 3333);
            PrintWriter outputToServer = new PrintWriter(sock.getOutputStream(), true);
        //    outputToServer.println("encryption\\"+fileName_to_download);//specify the filename here
            outputToServer.println(fileName_to_download);//specify the filename here
            //outputToServer.close();
            byte[] mybytearray = new byte[1024];
            InputStream is = sock.getInputStream();
            FileOutputStream fos = new FileOutputStream(fileName_to_download);//give name that u want to give to saved file
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int bytesRead = 0;
            bytesRead = is.read(mybytearray, 0, mybytearray.length);
            while(bytesRead != -1){
                //System.out.println(mybytearray.length+"   "+bytesRead+" "+mybytearray);
                bos.write(mybytearray, 0, bytesRead);
                bytesRead = is.read(mybytearray, 0, mybytearray.length);
            }
            bos.close();    
            sock.close();
        }
        
    
    
    /*
    private void downloadButtonClicked(java.awt.event.MouseEvent evt) {
        String fileName="tejas2";
        String extension=".txt";
        int numberOfFiles=3;
        
        int i;
        try {
            for(i=1;i<=numberOfFiles;i++){
                String splitFileName="decryption\\"+fileName+"_"+i;
                System.out.println(splitFileName);
                downloadFileServer(splitFileName+extension);
            
                        
            }
        } catch (Exception ex) {
            Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
    
    private void downloadFileServer(String fileName_to_download) throws Exception{
        
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        int FILE_SIZE=1024;
        try {
          sock = new Socket("localhost", 3332);
          // receive file
          byte [] mybytearray  = new byte [FILE_SIZE];
          InputStream is = sock.getInputStream();
          fos = new FileOutputStream(fileName_to_download);
          bos = new BufferedOutputStream(fos);
          bytesRead = is.read(mybytearray,0,mybytearray.length);
          current = bytesRead;

          do {
             bytesRead =
                is.read(mybytearray, current, (mybytearray.length-current));
             if(bytesRead >= 0) current += bytesRead;
          } while(bytesRead > -1);

          bos.write(mybytearray, 0 , current);
          bos.flush();
          System.out.println("File " + fileName_to_download
              + " downloaded (" + current + " bytes read)");
        }
        finally {
          if (fos != null) fos.close();
          if (bos != null) bos.close();
          if (sock != null) sock.close();
        }
        
    }
    */
    
    
}
