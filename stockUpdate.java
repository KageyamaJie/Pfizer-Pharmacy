/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;

/**
 *
 * @author Teddy
 */
import Domain.Inventory;
import Domain.Product;
import Logic.inventoryRecord;
import Logic.productRecord;
import com.sun.xml.internal.fastinfoset.EncodingConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class stockUpdate extends JPanel{
    private inventoryRecord iRecord;
    private productRecord pRecord;
    // component =============================================
    private JComboBox cboxIdYear, cboxIdMonth, cboxIdDate, cboxMYear, cboxMMonth, cboxMDate, cboxEYear, cboxEMonth, cboxEDate;
    
    private JTextField txtStockID = new JTextField(10);
    private JTextField txtProdName = new JTextField();
    private JTextField txtQuantity = new JTextField("0");
    private JTextField txtManuDate = new JTextField(10);
    private JTextField txtExpDate = new JTextField(10);
    
    private JButton btnEnter = new JButton("Enter");
    private JButton btnUpdate = new JButton("Update");
    private JButton btnReset = new JButton("Reset");
    
    private JPanel pnlIdSection = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnlForm = new JPanel(new GridLayout(4, 2));
    private JPanel pnlBtnSection = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JPanel pnlMDate = new JPanel(new GridLayout(1, 3));
    private JPanel pnlEDate = new JPanel(new GridLayout(1, 3));
    private JPanel pnlManuDate = new JPanel(new GridLayout(2, 1));
    private JPanel pnlExpDate = new JPanel(new GridLayout(2, 1));
    
    final String day1[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String day2[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String day3[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String day4[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    
    private String idYear[] = new String[2];
    private String mYear[] = new String[2];
    private String eYear[] = new String[3];
    private String month[] = new String[12];
    private String date[] = {"00"};
    private String tempID, updatedMDate, updatedEDate, currentMDate, currentEDate;
    private int r = 0, yn;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    public stockUpdate() {
        setLayout(new BorderLayout());
        iRecord = new inventoryRecord();
        pRecord = new productRecord();
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
        for(int i = 0; i < 3; i++){
            if(i<2){
                mYear[i] = tempYear;
                idYear[i] = tempYear;
            }
            eYear[i] = tempYear;
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
           
        cboxIdYear = new JComboBox(idYear);
        cboxIdMonth = new JComboBox(month);
        cboxIdDate = new JComboBox(date); // edit in future <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        cboxMYear = new JComboBox(mYear);
        cboxMMonth = new JComboBox(month);
        cboxMDate = new JComboBox(date);
        cboxEYear = new JComboBox(eYear);
        cboxEMonth = new JComboBox(month);
        cboxEDate = new JComboBox(date);
        // disable==================================================
        cboxEDate.setEnabled(false);
        cboxMDate.setEnabled(false);
        cboxIdDate.setEnabled(false);
        txtProdName.setEnabled(false);
        txtProdName.setEditable(false);
        txtQuantity.setEditable(false);
        txtExpDate.setEditable(false);
        txtManuDate.setEditable(false);
        cboxEMonth.setEnabled(false);
        cboxEYear.setEnabled(false);
        cboxMMonth.setEnabled(false);
        cboxMYear.setEnabled(false);
        btnUpdate.setEnabled(false);
        // header panel =============================================
        pnlIdSection.setBorder(new TitledBorder("Stock ID"));
        pnlIdSection.add(new JLabel("Stock ID: "));
        pnlIdSection.add(cboxIdYear);
        pnlIdSection.add(cboxIdMonth);
        pnlIdSection.add(cboxIdDate);
        pnlIdSection.add(new JLabel("STO"));
        pnlIdSection.add(txtStockID);
        pnlIdSection.add(btnEnter);
        // form panel ==============================================
        pnlForm.setBorder(new TitledBorder("Stock Information"));
        pnlForm.add(new JLabel("Product Name"));
        pnlForm.add(txtProdName);
        pnlForm.add(new JLabel("Quantity"));
        pnlForm.add(txtQuantity);
        pnlForm.add(new JLabel("Manufacturer Date"));
        //pnlEDate.setAlignmentY(CENTER_ALIGNMENT);
        pnlMDate.add(cboxMYear);
        pnlMDate.add(cboxMMonth);
        pnlMDate.add(cboxMDate);
        pnlManuDate.add(txtManuDate);
        pnlManuDate.add(pnlMDate);
        pnlForm.add(pnlManuDate);
        pnlForm.add(new JLabel("Expired Date"));
        pnlEDate.add(cboxEYear);
        pnlEDate.add(cboxEMonth);
        pnlEDate.add(cboxEDate);
        pnlExpDate.add(txtExpDate);
        pnlExpDate.add(pnlEDate);
        pnlForm.add(pnlExpDate);
        // button panel ===========================================
        pnlBtnSection.add(btnUpdate);
        pnlBtnSection.add(btnReset);
        // action listener ==========================================
        cboxIdMonth.addActionListener(new idDaySelection());
        cboxEMonth.addActionListener(new eDaySelection());
        cboxMMonth.addActionListener(new mDaySelection());
        txtStockID.addKeyListener(new keyLimit());
        txtQuantity.addKeyListener(new keyLimit());
        cboxEYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cboxEMonth.setEnabled(true);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        cboxMYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                cboxMMonth.setEnabled(true);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        btnEnter.addActionListener(new idVerification());
        btnReset.addActionListener(new ResetALL());
        btnUpdate.addActionListener(new verification());
        // interface frame=========================================
        add(pnlIdSection, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);
        add(pnlBtnSection, BorderLayout.SOUTH);
        
        //setVisible(true);
        //setSize(500, 500);
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
                    String prodID = inv.getProdID();
                    System.out.print("\n" + prodID);
                    Product prod = pRecord.SelectRecord(prodID);
                    currentMDate = inv.getManuDate();
                    currentEDate = inv.getExpDate();
                    txtQuantity.setText(Integer.toString(inv.getQuantity()));
                    txtProdName.setText(prod.getProductName());
                    txtManuDate.setText(currentMDate);
                    txtExpDate.setText(currentEDate);
                
                    txtQuantity.setEditable(true);
                    cboxEYear.setEnabled(true);
                    //cboxEMonth.setEnabled(true);
                    cboxMYear.setEnabled(true);
                    //cboxMMonth.setEnabled(true);
                    btnUpdate.setEnabled(true);
                    btnEnter.setEnabled(false);
                    txtStockID.setEditable(false);
                    cboxIdDate.setEnabled(false);
                    cboxIdMonth.setEnabled(false);
                    cboxIdYear.setEnabled(false);
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
    
    private class mDaySelection implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
                // Manufacture Date section =============================================
                if(cboxMMonth.getSelectedIndex() == 0){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }else if(cboxMMonth.getSelectedIndex() == 1){
                    if(Integer.parseInt("" + cboxMYear.getSelectedItem()) % 4 == 0){
                        cboxMDate.removeAllItems();
                        mDatePattern4();
                    }else{
                        cboxMDate.removeAllItems();
                        mDatePattern3();
                    }
                }else if(cboxMMonth.getSelectedIndex() == 2){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }else if(cboxMMonth.getSelectedIndex() == 3){
                    cboxMDate.removeAllItems();
                    mDatePattern2();
                }else if(cboxMMonth.getSelectedIndex() == 4){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }else if(cboxMMonth.getSelectedIndex() == 5){
                    cboxMDate.removeAllItems();
                    mDatePattern2();
                }else if(cboxMMonth.getSelectedIndex() == 6){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }else if(cboxMMonth.getSelectedIndex() == 7){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }else if(cboxMMonth.getSelectedIndex() == 8){
                    cboxMDate.removeAllItems();
                    mDatePattern2();
                }else if(cboxMMonth.getSelectedIndex() == 9){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }else if(cboxMMonth.getSelectedIndex() == 10){
                    cboxMDate.removeAllItems();
                    mDatePattern2();
                }else if(cboxMMonth.getSelectedIndex() == 11){
                    cboxMDate.removeAllItems();
                    mDatePattern1();
                }
                cboxMDate.setEnabled(true);
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private class eDaySelection implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
                // Expire Date section =============================================
                if(cboxEMonth.getSelectedIndex() == 0){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }else if(cboxEMonth.getSelectedIndex() == 1){
                    if(Integer.parseInt("" + cboxEYear.getSelectedItem()) % 4 == 0){
                        cboxEDate.removeAllItems();
                        eDatePattern4();
                    }else{
                        cboxEDate.removeAllItems();
                        eDatePattern3();
                    }
                }else if(cboxEMonth.getSelectedIndex() == 2){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }else if(cboxEMonth.getSelectedIndex() == 3){
                    cboxEDate.removeAllItems();
                    eDatePattern2();
                }else if(cboxEMonth.getSelectedIndex() == 4){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }else if(cboxEMonth.getSelectedIndex() == 5){
                    cboxEDate.removeAllItems();
                    eDatePattern2();
                }else if(cboxEMonth.getSelectedIndex() == 6){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }else if(cboxEMonth.getSelectedIndex() == 7){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }else if(cboxEMonth.getSelectedIndex() == 8){
                    cboxEDate.removeAllItems();
                    eDatePattern2();
                }else if(cboxEMonth.getSelectedIndex() == 9){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }else if(cboxEMonth.getSelectedIndex() == 10){
                    cboxEDate.removeAllItems();
                    eDatePattern2();
                }else if(cboxEMonth.getSelectedIndex() == 11){
                    cboxEDate.removeAllItems();
                    eDatePattern1();
                }
                cboxEDate.setEnabled(true);
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
    
    public void mDatePattern1(){ // add the item into Jcombo
        for(int i = 0 ; i < day1.length; i++){
            cboxMDate.addItem(day1[i]);
        }        
    }
    
    public void mDatePattern2(){ // add the item into Jcombo
        for(int i = 0 ; i < day2.length; i++){
            cboxMDate.addItem(day2[i]);
        }        
    }
    
    public void mDatePattern3(){ // add the item into Jcombo
        for(int i = 0 ; i < day3.length; i++){
            cboxMDate.addItem(day3[i]);
        }        
    }
    
    public void mDatePattern4(){ // add the item into Jcombo
        for(int i = 0 ; i < day4.length; i++){
            cboxMDate.addItem(day4[i]);
        }        
    }
    
    public void eDatePattern1(){ // add the item into Jcombo
        for(int i = 0 ; i < day1.length; i++){
            cboxEDate.addItem(day1[i]);
        }        
    }
    
    public void eDatePattern2(){ // add the item into Jcombo
        for(int i = 0 ; i < day2.length; i++){
            cboxEDate.addItem(day2[i]);
        }        
    }
    
    public void eDatePattern3(){ // add the item into Jcombo
        for(int i = 0 ; i < day3.length; i++){
            cboxEDate.addItem(day3[i]);
        }        
    }
    
    public void eDatePattern4(){ // add the item into Jcombo
        for(int i = 0 ; i < day4.length; i++){
            cboxEDate.addItem(day4[i]);
        }        
    }
    
    private class verification implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            updatedEDate = "" + cboxEYear.getSelectedItem() + "/" + cboxEMonth.getSelectedItem() + "/" + cboxEDate.getSelectedItem();
            updatedMDate = "" + cboxMYear.getSelectedItem() + "/" + cboxMMonth.getSelectedItem() + "/" + cboxMDate.getSelectedItem();
            System.out.println("\n" + updatedEDate + " " + updatedMDate + " " + currentEDate + " " + currentMDate);
                try {
                    if(!(txtQuantity.getText().isEmpty())){
                        if(Integer.parseInt(txtQuantity.getText()) > 0 || Integer.parseInt(txtQuantity.getText()) < 100){
                            if(cboxMDate.getSelectedItem() != "00" && cboxEDate.getSelectedItem().equals("00")){
                                if(sdf.parse(currentEDate).before(sdf.parse(updatedMDate)) || sdf.parse(currentEDate).compareTo(sdf.parse(updatedMDate)) == 0){
                                    System.out.print("\nIm here");
                                    JOptionPane.showMessageDialog(null, "Expire Date should be after Manufacture Date.", "Wrong Date", JOptionPane.ERROR_MESSAGE);
                                }else{
                                    yn = JOptionPane.showConfirmDialog(null, "Do you sure you want to update the information?", "Pfizer Pharmacy", JOptionPane.YES_NO_OPTION);
                                    if(yn == 0){
                                        iRecord.UpdateRecord(tempID, Integer.parseInt(txtQuantity.getText()), updatedMDate, currentEDate);
                                        JOptionPane.showMessageDialog(null, "Stock ID: " + tempID + " had been updated.", "Pfizer Pharmacy", JOptionPane.INFORMATION_MESSAGE);
                                        resetAll();
                                        
                                    }else{
                                        resetAll();
                                    }
                                }
                            }else if(cboxEDate.getSelectedItem() != "00" && cboxMDate.getSelectedItem().equals("00")){
                                if(sdf.parse(updatedEDate).before(sdf.parse(currentMDate)) || sdf.parse(updatedEDate).compareTo(sdf.parse(currentMDate)) == 0){
                                    JOptionPane.showMessageDialog(null, "Expire Date should be after Manufacture Date.", "Wrong Date", JOptionPane.ERROR_MESSAGE);
                                }else{
                                    yn = JOptionPane.showConfirmDialog(null, "Do you sure you want to update the information?", "Pfizer Pharmacy", JOptionPane.YES_NO_OPTION);
                                    if(yn == 0){
                                        iRecord.UpdateRecord(tempID, Integer.parseInt(txtQuantity.getText()), currentMDate, updatedEDate);
                                        JOptionPane.showMessageDialog(null, "Stock ID: " + tempID + " had been updated.", "Pfizer Pharmacy", JOptionPane.INFORMATION_MESSAGE);
                                        resetAll();
                                    }else{
                                        resetAll();
                                    }   
                                }
                            }else if(cboxMDate.getSelectedItem() != "00" && cboxEDate.getSelectedItem() != "00"){
                                if(sdf.parse(updatedEDate).before(sdf.parse(updatedMDate)) || sdf.parse(updatedEDate).compareTo(sdf.parse(updatedMDate)) == 0){
                                    JOptionPane.showMessageDialog(null, "Expire Date should be after Manufacture Date.", "Wrong Date", JOptionPane.ERROR_MESSAGE);
                                }else{
                                    yn = JOptionPane.showConfirmDialog(null, "Do you sure you want to update the information?", "Pfizer Pharmacy", JOptionPane.YES_NO_OPTION);
                                    if(yn == 0){
                                        iRecord.UpdateRecord(tempID, Integer.parseInt(txtQuantity.getText()), updatedMDate, updatedEDate);
                                        JOptionPane.showMessageDialog(null, "Stock ID: " + tempID + " had been updated.", "Pfizer Pharmacy", JOptionPane.INFORMATION_MESSAGE);
                                        resetAll();
                                    }else{
                                        resetAll();
                                    }
                                }
                            }else if(cboxMDate.getSelectedItem().equals("00") && cboxEDate.getSelectedItem().equals("00")){
                                if(sdf.parse(currentEDate).before(sdf.parse(currentMDate)) || sdf.parse(currentEDate).compareTo(sdf.parse(currentMDate)) == 0){
                                    JOptionPane.showMessageDialog(null, "Expire Date should be after Manufacture Date.", "Wrong Date", JOptionPane.ERROR_MESSAGE);
                                }else{
                                    yn = JOptionPane.showConfirmDialog(null, "Do you sure you want to update the information?", "Pfizer Pharmacy", JOptionPane.YES_NO_OPTION);
                                    if(yn == 0){
                                        iRecord.UpdateRecord(tempID, Integer.parseInt(txtQuantity.getText()), currentMDate, currentEDate);
                                        JOptionPane.showMessageDialog(null, "Stock ID: " + tempID + " had been updated.", "Pfizer Pharmacy", JOptionPane.INFORMATION_MESSAGE);
                                        resetAll();
                                    }else{
                                        resetAll();
                                    }
                                }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Please enter value in between 1 until 99 for QUANTITY.", "Wrong value input", JOptionPane.ERROR_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Please enter value for QUANTITY.", "Null value detected", JOptionPane.ERROR_MESSAGE);
                    }
            } catch (ParseException ex) {
                Logger.getLogger(stockUpdate.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    private void resetAll(){

            cboxIdDate.setSelectedIndex(0);
            cboxIdMonth.setSelectedIndex(0);
            cboxIdYear.setSelectedIndex(0);
            cboxEDate.setSelectedIndex(0);
            cboxEMonth.setSelectedIndex(0);
            cboxEYear.setSelectedIndex(0);
            cboxMDate.setSelectedIndex(0);
            cboxMMonth.setSelectedIndex(0);
            cboxMYear.setSelectedIndex(0);
            txtExpDate.setText("");
            txtManuDate.setText("");
            txtProdName.setText("");
            txtQuantity.setText("");
            txtStockID.setText("");
            
            btnEnter.setEnabled(true);
            txtStockID.setEditable(true);
            cboxEDate.setEnabled(false);
            cboxMDate.setEnabled(false);
            cboxIdDate.setEnabled(false);
            cboxIdMonth.setEnabled(true);
            cboxIdYear.setEnabled(true);
            txtProdName.setEnabled(false);
            txtProdName.setEditable(false);
            txtQuantity.setEditable(false);
            txtExpDate.setEditable(false);
            txtManuDate.setEditable(false);
            cboxEMonth.setEnabled(false);
            cboxEYear.setEnabled(false);
            cboxMMonth.setEnabled(false);
            cboxMYear.setEnabled(false);
            btnUpdate.setEnabled(false);
            
            currentEDate = "";
            currentMDate = "";
            updatedEDate = "";
            updatedMDate = "";
     }
    
    private class ResetALL implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            resetAll();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    public static void main(String[] args) {
        new stockUpdate();
    }
}
