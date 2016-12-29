/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.TitledBorder;
import Logic.*;
import Domain.*;
/**
 *
 * @author Ooi
 */
public class editMembership extends JPanel{
    private memberRecord mRecord;
    Member mem = null;
    Date dNow = new Date();
    SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy");
    SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
    SimpleDateFormat ft1 = new SimpleDateFormat ("dd");
    String day, birthday;
    String month;
    String year;
    String loginID = "A0001";   
    
    private JPanel topSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel form = new JPanel(new GridLayout(10,2));
    private JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JPanel genderPanel = new JPanel(new GridLayout(1, 4));
    private JPanel contactPanel = new JPanel(new GridLayout(1, 2));
    private JPanel contactPanel2 = new JPanel(new GridLayout(1, 2));
    
    private JComboBox contactCom, contactCom2;  
    
    private ButtonGroup gender = new ButtonGroup();
    private JRadioButton rdMale = new JRadioButton("Male");
    private JRadioButton rdFemale = new JRadioButton("Female");
    
    private JTextField memberName = new JTextField();
    private JTextField memberID = new JTextField(20);
    private JTextField birthDate = new JTextField();
    private JTextField address = new JTextField();
    private JTextField contact1 = new JTextField();
    private JTextField contact2 = new JTextField();
    private JTextField urine = new JTextField();
    private JTextField cholesterol = new JTextField();
    private JTextField joinDate = new JTextField();
    private JTextField expDate = new JTextField();
    
    int arraySize = 0, charCount, determine, token;
    String mID, contactSur, contactSur2, contact, contactSec, MF, genderViarable, con1, con2;
    
    private JButton enter = new JButton("Enter");
    private JButton updateDate = new JButton("Update Membership Date");
    private JButton update = new JButton("Update");
    private JButton reset = new JButton("Reset");

    
    final String dates[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String dates2[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String dates3[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String dates4[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    String months[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}; 
    private JPanel birDatePanel = new JPanel(new GridLayout(1, 3));
    private JComboBox bDate, bMonth, bYear; 
    
    public editMembership(){
        mRecord = new memberRecord();
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Update Member Data"));
        topSearch.setBorder(new TitledBorder("ID Search"));
        topSearch.add(new JLabel("Membership ID:   M"));
        topSearch.add(memberID);
        memberID.setFocusable(true);
        topSearch.add(enter);
        
        //Contact panel
        String list[] = { "010", "011", "012", "013", "014", "016", "017", "018", "04", "08"};
        contactCom = new JComboBox(list);
        contactCom2 = new JComboBox(list);
        contactPanel.add(contactCom);
        contactPanel.add(contact1);
        contactPanel2.add(contactCom2);
        contactPanel2.add(contact2);
        
        //Gender panel
        gender.add(rdMale);
        gender.add(rdFemale);
        genderPanel.add(rdMale);
        genderPanel.add(rdFemale);

        //Birthdate panel
        arraySize = Integer.parseInt(ft3.format(dNow)) - 1950 + 1;
        String[] yearList = new String[arraySize];
        int a = 0;
        for(int i = 1950; i <= Integer.parseInt(ft3.format(dNow));i++){
            yearList[a] = Integer.toString(i);
            a++;
        }

        bDate = new JComboBox(dates);
        bMonth = new JComboBox(months);
        bYear = new JComboBox(yearList);
        birDatePanel.add(bYear);
        birDatePanel.add(bMonth);
        birDatePanel.add(bDate);
        
        bYear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Month();
            }
        });
        
        bMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(bMonth.getSelectedIndex() == 0){
                    bDate.removeAllItems(); // remove all the item from jcombobox
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 1){
                     bDate.removeAllItems();
                     datePattern1();
                }else if(bMonth.getSelectedIndex() == 2){
                    if(Integer.parseInt("" + bYear.getSelectedItem()) % 4 == 0){
                        bDate.removeAllItems();
                        datePattern4();
                    }else{
                        bDate.removeAllItems();
                        datePattern3();
                    }
                }else if(bMonth.getSelectedIndex() == 3){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 4){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 5){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 6){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 7){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 8){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 9){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 10){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 11){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 12){
                    bDate.removeAllItems();
                    datePattern1();
                }
            }
        });
        
        form.setBorder(new TitledBorder("Member Information"));
        form.add(new JLabel("Member Name"));
        form.add(memberName);
        form.add(new JLabel("Gender"));
        form.add(genderPanel);
        form.add(new JLabel("Date of Birth[Year/Month/Day]"));
        form.add(birthDate);
        form.add(new JLabel("Date of Birth[For edit purpose]"));
        form.add(birDatePanel);
        form.add(new JLabel("Home Address"));
        form.add(address);
        form.add(new JLabel("Contact No.1"));
        form.add(contactPanel);
        form.add(new JLabel("Contact No.2 [Optional]"));
        form.add(contactPanel2);
        form.add(new JLabel("Urine Acid (Miligram)"));
        form.add(urine);
        form.add(new JLabel("Cholesterol (Miligram)"));
        form.add(cholesterol);
        form.add(new JLabel("Date of Registered"));
        form.add(joinDate);

        //limit user input
        memberID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        contact1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        contact2.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        urine.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        cholesterol.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        // disable the field
        update.setEnabled(false);
        memberName.setEnabled(false);
        rdMale.setEnabled(false);
        rdFemale.setEnabled(false);
        birthDate.setEnabled(false);
        bDate.setEnabled(false);
        bMonth.setEnabled(false);
        bYear.setEnabled(false);
        address.setEnabled(false);
        contactCom.setEnabled(false); 
        contactCom2.setEnabled(false); 
        contact1.setEnabled(false);
        contact2.setEnabled(false);
        urine.setEnabled(false);    
        cholesterol.setEnabled(false);      
        joinDate.setEnabled(false);
        expDate.setEnabled(false);
        if(loginID.charAt(0) == 'A'){
            updateDate.setEnabled(false);
        }else if(loginID.charAt(0) == 'S'){
            updateDate.setEnabled(true);
            update.setEnabled(false);
            reset.setEnabled(false);
        }
        
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(memberID.getText().length() == 4){
                    mID = "M" + memberID.getText();
                    mem = mRecord.SelectRecord(mID);

                    if(mem != null){
                        memberName.setEnabled(true);
                        rdFemale.setEnabled(true);
                        rdMale.setEnabled(true);
                        address.setEnabled(true);
                        contactCom.setEnabled(true); 
                        contactCom2.setEnabled(true); 
                        contact1.setEnabled(true);
                        contact2.setEnabled(true);
                        urine.setEnabled(true);    
                        cholesterol.setEnabled(true);      
                        update.setEnabled(true);
                        bDate.setEnabled(true);
                        bMonth.setEnabled(true);
                        bYear.setEnabled(true);
                        memberID.setEnabled(false);
                        enter.setEnabled(false);
                        
                        contactNoOneManaging();
                        contactNoTwoManaging();
                        
                        memberName.setText(mem.getName());
                        if(mem.getGender().equals("Male")){
                            rdMale.setSelected(true);
                        }else if(mem.getGender().equals("Female")){
                            rdFemale.setSelected(true);
                        }
                        birthDate.setText(mem.getBirthday());
                        address.setText(mem.getAddress());
                        contact1.setText(contact);
                        contact2.setText(contactSec);
                        urine.setText(Integer.toString(mem.getUrineAcidMG()));
                        cholesterol.setText(Integer.toString(mem.getCholesterolMG()));
                        joinDate.setText(mem.getJoinDate());
                        expDate.setText(mem.getExpiredDate());

                    }else{
                        memberID.setText("");
                        JOptionPane.showMessageDialog(null, "No such data.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }

                     
                }else{
                    memberID.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid data enter, please enter again.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        
        updateDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                determine = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Extends The Membership Duration?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                if(determine == 0){
                    memberID.setEnabled(true);
                    enter.setEnabled(true);
                    // update the "join date" and "expired date"
                    reset();
                }
            }
        });
        
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mID = "M" + memberID.getText();
                mem = mRecord.SelectRecord(mID);
                if(mem != null){
                    if(memberName.getText().isEmpty() || contact1.getText().isEmpty() || contact2.getText().isEmpty() || address.getText().isEmpty() || urine.getText().isEmpty() || cholesterol.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please fill up each field.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }else{
                        // "if" for length veridation
                        token = 0;
                        if(memberName.getText().length() > 99){
                            JOptionPane.showMessageDialog(null, "Name length cannot more than 99 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(contact1.getText().length() > 9){
                            JOptionPane.showMessageDialog(null, "Contact number 1 Right textfied length cannot more than 9 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(contact2.getText().length() > 9){
                            JOptionPane.showMessageDialog(null, "Contact number 2 Right textfied length cannot more than 9 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(address.getText().length() > 255){
                            JOptionPane.showMessageDialog(null, "Address length cannot more than 255 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(cholesterol.getText().length() > 3){
                            JOptionPane.showMessageDialog(null, "Cholesterol length cannot more than 3 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(urine.getText().length() > 2){
                            JOptionPane.showMessageDialog(null, "UrineAcid length cannot more than 2 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(Integer.parseInt(cholesterol.getText()) < 1){
                            JOptionPane.showMessageDialog(null, "Cholesterol length cannot be 0 or lesser.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(Integer.parseInt(urine.getText()) < 1){
                            JOptionPane.showMessageDialog(null, "Urine Acid value cannot be 0 or lesser.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }

                        if(token == 0){
                            determine = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Update The Information of This Member Record?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                            if(determine == 0){
                                // Insert the data into database
                                if(rdMale.isSelected()){
                                    genderViarable = "Male";
                                }else if(rdFemale.isSelected()){
                                    genderViarable = "Female";
                                }

                                birthday = "";
                                if(bDate.getSelectedIndex() != 0 && bMonth.getSelectedIndex() != 0 ){
                                    birthday = "" + bDate.getSelectedItem() + "/" + bMonth.getSelectedItem() + "/" + bYear.getSelectedItem();
                                }else{
                                    birthday = birthDate.getText();
                                }

                                con1 = contactCom.getSelectedItem() + "-" + contact1.getText();
                                con2 = contactCom2.getSelectedItem() + "-"  + contact2.getText();

                                mRecord.UpdateRecord2(mID, memberName.getText(), genderViarable,  birthday, address.getText(), con1, con2,  Integer.parseInt(urine.getText()),  Integer.parseInt(cholesterol.getText()));
                                JOptionPane.showMessageDialog(null, "Update Successful", "Message", JOptionPane.INFORMATION_MESSAGE); 
                                // Re-disable the textfield                       
                                reset();

                            }                     
                        }                    
                    }                    
                }else{
                    JOptionPane.showMessageDialog(null, "No Such Data", "Warning", JOptionPane.WARNING_MESSAGE);
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
        
        south.add(updateDate);
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
        memberName.setEnabled(false);
        rdFemale.setEnabled(false);
        rdMale.setEnabled(false);
        birthDate.setEnabled(false);
        address.setEnabled(false);
        contactCom.setEnabled(false); 
        contactCom2.setEnabled(false); 
        contact1.setEnabled(false);
        contact2.setEnabled(false);
        urine.setEnabled(false);    
        cholesterol.setEnabled(false); 
        update.setEnabled(false);
        expDate.setEnabled(false);
        joinDate.setEnabled(false);
        memberID.setEnabled(true);
        enter.setEnabled(true);
        bDate.setEnabled(false);
        bMonth.setEnabled(false);
        bYear.setEnabled(false);
        
        memberID.setText("");
        memberName.setText("");
        gender.clearSelection();
        address.setText("");
        birthDate.setText("");
        contactCom.setSelectedIndex(0);
        contactCom2.setSelectedIndex(0);
        contact1.setText("");
        contact2.setText("");
        urine.setText("");
        cholesterol.setText("");
        joinDate.setText("");
        expDate.setText("");
        bDate.setSelectedIndex(0);
        bMonth.setSelectedIndex(0);
        bYear.setSelectedIndex(0);
    }
    
    public void contactNoOneManaging(){
         int i = 0;
        contactSur = "";
        while(mem.getContact1().charAt(i) != '-'){ // to determine which index contain "-", hence detected, end while loop
            contactSur = contactSur + mem.getContact1().charAt(i); 
            i++;
        }

        contact = "";
        if(contactSur.equals("010")){
            contactCom.setSelectedIndex(0);
            contact = mem.getContact1().replace("010-", "");
        }else if(contactSur.equals("011")){
            contactCom.setSelectedIndex(1);
            contact = mem.getContact1().replace("011-", "");
        }else if(contactSur.equals("012")){
            contactCom.setSelectedIndex(2);
            contact = mem.getContact1().replace("012-", "");
        }else if(contactSur.equals("013")){
            contactCom.setSelectedIndex(3);
            contact = mem.getContact1().replace("013-", "");
        }else if(contactSur.equals("014")){
            contactCom.setSelectedIndex(4);
            contact = mem.getContact1().replace("014-", "");
        }else if(contactSur.equals("016")){
            contactCom.setSelectedIndex(5);
            contact = mem.getContact1().replace("016-", "");
        }else if(contactSur.equals("017")){
            contactCom.setSelectedIndex(6);
            contact = mem.getContact1().replace("017-", "");
        }else if(contactSur.equals("018")){
            contactCom.setSelectedIndex(7);
            contact = mem.getContact1().replace("018-", "");
        }else if(contactSur.equals("04")){
            contactCom.setSelectedIndex(8);
            contact = mem.getContact1().replace("04-", "");
        }else if(contactSur.equals("08")){
            contactCom.setSelectedIndex(9);
            contact = mem.getContact1().replace("08-", "");
        }
    }
    
    public void contactNoTwoManaging(){
        int i = 0;
        contactSur2 = "";
        while(mem.getContact2().charAt(i) != '-'){ // to determine which index contain "-", hence detected, end while loop
            contactSur2 = contactSur2 + mem.getContact2().charAt(i); 
            i++;
        }

        contactSec = "";
        if(contactSur2.equals("010")){
            contactCom.setSelectedIndex(0);
            contactSec = mem.getContact2().replace("010-", "");
        }else if(contactSur2.equals("011")){
            contactCom2.setSelectedIndex(1);
            contactSec = mem.getContact2().replace("011-", "");
        }else if(contactSur2.equals("012")){
            contactCom2.setSelectedIndex(2);
            contactSec = mem.getContact2().replace("012-", "");
        }else if(contactSur2.equals("013")){
            contactCom2.setSelectedIndex(3);
            contactSec = mem.getContact2().replace("013-", "");
        }else if(contactSur2.equals("014")){
            contactCom2.setSelectedIndex(4);
            contactSec = mem.getContact2().replace("014-", "");
        }else if(contactSur2.equals("016")){
            contactCom2.setSelectedIndex(5);
            contactSec = mem.getContact2().replace("016-", "");
        }else if(contactSur2.equals("017")){
            contactCom2.setSelectedIndex(6);
            contactSec = mem.getContact2().replace("017-", "");
        }else if(contactSur2.equals("018")){
            contactCom2.setSelectedIndex(7);
            contactSec = mem.getContact2().replace("018-", "");
        }else if(contactSur2.equals("04")){
            contactCom2.setSelectedIndex(8);
            contactSec = mem.getContact2().replace("04-", "");
        }else if(contactSur2.equals("08")){
            contactCom2.setSelectedIndex(9);
            contactSec = mem.getContact2().replace("08-", "");
        }
    }    
    
    public void birthDayManage(){
        //int i, j, k;
        
        for(int i = 0; i < 2 ;i++){
            day = day + mem.getBirthday().charAt(i);
        }
        
        for(int j = 3; j < 5; j++){
            month = month + mem.getBirthday().charAt(j);
        }
        
        for(int k = 6; k < 10; k++){
            year = year + mem.getBirthday().charAt(k);
        }
        
        //-------------------------- Start get the data load into combo box --------------------------------


    }
    
    public void Month(){ // add the item into Jcombo
        bMonth.removeAllItems();
        for(int i = 0 ; i < months.length; i++){
            bMonth.addItem(months[i]);
        }        
    }     
    
    public void datePattern1(){ // add the item into Jcombo
        for(int i = 0 ; i < dates.length; i++){
            bDate.addItem(dates[i]);
        }        
    }
    public void datePattern2(){ // add the item into Jcombo
        for(int i = 0 ; i < dates2.length; i++){
            bDate.addItem(dates2[i]);
        }        
    }
    public void datePattern3(){ // add the item into Jcombo
        for(int i = 0 ; i < dates3.length; i++){
            bDate.addItem(dates3[i]);
        }        
    }
    public void datePattern4(){ // add the item into Jcombo
        for(int i = 0 ; i < dates4.length; i++){
            bDate.addItem(dates4[i]);
        }        
    }
    
    public static void main(String[] args){
        new editMembership();
    }
    
}
