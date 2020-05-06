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
public class EditBanker extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer;
    String text;
    int type;

    /**
     * Creates new form EditBanker
     *
     * @param home Dynamic panel.
     */
    public EditBanker(swing.Home home) {
        initComponents();
        frame = home;

        ((AbstractDocument) searchtext.getDocument()).setDocumentFilter(new Filter(1, 16));
        ((AbstractDocument) fullnametext.getDocument()).setDocumentFilter(new Filter(0, 32));
        ((AbstractDocument) dateofbirthtext.getDocument()).setDocumentFilter(new Filter(0, 10));
        ((AbstractDocument) phonenumbertext.getDocument()).setDocumentFilter(new Filter(1, 11));
        ((AbstractDocument) homeaddresstext.getDocument()).setDocumentFilter(new Filter(0, 32));
        ((AbstractDocument) passwordtext.getDocument()).setDocumentFilter(new Filter(0, 15));
        ((AbstractDocument) salarytext.getDocument()).setDocumentFilter(new Filter(0, 15));

        visible(false);
    }

    public final void visible(boolean option) {
        infolabel3.setVisible(option);
        fullnametext.setVisible(option);
        infolabel4.setVisible(option);
        dateofbirthtext.setVisible(option);
        infolabel5.setVisible(option);
        phonenumbertext.setVisible(option);
        infolabel6.setVisible(option);
        homeaddresstext.setVisible(option);
        infolabel7.setVisible(option);
        passwordtext.setVisible(option);
        infolabel8.setVisible(option);
        salarytext.setVisible(option);
    }

    public void search() {
        String[] typename = {"ID", "FullName"};
        if (timer != null) {
            timer.stop();
        }
        if (!searchtext.getText().isBlank()) {
            if (Database.exists("Accounts", typename[searchtype.getSelectedIndex()], searchtext.getText())) {
                if (Database.getInt("Accounts", typename[searchtype.getSelectedIndex()], searchtext.getText(), "AccountType") == 1) {
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
                                fullnametext.setText(data[3]);
                                dateofbirthtext.setText(data[4]);
                                phonenumbertext.setText(data[6]);
                                homeaddresstext.setText(data[11]);
                                passwordtext.setText(data[12]);
                                salarytext.setText(data[13]);
                            }
                        }
                    });
                    timer.setInitialDelay(0);
                    timer.start();
                    visible(true);
                    searchtext.setText(null);
                    searchtype.setSelectedIndex(0);
                    infolabel.setText("The banker was successfully found.");
                } else {
                    visible(false);
                    infolabel.setText("The account type is invalid.");
                }
            } else {
                visible(false);
                infolabel.setText("The banker is invalid.");
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

    public void editBanker() {

        /*
        1) ^ - start of the string
        2) (?=.*[0-9]) - Positive look ahead expression for any number
        3) (?=.*[a-z]) - Positive look ahead expression for lower case letter
        4) (?=.*[A-Z]) - Positive look ahead expression for upper case letter
        5) (?=.*[!@#$%^&*]) - Positive look ahead expression for any special character
        6) (?=\\S+$) - Positive look ahead expression for \S (non-space character)
        7) . – any character
        8) {8,} - minimum 8 characters in length
        9) $ - end of the string
         */
        String[] typename = {"ID", "FullName"};
        if (!fullnametext.getText().isBlank() && !dateofbirthtext.getText().isBlank() && !phonenumbertext.getText().isBlank() && !homeaddresstext.getText().isBlank() && !passwordtext.getText().isBlank() && !salarytext.getText().isBlank()) {
            if (Data.dateValidation(dateofbirthtext.getText())) {
                if (passwordtext.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,15}$")) {
                    BigDecimal salary = Database.isBigDecimal(salarytext.getText());
                    if (salary.compareTo(BigDecimal.ZERO) > 0 && salary.compareTo(new BigDecimal("-1")) != 0) {
                        if (Database.exists("Accounts", typename[type], text)) {
                            if (Database.getInt("Accounts", typename[type], text, "AccountType") == 1) {
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
                                    Database.set("Accounts", "ID", ID, "FullName", fullnametext.getText());
                                    Database.set("Accounts", "ID", ID, "DateofBirth", dateofbirthtext.getText());
                                    Database.set("Accounts", "ID", ID, "PhoneNumber", phonenumbertext.getText());
                                    Database.set("Accounts", "ID", ID, "HomeAddress", homeaddresstext.getText());
                                    Database.set("Accounts", "ID", ID, "Password", passwordtext.getText());
                                    Database.set("Accounts", "ID", ID, "Salary", salary);
                                    Database.set("Accounts", "ID", ID, "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                                    fullnametext.setText(null);
                                    dateofbirthtext.setText(null);
                                    phonenumbertext.setText(null);
                                    homeaddresstext.setText(null);
                                    passwordtext.setText(null);
                                    salarytext.setText(null);
                                    visible(false);
                                    infolabel.setText("The banker was successfully edited. [ID: " + ID + "]");
                                } else {
                                    if (timer != null) {
                                        timer.stop();
                                    }
                                    visible(false);
                                    infolabel.setText("System error and the banker couldn't be edited.");
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
                            infolabel.setText("The banker is invalid.");
                        }
                    } else {
                        infolabel.setText("The salary doesn't follow the rules.");
                    }
                } else {
                    infolabel.setText("The password doesn't follow the rules.");
                }
            } else {
                infolabel.setText("The date of birth doesn't follow the rules.");
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
        fullnametext = new javax.swing.JTextField();
        infolabel4 = new javax.swing.JLabel();
        dateofbirthtext = new javax.swing.JTextField();
        infolabel5 = new javax.swing.JLabel();
        phonenumbertext = new javax.swing.JTextField();
        infolabel6 = new javax.swing.JLabel();
        homeaddresstext = new javax.swing.JTextField();
        infolabel7 = new javax.swing.JLabel();
        passwordtext = new javax.swing.JTextField();
        infolabel8 = new javax.swing.JLabel();
        salarytext = new javax.swing.JTextField();
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
        mainlabel.setText("Edit Banker");
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
        searchtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Full Name" }));
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
        infolabel3.setText("Full Name");

        fullnametext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        fullnametext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fullnametext.setMaximumSize(new java.awt.Dimension(7, 39));
        fullnametext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullnametextActionPerformed(evt);
            }
        });
        fullnametext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fullnametextKeyPressed(evt);
            }
        });

        infolabel4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel4.setForeground(new java.awt.Color(255, 255, 255));
        infolabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel4.setText("Date of Birth");

        dateofbirthtext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        dateofbirthtext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dateofbirthtext.setMaximumSize(new java.awt.Dimension(7, 39));
        dateofbirthtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateofbirthtextActionPerformed(evt);
            }
        });
        dateofbirthtext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateofbirthtextKeyPressed(evt);
            }
        });

        infolabel5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel5.setForeground(new java.awt.Color(255, 255, 255));
        infolabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel5.setText("Phone Number");

        phonenumbertext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        phonenumbertext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        phonenumbertext.setMaximumSize(new java.awt.Dimension(7, 39));
        phonenumbertext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonenumbertextActionPerformed(evt);
            }
        });
        phonenumbertext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                phonenumbertextKeyPressed(evt);
            }
        });

        infolabel6.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel6.setForeground(new java.awt.Color(255, 255, 255));
        infolabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel6.setText("Home Address");

        homeaddresstext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        homeaddresstext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        homeaddresstext.setMaximumSize(new java.awt.Dimension(7, 39));
        homeaddresstext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeaddresstextActionPerformed(evt);
            }
        });
        homeaddresstext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                homeaddresstextKeyPressed(evt);
            }
        });

        infolabel7.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel7.setForeground(new java.awt.Color(255, 255, 255));
        infolabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel7.setText("Password");

        passwordtext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        passwordtext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordtext.setMaximumSize(new java.awt.Dimension(7, 39));
        passwordtext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordtextActionPerformed(evt);
            }
        });
        passwordtext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordtextKeyPressed(evt);
            }
        });

        infolabel8.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infolabel8.setForeground(new java.awt.Color(255, 255, 255));
        infolabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel8.setText("Salary");

        salarytext.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        salarytext.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        salarytext.setMaximumSize(new java.awt.Dimension(7, 39));
        salarytext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salarytextActionPerformed(evt);
            }
        });
        salarytext.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                salarytextKeyPressed(evt);
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

        editicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_edit_property_48px.png"))); // NOI18N

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
                        .addGap(30, 30, 30)
                        .addComponent(homeaddresstext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(passwordtext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(salarytext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(infolabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infolabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(infolabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(infolabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(infolabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(infolabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(fullnametext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(dateofbirthtext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(phonenumbertext, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(phonenumbertext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateofbirthtext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fullnametext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infolabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infolabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infolabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(homeaddresstext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordtext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salarytext, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        editBanker();
    }//GEN-LAST:event_editbtnActionPerformed

    private void fullnametextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullnametextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullnametextActionPerformed

    private void cancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelbtnActionPerformed
        if (timer != null) {
            timer.stop();
        }
        frame.ChangeJPanel("HomeAdmin");
    }//GEN-LAST:event_cancelbtnActionPerformed

    private void homeaddresstextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeaddresstextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_homeaddresstextActionPerformed

    private void dateofbirthtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateofbirthtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateofbirthtextActionPerformed

    private void passwordtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordtextActionPerformed

    private void phonenumbertextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonenumbertextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phonenumbertextActionPerformed

    private void salarytextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salarytextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_salarytextActionPerformed

    private void searchtextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchtextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchtextActionPerformed

    private void okbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okbtnActionPerformed
        search();
    }//GEN-LAST:event_okbtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void fullnametextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullnametextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editBanker();
        }
    }//GEN-LAST:event_fullnametextKeyPressed

    private void dateofbirthtextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateofbirthtextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editBanker();
        }
    }//GEN-LAST:event_dateofbirthtextKeyPressed

    private void phonenumbertextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phonenumbertextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editBanker();
        }
    }//GEN-LAST:event_phonenumbertextKeyPressed

    private void homeaddresstextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_homeaddresstextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editBanker();
        }
    }//GEN-LAST:event_homeaddresstextKeyPressed

    private void passwordtextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordtextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editBanker();
        }
    }//GEN-LAST:event_passwordtextKeyPressed

    private void salarytextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_salarytextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editBanker();
        }
    }//GEN-LAST:event_salarytextKeyPressed

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
    private javax.swing.JTextField dateofbirthtext;
    private java.awt.Button editbtn;
    private javax.swing.JLabel editicon;
    private javax.swing.JTextField fullnametext;
    private javax.swing.JTextField homeaddresstext;
    private javax.swing.JLabel infolabel;
    private javax.swing.JLabel infolabel2;
    private javax.swing.JLabel infolabel3;
    private javax.swing.JLabel infolabel4;
    private javax.swing.JLabel infolabel5;
    private javax.swing.JLabel infolabel6;
    private javax.swing.JLabel infolabel7;
    private javax.swing.JLabel infolabel8;
    private javax.swing.JLabel mainlabel;
    private java.awt.Button okbtn;
    private javax.swing.JTextField passwordtext;
    private javax.swing.JTextField phonenumbertext;
    private javax.swing.JTextField salarytext;
    private javax.swing.JTextField searchtext;
    private javax.swing.JComboBox<String> searchtype;
    // End of variables declaration//GEN-END:variables
}
