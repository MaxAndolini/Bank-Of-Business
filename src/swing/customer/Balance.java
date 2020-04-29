/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.customer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import library.*;

/**
 *
 * @author ercan
 */
public class Balance extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer;

    /**
     * Creates new form Balance
     *
     * @param home Dynamic panel.
     */
    public Balance(swing.Home home) {
        initComponents();
        frame = home;

        ((AbstractDocument) moneytext.getDocument()).setDocumentFilter(new Filter(1, 5));

        timer = new Timer(2000, new ActionListener() {
            int type = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (type) {
                    case 0: {
                        String[] load = Database.getArray("Currencies", "Rate", "Dollar");
                        typelabel.setText("Dollar");
                        firstlabel.setText("Euro:");
                        secondlabel.setText("Pound:");
                        thirdlabel.setText("Turkish Lira:");
                        firstalabel.setText(Data.currencyFormat(1, Database.isBigDecimal(load[2])));
                        secondalabel.setText(Data.currencyFormat(2, Database.isBigDecimal(load[3])));
                        thirdalabel.setText(Data.currencyFormat(3, Database.isBigDecimal(load[4])));
                        type++;
                        break;
                    }
                    case 1: {
                        String[] load = Database.getArray("Currencies", "Rate", "Euro");
                        typelabel.setText("Euro");
                        firstlabel.setText("Dollar:");
                        secondlabel.setText("Pound:");
                        thirdlabel.setText("Turkish Lira:");
                        firstalabel.setText(Data.currencyFormat(0, Database.isBigDecimal(load[1])));
                        secondalabel.setText(Data.currencyFormat(2, Database.isBigDecimal(load[3])));
                        thirdalabel.setText(Data.currencyFormat(3, Database.isBigDecimal(load[4])));
                        type++;
                        break;
                    }
                    case 2: {
                        String[] load = Database.getArray("Currencies", "Rate", "Pound");
                        typelabel.setText("Pound");
                        firstlabel.setText("Dollar:");
                        secondlabel.setText("Euro:");
                        thirdlabel.setText("Turkish Lira:");
                        firstalabel.setText(Data.currencyFormat(0, Database.isBigDecimal(load[1])));
                        secondalabel.setText(Data.currencyFormat(1, Database.isBigDecimal(load[2])));
                        thirdalabel.setText(Data.currencyFormat(3, Database.isBigDecimal(load[4])));
                        type++;
                        break;
                    }
                    case 3: {
                        String[] load = Database.getArray("Currencies", "Rate", "TurkishLira");
                        typelabel.setText("Turkish Lira");
                        firstlabel.setText("Dollar:");
                        secondlabel.setText("Euro:");
                        thirdlabel.setText("Pound:");
                        firstalabel.setText(Data.currencyFormat(0, Database.isBigDecimal(load[1])));
                        secondalabel.setText(Data.currencyFormat(1, Database.isBigDecimal(load[2])));
                        thirdalabel.setText(Data.currencyFormat(2, Database.isBigDecimal(load[3])));
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
        dolaralabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
        euroalabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
        poundalabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
        turkishliraalabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
    }

    public void balance() {
        String[] typename = {"Dollar", "Euro", "Pound", "TurkishLira"};
        if (!moneytext.getText().isBlank()) {
            if (moneytype.getSelectedIndex() != moneyctype.getSelectedIndex()) {
                BigDecimal money = Database.isBigDecimal(moneytext.getText());
                if (money.compareTo(BigDecimal.ZERO) > 0 && money.compareTo(Database.isBigDecimal("10000")) <= 0) {
                    BigDecimal rate = Database.getBigDecimal("Currencies", "Rate", typename[moneytype.getSelectedIndex()], typename[moneyctype.getSelectedIndex()]);
                    BigDecimal result = money.multiply(rate);
                    if (moneytype.getSelectedIndex() == 0 && Data.getCustomer().getDollar().compareTo(money) >= 0) {
                        Data.getCustomer().subtractDollar(money, 1);
                        dolaralabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                        switch (moneyctype.getSelectedIndex()) {
                            case 1:
                                Data.getCustomer().addEuro(result, 1);
                                euroalabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                                break;
                            case 2:
                                Data.getCustomer().addPound(result, 1);
                                poundalabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                                break;
                            case 3:
                                Data.getCustomer().addTurkishLira(result, 1);
                                turkishliraalabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                                break;
                            default:
                                break;
                        }
                        try {
                            moneytext.getDocument().remove(0, moneytext.getText().length());
                        } catch (BadLocationException ex) {
                            System.out.println(ex.toString());
                        }
                        infolabel3.setText("The money was successfully converted.");
                    } else if (moneytype.getSelectedIndex() == 1 && Data.getCustomer().getEuro().compareTo(money) >= 0) {
                        Data.getCustomer().subtractEuro(money, 1);
                        euroalabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                        switch (moneyctype.getSelectedIndex()) {
                            case 0:
                                Data.getCustomer().addDollar(result, 1);
                                dolaralabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                                break;
                            case 2:
                                Data.getCustomer().addPound(result, 1);
                                poundalabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                                break;
                            case 3:
                                Data.getCustomer().addTurkishLira(result, 1);
                                turkishliraalabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                                break;
                            default:
                                break;
                        }
                        try {
                            moneytext.getDocument().remove(0, moneytext.getText().length());
                        } catch (BadLocationException ex) {
                            System.out.println(ex.toString());
                        }
                        infolabel3.setText("The money was successfully converted.");
                    } else if (moneytype.getSelectedIndex() == 2 && Data.getCustomer().getPound().compareTo(money) >= 0) {
                        Data.getCustomer().subtractPound(money, 1);
                        poundalabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                        switch (moneyctype.getSelectedIndex()) {
                            case 0:
                                Data.getCustomer().addDollar(result, 1);
                                dolaralabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                                break;
                            case 1:
                                Data.getCustomer().addEuro(result, 1);
                                euroalabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                                break;
                            case 3:
                                Data.getCustomer().addTurkishLira(result, 1);
                                turkishliraalabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                                break;
                            default:
                                break;
                        }
                        try {
                            moneytext.getDocument().remove(0, moneytext.getText().length());
                        } catch (BadLocationException ex) {
                            System.out.println(ex.toString());
                        }
                        infolabel3.setText("The money was successfully converted.");
                    } else if (moneytype.getSelectedIndex() == 3 && Data.getCustomer().getTurkishLira().compareTo(money) >= 0) {
                        Data.getCustomer().subtractTurkishLira(money, 1);
                        turkishliraalabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                        switch (moneyctype.getSelectedIndex()) {
                            case 0:
                                Data.getCustomer().addDollar(result, 1);
                                dolaralabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                                break;
                            case 1:
                                Data.getCustomer().addEuro(result, 1);
                                euroalabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                                break;
                            case 2:
                                Data.getCustomer().addPound(result, 1);
                                poundalabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                                break;
                            default:
                                break;
                        }
                        try {
                            moneytext.getDocument().remove(0, moneytext.getText().length());
                        } catch (BadLocationException ex) {
                            System.out.println(ex.toString());
                        }
                        infolabel3.setText("The money was successfully converted.");
                    } else {
                        infolabel3.setText("You don't have the amount you write down.");
                    }
                } else {
                    infolabel3.setText("The number you enter must be between 1 and 10000.");
                }
            } else {
                infolabel3.setText("You can't convert it to the same type.");
            }
        } else {
            infolabel3.setText("The field can't be left blank.");
        }
    }

    public void result() {
        String[] typename = {"Dollar", "Euro", "Pound", "TurkishLira"};
        if (!moneytext.getText().isBlank()) {
            if (moneytype.getSelectedIndex() != moneyctype.getSelectedIndex()) {
                BigDecimal money = Database.isBigDecimal(moneytext.getText());
                if (money.compareTo(BigDecimal.ZERO) > 0 && money.compareTo(Database.isBigDecimal("10000")) <= 0) {
                    BigDecimal rate = Database.getBigDecimal("Currencies", "Rate", typename[moneytype.getSelectedIndex()], typename[moneyctype.getSelectedIndex()]);
                    BigDecimal result = money.multiply(rate);
                    infolabel3.setText("Result: " + money.toString() + " * " + rate.toString() + " = " + result);
                } else {
                    infolabel3.setText("The number you enter must be between 1 and 10000.");
                }
            } else {
                infolabel3.setText("You can't convert it to the same type.");
            }
        } else {
            infolabel3.setText("");
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
        cancelbtn = new java.awt.Button();
        cancelicon = new javax.swing.JLabel();
        infolabel = new javax.swing.JLabel();
        moneytext = new javax.swing.JTextField();
        okbtn = new java.awt.Button();
        moneytype = new javax.swing.JComboBox<>();
        moneyctype = new javax.swing.JComboBox<>();
        infolabel2 = new javax.swing.JLabel();
        infolabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        dolarlabel = new javax.swing.JLabel();
        eurolabel = new javax.swing.JLabel();
        poundlabel = new javax.swing.JLabel();
        turkishliralabel = new javax.swing.JLabel();
        dolaralabel = new javax.swing.JLabel();
        euroalabel = new javax.swing.JLabel();
        poundalabel = new javax.swing.JLabel();
        turkishliraalabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        typelabel = new javax.swing.JLabel();
        firstlabel = new javax.swing.JLabel();
        secondlabel = new javax.swing.JLabel();
        thirdlabel = new javax.swing.JLabel();
        firstalabel = new javax.swing.JLabel();
        secondalabel = new javax.swing.JLabel();
        thirdalabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        mainlabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainlabel.setForeground(new java.awt.Color(255, 255, 255));
        mainlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainlabel.setText("Balance");
        mainlabel.setMaximumSize(new java.awt.Dimension(223, 47));
        mainlabel.setMinimumSize(new java.awt.Dimension(223, 47));
        mainlabel.setPreferredSize(new java.awt.Dimension(223, 47));

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

        infolabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infolabel.setForeground(new java.awt.Color(255, 255, 255));
        infolabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel.setText("Enter amount you want to convert and press OK.");

        moneytext.setBackground(new java.awt.Color(23, 35, 51));
        moneytext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        moneytext.setForeground(new java.awt.Color(255, 255, 255));
        moneytext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        moneytext.setMaximumSize(new java.awt.Dimension(7, 39));
        moneytext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneytextActionPerformed(evt);
            }
        });
        moneytext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                moneytextKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                moneytextKeyReleased(evt);
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

        moneytype.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        moneytype.setForeground(new java.awt.Color(23, 35, 51));
        moneytype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dollar", "Euro", "Pound", "Turkish Lira" }));
        moneytype.setToolTipText("");
        moneytype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                moneytypeİtemStateChanged(evt);
            }
        });

        moneyctype.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        moneyctype.setForeground(new java.awt.Color(23, 35, 51));
        moneyctype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dollar", "Euro", "Pound", "Turkish Lira" }));
        moneyctype.setSelectedIndex(1);
        moneyctype.setToolTipText("");
        moneyctype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                moneyctypeİtemStateChanged(evt);
            }
        });

        infolabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infolabel2.setForeground(new java.awt.Color(255, 255, 255));
        infolabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel2.setText("Currency you want to convert.");

        infolabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infolabel3.setForeground(new java.awt.Color(255, 255, 255));
        infolabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel1.setMaximumSize(new java.awt.Dimension(296, 179));
        jPanel1.setMinimumSize(new java.awt.Dimension(296, 179));
        jPanel1.setPreferredSize(new java.awt.Dimension(296, 179));

        dolarlabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        dolarlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dolarlabel.setText("Dollar:");

        eurolabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        eurolabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eurolabel.setText("Euro:");

        poundlabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        poundlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        poundlabel.setText("Pound:");

        turkishliralabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        turkishliralabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        turkishliralabel.setText("Turkish Lira:");

        dolaralabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        dolaralabel.setForeground(new java.awt.Color(133, 187, 101));
        dolaralabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dolaralabel.setText("$500");

        euroalabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        euroalabel.setForeground(new java.awt.Color(133, 187, 101));
        euroalabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        euroalabel.setText("400 €");

        poundalabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        poundalabel.setForeground(new java.awt.Color(133, 187, 101));
        poundalabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        poundalabel.setText("£200");

        turkishliraalabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        turkishliraalabel.setForeground(new java.awt.Color(133, 187, 101));
        turkishliraalabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        turkishliraalabel.setText("₺500");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dolarlabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dolaralabel, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(turkishliralabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(turkishliraalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(poundlabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(poundalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(eurolabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(euroalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dolarlabel)
                    .addComponent(dolaralabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eurolabel)
                    .addComponent(euroalabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(poundlabel)
                    .addComponent(poundalabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(turkishliralabel)
                    .addComponent(turkishliraalabel))
                .addContainerGap())
        );

        jPanel2.setMaximumSize(new java.awt.Dimension(296, 179));
        jPanel2.setMinimumSize(new java.awt.Dimension(296, 179));

        typelabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        typelabel.setForeground(new java.awt.Color(133, 187, 101));
        typelabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typelabel.setText("Dollar");

        firstlabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        firstlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        firstlabel.setText("Euro:");

        secondlabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        secondlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        secondlabel.setText("Pound:");

        thirdlabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        thirdlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        thirdlabel.setText("Turkish Lira:");

        firstalabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        firstalabel.setForeground(new java.awt.Color(133, 187, 101));
        firstalabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstalabel.setText("1.21 €");

        secondalabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        secondalabel.setForeground(new java.awt.Color(133, 187, 101));
        secondalabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        secondalabel.setText("£1.14");

        thirdalabel.setFont(new java.awt.Font("Segoe UI", 0, 23)); // NOI18N
        thirdalabel.setForeground(new java.awt.Color(133, 187, 101));
        thirdalabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        thirdalabel.setText("₺5.42");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(typelabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(firstlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(secondlabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(thirdlabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstalabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(secondalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(thirdalabel, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(typelabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstlabel)
                    .addComponent(firstalabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secondlabel)
                    .addComponent(secondalabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thirdlabel)
                    .addComponent(thirdalabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(13, 13, 13)
                                    .addComponent(moneytext, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(moneytype, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(13, 13, 13))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(77, 77, 77)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(moneyctype, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(146, 146, 146))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(infolabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(77, 77, 77)))))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(cancelicon)
                            .addGap(220, 220, 220)
                            .addComponent(okbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(476, 476, 476))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infolabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infolabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(300, 300, 300))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(moneytext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(moneytype, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(infolabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(moneyctype, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(infolabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelicon)
                    .addComponent(okbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelbtnActionPerformed
        Data.setPage1(null);
        Data.setPage2(null);
        timer.stop();
        frame.ChangeJPanel("HomeCustomer");
    }//GEN-LAST:event_cancelbtnActionPerformed

    private void moneytextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneytextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moneytextActionPerformed

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
        balance();
    }//GEN-LAST:event_okbtnActionPerformed

    private void moneytextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moneytextKeyReleased
        result();
    }//GEN-LAST:event_moneytextKeyReleased

    private void moneytypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_moneytypeİtemStateChanged
        result();
    }//GEN-LAST:event_moneytypeİtemStateChanged

    private void moneyctypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_moneyctypeİtemStateChanged
        result();
    }//GEN-LAST:event_moneyctypeİtemStateChanged

    private void moneytextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moneytextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            balance();
        }
    }//GEN-LAST:event_moneytextKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button cancelbtn;
    private javax.swing.JLabel cancelicon;
    private javax.swing.JLabel dolaralabel;
    private javax.swing.JLabel dolarlabel;
    private javax.swing.JLabel euroalabel;
    private javax.swing.JLabel eurolabel;
    private javax.swing.JLabel firstalabel;
    private javax.swing.JLabel firstlabel;
    private javax.swing.JLabel infolabel;
    private javax.swing.JLabel infolabel2;
    private javax.swing.JLabel infolabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel mainlabel;
    private javax.swing.JComboBox<String> moneyctype;
    private javax.swing.JTextField moneytext;
    private javax.swing.JComboBox<String> moneytype;
    private java.awt.Button okbtn;
    private javax.swing.JLabel poundalabel;
    private javax.swing.JLabel poundlabel;
    private javax.swing.JLabel secondalabel;
    private javax.swing.JLabel secondlabel;
    private javax.swing.JLabel thirdalabel;
    private javax.swing.JLabel thirdlabel;
    private javax.swing.JLabel turkishliraalabel;
    private javax.swing.JLabel turkishliralabel;
    private javax.swing.JLabel typelabel;
    // End of variables declaration//GEN-END:variables
}
