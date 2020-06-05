
package chat;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
/**
 * @author tejas
 */

public class ChatClient extends javax.swing.JFrame {
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    ArrayList<String> userList = new ArrayList();
    Boolean isConnected = false;
    String file_name,selected;
    File f;
    
    DefaultListModel jlm= new DefaultListModel();
    DefaultListModel chatlistmodel = new DefaultListModel();


    public ChatClient() {
        initComponents();
        System.out.println(ChatLogin.username);
        userslistview.setModel(jlm);
        chatlistview.setModel(chatlistmodel);
        chatlistview.setCellRenderer(new Renderer());
        userslistview.setCellRenderer(new Userlistrenderer());
        
        try{
            sock = new Socket("127.0.0.1",5000);
            InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamreader);
            writer = new PrintWriter(sock.getOutputStream());
            writer.println(ChatLogin.username + "! has Connected.. !Connect");
            writer.flush();
            isConnected = true;
            usernametitle.setText(ChatLogin.username);
            userstatus.setText("Connected");
  
        }catch(Exception e){
             chatlistmodel.addElement(new ImgsNText("Cannot Connect! Try Again. ", new ImageIcon(),"chat","center"));
            // chatlistmodel.addElement("Cannot Connect! Try Again. \n");
            
        }
        ListenThread();
       
    }
    
    public class IncomingReader implements Runnable{

        @Override
        public void run() {
            String stream;
            String[] data;
            String done="Done",connect="Connect",disconnect="Disconnect",chat="Chat",file="File";
            
            try{
                while((stream = reader.readLine())!=null){
                
                data=stream.split("!");
                
                if(data[2].equals(chat)){
                    chatlistmodel.addElement(new ImgsNText(data[0] + ":" +data[1], new ImageIcon(),"chat","rec"));
                 
                }else if(data[2].equals(file)){
                    chatlistmodel.addElement(new ImgsNText(data[1], new ImageIcon("C:\\Users\\tejas\\Desktop\\ChatAppImages\\download.png"),"file","rec",Integer.parseInt(data[4]),data[5]));
                    System.out.println("NumberOfFilessssssss"+data[4]);
                }
                else if(data[2].equals(connect)){
                    userAdd(data[0]);
                }
                else if(data[2].equals(disconnect)){
                    userRemove(data[0]);
                }
                else if(data[2].equals(done)){
                    
                    jlm.removeAllElements();
                    writeUsers();
                    userList.clear();
                
                }
                
                }
                
            }catch(Exception ex){
            
            
            }
        
        }
    
    }
    
    public void ListenThread(){
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }
    
    public void userAdd(String data){
        userList.add(data);
    }
    
    public void userRemove(String data){
        chatlistmodel.addElement(new ImgsNText(data + "has disconnected.", new ImageIcon(),"chat","center"));
        userList.remove(data);
    }
    
    public void writeUsers(){
        String[] tempList = new String[(userList.size())];
        userList.toArray(tempList);
        for(String token:tempList){
            jlm.addElement(new ImgsNText(token, new ImageIcon("C:\\Users\\tejas\\Desktop\\ChatAppImages\\user.png")));
        }
    }
    
    public void sendDisconnect(){
        
        String bye= (ChatLogin.username + "! !Disconnect");
        try{
            writer.println(bye);
            writer.flush();
        }catch(Exception ex){
         chatlistmodel.addElement(new ImgsNText("Could not send Disconnect Message. ", new ImageIcon(),"chat","center"));
        }   
    }
    
    public void Disconnect(){
        try{
            chatlistmodel.addElement(new ImgsNText("Disconnected.", new ImageIcon(),"chat","center"));

            sock.close();
        }catch(Exception ex){
            chatlistmodel.addElement(new ImgsNText("Failed to Disconnect.", new ImageIcon(),"chat","center"));
          //  chatlistmodel.addElement("Failed to Disconnect.");
        }
        isConnected=false;
        userstatus.setText("Disconnected");
        
        jlm.removeAllElements();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        userslistview = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        emojibtn = new javax.swing.JLabel();
        attachmentlbl = new javax.swing.JLabel();
        chatmsgtxt = new javax.swing.JTextField();
        sendbtn = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        usernametitle = new javax.swing.JLabel();
        userstatus = new javax.swing.JLabel();
        logoutbtn = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatlistview = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(5, 100, 153));

        jPanel2.setBackground(new java.awt.Color(5, 100, 153));

        jPanel4.setBackground(new java.awt.Color(8, 151, 230));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\home.png")); // NOI18N

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\contact.png")); // NOI18N

        jLabel3.setBackground(new java.awt.Color(5, 100, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\comment.png")); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 4, 0, 0, new java.awt.Color(255, 255, 255)));
        jLabel3.setOpaque(true);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\settings.png")); // NOI18N

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\user.png")); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(8, 151, 230));

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\search.png")); // NOI18N

        jTextField1.setBackground(new java.awt.Color(8, 151, 230));
        jTextField1.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Search");
        jTextField1.setBorder(null);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(jTextField1)
        );

        userslistview.setBackground(new java.awt.Color(5, 100, 153));
        userslistview.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        userslistview.setForeground(new java.awt.Color(255, 255, 255));
        userslistview.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        userslistview.setSelectionBackground(new java.awt.Color(8, 151, 230));
        userslistview.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userslistviewMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(userslistview);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3))
        );

        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        emojibtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        emojibtn.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\emoji.png")); // NOI18N
        emojibtn.setPreferredSize(new java.awt.Dimension(32, 40));

        attachmentlbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        attachmentlbl.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\attachment.png")); // NOI18N
        attachmentlbl.setPreferredSize(new java.awt.Dimension(32, 40));
        attachmentlbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attachmentlblMouseClicked(evt);
            }
        });

        chatmsgtxt.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        chatmsgtxt.setForeground(new java.awt.Color(153, 153, 153));
        chatmsgtxt.setText("Type your message here...");
        chatmsgtxt.setBorder(null);
        chatmsgtxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chatmsgtxtMouseClicked(evt);
            }
        });
        chatmsgtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatmsgtxtActionPerformed(evt);
            }
        });

        sendbtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sendbtn.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\send.png")); // NOI18N
        sendbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendbtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addComponent(emojibtn, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(attachmentlbl, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chatmsgtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sendbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(emojibtn, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(attachmentlbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(chatmsgtxt)
            .addComponent(sendbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(8, 151, 230));

        jPanel10.setBackground(new java.awt.Color(8, 151, 230));

        jLabel19.setBackground(new java.awt.Color(8, 151, 230));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\user.png")); // NOI18N
        jLabel19.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(8, 151, 230)));

        usernametitle.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        usernametitle.setForeground(new java.awt.Color(255, 255, 255));
        usernametitle.setText("Tejack");

        userstatus.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        userstatus.setForeground(new java.awt.Color(255, 255, 255));
        userstatus.setText("Typing...");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernametitle, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(userstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(usernametitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(userstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        logoutbtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoutbtn.setIcon(new javax.swing.ImageIcon("C:\\Users\\tejas\\Documents\\NetBeansProjects\\Client1\\src\\main\\java\\chat\\ChatAppImages\\logout.png")); // NOI18N
        logoutbtn.setPreferredSize(new java.awt.Dimension(32, 40));
        logoutbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutbtnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logoutbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logoutbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        chatlistview.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        chatlistview.setForeground(new java.awt.Color(5, 100, 153));
        chatlistview.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(chatlistview);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chatmsgtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatmsgtxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chatmsgtxtActionPerformed

    private void logoutbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutbtnMouseClicked
        sendDisconnect();
        Disconnect();
        
         dispose();
         new ChatLogin().setVisible(true);
    }//GEN-LAST:event_logoutbtnMouseClicked

    private void sendbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendbtnMouseClicked
        String nothing="";
        if((chatmsgtxt).getText().equals(nothing)){
            
            chatmsgtxt.setText("");
            chatmsgtxt.requestFocus();
        }else{
            try{
                chatlistmodel.addElement(new ImgsNText(ChatLogin.username+":"+chatmsgtxt.getText(),new ImageIcon(),"chat","send"));
                //writer.println(ChatLogin.username+"!"+chatmsgtxt.getText()+"!"+"Chat!"+selected+"!"+ChatLogin.username);
                writer.println(ChatLogin.username+"!"+chatmsgtxt.getText()+"!"+"Chat!"+selected);
                writer.flush();
            
            }catch(Exception ex){
                chatlistmodel.addElement(new ImgsNText("Message was not send.", new ImageIcon(),"chat","center"));
               // chatlistmodel.addElement("Message was not send. \n");
            }
            chatmsgtxt.setText("");
            chatmsgtxt.requestFocus();
        }
    }//GEN-LAST:event_sendbtnMouseClicked

    private static void copyFile(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);

            // buffer size 1K
            byte[] buf = new byte[1024];

            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    
    /*old*/
    private void uploadFileServer(String fileName_to_upload) throws Exception{
        Socket socket = new Socket("localhost", 3332);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        oos.writeObject(fileName_to_upload);
        FileInputStream fis = new FileInputStream(fileName_to_upload);
        byte [] buffer = new byte[500];
        Integer bytesRead = 0;

        while ((bytesRead = fis.read(buffer)) > 0) {
            oos.writeObject(bytesRead);
            oos.writeObject(Arrays.copyOf(buffer, buffer.length));
        }
        oos.close();
        ois.close();
        socket.close();
    }
    
    
    private void attachmentlblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attachmentlblMouseClicked
       
        JFileChooser ch = new JFileChooser();
        int x =ch.showOpenDialog(null);
        if(x==JFileChooser.APPROVE_OPTION){
        try{
        f= ch.getSelectedFile();
        file_name = f.getAbsolutePath();
        
        File file = new File(file_name);
        String fileName=file.getName();
        File destFile=new File(fileName);
        int indexOfDot=fileName.indexOf('.');
        String extension=fileName.substring(indexOfDot, fileName.length());
        fileName=fileName.substring(0,indexOfDot);
        copyFile(file,destFile);
        
        String[] secretKeyList =new ControllerFile().startFile(fileName,extension,1);
        int numberOfFiles=secretKeyList.length;
        int i;
        String secretKeyString="";
        for(i=1;i<=numberOfFiles;i++){
            secretKeyString+=secretKeyList[i-1]+"\t";
            
            String splitFileName="encryption\\"+fileName+"_"+i;
            System.out.println(splitFileName);
                       
            uploadFileServer(splitFileName+extension);
                        
        }
        uploadFileServer("Digital_Signature\\"+fileName+".txt");
        uploadFileServer("Keys\\"+fileName+".pub");
        //uploadFileServer("Keys\\"+fileName+".txt");
        writer.println(ChatLogin.username+"!"+file.getName()+"!"+"File!"+selected+"!"+numberOfFiles+"!"+secretKeyString);
        writer.flush();
        
        chatlistmodel.addElement(new ImgsNText(file.getName(), new ImageIcon("C:\\Users\\tejas\\Desktop\\ChatAppImages\\download.png"),"file","send",numberOfFiles,secretKeyString)); 

        }catch(Exception e){
        
            System.out.println(e);
        }
        
        
        }
    }//GEN-LAST:event_attachmentlblMouseClicked

    
    private void chatmsgtxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chatmsgtxtMouseClicked
       //chatmsgtxt.setText("");
       if("Type your meassage here...".equals(chatmsgtxt.getText()))
           chatmsgtxt.setText("");
    }//GEN-LAST:event_chatmsgtxtMouseClicked

    private void userslistviewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userslistviewMouseClicked
         Object val = userslistview.getSelectedValue();
            ImgsNText is = (ImgsNText) val;
            Object mselected =is.getName();
            selected = mselected.toString();
    }//GEN-LAST:event_userslistviewMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel attachmentlbl;
    private javax.swing.JList<String> chatlistview;
    private javax.swing.JTextField chatmsgtxt;
    private javax.swing.JLabel emojibtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel logoutbtn;
    private javax.swing.JLabel sendbtn;
    private javax.swing.JLabel usernametitle;
    private javax.swing.JList<String> userslistview;
    private javax.swing.JLabel userstatus;
    // End of variables declaration//GEN-END:variables
}
