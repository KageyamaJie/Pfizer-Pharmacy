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
import DA.*;
import Domain.*;
import Logic.*;
/**
 *
 * @author Ooi
 */
public class employeeRegistration extends JPanel{
    private employeeRecord eRecord;
    private Employee emp = null;
    
    Date dNow = new Date();
    SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy");
    SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
    SimpleDateFormat ft1 = new SimpleDateFormat ("dd");
    String day, month, year;
    int arraySize = 0, access, number = 0, numberA = 0, numberS = 0, numChar, token;
    String employeeID, incrementID = "", birthDay, phoneNo, MF, systemUserID = "";
    char checkChar;
    
    private JPanel form = new JPanel(new GridLayout(13, 2));
    private JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JPanel genderPanel = new JPanel(new GridLayout(1, 4));
    private JPanel contactPanel = new JPanel(new GridLayout(1, 2));
    private JPanel birDatePanel = new JPanel(new GridLayout(1, 3));
    
    private JComboBox contactCom, bDay, bMonth, bYear, pos;
    
    private JTextField empID = new JTextField();
    private JTextField empName = new JTextField();
    private JTextField address = new JTextField();
    private JTextField contactNo = new JTextField();
    private JTextField joinDate = new JTextField();
    private JTextField salary = new JTextField();
    private JTextField bankAccount = new JTextField();
    private JTextField userID = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JPasswordField passwordRE = new JPasswordField();
    
    private ButtonGroup gender = new ButtonGroup();
    private JRadioButton rdMale = new JRadioButton("Male");
    private JRadioButton rdFemale = new JRadioButton("Female");
    
    private JButton register = new JButton("Register");
    private JButton reset = new JButton("Reset");
    
    final String day1[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String day2[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String day3[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String day4[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    final String months[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}; 
    
    public employeeRegistration(){
        eRecord = new employeeRecord();
        
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Employee Registration Form"));
        //Contact panel
        String list[] = { "010", "011", "012", "013", "014", "016", "017", "018", "04", "08"};
        contactCom = new JComboBox(list);
        contactPanel.add(contactCom);
        contactPanel.add(contactNo);
        
        //Position 
        String[] postList = {"Admin", "Staff"};
        pos = new JComboBox(postList);
        pos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pos.getSelectedIndex() == 0){
                    admIDIncrement();
                }else if(pos.getSelectedIndex() == 1){
                    stfIDIncrement();
                }
            }
        });
        
        //Gender panel
        gender.add(rdMale);
        gender.add(rdFemale);
        genderPanel.add(rdMale);
        genderPanel.add(rdFemale);
        
        //System date setting
         year = ft3.format(dNow); //<<---- pass in the year to a variable
         month = ft2.format(dNow);
         day = ft1.format(dNow);
         joinDate.setText(day + "/" + month + "/" + year);
         joinDate.setEnabled(false);
         
        //Birthdate panel
        arraySize = Integer.parseInt(ft3.format(dNow)) - 1970 + 1;
        String[] yearList = new String[arraySize];
        int a = 0;
        for(int i = 1970; i <= Integer.parseInt(ft3.format(dNow)); i++){
            yearList[a] = Integer.toString(i);
            a++;
        }
        
        bDay = new JComboBox(day1);
        bMonth = new JComboBox(months);
        bYear = new JComboBox(yearList);
        birDatePanel.add(bYear);
        birDatePanel.add(bMonth);
        birDatePanel.add(bDay);
        
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
                    bDay.removeAllItems(); // remove all the item from jcombobox
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 1){
                    if(Integer.parseInt("" + bYear.getSelectedItem()) % 4 == 0){
                        bDay.removeAllItems();
                        datePattern4();
                    }else{
                        bDay.removeAllItems();
                        datePattern3();
                    }
                }else if(bMonth.getSelectedIndex() == 2){
                    bDay.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 3){
                    bDay.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 4){
                    bDay.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 5){
                    bDay.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 6){
                    bDay.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 7){
                    bDay.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 8){
                    bDay.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 9){
                    bDay.removeAllItems();
                    datePattern1();
                }else if(bMonth.getSelectedIndex() == 10){
                    bDay.removeAllItems();
                    datePattern2();
                }else if(bMonth.getSelectedIndex() == 11){
                    bDay.removeAllItems();
                    datePattern1();
                }
            }
        });

        // set encoding password
        password.setEchoChar('*');
        passwordRE.setEchoChar('*');
        
        // disable textfield
        empID.setEnabled(false);
        userID.setEnabled(false);
        
         
        form.setBorder(new TitledBorder("Employee Form"));
        form.add(new JLabel("Employee ID"));
        form.add(empID);
        form.add(new JLabel("Employee Name"));
        form.add(empName);
        form.add(new JLabel("Date of Birth[Year/Month/Day]"));
        form.add(birDatePanel);
        form.add(new JLabel("Gender"));
        form.add(genderPanel);
        form.add(new JLabel("Home Address"));
        form.add(address);
        form.add(new JLabel("Contact No"));
        form.add(contactPanel);
        form.add(new JLabel("Date of Recuit"));
        form.add(joinDate);
        form.add(new JLabel("Salary"));
        form.add(salary);
        form.add(new JLabel("Bank Account Number"));
        form.add(bankAccount);
        form.add(new JLabel("Position"));
        form.add(pos);
        form.add(new JLabel("UserID"));
        form.add(userID);
        form.add(new JLabel("Password for System"));
        form.add(password);
        form.add(new JLabel("Confirm Password"));
        form.add(passwordRE);
        
        //
        
        IDupdate();
        admIDIncrement();
        
        southPanel.add(register);
        southPanel.add(reset);
        add(form, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
        
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(empName.getText().isEmpty() || address.getText().isEmpty() || contactNo.getText().isEmpty() || salary.getText().isEmpty() || bankAccount.getText().isEmpty() || userID.getText().isEmpty() || password.getText().isEmpty() || passwordRE.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Please fill up each field.", "Warning", JOptionPane.WARNING_MESSAGE);
                }else{
                    if(rdFemale.isSelected() || rdMale.isSelected()){
                        // veridate the actual length
                        token = 0;
                        if(empName.getText().length() > 99){
                            JOptionPane.showMessageDialog(null, "Name length cannot more than 99 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(contactNo.getText().length() > 9){
                            JOptionPane.showMessageDialog(null, "Contact Number length cannot more than 9 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(address.getText().length() > 255){
                            JOptionPane.showMessageDialog(null, "Address length cannot more than 255 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(salary.getText().length() > 5){
                            JOptionPane.showMessageDialog(null, "Salary only can reached to maximum 99999", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(bankAccount.getText().length() != 12){
                            JOptionPane.showMessageDialog(null, "Bank Account Number length must exactly 12 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(password.getText().length() > 15){
                            JOptionPane.showMessageDialog(null, "Password length cannot more than 15 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(userID.getText().length() > 5){
                            JOptionPane.showMessageDialog(null, "User ID length cannot more than 5 characters", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }
                        
                        // Grant the token access
                        if(token == 0){
                            if(password.getText().equals(passwordRE.getText())){
                            // grand access to insert data
                                access = JOptionPane.showConfirmDialog(null, "Are You Sure You The Data You Enter Is Corrected", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                                if(access == 0){
                                    // insert data into database
                                    phoneNo = "";
                                    empID.getText();
                                    empName.getText();

                                    MF = "";
                                    if(rdFemale.isSelected()){
                                        MF = "Female";
                                    }else if(rdMale.isSelected()){
                                        MF = "Male";
                                    }

                                    birthDay = bDay.getSelectedItem()+ "/" + bMonth.getSelectedItem() + "/" + bYear.getSelectedItem();

                                    phoneNo = contactCom.getSelectedItem() + "-" + contactNo.getText();
                                    contactNo.getText();
                                    salary.getText();
                                    bankAccount.getText();
                                    userID.getText();
                                    password.getText();
                                    eRecord.CreateRecord(empID.getText(), empName.getText(), MF, birthDay, address.getText(), phoneNo,  joinDate.getText(), Integer.parseInt(salary.getText()), bankAccount.getText(), userID.getText(), password.getText());
                                    JOptionPane.showMessageDialog(null, "Register Successful", "Message", JOptionPane.INFORMATION_MESSAGE);

                                    IDupdate(); //  updating the id
                                    stfIDIncrement();
                                    admIDIncrement();
                                    //reset back to default textfield
                                    reset();                               
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "Confirm Password Not Match, Please re-enter again.", "Warning", JOptionPane.WARNING_MESSAGE);
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
    }
    
    public void reset(){
        empName.setText("");
        address.setText("");
        gender.clearSelection();
        bDay.setSelectedIndex(0);
        bMonth.setSelectedIndex(0);
        bYear.setSelectedIndex(0);
        contactCom.setSelectedIndex(0);
        contactNo.setText("");
        salary.setText("");
        bankAccount.setText("");
        pos.setSelectedIndex(0);
        userID.setText("");
        password.setText("");
        passwordRE.setText("");
    }
    
    public void IDupdate(){
        emp = eRecord.IDIncrement();
        eRecord.numEmployee();
        numChar = 0;
        if(eRecord.numEmployee() != 0){
            for(int i = 0 ; i < emp.getID().length(); i++){
                checkChar = emp.getID().charAt(i);
                if(Character.isLetter(checkChar) || checkChar == '0'){
                    numChar++;
                }else{
                    break;
                }   
            }

            employeeID = emp.getID();
            if(numChar == 1){
                employeeID = employeeID.replaceAll("E", "");
                number = Integer.parseInt(employeeID) + 1;
                incrementID = 'E' + Integer.toString(number);
            }else if(numChar == 2){
                employeeID = employeeID.replaceAll("E0", "");
                number = Integer.parseInt(employeeID) + 1;
                incrementID = "E0" + Integer.toString(number);
                if(number == 1000){
                    incrementID = incrementID.replaceAll("E0", "");
                    incrementID = "E" + incrementID;
                }
            }else if(numChar == 3){
                employeeID = employeeID.replaceAll("E00", "");
                number = Integer.parseInt(employeeID) + 1;
                incrementID = "E00" + Integer.toString(number);
                if(number == 100){
                    incrementID = incrementID.replaceAll("E00", "");
                    incrementID = "E0" + incrementID;
                }
            }else if(numChar == 4){
                employeeID = employeeID.replaceAll("E000", "");
                number = Integer.parseInt(employeeID) + 1;
                incrementID = "E000" + Integer.toString(number);
                if(number == 10){
                    incrementID = incrementID.replaceAll("E000", "");
                    incrementID = "E00" + incrementID;
                }
            }else{
                incrementID = "";
                register.setEnabled(false);
                reset.setEnabled(false);
            }            
        }else{
            incrementID = "E0001";
        }

        empID.setText(incrementID);        
    }
    
    public void admIDIncrement(){
        emp = eRecord.admIDIncrement3();
        eRecord.numAdmin();
        numChar = 0;
        if(eRecord.numAdmin() != 0){
            for(int i = 0 ; i < emp.getUserID().length(); i++){
                checkChar = emp.getUserID().charAt(i);
                if(Character.isLetter(checkChar) || checkChar == '0'){
                    numChar++;
                }else{
                    break;
                }   
            }

            systemUserID = emp.getUserID();
            if(numChar == 1){
                systemUserID = systemUserID.replaceAll("A", "");
                numberA = Integer.parseInt(systemUserID) + 1;
                incrementID = 'A' + Integer.toString(numberA);
            }else if(numChar == 2){
                systemUserID = systemUserID.replaceAll("A0", "");
                numberA = Integer.parseInt(systemUserID) + 1;
                incrementID = "A0" + Integer.toString(numberA);
                if(number == 1000){
                    incrementID = incrementID.replaceAll("A0", "");
                    incrementID = "A" + incrementID;
                }
            }else if(numChar == 3){
                systemUserID = systemUserID.replaceAll("A00", "");
                numberA = Integer.parseInt(systemUserID) + 1;
                incrementID = "A00" + Integer.toString(numberA);
                if(number == 100){
                    incrementID = incrementID.replaceAll("A00", "");
                    incrementID = "A0" + incrementID;
                }
            }else if(numChar == 4){
                systemUserID = systemUserID.replaceAll("A000", "");
                numberA = Integer.parseInt(systemUserID) + 1;
                incrementID = "A000" + Integer.toString(numberA);
                if(number == 10){
                    incrementID = incrementID.replaceAll("A000", "");
                    incrementID = "A00" + incrementID;
                }
            }else{
                incrementID = "";
                register.setEnabled(false);
                reset.setEnabled(false);
            }
        }else{
            incrementID = "A0001";
        }

        userID.setText(incrementID);        
    }    
    
    public void stfIDIncrement(){
        emp = eRecord.stfIDIncrement2();
        eRecord.numStaff();
        numChar = 0;
        if(eRecord.numStaff() != 0){
            for(int i = 0 ; i < emp.getUserID().length(); i++){
                checkChar = emp.getUserID().charAt(i);
                if(Character.isLetter(checkChar) || checkChar == '0'){
                    numChar++;
                }else{
                    break;
                }   
            }

            systemUserID = emp.getUserID();
            if(numChar == 1){
                systemUserID = systemUserID.replaceAll("S", "");
                numberS = Integer.parseInt(systemUserID) + 1;
                incrementID = 'S' + Integer.toString(numberS);
            }else if(numChar == 2){
                systemUserID = systemUserID.replaceAll("S0", "");
                numberS = Integer.parseInt(systemUserID) + 1;
                incrementID = "S0" + Integer.toString(numberS);
                if(number == 1000){
                    incrementID = incrementID.replaceAll("S0", "");
                    incrementID = "S" + incrementID;
                }
            }else if(numChar == 3){
                systemUserID = systemUserID.replaceAll("S00", "");
                numberS = Integer.parseInt(systemUserID) + 1;
                incrementID = "S00" + Integer.toString(numberS);
                if(number == 100){
                    incrementID = incrementID.replaceAll("S00", "");
                    incrementID = "S0" + incrementID;
                }
            }else if(numChar == 4){
                systemUserID = systemUserID.replaceAll("S000", "");
                numberS = Integer.parseInt(systemUserID) + 1;
                incrementID = "S000" + Integer.toString(numberS);
                if(number == 10){
                    incrementID = incrementID.replaceAll("S000", "");
                    incrementID = "S00" + incrementID;
                }
            }else{
                incrementID = "";
                register.setEnabled(false);
                reset.setEnabled(false);
            }            
        }else{
            incrementID = "S0001";
        }
        
        userID.setText(incrementID);        
    }

    public void Month(){ // add the item into Jcombo
        bMonth.removeAllItems();
        for(int i = 0 ; i < months.length; i++){
            bMonth.addItem(months[i]);
        }        
    }    
    
    public void datePattern1(){ // add the item into Jcombo
        for(int i = 0 ; i < day1.length; i++){
            bDay.addItem(day1[i]);
        }        
    }
    
    public void datePattern2(){ // add the item into Jcombo
        for(int i = 0 ; i < day2.length; i++){
            bDay.addItem(day2[i]);
        }        
    }
    
    public void datePattern3(){ // add the item into Jcombo
        for(int i = 0 ; i < day3.length; i++){
            bDay.addItem(day3[i]);
        }        
    }
    
    public void datePattern4(){ // add the item into Jcombo
        for(int i = 0 ; i < day4.length; i++){
            bDay.addItem(day4[i]);
        }        
    }
    
    public static void main(String[] args){
        new employeeRegistration();
    }
}
