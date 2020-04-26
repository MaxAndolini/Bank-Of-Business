/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import library.*;

/**
 *
 * @author ercan
 */
public class Receipt extends javax.swing.JPanel {

    final private swing.Home frame;

    /**
     * Creates new form Receipt
     *
     * @param home Dynamic panel.
     */
    public Receipt(swing.Home home) {
        initComponents();
        frame = home;

        String[] typename = {"Dollar", "Euro", "Pound", "Turkish Lira"};
        Date date = new Date();
        int ID = Database.create("Transactions");
        Database.set("Transactions", "ID", ID, "Account", Data.getCustomer().getId().getID());
        switch (Data.getPage2()) {
            case "DepositCustomer":
                Database.set("Transactions", "ID", ID, "Transaction", "Deposit");
                break;
            case "WithdrawalCustomer":
                Database.set("Transactions", "ID", ID, "Transaction", "Withdrawal");
                break;
            case "TransferMoneyCustomer":
                Database.set("Transactions", "ID", ID, "Transaction", "Transfer");
                break;
            default:
                break;
        }
        Database.set("Transactions", "ID", ID, "Type", typename[Data.getMoneyType()]);
        Database.set("Transactions", "ID", ID, "Amount", Data.getMoney());
        if (Data.getPage2().equals("TransferMoneyCustomer")) {
            Database.set("Transactions", "ID", ID, "Transfer", Data.getTransfer());
        }
        Database.set("Transactions", "ID", ID, "DateTime", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));

        switch (Data.getPage2()) {
            case "DepositCustomer":
                infolabel.setText("The money has been deposited to your account.");
                break;
            case "WithdrawalCustomer":
                infolabel.setText("The money has been withdrawn from your account.");
                break;
            case "TransferMoneyCustomer":
                infolabel.setText("The money has been transferred to the account.");
                break;
            default:
                break;
        }
        rdatetlabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        rtimetlabel.setText(new SimpleDateFormat("HH:mm:ss").format(date));
        rcnumbertlabel.setText(Data.getCustomer().getCardNumber());
        switch (Data.getPage2()) {
            case "DepositCustomer":
                rtransactiontlabel.setText("Deposit");
                break;
            case "WithdrawalCustomer":
                rtransactiontlabel.setText("Withdrawal");
                break;
            case "TransferMoneyCustomer":
                rtransactiontlabel.setText("Transfer - " + Data.getTransfer());
                break;
            default:
                break;
        }
        rtypetlabel.setText(typename[Data.getMoneyType()]);
        ramounttlabel.setText(Data.getMoney().toString());
        rtotalbalancelabel.setText("Total " + typename[Data.getMoneyType()] + " Balance:");
        switch (Data.getMoneyType()) {
            case 0:
                switch (Data.getPage2()) {
                    case "DepositCustomer":
                        Data.getCustomer().addDollar(Data.getMoney(), 1);
                        break;
                    case "WithdrawalCustomer":
                        Data.getCustomer().subtractDollar(Data.getMoney(), 1);
                        break;
                    case "TransferMoneyCustomer":
                        break;
                    default:
                        break;
                }
                rtotalbalancetlabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                break;

            case 1:
                switch (Data.getPage2()) {
                    case "DepositCustomer":
                        Data.getCustomer().addEuro(Data.getMoney(), 1);
                        break;
                    case "WithdrawalCustomer":
                        Data.getCustomer().subtractEuro(Data.getMoney(), 1);
                        break;
                    case "TransferMoneyCustomer":
                        break;
                    default:
                        break;
                }
                rtotalbalancetlabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                break;

            case 2:
                switch (Data.getPage2()) {
                    case "DepositCustomer":
                        Data.getCustomer().addPound(Data.getMoney(), 1);
                        break;
                    case "WithdrawalCustomer":
                        Data.getCustomer().subtractPound(Data.getMoney(), 1);
                        break;
                    case "TransferMoneyCustomer":
                        break;
                    default:
                        break;
                }
                rtotalbalancetlabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                break;

            case 3:
                switch (Data.getPage2()) {
                    case "DepositCustomer":
                        Data.getCustomer().addTurkishLira(Data.getMoney(), 1);
                        break;
                    case "WithdrawalCustomer":
                        Data.getCustomer().subtractTurkishLira(Data.getMoney(), 1);
                        break;
                    case "TransferMoneyCustomer":
                        break;
                    default:
                        break;
                }
                rtotalbalancetlabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                break;

            default:
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainlabel = new javax.swing.JLabel();
        exitbtn = new java.awt.Button();
        exiticon = new javax.swing.JLabel();
        mainmenubtn = new java.awt.Button();
        mainmenuicon = new javax.swing.JLabel();
        infolabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbankicon = new javax.swing.JLabel();
        rboblabel = new javax.swing.JLabel();
        rdatelabel = new javax.swing.JLabel();
        rtimelabel = new javax.swing.JLabel();
        rdatetlabel = new javax.swing.JLabel();
        rtimetlabel = new javax.swing.JLabel();
        rcnumberlabel = new javax.swing.JLabel();
        rcnumbertlabel = new javax.swing.JLabel();
        rtypelabel = new javax.swing.JLabel();
        rtypetlabel = new javax.swing.JLabel();
        rboblabel2 = new javax.swing.JLabel();
        rtotalbalancelabel = new javax.swing.JLabel();
        rtotalbalancetlabel = new javax.swing.JLabel();
        rtransactionlabel = new javax.swing.JLabel();
        rtransactiontlabel = new javax.swing.JLabel();
        rhaveanicedaylabel = new javax.swing.JLabel();
        ramountlabel = new javax.swing.JLabel();
        ramounttlabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        mainlabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainlabel.setForeground(new java.awt.Color(255, 255, 255));
        mainlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainlabel.setText("Information");
        mainlabel.setMaximumSize(new java.awt.Dimension(223, 47));
        mainlabel.setMinimumSize(new java.awt.Dimension(223, 47));
        mainlabel.setPreferredSize(new java.awt.Dimension(223, 47));

        exitbtn.setBackground(new java.awt.Color(23, 35, 51));
        exitbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        exitbtn.setForeground(new java.awt.Color(255, 255, 255));
        exitbtn.setLabel("Exit");
        exitbtn.setMinimumSize(new java.awt.Dimension(80, 49));
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });

        exiticon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

        mainmenubtn.setBackground(new java.awt.Color(23, 35, 51));
        mainmenubtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        mainmenubtn.setForeground(new java.awt.Color(255, 255, 255));
        mainmenubtn.setLabel("Main Menu");
        mainmenubtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainmenubtnActionPerformed(evt);
            }
        });

        mainmenuicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

        infolabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        infolabel.setForeground(new java.awt.Color(255, 255, 255));
        infolabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel.setText("The money has been withdrawn from your account.");

        jPanel1.setMaximumSize(new java.awt.Dimension(364, 349));
        jPanel1.setMinimumSize(new java.awt.Dimension(364, 349));
        jPanel1.setRequestFocusEnabled(false);

        rbankicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_bank_48px.png"))); // NOI18N

        rboblabel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        rboblabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rboblabel.setText("Bank of Business");

        rdatelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rdatelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rdatelabel.setText("Date:");

        rtimelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtimelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtimelabel.setText("Time:");

        rdatetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rdatetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rdatetlabel.setText("02/04/2020");

        rtimetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtimetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtimetlabel.setText("10:10:10");

        rcnumberlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rcnumberlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rcnumberlabel.setText("Card Number:");

        rcnumbertlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rcnumbertlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rcnumbertlabel.setText("1111222233334444");

        rtypelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtypelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtypelabel.setText("Type:");

        rtypetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtypetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtypetlabel.setText("Dollar");

        rboblabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rboblabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rboblabel2.setText("Bank of Business");

        rtotalbalancelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtotalbalancelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtotalbalancelabel.setText("Total Balance:");

        rtotalbalancetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtotalbalancetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtotalbalancetlabel.setText("$945.456");

        rtransactionlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtransactionlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtransactionlabel.setText("Transaction:");

        rtransactiontlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtransactiontlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtransactiontlabel.setText("Withdrawal");

        rhaveanicedaylabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rhaveanicedaylabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rhaveanicedaylabel.setText("HAVE A NICE DAY!");

        ramountlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ramountlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ramountlabel.setText("Amount:");

        ramounttlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ramounttlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ramounttlabel.setText("94");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rbankicon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rboblabel)
                .addGap(84, 84, 84))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rtimelabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rtimetlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rdatelabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdatetlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rcnumberlabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rcnumbertlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(rtransactionlabel)
                            .addComponent(ramountlabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rtypelabel, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ramounttlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rtransactiontlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rtypetlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rhaveanicedaylabel)
                            .addComponent(rboblabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rtotalbalancelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rtotalbalancetlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rbankicon))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(rboblabel)))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdatelabel)
                    .addComponent(rdatetlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtimelabel)
                    .addComponent(rtimetlabel))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rcnumberlabel)
                    .addComponent(rcnumbertlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtransactiontlabel)
                    .addComponent(rtransactionlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtypelabel)
                    .addComponent(rtypetlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ramountlabel)
                    .addComponent(ramounttlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(rboblabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtotalbalancelabel)
                    .addComponent(rtotalbalancetlabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rhaveanicedaylabel)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(exiticon)
                                .addGap(87, 87, 87)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(mainmenuicon)
                                .addGap(10, 10, 10)
                                .addComponent(mainmenubtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(562, 562, 562))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(239, 239, 239)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exiticon)
                        .addComponent(mainmenubtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mainmenuicon))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        Data.setCustomer(null);
        frame.ChangeJPanel("LoginCustomer");
    }//GEN-LAST:event_exitbtnActionPerformed

    private void mainmenubtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainmenubtnActionPerformed
        frame.ChangeJPanel("HomeCustomer");
    }//GEN-LAST:event_mainmenubtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button exitbtn;
    private javax.swing.JLabel exiticon;
    private javax.swing.JLabel infolabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel mainlabel;
    private java.awt.Button mainmenubtn;
    private javax.swing.JLabel mainmenuicon;
    private javax.swing.JLabel ramountlabel;
    private javax.swing.JLabel ramounttlabel;
    private javax.swing.JLabel rbankicon;
    private javax.swing.JLabel rboblabel;
    private javax.swing.JLabel rboblabel2;
    private javax.swing.JLabel rcnumberlabel;
    private javax.swing.JLabel rcnumbertlabel;
    private javax.swing.JLabel rdatelabel;
    private javax.swing.JLabel rdatetlabel;
    private javax.swing.JLabel rhaveanicedaylabel;
    private javax.swing.JLabel rtimelabel;
    private javax.swing.JLabel rtimetlabel;
    private javax.swing.JLabel rtotalbalancelabel;
    private javax.swing.JLabel rtotalbalancetlabel;
    private javax.swing.JLabel rtransactionlabel;
    private javax.swing.JLabel rtransactiontlabel;
    private javax.swing.JLabel rtypelabel;
    private javax.swing.JLabel rtypetlabel;
    // End of variables declaration//GEN-END:variables
}
