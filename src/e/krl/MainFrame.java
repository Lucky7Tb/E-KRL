package e.krl;

import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;
import java.text.ParseException;
import javax.swing.JOptionPane;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class MainFrame extends javax.swing.JFrame {
    DateFormat DateFormat = new SimpleDateFormat("YYYY-MM-dd");
    DateFormat DateFormat2 = new SimpleDateFormat("HH:mm:ss");
    DateFormat IdFormat = new SimpleDateFormat("HHmmss");
    Dimension Layar = Toolkit.getDefaultToolkit().getScreenSize();
    Date date = new Date();
    Admin admin = new Admin();
    ImageIcon Icon;
    private int Total;
    private int Ticket;
    private int Harga;
    private int JumlahTiket;
    private String Id;
    private String Query;
    private static Statement statement;
    private static ResultSet result;
    public MainFrame() throws SQLException {
        initComponents();
        DBConnection.RunConnection();
        statement = DBConnection.statement;
        result = DBConnection.result;
        LoadTableUser();
        LoadTableAdmin();
        Konfirmasi.setSize(470, 300);
        Admin.setSize(367, 310);
        RegisterAdmin.setSize(415, 339);
        MainMenuAdmin.setSize(643, 398);
        this.setLocation(Layar.width / 2  - this.getSize().width / 2, Layar.height / 2 - this.getSize().height / 2);
        Konfirmasi.setLocation(Layar.width / 2  - Konfirmasi.getSize().width / 2, Layar.height / 2 - Konfirmasi.getSize().height / 2);
        Admin.setLocation(Layar.width / 2  - Admin.getSize().width / 2, Layar.height / 2 - Admin.getSize().height / 2);
        RegisterAdmin.setLocation(Layar.width / 2  - RegisterAdmin.getSize().width / 2, Layar.height / 2 - RegisterAdmin.getSize().height / 2);
        MainMenuAdmin.setLocation(Layar.width / 2  - MainMenuAdmin.getSize().width / 2, Layar.height / 2 - MainMenuAdmin.getSize().height / 2);
        Icon = new ImageIcon("Icon/logo.jpg");
        setIconImage(Icon.getImage());
        Konfirmasi.setIconImage(Icon.getImage());
        RegisterAdmin.setIconImage(Icon.getImage());
        Admin.setIconImage(Icon.getImage());
        MainMenuAdmin.setIconImage(Icon.getImage());
    }
    
    private void setIcon(){
        setIconImage(Toolkit.getDefaultToolkit().getImage("Image/logo.jpg"));
    }
    
    private void CheckSchedule(){
        Ticket = Integer.parseInt((String) TicketForm.getSelectedItem());
        try{
            Query = "SELECT * FROM jadwal_pemberangkatan WHERE sisa_tiket >= %d AND kelas = '%s' AND tanggal = '%s' AND stasiun_tujuan = '%s' ";
            Query = String.format(Query, Integer.parseInt(TicketForm.getSelectedItem().toString()), ClassForm.getSelectedItem(), Calender.getText(), DestinationForm.getSelectedItem() );
            result = statement.executeQuery(Query);
            OrderTable.setModel(DbUtils.resultSetToTableModel(result));
        }catch(SQLException e){
            e.getSQLState();
        }
    }
    
    private void LoadTableUser() throws SQLException{
        result = statement.executeQuery("SELECT * FROM jadwal_pemberangkatan");
        OrderTable.setModel(DbUtils.resultSetToTableModel(result));
    }
    
    private void LoadTableAdmin() throws SQLException{
        result = statement.executeQuery("SELECT * FROM jadwal_pemberangkatan");
        JadwalTable.setModel(DbUtils.resultSetToTableModel(result));
    }

    private void InsertData() {
        try {
            Query = "INSERT INTO jadwal_pemberangkatan VALUE('%s','%s','%s','%s','%tT','%tT','%d', '%d', '%s')";
            String ID = IdFormat.format(date);
            Harga = Integer.parseInt(PriceForm.getText());
            JumlahTiket = Integer.parseInt(JumlahTiketForm.getText());
            Query = String.format(Query, "PBKT"+ID, "BD - Bandung - Bandung", FormStasiunTujuan.getSelectedItem(), DatePicker.getText(), TimeChooser.getCalendarWithTime(date), TimeChooser2.getCalendarWithTime(date), Harga, JumlahTiket, Class.getSelectedItem());
            if (!statement.execute(Query)) {
                LoadTableAdmin();
                ClearForm();
            } else {
                System.out.println("Gagal");
            }
        } catch (SQLException e) {
            e.getSQLState();
        }
    }
    
    private void DeleteData(){
        try{
            Id = DisplayId.getText();
            Query = String.format("DELETE FROM jadwal_pemberangkatan WHERE id = '%s' ", Id);
             if (!statement.execute(Query)) {
                System.out.println("Data Berhasil Dihapus");
                LoadTableAdmin();
                ClearForm();
            } else {
                System.out.println("Gagal");
            }
        }catch(SQLException e){
            e.getSQLState();
        }
    }
    
    private void UpdateData(){
        try{
            Query = "UPDATE jadwal_pemberangkatan SET stasiun_tujuan = '%s', tanggal = '%s ', waktu_berangkat = '%tT', waktu_sampai = '%tT', harga = %d, sisa_tiket = %d, kelas = '%s' WHERE id = '%s' ";
            Harga = Integer.parseInt(PriceForm.getText());
            JumlahTiket = Integer.parseInt(JumlahTiketForm.getText());
            Query = String.format(Query, FormStasiunTujuan.getSelectedItem(), DatePicker.getText(), TimeChooser.getCalendarWithTime(date), TimeChooser2.getCalendarWithTime(date), Harga, JumlahTiket, Class.getSelectedItem() , DisplayId.getText());
            if (!statement.execute(Query)) {
              System.out.println("Data Berhasil DiUpdate");
              LoadTableAdmin();
              ClearForm();
            } else {
                System.out.println("Gagal");
            }
        }catch(SQLException e){
            e.getSQLState();
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
                        MainMenuAdmin.setVisible(true);
                        Admin.dispose();
                    } else {
                        UsernameError.setText("");
                        PasswordError.setText("Password Anda Salah");
                    }
                } else {
                    PasswordError.setText("");
                    UsernameError.setText("Username Anda Salah");
                }
            } else {
                UsernameError.setText("Username Tidak Ada");
                PasswordError.setText("");
            }
        } catch (SQLException e) {
             e.getSQLState();
        }
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
                    JOptionPane.showMessageDialog(RegisterAdmin, "Register Berhasil", "Success", INFORMATION_MESSAGE);
                    Admin.setVisible(true);
                    RegisterAdmin.dispose();
                } else {
                    System.out.println("Register Gagal");
                }
            }
        } catch (SQLException e) {
           e.getSQLState();
        }
    }
    
    private void ClearForm(){
        try {
            TimeChooser.setTime(DateFormat2.parse("00:00:00"));
            TimeChooser2.setTime(DateFormat2.parse("00:00:00"));
            JumlahTiketForm.setText("");
            DatePicker.setText("");
            PriceForm.setText("");
        } catch (ParseException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setTotal(int Total){
        this.Total = Total;
    }
    
    private int getTotal(){
        return Total;
    }
    
    private String getId(){
        return Id;
    }
    
    private int getJumlahTiket(){
        return JumlahTiket;
    }
    
    public void Ticket() throws SQLException{
        TicketDisplay.setText(TicketDisplay.getText()+"========================================\n");
        TicketDisplay.setText(TicketDisplay.getText()+"================ Tiket  ===================\n");
        TicketDisplay.setText(TicketDisplay.getText()+"Nama      :"+Nama.getText()+"\n");
        TicketDisplay.setText(TicketDisplay.getText()+"Kode      :"+IdFormat.format(date)+"\n");
        TicketDisplay.setText(TicketDisplay.getText()+"KodeTujuan:"+getId()+"\n");
        TicketDisplay.setText(TicketDisplay.getText()+"========================================\n");
        Query = "INSERT INTO pemesanan VALUES('%s','%s','%s','%s','%s', %d)";
        Query = String.format(Query , IdFormat.format(date) , getId() , Nama.getText(), DateFormat.format(date), getTotal(), getJumlahTiket());
        statement.execute(Query);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        RegisterAdmin = new javax.swing.JFrame();
        jPanel2 = new javax.swing.JPanel();
        UsernameRegister = new javax.swing.JTextField();
        RegisterButton = new javax.swing.JLabel();
        PasswordRegister = new javax.swing.JPasswordField();
        BackgrounRegister = new javax.swing.JLabel();
        Home = new javax.swing.JLabel();
        MainMenuAdmin = new javax.swing.JFrame();
        jLabel14 = new javax.swing.JLabel();
        Register = new javax.swing.JLabel();
        TimeChooser = new lu.tudor.santec.jtimechooser.JTimeChooser();
        TimeChooser2 = new lu.tudor.santec.jtimechooser.JTimeChooser();
        FormStasiunTujuan = new javax.swing.JComboBox<>();
        PriceForm = new javax.swing.JTextField();
        JumlahTiketForm = new javax.swing.JTextField();
        DisplayId = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        EditButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        AddButton = new javax.swing.JButton();
        Class = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        ClearButton = new javax.swing.JButton();
        LogoutAdmin = new javax.swing.JLabel();
        DatePicker = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        JadwalTable = new javax.swing.JTable();
        BackgroundAdmin = new javax.swing.JLabel();
        Admin = new javax.swing.JFrame();
        PasswordForm = new javax.swing.JPasswordField();
        UsernameForm = new javax.swing.JTextField();
        UsernameError = new javax.swing.JLabel();
        PasswordError = new javax.swing.JLabel();
        LoginButton = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        HomeAdmin = new javax.swing.JLabel();
        BackgroundLogin = new javax.swing.JLabel();
        Konfirmasi = new javax.swing.JFrame();
        Nama = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        CashField = new javax.swing.JTextField();
        PrintButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TicketDisplay = new javax.swing.JTextArea();
        Error = new javax.swing.JLabel();
        CashCheck = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        entityManager0 = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("db_e_ticket?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        jadwalPemberangkatanQuery = java.beans.Beans.isDesignTime() ? null : entityManager0.createQuery("SELECT j FROM JadwalPemberangkatan j");
        jadwalPemberangkatanList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : jadwalPemberangkatanQuery.getResultList();
        OrderButon = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        OrderTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        CheckButton = new javax.swing.JButton();
        ClassForm = new javax.swing.JComboBox<>();
        DestinationForm = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        TicketForm = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        IdDisplay = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        Calender = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        RefreshButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        LoginAdmin = new javax.swing.JLabel();
        PriceDisplay = new javax.swing.JLabel();
        TotalDisplay = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        RegisterAdmin.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        RegisterAdmin.setTitle("Registrasi Admin");
        RegisterAdmin.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        UsernameRegister.setBackground(new java.awt.Color(75, 187, 244));
        UsernameRegister.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        UsernameRegister.setForeground(new java.awt.Color(255, 255, 255));
        UsernameRegister.setBorder(null);

        RegisterButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        RegisterButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterButtonMouseClicked(evt);
            }
        });

        PasswordRegister.setBackground(new java.awt.Color(75, 187, 244));
        PasswordRegister.setForeground(new java.awt.Color(255, 255, 255));
        PasswordRegister.setBorder(null);

        BackgrounRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/RegisterAdmin.png"))); // NOI18N

        Home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeMouseClicked(evt);
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
                .addComponent(RegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(370, 370, 370)
                .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(BackgrounRegister)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(UsernameRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(PasswordRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(RegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(Home, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BackgrounRegister)
        );

        RegisterAdmin.getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        MainMenuAdmin.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        MainMenuAdmin.setTitle("Main Menu");
        MainMenuAdmin.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Main Menu");
        MainMenuAdmin.getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 0, 100, 20));

        Register.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Register.setText("Create New Admin");
        Register.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Register.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterMouseClicked(evt);
            }
        });
        MainMenuAdmin.getContentPane().add(Register, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 130, 30));
        MainMenuAdmin.getContentPane().add(TimeChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, -1, -1));
        MainMenuAdmin.getContentPane().add(TimeChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, -1, -1));

        FormStasiunTujuan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CN - Cirebon - Cirebon", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        MainMenuAdmin.getContentPane().add(FormStasiunTujuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 150, -1));
        MainMenuAdmin.getContentPane().add(PriceForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 160, 80, 20));
        MainMenuAdmin.getContentPane().add(JumlahTiketForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 160, 80, -1));

        DisplayId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MainMenuAdmin.getContentPane().add(DisplayId, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Tujuan");
        MainMenuAdmin.getContentPane().add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 60, 20));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Sampai");
        MainMenuAdmin.getContentPane().add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, 50, 20));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Berangkat");
        MainMenuAdmin.getContentPane().add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 90, 60, 20));

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Harga");
        MainMenuAdmin.getContentPane().add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 80, 20));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Jumlah Tiket");
        MainMenuAdmin.getContentPane().add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 140, 80, 20));

        EditButton.setText("Update");
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });
        MainMenuAdmin.getContentPane().add(EditButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 90, -1));

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });
        MainMenuAdmin.getContentPane().add(DeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 140, 90, -1));

        AddButton.setText("Add");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });
        MainMenuAdmin.getContentPane().add(AddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 90, -1));

        Class.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ekonomi", "eksekutif", "firstclass" }));
        MainMenuAdmin.getContentPane().add(Class, new org.netbeans.lib.awtextra.AbsoluteConstraints(348, 160, 110, -1));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Kelas");
        MainMenuAdmin.getContentPane().add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 100, 20));

        ClearButton.setText("Clear");
        ClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearButtonActionPerformed(evt);
            }
        });
        MainMenuAdmin.getContentPane().add(ClearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 90, -1));

        LogoutAdmin.setText("Logout");
        LogoutAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LogoutAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutAdminMouseClicked(evt);
            }
        });
        MainMenuAdmin.getContentPane().add(LogoutAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, 30));
        MainMenuAdmin.getContentPane().add(DatePicker, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 90, -1));

        JadwalTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        JadwalTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JadwalTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(JadwalTable);

        MainMenuAdmin.getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 630, 130));

        BackgroundAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin_data.png"))); // NOI18N
        MainMenuAdmin.getContentPane().add(BackgroundAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(-40, -10, 670, 370));

        Admin.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        Admin.setTitle("Login");
        Admin.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PasswordForm.setBackground(new java.awt.Color(76, 188, 246));
        PasswordForm.setForeground(new java.awt.Color(255, 255, 255));
        PasswordForm.setBorder(null);
        Admin.getContentPane().add(PasswordForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 130, 20));

        UsernameForm.setBackground(new java.awt.Color(76, 188, 246));
        UsernameForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        UsernameForm.setForeground(new java.awt.Color(255, 255, 255));
        UsernameForm.setBorder(null);
        UsernameForm.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Admin.getContentPane().add(UsernameForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 130, 20));

        UsernameError.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        UsernameError.setForeground(new java.awt.Color(255, 0, 0));
        UsernameError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Admin.getContentPane().add(UsernameError, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 130, 20));

        PasswordError.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        PasswordError.setForeground(new java.awt.Color(255, 0, 0));
        PasswordError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Admin.getContentPane().add(PasswordError, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 190, 130, 20));

        LoginButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LoginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginButtonMouseClicked(evt);
            }
        });
        Admin.getContentPane().add(LoginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 130, 40));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        HomeAdmin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        HomeAdmin.setForeground(new java.awt.Color(255, 255, 255));
        HomeAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HomeAdmin.setText("Home");
        HomeAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        HomeAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HomeAdminMouseClicked(evt);
            }
        });
        jPanel1.add(HomeAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 30));

        BackgroundLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/LoginAdmin.png"))); // NOI18N
        jPanel1.add(BackgroundLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 280));

        Admin.getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 280));

        Konfirmasi.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        Konfirmasi.setTitle("Konfirmasi Pembayaran");
        Konfirmasi.setResizable(false);
        Konfirmasi.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Nama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaKeyPressed(evt);
            }
        });
        Konfirmasi.getContentPane().add(Nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 120, -1));

        jLabel6.setText("Atas Nama");
        Konfirmasi.getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        jLabel7.setText("Cash");
        Konfirmasi.getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 50, 30));

        CashField.setEnabled(false);
        Konfirmasi.getContentPane().add(CashField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 120, -1));

        PrintButton.setText("Print Tiket");
        PrintButton.setEnabled(false);
        PrintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintButtonActionPerformed(evt);
            }
        });
        Konfirmasi.getContentPane().add(PrintButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 110, 40));

        TicketDisplay.setColumns(20);
        TicketDisplay.setRows(5);
        TicketDisplay.setEnabled(false);
        jScrollPane1.setViewportView(TicketDisplay);

        Konfirmasi.getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, 240, 140));
        Konfirmasi.getContentPane().add(Error, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 140, 120, 20));

        CashCheck.setText("Cek Uang");
        CashCheck.setEnabled(false);
        CashCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CashCheckActionPerformed(evt);
            }
        });
        Konfirmasi.getContentPane().add(CashCheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 100, 40));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("QUIT");
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel5MouseClicked(evt);
            }
        });
        Konfirmasi.getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 30));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin_data.png"))); // NOI18N
        Konfirmasi.getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, -100, -1, -1));

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Jadwal Kereta Api");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        OrderButon.setText("Pesan");
        OrderButon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderButonActionPerformed(evt);
            }
        });
        getContentPane().add(OrderButon, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, 80, 30));

        OrderTable.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N
        OrderTable.setAutoscrolls(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, jadwalPemberangkatanList, OrderTable);
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
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${kelas}"));
        columnBinding.setColumnName("Kelas");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${sisaTiket}"));
        columnBinding.setColumnName("Sisa Tiket");
        columnBinding.setColumnClass(Integer.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        OrderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OrderTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(OrderTable);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 500, 250));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Jadwal Kereta Api");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 420, 40));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Cari Jadwal Kereta Api");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, 310, 40));

        CheckButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        CheckButton.setText("Cek");
        CheckButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CheckButtonActionPerformed(evt);
            }
        });
        getContentPane().add(CheckButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 290, 70, 40));

        ClassForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ClassForm.setForeground(new java.awt.Color(51, 51, 51));
        ClassForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ekonomi", "eksekutif", "firstclass" }));
        getContentPane().add(ClassForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 230, 170, 41));

        DestinationForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        DestinationForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CN - Cirebon - Cirebon", "PSE - Pasar Senen - Jakarta", "ML - Malang - Malang", "SMC - Semarang Poncol - Semarang", "SBI - Surabaya Pasar Turi - Surabaya", "YK - Yogyakarta - Yogyakarta" }));
        getContentPane().add(DestinationForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 130, 170, 41));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tujuan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 90, 140, 40));

        TicketForm.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TicketForm.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3" }));
        getContentPane().add(TicketForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 230, 160, 41));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Kelas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 200, 190, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Jumlah Tiket");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 190, 140, 40));

        IdDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(IdDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, 80, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Harga");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 80, 30));
        getContentPane().add(Calender, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 130, 160, 40));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Tanggal");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 90, 160, 40));

        RefreshButton.setText("Refresh");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });
        getContentPane().add(RefreshButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, 80, 30));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 20, 340));

        LoginAdmin.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LoginAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LoginAdmin.setText("Login Admin");
        LoginAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        LoginAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginAdminMouseClicked(evt);
            }
        });
        getContentPane().add(LoginAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 80, 30));

        PriceDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        PriceDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(PriceDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 254, 90, 30));

        TotalDisplay.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TotalDisplay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(TotalDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 290, 90, 30));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("QUIT");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, -6, 60, 40));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Admin_data_2.png"))); // NOI18N
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(-100, 0, 1220, 340));

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CheckButtonActionPerformed
        CheckSchedule();
    }//GEN-LAST:event_CheckButtonActionPerformed

    private void OrderTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrderTableMouseClicked
        int row = OrderTable.rowAtPoint(evt.getPoint());
        Id = OrderTable.getValueAt(row,0).toString();
        IdDisplay.setText(OrderTable.getValueAt(row,0).toString());
        PriceDisplay.setText(OrderTable.getValueAt(row,6).toString());
        Harga = Integer.parseInt(OrderTable.getValueAt(row,6).toString());
        JumlahTiket = Integer.parseInt(TicketForm.getSelectedItem().toString());
        Total = Harga * JumlahTiket;
        TotalDisplay.setText(String.valueOf(Total));
        setTotal(Total);
    }//GEN-LAST:event_OrderTableMouseClicked

    private void PrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButtonActionPerformed
        try {
            Ticket();
            TicketDisplay.print();
        } catch (PrinterException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException e) {
            e.getSQLState();
        }
    }//GEN-LAST:event_PrintButtonActionPerformed

    private void OrderButonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderButonActionPerformed
        this.dispose();
        Konfirmasi.setVisible(true);
    }//GEN-LAST:event_OrderButonActionPerformed

    private void CashCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CashCheckActionPerformed
        if(Integer.parseInt(CashField.getText()) < getTotal()){
            Error.setText("Uang Kurang");
            CashField.setText("");
            PrintButton.setEnabled(false);
        }else if(Integer.parseInt(CashField.getText()) > getTotal()){
            int kembalian = Integer.parseInt(CashField.getText()) - getTotal();
            Error.setText(String.valueOf(kembalian));
            PrintButton.setEnabled(true);
        }
        else{
            Error.setText("");
            PrintButton.setEnabled(true);
        }
    }//GEN-LAST:event_CashCheckActionPerformed

    private void NamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            Nama.setEnabled(false);
            CashCheck.setEnabled(true);
            CashField.setEnabled(true);
        }
    }//GEN-LAST:event_NamaKeyPressed

    private void LoginButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginButtonMouseClicked
        Login();
    }//GEN-LAST:event_LoginButtonMouseClicked

    private void RegisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterMouseClicked
        RegisterAdmin.setVisible(true);
        MainMenuAdmin.dispose();
    }//GEN-LAST:event_RegisterMouseClicked

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        UpdateData();
    }//GEN-LAST:event_EditButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        DeleteData();
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        InsertData();
    }//GEN-LAST:event_AddButtonActionPerformed

    private void ClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearButtonActionPerformed
        ClearForm();
    }//GEN-LAST:event_ClearButtonActionPerformed

    private void LogoutAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutAdminMouseClicked
        MainMenuAdmin.dispose();
        Admin.setVisible(true);
    }//GEN-LAST:event_LogoutAdminMouseClicked

    private void JadwalTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JadwalTableMouseClicked
        ClearForm();
        int row = JadwalTable.rowAtPoint(evt.getPoint());
        Id = JadwalTable.getValueAt(row, 0).toString();
        String StasiunTujuan = JadwalTable.getValueAt(row, 2).toString();
        String Tanggal = JadwalTable.getValueAt(row, 3).toString();
        String WaktuBerangkat = JadwalTable.getValueAt(row, 4).toString();
        String WaktuSampai = JadwalTable.getValueAt(row, 5).toString();
        String HargaTiket = JadwalTable.getValueAt(row, 6).toString();
        String JumlahTiketUser = JadwalTable.getValueAt(row, 7).toString();
        String Kelas = JadwalTable.getValueAt(row, 8).toString();
        try {
            Date Time1 = new SimpleDateFormat("HH:mm:ss").parse(WaktuBerangkat);
            Date Time2 = new SimpleDateFormat("HH:mm:ss").parse(WaktuSampai);
            TimeChooser.setTime(Time1);
            TimeChooser2.setTime(Time2);
        } catch (ParseException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
        DatePicker.setText(Tanggal);
        FormStasiunTujuan.setSelectedItem(StasiunTujuan);
        JumlahTiketForm.setText(JumlahTiketUser);
        Class.setSelectedItem(Kelas);
        PriceForm.setText(HargaTiket);
        DisplayId.setText(Id);
    }//GEN-LAST:event_JadwalTableMouseClicked

    private void RegisterButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RegisterButtonMouseClicked
        Register();
    }//GEN-LAST:event_RegisterButtonMouseClicked

    private void HomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeMouseClicked
        MainMenuAdmin.setVisible(true);
        RegisterAdmin.dispose();
    }//GEN-LAST:event_HomeMouseClicked

    private void HomeAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HomeAdminMouseClicked
        Admin.dispose();
        this.setVisible(true);
    }//GEN-LAST:event_HomeAdminMouseClicked

    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        try {
            LoadTableUser();
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void LoginAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginAdminMouseClicked
        this.dispose();
        Admin.setVisible(true);
    }//GEN-LAST:event_LoginAdminMouseClicked

    private void jLabel5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel5MouseClicked
        Konfirmasi.dispose();
        this.setVisible(true);
    }//GEN-LAST:event_jLabel5MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel15MouseClicked

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new MainFrame().setVisible(true);
            } catch (SQLException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JFrame Admin;
    private javax.swing.JLabel BackgrounRegister;
    private javax.swing.JLabel BackgroundAdmin;
    private javax.swing.JLabel BackgroundLogin;
    private javax.swing.JTextField Calender;
    private javax.swing.JButton CashCheck;
    private javax.swing.JTextField CashField;
    private javax.swing.JButton CheckButton;
    private javax.swing.JComboBox<String> Class;
    private javax.swing.JComboBox<String> ClassForm;
    private javax.swing.JButton ClearButton;
    private javax.swing.JTextField DatePicker;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JComboBox<String> DestinationForm;
    private javax.swing.JLabel DisplayId;
    private javax.swing.JButton EditButton;
    private javax.swing.JLabel Error;
    private javax.swing.JComboBox<String> FormStasiunTujuan;
    private javax.swing.JLabel Home;
    private javax.swing.JLabel HomeAdmin;
    private javax.swing.JLabel IdDisplay;
    private javax.swing.JTable JadwalTable;
    private javax.swing.JTextField JumlahTiketForm;
    private javax.swing.JFrame Konfirmasi;
    private javax.swing.JLabel LoginAdmin;
    private javax.swing.JLabel LoginButton;
    private javax.swing.JLabel LogoutAdmin;
    private javax.swing.JFrame MainMenuAdmin;
    private javax.swing.JTextField Nama;
    private javax.swing.JButton OrderButon;
    private javax.swing.JTable OrderTable;
    private javax.swing.JLabel PasswordError;
    private javax.swing.JPasswordField PasswordForm;
    private javax.swing.JPasswordField PasswordRegister;
    private javax.swing.JLabel PriceDisplay;
    private javax.swing.JTextField PriceForm;
    private javax.swing.JButton PrintButton;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JLabel Register;
    private javax.swing.JFrame RegisterAdmin;
    private javax.swing.JLabel RegisterButton;
    private javax.swing.JTextArea TicketDisplay;
    private javax.swing.JComboBox<String> TicketForm;
    private lu.tudor.santec.jtimechooser.JTimeChooser TimeChooser;
    private lu.tudor.santec.jtimechooser.JTimeChooser TimeChooser2;
    private javax.swing.JLabel TotalDisplay;
    private javax.swing.JLabel UsernameError;
    private javax.swing.JTextField UsernameForm;
    private javax.swing.JTextField UsernameRegister;
    private javax.persistence.EntityManager entityManager0;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private java.util.List<e.krl.JadwalPemberangkatan> jadwalPemberangkatanList;
    private javax.persistence.Query jadwalPemberangkatanQuery;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
