package pharmacysystem;

/**
 *
 * @author Manfred Ooi
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import org.jdesktop.swingx.prompt.PromptSupport;
import Logic.*;
import Domain.Employee;

public class login extends JFrame{
    private employeeRecord eRecord;
    private Employee emp = null;
    
    private ImageIcon bgImg = new ImageIcon(getClass().getResource("/Logo/pfizer.png"));
    private JPanel poster = new JPanel();
    
    private JLabel titles = new JLabel("Pfizer Pharmacy");
    private JLabel image = new JLabel();
    private JLabel un = new JLabel("User ID");
    private JLabel pw = new JLabel("Password"); 
     
    private JTextField userName = new JTextField();
    private JPasswordField password = new JPasswordField();
    
    private JButton logBtn = new JButton("Login");
    private JButton cancelBtn = new JButton("Reset");
    
    private static String ID = "";
   
    public login(){
        eRecord = new employeeRecord();
        setTitle("Login");
        setVisible(true);
        // Title 
        titles.setFont(new Font("Courier New", Font.BOLD, 50));
        userName.setFocusable(true);
        
        // Poster settings
        poster.setBackground(Color.WHITE);
        poster.add(new JLabel(bgImg));

        JPanel panel = new JPanel();
        panel.add(titles);
        panel.setBackground(Color.WHITE);
        
        // input panel settings
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2,2));
        inputPanel.add(un);
        inputPanel.add(userName);
        inputPanel.add(pw);
        inputPanel.add(password);
        inputPanel.setBackground(Color.WHITE);
        userName.setFocusable(true);
        userName.setColumns(6);
        password.setEchoChar('*');
        userName.setFont(new Font("Times New Roman", Font.BOLD, 20));
        password.setFont(new Font("Times New Roman", Font.BOLD, 20));
        // import swingX- all 1.6.4 JAR
        PromptSupport.setPrompt("Enter your user ID here", userName);
        PromptSupport.setPrompt("Enter your password here", password);
        
        //set font and size
        un.setFont(new Font("Times New Roman", Font.BOLD, 20));
        un.setHorizontalAlignment(SwingConstants.CENTER);
        pw.setFont(new Font("Times New Roman", Font.BOLD, 20));
        pw.setHorizontalAlignment(SwingConstants.CENTER);
        
        // South Panel settings
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        southPanel.setAlignmentY((float) 30.00);
        southPanel.setBackground(Color.WHITE);
        southPanel.add(logBtn);
        southPanel.add(cancelBtn);
        
        // Button Settings
        logBtn.setPreferredSize(new Dimension(120, 30));
        logBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
        cancelBtn.setPreferredSize(new Dimension(120, 30));
        cancelBtn.setFont(new Font("Times New Roman", Font.BOLD, 20));
        
       
        logBtn.addActionListener(new ActionListener(){
            
           public void actionPerformed(ActionEvent e){
                   emp = eRecord.SelectUserRecord(userName.getText());
                   if(emp != null){
                       if(userName.getText().charAt(0) == 'A'){
                            if((emp.getPassword()).equals(password.getText())){
                               ID = emp.getUserID();
                               dataMonitoringPage dmp = new dataMonitoringPage(ID);
                               dispose();
                            }else if(!(emp.getPassword()).equals(password.getText())){
                                password.setText("");
                                JOptionPane.showMessageDialog(null, "Invalid Password.", "Login", JOptionPane.WARNING_MESSAGE);
                            }                               
                       }else if(userName.getText().charAt(0) == 'S'){
                            if((emp.getPassword()).equals(password.getText())){
                               ID = emp.getUserID();
                               setVisible(false);
                               orderPage OP = new orderPage(ID);
                               dispose();
                               
                            }else if(!(emp.getPassword()).equals(password.getText())){
                                password.setText("");
                                JOptionPane.showMessageDialog(null, "Invalid Password.", "Login", JOptionPane.WARNING_MESSAGE);
                            }                             
                       }
                   }else if(password.getText().equals("admin")){
                       dataMonitoringPage dmp = new dataMonitoringPage(ID);
                   }else{
                       userName.setText("");
                       password.setText("");
                       JOptionPane.showMessageDialog(null, "No such ID. Please Enter The Correct ID.", "Warning", JOptionPane.WARNING_MESSAGE);
                   }
               }
           
        });      
        
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                       userName.setText("");
                       password.setText("");
            }
        });
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.add(panel);
        mainPanel.add(inputPanel);
        mainPanel.add(southPanel);
        mainPanel.setBorder(new TitledBorder("Login"));
        mainPanel.setBackground(Color.WHITE);
        
        add(poster, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        
        setAlwaysOnTop(false);  
        setResizable(false);
        setNimbusLookAndFeel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);
            
    }
    
    private static void setNimbusLookAndFeel(){
         try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.toString());
        } catch (InstantiationException ex) {
            System.err.println(ex.toString());
        } catch (IllegalAccessException ex) {
            System.err.println(ex.toString());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println(ex.toString());
        }
    }      
    
    public static void main(String[] args){
      new login();
    }
    
}
