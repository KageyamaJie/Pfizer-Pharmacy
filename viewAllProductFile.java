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
class product{
    private String productID;
    private String productName;
    private String type;
    private double price;

    public product(String productID, String productName, String type, double price) {
        this.productID = productID;
        this.productName = productName;
        this.type = type;
        this.price = price;
    }

    public String getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }    
}

public class viewAllProductFile extends JPanel{
    private String host = "jdbc:derby://localhost:1527/FinalYearProject";
    private String user = "nbuser";
    private String password = "nbuser";
    private ResultSet rs;
    private PreparedStatement stmt;
    private String tableName = "PRODUCT";
    private JTable table = new JTable();
    private TableRowSorter<TableModel> sorter;
    private DefaultTableModel model = new DefaultTableModel();    

    private JButton btnRefresh = new JButton("Refresh");
    private JButton btnsearch = new JButton("Search");
    private JTextField searchid = new JTextField();
    private JPanel forsearch = new JPanel(new GridLayout(1,3));    
    Object[] rowData = new Object[4];
    
    public viewAllProductFile(){
        setLayout(new BorderLayout());
        Object[] columnName = new Object[4];
        columnName[0] = "Product ID";
        columnName[1] = "Product Name";
        columnName[2] = "CategoryType";
        columnName[3] = "Price";

        
        model.setColumnIdentifiers(columnName);
        
        for(int i = 0; i < getProduct().size(); i++){
            rowData[0] = getProduct().get(i).getProductID();
            rowData[1] = getProduct().get(i).getProductName();
            rowData[2] = getProduct().get(i).getType();
            rowData[3] = getProduct().get(i).getPrice();
            
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
        searchTitle.add(new JLabel("Product ID: P"));
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
        
        panel.setBorder(new TitledBorder("Product File"));
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
                String text = 'P' + searchid.getText();
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
    
    static ArrayList<product> getProduct(){
        
        ArrayList<product> productFile = new ArrayList<product>();
        
        Connection con = createConnection();
        Statement st;
        ResultSet rs;
        product p = null;
        
        try{
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM PRODUCT");   
            
            while(rs.next()){ 
                
                p = new product(rs.getString("PRODUCTID"), rs.getString("PRODUCTNAME"), rs.getString("TYPE"), rs.getDouble("PRICE"));
                productFile.add(p);
            };
            
            rs.refreshRow();
        }
        catch(SQLException ex){
            ex.getMessage();
        }
   
        return productFile;
    }
    
    public void table_update(){
        model.setRowCount(0); // clear table

        for(int i = 0; i < getProduct().size(); i++){
            rowData[0] = getProduct().get(i).getProductID();
            rowData[1] = getProduct().get(i).getProductName();
            rowData[2] = getProduct().get(i).getType();
            rowData[3] = getProduct().get(i).getPrice();
            
            model.addRow(rowData);
            
        }
        table.setModel(model);
    }
    
    public static void main(String[] args){
        new viewAllProductFile();
    }
}
