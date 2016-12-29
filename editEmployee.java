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
import java.text.SimpleDateFormat;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.TitledBorder;
import java.util.Date;
import DA.*;
import Domain.*;
import Logic.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author Ooi
 */
public class editEmployee extends JPanel{
    private employeeRecord eRecord; //
    
    Date dNow = new Date();
    SimpleDateFormat ft3 = new SimpleDateFormat ("yyyy");
    SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
    SimpleDateFormat ft1 = new SimpleDateFormat ("dd");
    String day, birthday;
    String month;
    String year;
    int arraySize = 0;
    
    private JPanel topSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel form = new JPanel(new GridLayout(9,2));
    private JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    private JPanel genderPanel = new JPanel(new GridLayout(1, 2));
    private JPanel contactPanel = new JPanel(new GridLayout(1, 2));
    
    private JComboBox contactCom;
    
    private JTextField empID = new JTextField(20);
    private JTextField empName = new JTextField();
    private JTextField birthDate = new JTextField();
    private JTextField address = new JTextField();
    private JTextField contactNo = new JTextField();
    private JTextField joinDate = new JTextField();
    private JTextField salary = new JTextField();
    private JTextField bankAccount = new JTextField();
    private JTextField userID = new JTextField();
    
    private ButtonGroup genderCtgy = new ButtonGroup();
    private JRadioButton rdbMale = new JRadioButton("Male");
    private JRadioButton rdbFemale = new JRadioButton("Female");
    
    private JButton enter = new JButton("Enter");
    private JButton update = new JButton("Update");
    private JButton reset = new JButton("Reset");
    
    int charCount, determine, token;
    String eID, contactSur, contact;
    
    final String dates[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    final String dates2[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
    final String dates3[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
    final String dates4[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29"};
    String months[] = {"","01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}; 
    private JPanel birDatePanel = new JPanel(new GridLayout(1, 3));
    private JComboBox bDate, bMonth, bYear;    
    
    public editEmployee(){
        eRecord = new employeeRecord(); //
        
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Update Employee Data"));
        topSearch.setBorder(new TitledBorder("ID Search"));
        topSearch.add(new JLabel("Employee ID:   E"));
        topSearch.add(empID);
        empID.setFocusable(true);
        topSearch.add(enter);
        
        String list[] = { "010", "011", "012", "013", "014", "016", "017", "018", "04", "08"};
        contactCom = new JComboBox(list);
        contactPanel.add(contactCom);
        contactPanel.add(contactNo);    
        
        //Gender panel
        genderCtgy.add(rdbMale);
        genderCtgy.add(rdbFemale);
        genderPanel.add(rdbMale);
        genderPanel.add(rdbFemale);

        //Birthdate panel
        arraySize = Integer.parseInt(ft3.format(dNow)) - 1970 + 1;
        String[] yearList = new String[arraySize];
        int a = 0;
        for(int i = 1970; i <= Integer.parseInt(ft3.format(dNow));i++){
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
        
        form.setBorder(new TitledBorder("Employee Information"));
        form.add(new JLabel("Employee Name"));
        form.add(empName);
        form.add(new JLabel("Gender"));
        form.add(genderPanel);
        form.add(new JLabel("Date of Birth[Date/Month/Year]"));
        form.add(birthDate);
        form.add(new JLabel("Date of Birth[For edit purpose]"));
        form.add(birDatePanel);
        form.add(new JLabel("Home Address"));
        form.add(address);
        form.add(new JLabel("Contact No"));
        form.add(contactPanel);
        form.add(new JLabel("Date of Recuit"));
        form.add(joinDate);
        form.add(new JLabel("Salary"));
        form.add(salary);
        form.add(new JLabel("Bank Account No"));
        form.add(bankAccount);
        
        empID.setFocusable(true);
        empID.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e){
               char input = e.getKeyChar();
               if((input < '0' || input > '9') && input != '\b'){
                   e.consume();
               }  
           }
        });       
        
        contactNo.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e){
               char input = e.getKeyChar();
               if((input < '0' || input > '9') && input != '\b'){
                   e.consume();
               }  
           }
        });
        
        bankAccount.addKeyListener(new KeyAdapter() {
           public void keyTyped(KeyEvent e){
               char input = e.getKeyChar();
               if((input < '0' || input > '9') && input != '\b'){
                   e.consume();
               }  
           }
        });
        
        //Disable
        empName.setEnabled(false);
        address.setEnabled(false);
        rdbMale.setEnabled(false);
        rdbFemale.setEnabled(false);
        bDate.setEnabled(false);
        bMonth.setEnabled(false);
        bYear.setEnabled(false);
        contactNo.setEnabled(false);
        contactCom.setEnabled(false);
        salary.setEnabled(false);
        birthDate.setEnabled(false);
        joinDate.setEnabled(false);
        bankAccount.setEnabled(false);
        userID.setEnabled(false);   
        update.setEnabled(false);
        
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(empID.getText().length() == 4){
                    
                    eID = "E" + empID.getText();
                    Employee emp = eRecord.SelectRecord(eID); //
                    if(emp != null){
                        empName.setText(emp.getName());
                        if(emp.getGender().equals("Male")){
                            rdbMale.setSelected(true);
                        }else if(emp.getGender().equals("Female")){
                            rdbFemale.setSelected(true);
                        }
                        birthDate.setText(emp.getBirthDate());
                        address.setText(emp.getAddress());
                        
                        int i = 0;
                        contactSur = "";
                        while(emp.getContactNo().charAt(i) != '-'){ // to determine which index contain "-", hence detected, end while loop
                            contactSur = contactSur + emp.getContactNo().charAt(i); 
                            i++;
                        }
                        
                        contact = "";
                        if(contactSur.equals("010")){
                            contactCom.setSelectedIndex(0);
                            contact = emp.getContactNo().replace("010-", "");
                        }else if(contactSur.equals("011")){
                            contactCom.setSelectedIndex(1);
                            contact = emp.getContactNo().replace("011-", "");
                        }else if(contactSur.equals("012")){
                            contactCom.setSelectedIndex(2);
                            contact = emp.getContactNo().replace("012-", "");
                        }else if(contactSur.equals("013")){
                            contactCom.setSelectedIndex(3);
                            contact = emp.getContactNo().replace("013-", "");
                        }else if(contactSur.equals("014")){
                            contactCom.setSelectedIndex(4);
                            contact = emp.getContactNo().replace("014-", "");
                        }else if(contactSur.equals("016")){
                            contactCom.setSelectedIndex(5);
                            contact = emp.getContactNo().replace("016-", "");
                        }else if(contactSur.equals("017")){
                            contactCom.setSelectedIndex(6);
                            contact = emp.getContactNo().replace("017-", "");
                        }else if(contactSur.equals("018")){
                            contactCom.setSelectedIndex(7);
                            contact = emp.getContactNo().replace("018-", "");
                        }else if(contactSur.equals("04")){
                            contactCom.setSelectedIndex(8);
                            contact = emp.getContactNo().replace("04-", "");
                        }else if(contactSur.equals("08")){
                            contactCom.setSelectedIndex(9);
                            contact = emp.getContactNo().replace("08-", "");
                        }
                        
                        contactNo.setText(contact);
                        joinDate.setText(emp.getJoinDate());
                        salary.setText(Integer.toString(emp.getSalary()));
                        bankAccount.setText(emp.getBankAcc());
                        userID.setText(emp.getUserID());
                        
                        empName.setEnabled(true);
                        address.setEnabled(true);
                        rdbMale.setEnabled(true);
                        rdbFemale.setEnabled(true);
                        bDate.setEnabled(true);
                        bMonth.setEnabled(true);
                        bYear.setEnabled(true);
                        contactCom.setEnabled(true);
                        contactNo.setEnabled(true);
                        salary.setEnabled(true);
                        bankAccount.setEnabled(true);
                        update.setEnabled(true);
                        enter.setEnabled(false);
                        empID.setEnabled(false);
                    }else{
                        empID.setText("");
                        JOptionPane.showMessageDialog(null, "No such data.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                   
                }else{
                    empID.setText("");
                    JOptionPane.showMessageDialog(null, "Invalid data enter, please enter again.", "Warning", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            // "if" veridate the actual length of each field
                eID = "E" + empID.getText();
                Employee emp = eRecord.SelectRecord(eID);
                if(emp != null){
                    if(empName.getText().isEmpty() || address.getText().isEmpty() || contactNo.getText().isEmpty() || salary.getText().isEmpty() || bankAccount.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Please fill up each field.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }else{
                        token = 0;
                        if(empName.getText().length() > 99){
                            JOptionPane.showMessageDialog(null, "Name length cannot more than 99 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(address.getText().length() > 255){
                            JOptionPane.showMessageDialog(null, "Address length cannot more than 255 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(contactNo.getText().length() > 9){
                            JOptionPane.showMessageDialog(null, "Contact Number length cannot more than 9 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(salary.getText().length() > 5){
                            JOptionPane.showMessageDialog(null, "Salary only can reached to maximum 99999", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }else if(bankAccount.getText().length() != 12){
                            JOptionPane.showMessageDialog(null, "Bank account number length must exactly equal 12 digits.", "Warning", JOptionPane.WARNING_MESSAGE);
                            token = 1;
                        }

                        // Grant the token
                        if(token == 0){
                        determine = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Update The Information of This Employee Record?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                            if(determine == 0){
                                // Insert the data into database
                                String MF ="";
                                if(rdbFemale.isSelected()){
                                    MF = "Female";
                                }else if(rdbMale.isSelected()){
                                    MF = "Male";
                                }

                                birthday = "";
                                if(bDate.getSelectedIndex() != 0 && bMonth.getSelectedIndex() != 0 ){
                                    birthday = "" + bDate.getSelectedItem() + "/" + bMonth.getSelectedItem() + "/" + bYear.getSelectedItem();
                                }else{
                                    birthday = birthDate.getText();
                                }

                                String hpNo = contactCom.getSelectedItem() + "-" + contactNo.getText();
                                eRecord.UpdateRecord2(eID, empName.getText(), MF, birthday, address.getText(), hpNo, Integer.parseInt(salary.getText()), bankAccount.getText());
                                JOptionPane.showMessageDialog(null, "Update Successful", "Message", JOptionPane.INFORMATION_MESSAGE); 
                                // Re-disable the textfield
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
    }
    
    public void reset(){
        empName.setEnabled(false);
        rdbFemale.setEnabled(false);
        rdbMale.setEnabled(false);
        address.setEnabled(false);
        bDate.setEnabled(false);
        bMonth.setEnabled(false);
        bYear.setEnabled(false);
        contactCom.setEnabled(false);  
        contactNo.setEnabled(false);
        salary.setEnabled(false);
        bankAccount.setEnabled(false);
        userID.setEnabled(false);      
        update.setEnabled(false);
        enter.setEnabled(true);
        empID.setEnabled(true);

        empID.setText("");
        empName.setText("");
        address.setText("");
        birthDate.setText("");
        genderCtgy.clearSelection();
        contactCom.setSelectedIndex(0);
        contactNo.setText("");
        bDate.setSelectedIndex(0);
        bMonth.setSelectedIndex(0);
        bYear.setSelectedIndex(0);
        joinDate.setText("");
        salary.setText("");
        bankAccount.setText("");        
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
        new editEmployee();
    }
}
