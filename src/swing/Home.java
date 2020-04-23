/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
        if(created == true) {
            int column = Database.columnExists("Accounts");
            if(column != 14) Database.createColumn("Accounts", 0, 14);
            
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
        }
        
        created = Database.fcreate("Currencies");
        if(created == true) {
            int column = Database.columnExists("Currencies");
            if(column != 5) Database.createColumn("Currencies", 0, 5);
            
            Database.setColumn("Currencies", 0, "Rate");
            Database.setColumn("Currencies", 1, "Dollar");
            Database.setColumn("Currencies", 2, "Euro");
            Database.setColumn("Currencies", 3, "Pound");
            Database.setColumn("Currencies", 4, "TurkishLira");
            
            Database.create("Currencies", "Rate", "Dollar");
            Database.create("Currencies", "Rate", "Euro");
            Database.create("Currencies", "Rate", "Pound");
            Database.create("Currencies", "Rate", "TurkishLira");
            
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
            Database.set("Currencies", "Rate", "TurkishLira", "Dollar", 1.0);
            Database.set("Currencies", "Rate", "TurkishLira", "Euro", 1.0);
            Database.set("Currencies", "Rate", "TurkishLira", "Pound", 1.0);
            Database.set("Currencies", "Rate", "TurkishLira", "TurkishLira", 1.0);
        }
    }
    
    public final void ChangeJPanel(String cName) {
        
        DynamicPanel.removeAll();
        
        // Main
        if(cName.equals("MainSelect")) DynamicPanel.add(new MainSelect(this), "MainSelect");
        
        // Admin
        else if(cName.equals("AddAdminAdmin")) DynamicPanel.add(new swing.admin.AddAdmin(this), "AddAdminAdmin");
        else if(cName.equals("AddBankerAdmin")) DynamicPanel.add(new swing.admin.AddBanker(this), "AddBankerAdmin");
        else if(cName.equals("AdminsAdmin")) DynamicPanel.add(new swing.admin.Admins(this), "AdminsAdmin");
        else if(cName.equals("BankersAdmin")) DynamicPanel.add(new swing.admin.Bankers(this), "BankersAdmin");
        else if(cName.equals("CurrencyRateAdmin")) DynamicPanel.add(new swing.admin.CurrencyRate(this), "CurrencyRateAdmin");
        else if(cName.equals("CustomersAdmin")) DynamicPanel.add(new swing.admin.Customers(this), "CustomersAdmin");
        else if(cName.equals("DeleteBankerAdmin")) DynamicPanel.add(new swing.admin.DeleteBanker(this), "DeleteBankerAdmin");
        else if(cName.equals("EditBankerAdmin")) DynamicPanel.add(new swing.admin.EditBanker(this), "EditBankerAdmin");
        else if(cName.equals("EditMoneyAdmin")) DynamicPanel.add(new swing.admin.EditMoney(this), "EditMoneyAdmin");
        else if(cName.equals("HomeAdmin")) DynamicPanel.add(new swing.admin.Home(this), "HomeAdmin");
        else if(cName.equals("LoginAdmin")) DynamicPanel.add(new swing.admin.Login(this), "LoginAdmin");
        else if(cName.equals("SettingsAdmin")) DynamicPanel.add(new swing.admin.Settings(this), "SettingsAdmin");
        
        // Banker
        else if(cName.equals("AddCustomerBanker")) DynamicPanel.add(new swing.banker.AddCustomer(this), "AddCustomerBanker");
        else if(cName.equals("BalanceBanker")) DynamicPanel.add(new swing.banker.Balance(this), "BalanceBanker");
        else if(cName.equals("BankersBanker")) DynamicPanel.add(new swing.banker.Bankers(this), "BankersBanker");
        else if(cName.equals("CustomersBanker")) DynamicPanel.add(new swing.banker.Customers(this), "CustomersBanker");
        else if(cName.equals("DeleteCustomerBanker")) DynamicPanel.add(new swing.banker.DeleteCustomer(this), "DeleteCustomerBanker");
        else if(cName.equals("EditCustomerBanker")) DynamicPanel.add(new swing.banker.EditCustomer(this), "EditCustomerBanker");
        else if(cName.equals("HomeBanker")) DynamicPanel.add(new swing.banker.Home(this), "HomeBanker");
        else if(cName.equals("LoginBanker")) DynamicPanel.add(new swing.banker.Login(this), "LoginBanker");
        else if(cName.equals("SettingsBanker")) DynamicPanel.add(new swing.banker.Settings(this), "SettingsBanker");
        else if(cName.equals("TransactionsBanker")) DynamicPanel.add(new swing.banker.Transactions(this), "TransactionsBanker");
        
        // Customer
        else if(cName.equals("BalanceCustomer")) DynamicPanel.add(new swing.customer.Balance(this), "BalanceCustomer");
        else if(cName.equals("DepositCustomer")) DynamicPanel.add(new swing.customer.Deposit(this), "DepositCustomer");
        else if(cName.equals("HomeCustomer")) DynamicPanel.add(new swing.customer.Home(this), "HomeCustomer");
        else if(cName.equals("LoginCustomer")) DynamicPanel.add(new swing.customer.Login(this), "LoginCustomer");
        else if(cName.equals("ReceiptCustomer")) DynamicPanel.add(new swing.customer.Receipt(this), "ReceiptCustomer");
        else if(cName.equals("SettingsCustomer")) DynamicPanel.add(new swing.customer.Settings(this), "SettingsCustomer");
        else if(cName.equals("TransactionsCustomer")) DynamicPanel.add(new swing.customer.Transactions(this), "TransactionsCustomer");
        else if(cName.equals("TransferCustomer")) DynamicPanel.add(new swing.customer.Transfer(this), "TransferCustomer");
        else if(cName.equals("TransferMoneyCustomer")) DynamicPanel.add(new swing.customer.TransferMoney(this), "TransferMoneyCustomer");
        else if(cName.equals("WarningCustomer")) DynamicPanel.add(new swing.customer.Warning(this), "WarningCustomer");
        else if(cName.equals("WithdrawalCustomer")) DynamicPanel.add(new swing.customer.Withdrawal(this), "WithdrawalCustomer");
        
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
        setTitle("Bank Of Business");
        setBackground(new java.awt.Color(255, 255, 255));
        setLocationByPlatform(true);
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        closebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_close_window_50px_1.png"))); // NOI18N
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
                .addContainerGap()
                .addComponent(bankicon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(boblabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 744, Short.MAX_VALUE)
                .addComponent(minimizebtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boblabel)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(bankicon)
                .addGap(2, 2, 2))
            .addComponent(closebtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(minimizebtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 50));

        DynamicPanel.setBackground(new java.awt.Color(71, 120, 197));
        DynamicPanel.setPreferredSize(new java.awt.Dimension(1070, 590));
        DynamicPanel.setLayout(new java.awt.CardLayout());
        getContentPane().add(DynamicPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 590));

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
        System.exit(0);
    }//GEN-LAST:event_closebtnActionPerformed

    private void minimizebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minimizebtnActionPerformed
        setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizebtnActionPerformed

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
