/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.banker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
public class EditCustomer extends javax.swing.JPanel {

    final private swing.Home frame;
    Timer timer;
    String text;
    int type;

    /**
     * Creates new form EditCustomer
     *
     * @param home Dynamic panel.
     */
    public EditCustomer(swing.Home home) {
        initComponents();
        frame = home;

        ((AbstractDocument) searchText.getDocument()).setDocumentFilter(new Filter(1, 16));
        ((AbstractDocument) fullNameText.getDocument()).setDocumentFilter(new Filter(0, 32));
        ((AbstractDocument) dateOfBirthText.getDocument()).setDocumentFilter(new Filter(0, 10));
        ((AbstractDocument) jobText.getDocument()).setDocumentFilter(new Filter(0, 32));
        ((AbstractDocument) phoneNumberText.getDocument()).setDocumentFilter(new Filter(1, 11));
        ((AbstractDocument) homeAddressText.getDocument()).setDocumentFilter(new Filter(0, 32));
        ((AbstractDocument) passwordText.getDocument()).setDocumentFilter(new Filter(0, 15));

        visible(false);
    }

    private final void visible(boolean option) {
        infoLabel3.setVisible(option);
        fullNameText.setVisible(option);
        infoLabel4.setVisible(option);
        dateOfBirthText.setVisible(option);
        infoLabel5.setVisible(option);
        jobText.setVisible(option);
        infoLabel6.setVisible(option);
        phoneNumberText.setVisible(option);
        infoLabel7.setVisible(option);
        homeAddressText.setVisible(option);
        infoLabel8.setVisible(option);
        passwordText.setVisible(option);
    }

    private void search() {
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
                                fullNameText.setText(data[3]);
                                dateOfBirthText.setText(data[4]);
                                jobText.setText(data[5]);
                                phoneNumberText.setText(data[6]);
                                homeAddressText.setText(data[11]);
                                passwordText.setText(data[12]);
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

    private void clear() {
        searchText.setText(null);
        searchType.setSelectedIndex(0);
        if (timer != null) {
            timer.stop();
        }
        visible(false);
        infoLabel.setText("The search was successfully cleared.");
    }

    private void editCustomer() {

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
        String[] typename = {"ID", "CardNumber", "FullName"};
        if (!fullNameText.getText().isBlank() && !dateOfBirthText.getText().isBlank() && !jobText.getText().isBlank() && !phoneNumberText.getText().isBlank() && !homeAddressText.getText().isBlank() && !passwordText.getText().isBlank()) {
            if (Data.dateValidation(dateOfBirthText.getText())) {
                if (passwordText.getText().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,15}$")) {
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
                                Database.set("Accounts", "ID", ID, "FullName", fullNameText.getText());
                                Database.set("Accounts", "ID", ID, "DateofBirth", dateOfBirthText.getText());
                                Database.set("Accounts", "ID", ID, "Job", jobText.getText());
                                Database.set("Accounts", "ID", ID, "PhoneNumber", phoneNumberText.getText());
                                Database.set("Accounts", "ID", ID, "HomeAddress", homeAddressText.getText());
                                Database.set("Accounts", "ID", ID, "Password", passwordText.getText());
                                Database.set("Accounts", "ID", ID, "UpdatedAt", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
                                fullNameText.setText(null);
                                dateOfBirthText.setText(null);
                                jobText.setText(null);
                                phoneNumberText.setText(null);
                                homeAddressText.setText(null);
                                passwordText.setText(null);
                                visible(false);
                                infoLabel.setText("The customer was successfully edited. [ID: " + ID + "]");
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
                    infoLabel.setText("The password doesn't follow the rules.");
                }
            } else {
                infoLabel.setText("The date of birth doesn't follow the rules.");
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
        fullNameText = new javax.swing.JTextField();
        infoLabel4 = new javax.swing.JLabel();
        dateOfBirthText = new javax.swing.JTextField();
        infoLabel5 = new javax.swing.JLabel();
        jobText = new javax.swing.JTextField();
        infoLabel6 = new javax.swing.JLabel();
        phoneNumberText = new javax.swing.JTextField();
        infoLabel7 = new javax.swing.JLabel();
        homeAddressText = new javax.swing.JTextField();
        infoLabel8 = new javax.swing.JLabel();
        passwordText = new javax.swing.JTextField();
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
        mainLabel.setText("Edit Customer");
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
        infoLabel3.setText("Full Name");

        fullNameText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        fullNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fullNameText.setMaximumSize(new java.awt.Dimension(7, 39));
        fullNameText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fullNameTextActionPerformed(evt);
            }
        });
        fullNameText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                fullNameTextKeyPressed(evt);
            }
        });

        infoLabel4.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel4.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel4.setText("Date of Birth");

        dateOfBirthText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        dateOfBirthText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dateOfBirthText.setMaximumSize(new java.awt.Dimension(7, 39));
        dateOfBirthText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateOfBirthTextActionPerformed(evt);
            }
        });
        dateOfBirthText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dateOfBirthTextKeyPressed(evt);
            }
        });

        infoLabel5.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel5.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel5.setText("Job");

        jobText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        jobText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jobText.setMaximumSize(new java.awt.Dimension(7, 39));
        jobText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jobTextActionPerformed(evt);
            }
        });
        jobText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jobTextKeyPressed(evt);
            }
        });

        infoLabel6.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel6.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel6.setText("Phone Number");

        phoneNumberText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        phoneNumberText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        phoneNumberText.setMaximumSize(new java.awt.Dimension(7, 39));
        phoneNumberText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneNumberTextActionPerformed(evt);
            }
        });
        phoneNumberText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                phoneNumberTextKeyPressed(evt);
            }
        });

        infoLabel7.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel7.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel7.setText("Home Address");

        homeAddressText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        homeAddressText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        homeAddressText.setMaximumSize(new java.awt.Dimension(7, 39));
        homeAddressText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeAddressTextActionPerformed(evt);
            }
        });
        homeAddressText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                homeAddressTextKeyPressed(evt);
            }
        });

        infoLabel8.setFont(new java.awt.Font("Segoe UI", 0, 26)); // NOI18N
        infoLabel8.setForeground(new java.awt.Color(255, 255, 255));
        infoLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoLabel8.setText("Password");

        passwordText.setFont(new java.awt.Font("Tahoma", 1, 27)); // NOI18N
        passwordText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordText.setMaximumSize(new java.awt.Dimension(7, 39));
        passwordText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordTextActionPerformed(evt);
            }
        });
        passwordText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passwordTextKeyPressed(evt);
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

        editIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_edit_property_48px.png"))); // NOI18N

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
                        .addGap(30, 30, 30)
                        .addComponent(phoneNumberText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(homeAddressText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(infoLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(infoLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(infoLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(infoLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(infoLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(infoLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(10, 10, 10))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(infoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(300, 300, 300))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(fullNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(dateOfBirthText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(jobText, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jobText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateOfBirthText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fullNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(infoLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(infoLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(homeAddressText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        editCustomer();
    }//GEN-LAST:event_editButtonActionPerformed

    private void fullNameTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fullNameTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fullNameTextActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        if (timer != null) {
            timer.stop();
        }
        frame.changeJPanel("HomeBanker");
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void phoneNumberTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneNumberTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneNumberTextActionPerformed

    private void dateOfBirthTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateOfBirthTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateOfBirthTextActionPerformed

    private void homeAddressTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeAddressTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_homeAddressTextActionPerformed

    private void jobTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jobTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jobTextActionPerformed

    private void passwordTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passwordTextActionPerformed

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        search();
    }//GEN-LAST:event_okButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clear();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void fullNameTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fullNameTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editCustomer();
        }
    }//GEN-LAST:event_fullNameTextKeyPressed

    private void dateOfBirthTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dateOfBirthTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editCustomer();
        }
    }//GEN-LAST:event_dateOfBirthTextKeyPressed

    private void jobTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jobTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editCustomer();
        }
    }//GEN-LAST:event_jobTextKeyPressed

    private void phoneNumberTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneNumberTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editCustomer();
        }
    }//GEN-LAST:event_phoneNumberTextKeyPressed

    private void homeAddressTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_homeAddressTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editCustomer();
        }
    }//GEN-LAST:event_homeAddressTextKeyPressed

    private void passwordTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passwordTextKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            editCustomer();
        }
    }//GEN-LAST:event_passwordTextKeyPressed

    private void searchTypeİtemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_searchTypeİtemStateChanged
        switch (searchType.getSelectedIndex()) {
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
    private javax.swing.JTextField dateOfBirthText;
    private java.awt.Button editButton;
    private javax.swing.JLabel editIcon;
    private javax.swing.JTextField fullNameText;
    private javax.swing.JTextField homeAddressText;
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel infoLabel2;
    private javax.swing.JLabel infoLabel3;
    private javax.swing.JLabel infoLabel4;
    private javax.swing.JLabel infoLabel5;
    private javax.swing.JLabel infoLabel6;
    private javax.swing.JLabel infoLabel7;
    private javax.swing.JLabel infoLabel8;
    private javax.swing.JTextField jobText;
    private javax.swing.JLabel mainLabel;
    private java.awt.Button okButton;
    private javax.swing.JTextField passwordText;
    private javax.swing.JTextField phoneNumberText;
    private javax.swing.JTextField searchText;
    private javax.swing.JComboBox<String> searchType;
    // End of variables declaration//GEN-END:variables
}
