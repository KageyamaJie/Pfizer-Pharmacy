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
import java.sql.*;
import java.util.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
/**
 *
 * @author Ooi
 */
class employee{
    private String ID;
    private String name;
    private String gender;
    private String birthDate;
    private String  address;
    private String contactNo;
    private String joinDate;
    private int salary;
    private String bankAcc;
    private String userID;
    
    public employee(String ID, String name, String gender, String birthDate, String  address, String contactNo, String joinDate, int salary, String bankAcc, String userID){
      this.ID = ID;
      this.name = name;
      this.gender = gender;
      this.birthDate = birthDate;
      this.address = address;
      this.contactNo = contactNo;
      this.joinDate = joinDate;
      this.salary = salary;
      this.bankAcc = bankAcc;
      this.userID = userID;       
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }
    
    public String getBirthDate(){
        return birthDate;
    }

    public String getAddress() {
        return address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public int getSalary() {
        return salary;
    }

    public String getBankAcc() {
        return bankAcc;
    }

    public String getUserID() {
        return userID;
    }
    
}

public class viewAllEmployeeFile extends JPanel{
    private String host = "jdbc:derby://localhost:1527/FinalYearProject";
    private String user = "nbuser";
    private String password = "nbuser";
    private ResultSet rs;
    private PreparedStatement stmt;
    private String tableName= "EMPLOYEE";
    private Connection conn = null;
    private TableRowSorter<TableModel> sorter;
    JTable table = new JTable();
    DefaultTableModel model = new DefaultTableModel();
    
    private JButton refreshBtn = new JButton("Refresh");
    private JButton btnsearch = new JButton("Search");
    private JTextField searchid = new JTextField();
    private JPanel forsearch = new JPanel(new GridLayout(1,3));  
    Object[] rowData = new Object[10];
    
    public viewAllEmployeeFile(){
        setLayout(new BorderLayout());

        
        Object[] columnName = new Object[10];
        columnName[0] = "Employee ID";
        columnName[1] = "Employee Name";
        columnName[2] = "Gender";
        columnName[3] = "Birth Date";
        columnName[4] = "Address";
        columnName[5] = "Contact No. 1";
        columnName[6] = "Date of Recruit";
        columnName[7] = "Salary";
        columnName[8] = "Bank Account No.";
        columnName[9] = "User ID";
        
        model.setColumnIdentifiers(columnName);
        
        
        
        for(int i = 0; i < getEmployee().size(); i++){
            rowData[0] = getEmployee().get(i).getID();
            rowData[1] = getEmployee().get(i).getName();
            rowData[2] = getEmployee().get(i).getGender();
            rowData[3] = getEmployee().get(i).getBirthDate();
            rowData[4] = getEmployee().get(i).getAddress();
            rowData[5] = getEmployee().get(i).getContactNo();
            rowData[6] = getEmployee().get(i).getJoinDate();
            rowData[7] = getEmployee().get(i).getSalary();
            rowData[8] = getEmployee().get(i).getBankAcc();
            rowData[9] = getEmployee().get(i).getUserID();
            
            model.addRow(rowData);
            
        }
        sorter= new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);        
        table.setModel(model);
        
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_update();
            }
        });
        
        JPanel searchTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchTitle.add(new JLabel("Employee ID: E"));
        forsearch.add(searchTitle);
        forsearch.add(searchid);
        forsearch.add(btnsearch); 
        
        searchid.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e){
                char input = e.getKeyChar();
                if((input < '0' || input > '9') && input != '\b'){
                    e.consume();
                }  
            }
        });
        
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane pane = new JScrollPane(table);
        panel.setBorder(new TitledBorder("Employee File"));
        panel.add(pane);
        add(panel, BorderLayout.CENTER);
        add(refreshBtn, BorderLayout.SOUTH);
        add(forsearch, BorderLayout.NORTH);
        btnsearch.addActionListener(new searchListener());
        //setSize(1000,1000);
        //setVisible(true);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    static Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FinalYearProject", "nbuser", "nbuser");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
        return conn;
    }

    private class searchListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
                String text = 'E' + searchid.getText();
                if (text.length() == 0){
                    sorter.setRowFilter(null);
                }else{
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    }catch(Exception ex){
                    
                    }
                }
        }
    }
    
    static ArrayList<employee> getEmployee(){
        
        ArrayList<employee> employeeFile = new ArrayList<employee>();
        
        Connection con = createConnection();
        Statement st;
        ResultSet rs;
        employee e = null;
        
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM EMPLOYEE");   
            
            while(rs.next()){ 
                
                e = new employee(rs.getString("employeeID"), rs.getString("employeeName"), rs.getString("gender"), rs.getString("birthDate"), rs.getString("address"), rs.getString("contactNo"), rs.getString("joinDate"), rs.getInt("salary"), rs.getString("bankAcc") , rs.getString("userID"));
                employeeFile.add(e);
            };
        }catch(SQLException ex){
            ex.getMessage();
        }
   
        return employeeFile;
    }
    
    public void table_update(){
        model.setRowCount(0); // clear table
        
        for(int i = 0; i < getEmployee().size(); i++){
            rowData[0] = getEmployee().get(i).getID();
            rowData[1] = getEmployee().get(i).getName();
            rowData[2] = getEmployee().get(i).getGender();
            rowData[3] = getEmployee().get(i).getBirthDate();
            rowData[4] = getEmployee().get(i).getAddress();
            rowData[5] = getEmployee().get(i).getContactNo();
            rowData[6] = getEmployee().get(i).getJoinDate();
            rowData[7] = getEmployee().get(i).getSalary();
            rowData[8] = getEmployee().get(i).getBankAcc();
            rowData[9] = getEmployee().get(i).getUserID();
            
            model.addRow(rowData);
            
        }
        
        table.setModel(model);
    }
    
    public static void main(String[] args){
        new viewAllEmployeeFile();
    }
}
