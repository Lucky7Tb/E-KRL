package e.krl;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
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
        LoadTable();
        RegisterFrame.setSize(415, 339);
        MainMenu.setSize(643, 405);
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
                    JOptionPane.showMessageDialog(RegisterFrame, "Register Berhasil", "Success", INFORMATION_MESSAGE);
                    new LoginFrame().setVisible(true);
                    RegisterFrame.dispose();
                } else {
                    System.out.println("Register Gagal");
                }
            }
        } catch (Exception Error) {
           Error.printStackTrace();
        }
    }

    private void InsertData() {
        try {
            Query = "INSERT INTO jadwal_pemberangkatan VALUE('%s','%s','%s','%s','%tT','%tT','%d','%d','%d','%d','%d','%d')";
            String ID = dateFormat.format(date);
            Ekonomi = Integer.parseInt(EkonomiForm.getText());
            Eksekutif = Integer.parseInt(EksekutifForm.getText());
            FirstClass = Integer.parseInt(FirstClassForm.getText());
            HargaEkonomi = Integer.parseInt(PriceForm.getText());
            HargaEksekutif = Integer.parseInt(PriceForm2.getText());
            HargaFirstClass = Integer.parseInt(PriceForm3.getText());
            Query = String.format(Query, "PBKT"+ID, "BD - Bandung - Bandung", FormStasiunTujuan.getSelectedItem(), DatePicker.getText(), TimeChooser.getCalendarWithTime(date), TimeChooser2.getCalendarWithTime(date), HargaEkonomi, HargaEksekutif, HargaFirstClass, Ekonomi, Eksekutif, FirstClass);
            if (!statement.execute(Query)) {
                System.out.println("Data Berhasil Masuk");
                LoadTable();
                ClearForm();
            } else {
                System.out.println("Gagal");
            }
        } catch (Exception Error) {
             Error.printStackTrace();
        }
    }
    
    private void DeleteData(){
        try{
            String Id = DisplayId.getText();
            Query = String.format("DELETE FROM jadwal_pemberangkatan WHERE id = '%s' ", Id);
             if (!statement.execute(Query)) {
                System.out.println("Data Berhasil Dihapus");
                LoadTable();
                ClearForm();
            } else {
                System.out.println("Gagal");
            }
        }catch(Exception Error){
            Error.printStackTrace();
        }
    }
    
    private void UpdateData(){
        try{
            Query = "UPDATE jadwal_pemberangkatan SET stasiun_tujuan = '%s', tanggal = '%sadmin ', waktu_berangkat = '%tT', waktu_sampai = '%tT', harga = %d, harga_eksekutif = %d, harga_firstclass = %d, ekonomi = %d, eksekutif = %d, firstclass = %d WHERE id = '%s' ";
            Ekonomi = Integer.parseInt(EkonomiForm.getText());
            Eksekutif = Integer.parseInt(EksekutifForm.getText());
            FirstClass = Integer.parseInt(FirstClassForm.getText());
            HargaEkonomi = Integer.parseInt(PriceForm.getText());
            HargaEksekutif = Integer.parseInt(PriceForm2.getText());
            HargaFirstClass = Integer.parseInt(PriceForm3.getText());
            Query = String.format(Query, FormStasiunTujuan.getSelectedItem(), DatePicker.getText(), TimeChooser.getCalendarWithTime(date), TimeChooser2.getCalendarWithTime(date), HargaEkonomi, HargaEksekutif, HargaFirstClass, Ekonomi, Eksekutif, FirstClass, DisplayId.getText());
            if (!statement.execute(Query)) {
              System.out.println("Data Berhasil DiUpdate");
              LoadTable();
              ClearForm();
            } else {
                System.out.println("Gagal");
            }
        }catch(Exception Error){
            Error.printStackTrace();
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
                        UsernameError.setText("");
                        PasswordError.setText("Password Anda Salah");
                    }
                } else {
                    System.out.println("Username Anda Salah");
                    PasswordError.setText("");
                    UsernameError.setText("Username Anda Salah");
                }
            } else {
                System.out.println("Username Tidak Ada");
                UsernameError.setText("Username Tidak Ada");
                PasswordError.setText("");
            }
        } catch (Exception Error) {
             Error.printStackTrace();
        }
    }
    
    private void ClearForm(){
        try {
            TimeChooser.setTime(dateFormat2.parse("00:00:00"));
            TimeChooser2.setTime(dateFormat2.parse("00:00:00"));
            EkonomiForm.setText("");
            EksekutifForm.setText("");
            FirstClassForm.setText("");
            PriceForm.setText("");
            PriceForm2.setText("");
            PriceForm3.setText("");
        } catch (ParseException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        RegisterFrame = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        UsernameRegister = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        PasswordRegister = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        MainMenu = new javax.swing.JFrame();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TimeChooser = new lu.tudor.santec.jtimechooser.JTimeChooser();
        TimeChooser2 = new lu.tudor.santec.jtimechooser.JTimeChooser();
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
        jButton1 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        DatePicker = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        entityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("db_e_ticket?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        jadwalPemberangkatanQuery = java.beans.Beans.isDesignTime() ? null : entityManager.createQuery("SELECT j FROM JadwalPemberangkatan j");
        jadwalPemberangkatanList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jadwalPemberangkatanQuery.getResultList();
        jLabel21 = new javax.swing.JLabel();
        PasswordForm = new javax.swing.JPasswordField();
        UsernameForm = new javax.swing.JTextField();
        UsernameError = new javax.swing.JLabel();
        PasswordError = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        UsernameRegister.setBackground(new java.awt.Color(75, 187, 244));
        UsernameRegister.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        UsernameRegister.setForeground(new java.awt.Color(255, 255, 255));
        UsernameRegister.setBorder(null);

        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        PasswordRegister.setBackground(new java.awt.Color(75, 187, 244));
        PasswordRegister.setForeground(new java.awt.Color(255, 255, 255));
        PasswordRegister.setBorder(null);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/RegisterAdmin.png"))); // NOI18N

        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(UsernameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(PasswordRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(UsernameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(PasswordRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel1)
        );

        javax.swing.GroupLayout RegisterFrameLayout = new javax.swing.GroupLayout(RegisterFrame.getContentPane());
        RegisterFrame.getContentPane().setLayout(RegisterFrameLayout);
        RegisterFrameLayout.setHorizontalGroup(
            RegisterFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        RegisterFrameLayout.setVerticalGroup(
            RegisterFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        MainMenu.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Main Menu");
        MainMenu.getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 100, 20));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Create New Admin");
        jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        MainMenu.getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 130, 30));
        MainMenu.getContentPane().add(TimeChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 90, -1, -1));
        MainMenu.getContentPane().add(TimeChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, -1, -1));

        FormStasiunTujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CN - Cirebon - Cirebon", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        MainMenu.getContentPane().add(FormStasiunTujuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 150, -1));
        MainMenu.getContentPane().add(PriceForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 210, 80, 20));
        MainMenu.getContentPane().add(FirstClassForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 80, -1));
        MainMenu.getContentPane().add(EkonomiForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 80, -1));
        MainMenu.getContentPane().add(EksekutifForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 80, -1));

        DisplayId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MainMenu.getContentPane().add(DisplayId, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Tujuan");
        MainMenu.getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 60, 20));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Sampai");
        MainMenu.getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 70, 20));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Berangkat");
        MainMenu.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 70, 20));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Harga Ekonomi");
        MainMenu.getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, 130, -1));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Ekonomi");
        MainMenu.getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 80, -1));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Eksekutif");
        jLabel15.setToolTipText("");
        MainMenu.getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 80, -1));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("FirstClass");
        MainMenu.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 140, 80, -1));

        jLabel17.setText("Jumlah Tiket");
        MainMenu.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        EditButton.setText("Update");
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });
        MainMenu.getContentPane().add(EditButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 90, -1));

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });
        MainMenu.getContentPane().add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 90, -1));

        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        MainMenu.getContentPane().add(AddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 90, -1));
        MainMenu.getContentPane().add(PriceForm2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 210, 80, -1));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Harga Eksekutif");
        MainMenu.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 190, 160, -1));
        MainMenu.getContentPane().add(PriceForm3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 80, 20));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Harga FirstClass");
        MainMenu.getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, 140, -1));

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jadwalPemberangkatanList, JadwalTable);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${stasiunPemberangkatan}"));
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

        JadwalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JadwalTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(JadwalTable);

        MainMenu.getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 630, 130));

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        MainMenu.getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 90, -1));

        jLabel20.setText("Logout");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel20MouseClicked(evt);
            }
        });
        MainMenu.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, 30));
        MainMenu.getContentPane().add(DatePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 90, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin_data.png"))); // NOI18N
        MainMenu.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, -90, 680, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LoginAdmin.png"))); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PasswordForm.setBackground(new java.awt.Color(76, 188, 246));
        PasswordForm.setForeground(new java.awt.Color(255, 255, 255));
        PasswordForm.setBorder(null);
        getContentPane().add(PasswordForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 130, 20));

        UsernameForm.setBackground(new java.awt.Color(76, 188, 246));
        UsernameForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        UsernameForm.setForeground(new java.awt.Color(255, 255, 255));
        UsernameForm.setBorder(null);
        UsernameForm.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        getContentPane().add(UsernameForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 130, 20));

        UsernameError.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        UsernameError.setForeground(new java.awt.Color(255, 0, 0));
        UsernameError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(UsernameError, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 130, 20));

        PasswordError.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PasswordError.setForeground(new java.awt.Color(255, 0, 0));
        PasswordError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(PasswordError, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 130, 20));

        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 130, 40));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LoginAdmin.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 280));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 280));

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JadwalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JadwalTableMouseClicked
        ClearForm();
        int row = JadwalTable.rowAtPoint(evt.getPoint());
        String Id = JadwalTable.getValueAt(row, 0).toString();
        String StasiunTujuan = JadwalTable.getValueAt(row, 2).toString();
        String Tanggal = JadwalTable.getValueAt(row, 3).toString();
        String WaktuBerangkat = JadwalTable.getValueAt(row, 4).toString();
        String WaktuSampai = JadwalTable.getValueAt(row, 5).toString();
        String HargaEkonomi = JadwalTable.getValueAt(row, 6).toString();
        String HargaEksekutif = JadwalTable.getValueAt(row, 7).toString();
        String HargaFristClass = JadwalTable.getValueAt(row, 8).toString();
        String TicketEkonomi = JadwalTable.getValueAt(row, 9).toString();
        String TicketEksekutif = JadwalTable.getValueAt(row, 10).toString();
        String TicketFristClass = JadwalTable.getValueAt(row, 11).toString();
        try {
            Date Time1 = new SimpleDateFormat("HH:mm:ss").parse(WaktuBerangkat);
            Date Time2 = new SimpleDateFormat("HH:mm:ss").parse(WaktuSampai);
            TimeChooser.setTime(Time1);
            TimeChooser2.setTime(Time2);
        } catch (ParseException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        DatePicker.setText(Tanggal);
        FormStasiunTujuan.setSelectedItem(StasiunTujuan);
        EkonomiForm.setText(TicketEkonomi);
        EksekutifForm.setText(TicketEksekutif);
        FirstClassForm.setText(TicketFristClass);
        PriceForm.setText(HargaEkonomi);
        PriceForm2.setText(HargaEksekutif);
        PriceForm3.setText(HargaFristClass);
        DisplayId.setText(Id);
    }//GEN-LAST:event_JadwalTableMouseClicked

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        InsertData();
    }//GEN-LAST:event_AddButtonActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        RegisterFrame.setVisible(true);
        MainMenu.dispose();
    }//GEN-LAST:event_jLabel4MouseClicked

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        DeleteData();
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        UpdateData();
    }//GEN-LAST:event_EditButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    ClearForm();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel20MouseClicked
    MainMenu.dispose();
    this.setVisible(true);
    }//GEN-LAST:event_jLabel20MouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        Login();
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        Register();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        MainMenu.setVisible(true);
        RegisterFrame.dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

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
    private javax.swing.JTextField DatePicker;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel DisplayId;
    private javax.swing.JButton EditButton;
    private javax.swing.JTextField EkonomiForm;
    private javax.swing.JTextField EksekutifForm;
    private javax.swing.JTextField FirstClassForm;
    private javax.swing.JComboBox<String> FormStasiunTujuan;
    private javax.swing.JTable JadwalTable;
    private javax.swing.JFrame MainMenu;
    private javax.swing.JLabel PasswordError;
    private javax.swing.JPasswordField PasswordForm;
    private javax.swing.JPasswordField PasswordRegister;
    private javax.swing.JTextField PriceForm;
    private javax.swing.JTextField PriceForm2;
    private javax.swing.JTextField PriceForm3;
    private javax.swing.JFrame RegisterFrame;
    private lu.tudor.santec.jtimechooser.JTimeChooser TimeChooser;
    private lu.tudor.santec.jtimechooser.JTimeChooser TimeChooser2;
    private javax.swing.JLabel UsernameError;
    private javax.swing.JTextField UsernameForm;
    private javax.swing.JTextField UsernameRegister;
    private javax.persistence.EntityManager entityManager;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList;
    private javax.persistence.Query jadwalPemberangkatanQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
