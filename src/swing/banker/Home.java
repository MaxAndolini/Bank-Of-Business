/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing.banker;

/**
 *
 * @author ercan
 */
public class Home extends javax.swing.JPanel {

    final private swing.Home frame;
    
    /**
     * Creates new form Home
     */
    public Home(swing.Home home) {
        initComponents();
        frame = home;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addcustomerbtn = new java.awt.Button();
        deletecustomerbtn = new java.awt.Button();
        settingsbtn = new java.awt.Button();
        mainlabel = new javax.swing.JLabel();
        addcustomericon = new javax.swing.JLabel();
        deletecustomericon = new javax.swing.JLabel();
        settingsicon = new javax.swing.JLabel();
        balancebtn = new java.awt.Button();
        balanceicon = new javax.swing.JLabel();
        logoutbtn = new java.awt.Button();
        logouticon = new javax.swing.JLabel();
        infolabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(71, 120, 197));
        setMaximumSize(new java.awt.Dimension(1070, 590));
        setMinimumSize(new java.awt.Dimension(1070, 590));
        setPreferredSize(new java.awt.Dimension(1070, 590));

        addcustomerbtn.setBackground(new java.awt.Color(23, 35, 51));
        addcustomerbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        addcustomerbtn.setForeground(new java.awt.Color(255, 255, 255));
        addcustomerbtn.setLabel("Add Customer");
        addcustomerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addcustomerbtnActionPerformed(evt);
            }
        });

        deletecustomerbtn.setBackground(new java.awt.Color(23, 35, 51));
        deletecustomerbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        deletecustomerbtn.setForeground(new java.awt.Color(255, 255, 255));
        deletecustomerbtn.setLabel("Del. Customer");
        deletecustomerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletecustomerbtnActionPerformed(evt);
            }
        });

        settingsbtn.setBackground(new java.awt.Color(23, 35, 51));
        settingsbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        settingsbtn.setForeground(new java.awt.Color(255, 255, 255));
        settingsbtn.setLabel("Settings");
        settingsbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsbtnActionPerformed(evt);
            }
        });

        mainlabel.setFont(new java.awt.Font("Segoe UI", 0, 35)); // NOI18N
        mainlabel.setForeground(new java.awt.Color(255, 255, 255));
        mainlabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        mainlabel.setText("Welcome Banker");

        addcustomericon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_add_property_48px.png"))); // NOI18N

        deletecustomericon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_remove_property_48px_1.png"))); // NOI18N

        settingsicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_automatic_48px.png"))); // NOI18N

        balancebtn.setBackground(new java.awt.Color(23, 35, 51));
        balancebtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        balancebtn.setForeground(new java.awt.Color(255, 255, 255));
        balancebtn.setLabel("Edit Customer");
        balancebtn.setMinimumSize(new java.awt.Dimension(80, 49));
        balancebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balancebtnActionPerformed(evt);
            }
        });

        balanceicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_edit_property_48px.png"))); // NOI18N

        logoutbtn.setBackground(new java.awt.Color(23, 35, 51));
        logoutbtn.setFont(new java.awt.Font("Segoe UI", 0, 30)); // NOI18N
        logoutbtn.setForeground(new java.awt.Color(255, 255, 255));
        logoutbtn.setLabel("Logout");
        logoutbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutbtnActionPerformed(evt);
            }
        });

        logouticon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/swing/images/icons8_exit_48px.png"))); // NOI18N

        infolabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        infolabel.setForeground(new java.awt.Color(255, 255, 255));
        infolabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infolabel.setText("You can select the actions to do from below.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(300, 300, 300))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(settingsicon)
                                .addGap(511, 511, 511))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addcustomerbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addcustomericon)
                                .addGap(538, 538, 538)
                                .addComponent(deletecustomericon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deletecustomerbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(mainlabel, javax.swing.GroupLayout.PREFERRED_SIZE, 488, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(balancebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(balanceicon)
                                .addGap(170, 170, 170)
                                .addComponent(settingsbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(170, 170, 170)
                                .addComponent(logouticon)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(logoutbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(mainlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(infolabel, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deletecustomerbtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(deletecustomericon))
                    .addComponent(addcustomerbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addcustomericon))
                .addGap(190, 190, 190)
                .addComponent(settingsicon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(settingsbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balancebtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(balanceicon)
                    .addComponent(logoutbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logouticon, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addcustomerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addcustomerbtnActionPerformed
        frame.ChangeJPanel("AddCustomerBanker");
    }//GEN-LAST:event_addcustomerbtnActionPerformed

    private void deletecustomerbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletecustomerbtnActionPerformed
        frame.ChangeJPanel("DeleteCustomerBanker");
    }//GEN-LAST:event_deletecustomerbtnActionPerformed

    private void settingsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsbtnActionPerformed
        frame.ChangeJPanel("SettingsBanker");
    }//GEN-LAST:event_settingsbtnActionPerformed

    private void balancebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balancebtnActionPerformed
        frame.ChangeJPanel("EditCustomerBanker");
    }//GEN-LAST:event_balancebtnActionPerformed

    private void logoutbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutbtnActionPerformed
        frame.ChangeJPanel("LoginCustomer");
    }//GEN-LAST:event_logoutbtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button addcustomerbtn;
    private javax.swing.JLabel addcustomericon;
    private java.awt.Button balancebtn;
    private javax.swing.JLabel balanceicon;
    private java.awt.Button deletecustomerbtn;
    private javax.swing.JLabel deletecustomericon;
    private javax.swing.JLabel infolabel;
    private java.awt.Button logoutbtn;
    private javax.swing.JLabel logouticon;
    private javax.swing.JLabel mainlabel;
    private java.awt.Button settingsbtn;
    private javax.swing.JLabel settingsicon;
    // End of variables declaration//GEN-END:variables
}