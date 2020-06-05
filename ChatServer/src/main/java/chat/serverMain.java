/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.*;
import java.io.*;
import java.util.*;


public class serverMain {
    ArrayList clientOutputStreams;
    ArrayList<String> onlineUsers = new ArrayList();
    
    Dictionary geek = new Hashtable();
    
    
    public class ClientHandler implements Runnable{
        
        BufferedReader reader;
        Socket sock;
        PrintWriter client;
        
        public ClientHandler(Socket ClientSocket, PrintWriter user){
        
            client = user;
            try{
                sock=ClientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
                
            
            }catch(Exception ex){
                System.out.println("error beginning streamreader");
            
            }

        }

        @Override
        public void run() {
            String message;
            String[] data;
            String connect="Connect";
            String chat="Chat";
            String disconnect="Disconnect";
            String file="File";
            
            try{
                while((message = reader.readLine())!=null){
                    System.out.println("Received:"+ message);
                    data = message.split("!");
                    for(String token:data){
                    System.out.println(token);
                    }
                    
                if(data[2].equals(connect)){
                    tellEveryOne(data[0] + "!" + data[1]+"!"+ chat);
                    clientOutputStreams.add(client);
                    geek.put(data[0], client);
                    System.out.println(geek);
                    userAdd(data[0]);
                
                }else if(data[2].equals(disconnect)){
                //    tellEveryOne(data[0] + "!has disconnected"+"!"+ chat);
                    userRemove(data[0]);
            
                }else if(data[2].equals(chat)){
                    tellOne(message);
   
                }
                else if(data[2].equals(file)){
                    tellOneFile(message);
   
                }
                else{
                    System.out.println("No condition were met");
                    
                }
                }
            }catch(Exception ex){
                  System.out.println("lost a connection");
                  clientOutputStreams.remove(client);
                    
            }
          
        }

    }
    
    public static void main(String[] args){
    
        new serverMain().go();
    
    } 
    
    
    
    
    
    public void go(){
    
    clientOutputStreams = new ArrayList();
    
    try{
            ServerSocket serverSock = new ServerSocket(5000);
            
            while(true){
                Socket clientSock = serverSock.accept();
                PrintWriter writer =  new PrintWriter(clientSock.getOutputStream());
               // clientOutputStreams.add(writer);
               
                Thread listener = new Thread(new ClientHandler(clientSock,writer));
                listener.start();
                System.out.println("got a connection");
            }
    
    
    }catch(Exception ex){
            
        System.out.print("Error making a connection.");
    
    }
    
    
    
    }
    
    
    public void userAdd(String data){
        String message;
        String add ="! !Connect",done="Server! !Done";
        onlineUsers.add(data);
        String[] tempList = new String[(onlineUsers.size())];
        onlineUsers.toArray(tempList);
        
        for(String token:tempList){
        
            message=(token + add);
            tellEveryOne(message);
        
        }
        tellEveryOne(done);
    }
    
    public void userRemove(String data){
        String message;
        String add ="! !Connect",done="Server! !Done";
        onlineUsers.remove(data);
        String[] tempList = new String[(onlineUsers.size())];
        onlineUsers.toArray(tempList);
        
        for(String token:tempList){
        
            message=(token + add);
            tellEveryOne(message);
        
        }
        tellEveryOne(done);
    }
    
    public void tellEveryOne(String message){
       
        Iterator it = clientOutputStreams.iterator();
        String dat[] = message.split("!");
        while(it.hasNext()){
            try{
              //  System.out.println(it.next());
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                System.out.println("Sending" + message);
                writer.flush();
            
            
            }catch(Exception ex){
                System.out.println("error telling everyone");
            }
        
        } 
 }
    
    public void tellOne(String message){
       
        
        String dat[] = message.split("!");
        
 /*old           try{
                PrintWriter writer1 = (PrintWriter)geek.get(dat[3]);
                writer1.println(message);
                PrintWriter writer2 = (PrintWriter)geek.get(dat[0]);
                writer2.println(message);
                writer1.flush();
                writer2.flush();
                System.out.println("Sending" + message);
            
                
            
            }catch(Exception ex){
                System.out.println("error telling everyone");
            }*/
            try{
                System.out.println(dat[0]+dat[3]);
                if (!dat[0].equals(dat[3])){
                    PrintWriter writer = (PrintWriter)geek.get(dat[3]);
                    writer.println(message);
                    System.out.println("Sending " + message +" inside------");
                    writer.flush();
                }
            }catch(Exception ex){
                System.out.println("error telling everyone");
            }
        
        
 }   
    public void tellOneFile(String message){
       
        
        String dat[] = message.split("!");
        
            /*old
            try{
              
                PrintWriter writer2 = (PrintWriter)geek.get(dat[3]);
                writer2.println(message);
              
                writer2.flush();
                System.out.println("----------------------------------Sending file to rec---------------------------------------");
                System.out.println("Sending" + message);
            
                
            
            }catch(Exception ex){
                System.out.println("error telling everyone");
            }*/
            
        try{
                if(!dat[0].equals(dat[3])){
                    PrintWriter writer = (PrintWriter)geek.get(dat[3]);
                    writer.println(message);
                    writer.flush();
                    System.out.println("----------------------------------Sending file to rec---------------------------------------");
                    System.out.println("Sending" + message);
                }
                
           
            }catch(Exception ex){
                System.out.println("error telling everyone");
            }
        
        
 }   
    
}
