/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.banker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
import library.*;

/**
 *
 * @author ercan
 */
public class Balance extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer, timer2;
    String text;
    int type;

    /**
     * Creates new form Balance
     *
     * @param home Dynamic panel.
     */
    public Balance(swing.Home home) {
        initComponents();
        frame = home;

        ((AbstractDocument) searchText.getDocument()).setDocumentFilter(new Filter(1, 16));

        timer = new Timer(2000, new ActionListener() {
            int type = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (type) {
                    case 0: {
                        String[] load = Database.getArray("Currencies", "Rate", "Dollar");
                        typeLabel.setText("Dollar");
                        firstLabel.setText("Euro:");
                        secondLabel.setText("Pound:");
                        thirdLabel.setText("Turkish Lira:");
                        firstAmountLabel.setText(Data.currencyFormat(1, Database.isBigDecimal(load[2])));
                        secondAmountLabel.setText(Data.currencyFormat(2, Database.isBigDecimal(load[3])));
                        thirdAmountLabel.setText(Data.currencyFormat(3, Database.isBigDecimal(load[4])));
                        type++;
                        break;
                    }
                    case 1: {
                        String[] load = Database.getArray("Currencies", "Rate", "Euro");
                        typeLabel.setText("Euro");
                        firstLabel.setText("Dollar:");
                        secondLabel.setText("Pound:");
                        thirdLabel.setText("Turkish Lira:");
                        firstAmountLabel.setText(Data.currencyFormat(0, Database.isBigDecimal(load[1])));
                        secondAmountLabel.setText(Data.currencyFormat(2, Database.isBigDecimal(load[3])));
                        thirdAmountLabel.setText(Data.currencyFormat(3, Database.isBigDecimal(load[4])));
                        type++;
                        break;
                    }
                    case 2: {
                        String[] load = Database.getArray("Currencies", "Rate", "Pound");
                        typeLabel.setText("Pound");
                        firstLabel.setText("Dollar:");
                        secondLabel.setText("Euro:");
                        thirdLabel.setText("Turkish Lira:");
                        firstAmountLabel.setText(Data.currencyFormat(0, Database.isBigDecimal(load[1])));
                        secondAmountLabel.setText(Data.currencyFormat(1, Database.isBigDecimal(load[2])));
                        thirdAmountLabel.setText(Data.currencyFormat(3, Database.isBigDecimal(load[4])));
                        type++;
                        break;
                    }
                    case 3: {
                        String[] load = Database.getArray("Currencies", "Rate", "Turkish Lira");
                        typeLabel.setText("Turkish Lira");
                        firstLabel.setText("Dollar:");
                        secondLabel.setText("Euro:");
                        thirdLabel.setText("Pound:");
                        firstAmountLabel.setText(Data.currencyFormat(0, Database.isBigDecimal(load[1])));
                        secondAmountLabel.setText(Data.currencyFormat(1, Database.isBigDecimal(load[2])));
                        thirdAmountLabel.setText(Data.currencyFormat(2, Database.isBigDecimal(load[3])));
                        type = 0;
                        break;
                    }
                    default:
                        break;
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
        jPanel2.setVisible(false);
    }

    private void balance() {
        String[] typename = {"ID", "CardNumber", "FullName"};
        if (!searchText.getText().isBlank()) {
            if (Database.exists("Accounts", typename[searchType.getSelectedIndex()], searchText.getText())) {
                if (Database.getInt("Accounts", typename[searchType.getSelectedIndex()], searchText.getText(), "AccountType") == 0) {
                    text = searchText.getText();
                    type = searchType.getSelectedIndex();
                    timer2 = new Timer(2000, (ActionEvent e) -> {
                        dolarAmountLabel.setText(Data.currencyFormat(0, Database.getBigDecimal("Accounts", typename[type], text, "Dollar")));
                        euroAmountLabel.setText(Data.currencyFormat(1, Database.getBigDecimal("Accounts", typename[type], text, "Euro")));
                        poundAmountLabel.setText(Data.currencyFormat(2, Database.getBigDecimal("Accounts", typename[type], text, "Pound")));
                        turkishLiraAmountLabel.setText(Data.currencyFormat(3, Database.getBigDecimal("Accounts", typename[type], text, "TurkishLira")));
                    });
                    timer2.setInitialDelay(0);
                    timer2.start();
                    jPanel2.setVisible(true);
                    searchText.setText(null);
                    searchType.setSelectedIndex(0);
                    infoLabel.setText("The customer was successfully found.");
                } else {
                    infoLabel.setText("The account type is invalid.");
                }
            } else {
                infoLabel.setText("The customer is invalid.");
            }
        } else {
            infoLabel.setText("The field can't be left blank.");
        }
    }

    private void clear() {
        searchText.setText(null);
        searchType.setSelectedIndex(0);
        text = null;
        type = 0;
        if (timer2 != null) {
            timer2.stop();
        }
        jPanel2.setVisible(false);
        infoLabel.setText("The search was successfully cleared.");
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
        infoLabel2 = new javax.swing.JLabel();
        searchText = new javax.swing.JTextField();
        searchType = new javax.swing.JComboBox<>();
        okButton = new java.awt.Button();
        clearButton = new java.awt.Button();
        jPanel = new javax.swing.JPanel();
        typeLabel = new javax.swing.JLabel();
        firstLabel = new javax.swing.JLabel();
        firstAmountLabel = new javax.swing.JLabel();
        secondLabel = new javax.swing.JLabel();
        secondAmountLabel = new javax.swing.JLabel();
        thirdLabel = new javax.swing.JLabel();
        thirdAmountLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        dolarLabel = new javax.swing.JLabel();
        dolarAmountLabel = new javax.swing.JLabel();
        euroLabel = new javax.swing.JLabel();
        euroAmountLabel = new javax.swing.JLabel();
        poundLabel = new javax.swing.JLabel();
        poundAmountLabel = new javax.swing.JLabel();
        turkishLiraLabel = new javax.swing.JLabel();
        turkishLiraAmountLabel = new javax.swing.JLabel();
        cancelButton = new java.awt.Button();
        cancelIcon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        mainLabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainLabel.setText("Balance");
        mainLabel.setMaximumSize(new java.awt.Dimension(223, 47));
        mainLabel.setMinimumSize(new java.awt.Dimension(223, 47));
        mainLabel.setPreferredSize(new java.awt.Dimension(223, 47));

        infoLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infoLabel.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        infoLabel2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel2.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel2.setText("Search");

        searchText.setBackground(new java.awt.Color(23, 35, 51));
        searchText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        searchText.setForeground(new java.awt.Color(255, 255, 255));
        searchText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchText.setMaximumSize(new java.awt.Dimension(7, 39));
        searchText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextActionPerformed(evt);
            }
        });
        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchTextKeyPressed(evt);
            }
        });

        searchType.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        searchType.setForeground(new java.awt.Color(23, 35, 51));
        searchType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Card Number", "Full Name" }));
        searchType.setToolTipText("");
        searchType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchTypeİtemStateChanged(evt);
            }
        });

        okButton.setBackground(new java.awt.Color(23, 35, 51));
        okButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        okButton.setForeground(new java.awt.Color(255, 255, 255));
        okButton.setLabel("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(23, 35, 51));
        clearButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        clearButton.setForeground(new java.awt.Color(255, 255, 255));
        clearButton.setLabel("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jPanel.setMaximumSize(new java.awt.Dimension(296, 179));
        jPanel.setMinimumSize(new java.awt.Dimension(296, 179));

        typeLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        typeLabel.setForeground(new java.awt.Color(133, 187, 101));
        typeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typeLabel.setText("Dollar");

        firstLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        firstLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        firstLabel.setText("Euro:");

        firstAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        firstAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        firstAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstAmountLabel.setText("1.21 €");

        secondLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        secondLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        secondLabel.setText("Pound:");

        secondAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        secondAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        secondAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        secondAmountLabel.setText("£1.14");

        thirdLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        thirdLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        thirdLabel.setText("Turkish Lira:");

        thirdAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        thirdAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        thirdAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        thirdAmountLabel.setText("₺5.42");

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(firstLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secondLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thirdLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addGap(6, 6, 6)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(secondAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thirdAmountLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(typeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstLabel)
                    .addComponent(firstAmountLabel))
                .addGap(11, 11, 11)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondLabel)
                    .addComponent(secondAmountLabel))
                .addGap(11, 11, 11)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thirdLabel)
                    .addComponent(thirdAmountLabel))
                .addGap(11, 11, 11))
        );

        jPanel2.setMaximumSize(new java.awt.Dimension(296, 179));
        jPanel2.setMinimumSize(new java.awt.Dimension(296, 179));

        dolarLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        dolarLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dolarLabel.setText("Dollar:");

        dolarAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        dolarAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        dolarAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dolarAmountLabel.setText("$500");

        euroLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        euroLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        euroLabel.setText("Euro:");

        euroAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        euroAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        euroAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        euroAmountLabel.setText("400 €");

        poundLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        poundLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        poundLabel.setText("Pound:");

        poundAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        poundAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        poundAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        poundAmountLabel.setText("£200");

        turkishLiraLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        turkishLiraLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turkishLiraLabel.setText("Turkish Lira:");

        turkishLiraAmountLabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        turkishLiraAmountLabel.setForeground(new java.awt.Color(133, 187, 101));
        turkishLiraAmountLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        turkishLiraAmountLabel.setText("₺500");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(dolarLabel)
                        .addGap(6, 6, 6)
                        .addComponent(dolarAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(turkishLiraLabel)
                            .addGap(6, 6, 6)
                            .addComponent(turkishLiraAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(poundLabel)
                            .addGap(6, 6, 6)
                            .addComponent(poundAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(euroLabel)
                            .addGap(6, 6, 6)
                            .addComponent(euroAmountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dolarLabel)
                    .addComponent(dolarAmountLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(euroLabel)
                    .addComponent(euroAmountLabel))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(poundLabel)
                    .addComponent(poundAmountLabel))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turkishLiraLabel)
                    .addComponent(turkishLiraAmountLabel))
                .addGap(11, 11, 11))
        );

        cancelButton.setBackground(new java.awt.Color(23, 35, 51));
        cancelButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setLabel("Cancel");
        cancelButton.setMinimumSize(new java.awt.Dimension(80, 49));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        cancelIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cancelIcon))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(infoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(126, 126, 126)
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(searchType, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(13, 13, 13)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(300, 300, 300))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchType, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(117, 117, 117)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelIcon))
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (timer != null) {
            timer.stop();
        }
        if (timer2 != null) {
            timer2.stop();
        }
        frame.changeJPanel("HomeBanker");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        balance();
    }//GEN-LAST:event_okButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextActionPerformed

    private void searchTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            balance();
        }
    }//GEN-LAST:event_searchTextKeyPressed

    private void searchTypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchTypeİtemStateChanged
        switch (evt.getStateChange()) {
            case 0:
                ((AbstractDocument) searchText.getDocument()).setDocumentFilter(new Filter(1, 16));
                searchText.setText(null);
                break;
            case 1:
                ((AbstractDocument) searchText.getDocument()).setDocumentFilter(new Filter(1, 16));
                searchText.setText(null);
                break;
            case 2:
                ((AbstractDocument) searchText.getDocument()).setDocumentFilter(new Filter(0, 32));
                searchText.setText(null);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_searchTypeİtemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button cancelButton;
    private javax.swing.JLabel cancelIcon;
    private java.awt.Button clearButton;
    private javax.swing.JLabel dolarAmountLabel;
    private javax.swing.JLabel dolarLabel;
    private javax.swing.JLabel euroAmountLabel;
    private javax.swing.JLabel euroLabel;
    private javax.swing.JLabel firstAmountLabel;
    private javax.swing.JLabel firstLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel infoLabel2;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel mainLabel;
    private java.awt.Button okButton;
    private javax.swing.JLabel poundAmountLabel;
    private javax.swing.JLabel poundLabel;
    private javax.swing.JTextField searchText;
    private javax.swing.JComboBox<String> searchType;
    private javax.swing.JLabel secondAmountLabel;
    private javax.swing.JLabel secondLabel;
    private javax.swing.JLabel thirdAmountLabel;
    private javax.swing.JLabel thirdLabel;
    private javax.swing.JLabel turkishLiraAmountLabel;
    private javax.swing.JLabel turkishLiraLabel;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
