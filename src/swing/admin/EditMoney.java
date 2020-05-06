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

        ((AbstractDocument) searchtext.getDocument()).setDocumentFilter(new Filter(1, 16));
        ((AbstractDocument) dollartext.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) eurotext.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) poundtext.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) turkishliratext.getDocument()).setDocumentFilter(new Filter(0, 15));

        visible(false);
    }

    public final void visible(boolean option) {
        infolabel3.setVisible(option);
        dollartext.setVisible(option);
        infolabel4.setVisible(option);
        eurotext.setVisible(option);
        infolabel5.setVisible(option);
        poundtext.setVisible(option);
        infolabel6.setVisible(option);
        turkishliratext.setVisible(option);
    }

    public void search() {
        String[] typename = {"ID", "CardNumber", "FullName"};
        if (timer != null) {
            timer.stop();
        }
        if (!searchtext.getText().isBlank()) {
            if (Database.exists("Accounts", typename[searchtype.getSelectedIndex()], searchtext.getText())) {
                if (Database.getInt("Accounts", typename[searchtype.getSelectedIndex()], searchtext.getText(), "AccountType") == 0) {
                    text = searchtext.getText();
                    type = searchtype.getSelectedIndex();
                    timer = new Timer(2000, new ActionListener() {
                        String[] load = null;
                        String[] data = null;

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            data = Database.getArray("Accounts", typename[type], text);
                            if (load == null || (data != null && !Arrays.equals(load, data))) {
                                load = data.clone();
                                dollartext.setText(data[7]);
                                eurotext.setText(data[8]);
                                poundtext.setText(data[9]);
                                turkishliratext.setText(data[10]);
                            }
                        }
                    });
                    timer.setInitialDelay(0);
                    timer.start();
                    visible(true);
                    searchtext.setText(null);
                    searchtype.setSelectedIndex(0);
                    infolabel.setText("The customer was successfully found.");
                } else {
                    visible(false);
                    infolabel.setText("The account type is invalid.");
                }
            } else {
                visible(false);
                infolabel.setText("The customer is invalid.");
            }
        } else {
            visible(false);
            infolabel.setText("The field can't be left blank.");
        }
    }

    public void clear() {
        searchtext.setText(null);
        searchtype.setSelectedIndex(0);
        if (timer != null) {
            timer.stop();
        }
        visible(false);
        infolabel.setText("The search was successfully cleared.");
    }

    public void editMoney() {
        String[] typename = {"ID", "CardNumber", "FullName"};
        if (!dollartext.getText().isBlank() && !eurotext.getText().isBlank() && !poundtext.getText().isBlank() && !turkishliratext.getText().isBlank()) {
            BigDecimal dollar = Database.isBigDecimal(dollartext.getText());
            BigDecimal euro = Database.isBigDecimal(eurotext.getText());
            BigDecimal pound = Database.isBigDecimal(poundtext.getText());
            BigDecimal turkishlira = Database.isBigDecimal(turkishliratext.getText());
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
                                        dollartext.setText(null);
                                        eurotext.setText(null);
                                        poundtext.setText(null);
                                        turkishliratext.setText(null);
                                        visible(false);
                                        infolabel.setText("The customer money was successfully edited. [ID: " + ID + "]");
                                    } else {
                                        if (timer != null) {
                                            timer.stop();
                                        }
                                        visible(false);
                                        infolabel.setText("System error and the customer couldn't be edited.");
                                    }
                                } else {
                                    if (timer != null) {
                                        timer.stop();
                                    }
                                    visible(false);
                                    infolabel.setText("The account type is invalid.");
                                }
                            } else {
                                if (timer != null) {
                                    timer.stop();
                                }
                                visible(false);
                                infolabel.setText("The customer is invalid.");
                            }
                        } else {
                            infolabel.setText("The turkish lira doesn't follow the rules.");
                        }
                    } else {
                        infolabel.setText("The pound doesn't follow the rules.");
                    }
                } else {
                    infolabel.setText("The euro doesn't follow the rules.");
                }
            } else {
                infolabel.setText("The dollar doesn't follow the rules.");
            }
        } else {
            infolabel.setText("The field can't be left blank.");
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
        infolabel2 = new javax.swing.JLabel();
        searchtext = new javax.swing.JTextField();
        searchtype = new javax.swing.JComboBox<>();
        okbtn = new java.awt.Button();
        clearbtn = new java.awt.Button();
        infolabel3 = new javax.swing.JLabel();
        dollartext = new javax.swing.JTextField();
        infolabel4 = new javax.swing.JLabel();
        eurotext = new javax.swing.JTextField();
        infolabel5 = new javax.swing.JLabel();
        poundtext = new javax.swing.JTextField();
        infolabel6 = new javax.swing.JLabel();
        turkishliratext = new javax.swing.JTextField();
        editbtn = new java.awt.Button();
        editicon = new javax.swing.JLabel();
        cancelbtn = new java.awt.Button();
        cancelicon = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        mainlabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainlabel.setForeground(new java.awt.Color(255, 255, 255));
        mainlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainlabel.setText("Edit Money");
        mainlabel.setMaximumSize(new java.awt.Dimension(223, 47));
        mainlabel.setMinimumSize(new java.awt.Dimension(223, 47));
        mainlabel.setPreferredSize(new java.awt.Dimension(223, 47));

        infolabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infolabel.setForeground(new java.awt.Color(255, 255, 255));
        infolabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        infolabel2.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel2.setForeground(new java.awt.Color(255, 255, 255));
        infolabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel2.setText("Search:");

        searchtext.setBackground(new java.awt.Color(23, 35, 51));
        searchtext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        searchtext.setForeground(new java.awt.Color(255, 255, 255));
        searchtext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        searchtext.setMaximumSize(new java.awt.Dimension(7, 39));
        searchtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchtextActionPerformed(evt);
            }
        });

        searchtype.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        searchtype.setForeground(new java.awt.Color(23, 35, 51));
        searchtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Card Number", "Full Name" }));
        searchtype.setToolTipText("");
        searchtype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                searchtypeİtemStateChanged(evt);
            }
        });

        okbtn.setBackground(new java.awt.Color(23, 35, 51));
        okbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        okbtn.setForeground(new java.awt.Color(255, 255, 255));
        okbtn.setLabel("OK");
        okbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okbtnActionPerformed(evt);
            }
        });

        clearbtn.setBackground(new java.awt.Color(23, 35, 51));
        clearbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        clearbtn.setForeground(new java.awt.Color(255, 255, 255));
        clearbtn.setLabel("Clear");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });

        infolabel3.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel3.setForeground(new java.awt.Color(255, 255, 255));
        infolabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel3.setText("Dollar");

        dollartext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        dollartext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dollartext.setMaximumSize(new java.awt.Dimension(7, 39));
        dollartext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dollartextActionPerformed(evt);
            }
        });
        dollartext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dollartextKeyPressed(evt);
            }
        });

        infolabel4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel4.setForeground(new java.awt.Color(255, 255, 255));
        infolabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel4.setText("Euro");

        eurotext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        eurotext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eurotext.setMaximumSize(new java.awt.Dimension(7, 39));
        eurotext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eurotextActionPerformed(evt);
            }
        });
        eurotext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                eurotextKeyPressed(evt);
            }
        });

        infolabel5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel5.setForeground(new java.awt.Color(255, 255, 255));
        infolabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel5.setText("Pound");

        poundtext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        poundtext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        poundtext.setMaximumSize(new java.awt.Dimension(7, 39));
        poundtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poundtextActionPerformed(evt);
            }
        });
        poundtext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                poundtextKeyPressed(evt);
            }
        });

        infolabel6.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel6.setForeground(new java.awt.Color(255, 255, 255));
        infolabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel6.setText("Turkish Lira");

        turkishliratext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        turkishliratext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        turkishliratext.setMaximumSize(new java.awt.Dimension(7, 39));
        turkishliratext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                turkishliratextActionPerformed(evt);
            }
        });
        turkishliratext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                turkishliratextKeyPressed(evt);
            }
        });

        editbtn.setBackground(new java.awt.Color(23, 35, 51));
        editbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        editbtn.setForeground(new java.awt.Color(255, 255, 255));
        editbtn.setLabel("Edit");
        editbtn.setMinimumSize(new java.awt.Dimension(80, 49));
        editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbtnActionPerformed(evt);
            }
        });

        editicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_change_48px_1.png"))); // NOI18N

        cancelbtn.setBackground(new java.awt.Color(23, 35, 51));
        cancelbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        cancelbtn.setForeground(new java.awt.Color(255, 255, 255));
        cancelbtn.setLabel("Cancel");
        cancelbtn.setMinimumSize(new java.awt.Dimension(80, 49));
        cancelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelbtnActionPerformed(evt);
            }
        });

        cancelicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(editicon)
                                .addGap(538, 538, 538)
                                .addComponent(cancelicon)
                                .addGap(10, 10, 10)
                                .addComponent(cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(390, 390, 390)
                        .addComponent(turkishliratext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(infolabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(infolabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(infolabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(infolabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(dollartext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(eurotext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(poundtext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infolabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(searchtext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(searchtype, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(okbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchtext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infolabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(searchtype, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(okbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infolabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infolabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infolabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(poundtext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(eurotext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dollartext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(infolabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(turkishliratext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(editicon)))
                    .addComponent(cancelicon))
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        editMoney();
    }//GEN-LAST:event_editbtnActionPerformed

    private void dollartextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dollartextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dollartextActionPerformed

    private void cancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelbtnActionPerformed
        if (timer != null) {
            timer.stop();
        }
        frame.ChangeJPanel("HomeAdmin");
    }//GEN-LAST:event_cancelbtnActionPerformed

    private void eurotextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eurotextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eurotextActionPerformed

    private void turkishliratextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_turkishliratextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_turkishliratextActionPerformed

    private void poundtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poundtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_poundtextActionPerformed

    private void searchtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtextActionPerformed

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
        search();
    }//GEN-LAST:event_okbtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void dollartextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dollartextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_dollartextKeyPressed

    private void eurotextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eurotextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_eurotextKeyPressed

    private void poundtextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_poundtextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_poundtextKeyPressed

    private void turkishliratextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_turkishliratextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editMoney();
        }
    }//GEN-LAST:event_turkishliratextKeyPressed

    private void searchtypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchtypeİtemStateChanged
        switch (evt.getStateChange()) {
            case 0:
                ((AbstractDocument) searchtext.getDocument()).setDocumentFilter(new Filter(1, 16));
                searchtext.setText(null);
                break;
            case 1:
                ((AbstractDocument) searchtext.getDocument()).setDocumentFilter(new Filter(1, 16));
                searchtext.setText(null);
                break;
            case 2:
                ((AbstractDocument) searchtext.getDocument()).setDocumentFilter(new Filter(0, 32));
                searchtext.setText(null);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_searchtypeİtemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button cancelbtn;
    private javax.swing.JLabel cancelicon;
    private java.awt.Button clearbtn;
    private javax.swing.JTextField dollartext;
    private java.awt.Button editbtn;
    private javax.swing.JLabel editicon;
    private javax.swing.JTextField eurotext;
    private javax.swing.JLabel infolabel;
    private javax.swing.JLabel infolabel2;
    private javax.swing.JLabel infolabel3;
    private javax.swing.JLabel infolabel4;
    private javax.swing.JLabel infolabel5;
    private javax.swing.JLabel infolabel6;
    private javax.swing.JLabel mainlabel;
    private java.awt.Button okbtn;
    private javax.swing.JTextField poundtext;
    private javax.swing.JTextField searchtext;
    private javax.swing.JComboBox<String> searchtype;
    private javax.swing.JTextField turkishliratext;
    // End of variables declaration//GEN-END:variables
}
