/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;

import Domain.Inventory;
import Domain.Product;
import Logic.inventoryRecord;
import Logic.productRecord;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Teddy
 */
public class stockReturn extends JPanel{
    
    private Product prod = null;
    private productRecord pRecord;
    private Inventory inv;
    private inventoryRecord iRecord;
    
    private JComboBox cboxReason, cboxIdYear, cboxIdMonth, cboxIdDate;
    
    private JTextField txtStockID = new JTextField(10);
    private JTextField txtProdName = new JTextField();
    private JTextField txtQuantity = new JTextField("");
    private JTextField txtCurrentQuantity = new JTextField("");
    
    private JButton btnSearch = new JButton("Search");
    private JButton btnConfirm = new JButton("Confirm");
    private JButton btnCancel = new JButton("Reset");
    
    private JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnlQuantity = new JPanel(new GridLayout(2, 1));
    private JPanel pnlForm = new JPanel(new GridLayout(3, 2));
    private JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    final String day1[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String day2[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String day3[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String day4[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    
    private String idYear[] = new String[2];
    private String month[] = new String[12];
    private String date[] = {"00"};
    private String tempID, updatedMDate, updatedEDate, currentMDate, currentEDate, prodID;
    private int r = 0, yn;
    
    public stockReturn(){
        setLayout(new BorderLayout());
        iRecord = new inventoryRecord();
        pRecord=new productRecord();
        //System date setting
        Date dNow = new Date();
        SimpleDateFormat ft3 = new SimpleDateFormat ("yy");
        SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
        SimpleDateFormat ft1 = new SimpleDateFormat ("dd");
        String tempDay;
        String tempMonth;
        String tempYear;
        int surpassYear;
        String incrementYear;
        
        tempYear = ft3.format(dNow); //<<---- pass in the year to a variable
        tempMonth = ft2.format(dNow);
        tempDay = ft1.format(dNow);
        surpassYear = Integer.parseInt(tempYear) - 1;
        tempYear = Integer.toString(surpassYear);
        // code for date >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
        for(int i = 0; i < 2; i++){
            idYear[i] = tempYear;
            surpassYear = Integer.parseInt(tempYear) + 1; //<<------ convert to year from String integer
            tempYear = Integer.toString(surpassYear); //<<----- convert back to String with increment of 1 year
        }
        
        for(int i=0; i<12; i++){
            int r = i + 1;
            if(r<10)
                month[i] = "0" + r;
            else
            month[i] = r + "";
        }
        // combo box declaration =======================================
        String reason[] = {"Spoiled/Damaged/Expired", "HQ request for return", "Manager decision"};
        cboxReason = new JComboBox(reason);
        cboxIdYear = new JComboBox(idYear);
        cboxIdMonth = new JComboBox(month);
        cboxIdDate = new JComboBox(date);
        // header panel ===============================================
        pnlHeader.setBorder(new TitledBorder("Stock ID"));
        pnlHeader.add(new JLabel("Stock ID: "));
        pnlHeader.add(cboxIdYear);
        pnlHeader.add(cboxIdMonth);
        pnlHeader.add(cboxIdDate);
        pnlHeader.add(new JLabel("STO"));
        pnlHeader.add(txtStockID);
        pnlHeader.add(btnSearch);
        // form panel =================================================
        pnlForm.setBorder(new TitledBorder("Return Stock"));
        pnlForm.add(new JLabel("Product Name"));
        pnlForm.add(txtProdName);
        pnlForm.add(new JLabel("Quantity"));
        pnlQuantity.add(txtCurrentQuantity);
        pnlQuantity.add(txtQuantity);
        pnlForm.add(pnlQuantity);
        pnlForm.add(new JLabel("Reason"));
        pnlForm.add(cboxReason);
        // button panel ================================================
        pnlButton.add(btnConfirm);
        pnlButton.add(btnCancel);
        // set enable & editable ========================================
        txtProdName.setEditable(false);
        cboxIdDate.setEnabled(false);
        txtQuantity.setEditable(false);
        cboxReason.setEnabled(false);
        txtCurrentQuantity.setEditable(false);
        btnConfirm.setEnabled(false);
        // action listener and key listener =================================
        btnSearch.addActionListener(new idVerification());
        txtStockID.addKeyListener(new keyLimit());
        txtQuantity.addKeyListener(new keyLimit());
        cboxIdMonth.addActionListener(new idDaySelection());
        btnCancel.addActionListener(new resetALL());
        btnConfirm.addActionListener(new verification());
        // Return stock interface panel ===================================
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
        
        //setVisible(true);
       // setSize(500, 500);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private class idVerification implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(txtStockID.getText().length() == 4){
                tempID = "" + cboxIdYear.getSelectedItem() + cboxIdMonth.getSelectedItem() + cboxIdDate.getSelectedItem() + "STO" + txtStockID.getText();
                System.out.print(tempID);
                Inventory inv = iRecord.SelectRecord(tempID);
                if(inv != null){
                    prodID = inv.getProdID();
                    System.out.print("\n" + prodID);
                    Product prod = pRecord.SelectRecord(prodID);
                    txtCurrentQuantity.setText(Integer.toString(inv.getQuantity()));
                    txtProdName.setText(prod.getProductName());
                    
                    btnConfirm.setEnabled(true); 
                    cboxReason.setEnabled(true);
                    btnSearch.setEnabled(false);
                    txtStockID.setEditable(false);
                    cboxIdDate.setEnabled(false);
                    cboxIdMonth.setEnabled(false);
                    cboxIdYear.setEnabled(false);
                    if(txtCurrentQuantity.getText().equals("0")){
                        txtQuantity.setEditable(false);
                        JOptionPane.showMessageDialog(null, "Stock ID: " + tempID + " is not able to return any stock due to OUT OF STOCK.", "Pfizer Pharmacy", JOptionPane.INFORMATION_MESSAGE);
                        resetAll();
                    }else{
                        txtQuantity.setEditable(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Please enter a valid Product ID.", "Invalid Product ID", JOptionPane.ERROR_MESSAGE);
                }
                    
            }else{
                JOptionPane.showMessageDialog(null, "Please enter 4 integer number for Product ID.", "Wrong Product ID format", JOptionPane.ERROR_MESSAGE);
            }
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class keyLimit implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } 

        @Override
        public void keyPressed(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class resetALL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            resetAll();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private void resetAll(){
        
        cboxIdMonth.setSelectedIndex(0);
        cboxIdYear.setSelectedIndex(0);
        txtStockID.setText("");
        txtQuantity.setText("");
        txtCurrentQuantity.setText("");
        
        txtProdName.setEditable(false);
        txtStockID.setEditable(true);
        cboxIdDate.setEnabled(false);
        cboxIdMonth.setEnabled(true);
        cboxIdYear.setEnabled(true);
        txtQuantity.setEditable(false);
        cboxReason.setEnabled(false);
        txtCurrentQuantity.setEditable(false);
        btnConfirm.setEnabled(false);
        btnSearch.setEnabled(true);
    }
    
    private class verification implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!(txtQuantity.getText().isEmpty()) && !(txtQuantity.getText().equals("0"))){
                if(Integer.parseInt(txtQuantity.getText()) <= Integer.parseInt(txtCurrentQuantity.getText())){
                    yn = JOptionPane.showConfirmDialog(null, "Do you sure you want to return Product: " + txtProdName.getText() + " ("+ tempID +") ?", "Pfizer Pharmacy", JOptionPane.YES_NO_OPTION);
                    if(yn == 0){
                        int calc = Integer.parseInt(txtCurrentQuantity.getText()) - Integer.parseInt(txtQuantity.getText());
                        iRecord.UpdateRecord2(tempID, calc);
                        JOptionPane.showMessageDialog(null, "Stock ID: " + tempID + " had been return " + txtQuantity.getText() + " item(s).\n Reason: " + cboxReason.getSelectedItem(), "Pfizer Pharmacy", JOptionPane.INFORMATION_MESSAGE);
                        resetAll();
                    }
                    resetAll();
                }else{
                    JOptionPane.showMessageDialog(null, "Return quantity cannot more than current having quantity of stock.", "Pfizer Pharmacy", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Please enter value for QUANTITY.", "Null value detected", JOptionPane.ERROR_MESSAGE);
            }
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class idDaySelection implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
                // Manufacture Date section =============================================
                if(cboxIdMonth.getSelectedIndex() == 0){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }else if(cboxIdMonth.getSelectedIndex() == 1){
                    if(Integer.parseInt("" + cboxIdYear.getSelectedItem()) % 4 == 0){
                        cboxIdDate.removeAllItems();
                        idDatePattern4();
                    }else{
                        cboxIdDate.removeAllItems();
                        idDatePattern3();
                    }
                }else if(cboxIdMonth.getSelectedIndex() == 2){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }else if(cboxIdMonth.getSelectedIndex() == 3){
                    cboxIdDate.removeAllItems();
                    idDatePattern2();
                }else if(cboxIdMonth.getSelectedIndex() == 4){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }else if(cboxIdMonth.getSelectedIndex() == 5){
                    cboxIdDate.removeAllItems();
                    idDatePattern2();
                }else if(cboxIdMonth.getSelectedIndex() == 6){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }else if(cboxIdMonth.getSelectedIndex() == 7){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }else if(cboxIdMonth.getSelectedIndex() == 8){
                    cboxIdDate.removeAllItems();
                    idDatePattern2();
                }else if(cboxIdMonth.getSelectedIndex() == 9){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }else if(cboxIdMonth.getSelectedIndex() == 10){
                    cboxIdDate.removeAllItems();
                    idDatePattern2();
                }else if(cboxIdMonth.getSelectedIndex() == 11){
                    cboxIdDate.removeAllItems();
                    idDatePattern1();
                }
                cboxIdDate.setEnabled(true);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public void idDatePattern1(){ // add the item into Jcombo
        for(int i = 0 ; i < day1.length; i++){
            cboxIdDate.addItem(day1[i]);
        }        
    }
    
    public void idDatePattern2(){ // add the item into Jcombo
        for(int i = 0 ; i < day2.length; i++){
            cboxIdDate.addItem(day2[i]);
        }        
    }
    
    public void idDatePattern3(){ // add the item into Jcombo
        for(int i = 0 ; i < day3.length; i++){
            cboxIdDate.addItem(day3[i]);
        }        
    }
    
    public void idDatePattern4(){ // add the item into Jcombo
        for(int i = 0 ; i < day4.length; i++){
            cboxIdDate.addItem(day4[i]);
        }        
    }
    
    public static void main(String[] args) {
        new stockReturn();
    }
    
}
