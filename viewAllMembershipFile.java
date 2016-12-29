/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
/**
 *
 * @author Ooi
 */
class membership{
    private String Id;
    private String name;
    private String birthDate;
    private String gender;
    private String address;
    private String contact1; 
    private String contact2; 
    private int urineAcid; 
    private int cholesterol; 
    private String joinDate; 
    private String expireDate;
    
    public membership(String ID, String name, String gender, String birthDate, String address, String contact1, String contact2, int urineAcid, int cholesterol, String joinDate, String expireDate){
          this.Id = ID;
          this.name = name;
          this.gender = gender;
          this.birthDate = birthDate;
          this.address = address;
          this.contact1 = contact1; 
          this.contact2 = contact2; 
          this.urineAcid = urineAcid; 
          this.cholesterol = cholesterol; 
          this.joinDate = joinDate; 
          this.expireDate = null;
    }
    
    public String getID(){
        return this.Id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getGender(){
        return this.gender;
    }
    
    public String getBirthDate(){
        return this.birthDate;
    }
    
    public String getAddress(){
        return this.address;
    }
    
    public String getContactNo1(){
        return this.contact1;
    }
    
    public String getContactNo2(){
        return this.contact2;
    }
    
    public int getUrineAcidMG(){
        return this.urineAcid;
    }
    
    public int getCholesterolMG(){
        return this.cholesterol;
    }
    
    public String getJoinDate(){
        return this.joinDate;
    }
    
}

public class viewAllMembershipFile extends JPanel{
    
    private String host = "jdbc:derby://localhost:1527/FinalYearProject";
    private String user = "nbuser";
    private String password = "nbuser";
    private ResultSet rs;
    private PreparedStatement stmt;
    private String tableName= "MEMBERSHIP";
    private JTable table = new JTable();
    private TableRowSorter<TableModel> sorter;
    private DefaultTableModel model = new DefaultTableModel();
    
    private JButton btnRefresh = new JButton("Refresh");
    private JButton btnsearch = new JButton("Search");
    private JTextField searchid = new JTextField();
    private JPanel forsearch = new JPanel(new GridLayout(1,3));
    Object[] rowData = new Object[11];
    
    public viewAllMembershipFile(){
        setLayout(new BorderLayout());

        Object[] columnName = new Object[10];
        columnName[0] = "Member ID";
        columnName[1] = "Member Name";
        columnName[2] = "Gender";
        columnName[3] = "Birth Date";
        columnName[4] = "Address";
        columnName[5] = "Contact No 1";
        columnName[6] = "contact No 2";
        columnName[7] = "Urine Acid [MG]";
        columnName[8] = "cholesterolMG";
        columnName[9] = "joinDate";
        
        model.setColumnIdentifiers(columnName);
        
        for(int i = 0; i < getMembership().size(); i++){
            rowData[0] = getMembership().get(i).getID();
            rowData[1] = getMembership().get(i).getName();
            rowData[2] = getMembership().get(i).getGender();
            rowData[3] = getMembership().get(i).getBirthDate();
            rowData[4] = getMembership().get(i).getAddress();
            rowData[5] = getMembership().get(i).getContactNo1();
            rowData[6] = getMembership().get(i).getContactNo2();
            rowData[7] = getMembership().get(i).getUrineAcidMG();
            rowData[8] = getMembership().get(i).getCholesterolMG();
            rowData[9] = getMembership().get(i).getJoinDate();
            
            model.addRow(rowData);
            
        }
        sorter= new TableRowSorter<TableModel>(model);
        table.setRowSorter(sorter);
        table.setModel(model);
        
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table_update();
            }
        });
        JPanel searchTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchTitle.add(new JLabel("Member ID: M"));
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
        
        panel.setBorder(new TitledBorder("Membership File"));
        panel.add(pane);
        add(panel, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);
        add(forsearch, BorderLayout.NORTH);
        btnsearch.addActionListener(new searchListener());
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
                String text = 'M' + searchid.getText();
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
    
    static ArrayList<membership> getMembership(){
        
        ArrayList<membership> membershipFile = new ArrayList<membership>();
        
        Connection con = createConnection();
        Statement st;
        ResultSet rs;
        membership m = null;
        
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM MEMBERSHIP");   
            
            while(rs.next()){ 
                
                m = new membership(rs.getString("memberID"), rs.getString("memberName"), rs.getString("gender"), rs.getString("birthDate"), rs.getString("address"), rs.getString("contactNo1"), rs.getString("contactNo2"), rs.getInt("urineAcidMG"), rs.getInt("cholesterolMG"), rs.getString("joinDate"), null);
                membershipFile.add(m);
            };
            
            rs.refreshRow();
        }
        catch(SQLException ex){
            
        }
   
        return membershipFile;
    } 
    
    public void table_update(){
        model.setRowCount(0); // clear table

        for(int i = 0; i < getMembership().size(); i++){
            rowData[0] = getMembership().get(i).getID();
            rowData[1] = getMembership().get(i).getName();
            rowData[2] = getMembership().get(i).getGender();
            rowData[3] = getMembership().get(i).getBirthDate();
            rowData[4] = getMembership().get(i).getAddress();
            rowData[5] = getMembership().get(i).getContactNo1();
            rowData[6] = getMembership().get(i).getContactNo2();
            rowData[7] = getMembership().get(i).getUrineAcidMG();
            rowData[8] = getMembership().get(i).getCholesterolMG();
            rowData[9] = getMembership().get(i).getJoinDate();
            
            model.addRow(rowData);
            
        }
        table.setModel(model);
    }
    
    public static void main(String[] args){
        new viewAllMembershipFile();
    }
}
