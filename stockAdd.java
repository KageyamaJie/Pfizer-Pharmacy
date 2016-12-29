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
import javax.swing.*;
import javax.swing.border.*;
import Domain.*;
import Logic.inventoryRecord;
import Logic.productRecord;
import java.text.ParseException;
import java.util.*;
import java.text.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Teddy
 */
public class stockAdd extends JPanel {
    Date dNow = new Date();
    SimpleDateFormat ft3 = new SimpleDateFormat ("yy");
    SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
    SimpleDateFormat ft1 = new SimpleDateFormat ("dd");
    
    private inventoryRecord iRecord;
    private Inventory inv = null;
    private productRecord pRecord;
    
    private JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
    private JPanel pnlForm = new JPanel(new GridLayout(4, 2));
    private JPanel pnlMDate = new JPanel(new GridLayout(1, 3));
    private JPanel pnlEDate = new JPanel(new GridLayout(1, 3));
    private JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    //private JTextField txtDate = new JTextField();
    private JTextField txtProdID = new JTextField(10);
    private JTextField txtProdName = new JTextField();
    private JTextField txtQuantity = new JTextField("1");
    
    private JButton btnConfirm = new JButton("Confirm");
    private JButton btnCancel = new JButton("Reset");
    private JButton btnSearch = new JButton("Search");
    
    private JComboBox cboxMYear, cboxMMonth, cboxMDate, cboxEYear, cboxEMonth, cboxEDate;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private String mDate;
    private String eDate;
    
    final String day1[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String day2[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String day3[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String day4[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    //final String months[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}; 
    
    private String mYear[] = new String[2];
    private String eYear[] = new String[3];
    private String month[] = new String[12];
    
    int access, token, numChar, number;
    String ctgy, productCost, productIDString, incrementID = "";
    char checkChar;
    String productID = "";
    public stockAdd() {
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
//
//        for(int i=0; i<31; i++){
//            int r = i + 1;
//            if(i<31){
//                if(r<10)
//                    tempoDate1[i] = "0" + r;
//                else
//                    tempoDate1[i] = r + "";
//            }
//            else if(i<30){
//                if(r<10)
//                    tempoDate1[i] = "0" + r;
//                else
//                    tempoDate2[i] = r + "";
//            }
//            else if(i<29){
//                if(r<10)
//                    tempoDate1[i] = "0" + r;
//                else
//                    tempoDate3[i] = r + "";
//            }
//            else if(i<28){
//                if(r<10)
//                    tempoDate1[i] = "0" + r;
//                else
//                    tempoDate4[i] = r + "";
//            }
//        }
        
        String date[] = {"00"};
        // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        
        // Combo box declaration
        cboxEYear = new JComboBox(eYear);
        cboxEMonth = new JComboBox(month);
        cboxEDate = new JComboBox(date);
        cboxMYear = new JComboBox(mYear);
        cboxMMonth = new JComboBox(month);
        cboxMDate = new JComboBox(date);
        // headcer panel ======================================================
        pnlHeader.setBorder(new TitledBorder("Product ID"));
        pnlHeader.add(new JLabel("Product ID: P"));
        pnlHeader.add(txtProdID);
        pnlHeader.add(btnSearch);
        txtProdID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        btnSearch.addActionListener(new idVerification());
        // manufacture date panel ==============================================
        pnlMDate.add(cboxMYear);
        pnlMDate.add(cboxMMonth);
        pnlMDate.add(cboxMDate);
        cboxMYear.setEnabled(false);
        cboxMMonth.setEnabled(false);
        cboxMDate.setEnabled(false);
        // expiry date panel ==============================================
        pnlEDate.add(cboxEYear);
        pnlEDate.add(cboxEMonth);
        pnlEDate.add(cboxEDate);
        cboxEYear.setEnabled(false);
        cboxEMonth.setEnabled(false);
        cboxEDate.setEnabled(false);
        // button panel ==================================================
        pnlButton.add(btnConfirm);
        pnlButton.add(btnCancel);
        btnConfirm.setEnabled(false);
        // form panel ========================================================
        pnlForm.setBorder(new TitledBorder("Stock Information"));
        pnlForm.add(new JLabel("Product Name"));
        pnlForm.add(txtProdName);
        txtProdName.setEditable(false);
        pnlForm.add(new JLabel("Quantity"));
        pnlForm.add(txtQuantity);
        txtQuantity.setEditable(false);
        txtQuantity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        pnlForm.add(new JLabel("Manufacture Date"));
        pnlForm.add(pnlMDate);
        pnlForm.add(new JLabel("Expired Date"));
        pnlForm.add(pnlEDate);
        // action listener declaration ============================================
        cboxEMonth.addActionListener(new eDaySelection());
        cboxMMonth.addActionListener(new mDaySelection());
        btnConfirm.addActionListener(new verification());
        btnCancel.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetAll();
                txtQuantity.setEditable(false);
                txtProdID.setEditable(true);
                btnSearch.setEnabled(true);
                cboxEDate.setEnabled(false);
                cboxEYear.setEnabled(false);
                cboxEMonth.setEnabled(false);
                cboxMDate.setEnabled(false);
                cboxMYear.setEnabled(false);
                cboxMMonth.setEnabled(false);
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        // stock add interface panel ============================================
        add(pnlHeader, BorderLayout.NORTH);
        add(pnlForm, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);
        IDUpdate();
        //setVisible(true);
        //setSize(500, 500);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    private class idVerification implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(txtProdID.getText().length() == 4){
                String tempID = "P" + txtProdID.getText();
                Product prod = pRecord.SelectRecord(tempID);
                if(prod != null){
                    txtProdName.setText(prod.getProductName());
                    txtProdID.setEditable(false);
                    btnSearch.setEnabled(false);
                    txtQuantity.setEditable(true);
                    cboxEYear.setEnabled(true);
                    cboxEMonth.setEnabled(true);
                    cboxMYear.setEnabled(true);
                    cboxMMonth.setEnabled(true);
                    btnConfirm.setEnabled(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Please enter a valid Product ID.", "Invalid Product ID", JOptionPane.ERROR_MESSAGE);
                }
                    
            }else{
                JOptionPane.showMessageDialog(null, "Please enter 4 integer number for Product ID.", "Wrong Product ID format", JOptionPane.ERROR_MESSAGE);
            }
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
    
    private class verification implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            
            
            
                int eYearIndex = cboxEYear.getSelectedIndex();
                int eMonthIndex = cboxEMonth.getSelectedIndex();
                int eDateIndex = cboxEDate.getSelectedIndex();
                int mYearIndex = cboxMYear.getSelectedIndex();
                int mMonthIndex = cboxMMonth.getSelectedIndex();
                int mDateIndex = cboxMDate.getSelectedIndex();
                String EYear = eYear[eYearIndex];
                String EMonth = month[eMonthIndex];
                String EDate = "";
                String expireDate = "";
                String MYear = mYear[mYearIndex];
                String MMonth = month[mMonthIndex];
                String MDate = "";
                String manufactureDate = "";
                if(eMonthIndex == 0 || eMonthIndex == 2 || eMonthIndex == 4 || eMonthIndex == 6 || eMonthIndex == 7 || eMonthIndex == 9 || eMonthIndex == 11){
                    for(int i = 0; i < 31; i++){
                        if(eDateIndex == i)
                            EDate = day1[i];
                    }
                }else if(eMonthIndex == 1){
                    if(Integer.parseInt(EYear) % 4 == 0){
                        for(int i = 0; i < 29; i++){
                            if(eDateIndex == i)
                                EDate = day4[i];
                        }
                    }else{
                        for(int i = 0; i < 28; i++){
                            if(eDateIndex == i)
                                EDate = day3[i];
                        }
                    }
                }else{
                    for(int i = 0; i < 30; i++){
                        if(eDateIndex == i)
                            EDate = day2[i];
                    }
                }
                
                if(mMonthIndex == 0 || mMonthIndex == 2 || mMonthIndex == 4 || mMonthIndex == 6 || mMonthIndex == 7 || mMonthIndex == 9 || mMonthIndex == 11){
                    for(int i = 0; i < 31; i++){
                        if(mDateIndex == i)
                            MDate = day1[i];
                    }
                }else if(mMonthIndex == 1){
                    if(Integer.parseInt(EYear) % 4 == 0){
                        for(int i = 0; i < 29; i++){
                            if(mDateIndex == i)
                                MDate = day4[i];
                        }
                    }else{
                        for(int i = 0; i < 28; i++){
                            if(mDateIndex == i)
                                MDate = day3[i];
                        }
                    }
                }else{
                    for(int i = 0; i < 30; i++){
                        if(mDateIndex == i)
                            MDate = day2[i];
                    }
                }
                
                expireDate = EYear + "/" + EMonth + "/" + EDate;
                manufactureDate = MYear + "/" + MMonth + "/" + MDate;
                SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
                
            try {
                Date expDate = sdf.parse(expireDate);
                Date manuDate = sdf.parse(manufactureDate);
                
                if(expDate.before(manuDate) || expDate.compareTo(manuDate) == 0)
                    JOptionPane.showMessageDialog(null, "Expire Date should be after Manufacture Date.", "Wrong Date", JOptionPane.ERROR_MESSAGE);
                else if(txtQuantity.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please enter value for QUANTITY field.", "Empty QUANTITY", JOptionPane.ERROR_MESSAGE);
                }else if(Integer.parseInt(txtQuantity.getText()) > 99 || Integer.parseInt(txtQuantity.getText()) == 0){
                        JOptionPane.showMessageDialog(null, "Please enter value in between 1 to 99.", "Wrong quantity detected", JOptionPane.ERROR_MESSAGE);
                }else{
                    iRecord.CreateRecord(incrementID, Integer.parseInt(txtQuantity.getText()), manufactureDate, expireDate, "P" + txtProdID.getText());
                    JOptionPane.showMessageDialog(null, "Stock had been inserted into INVENTORY.\nStock ID: " + incrementID, "Successful Activity", JOptionPane.INFORMATION_MESSAGE);
                    IDUpdate(); // update ID
                    resetAll();
                    txtQuantity.setEditable(false);
                    cboxEDate.setEnabled(false);
                    cboxEYear.setEnabled(false);
                    cboxEMonth.setEnabled(false);
                    cboxMDate.setEnabled(false);
                    cboxMYear.setEnabled(false);
                    cboxMMonth.setEnabled(false);
                    txtProdID.setEditable(true);
                    btnSearch.setEnabled(true);
                }
                
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            } catch (ParseException ex) {
                Logger.getLogger(stockAdd.class.getName()).log(Level.SEVERE, null, ex);
            }
               
        }
        
    }
    
    public void resetAll(){
        txtProdID.setText("");
        txtProdName.setText("");
        txtQuantity.setText("1");
        cboxEDate.setSelectedIndex(0);
        cboxEMonth.setSelectedIndex(0);
        cboxEYear.setSelectedIndex(0);
        cboxMDate.setSelectedIndex(0);
        cboxMMonth.setSelectedIndex(0);
        cboxMYear.setSelectedIndex(0);
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
    
    public void IDUpdate(){
        String tempoDateString = "";
        int proceed = 0;
        String testing = "";
        
        numChar = 0;
        iRecord.numInv();
        if(iRecord.numInv() != 0){ // check for database able to return no of record, else giving a new id

            inv = iRecord.IDIncrement(ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow));
            if(inv != null){
                productID = inv.getStockID();
            //JOptionPane.showMessageDialog(null, productID); //160822STO 0001
            for(int i = 0; i < 6; i++){ // Store the ID's date
                tempoDateString = tempoDateString + productID.charAt(i);
            }
            
//            for(int i = 0; i < 6; i++){
//                testing = testing + productID.charAt(i);
//            }
            
                if(!(tempoDateString.equals(ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow)))){ // comparing the date
                     proceed = 1; 
                }
            
                if(proceed  == 0){ // check for the date is same as current, else new id will be start at new date and from 0001
                    for(int i = 9 ; i < productID.length(); i++){
                        checkChar = productID.charAt(i);
                        if(checkChar == '0'){
                            numChar++; // check for how many Character or 0 occupy in the ID
                        }else{
                            break;
                        }   
                    }

                    productIDString = "";
                    for(int i = 9 ; i < productID.length(); i++){ // take out all the date left STOXXXX
                        if((Character.isDigit(checkChar) || checkChar != '0')){ 
                            productIDString = productIDString + productID.charAt(i); // left the integer
                        }  
                    }

                    //JOptionPane.showMessageDialog(null, numChar);
                    if(numChar == 0){ 
                        number = Integer.parseInt(productIDString) + 1;
                        if(number > 9999){ // check if the number is greater than 9999
                            incrementID = "";
                        }else{
                            incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO" + Integer.toString(number);
                        }
                    }else if(numChar == 1){
                        number = Integer.parseInt(productIDString) + 1;
                        incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO" + "0" + Integer.toString(number);
                        if(number == 1000){
                            incrementID = incrementID.replace(ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) +"STO0", "");
                            incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) +"STO" + incrementID;                       
                        }
                    }else if(numChar == 2){
                        number = Integer.parseInt(productIDString) + 1;
                        incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO" + "00" + Integer.toString(number);
                       if(number == 100){
                            incrementID = incrementID.replace(ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) +"STO00", "");
                            incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) +"STO0" + incrementID;                       
                       }
                    }else if(numChar == 3){
                        number = Integer.parseInt(productIDString) + 1;
                        incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO" + "000" + Integer.toString(number);
                        if(number == 10){
                            incrementID = incrementID.replace(ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) +"STO000", "");
                            incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) +"STO00" + incrementID;
                        }
                        //JOptionPane.showMessageDialog(null, number);
                        //JOptionPane.showMessageDialog(null, incrementID);
                    }                
                }else{
                    incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO0001";
                }                
            }else{
                incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO0001";
            }

        }else{
            incrementID = ft3.format(dNow) + ft2.format(dNow) + ft1.format(dNow) + "STO0001";
        }
        
        //System.out.println(incrementID);
    }    
    
    public static void main(String[] args) {
        new stockAdd();
    }
    
}
