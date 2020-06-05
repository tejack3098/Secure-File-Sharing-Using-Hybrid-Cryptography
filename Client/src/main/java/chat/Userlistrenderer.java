/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class Userlistrenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {


    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
       
    
        ImgsNText is = (ImgsNText) value;
        setIcon(is.getImg());
        setText(is.getName());
        
        
        if(isSelected){
   
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        
        }else{
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
     
        setEnabled(true);
        setFont(list.getFont());
        
        return this;
    }
    
    
}
