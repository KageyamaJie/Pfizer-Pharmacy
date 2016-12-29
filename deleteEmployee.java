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
import javax.swing.border.TitledBorder;
import Domain.*;
import Logic.*;
import java.sql.ResultSet;
/**
 *
 * @author Ooi
 */
public class deleteEmployee extends JPanel{
    private employeeRecord eRecord; //
    
    private JPanel topSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel form = new JPanel(new GridLayout(9,2));
    private JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
    private JTextField empID = new JTextField(20);
    private JTextField empName = new JTextField();
    private JTextField gender = new JTextField();
    private JTextField birthDate = new JTextField();
    private JTextField address = new JTextField();
    private JTextField contactNo = new JTextField();
    private JTextField joinDate = new JTextField();
    private JTextField salary = new JTextField();
    private JTextField bankAccount = new JTextField();
    private JTextField userID = new JTextField();
    
    private JButton enter = new JButton("Enter");
    private JButton delete = new JButton("Delete");
    private JButton reset = new JButton("Reset");
    
    int charCount, determine;
    String eID;
    ResultSet rs;
    
    public deleteEmployee(){
        eRecord = new employeeRecord(); //
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Delete Employee Data"));
        topSearch.setBorder(new TitledBorder("ID Search"));
        topSearch.add(new JLabel("Employee ID:   E"));
        topSearch.add(empID);
        empID.setFocusable(true);
        topSearch.add(enter);
       
        
        form.setBorder(new TitledBorder("Employee Information"));
        form.add(new JLabel("Employee Name"));
        form.add(empName);
        form.add(new JLabel("Gender"));
        form.add(gender);
        form.add(new JLabel("Date of Birth[Date/Month/Year]"));
        form.add(birthDate);
        form.add(new JLabel("Home Address"));
        form.add(address);
        form.add(new JLabel("Contact No"));
        form.add(contactNo);
        form.add(new JLabel("Date of Recuit"));
        form.add(joinDate);
        form.add(new JLabel("Salary"));
        form.add(salary);
        form.add(new JLabel("Bank Account No"));
        form.add(bankAccount);
        form.add(new JLabel("User ID"));
        form.add(userID);

        empID.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        //disable
        empName.setEnabled(false);
        gender.setEnabled(false);
        birthDate.setEnabled(false);
        address.setEnabled(false);
        contactNo.setEnabled(false);
        joinDate.setEnabled(false);
        salary.setEnabled(false);
        bankAccount.setEnabled(false);
        userID.setEnabled(false);
        delete.setEnabled(false);
        
        enter.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                if(empID.getText().length() == 4){
                    eID = "E" + empID.getText();
                    Employee emp = eRecord.SelectRecord(eID);
                    if(emp != null){
                        empName.setText(emp.getName());
                        gender.setText(emp.getGender());
                        address.setText(emp.getAddress());
                        contactNo.setText(emp.getContactNo());
                        birthDate.setText(emp.getBirthDate());
                        salary.setText(Integer.toString(emp.getSalary()));
                        joinDate.setText(emp.getJoinDate());
                        bankAccount.setText(emp.getBankAcc());
                        userID.setText(emp.getUserID());
                        empID.setEnabled(false);
                        delete.setEnabled(true);
                        enter.setEnabled(false);
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
        
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                determine = JOptionPane.showConfirmDialog(null, "Are You Sure You Want To Delete The Information of This Employee Record?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                if(determine == 0){
                    // delete the record
                    eRecord.DeleteRecord(eID);
                    JOptionPane.showMessageDialog(null, "Delete Successful", "Message", JOptionPane.INFORMATION_MESSAGE); 
                    //disable 
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
        empID.setText("");
        empName.setText("");
        address.setText("");
        gender.setText("");
        birthDate.setText("");
        joinDate.setText("");
        contactNo.setText("");
        salary.setText("");
        bankAccount.setText("");
        userID.setText("");
        delete.setEnabled(false);
        empID.setEnabled(true);
        enter.setEnabled(true);        
    }
       
    public static void main(String[] args){
        new deleteEmployee();
    }
}
