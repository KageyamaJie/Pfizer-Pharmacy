/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;

import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import Logic.*;
import Domain.*;

/**
 *
 * @author Ooi
 */
public class deleteProductPage extends JPanel{
    private productRecord pRecord;
    private Product prod = null;
    
    private JPanel topSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel form = new JPanel(new GridLayout(3,2));
    private JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    private JTextField productName = new JTextField();
    private JTextField productID = new JTextField(20);
    private JTextField price = new JTextField();
    private JTextField type = new JTextField();
    
    private JButton enter = new JButton("Enter");
    private JButton delete = new JButton("Delete");
    private JButton reset = new JButton("Reset");
    
    int charCount , i, access, cent;
    String pID, cost, centString;
    
    
    public deleteProductPage(){
        pRecord = new productRecord();
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Delete Product Data")); 
        
        topSearch.setBorder(new TitledBorder("ID Search"));
        topSearch.add(new JLabel("Product ID:   P"));
        topSearch.add(productID);
        topSearch.add(enter);
        
        form.setBorder(new TitledBorder("Product Information"));
        form.add(new JLabel("Product Name"));
        form.add(productName);
        form.add(new JLabel("Product Type"));
        form.add(type);
        form.add(new JLabel("Product Price"));
        form.add(price);
        
        // disable the field
        productName.setEnabled(false);
        type.setEnabled(false);
        price.setEnabled(false);
        delete.setEnabled(false);
        
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
        
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(productID.getText().length() == 4){
                    pID = "P" + productID.getText();
                    prod = pRecord.SelectRecord(pID);
                    if(prod != null){
                        productID.setEnabled(false);
                        productName.setText(prod.getProductName());
                        type.setText(prod.getType());
                        price.setText(Double.toString(prod.getPrice()));
                        delete.setEnabled(true);
                        enter.setEnabled(false);
                        
                    }else{
                        productID.setText("");
                        JOptionPane.showMessageDialog(null, "No such data.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    productID.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid data enter, please enter again.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                access = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Update The Information of This Product Record?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                if(access == 0){
                    pRecord.DeleteRecord(pID);
                    JOptionPane.showMessageDialog(null, "Delete Successful", "Message", JOptionPane.INFORMATION_MESSAGE); 
                    
                    reset();
                }

            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productID.setEnabled(true);
                reset();
            }
        });
        
        south.add(delete);
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
        productName.setText("");
        type.setText("");
        price.setText("");
        productID.setEnabled(true);
        delete.setEnabled(false);
        enter.setEnabled(true);
    }
    
    public static void main(String[] args){
        new deleteProductPage();
    }
}
