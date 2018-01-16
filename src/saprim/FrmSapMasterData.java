/*
 * frmSapMasterData.java
 *
 * Created on 07 Ekim 2008 Salı, 14:59
 */

package saprim;

/**
 *
 * @author  Kerem
 */

import saprim.sap.Sap;

public class FrmSapMasterData extends javax.swing.JFrame {

    private saprim.integration.integrator integ;
    private saprim.integration.MasterData master;
    private saprim.integration.Project project;
    
    /** Creates new form frmSapMasterData */
    public FrmSapMasterData(saprim.integration.integrator I) {
        constructor(I, null);
    }
    
    public FrmSapMasterData(saprim.integration.integrator I, saprim.integration.Project P) {
        constructor(I, P);
    }
    
    private void constructor(saprim.integration.integrator I, saprim.integration.Project P) {
        initComponents();
        
        integ = I;
        project = P;
        
        btnNext.setEnabled(false);
        
        try
        {
            master = Main.sap.getMasterData();
            cmbBusArea.setModel(new javax.swing.DefaultComboBoxModel(master.businessAreas));
            if (project != null && project.vgsbr != null) { for (int n = 0; n < master.businessAreas.length; n++) if (master.businessAreas[n].gsber.equals(project.vgsbr)) cmbBusArea.setSelectedIndex(n);}
            cmbCompany.setModel(new javax.swing.DefaultComboBoxModel(master.companyCodes));
            if (project != null && project.vbukr != null) { for (int n = 0; n < master.companyCodes.length; n++) if (master.companyCodes[n].bukrs.equals(project.vbukr)) cmbCompany.setSelectedIndex(n);}
            cmbPlant.setModel(new javax.swing.DefaultComboBoxModel(master.plants));
            if (project != null && project.werks != null) { for (int n = 0; n < master.plants.length; n++) if (master.plants[n].werks.equals(project.werks)) cmbPlant.setSelectedIndex(n);}
            cmbProjectProfile.setModel(new javax.swing.DefaultComboBoxModel(master.projectProfiles));
            if (project != null && project.profl != null) { for (int n = 0; n < master.projectProfiles.length; n++) if (master.projectProfiles[n].profidproj.equals(project.profl)) cmbProjectProfile.setSelectedIndex(n);}
            cmbProjectManager.setModel(new javax.swing.DefaultComboBoxModel(master.projectManagers));
            if (project != null && project.vernr != null) { for (int n = 0; n < master.projectManagers.length; n++) if (master.projectManagers[n].vernr.equals(project.vernr)) cmbProjectManager.setSelectedIndex(n);}
            cmbRequester.setModel(new javax.swing.DefaultComboBoxModel(master.requesters));
            if (project != null && project.astnr != null) { for (int n = 0; n < master.requesters.length; n++) if (master.requesters[n].astnr.equals(project.astnr)) cmbRequester.setSelectedIndex(n);}
            //cmbPlace.setModel(new javax.swing.DefaultComboBoxModel(master.places));
            if (master.profitCenters != null && master.profitCenters.length > 0) cmbProfitCenter.setModel(new javax.swing.DefaultComboBoxModel(master.profitCenters));
            if (project != null && project.prctr != null) { for (int n = 0; n < master.profitCenters.length; n++) if (master.profitCenters[n].prctr.equals(project.prctr)) cmbProfitCenter.setSelectedIndex(n);}
            cmbProjectType.setModel(new javax.swing.DefaultComboBoxModel(master.projectTypes));
            if (project != null && project.wbss != null && project.wbss.length >= 1) { for (int n = 0; n < master.projectTypes.length; n++) if (master.projectTypes[n].prart.equals(project.wbss[0].prart)) cmbProjectType.setSelectedIndex(n);}
            cmbMrpController.setModel(new javax.swing.DefaultComboBoxModel(master.mrpControllers));
            
            cmbWorkCenter.setModel(new javax.swing.DefaultComboBoxModel(master.workCenters));
            cmbDistributionKey.setModel(new javax.swing.DefaultComboBoxModel(master.distributionKeys));
            
            for (int n = 0; n < master.businessAreas.length; n++) if (Sap.pack(master.businessAreas[n].gsber).equals(Sap.pack(Main.config.defBusinessArea))) cmbBusArea.setSelectedIndex(n);
            for (int n = 0; n < master.companyCodes.length; n++) if (Sap.pack(master.companyCodes[n].bukrs).equals(Sap.pack(Main.config.defCompanyCode))) cmbCompany.setSelectedIndex(n);
            for (int n = 0; n < master.plants.length; n++) if (Sap.pack(master.plants[n].werks).equals(Sap.pack(Main.config.defPlant))) cmbPlant.setSelectedIndex(n);
            for (int n = 0; n < master.projectProfiles.length; n++) if (Sap.pack(master.projectProfiles[n].profidproj).equals(Sap.pack(Main.config.defProfile))) cmbProjectProfile.setSelectedIndex(n);
            for (int n = 0; n < master.projectManagers.length; n++) if (Sap.pack(master.projectManagers[n].vernr).equals(Sap.pack(Main.config.defManager))) cmbProjectManager.setSelectedIndex(n);
            for (int n = 0; n < master.requesters.length; n++) if (Sap.pack(master.requesters[n].astnr).equals(Sap.pack(Main.config.defRequester))) cmbRequester.setSelectedIndex(n);
            for (int n = 0; n < master.profitCenters.length; n++) if (Sap.pack(master.profitCenters[n].prctr).equals(Sap.pack(Main.config.defProfitCenter))) cmbProfitCenter.setSelectedIndex(n);
            for (int n = 0; n < master.projectTypes.length; n++) if (Sap.pack(master.projectTypes[n].prart).equals(Sap.pack(Main.config.defProjectType))) cmbProjectType.setSelectedIndex(n);
            for (int n = 0; n < master.mrpControllers.length; n++) if (Sap.pack(master.mrpControllers[n].dispo).equals(Sap.pack(Main.config.defMrpController)) && Sap.pack(master.mrpControllers[n].werks).equals(Sap.pack(Main.config.defPlant)) ) cmbMrpController.setSelectedIndex(n);
            for (int n = 0; n < master.workCenters.length; n++) if (Sap.pack(master.workCenters[n].arbpl).equals(Sap.pack(Main.config.defWorkCenter)) && Sap.pack(master.workCenters[n].werks).equals(Sap.pack(Main.config.defPlant)) ) cmbWorkCenter.setSelectedIndex(n);
            for (int n = 0; n < master.distributionKeys.length; n++) if (Sap.pack(master.distributionKeys[n].vertl).equals(Sap.pack(Main.config.defDistributionKey))) cmbDistributionKey.setSelectedIndex(n);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            this.dispose();
            return;
        }
        
        btnNext.setEnabled(true);        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmbBusArea = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cmbCompany = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        cmbPlant = new javax.swing.JComboBox();
        cmbProjectProfile = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        cmbProjectManager = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cmbRequester = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        cmbProfitCenter = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        cmbProjectType = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        cmbMrpController = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cmbWorkCenter = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cmbDistributionKey = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/saprim/saplogo2.png"))); // NOI18N

        jLabel2.setText("Business Area");

        jLabel3.setText("Company Code");

        jLabel4.setText("Plant");

        jLabel5.setText("Profile");

        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jLabel6.setText("Pr.Manager");

        jLabel7.setText("Requester");

        jLabel9.setText("Profit C.");

        jLabel10.setText("Project Type");

        jLabel11.setText("MRP Controller");

        jLabel12.setText("Work Center");

        jLabel13.setText("Distribution Key");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addGap(14, 14, 14))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(12, 12, 12))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                .addGap(17, 17, 17))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPlant, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbCompany, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbBusArea, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbProjectManager, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbRequester, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmbWorkCenter, 0, 243, Short.MAX_VALUE))
                            .addComponent(cmbProjectType, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbProfitCenter, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbMrpController, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)
                            .addComponent(cmbProjectProfile, javax.swing.GroupLayout.Alignment.TRAILING, 0, 243, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbDistributionKey, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNext, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbBusArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbCompany, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPlant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbProjectProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cmbProjectManager, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cmbRequester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbProfitCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbProjectType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cmbMrpController, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbWorkCenter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cmbDistributionKey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnNext)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (cmbBusArea.getSelectedIndex() < 0 ||
            cmbCompany.getSelectedIndex() < 0 ||
            cmbPlant.getSelectedIndex() < 0 ||
            cmbProjectProfile.getSelectedIndex() < 0 ||
            cmbProjectManager.getSelectedIndex() < 0 ||
            cmbRequester.getSelectedIndex() < 0 ||
            //cmbPlace.getSelectedIndex() < 0 ||
            cmbProjectType.getSelectedIndex() < 0 ||
            cmbMrpController.getSelectedIndex() < 0 ||
            cmbWorkCenter.getSelectedIndex() < 0 ||
            cmbDistributionKey.getSelectedIndex() < 0
                ) return;
        
        btnNext.setEnabled(false);
        integ.setSapMasterDataSelected(
                master.businessAreas[cmbBusArea.getSelectedIndex()], 
                master.companyCodes[cmbCompany.getSelectedIndex()], 
                master.plants[cmbPlant.getSelectedIndex()], 
                master.projectProfiles[cmbProjectProfile.getSelectedIndex()],
                master.projectManagers[cmbProjectManager.getSelectedIndex()],
                master.requesters[cmbRequester.getSelectedIndex()],
                null, //master.places[cmbPlace.getSelectedIndex()],
                cmbProfitCenter.getSelectedIndex() >= 0 ? master.profitCenters[cmbProfitCenter.getSelectedIndex()] : null,
                master.projectTypes[cmbProjectType.getSelectedIndex()],
                master.mrpControllers[cmbMrpController.getSelectedIndex()],
                master.workCenters[cmbWorkCenter.getSelectedIndex()],
                master.distributionKeys[cmbDistributionKey.getSelectedIndex()]
                );
        this.dispose();
}//GEN-LAST:event_btnNextActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmSapMasterData(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JComboBox cmbBusArea;
    private javax.swing.JComboBox cmbCompany;
    private javax.swing.JComboBox cmbDistributionKey;
    private javax.swing.JComboBox cmbMrpController;
    private javax.swing.JComboBox cmbPlant;
    private javax.swing.JComboBox cmbProfitCenter;
    private javax.swing.JComboBox cmbProjectManager;
    private javax.swing.JComboBox cmbProjectProfile;
    private javax.swing.JComboBox cmbProjectType;
    private javax.swing.JComboBox cmbRequester;
    private javax.swing.JComboBox cmbWorkCenter;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables

}
