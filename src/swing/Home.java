/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import library.*;

/**
 *
 * @author ercan
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();

        Home.this.setLayout(new BorderLayout());
        Home.this.add(DynamicPanel);
        ChangeJPanel("MainSelect");
        Home.this.setLocationRelativeTo(null);
        Home.this.setVisible(true);

        boolean created;
        created = Database.fcreate("Accounts");
        if (created == true) {
            int column = Database.columnExists("Accounts");
            if (column != 16) {
                Database.createColumn("Accounts", 0, 16);
            }

            Database.setColumn("Accounts", 0, "ID");
            Database.setColumn("Accounts", 1, "AccountType");
            Database.setColumn("Accounts", 2, "CardNumber");
            Database.setColumn("Accounts", 3, "FullName");
            Database.setColumn("Accounts", 4, "DateofBirth");
            Database.setColumn("Accounts", 5, "Job");
            Database.setColumn("Accounts", 6, "PhoneNumber");
            Database.setColumn("Accounts", 7, "Dollar");
            Database.setColumn("Accounts", 8, "Euro");
            Database.setColumn("Accounts", 9, "Pound");
            Database.setColumn("Accounts", 10, "TurkishLira");
            Database.setColumn("Accounts", 11, "HomeAddress");
            Database.setColumn("Accounts", 12, "Password");
            Database.setColumn("Accounts", 13, "Salary");
            Database.setColumn("Accounts", 14, "CreatedAt");
            Database.setColumn("Accounts", 15, "UpdatedAt");

            String ID = Database.create("Accounts");
            if (ID != null) {
                Date date = new Date();
                Database.set("Accounts", "ID", ID, "AccountType", 2);
                Database.set("Accounts", "ID", ID, "FullName", "Admin");
                Database.set("Accounts", "ID", ID, "DateofBirth", new SimpleDateFormat("dd/MM/yyyy").format(date));
                Database.set("Accounts", "ID", ID, "Password", "Admin123@");
                Database.set("Accounts", "ID", ID, "Salary", "1");
                Database.set("Accounts", "ID", ID, "CreatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
            }
        }

        created = Database.fcreate("Currencies");
        if (created == true) {
            int column = Database.columnExists("Currencies");
            if (column != 5) {
                Database.createColumn("Currencies", 0, 5);
            }

            Database.setColumn("Currencies", 0, "Rate");
            Database.setColumn("Currencies", 1, "Dollar");
            Database.setColumn("Currencies", 2, "Euro");
            Database.setColumn("Currencies", 3, "Pound");
            Database.setColumn("Currencies", 4, "TurkishLira");

            Database.create("Currencies", "Rate", "Dollar");
            Database.create("Currencies", "Rate", "Euro");
            Database.create("Currencies", "Rate", "Pound");
            Database.create("Currencies", "Rate", "Turkish Lira");

            // Dollar
            Database.set("Currencies", "Rate", "Dollar", "Dollar", 1.0);
            Database.set("Currencies", "Rate", "Dollar", "Euro", 1.0);
            Database.set("Currencies", "Rate", "Dollar", "Pound", 1.0);
            Database.set("Currencies", "Rate", "Dollar", "TurkishLira", 1.0);

            // Euro
            Database.set("Currencies", "Rate", "Euro", "Dollar", 1.0);
            Database.set("Currencies", "Rate", "Euro", "Euro", 1.0);
            Database.set("Currencies", "Rate", "Euro", "Pound", 1.0);
            Database.set("Currencies", "Rate", "Euro", "TurkishLira", 1.0);

            // Pound
            Database.set("Currencies", "Rate", "Pound", "Dollar", 1.0);
            Database.set("Currencies", "Rate", "Pound", "Euro", 1.0);
            Database.set("Currencies", "Rate", "Pound", "Pound", 1.0);
            Database.set("Currencies", "Rate", "Pound", "TurkishLira", 1.0);

            // Turkish Lira
            Database.set("Currencies", "Rate", "Turkish Lira", "Dollar", 1.0);
            Database.set("Currencies", "Rate", "Turkish Lira", "Euro", 1.0);
            Database.set("Currencies", "Rate", "Turkish Lira", "Pound", 1.0);
            Database.set("Currencies", "Rate", "Turkish Lira", "TurkishLira", 1.0);
        }

        created = Database.fcreate("Transactions");
        if (created == true) {
            int column = Database.columnExists("Transactions");
            if (column != 8) {
                Database.createColumn("Transactions", 0, 8);
            }

            Database.setColumn("Transactions", 0, "ID");
            Database.setColumn("Transactions", 1, "Account");
            Database.setColumn("Transactions", 2, "Transaction");
            Database.setColumn("Transactions", 3, "Type");
            Database.setColumn("Transactions", 4, "Amount");
            Database.setColumn("Transactions", 5, "TransferTo");
            Database.setColumn("Transactions", 6, "TransferFrom");
            Database.setColumn("Transactions", 7, "DateTime");
        }
    }

    public final void ChangeJPanel(String cName) {

        DynamicPanel.removeAll();

        switch (cName) {
            // Main
            case "Credits":
                DynamicPanel.add(new Credits(this), "Credits");
                break;
            case "MainSelect":
                DynamicPanel.add(new MainSelect(this), "MainSelect");
                break;
            // Admin
            case "AddAdminAdmin":
                DynamicPanel.add(new swing.admin.AddAdmin(this), "AddAdminAdmin");
                break;
            case "AddBankerAdmin":
                DynamicPanel.add(new swing.admin.AddBanker(this), "AddBankerAdmin");
                break;
            case "AdminsAdmin":
                DynamicPanel.add(new swing.admin.Admins(this), "AdminsAdmin");
                break;
            case "BankersAdmin":
                DynamicPanel.add(new swing.admin.Bankers(this), "BankersAdmin");
                break;
            case "CurrencyRateAdmin":
                DynamicPanel.add(new swing.admin.CurrencyRate(this), "CurrencyRateAdmin");
                break;
            case "CustomersAdmin":
                DynamicPanel.add(new swing.admin.Customers(this), "CustomersAdmin");
                break;
            case "DeleteBankerAdmin":
                DynamicPanel.add(new swing.admin.DeleteBanker(this), "DeleteBankerAdmin");
                break;
            case "EditBankerAdmin":
                DynamicPanel.add(new swing.admin.EditBanker(this), "EditBankerAdmin");
                break;
            case "EditMoneyAdmin":
                DynamicPanel.add(new swing.admin.EditMoney(this), "EditMoneyAdmin");
                break;
            case "HomeAdmin":
                DynamicPanel.add(new swing.admin.Home(this), "HomeAdmin");
                break;
            case "LoginAdmin":
                DynamicPanel.add(new swing.admin.Login(this), "LoginAdmin");
                break;
            case "SettingsAdmin":
                DynamicPanel.add(new swing.admin.Settings(this), "SettingsAdmin");
                break;
            // Banker
            case "AddCustomerBanker":
                DynamicPanel.add(new swing.banker.AddCustomer(this), "AddCustomerBanker");
                break;
            case "BalanceBanker":
                DynamicPanel.add(new swing.banker.Balance(this), "BalanceBanker");
                break;
            case "BankersBanker":
                DynamicPanel.add(new swing.banker.Bankers(this), "BankersBanker");
                break;
            case "CustomersBanker":
                DynamicPanel.add(new swing.banker.Customers(this), "CustomersBanker");
                break;
            case "DeleteCustomerBanker":
                DynamicPanel.add(new swing.banker.DeleteCustomer(this), "DeleteCustomerBanker");
                break;
            case "EditCustomerBanker":
                DynamicPanel.add(new swing.banker.EditCustomer(this), "EditCustomerBanker");
                break;
            case "HomeBanker":
                DynamicPanel.add(new swing.banker.Home(this), "HomeBanker");
                break;
            case "LoginBanker":
                DynamicPanel.add(new swing.banker.Login(this), "LoginBanker");
                break;
            case "SettingsBanker":
                DynamicPanel.add(new swing.banker.Settings(this), "SettingsBanker");
                break;
            case "TransactionsBanker":
                DynamicPanel.add(new swing.banker.Transactions(this), "TransactionsBanker");
                break;
            // Customer
            case "BalanceCustomer":
                DynamicPanel.add(new swing.customer.Balance(this), "BalanceCustomer");
                break;
            case "DepositCustomer":
                DynamicPanel.add(new swing.customer.Deposit(this), "DepositCustomer");
                break;
            case "HomeCustomer":
                DynamicPanel.add(new swing.customer.Home(this), "HomeCustomer");
                break;
            case "InformationCustomer":
                DynamicPanel.add(new swing.customer.Information(this), "InformationCustomer");
                break;
            case "LoginCustomer":
                DynamicPanel.add(new swing.customer.Login(this), "LoginCustomer");
                break;
            case "ReceiptCustomer":
                DynamicPanel.add(new swing.customer.Receipt(this), "ReceiptCustomer");
                break;
            case "SettingsCustomer":
                DynamicPanel.add(new swing.customer.Settings(this), "SettingsCustomer");
                break;
            case "TransactionsCustomer":
                DynamicPanel.add(new swing.customer.Transactions(this), "TransactionsCustomer");
                break;
            case "TransferCustomer":
                DynamicPanel.add(new swing.customer.Transfer(this), "TransferCustomer");
                break;
            case "TransferMoneyCustomer":
                DynamicPanel.add(new swing.customer.TransferMoney(this), "TransferMoneyCustomer");
                break;
            case "WarningCustomer":
                DynamicPanel.add(new swing.customer.Warning(this), "WarningCustomer");
                break;
            case "WithdrawalCustomer":
                DynamicPanel.add(new swing.customer.Withdrawal(this), "WithdrawalCustomer");
                break;
            default:
                break;
        }

        CardLayout cl = (CardLayout) DynamicPanel.getLayout();
        cl.show(DynamicPanel, cName);
        pack();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bankicon = new javax.swing.JLabel();
        boblabel = new javax.swing.JLabel();
        closebtn = new javax.swing.JButton();
        minimizebtn = new javax.swing.JButton();
        DynamicPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank of Business");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(23, 35, 51));
        jPanel2.setAlignmentX(0.0F);
        jPanel2.setAlignmentY(0.0F);
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        bankicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_bank_48px.png"))); // NOI18N

        boblabel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        boblabel.setForeground(new java.awt.Color(255, 255, 255));
        boblabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boblabel.setText("Bank of Business");

        closebtn.setForeground(new java.awt.Color(255, 255, 255));
        closebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_close_window_50px.png"))); // NOI18N
        closebtn.setContentAreaFilled(false);
        closebtn.setMaximumSize(new java.awt.Dimension(50, 50));
        closebtn.setMinimumSize(new java.awt.Dimension(50, 50));
        closebtn.setPreferredSize(new java.awt.Dimension(50, 50));
        closebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closebtnActionPerformed(evt);
            }
        });

        minimizebtn.setForeground(new java.awt.Color(255, 255, 255));
        minimizebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_minimize_window_50px.png"))); // NOI18N
        minimizebtn.setContentAreaFilled(false);
        minimizebtn.setMaximumSize(new java.awt.Dimension(50, 50));
        minimizebtn.setMinimumSize(new java.awt.Dimension(50, 50));
        minimizebtn.setPreferredSize(new java.awt.Dimension(50, 50));
        minimizebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minimizebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(bankicon)
                .addGap(6, 6, 6)
                .addComponent(boblabel)
                .addGap(744, 744, 744)
                .addComponent(minimizebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(closebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bankicon)
                .addGap(2, 2, 2))
            .addComponent(closebtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(minimizebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(boblabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        DynamicPanel.setBackground(new java.awt.Color(71, 120, 197));
        DynamicPanel.setPreferredSize(new java.awt.Dimension(1070, 590));
        DynamicPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(DynamicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(DynamicPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        // TODO add your handling code here:
        //drag this pane
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        // TODO add your handling code here:

        //source to drag
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xx, y - xy);
    }//GEN-LAST:event_jPanel2MouseDragged

    private void closebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closebtnActionPerformed
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_closebtnActionPerformed

    private void minimizebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizebtnActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizebtnActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (SystemTray.isSupported()) {
            String message;
            if (Data.getAdmin() != null) {
                message = Data.getAdmin().leavingMessage();
            } else if (Data.getBanker() != null) {
                message = Data.getBanker().leavingMessage();
            } else if (Data.getCustomer() != null) {
                message = Data.getCustomer().leavingMessage();
            } else {
                message = "See you later!";
            }

            TrayIcon trayIcon = new TrayIcon(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/swing/images/icons8_bank_48px.png")), "Good Bye");
            trayIcon.setToolTip("Bank of Business");
            trayIcon.setImageAutoSize(true);

            try {
                SystemTray.getSystemTray().add(trayIcon);
            } catch (AWTException ex) {
                System.out.println(ex.toString());
            }

            trayIcon.displayMessage("Bank of Business", message, TrayIcon.MessageType.INFO);
        }
    }//GEN-LAST:event_formWindowClosing

    int xx, xy;

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DynamicPanel;
    private javax.swing.JLabel bankicon;
    private javax.swing.JLabel boblabel;
    private javax.swing.JButton closebtn;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton minimizebtn;
    // End of variables declaration//GEN-END:variables
}
