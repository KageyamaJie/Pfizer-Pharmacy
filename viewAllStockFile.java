/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pharmacysystem;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.*;
/**
 *
 * @author Ooi
 */
class inventory{
    private String stockID;
    private int quantity;
    private String manuDate;
    private String expDate;
    private String prodID;

    public inventory(String stockID, int quantity, String manuDate, String expDate, String prodID) {
        this.stockID = stockID;
        this.quantity = quantity;
        this.manuDate = manuDate;
        this.expDate = expDate;
        this.prodID = prodID;
    }

//    inventory(String string, int aInt, String string0, String string1, String string2) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    public String getStockID() {
        return stockID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getManuDate() {
        return manuDate;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getProdID() {
        return prodID;
    }   
}

public class viewAllStockFile extends JPanel{
    private String host = "jdbc:derby://localhost:1527/FinalYearProject";
    private String user = "nbuser";
    private String password = "nbuser";
    private ResultSet rs;
    private PreparedStatement stmt;
    private String tableName = "INVENTORY";
    private JTable table = new JTable();
    private TableRowSorter<TableModel> sorter;
    private DefaultTableModel model = new DefaultTableModel();    

    private JButton btnRefresh = new JButton("Refresh");
    private JButton btnsearch = new JButton("Search");
    private JTextField searchid = new JTextField();
    private JPanel forsearch = new JPanel(new GridLayout(1,3));    
    Object[] rowData = new Object[5];
    
    public viewAllStockFile(){
        setLayout(new BorderLayout());
        Object[] columnName = new Object[5];
        columnName[0] = "Stock ID";
        columnName[1] = "Quantity";
        columnName[2] = "Manufacture Date";
        columnName[3] = "Expired Date";
        columnName[4] = "Product ID";

        
        model.setColumnIdentifiers(columnName);
        
        for(int i = 0; i < getStock().size(); i++){
            rowData[0] = getStock().get(i).getStockID();
            rowData[1] = getStock().get(i).getQuantity();
            rowData[2] = getStock().get(i).getManuDate();
            rowData[3] = getStock().get(i).getExpDate();
            rowData[4] = getStock().get(i).getProdID();
            
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
        
//        JPanel searchTitle = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        searchTitle.add(new JLabel("Stock ID: S"));
//        forsearch.add(searchTitle);
//        forsearch.add(searchid);
//        forsearch.add(btnsearch); 
//        
//        searchid.addKeyListener(new KeyAdapter() {
//            public void keyTyped(KeyEvent e){
//                char input = e.getKeyChar();
//                if((input < '0' || input > '9') && input != '\b'){
//                    e.consume();
//                }  
//            }
//        });
        
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane pane = new JScrollPane(table);
        
        panel.setBorder(new TitledBorder("Inventory File"));
        panel.add(pane);
        add(panel, BorderLayout.CENTER);
        add(btnRefresh, BorderLayout.SOUTH);
        //add(forsearch, BorderLayout.NORTH);
        //btnsearch.addActionListener(new searchListener());
       // setVisible(true);
       // setSize(500, 500);
       // setDefaultCloseOperation(EXIT_ON_CLOSE);
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
    
//    private class searchListener implements ActionListener{
//        public void actionPerformed(ActionEvent e){
//                String text = 'S' + searchid.getText();
//                if (text.length() == 0){
//                    sorter.setRowFilter(null);
//                }else{
//                    try {
//                        sorter.setRowFilter(RowFilter.regexFilter(text));
//                    }catch(Exception ex){
//                    
//                    }
//                }
//        }
//    }
    
    static ArrayList<inventory> getStock(){
        
        ArrayList<inventory> stockFile = new ArrayList<inventory>();
        
        Connection con = createConnection();
        Statement st;
        ResultSet rs;
        inventory i = null;
        
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM INVENTORY");   
            
            while(rs.next()){ 
                
                i = new inventory(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5));
                stockFile.add(i);
            };
            
            rs.refreshRow();
        }
        catch(SQLException ex){
            ex.getMessage();
        }
   
        return stockFile;
    }
    
    public void table_update(){
        model.setRowCount(0); // clear table

        for(int i = 0; i < getStock().size(); i++){
            rowData[0] = getStock().get(i).getStockID();
            rowData[1] = getStock().get(i).getQuantity();
            rowData[2] = getStock().get(i).getManuDate();
            rowData[3] = getStock().get(i).getExpDate();
            rowData[4] = getStock().get(i).getProdID();
            
            model.addRow(rowData);
            
        }
        table.setModel(model);
    }
    
    public static void main(String[] args){
        new viewAllStockFile();
    }
}
