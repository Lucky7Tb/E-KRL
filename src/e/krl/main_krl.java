package e.krl;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class main_krl extends javax.swing.JFrame {
    DefaultTableModel model = new DefaultTableModel();
    private int totalHarga;
    private String id;
    DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
    DateFormat dateFormat2 = new SimpleDateFormat("HHmmss");
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
        Konfirmasi.setSize(470, 300);
        
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
    
    private void setTotal(int total){
        totalHarga = total;
    }
    
    private int getTotal(){
        return totalHarga;
    }
    
    private String getId(){
        return id;
    }
    
    public void Ticket() throws SQLException{
        TicketDisplay.setText(TicketDisplay.getText()+"========================================\n");
        TicketDisplay.setText(TicketDisplay.getText()+"================ Tiket  ===================\n");
        TicketDisplay.setText(TicketDisplay.getText()+"Nama      :"+nama.getText()+"\n");
        TicketDisplay.setText(TicketDisplay.getText()+"Kode      :"+dateFormat2.format(date)+"\n");
        TicketDisplay.setText(TicketDisplay.getText()+"KodeTujuan:"+getId()+"\n");
        TicketDisplay.setText(TicketDisplay.getText()+"========================================\n");
        Query = "INSERT INTO pemesanan VALUES('%s','%s','%s','%s','%s')";
        Query = String.format(Query , dateFormat2.format(date) , getId() , nama.getText(), dateFormat.format(date), getTotal() );
        statement.execute(Query);
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
        Konfirmasi = new javax.swing.JFrame();
        nama = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CashField = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TicketDisplay = new javax.swing.JTextArea();
        Error = new javax.swing.JLabel();
        CashCheck = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
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
        jLabel11 = new javax.swing.JLabel();

        popupMenu1.setLabel("popupMenu1");

        Konfirmasi.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                namaKeyPressed(evt);
            }
        });
        Konfirmasi.getContentPane().add(nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 120, -1));

        jLabel6.setText("Atas Nama");
        Konfirmasi.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        jLabel7.setText("Cash");
        Konfirmasi.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 50, 30));

        CashField.setEnabled(false);
        Konfirmasi.getContentPane().add(CashField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 120, -1));

        jButton1.setText("Print Tiket");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Konfirmasi.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 90, 40));

        TicketDisplay.setColumns(20);
        TicketDisplay.setRows(5);
        jScrollPane1.setViewportView(TicketDisplay);

        Konfirmasi.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 240, 140));
        Konfirmasi.getContentPane().add(Error, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 120, 20));

        CashCheck.setText("Cek Uang");
        CashCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CashCheckActionPerformed(evt);
            }
        });
        Konfirmasi.getContentPane().add(CashCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 90, 40));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin_data.png"))); // NOI18N
        Konfirmasi.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, -80, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OrderButon.setText("Pesan");
        OrderButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderButonActionPerformed(evt);
            }
        });
        getContentPane().add(OrderButon, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 80, 40));

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

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 470, 220));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("Jadwal");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, -1));

        jLabel2.setFont(new java.awt.Font("UD Digi Kyokasho NP-R", 0, 36)); // NOI18N
        jLabel2.setText("TIKET KERETA API");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, -1, -1));

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
        getContentPane().add(CheckButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 290, 101, 65));

        ClassForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ekonomi", "eksekutif", "firstclass" }));
        getContentPane().add(ClassForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 230, 198, 41));

        TujuanForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CN - Cirebon - Cirebon", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        getContentPane().add(TujuanForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 130, 354, 41));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Tujuan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 90, -1, -1));

        TicketForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        getContentPane().add(TicketForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 230, 95, 41));

        jLabel1.setText("Kelas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 200, -1, -1));

        jLabel4.setText("Jumlah Tiket");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 200, -1, -1));

        Id.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(Id, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, 70, 30));
        getContentPane().add(DisplayTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 80, 30));
        getContentPane().add(DisplayHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 250, 80, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Harga");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 250, 70, 30));
        getContentPane().add(Calender, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 130, 50));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin_data_2.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-100, -110, 1240, 600));

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckButtonActionPerformed
        CekJadwal();
    }//GEN-LAST:event_CheckButtonActionPerformed

    private void JadwalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JadwalTableMouseClicked
        int row = JadwalTable.rowAtPoint(evt.getPoint());
        id = JadwalTable.getValueAt(row,0).toString();
        Id.setText(JadwalTable.getValueAt(row,0).toString());
        if(ClassForm.getSelectedItem().equals("ekonomi")){
            DisplayHarga.setText(JadwalTable.getValueAt(row,5).toString());
            int harga = Integer.parseInt( JadwalTable.getValueAt(row,5).toString());
            int JumlahTiket = Integer.parseInt(TicketForm.getSelectedItem().toString());
            DisplayTotal.setText(String.valueOf(harga*JumlahTiket));
            setTotal(harga*JumlahTiket);
        }else if(ClassForm.getSelectedItem().equals("eksekutif")){
            DisplayHarga.setText(JadwalTable.getValueAt(row,6).toString());
            int harga = Integer.parseInt( JadwalTable.getValueAt(row,6).toString());
            int JumlahTiket = Integer.parseInt(TicketForm.getSelectedItem().toString());
            setTotal(harga*JumlahTiket);
            DisplayTotal.setText(String.valueOf(harga*JumlahTiket));
        }else{
            DisplayHarga.setText(JadwalTable.getValueAt(row,7).toString());
            int harga = Integer.parseInt( JadwalTable.getValueAt(row,7).toString());
            int JumlahTiket = Integer.parseInt(TicketForm.getSelectedItem().toString());
            setTotal(harga*JumlahTiket);
            DisplayTotal.setText(String.valueOf(harga*JumlahTiket));
        }
    }//GEN-LAST:event_JadwalTableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       

        try {
            Ticket();
            TicketDisplay.print();
        } catch (PrinterException ex) {
            Logger.getLogger(main_krl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void OrderButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderButonActionPerformed
        Konfirmasi.setVisible(true);
    }//GEN-LAST:event_OrderButonActionPerformed

    private void CashCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CashCheckActionPerformed
        if(Integer.parseInt(CashField.getText()) < getTotal()){
            Error.setText("Uang Kurang");
            CashField.setText("");
            jButton1.setEnabled(false);
        }else if(Integer.parseInt(CashField.getText()) > getTotal()){
            int kembalian = Integer.parseInt(CashField.getText()) - getTotal();
            Error.setText(String.valueOf(kembalian));
            jButton1.setEnabled(true);
        }
        else{
            Error.setText("");
            jButton1.setEnabled(true);
        }
    }//GEN-LAST:event_CashCheckActionPerformed

    private void namaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_namaKeyPressed
        if(evt.getKeyCode() == evt.VK_ENTER){
            nama.setEnabled(false);
            CashField.setEnabled(true);
        }
    }//GEN-LAST:event_namaKeyPressed

    public static void main(String args[]) {
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
    private javax.swing.JButton CashCheck;
    private javax.swing.JTextField CashField;
    private javax.swing.JButton CheckButton;
    private javax.swing.JComboBox<String> ClassForm;
    private javax.swing.JTextField DisplayHarga;
    private javax.swing.JTextField DisplayTotal;
    private javax.swing.JLabel Error;
    private javax.swing.JLabel Id;
    private javax.swing.JTable JadwalTable;
    private javax.swing.JFrame Konfirmasi;
    private javax.swing.JButton OrderButon;
    private javax.swing.JTextArea TicketDisplay;
    private javax.swing.JComboBox<String> TicketForm;
    private javax.swing.JComboBox<String> TujuanForm;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList1;
    private javax.persistence.Query jadwalPemberangkatanQuery;
    private javax.persistence.Query jadwalPemberangkatanQuery1;
    private javax.swing.JTextField nama;
    private java.awt.PopupMenu popupMenu1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
