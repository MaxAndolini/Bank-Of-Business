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
        String ID;

        ID = Database.create("Transactions");
        if (ID != null) {
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
                Database.set("Transactions", "ID", ID, "TransferTo", Data.getTransfer());
            }
            Database.set("Transactions", "ID", ID, "DateTime", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
        } else {
            jPanel1.removeAll();
            infolabel.setText("System error and the transaction couldn't be created.");
            return;
        }

        if (Data.getPage2().equals("TransferMoneyCustomer")) {
            ID = Database.create("Transactions");
            if (ID != null) {
                Database.set("Transactions", "ID", ID, "Account", ((Data.getTransfer().length() != 16) ? (Data.getTransfer()) : (Database.getString("Accounts", "CardNumber", Data.getTransfer(), "ID"))));
                Database.set("Transactions", "ID", ID, "Transaction", "Transfer");
                Database.set("Transactions", "ID", ID, "Type", typename[Data.getMoneyType()]);
                Database.set("Transactions", "ID", ID, "Amount", Data.getMoney());
                if (Data.getPage2().equals("TransferMoneyCustomer")) {
                    Database.set("Transactions", "ID", ID, "TransferFrom", Data.getCustomer().getId().getID());
                }
                Database.set("Transactions", "ID", ID, "DateTime", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
            } else {
                jPanel1.removeAll();
                infolabel.setText("System error and the transaction couldn't be created.");
                return;
            }
        }

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
                        Data.getCustomer().subtractDollar(Data.getMoney(), 1);
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "Dollar", (Database.getBigDecimal("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "Dollar").add(Data.getMoney())));
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
                        Data.getCustomer().subtractEuro(Data.getMoney(), 1);
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "Euro", (Database.getBigDecimal("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "Euro").add(Data.getMoney())));
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
                        Data.getCustomer().subtractPound(Data.getMoney(), 1);
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "Pound", (Database.getBigDecimal("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "Pound").add(Data.getMoney())));
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
                        Data.getCustomer().subtractTurkishLira(Data.getMoney(), 1);
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "TurkishLira", (Database.getBigDecimal("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "TurkishLira").add(Data.getMoney())));
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
        infolabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rbankicon = new javax.swing.JLabel();
        rboblabel = new javax.swing.JLabel();
        rdatelabel = new javax.swing.JLabel();
        rdatetlabel = new javax.swing.JLabel();
        rtimelabel = new javax.swing.JLabel();
        rtimetlabel = new javax.swing.JLabel();
        rcnumberlabel = new javax.swing.JLabel();
        rcnumbertlabel = new javax.swing.JLabel();
        rtransactionlabel = new javax.swing.JLabel();
        rtransactiontlabel = new javax.swing.JLabel();
        rtypelabel = new javax.swing.JLabel();
        rtypetlabel = new javax.swing.JLabel();
        ramountlabel = new javax.swing.JLabel();
        ramounttlabel = new javax.swing.JLabel();
        rboblabel2 = new javax.swing.JLabel();
        rtotalbalancelabel = new javax.swing.JLabel();
        rtotalbalancetlabel = new javax.swing.JLabel();
        rhaveanicedaylabel = new javax.swing.JLabel();
        exitbtn = new java.awt.Button();
        exiticon = new javax.swing.JLabel();
        mainmenubtn = new java.awt.Button();
        mainmenuicon = new javax.swing.JLabel();

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

        rdatetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rdatetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rdatetlabel.setText("02/04/2020");

        rtimelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtimelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtimelabel.setText("Time:");

        rtimetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtimetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtimetlabel.setText("10:10:10");

        rcnumberlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rcnumberlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rcnumberlabel.setText("Card Number:");

        rcnumbertlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rcnumbertlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rcnumbertlabel.setText("1111222233334444");

        rtransactionlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtransactionlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtransactionlabel.setText("Transaction:");

        rtransactiontlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtransactiontlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtransactiontlabel.setText("Withdrawal");

        rtypelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtypelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtypelabel.setText("Type:");

        rtypetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtypetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtypetlabel.setText("Dollar");

        ramountlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ramountlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ramountlabel.setText("Amount:");

        ramounttlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        ramounttlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ramounttlabel.setText("94");

        rboblabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rboblabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rboblabel2.setText("Bank of Business");

        rtotalbalancelabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtotalbalancelabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rtotalbalancelabel.setText("Total Balance:");

        rtotalbalancetlabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rtotalbalancetlabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        rtotalbalancetlabel.setText("$945.456");

        rhaveanicedaylabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        rhaveanicedaylabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        rhaveanicedaylabel.setText("HAVE A NICE DAY!");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(rbankicon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rboblabel))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rhaveanicedaylabel)
                            .addComponent(rboblabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rtotalbalancelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(rtotalbalancetlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rtimelabel)
                                .addGap(225, 225, 225)
                                .addComponent(rtimetlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdatelabel)
                                .addGap(208, 208, 208)
                                .addComponent(rdatetlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rcnumberlabel)
                                .addGap(95, 95, 95)
                                .addComponent(rcnumbertlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rtransactionlabel)
                                    .addComponent(ramountlabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rtypelabel, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rtransactiontlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rtypetlabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(ramounttlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(rbankicon))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(rboblabel)))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdatelabel)
                    .addComponent(rdatetlabel))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtimelabel)
                    .addComponent(rtimetlabel))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rcnumberlabel)
                    .addComponent(rcnumbertlabel))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtransactiontlabel)
                    .addComponent(rtransactionlabel))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtypelabel)
                    .addComponent(rtypetlabel))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ramountlabel)
                    .addComponent(ramounttlabel))
                .addGap(14, 14, 14)
                .addComponent(rboblabel2)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rtotalbalancelabel)
                    .addComponent(rtotalbalancetlabel))
                .addGap(6, 6, 6)
                .addComponent(rhaveanicedaylabel)
                .addGap(11, 11, 11))
        );

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
                            .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
        Data.setPage1(null);
        Data.setPage2(null);
        frame.ChangeJPanel("LoginCustomer");
    }//GEN-LAST:event_exitbtnActionPerformed

    private void mainmenubtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainmenubtnActionPerformed
        Data.setPage1(null);
        Data.setPage2(null);
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
