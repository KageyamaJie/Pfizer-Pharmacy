package pharmacysystem;
/**
 *
 * @author Manfred Ooi 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;
import java.util.*;
import java.text.*;
import DA.*;
import Domain.*;
import Logic.*;

public class orderPage extends JFrame{
    Date dNow = new Date( ); 
    SimpleDateFormat ft1 = new SimpleDateFormat ("yyyy");
    SimpleDateFormat ft2 = new SimpleDateFormat ("MM");
    SimpleDateFormat ft3 = new SimpleDateFormat ("dd");
    // Decimal
    DecimalFormat deci = new DecimalFormat("0.00");
    
    // Array for store price
    protected ArrayList<String> itemBuy = new ArrayList<>();
    protected ArrayList<Integer> itemAmountList = new ArrayList<>();
    protected ArrayList<Double> priceList = new ArrayList<>();
    
    private JLabel titles = new JLabel("< PFIZER PHARMACY ORDERING SYSTEM >");

    // DATA LINK access settings
    private String foodNames = "";
    
    //order record normal variable
    private String letter = "O";
    private int noOfID = 0;
    private String orderID = "";
    private int orderNo = 1;
    private int noOfFood;
    private double[] priceTag;
    private String itemInfo = "";
    private String IDstr = "";
    int adTotal, rdTotal, ndTotal, pdTotal, aTotal, sTotal, gTotal, dTotal, creamTotal, CleanserTotal, LotionTotal;
    
    private JTextField text1 = new JTextField(30);
    private JTextArea text2 = new JTextArea();
    private JTextField staffs = new JTextField(20);
    private JTextField loginDate = new JTextField(20);
    private JTextField noOfCustomer = new JTextField();
    
    private ButtonGroup radioGroup = new ButtonGroup();
    private JRadioButton byCash = new JRadioButton("Cash");
    private JRadioButton byCard = new JRadioButton("Master Card");
    private ButtonGroup memVip = new ButtonGroup();
    private JRadioButton normal = new JRadioButton("Member");
    private JRadioButton nonmem = new JRadioButton("Non-member");
    
    private JButton delete = new JButton("Delete");
    private JButton logOut = new JButton("Log out");
    
    private JLabel lblNoCustomer = new JLabel("No.of Customer");
    
    public int loop = 0;
    // East Variable Settings
    private JButton purchase = new JButton("Purchase");
    private JTextArea purchaseShow = new JTextArea();
    private JButton refresh = new JButton("Refresh");
    
    private static String ID2 = "";
    
    // Agile button
    String[] medicineName;
    String[] medicineName2;
    String[] medicineName3;
    String[] medicineName4;
    String[] medicineName5;
    String[] medicineName6;
    String[] medicineName7;
    String[] medicineName8;
    String[] medicineName9;
    String[] medicineName10;
    String[] medicineName11;
    JButton[] rd;
    JButton[] ad;
    JButton[] nd;
    JButton[] pd;
    JButton[] a;
    JButton[] s;
    JButton[] g;
    JButton[] d;
    JButton[] cream;
    JButton[] cleanser;
    JButton[] lotion;
    int access, token, numChar, number;
    String ctgy, productCost, tranIDString, incrementID;
    char checkChar;
    
    double totalBeforeTax, totalAfterDiscount;
    // must declare othwise event error occur
    productRecord pR = new productRecord();
    Product medicine = new Product();
    Transaction transaction = new Transaction();
    TransactionRecord tRecord = new TransactionRecord();
    memberRecord mRecord = new memberRecord();
    Member mem;
    public orderPage(){
        
    }
    
    public orderPage(String ID){
        setUndecorated(true);
        titles.setHorizontalAlignment(SwingConstants.CENTER);
        titles.setFont(new Font("Times New Roman", Font.BOLD, 20));
        staffs.setEditable(false);
        staffs.setText(ID);
        IDstr = ID;
        loginDate.setEditable(false);
        loginDate.setText(ft1.format(dNow) + "/" + ft2.format(dNow)+ "/"+ ft3.format(dNow));
        JTabbedPane tab = new JTabbedPane();
        JTabbedPane subtab = new JTabbedPane();
        
        //---------------------------- Insert food button and listener
        // new variable created for agile
        int total = 0;
        int col = 0;
        int row = 0;
        
        
        // Anti-infective settings------------------------------------------------
        adTotal = 0;
        total = pR.numProductCategory("Anti-infective drugs");
        ad = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel CPanel = new JPanel(new GridLayout(row,4));
        CPanel.setBorder(new TitledBorder("Anti-infective drugs"));
        for(int i = 0; i < total; i++){
            medicineName2 = pR.productCategory("Anti-infective drugs");
            priceTag = pR.productPrice("Anti-infective drugs");
            
            CPanel.add(ad[i] = new JButton(medicineName2[i]));
            ad[i].addActionListener(new adListener());
            ad[i].setToolTipText(""+priceTag[i]);
            ++adTotal;
        }
        
        // Respiratory settings 
        rdTotal = 0;
        total = pR.numProductCategory("Respiratory drugs");
        rd = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel FPanel = new JPanel(new GridLayout(row,4));
        FPanel.setBorder(new TitledBorder("Respiratory drugs"));
        for(int i = 0; i < total; i++){
            medicineName = pR.productCategory("Respiratory drugs");
                        priceTag = pR.productPrice("Respiratory drugs");
            
            FPanel.add(rd[i] = new JButton(medicineName[i]));
            rd[i].addActionListener(new rdListener());
            rd[i].setToolTipText(""+priceTag[i]);
            ++rdTotal;
        }    
        
        // Neurological settings
        ndTotal = 0;
        total = pR.numProductCategory("Neurological drugs");
        nd = new JButton[total];
        col = total % 4;
        row = total / 4;
        JPanel PPanel = new JPanel(new GridLayout(row,4));
        PPanel.setBorder(new TitledBorder("Neurological drugs"));
        for(int i = 0; i < total; i++){
            medicineName3 = pR.productCategory("Neurological drugs");
            priceTag = pR.productPrice("Neurological drugs");
            
            PPanel.add(nd[i] = new JButton(medicineName3[i]));
            nd[i].addActionListener(new ndListener());
            nd[i].setToolTipText(""+priceTag[i]);
            ++ndTotal;
        }
        
        // Psychotropic settings
        pdTotal = 0;
        total = pR.numProductCategory("Psychotropic drugs");
        pd = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel LPanel = new JPanel(new GridLayout(row, 4));
        LPanel.setBorder(new TitledBorder("Psychotropic drugs"));
        for(int i = 0; i < total; i++){
            medicineName4= pR.productCategory("Psychotropic drugs");
            priceTag = pR.productPrice("Psychotropic drugs");
            
            LPanel.add(pd[i] = new JButton(medicineName4[i]));
            pd[i].addActionListener(new pdListener());
            pd[i].setToolTipText(""+priceTag[i]);
            ++pdTotal;
        }
        
        // Antibiotic settings
        aTotal = 0;
        total = pR.numProductCategory("Antibiotic");
        a = new JButton[total];
        col = total % 4;
        row = total / 4;   
        if(col > 0)
            row++;
        JPanel MPanel = new JPanel(new GridLayout(row, 4));
        MPanel.setBorder(new TitledBorder("Antibiotic"));
        for(int i = 0; i < total; i++){
            medicineName5 = pR.productCategory("Antibiotic");
            priceTag = pR.productPrice("Antibiotic");
            
            MPanel.add(a[i] = new JButton(medicineName5[i]));
            a[i].addActionListener(new aListener());
            a[i].setToolTipText(""+priceTag[i]);
            ++aTotal;
        }
        
        // Supplement settings
        sTotal = 0;
        total = pR.numProductCategory("Supplement");
        s = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel SPanel = new JPanel(new GridLayout(row, 4));
        SPanel.setBorder(new TitledBorder("Supplement"));
        for(int i = 0; i < total; i++){
            medicineName6 = pR.productCategory("Supplement");
            priceTag = pR.productPrice("Supplement");
            
            SPanel.add(s[i] = new JButton(medicineName6[i]));
            s[i].addActionListener(new sListener());
            s[i].setToolTipText(""+priceTag[i]);
            ++sTotal;
        }
        
        // Gel settings
        gTotal = 0;
        total = pR.numProductCategory("Gel");
        g = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel BFPanel = new JPanel(new GridLayout(row, 4));
        BFPanel.setBorder(new TitledBorder("Gel"));
        for(int i = 0; i < total; i++){
            medicineName7= pR.productCategory("Gel");
            priceTag = pR.productPrice("Gel");
            
            BFPanel.add(g[i] = new JButton(medicineName7[i]));
            g[i].addActionListener(new gListener());
            g[i].setToolTipText(""+priceTag[i]);
            ++gTotal;
        }       
        
        // Appetizer settings
        dTotal = 0;
        total = pR.numProductCategory("Desinfectant");
        d = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel APanel = new JPanel(new GridLayout(row, 4));
        APanel.setBorder(new TitledBorder("Desinfectant"));    
        for(int i = 0; i < total; i++){
            medicineName8 = pR.productCategory("Desinfectant");
            priceTag = pR.productPrice("Desinfectant");
            
            APanel.add(d[i] = new JButton(medicineName8[i]));
            d[i].addActionListener(new dListener());
            d[i].setToolTipText(""+priceTag[i]);
            ++dTotal;
        }

        // Cream settings 
        creamTotal = 0;
        total = pR.numProductCategory("Cream");
        cream = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel CreamPanel = new JPanel(new GridLayout(row,4));
        FPanel.setBorder(new TitledBorder("Cream"));
        for(int i = 0; i < total; i++){
            medicineName9 = pR.productCategory("Cream");
            priceTag = pR.productPrice("Cream");
            
            CreamPanel.add(cream[i] = new JButton(medicineName9[i]));
            cream[i].addActionListener(new creamListener());
            cream[i].setToolTipText(""+priceTag[i]);
            ++creamTotal;
        } 
        
        // Cleanser settings 
        CleanserTotal = 0;
        total = pR.numProductCategory("Cleanser");
        cleanser = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel CleanserPanel = new JPanel(new GridLayout(row,4));
        FPanel.setBorder(new TitledBorder("Cleanser"));
        for(int i = 0; i < total; i++){
            medicineName10 = pR.productCategory("Cleanser");
            priceTag = pR.productPrice("Cleanser");
            
            CleanserPanel.add(cleanser[i] = new JButton(medicineName10[i]));
            cleanser[i].addActionListener(new CleanserListener());
            cleanser[i].setToolTipText(""+priceTag[i]);
            ++CleanserTotal;
        }   
        
        // Cleanser settings 
        LotionTotal = 0;
        total = pR.numProductCategory("Lotion");
        lotion = new JButton[total];
        col = total % 4;
        row = total / 4;
        if(col > 0)
            row++;
        JPanel LotionPanel = new JPanel(new GridLayout(row,4));
        FPanel.setBorder(new TitledBorder("Lotion"));
        for(int i = 0; i < total; i++){
            medicineName11 = pR.productCategory("Lotion");
            priceTag = pR.productPrice("Lotion");
            
            LotionPanel.add(lotion[i] = new JButton(medicineName11[i]));
            lotion[i].addActionListener(new LotionListener());
            lotion[i].setToolTipText(""+priceTag[i]);
            ++LotionTotal;
        }
        
        // eastPanel settings (Display)
        //-----------------------------
        purchaseShow.setEditable(false);
         
        JPanel output = new JPanel(new BorderLayout());
        JScrollPane scrollArea = new JScrollPane(purchaseShow);
        output.add(scrollArea);
        
        JPanel memberOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
        memberOption.setBorder(new TitledBorder("Member"));
        memVip.add(normal);
        memVip.add(nonmem);
        memberOption.add(nonmem);
        memberOption.add(normal);
        
        JPanel payOption = new JPanel(new FlowLayout(FlowLayout.LEFT));
        payOption.setBorder(new TitledBorder("Payment method"));
        radioGroup.add(byCash);
        radioGroup.add(byCard);
        payOption.add(byCash);
        payOption.add(byCard);
         
        JPanel PDRbtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PDRbtnPanel.add(purchase);
        PDRbtnPanel.add(delete);
        PDRbtnPanel.add(refresh);
        
        JPanel mainEastBtmPanel = new JPanel(new GridLayout(4, 1));
//        mainEastBtmPanel.add(inputPanel);
        mainEastBtmPanel.add(memberOption);
        mainEastBtmPanel.add(payOption);
        mainEastBtmPanel.add(PDRbtnPanel);
        
        JPanel mainEastPanel = new JPanel(new BorderLayout());// East side information Panel
        mainEastPanel.setBorder(new TitledBorder("Details Information"));
        //mainEastPanel.add(tableList, BorderLayout.NORTH);
        mainEastPanel.add(output, BorderLayout.CENTER);
        mainEastPanel.add(mainEastBtmPanel, BorderLayout.SOUTH);
        IDUpdate();
        // Action Event
        purchase.addActionListener(new ActionListener() { // process purchase function====================================================
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                int quantity = 0;
                double total = 0;
                double change = 0;
                for(int i = 0; i < itemBuy.size(); i++){
                    output += String.format("\n%d.%-50s %5d Qty", (i+1), itemBuy.get(i), itemAmountList.get(i));
                }
                for(int i = 0; i < priceList.size(); i++){
                    total += (priceList.get(i) * itemAmountList.get(i));
                }
                for(int i = 0; i < itemAmountList.size(); i++){
                    quantity += itemAmountList.get(i);
                }
                if(purchaseShow.getText().isEmpty()){ // check if empty
                    JOptionPane.showMessageDialog(null, "No item is made. Cannot make payment right now", "Notice", JOptionPane.WARNING_MESSAGE);
                }else{
                    if(normal.isSelected()){
                        String membershipID = JOptionPane.showInputDialog(null, "Please enter Customer Membership ID", "Membership ID", JOptionPane.INFORMATION_MESSAGE);
                        mem = mRecord.SelectRecord(membershipID);
                        
                            if(byCash.isSelected()){ // for member
                                total *= 0.90;
                                totalAfterDiscount = total;
                                total = gst(total); // include tax
                                try{
                                    String paid = JOptionPane.showInputDialog(null, "Total Amount need to pay: RM" + deci.format(total) + ". Please enter customer pay", "Input", JOptionPane.INFORMATION_MESSAGE);
                                    if(compare(total, Double.parseDouble(paid))){
                                    generateReceipt(total, Double.parseDouble(paid), cashBack(total, Double.parseDouble(paid)));
                                    JOptionPane.showMessageDialog(null, "Payment Made Successful.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                    tRecord.CreateRecord(incrementID, total, ft3.format(dNow) + "/" + ft2.format(dNow)+ "/"+ ft1.format(dNow));
                                    IDUpdate();
                                    resetData();
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Total payment Cannot more than pay.", "Notice", JOptionPane.WARNING_MESSAGE);
                                    }
                                }catch(NumberFormatException s){
                                    JOptionPane.showMessageDialog(null, "No character is allow.");
                                }catch(Exception ex){
                                    JOptionPane.showMessageDialog(null, "Cannot left empty. Please enter the value.");
                                }
                            }else if(byCard.isSelected()){ // for member
                                total *= 0.90;
                                totalAfterDiscount = total;
                                total = gst(total); // include tax
                                String creditNo = JOptionPane.showInputDialog(null, "Enter customer credit card number", "Input", JOptionPane.INFORMATION_MESSAGE);
                                
                                    double paid = total;
                                    if(compare(total, paid)){
                                        generateReceipt(total, paid, cashBack(total, paid));
                                        JOptionPane.showMessageDialog(null, "Payment Made Successful.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                        tRecord.CreateRecord(incrementID, total, ft3.format(dNow) + "/" + ft2.format(dNow)+ "/"+ ft1.format(dNow));
                                        IDUpdate();
                                        resetData();
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Total payment Cannot more than pay.", "Notice", JOptionPane.WARNING_MESSAGE);
                                    }                                    


                            }else{
                                JOptionPane.showMessageDialog(null, "Kindly select payment method.", "Notice", JOptionPane.WARNING_MESSAGE);
                            }                            

                    }else if(nonmem.isSelected()){
                        if(byCash.isSelected()){ // for non-member
                            totalAfterDiscount = total;
                            total = gst(total); // include tax
                            try{
                                String paid = JOptionPane.showInputDialog(null, "Total Amount need to pay: RM" + deci.format(total) + ". Please enter customer pay", "Input", JOptionPane.INFORMATION_MESSAGE);
                                if(compare(total, Double.parseDouble(paid))){
                                generateReceipt(total, Double.parseDouble(paid), cashBack(total, Double.parseDouble(paid)));
                                JOptionPane.showMessageDialog(null, "Payment Made Successful.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                tRecord.CreateRecord(incrementID, total, ft3.format(dNow) + "/" + ft2.format(dNow)+ "/"+ ft1.format(dNow));
                                IDUpdate();
                                resetData();
                                }else{
                                    JOptionPane.showMessageDialog(null, "Total payment Cannot more than pay.", "Notice", JOptionPane.WARNING_MESSAGE);
                                }
                            }catch(Exception ex){
                                JOptionPane.showMessageDialog(null, "Cannot left empty. Please enter the value.");
                            }

                        }else if(byCard.isSelected()){ // for non-member
                            totalAfterDiscount = total;
                            total = gst(total); // include tax
                            String creditNo = JOptionPane.showInputDialog(null, "Enter customer credit card number", "Input", JOptionPane.INFORMATION_MESSAGE);
                            try{
                                
                                    double paid = total;
                                    if(compare(total, paid)){
                                        generateReceipt(total, paid, cashBack(total, paid));
                                        JOptionPane.showMessageDialog(null, "Payment Made Successful.", "Notice", JOptionPane.INFORMATION_MESSAGE);
                                        tRecord.CreateRecord(incrementID, total, ft3.format(dNow) + "/" + ft2.format(dNow)+ "/"+ ft1.format(dNow));
                                        IDUpdate();
                                        resetData();
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Total payment Cannot more than pay.", "Notice", JOptionPane.WARNING_MESSAGE);
                                    }                                    
                                
                            }catch(NumberFormatException s){
                                JOptionPane.showMessageDialog(null, "No character is allow.");
                            }catch(Exception ex){
                                JOptionPane.showMessageDialog(null, "Cannot left empty. Please enter the value.");
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Kindly select payment method.", "Notice", JOptionPane.WARNING_MESSAGE);
                        }                        
                    }else{
                        JOptionPane.showMessageDialog(null, "Kindly select customer status.", "Notice", JOptionPane.WARNING_MESSAGE);
                    }                        
                }
            }
        });
        
        delete.addActionListener(new ActionListener() { // delete purchased items
            @Override
            public void actionPerformed(ActionEvent e) {
                String output = "";
                String delIndex;
                try{
                    for(int i = 0; i < itemBuy.size(); i++){
                        output += String.format("%d. %s\n", (i+1), itemBuy.get(i));
                    }
                    JOptionPane.showMessageDialog(null, output, "Item List", JOptionPane.INFORMATION_MESSAGE);
                    delIndex = JOptionPane.showInputDialog(null, "Which row number you wish to remove?");

                    itemBuy.remove(Integer.parseInt(delIndex) - 1);
                    itemAmountList.remove(Integer.parseInt(delIndex) - 1);
                    priceList.remove(Integer.parseInt(delIndex) - 1);

                    output = "";
                    for(int i = 0; i < itemBuy.size(); i++){
                        output += String.format("%d. %s\n", (i+1), itemBuy.get(i));
                    }
                    JOptionPane.showMessageDialog(null, output);                    
                }catch(IndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(null, "No such record can be deleted", "Error",JOptionPane.ERROR_MESSAGE);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Input box cannot be null", "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        refresh.addActionListener(new ActionListener() { // refresh latest records
            @Override
            public void actionPerformed(ActionEvent e) {
                    String output = "";
                    for(int i = 0; i < itemBuy.size(); i++){
                        output += (i+1) + ". " + itemBuy.get(i) + "\t" + itemAmountList.get(i) + " sets\n";
                    }
                    purchaseShow.setText(output);
                    
            }
        });

        //----------------------------------------- END OF EAST PANEL-----------------------------------------------------------------------------------
        
        // southPanel settings (Display)
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        southPanel.setBorder(new TitledBorder("Staff Information"));
        southPanel.add(new JLabel("Staff ID "));
        southPanel.add(staffs);
        southPanel.add(new JLabel("Log in Date "));
        southPanel.add(loginDate);
        southPanel.add(logOut);
        
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int i = JOptionPane.showConfirmDialog(null, "Do you wish to back to login page?", "Information", JOptionPane.INFORMATION_MESSAGE);
                if(i == 1){
                    System.exit(0);
                }else if (i == 0){
                    setVisible(false);
                    new login();
                }
            }
        });
        
        // sub tab Panel add here
        subtab.addTab("Anti-infective drugs", CPanel);
        subtab.addTab("Respiratory drugs", FPanel);
        subtab.addTab("Neurological drugs", PPanel);
        subtab.addTab("Psychotropic drugs", LPanel);
        subtab.addTab("Antibiotic", MPanel);
        subtab.addTab("Gel", BFPanel);
        subtab.addTab("Supplement", SPanel);
        subtab.addTab("Desinfectant", APanel);
        subtab.addTab("Cream", CreamPanel);
        subtab.addTab("Cleanser", CleanserPanel);
        subtab.addTab("Lotion", LotionPanel);
        
        // tab Panel add here
        tab.addTab("Medicine Log", subtab);    
        
        //----------------------------------------------------------------------
        add(tab,BorderLayout.CENTER);
        add(mainEastPanel, BorderLayout.EAST);
        add(southPanel, BorderLayout.SOUTH);
        add(titles, BorderLayout.NORTH);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        Toolkit tk = Toolkit.getDefaultToolkit();
        int x = (int)tk.getScreenSize().getWidth();
        int y = (int)tk.getScreenSize().getHeight();
        setVisible(true);
        setSize(x, y);
        
    }
 
    public void IDUpdate(){
        transaction = tRecord.IDIncrement();
        numChar = 0;
        if(tRecord.numTransaction() != 0){
            for(int i = 0 ; i < transaction.getTranID().length(); i++){
                checkChar = transaction.getTranID().charAt(i);
                if(Character.isLetter(checkChar) || checkChar == '0'){
                    numChar++;
                }else{
                    break;
                }   
            }

            tranIDString = transaction.getTranID();
            if(numChar == 1){
                tranIDString = tranIDString.replaceAll("T", "");
                number = Integer.parseInt(tranIDString) + 1;
                incrementID = 'T' + Integer.toString(number);
            }else if(numChar == 2){
                tranIDString = tranIDString.replaceAll("T0", "");
                number = Integer.parseInt(tranIDString) + 1;
                incrementID = "T0" + Integer.toString(number);
                if(number == 1000){
                    incrementID = incrementID.replaceAll("T0", "");
                    incrementID = "T" + incrementID;
                }
            }else if(numChar == 3){
                tranIDString = tranIDString.replaceAll("T00", "");
                number = Integer.parseInt(tranIDString) + 1;
                incrementID = "T00" + Integer.toString(number);
                if(number == 100){
                    incrementID = incrementID.replaceAll("T00", "");
                    incrementID = "T0" + incrementID;
                }
            }else if(numChar == 4){
                tranIDString = tranIDString.replaceAll("T000", "");
                number = Integer.parseInt(tranIDString) + 1;
                incrementID = "T000" + Integer.toString(number);
                if(number == 10){
                    incrementID = incrementID.replaceAll("T000", "");
                    incrementID = "T00" + incrementID;
                }
            }else{
                incrementID = "";
            }            
        }else{
            incrementID = "T0001";
        }
    }
   
    // For Each Button Settings ------------------------------------------------ BUTTON -------------------------------------------------------------- 
    public void actionPerforming(String itemName, double price){
        String amount = "";
        String output = "";
        try{
            do{
                amount = JOptionPane.showInputDialog(null,"Please enter the amount of quantity");
                if(Integer.parseInt(amount) > 0 && amount != null){
                    int yesNo = confirmed();
                    if(yesNo == 0){
                        itemBuy.add(itemName);
                        itemAmountList.add(Integer.parseInt(amount));
                        priceList.add(price);     
                        loop = 0;

                        // Display item purchased
                        for(int i = 0; i < itemBuy.size(); i++){
                         output += (i+1) + ". " + itemBuy.get(i) + "\t" + itemAmountList.get(i) + "sets\n";
                        }
                        purchaseShow.setText(output);
                    }else if(yesNo == 1){
                        loop = 1;
                    }   
                }else if(Integer.parseInt(amount) < 0 ){
                    JOptionPane.showMessageDialog(null, "Must enter atleast less than 1 quantity!");
                }else if(amount == null){
                    JOptionPane.showMessageDialog(null, "Cannot leave it blank!");
                }
            }while(loop == 1);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Characters Symbolic and NULL are not acceptable. Please enter the value.");
        } 
    }
    
    public static int confirmed(){
        int btnRst = JOptionPane.showConfirmDialog(null, "Once the amount insert cannot be altered. Press NO to re-enter.", "Warning", JOptionPane.INFORMATION_MESSAGE);
        return btnRst;
    }
    
    public static double gst(double cash){
        cash *= 1.06;
        int remain;
        cash *= 10000;
        cash = Math.round(cash); // round off the decimal else will get small value in the end like example: 100.000000001
        remain = (int)(cash % 1000);
        
        if(remain > 500){
            if(remain >= 800){
                cash = (cash - remain) + 1000;
                cash /= 10000;
            }else if(remain < 800){
                cash = (cash - remain) + 500;
                cash /= 10000;
            }
        }else if(remain < 500){
            if(remain >= 300){
                cash = (cash - remain) + 500;
                cash /= 10000;
            }else if(remain < 300){
                cash -= remain;
                cash /= 10000;
            }
        }else{
            cash /= 10000;
        }
        return cash;
    }
    
    public void resetData(){
        itemBuy.removeAll(itemBuy);
        itemAmountList.removeAll(itemAmountList);
        priceList.removeAll(priceList);
        purchaseShow.setText("");
        noOfCustomer.setText("");     
        memVip.clearSelection();
        radioGroup.clearSelection();
    }
    
    public boolean compare(double total, double paid){
        if(paid > total || paid == total){
            return true;
        }else{
            return false;
        }
    }
    
    public static double cashBack(double total, double paid){
        double change = 0;
        change = paid - total;
        return change;
    }
    
    public void generateReceipt(double total, double paid, double change){
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        Enumeration<AbstractButton> bg = memVip.getElements();
        String status = "";
        if(normal.isSelected()){
            status = mem.getID();
        }else if(nonmem.isSelected()){
            status = "Non-Member";
        }
        totalBeforeTax = 0;
        itemInfo = String.format("%s\n%s\nReceipt Generated: %s\nCustomer Status: %s\n%s\n"
                + "==========================================================================================================\n", 
                "235 East 42nd Street Sdn Bhd", "NY, NY 10017", ft.format(dNow), status, "Purchased List");
        for(int i = 0; i < itemBuy.size(); i++){
            itemInfo += String.format("%d. %-30s%6dQty%10sRM%.2f\n", (i+1) , itemBuy.get(i) , itemAmountList.get(i) , "" , (itemAmountList.get(i) * priceList.get(i)));
        }       
        itemInfo += String.format("\n==========================================================================================================\n");
        itemInfo += String.format("Total price(Tax included): RM%.2f", total);
        itemInfo += String.format("\nCustomer Paid: RM%.2f", paid);
        itemInfo += String.format("\nChange: RM%.2f", change);
        for(int i = 0; i < priceList.size(); i++){
            totalBeforeTax += (priceList.get(i) * itemAmountList.get(i));
        }
        itemInfo += String.format("\nGST Charged(1.06%%): RM%.2f", total - totalAfterDiscount);

        JOptionPane.showMessageDialog(null, itemInfo, "Receipt", JOptionPane.PLAIN_MESSAGE);  
    }

    public class rdListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < rdTotal; i++){
                if(e.getSource() == rd[i]){
                medicine = pR.SelectRecordByName(medicineName[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    }    

    public class adListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < adTotal; i++){
                if(e.getSource() == ad[i]){
                medicine = pR.SelectRecordByName(medicineName2[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    } 
    
    public class ndListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < ndTotal; i++){
                if(e.getSource() == nd[i]){
                medicine = pR.SelectRecordByName(medicineName3[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    } 
    
    public class pdListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < pdTotal; i++){
                if(e.getSource() == pd[i]){
                medicine = pR.SelectRecordByName(medicineName4[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    }
     public class aListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < aTotal; i++){
                if(e.getSource() == a[i]){
                medicine = pR.SelectRecordByName(medicineName5[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    } 
    public class sListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < sTotal; i++){
                if(e.getSource() == s[i]){
                medicine = pR.SelectRecordByName(medicineName6[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    } 
    public class gListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < gTotal; i++){
                if(e.getSource() == g[i]){
                medicine = pR.SelectRecordByName(medicineName7[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    } 
    public class dListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < dTotal; i++){
                if(e.getSource() == d[i]){
                medicine = pR.SelectRecordByName(medicineName8[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    }
    public class creamListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < creamTotal; i++){
                if(e.getSource() == cream[i]){
                medicine = pR.SelectRecordByName(medicineName9[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    }
    public class CleanserListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < CleanserTotal; i++){
                if(e.getSource() == cleanser[i]){
                medicine = pR.SelectRecordByName(medicineName10[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    }
    public class LotionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < LotionTotal; i++){
                if(e.getSource() == lotion[i]){
                medicine = pR.SelectRecordByName(medicineName11[i]);
                    if(medicine != null){
                        String Fname= medicine.getProductName();
                        double fPrice= medicine.getPrice();
                        actionPerforming(Fname, fPrice);
                    }else{
                        JOptionPane.showConfirmDialog(null, "Cannot perform");
                    }
                }
            }
        }
    }
    
    
    
    public static void main(String[] args){
        new orderPage("Monitor");
    }
}
