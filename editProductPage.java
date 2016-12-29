/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Date;
import Logic.*;
import Domain.*;
import java.text.DecimalFormat;
/**
 *
 * @author Ooi
 */
public class editProductPage extends JPanel{
    private productRecord pRecord;
    private Product prod = null;
    
    private JPanel topSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel form = new JPanel(new GridLayout(3,2));
    private JPanel productPrice = new JPanel(new GridLayout(1,2));
    private JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    private JTextField productName = new JTextField();
    private JTextField productID = new JTextField(20);
    private JTextField price = new JTextField();
    private JTextField decimal = new JTextField();
    
    private JComboBox type;
    
    private JButton enter = new JButton("Enter");
    private JButton update = new JButton("Update");
    private JButton reset = new JButton("Reset");
    
    DecimalFormat deci = new DecimalFormat("0.00");
    int token, access, i;
    String ctgy, productCost, pID, cent;
    
    public editProductPage(){
        pRecord = new productRecord();
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Update Product Data"));
        String list[] = {"Anti-infective drugs", "Respiratory drugs", "Neurological drugs", "Psychotropic drugs", "Antibiotic", "Supplement", "Gel", "Desinfectant", "Cream" , "Cleanser", "Lotion"};
        type = new JComboBox(list);
        
        topSearch.setBorder(new TitledBorder("ID Search"));
        topSearch.add(new JLabel("Product ID:   P"));
        topSearch.add(productID);
        topSearch.add(enter);
        
        productPrice.add(price);
        productPrice.add(decimal);
        
        form.setBorder(new TitledBorder("Product Information"));
        form.add(new JLabel("Product Name"));
        form.add(productName);
        form.add(new JLabel("Product Type"));
        form.add(type);
        form.add(new JLabel("Product Price"));
        form.add(productPrice);
        
        //Disable
        productName.setEnabled(false);
        type.setEnabled(false);
        price.setEnabled(false);
        update.setEnabled(false);
        decimal.setEnabled(false);
        
        productID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
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

        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            if(productID.getText().length() == 4){
                pID = "P" + productID.getText();
                prod = pRecord.SelectRecord(pID);

                if(prod == null){
                    JOptionPane.showMessageDialog(null, "No such data.", "Warning", JOptionPane.WARNING_MESSAGE);
                    productID.setText("");     
                }else{
                    productID.setEnabled(false);
                    productName.setText(prod.getProductName());
                    i = 0;
                    while(Double.toString(prod.getPrice()).charAt(i) != '.'){
                        productCost = "" + Double.toString(prod.getPrice()).charAt(i);
                        i++;
                    }

                    if(prod.getType().equals("Anti-infective drugs")){
                        type.setSelectedIndex(0);
                    }else if(prod.getType().equals("Respiratory drugs")){
                        type.setSelectedIndex(1);
                    }else if(prod.getType().equals("Neurological drugs")){
                        type.setSelectedIndex(2);
                    }else if(prod.getType().equals("Psychotropic drugs")){
                        type.setSelectedIndex(3);
                    }else if(prod.getType().equals("Antibiotic")){
                        type.setSelectedIndex(4);
                    }else if(prod.getType().equals("Supplement")){
                        type.setSelectedIndex(5);
                    }else if(prod.getType().equals("Gel")){
                        type.setSelectedIndex(6);
                    }else if(prod.getType().equals("Desinfectant")){
                        type.setSelectedIndex(7);
                    }else if(prod.getType().equals("Cream")){
                        type.setSelectedIndex(8);
                    }else if(prod.getType().equals("Cleanser")){
                        type.setSelectedIndex(9);
                    }else if(prod.getType().equals("Lotion")){
                        type.setSelectedIndex(10);
                    }
                    cent = deci.format(prod.getPrice() - (int)prod.getPrice());
                    cent = cent.replaceAll("0.", "");
                    
                    price.setText(Integer.toString((int)prod.getPrice()));
                    decimal.setText(cent);
                    
                    //Enable All
                    enter.setEnabled(false);
                    update.setEnabled(true);
                    productName.setEnabled(true);
                    type.setEnabled(true);
                    price.setEnabled(true);
                    decimal.setEnabled(true);
                    
                }
            }else{
                    productID.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid data enter, please enter again.", "Warning", JOptionPane.WARNING_MESSAGE);
            }

            }
        });
        
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pID = "P" + productID.getText();
                prod = pRecord.SelectRecord(pID);
                if(prod != null){
                    if(productID.getText().isEmpty() || productName.getText().isEmpty() || price.getText().isEmpty() || decimal.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please fill up each field.", "Warning", JOptionPane.WARNING_MESSAGE);

                    }else{
                        token = 0;
                        if(productID.getText().length() > 5){
                            JOptionPane.showMessageDialog(null, "ID length cannot more than 5.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(productName.getText().length() > 50){
                            JOptionPane.showMessageDialog(null, "product name length cannot more than 50.", "Warning", JOptionPane.WARNING_MESSAGE);
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
                               pRecord.UpdateRecord(pID, productName.getText(), ctgy, Double.parseDouble(productCost));
                               JOptionPane.showMessageDialog(null, "Update Successful", "Message", JOptionPane.INFORMATION_MESSAGE); 
                               productID.setEnabled(true);
                               reset();
                           }
                        }

                    }                    
                }else{
                    JOptionPane.showMessageDialog(null, "No Such Data.", "Warning", JOptionPane.WARNING_MESSAGE);
                    reset();
                }

            }
        });
        
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        
        south.add(update);
        south.add(reset);
        add(topSearch, BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        //setSize(1000,1000);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setVisible(true);
    }
    
    public void reset(){
        productID.setText("");
        price.setText("");
        productName.setText("");
        decimal.setText("");
        type.setSelectedIndex(0);
        productName.setEnabled(false);
        type.setEnabled(false);
        price.setEnabled(false);
        decimal.setEnabled(false);
        update.setEnabled(false);
        productID.setEnabled(true);
        enter.setEnabled(true);
    }
    
    public static void main(String[] args){
        new editProductPage();
    }
}
