/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.customer;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;
import library.*;

/**
 *
 * @author ercan
 */
public class Receipt extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer;

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
            jPanel.removeAll();
            infoLabel.setText("System error and the transaction couldn't be created.");
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
                jPanel.removeAll();
                infoLabel.setText("System error and the transaction couldn't be created.");
                return;
            }
        }

        switch (Data.getPage2()) {
            case "DepositCustomer":
                infoLabel.setText("The money has been deposited to your account.");
                break;
            case "WithdrawalCustomer":
                infoLabel.setText("The money has been withdrawn from your account.");
                break;
            case "TransferMoneyCustomer":
                infoLabel.setText("The money has been transferred to the account.");
                break;
            default:
                break;
        }
        dateAmountLabel.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        timeAmountLabel.setText(new SimpleDateFormat("HH:mm:ss").format(date));
        cardNumberAmountLabel.setText(Data.getCustomer().getCardNumber());
        switch (Data.getPage2()) {
            case "DepositCustomer":
                transactionAmountLabel.setText("Deposit");
                break;
            case "WithdrawalCustomer":
                transactionAmountLabel.setText("Withdrawal");
                break;
            case "TransferMoneyCustomer":
                transactionAmountLabel.setText("Transfer - " + Data.getTransfer());
                break;
            default:
                break;
        }
        typeAmountLabel.setText(typename[Data.getMoneyType()]);
        amountAmountLabel.setText(Data.getMoney().toString());
        totalBalanceLabel.setText("Total " + typename[Data.getMoneyType()] + " Balance:");
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
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
                        break;
                    default:
                        break;
                }
                timer = new Timer(2000, (ActionEvent e) -> {
                    totalBalanceAmountLabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                });
                timer.setInitialDelay(0);
                timer.start();
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
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
                        break;
                    default:
                        break;
                }
                timer = new Timer(2000, (ActionEvent e) -> {
                    totalBalanceAmountLabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                });
                timer.setInitialDelay(0);
                timer.start();
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
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
                        break;
                    default:
                        break;
                }
                timer = new Timer(2000, (ActionEvent e) -> {
                    totalBalanceAmountLabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                });
                timer.setInitialDelay(0);
                timer.start();
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
                        Database.set("Accounts", ((Data.getTransfer().length() != 16) ? ("ID") : ("CardNumber")), Data.getTransfer(), "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date));
                        break;
                    default:
                        break;
                }
                timer = new Timer(2000, (ActionEvent e) -> {
                    totalBalanceAmountLabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                });
                timer.setInitialDelay(0);
                timer.start();
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

        mainLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();
        jPanel = new javax.swing.JPanel();
        bankIcon = new javax.swing.JLabel();
        bobLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        dateAmountLabel = new javax.swing.JLabel();
        timeLabel = new javax.swing.JLabel();
        timeAmountLabel = new javax.swing.JLabel();
        cardNumberLabel = new javax.swing.JLabel();
        cardNumberAmountLabel = new javax.swing.JLabel();
        transactionLabel = new javax.swing.JLabel();
        transactionAmountLabel = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        typeAmountLabel = new javax.swing.JLabel();
        amountLabel = new javax.swing.JLabel();
        amountAmountLabel = new javax.swing.JLabel();
        bobLabel2 = new javax.swing.JLabel();
        totalBalanceLabel = new javax.swing.JLabel();
        totalBalanceAmountLabel = new javax.swing.JLabel();
        haveANiceDayLabel = new javax.swing.JLabel();
        exitButton = new java.awt.Button();
        exitIcon = new javax.swing.JLabel();
        mainMenuButton = new java.awt.Button();
        mainMenuIcon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        mainLabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainLabel.setText("Information");
        mainLabel.setMaximumSize(new java.awt.Dimension(223, 47));
        mainLabel.setMinimumSize(new java.awt.Dimension(223, 47));
        mainLabel.setPreferredSize(new java.awt.Dimension(223, 47));

        infoLabel.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        infoLabel.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel.setText("The money has been withdrawn from your account.");

        jPanel.setMaximumSize(new java.awt.Dimension(364, 349));
        jPanel.setMinimumSize(new java.awt.Dimension(364, 349));
        jPanel.setRequestFocusEnabled(false);

        bankIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_bank_48px.png"))); // NOI18N

        bobLabel.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        bobLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bobLabel.setText("Bank of Business");

        dateLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dateLabel.setText("Date:");

        dateAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        dateAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateAmountLabel.setText("02/04/2020");

        timeLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        timeLabel.setText("Time:");

        timeAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        timeAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        timeAmountLabel.setText("10:10:10");

        cardNumberLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        cardNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cardNumberLabel.setText("Card Number:");

        cardNumberAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        cardNumberAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cardNumberAmountLabel.setText("1111222233334444");

        transactionLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        transactionLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        transactionLabel.setText("Transaction:");

        transactionAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        transactionAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        transactionAmountLabel.setText("Withdrawal");

        typeLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        typeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        typeLabel.setText("Type:");

        typeAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        typeAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        typeAmountLabel.setText("Dollar");

        amountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        amountLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        amountLabel.setText("Amount:");

        amountAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        amountAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        amountAmountLabel.setText("94");

        bobLabel2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        bobLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        bobLabel2.setText("Bank of Business");

        totalBalanceLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        totalBalanceLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totalBalanceLabel.setText("Total Balance:");

        totalBalanceAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        totalBalanceAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalBalanceAmountLabel.setText("$945.456");

        haveANiceDayLabel.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        haveANiceDayLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        haveANiceDayLabel.setText("HAVE A NICE DAY!");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(bankIcon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bobLabel))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(haveANiceDayLabel)
                            .addComponent(bobLabel2)
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(totalBalanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(totalBalanceAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(timeLabel)
                                .addGap(225, 225, 225)
                                .addComponent(timeAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(dateLabel)
                                .addGap(208, 208, 208)
                                .addComponent(dateAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addComponent(cardNumberLabel)
                                .addGap(95, 95, 95)
                                .addComponent(cardNumberAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLayout.createSequentialGroup()
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(transactionLabel)
                                    .addComponent(amountLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(typeLabel, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelLayout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(transactionAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(typeAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(amountAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(10, 10, 10))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(bankIcon))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(bobLabel)))
                .addGap(26, 26, 26)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(dateAmountLabel))
                .addGap(6, 6, 6)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeLabel)
                    .addComponent(timeAmountLabel))
                .addGap(14, 14, 14)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cardNumberLabel)
                    .addComponent(cardNumberAmountLabel))
                .addGap(6, 6, 6)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(transactionAmountLabel)
                    .addComponent(transactionLabel))
                .addGap(6, 6, 6)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeLabel)
                    .addComponent(typeAmountLabel))
                .addGap(6, 6, 6)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amountAmountLabel))
                .addGap(14, 14, 14)
                .addComponent(bobLabel2)
                .addGap(6, 6, 6)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(totalBalanceLabel)
                    .addComponent(totalBalanceAmountLabel))
                .addGap(6, 6, 6)
                .addComponent(haveANiceDayLabel)
                .addGap(11, 11, 11))
        );

        exitButton.setBackground(new java.awt.Color(23, 35, 51));
        exitButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setLabel("Exit");
        exitButton.setMinimumSize(new java.awt.Dimension(80, 49));
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        exitIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

        mainMenuButton.setBackground(new java.awt.Color(23, 35, 51));
        mainMenuButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        mainMenuButton.setForeground(new java.awt.Color(255, 255, 255));
        mainMenuButton.setLabel("Main Menu");
        mainMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mainMenuButtonActionPerformed(evt);
            }
        });

        mainMenuIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

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
                                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(exitIcon)
                                .addGap(87, 87, 87)
                                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(mainMenuIcon)
                                .addGap(10, 10, 10)
                                .addComponent(mainMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 572, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(exitIcon)
                        .addComponent(mainMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mainMenuIcon))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        Data.setCustomer(null);
        Data.setPage1(null);
        Data.setPage2(null);
        if (timer != null) {
            timer.stop();
        }
        frame.changeJPanel("LoginCustomer");
    }//GEN-LAST:event_exitButtonActionPerformed

    private void mainMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mainMenuButtonActionPerformed
        Data.setPage1(null);
        Data.setPage2(null);
        if (timer != null) {
            timer.stop();
        }
        frame.changeJPanel("HomeCustomer");
    }//GEN-LAST:event_mainMenuButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel amountAmountLabel;
    private javax.swing.JLabel amountLabel;
    private javax.swing.JLabel bankIcon;
    private javax.swing.JLabel bobLabel;
    private javax.swing.JLabel bobLabel2;
    private javax.swing.JLabel cardNumberAmountLabel;
    private javax.swing.JLabel cardNumberLabel;
    private javax.swing.JLabel dateAmountLabel;
    private javax.swing.JLabel dateLabel;
    private java.awt.Button exitButton;
    private javax.swing.JLabel exitIcon;
    private javax.swing.JLabel haveANiceDayLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JPanel jPanel;
    private javax.swing.JLabel mainLabel;
    private java.awt.Button mainMenuButton;
    private javax.swing.JLabel mainMenuIcon;
    private javax.swing.JLabel timeAmountLabel;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JLabel totalBalanceAmountLabel;
    private javax.swing.JLabel totalBalanceLabel;
    private javax.swing.JLabel transactionAmountLabel;
    private javax.swing.JLabel transactionLabel;
    private javax.swing.JLabel typeAmountLabel;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
