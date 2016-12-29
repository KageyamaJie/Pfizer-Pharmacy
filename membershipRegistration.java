/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;
import Domain.*;
import Logic.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.Date;
/**
 *
 * @author Ooi
 */
public class membershipRegistration extends JPanel{
    private memberRecord mRecord;
    Member mem = null;
    
    Date dNow = new Date();
    SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy");
    SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
    SimpleDateFormat ft1 = new SimpleDateFormat ("dd");
    String day;
    String month;
    String year;
    int surpassYear;
    String incrementYear;
    int arraySize = 0;
    int access, charCount;
    int number = 0, numChar, token;
    String membershipID, incrementID = "";
    char checkChar;    
    String genderVariable;
    
    private JPanel form = new JPanel(new GridLayout(10, 2));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JPanel genderPanel = new JPanel(new GridLayout(1, 4));
    private JPanel contactPanel = new JPanel(new GridLayout(1, 2));
    private JPanel contactPanel2 = new JPanel(new GridLayout(1, 2));
    private JPanel birDatePanel = new JPanel(new GridLayout(1, 3));
    
    private JComboBox contactCom, contactCom2, bDate, bMonth, bYear;
    
    private JTextField memberID = new JTextField();
    private JTextField name = new JTextField();
    private JTextField address = new JTextField();
    private JTextField contact1 = new JTextField();
    private JTextField contact2 = new JTextField();
    private JTextField urineAcid = new JTextField("1");
    private JTextField cholesterol = new JTextField("1");
    private JTextField joinDate = new JTextField();
    private JTextField expDate = new JTextField();
    
    private ButtonGroup gender = new ButtonGroup();
    private JRadioButton rdMale = new JRadioButton("Male");
    private JRadioButton rdFemale = new JRadioButton("Female");
    
    private JButton register = new JButton("Register");
    private JButton reset = new JButton("Reset");
    
    final String dates[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String dates2[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String dates3[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String dates4[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    String months[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}; 
    
    public membershipRegistration(){
        mRecord = new memberRecord();
         
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Member Registration Form"));
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
        
        //System date setting
         year = ft3.format(dNow); //<<---- pass in the year to a variable
         surpassYear = Integer.parseInt(year) + 1; //<<------ convert to year from String integer
         incrementYear = Integer.toString(surpassYear); //<<----- convert back to String with increment of 1 year
         month = ft2.format(dNow);
         day = ft1.format(dNow);
         joinDate.setText(day + "/" + month + "/" + year);
         expDate.setText(day + "/" + month + "/" + incrementYear);
         joinDate.setEnabled(false);
         expDate.setEnabled(false);
         
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
                    if(Integer.parseInt("" + bYear.getSelectedItem()) % 4 == 0){
                        bDate.removeAllItems();
                        datePattern4();
                    }else{
                        bDate.removeAllItems();
                        datePattern3();
                    }
                }else if(bMonth.getSelectedIndex() == 2){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 3){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 4){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 5){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 6){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 7){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 8){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 9){
                    bDate.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 10){
                    bDate.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 11){
                    bDate.removeAllItems();
                    datePattern1();
                }
            }
        });
        
         
        form.setBorder(new TitledBorder("Membership Form"));
        form.add(new JLabel("Membership ID"));
        form.add(memberID);
        form.add(new JLabel("Membership Name"));
        form.add(name);
        form.add(new JLabel("Date of Birth[Year/Month/Day]"));
        form.add(birDatePanel);
        form.add(new JLabel("Gender"));
        form.add(genderPanel);
        form.add(new JLabel("Home Address"));
        form.add(address);
        form.add(new JLabel("Contact No-1"));
        form.add(contactPanel);
        form.add(new JLabel("Contact No-2 [Optional]"));
        form.add(contactPanel2);
        form.add(new JLabel("Urine Acid(Milligram)"));
        form.add(urineAcid);
        form.add(new JLabel("Cholesterol(Milligram)"));
        form.add(cholesterol);
        form.add(new JLabel("Date of join"));
        form.add(joinDate);
        
        //Testing limit user input
        urineAcid.addKeyListener(new KeyAdapter() {
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
        
       
        IDUpdate();  

        southPanel.add(register);
        southPanel.add(reset);
        //add(new JLabel("Member registration form"), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        //disable
        memberID.setEnabled(false);
        
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(name.getText().isEmpty() || address.getText().isEmpty() || contact1.getText().isEmpty() || urineAcid.getText().isEmpty() || cholesterol.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill up each field.", "Warning", JOptionPane.WARNING_MESSAGE);
                }else{
                    if(rdFemale.isSelected() || rdMale.isSelected()){
                        // veridate the actual length of each field
                        token = 0;
                        if(memberID.getText().length() > 5){
                            JOptionPane.showMessageDialog(null, "ID length cannot more than 5 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }
                        else if(name.getText().length() > 99){
                            JOptionPane.showMessageDialog(null, "Name length cannot more than 99 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(contact1.getText().length() > 9){
                            JOptionPane.showMessageDialog(null, "Contact number 1 length cannot more than 9 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(contact2.getText().length() > 9){
                            JOptionPane.showMessageDialog(null, "Contact number 2 length cannot more than 9 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(address.getText().length() > 255){
                            JOptionPane.showMessageDialog(null, "Address length cannot more than 255 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(cholesterol.getText().length() > 3){
                            JOptionPane.showMessageDialog(null, "Cholesterol length cannot more than 3 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(urineAcid.getText().length() > 2){
                            JOptionPane.showMessageDialog(null, "UrineAcid length cannot more than 2 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(Integer.parseInt(cholesterol.getText()) < 1){
                            JOptionPane.showMessageDialog(null, "Cholesterol length cannot be 0 or lesser.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(Integer.parseInt(urineAcid.getText()) < 1){
                            JOptionPane.showMessageDialog(null, "Urine Acid value cannot be 0 or lesser.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }
                        
                        // grand access to insert data
                        if(token == 0){
                        access = JOptionPane.showConfirmDialog(null, "Are You Sure The Data That You Enter Is Corrected?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                            if(access == 0){
                                // insert data into database
                                if(rdMale.isSelected()){
                                     genderVariable = "Male";
                                }else if(rdFemale.isSelected()){
                                     genderVariable = "Female";
                                }
                                
                                String birth = bDate.getSelectedItem() + "/" + bMonth.getSelectedItem() + "/" + bYear.getSelectedItem();
                                String firstContact = contactCom.getSelectedItem() + "-" + contact1.getText();
                                String secondContact = contactCom2.getSelectedItem() + "-" + contact2.getText();
                                
                                mRecord.CreateRecord(memberID.getText(), name.getText(), birth, genderVariable , address.getText(), firstContact, secondContact, Integer.parseInt(urineAcid.getText()), Integer.parseInt(cholesterol.getText()), joinDate.getText(), null);
                                JOptionPane.showMessageDialog(null, "Register Successful", "Message", JOptionPane.INFORMATION_MESSAGE); 
                                IDUpdate();
                                reset();
                            }    
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Please Select Gender.", "Warning", JOptionPane.WARNING_MESSAGE);
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
        
        //setSize(1000,1000);
        //setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
    };
    
    public void IDUpdate(){
        mem = mRecord.IDIncrement();
        mRecord.numMember();
        numChar = 0;
        if(mRecord.numMember() != 0){
            for(int i = 0 ; i < mem.getID().length(); i++){
                checkChar = mem.getID().charAt(i);
                if(Character.isLetter(checkChar) || checkChar == '0'){
                    numChar++;
                }else{
                    break;
                }   
            }

            membershipID = mem.getID();
            if(numChar == 1){
                membershipID = membershipID.replaceAll("M", "");
                number = Integer.parseInt(membershipID) + 1;
                incrementID = 'M' + Integer.toString(number);
            }else if(numChar == 2){
                membershipID = membershipID.replaceAll("M0", "");
                number = Integer.parseInt(membershipID) + 1;
                incrementID = "M0" + Integer.toString(number);
                if(number == 1000){
                    incrementID = incrementID.replaceAll("M0", "");
                    incrementID = "M" + incrementID;
                }
            }else if(numChar == 3){
                membershipID = membershipID.replaceAll("M00", "");
                number = Integer.parseInt(membershipID) + 1;
                incrementID = "M00" + Integer.toString(number);
                if(number == 100){
                    incrementID = incrementID.replaceAll("M00", "");
                    incrementID = "M0" + incrementID;
                }
            }else if(numChar == 4){
                membershipID = membershipID.replaceAll("M000", "");
                number = Integer.parseInt(membershipID) + 1;
                incrementID = "M000" + Integer.toString(number);
                if(number == 10){
                    incrementID = incrementID.replaceAll("M000", "");
                    incrementID = "M00" + incrementID;
                }
            }else{
                incrementID = "";
                register.setEnabled(false);
                reset.setEnabled(false);
            }
        }else{
            incrementID = "M0001";
        }
        
        memberID.setText(incrementID);  
    }
    
    public void reset(){
        name.setText("");
        gender.clearSelection();
        address.setText("");
        bDate.setSelectedIndex(0);
        bMonth.setSelectedIndex(0);
        bYear.setSelectedIndex(0);
        contactCom.setSelectedIndex(0);
        contact1.setText("");
        contactCom2.setSelectedIndex(0);
        contact2.setText("");
        urineAcid.setText("1");
        cholesterol.setText("1");
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
        new membershipRegistration();
    }
    
}
