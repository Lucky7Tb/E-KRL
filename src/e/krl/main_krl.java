package e.krl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class main_krl extends javax.swing.JFrame {
     DefaultTableModel model = new DefaultTableModel();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    protected static Statement statement;
    protected static ResultSet result;
    protected String Query;
    protected int Ticket;
    
    public main_krl() throws SQLException {
        initComponents();
        DBConnection.RunConnection();
        statement = DBConnection.statement;
        result = DBConnection.result;
    }
    
    private void CekJadwal(){
        Ticket = Integer.parseInt((String) TicketForm.getSelectedItem());
        try{
            Query = "SELECT id,stasiun_tujuan,tanggal,waktu_berangkat,waktu_sampai,harga,harga_eksekutif,harga_firstclass FROM jadwal_pemberangkatan WHERE %s >= %d AND tanggal = '%s' AND stasiun_tujuan = '%s' ";
            Query = String.format(Query, ClassForm.getSelectedItem(), Ticket, Calender.getText(), TujuanForm.getSelectedItem() );
            result = statement.executeQuery(Query);
            JadwalTable.setModel(DbUtils.resultSetToTableModel(result));
            
        }catch(Exception Error){
            Error.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        popupMenu1 = new java.awt.PopupMenu();
        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("db_e_ticket?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        jadwalPemberangkatanQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT j FROM JadwalPemberangkatan j");
        jadwalPemberangkatanList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jadwalPemberangkatanQuery.getResultList();
        jadwalPemberangkatanQuery1 = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT j FROM JadwalPemberangkatan j");
        jadwalPemberangkatanList1 = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jadwalPemberangkatanQuery1.getResultList();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        DisplayId = new javax.swing.JLabel();
        OrderButon = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        JadwalTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        CheckButton = new javax.swing.JButton();
        ClassForm = new javax.swing.JComboBox<>();
        TujuanForm = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        TicketForm = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Id = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        DisplayTotal = new javax.swing.JTextField();
        DisplayHarga = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        Calender = new javax.swing.JTextField();

        popupMenu1.setLabel("popupMenu1");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setText("Kelas Kereta");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 200, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("Jumlah Tiket");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 200, -1, -1));
        jPanel1.add(DisplayId, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 40));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OrderButon.setText("Pesan");
        getContentPane().add(OrderButon, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, 80, 40));

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jadwalPemberangkatanList1, JadwalTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${stasiunTujuan}"));
        columnBinding.setColumnName("Stasiun Tujuan");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${tanggal}"));
        columnBinding.setColumnName("Tanggal");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${waktuBerangkat}"));
        columnBinding.setColumnName("Waktu Berangkat");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${waktuSampai}"));
        columnBinding.setColumnName("Waktu Sampai");
        columnBinding.setColumnClass(java.util.Date.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${harga}"));
        columnBinding.setColumnName("Harga");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${hargaEksekutif}"));
        columnBinding.setColumnName("Harga Eksekutif");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${hargaFirstclass}"));
        columnBinding.setColumnName("Harga Firstclass");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        JadwalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JadwalTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JadwalTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 470, 220));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Jadwal");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, -1, -1));

        jLabel2.setFont(new java.awt.Font("UD Digi Kyokasho NP-R", 0, 36)); // NOI18N
        jLabel2.setText("TIKET KERETA API");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Tanggal Berangkat");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 90, -1, -1));

        CheckButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        CheckButton.setText("Cek");
        CheckButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckButtonActionPerformed(evt);
            }
        });
        getContentPane().add(CheckButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 350, 101, 65));

        ClassForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ekonomi", "eksekutif", "firstclass" }));
        getContentPane().add(ClassForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 230, 198, 41));

        TujuanForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CN - Cirebon - Cirebon", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        getContentPane().add(TujuanForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 130, 354, 41));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Tujuan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 90, -1, -1));

        TicketForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5" }));
        getContentPane().add(TicketForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 230, 95, 41));

        jLabel1.setText("Kelas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 200, -1, -1));

        jLabel4.setText("Jumlah Tiket");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 200, -1, -1));

        Id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(Id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 70, 30));
        getContentPane().add(DisplayTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 80, 30));
        getContentPane().add(DisplayHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 80, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Harga");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 70, 30));
        getContentPane().add(Calender, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 130, 50));

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckButtonActionPerformed
        CekJadwal();
    }//GEN-LAST:event_CheckButtonActionPerformed

    private void JadwalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JadwalTableMouseClicked
        int row = JadwalTable.rowAtPoint(evt.getPoint());
        Id.setText(JadwalTable.getValueAt(row,0).toString());
        if(ClassForm.getSelectedItem().equals("ekonomi")){
            DisplayHarga.setText(JadwalTable.getValueAt(row,5).toString());
            int harga = Integer.parseInt( JadwalTable.getValueAt(row,5).toString());
            int JumlahTiket = Integer.parseInt(TicketForm.getSelectedItem().toString());
            DisplayTotal.setText(String.valueOf(harga*JumlahTiket));
        }
    }//GEN-LAST:event_JadwalTableMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(main_krl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(main_krl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(main_krl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(main_krl.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new main_krl().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(main_krl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Calender;
    private javax.swing.JButton CheckButton;
    private javax.swing.JComboBox<String> ClassForm;
    private javax.swing.JTextField DisplayHarga;
    private javax.swing.JLabel DisplayId;
    private javax.swing.JTextField DisplayTotal;
    private javax.swing.JLabel Id;
    private javax.swing.JTable JadwalTable;
    private javax.swing.JButton OrderButon;
    private javax.swing.JComboBox<String> TicketForm;
    private javax.swing.JComboBox<String> TujuanForm;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList1;
    private javax.persistence.Query jadwalPemberangkatanQuery;
    private javax.persistence.Query jadwalPemberangkatanQuery1;
    private java.awt.PopupMenu popupMenu1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
