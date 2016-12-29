/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import Logic.*;
import Domain.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.jdesktop.swingx.prompt.PromptSupport;
/**
 *
 * @author Ooi
 */
public class productRegistration extends JPanel{
    private productRecord pRecord;
    private Product prod = null;
    
    private JTextField productID = new JTextField();
    private JTextField productName = new JTextField();
    private JTextField price = new JTextField();
    private JTextField decimal = new JTextField();
    
    private JComboBox type;
    
    private JButton register = new JButton("Register");
    private JButton reset = new JButton("Reset");
    
    private JPanel form = new JPanel(new GridLayout(4, 2));
    private JPanel productPrice = new JPanel(new GridLayout(1, 2));
    private JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    int access, token, numChar, number;
    String ctgy, productCost, productIDString, incrementID;
    char checkChar;
    
    public productRegistration(){
        pRecord = new productRecord();
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Product Registration Form"));
        String list[] = {"Anti-infective drugs", "Respiratory drugs", "Neurological drugs", "Psychotropic drugs", "Antibiotic", "Supplement", "Gel", "Desinfectant", "Cream" , "Cleanser", "Lotion"};
        type = new JComboBox(list);
        
        productPrice.add(price);
        productPrice.add(decimal);
                
        form.add(new JLabel("Product ID"));
        form.add(productID);
        form.add(new JLabel("Product Name"));
        form.add(productName);
        form.add(new JLabel("Product Type"));
        form.add(type);
        form.add(new JLabel("Product Price"));
        form.add(productPrice);
        
        south.add(register);
        south.add(reset);
        
        add(form, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        
        IDUpdate();
        
        // disable
        productID.setEnabled(false);
        
        PromptSupport.setPrompt("0000", price);
        PromptSupport.setPrompt("00", decimal);
        
        //limit user input
        price.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        decimal.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(productID.getText().isEmpty() || productName.getText().isEmpty() || price.getText().isEmpty() || decimal.getText().isEmpty()){
                   JOptionPane.showMessageDialog(null, "Please fill up each field.", "Warning", JOptionPane.WARNING_MESSAGE);
                   
               }else{
                   token = 0;
                   if(productID.getText().length() > 5){
                       JOptionPane.showMessageDialog(null, "ID length cannot more than 5 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                       token = 1;
                   }else if(productName.getText().length() > 50){
                       JOptionPane.showMessageDialog(null, "Product name length cannot more than 50 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                       token = 1;
                   }else if(Integer.parseInt(price.getText()) > 9999 ||  Integer.parseInt(price.getText()) < 0 ||  Integer.parseInt(price.getText()) == 0){
                       JOptionPane.showMessageDialog(null, "Price cannot more than 9999 or lesser than 0.", "Warning", JOptionPane.WARNING_MESSAGE);
                       token = 1;
                   }else if(Integer.parseInt(decimal.getText()) > 99 || Integer.parseInt(decimal.getText()) < 0 || Integer.parseInt(decimal.getText()) == 0){
                       JOptionPane.showMessageDialog(null, "There is only from 0 cent to 99 cent.", "Warning", JOptionPane.WARNING_MESSAGE);
                       token = 1;
                   }
                   
                   if(token == 0){
                      access = JOptionPane.showConfirmDialog(null, "Are You Sure The Data That You Enter Is Corrected", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                      if(access == 0){
                          ctgy = "" + type.getSelectedItem();
                          productCost = price.getText() + "." + decimal.getText();
                          pRecord.CreateRecord(productID.getText(), productName.getText(), ctgy, Double.parseDouble(productCost));
                          JOptionPane.showMessageDialog(null, "Register Successful", "Message", JOptionPane.INFORMATION_MESSAGE);
                          IDUpdate();
                          reset();
                      }
                   }
                   
               }
            }
        });
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }
    
    public void IDUpdate(){
        prod = pRecord.IDIncrement();
        pRecord.numProduct();
        numChar = 0;
        if(pRecord.numProduct() != 0){
            for(int i = 0 ; i < prod.getProductID().length(); i++){
                checkChar = prod.getProductID().charAt(i);
                if(Character.isLetter(checkChar) || checkChar == '0'){
                    numChar++;
                }else{
                    break;
                }   
            }

            productIDString = prod.getProductID();
            if(numChar == 1){
                productIDString = productIDString.replaceAll("P", "");
                number = Integer.parseInt(productIDString) + 1;
                incrementID = 'P' + Integer.toString(number);
            }else if(numChar == 2){
                productIDString = productIDString.replaceAll("P0", "");
                number = Integer.parseInt(productIDString) + 1;
                incrementID = "P0" + Integer.toString(number);
                if(number == 1000){
                    incrementID = incrementID.replaceAll("P0", "");
                    incrementID = "P" + incrementID;
                }
            }else if(numChar == 3){
                productIDString = productIDString.replaceAll("P00", "");
                number = Integer.parseInt(productIDString) + 1;
                incrementID = "P00" + Integer.toString(number);
                if(number == 100){
                    incrementID = incrementID.replaceAll("P00", "");
                    incrementID = "P0" + incrementID;
                }
            }else if(numChar == 4){
                productIDString = productIDString.replaceAll("P000", "");
                number = Integer.parseInt(productIDString) + 1;
                incrementID = "P000" + Integer.toString(number);
                if(number == 10){
                    incrementID = incrementID.replaceAll("P000", "");
                    incrementID = "P00" + incrementID;
                }
            }else{
                incrementID = "";
                register.setEnabled(false);
                reset.setEnabled(false);
            }            
        }else{
            incrementID = "P0001";
        }
        
        productID.setText(incrementID);  
    }
    
    public void reset(){
        decimal.setText("");
        productName.setText("");
        type.setSelectedIndex(0);
        price.setText("");
    }

    public static void main(String[] args){
        new productRegistration();
    }
}
