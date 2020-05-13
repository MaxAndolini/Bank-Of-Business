/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
import library.*;

/**
 *
 * @author ercan
 */
public class EditMoney extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer;
    String text;
    int type;

    /**
     * Creates new form EditMoney
     *
     * @param home Dynamic panel.
     */
    public EditMoney(swing.Home home) {
        initComponents();
        frame = home;

        ((AbstractDocument) searchText.getDocument()).setDocumentFilter(new Filter(1, 16));
        ((AbstractDocument) dollarText.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) euroText.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) poundText.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) turkishLiraText.getDocument()).setDocumentFilter(new Filter(0, 15));

        visible(false);
    }

    public final void visible(boolean option) {
        infoLabel3.setVisible(option);
        dollarText.setVisible(option);
        infoLabel4.setVisible(option);
        euroText.setVisible(option);
        infoLabel5.setVisible(option);
        poundText.setVisible(option);
        infoLabel6.setVisible(option);
        turkishLiraText.setVisible(option);
    }

    public void search() {
        String[] typename = {"ID", "CardNumber", "FullName"};
        if (timer != null) {
            timer.stop();
        }
        if (!searchText.getText().isBlank()) {
            if (Database.exists("Accounts", typename[searchType.getSelectedIndex()], searchText.getText())) {
                if (Database.getInt("Accounts", typename[searchType.getSelectedIndex()], searchText.getText(), "AccountType") == 0) {
                    text = searchText.getText();
                    type = searchType.getSelectedIndex();
                    timer = new Timer(2000, new ActionListener() {
                        String[] load = null;
                        String[] data = null;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            data = Database.getArray("Accounts", typename[type], text);
                            if (load == null || (data != null && !Arrays.equals(load, data))) {
                                load = data.clone();
                                dollarText.setText(data[7]);
                                euroText.setText(data[8]);
                                poundText.setText(data[9]);
                                turkishLiraText.setText(data[10]);
                            }
                        }
                    });
                    timer.setInitialDelay(0);
                    timer.start();
                    visible(true);
                    searchText.setText(null);
                    searchType.setSelectedIndex(0);
                    infoLabel.setText("The customer was successfully found.");
                } else {
                    visible(false);
                    infoLabel.setText("The account type is invalid.");
                }
            } else {
                visible(false);
                infoLabel.setText("The customer is invalid.");
            }
        } else {
            visible(false);
            infoLabel.setText("The field can't be left blank.");
        }
    }

    public void clear() {
        searchText.setText(null);
        searchType.setSelectedIndex(0);
        if (timer != null) {
            timer.stop();
        }
        visible(false);
        infoLabel.setText("The search was successfully cleared.");
    }

    public void editMoney() {
        String[] typename = {"ID", "CardNumber", "FullName"};
        if (!dollarText.getText().isBlank() && !euroText.getText().isBlank() && !poundText.getText().isBlank() && !turkishLiraText.getText().isBlank()) {
            BigDecimal dollar = Database.isBigDecimal(dollarText.getText());
            BigDecimal euro = Database.isBigDecimal(euroText.getText());
            BigDecimal pound = Database.isBigDecimal(poundText.getText());
            BigDecimal turkishlira = Database.isBigDecimal(turkishLiraText.getText());
            if (dollar.compareTo(BigDecimal.ZERO) >= 0 && dollar.compareTo(new BigDecimal("-1")) != 0) {
                if (euro.compareTo(BigDecimal.ZERO) >= 0 && euro.compareTo(new BigDecimal("-1")) != 0) {
                    if (pound.compareTo(BigDecimal.ZERO) >= 0 && pound.compareTo(new BigDecimal("-1")) != 0) {
                        if (turkishlira.compareTo(BigDecimal.ZERO) >= 0 && turkishlira.compareTo(new BigDecimal("-1")) != 0) {
                            if (Database.exists("Accounts", typename[type], text)) {
                                if (Database.getInt("Accounts", typename[type], text, "AccountType") == 0) {
                                    String ID;
                                    if (type == 0) {
                                        ID = text;
                                    } else {
                                        ID = Database.getString("Accounts", typename[type], text, "ID");
                                    }
                                    if (ID != null) {
                                        if (timer != null) {
                                            timer.stop();
                                        }
                                        Database.set("Accounts", "ID", ID, "Dollar", dollar);
                                        Database.set("Accounts", "ID", ID, "Euro", euro);
                                        Database.set("Accounts", "ID", ID, "Pound", pound);
                                        Database.set("Accounts", "ID", ID, "TurkishLira", turkishlira);
                                        Database.set("Accounts", "ID", ID, "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                                        dollarText.setText(null);
                                        euroText.setText(null);
                                        poundText.setText(null);
                                        turkishLiraText.setText(null);
                                        visible(false);
                                        infoLabel.setText("The customer money was successfully edited. [ID: " + ID + "]");
                                    } else {
                                        if (timer != null) {
                                            timer.stop();
                                        }
                                        visible(false);
                                        infoLabel.setText("System error and the customer couldn't be edited.");
                                    }
                                } else {
                                    if (timer != null) {
                                        timer.stop();
                                    }
                                    visible(false);
                                    infoLabel.setText("The account type is invalid.");
                                }
                            } else {
                                if (timer != null) {
                                    timer.stop();
                                }
                                visible(false);
                                infoLabel.setText("The customer is invalid.");
                            }
                        } else {
                            infoLabel.setText("The turkish lira doesn't follow the rules.");
                        }
                    } else {
                        infoLabel.setText("The pound doesn't follow the rules.");
                    }
                } else {
                    infoLabel.setText("The euro doesn't follow the rules.");
                }
            } else {
                infoLabel.setText("The dollar doesn't follow the rules.");
            }
        } else {
            infoLabel.setText("The field can't be left blank.");
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
        infoLabel2 = new javax.swing.JLabel();
        searchText = new javax.swing.JTextField();
        searchType = new javax.swing.JComboBox<>();
        okButton = new java.awt.Button();
        clearButton = new java.awt.Button();
        infoLabel3 = new javax.swing.JLabel();
        dollarText = new javax.swing.JTextField();
        infoLabel4 = new javax.swing.JLabel();
        euroText = new javax.swing.JTextField();
        infoLabel5 = new javax.swing.JLabel();
        poundText = new javax.swing.JTextField();
        infoLabel6 = new javax.swing.JLabel();
        turkishLiraText = new javax.swing.JTextField();
        editButton = new java.awt.Button();
        editIcon = new javax.swing.JLabel();
        cancelButton = new java.awt.Button();
        cancelIcon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        mainLabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainLabel.setForeground(new java.awt.Color(255, 255, 255));
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainLabel.setText("Edit Money");
        mainLabel.setMaximumSize(new java.awt.Dimension(223, 47));
        mainLabel.setMinimumSize(new java.awt.Dimension(223, 47));
        mainLabel.setPreferredSize(new java.awt.Dimension(223, 47));

        infoLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infoLabel.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        infoLabel2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel2.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel2.setText("Search:");

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

        infoLabel3.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel3.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel3.setText("Dollar");

        dollarText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        dollarText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dollarText.setMaximumSize(new java.awt.Dimension(7, 39));
        dollarText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dollarTextActionPerformed(evt);
            }
        });
        dollarText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dollarTextKeyPressed(evt);
            }
        });

        infoLabel4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel4.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel4.setText("Euro");

        euroText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        euroText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        euroText.setMaximumSize(new java.awt.Dimension(7, 39));
        euroText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                euroTextActionPerformed(evt);
            }
        });
        euroText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                euroTextKeyPressed(evt);
            }
        });

        infoLabel5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel5.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel5.setText("Pound");

        poundText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        poundText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        poundText.setMaximumSize(new java.awt.Dimension(7, 39));
        poundText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poundTextActionPerformed(evt);
            }
        });
        poundText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                poundTextKeyPressed(evt);
            }
        });

        infoLabel6.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel6.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel6.setText("Turkish Lira");

        turkishLiraText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        turkishLiraText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        turkishLiraText.setMaximumSize(new java.awt.Dimension(7, 39));
        turkishLiraText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turkishLiraTextActionPerformed(evt);
            }
        });
        turkishLiraText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                turkishLiraTextKeyPressed(evt);
            }
        });

        editButton.setBackground(new java.awt.Color(23, 35, 51));
        editButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setLabel("Edit");
        editButton.setMinimumSize(new java.awt.Dimension(80, 49));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        editIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_change_48px.png"))); // NOI18N

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(editIcon)
                                .addGap(538, 538, 538)
                                .addComponent(cancelIcon)
                                .addGap(10, 10, 10)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(turkishLiraText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(infoLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(infoLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(infoLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(infoLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(dollarText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(euroText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(poundText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(searchType, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchType, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infoLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(poundText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(euroText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dollarText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(infoLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(turkishLiraText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editIcon)))
                    .addComponent(cancelIcon))
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editMoney();
    }//GEN-LAST:event_editButtonActionPerformed

    private void dollarTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dollarTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dollarTextActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (timer != null) {
            timer.stop();
        }
        frame.ChangeJPanel("HomeAdmin");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void euroTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_euroTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_euroTextActionPerformed

    private void turkishLiraTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turkishLiraTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_turkishLiraTextActionPerformed

    private void poundTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poundTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_poundTextActionPerformed

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        search();
    }//GEN-LAST:event_okButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void dollarTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dollarTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_dollarTextKeyPressed

    private void euroTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_euroTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_euroTextKeyPressed

    private void poundTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_poundTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_poundTextKeyPressed

    private void turkishLiraTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_turkishLiraTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_turkishLiraTextKeyPressed

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
    private javax.swing.JTextField dollarText;
    private java.awt.Button editButton;
    private javax.swing.JLabel editIcon;
    private javax.swing.JTextField euroText;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel infoLabel2;
    private javax.swing.JLabel infoLabel3;
    private javax.swing.JLabel infoLabel4;
    private javax.swing.JLabel infoLabel5;
    private javax.swing.JLabel infoLabel6;
    private javax.swing.JLabel mainLabel;
    private java.awt.Button okButton;
    private javax.swing.JTextField poundText;
    private javax.swing.JTextField searchText;
    private javax.swing.JComboBox<String> searchType;
    private javax.swing.JTextField turkishLiraText;
    // End of variables declaration//GEN-END:variables
}
