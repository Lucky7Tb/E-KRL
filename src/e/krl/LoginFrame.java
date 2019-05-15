package e.krl;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class LoginFrame extends javax.swing.JFrame {

    protected static Statement statement;
    protected static ResultSet result;
    private String Query;
    private int Ekonomi;
    private int Eksekutif;
    private int FirstClass;
    private int HargaEkonomi;
    private int HargaEksekutif;
    private int HargaFirstClass;
    
    DateFormat dateFormat = new SimpleDateFormat("HHmmss");
    DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();
    DefaultTableModel model = new DefaultTableModel();
    
    Admin admin = new Admin();

    public LoginFrame() throws SQLException {
        initComponents();
        DBConnection.RunConnection();
        statement = DBConnection.statement;
        result = DBConnection.result;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DatePicker.setDateFormat(dateFormat);
        LoadTable();
        RegisterFrame.setSize(400, 300);
    }
    
    private void LoadTable() throws SQLException{
        result = statement.executeQuery("SELECT * FROM jadwal_pemberangkatan");
        JadwalTable.setModel(DbUtils.resultSetToTableModel(result));
    }

    private void Register() {
        admin.setUsername(UsernameRegister.getText().toLowerCase());
        admin.setPassword(PasswordRegister.getText());
        try {
            Query = String.format("SELECT username FROM admin WHERE username = '%s'", admin.getUsername());
            result = statement.executeQuery(Query);
            if (result.next()) {
                System.out.println("Username Sudah Ada");
                UsernameRegister.setText("");
                PasswordRegister.setText("");
            } else {
                Query = "INSERT INTO admin (username,password) VALUE ('%s','%s')";
                Query = String.format(Query, admin.getUsername(), admin.getPassword());
                if (!statement.execute(Query)) {
                    System.out.println("Register Berhasil");
                    new LoginFrame().setVisible(true);
                    RegisterFrame.dispose();
                } else {
                    System.out.println("Register Gagal");
                }
            }
        } catch (SQLException error) {
            System.out.println(error.getSQLState());
        }
    }

    private void InsertData() {
        try {
            Query = "INSERT INTO jadwal_pemberangkatan VALUE('%s','%s','%s','%tF','%tT','%tT','%d','%d','%d','%d','%d','%d')";
            String ID = dateFormat.format(date);
            Ekonomi = Integer.parseInt(EkonomiForm.getText());
            Eksekutif = Integer.parseInt(EksekutifForm.getText());
            FirstClass = Integer.parseInt(FirstClassForm.getText());
            HargaEkonomi = Integer.parseInt(PriceForm.getText());
            HargaEksekutif = Integer.parseInt(PriceForm2.getText());
            HargaFirstClass = Integer.parseInt(PriceForm3.getText());
            Query = String.format(Query, "PBKT"+ID, FormStasiunBerangkat.getSelectedItem(), FormStasiunTujuan.getSelectedItem(), DatePicker.getSelectedDate(), TimeChooser.getCalendarWithTime(date), TimeChooser2.getCalendarWithTime(date), HargaEkonomi, HargaEksekutif, HargaFirstClass, Ekonomi, Eksekutif, FirstClass);
            if (!statement.execute(Query)) {
                System.out.println("Data Berhasil Masuk");
                LoadTable();
            } else {
                System.out.println("Gagal");
            }
        } catch (SQLException error) {
            System.out.println(error.getSQLState());
        }
    }

    private void Login() {
        admin.setUsername(UsernameForm.getText().toLowerCase());
        admin.setPassword(PasswordForm.getText());
        try {
            Query = String.format("SELECT username, password FROM admin WHERE username  = '%s' ", admin.getUsername());
            result = statement.executeQuery(Query);
            if (result.next()) {
                if (result.getString("username").equals(admin.getUsername())) {
                    if (result.getString("password").equals(admin.getPassword())) {
                        System.out.println("Anda Behasil Login");
                        this.setVisible(false);
                        MainMenu.setVisible(true);
                    } else {
                        System.out.println("Password Anda Salah");
                        PasswordError.setText("Password Anda Salah");
                    }
                } else {
                    System.out.println("Username Anda Salah");
                    UsernameError.setText("Username Anda Salah");
                }
            } else {
                System.out.println("Username Tidak Ada");
                UsernameError.setText("Username Tidak Ada");
            }
        } catch (SQLException error) {
            System.out.println(error.getSQLState());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        RegisterFrame = new javax.swing.JFrame();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        UsernameRegister = new javax.swing.JTextField();
        RegisterButton = new javax.swing.JButton();
        PasswordRegister = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        MainMenu = new javax.swing.JFrame();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TimeChooser = new lu.tudor.santec.jtimechooser.JTimeChooser();
        TimeChooser2 = new lu.tudor.santec.jtimechooser.JTimeChooser();
        DatePicker = new datechooser.beans.DateChooserCombo();
        FormStasiunBerangkat = new javax.swing.JComboBox<>();
        FormStasiunTujuan = new javax.swing.JComboBox<>();
        PriceForm = new javax.swing.JTextField();
        FirstClassForm = new javax.swing.JTextField();
        EkonomiForm = new javax.swing.JTextField();
        EksekutifForm = new javax.swing.JTextField();
        DisplayId = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        EditButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        AddButton = new javax.swing.JButton();
        PriceForm2 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        PriceForm3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JadwalTable = new javax.swing.JTable();
        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("db_e_ticket?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        jadwalPemberangkatanQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT j FROM JadwalPemberangkatan j");
        jadwalPemberangkatanList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jadwalPemberangkatanQuery.getResultList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UsernameForm = new javax.swing.JTextField();
        LoginButton = new javax.swing.JButton();
        PasswordForm = new javax.swing.JPasswordField();
        PasswordError = new javax.swing.JLabel();
        UsernameError = new javax.swing.JLabel();

        RegisterFrame.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Register Admin");
        RegisterFrame.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(162, 11, -1, -1));

        jLabel6.setText("Username");
        RegisterFrame.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, 20));

        jLabel7.setText("Password");
        RegisterFrame.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, 20));
        RegisterFrame.getContentPane().add(UsernameRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 120, 20));

        RegisterButton.setText("Register");
        RegisterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterButtonActionPerformed(evt);
            }
        });
        RegisterFrame.getContentPane().add(RegisterButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 120, -1));
        RegisterFrame.getContentPane().add(PasswordRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 120, -1));

        jLabel8.setText("Login");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        RegisterFrame.getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 50, 20));

        MainMenu.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setText("Main Menu");
        MainMenu.getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 60, 20));

        jLabel4.setText("Create New Admin");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        MainMenu.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 0, 100, 30));
        MainMenu.getContentPane().add(TimeChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, -1, -1));
        MainMenu.getContentPane().add(TimeChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 90, -1, -1));
        MainMenu.getContentPane().add(DatePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        FormStasiunBerangkat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BD - Bandung - Bandung ", "CN - Cirebon - Cirebon", "GMR - Gambir - Jakarta", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        MainMenu.getContentPane().add(FormStasiunBerangkat, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 150, -1));

        FormStasiunTujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BD - Bandung - Bandung ", "CN - Cirebon - Cirebon", "GMR - Gambir - Jakarta", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        MainMenu.getContentPane().add(FormStasiunTujuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 150, -1));
        MainMenu.getContentPane().add(PriceForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 80, 20));
        MainMenu.getContentPane().add(FirstClassForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 60, -1));
        MainMenu.getContentPane().add(EkonomiForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 60, -1));
        MainMenu.getContentPane().add(EksekutifForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 60, -1));
        MainMenu.getContentPane().add(DisplayId, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Ke");
        MainMenu.getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, 30, 20));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Sampai");
        MainMenu.getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 40, 20));

        jLabel12.setText("Berangkat");
        MainMenu.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, -1, 20));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Harga Ekonomi");
        MainMenu.getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, 80, -1));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Ekonomi");
        MainMenu.getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 60, -1));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Eksekutif");
        jLabel15.setToolTipText("");
        MainMenu.getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 60, -1));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("FirstClass");
        MainMenu.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 60, -1));

        jLabel17.setText("Jumlah Tiket");
        MainMenu.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        EditButton.setText("Edit");
        MainMenu.getContentPane().add(EditButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 70, -1));

        DeleteButton.setText("Hapus");
        MainMenu.getContentPane().add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, 70, -1));

        AddButton.setText("Tambah");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        MainMenu.getContentPane().add(AddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 60, -1, -1));
        MainMenu.getContentPane().add(PriceForm2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 210, 60, -1));

        jLabel18.setText("Harga Eksekutif");
        MainMenu.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));
        MainMenu.getContentPane().add(PriceForm3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 210, 60, 20));

        jLabel19.setText("Harga FirstClass");
        MainMenu.getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 190, -1, -1));

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jadwalPemberangkatanList, JadwalTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${stasiunPemberangkatan}"));
        columnBinding.setColumnName("Stasiun Pemberangkatan");
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${ekonomi}"));
        columnBinding.setColumnName("Ekonomi");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${eksekutif}"));
        columnBinding.setColumnName("Eksekutif");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${firstclass}"));
        columnBinding.setColumnName("Firstclass");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();

        JadwalTable.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                JadwalTableAncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(JadwalTable);

        MainMenu.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 630, 100));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Login Admin");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, -1, -1));

        jLabel2.setText("Username");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, 20));

        jLabel3.setText("Password");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, 20));
        getContentPane().add(UsernameForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 120, 20));

        LoginButton.setText("Login");
        LoginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginButtonActionPerformed(evt);
            }
        });
        getContentPane().add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 120, -1));
        getContentPane().add(PasswordForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 120, -1));
        getContentPane().add(PasswordError, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 120, 20));
        getContentPane().add(UsernameError, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 120, 20));

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        RegisterFrame.setVisible(true);
        MainMenu.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        this.setVisible(true);
        RegisterFrame.dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void RegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterButtonActionPerformed
        Register();
    }//GEN-LAST:event_RegisterButtonActionPerformed

    private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginButtonActionPerformed
        Login();
    }//GEN-LAST:event_LoginButtonActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        InsertData();
    }//GEN-LAST:event_AddButtonActionPerformed

    private void JadwalTableAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_JadwalTableAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_JadwalTableAncestorAdded

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new LoginFrame().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private datechooser.beans.DateChooserCombo DatePicker;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel DisplayId;
    private javax.swing.JButton EditButton;
    private javax.swing.JTextField EkonomiForm;
    private javax.swing.JTextField EksekutifForm;
    private javax.swing.JTextField FirstClassForm;
    private javax.swing.JComboBox<String> FormStasiunBerangkat;
    private javax.swing.JComboBox<String> FormStasiunTujuan;
    private javax.swing.JTable JadwalTable;
    private javax.swing.JButton LoginButton;
    private javax.swing.JFrame MainMenu;
    private javax.swing.JLabel PasswordError;
    private javax.swing.JPasswordField PasswordForm;
    private javax.swing.JPasswordField PasswordRegister;
    private javax.swing.JTextField PriceForm;
    private javax.swing.JTextField PriceForm2;
    private javax.swing.JTextField PriceForm3;
    private javax.swing.JButton RegisterButton;
    private javax.swing.JFrame RegisterFrame;
    private lu.tudor.santec.jtimechooser.JTimeChooser TimeChooser;
    private lu.tudor.santec.jtimechooser.JTimeChooser TimeChooser2;
    private javax.swing.JLabel UsernameError;
    private javax.swing.JTextField UsernameForm;
    private javax.swing.JTextField UsernameRegister;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList;
    private javax.persistence.Query jadwalPemberangkatanQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
