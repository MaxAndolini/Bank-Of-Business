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
import library.*;

/**
 *
 * @author ercan
 */
public class Balance extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer, timer2;

    /**
     * Creates new form Balance
     *
     * @param home Dynamic panel.
     */
    public Balance(swing.Home home) {
        initComponents();
        frame = home;

        ((AbstractDocument) moneyText.getDocument()).setDocumentFilter(new Filter(1, 5));

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

        timer2 = new Timer(2000, (ActionEvent e) -> {
            dolarAmountLabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
            euroAmountLabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
            poundAmountLabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
            turkishLiraAmountLabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
        });
        timer2.setInitialDelay(0);
        timer2.start();
    }

    private void balance() {
        String[] typename = {"Dollar", "Euro", "Pound", "Turkish Lira"};
        String[] typename2 = {"Dollar", "Euro", "Pound", "TurkishLira"};
        if (!moneyText.getText().isBlank()) {
            if (moneyType.getSelectedIndex() != moneyConvertType.getSelectedIndex()) {
                BigDecimal money = Database.isBigDecimal(moneyText.getText());
                if (money.compareTo(BigDecimal.ZERO) > 0 && money.compareTo(new BigDecimal("10000")) <= 0) {
                    BigDecimal rate = Database.getBigDecimal("Currencies", "Rate", typename[moneyType.getSelectedIndex()], typename2[moneyConvertType.getSelectedIndex()]);
                    BigDecimal result = money.multiply(rate);
                    if (moneyType.getSelectedIndex() == 0 && Data.getCustomer().getDollar().compareTo(money) >= 0) {
                        Data.getCustomer().subtractDollar(money, 1);
                        dolarAmountLabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                        switch (moneyConvertType.getSelectedIndex()) {
                            case 1:
                                Data.getCustomer().addEuro(result, 1);
                                euroAmountLabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                                break;
                            case 2:
                                Data.getCustomer().addPound(result, 1);
                                poundAmountLabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                                break;
                            case 3:
                                Data.getCustomer().addTurkishLira(result, 1);
                                turkishLiraAmountLabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                                break;
                            default:
                                break;
                        }
                        moneyText.setText(null);
                        infoLabel3.setText("The money was successfully converted.");
                    } else if (moneyType.getSelectedIndex() == 1 && Data.getCustomer().getEuro().compareTo(money) >= 0) {
                        Data.getCustomer().subtractEuro(money, 1);
                        euroAmountLabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                        switch (moneyConvertType.getSelectedIndex()) {
                            case 0:
                                Data.getCustomer().addDollar(result, 1);
                                dolarAmountLabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                                break;
                            case 2:
                                Data.getCustomer().addPound(result, 1);
                                poundAmountLabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                                break;
                            case 3:
                                Data.getCustomer().addTurkishLira(result, 1);
                                turkishLiraAmountLabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                                break;
                            default:
                                break;
                        }
                        moneyText.setText(null);
                        infoLabel3.setText("The money was successfully converted.");
                    } else if (moneyType.getSelectedIndex() == 2 && Data.getCustomer().getPound().compareTo(money) >= 0) {
                        Data.getCustomer().subtractPound(money, 1);
                        poundAmountLabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                        switch (moneyConvertType.getSelectedIndex()) {
                            case 0:
                                Data.getCustomer().addDollar(result, 1);
                                dolarAmountLabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                                break;
                            case 1:
                                Data.getCustomer().addEuro(result, 1);
                                euroAmountLabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                                break;
                            case 3:
                                Data.getCustomer().addTurkishLira(result, 1);
                                turkishLiraAmountLabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                                break;
                            default:
                                break;
                        }
                        moneyText.setText(null);
                        infoLabel3.setText("The money was successfully converted.");
                    } else if (moneyType.getSelectedIndex() == 3 && Data.getCustomer().getTurkishLira().compareTo(money) >= 0) {
                        Data.getCustomer().subtractTurkishLira(money, 1);
                        turkishLiraAmountLabel.setText(Data.currencyFormat(3, Data.getCustomer().getTurkishLira()));
                        switch (moneyConvertType.getSelectedIndex()) {
                            case 0:
                                Data.getCustomer().addDollar(result, 1);
                                dolarAmountLabel.setText(Data.currencyFormat(0, Data.getCustomer().getDollar()));
                                break;
                            case 1:
                                Data.getCustomer().addEuro(result, 1);
                                euroAmountLabel.setText(Data.currencyFormat(1, Data.getCustomer().getEuro()));
                                break;
                            case 2:
                                Data.getCustomer().addPound(result, 1);
                                poundAmountLabel.setText(Data.currencyFormat(2, Data.getCustomer().getPound()));
                                break;
                            default:
                                break;
                        }
                        moneyText.setText(null);
                        infoLabel3.setText("The money was successfully converted.");
                    } else {
                        infoLabel3.setText("You don't have the amount you write down.");
                    }
                } else {
                    infoLabel3.setText("The number you enter must be between 1 and 10000.");
                }
            } else {
                infoLabel3.setText("You can't convert it to the same type.");
            }
        } else {
            infoLabel3.setText("The field can't be left blank.");
        }
    }

    private void result() {
        String[] typename = {"Dollar", "Euro", "Pound", "Turkish Lira"};
        String[] typename2 = {"Dollar", "Euro", "Pound", "TurkishLira"};
        if (!moneyText.getText().isBlank()) {
            if (moneyType.getSelectedIndex() != moneyConvertType.getSelectedIndex()) {
                BigDecimal money = Database.isBigDecimal(moneyText.getText());
                if (money.compareTo(BigDecimal.ZERO) > 0 && money.compareTo(new BigDecimal("10000")) <= 0) {
                    BigDecimal rate = Database.getBigDecimal("Currencies", "Rate", typename[moneyType.getSelectedIndex()], typename2[moneyConvertType.getSelectedIndex()]);
                    BigDecimal result = money.multiply(rate);
                    infoLabel3.setText("Result: " + money.toString() + " * " + rate.toString() + " = " + result);
                } else {
                    infoLabel3.setText("The number you enter must be between 1 and 10000.");
                }
            } else {
                infoLabel3.setText("You can't convert it to the same type.");
            }
        } else {
            infoLabel3.setText("");
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
        moneyText = new javax.swing.JTextField();
        moneyType = new javax.swing.JComboBox<>();
        moneyConvertType = new javax.swing.JComboBox<>();
        infoLabel2 = new javax.swing.JLabel();
        infoLabel3 = new javax.swing.JLabel();
        okButton = new java.awt.Button();
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
        infoLabel.setText("Enter amount you want to convert and press OK.");

        moneyText.setBackground(new java.awt.Color(23, 35, 51));
        moneyText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        moneyText.setForeground(new java.awt.Color(255, 255, 255));
        moneyText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        moneyText.setMaximumSize(new java.awt.Dimension(7, 39));
        moneyText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moneyTextActionPerformed(evt);
            }
        });
        moneyText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                moneyTextKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                moneyTextKeyReleased(evt);
            }
        });

        moneyType.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        moneyType.setForeground(new java.awt.Color(23, 35, 51));
        moneyType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dollar", "Euro", "Pound", "Turkish Lira" }));
        moneyType.setToolTipText("");
        moneyType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                moneyTypeİtemStateChanged(evt);
            }
        });

        moneyConvertType.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        moneyConvertType.setForeground(new java.awt.Color(23, 35, 51));
        moneyConvertType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dollar", "Euro", "Pound", "Turkish Lira" }));
        moneyConvertType.setSelectedIndex(1);
        moneyConvertType.setToolTipText("");
        moneyConvertType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                moneyConvertTypeİtemStateChanged(evt);
            }
        });

        infoLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infoLabel2.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel2.setText("Currency you want to convert.");

        infoLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infoLabel3.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        okButton.setBackground(new java.awt.Color(23, 35, 51));
        okButton.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        okButton.setForeground(new java.awt.Color(255, 255, 255));
        okButton.setLabel("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
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
                .addGap(11, 11, 11)
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
        jPanel2.setPreferredSize(new java.awt.Dimension(296, 179));

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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(13, 13, 13)
                                    .addComponent(moneyText, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(10, 10, 10)
                                    .addComponent(moneyType, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(13, 13, 13))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(77, 77, 77)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(moneyConvertType, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(146, 146, 146))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(infoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(77, 77, 77)))))
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(cancelIcon)
                            .addGap(220, 220, 220)
                            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(476, 476, 476))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(moneyText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(moneyType, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(infoLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(moneyConvertType, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(infoLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelIcon)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        frame.ChangeJPanel("HomeCustomer");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void moneyTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moneyTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moneyTextActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        balance();
    }//GEN-LAST:event_okButtonActionPerformed

    private void moneyTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moneyTextKeyReleased
        result();
    }//GEN-LAST:event_moneyTextKeyReleased

    private void moneyTypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_moneyTypeİtemStateChanged
        result();
    }//GEN-LAST:event_moneyTypeİtemStateChanged

    private void moneyConvertTypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_moneyConvertTypeİtemStateChanged
        result();
    }//GEN-LAST:event_moneyConvertTypeİtemStateChanged

    private void moneyTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_moneyTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            balance();
        }
    }//GEN-LAST:event_moneyTextKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button cancelButton;
    private javax.swing.JLabel cancelIcon;
    private javax.swing.JLabel dolarAmountLabel;
    private javax.swing.JLabel dolarLabel;
    private javax.swing.JLabel euroAmountLabel;
    private javax.swing.JLabel euroLabel;
    private javax.swing.JLabel firstAmountLabel;
    private javax.swing.JLabel firstLabel;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel infoLabel2;
    private javax.swing.JLabel infoLabel3;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel mainLabel;
    private javax.swing.JComboBox<String> moneyConvertType;
    private javax.swing.JTextField moneyText;
    private javax.swing.JComboBox<String> moneyType;
    private java.awt.Button okButton;
    private javax.swing.JLabel poundAmountLabel;
    private javax.swing.JLabel poundLabel;
    private javax.swing.JLabel secondAmountLabel;
    private javax.swing.JLabel secondLabel;
    private javax.swing.JLabel thirdAmountLabel;
    private javax.swing.JLabel thirdLabel;
    private javax.swing.JLabel turkishLiraAmountLabel;
    private javax.swing.JLabel turkishLiraLabel;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration//GEN-END:variables
}
