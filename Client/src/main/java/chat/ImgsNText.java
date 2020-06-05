/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

/**
 *
 * @author tejas
 */
import javax.swing.Icon;
import javax.swing.ImageIcon;


public class ImgsNText {
    
    private String name;
    private ImageIcon img;
    private String type, allign;
    private int numberOfFiles;
    private String secretKeyString;
    
    public String[] getSecretKeyList(){
//        String []tp=this.secretKeyString.split("\t");
//        System.out.println("SecretKey String   "+this.secretKeyString+"0"+tp[0]+"1"+tp[1]+"2"+tp[2]+"end");
        return this.secretKeyString.split("\t");
    }

    public ImgsNText(String text,ImageIcon icon){
        this.name = text;
        this.img = icon;
  
        
    }
    
    public ImgsNText(String text,ImageIcon icon, String type, String allign){
        this.name = text;
        this.img = icon;
        this.type = type;
        this.allign=allign;
        
    }
    
    public ImgsNText(String text,ImageIcon icon, String type, String allign, int numberOfFiles){
        this.name = text;
        this.img = icon;
        this.type = type;
        this.numberOfFiles=numberOfFiles;
        this.allign=allign;
        
    }
    
    public ImgsNText(String text,ImageIcon icon, String type, String allign, int numberOfFiles, String secretKeyString){
        this.name = text;
        this.img = icon;
        this.type = type;
        this.numberOfFiles=numberOfFiles;
        this.allign=allign;   
        this.secretKeyString=secretKeyString;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getAllign() {
        return allign;
    }

    public void setAllign(String allign) {
        this.allign = allign;
    }
    
    public int getNumberOfFiles(){
        return numberOfFiles;
    }
    
    
}
