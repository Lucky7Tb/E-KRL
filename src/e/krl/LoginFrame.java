package e.krl;

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFrame extends javax.swing.JFrame {
    protected static Statement statement;
    protected static ResultSet result;
    private String Query;
    Admin admin = new Admin();
    
    public LoginFrame() {
        initComponents();
        DBConnection.RunConnection();
        statement = DBConnection.statement;
        result = DBConnection.result;
        RegisterFrame.setSize(400, 300);
    }
    
    private void Register(){
        admin.setUsername(UsernameRegister.getText().toLowerCase());
        admin.setPassword(PasswordRegister.getText());
        try{
            Query = String.format("SELECT username FROM admin WHERE username = '%s'",admin.getUsername());
            result = statement.executeQuery(Query);            
            if(result.next()){
                System.out.println("Username Sudah Ada");
                UsernameRegister.setText("");
                PasswordRegister.setText("");
            }else{
                Query = "INSERT INTO admin (username,password) VALUE ('%s','%s')";
                Query = String.format(Query, admin.getUsername(), admin.getPassword());
                if(!statement.execute(Query)){
                    System.out.println("Register Berhasil");
                    new LoginFrame().setVisible(true);
                    RegisterFrame.dispose();
                }else{
                    System.out.println("Register Gagal");
                }
            }
        }catch(SQLException error){
            System.out.println(error.getSQLState());
        }
    }
    
    private void Login(){
        admin.setUsername(UsernameForm.getText().toLowerCase());
        admin.setPassword(PasswordForm.getText());
        try{
            Query = String.format("SELECT username, password FROM admin WHERE username  = '%s' ", admin.getUsername());
            result = statement.executeQuery(Query);
            if(result.next()){
                if(result.getString("username").equals(admin.getUsername())){
                    if(result.getString("password").equals(admin.getPassword())){
                        System.out.println("Anda Behasil Login");
                    }else{
                        System.out.println("Password Anda Salah");
                    }
                }else{
                    System.out.println("Username Anda Salah");
                }
            }else{
                System.out.println("Username Tidak Ada");
            }
        }catch(SQLException error){
            System.out.println(error.getSQLState());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RegisterFrame = new javax.swing.JFrame();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        UsernameRegister = new javax.swing.JTextField();
        RegisterButton = new javax.swing.JButton();
        PasswordRegister = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        UsernameForm = new javax.swing.JTextField();
        LoginButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        PasswordForm = new javax.swing.JPasswordField();

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

        jLabel4.setText("Don\"t Have Account?");
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, -1, 30));
        getContentPane().add(PasswordForm, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 120, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
    RegisterFrame.setVisible(true);
    this.dispose();
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

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });      
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LoginButton;
    private javax.swing.JPasswordField PasswordForm;
    private javax.swing.JPasswordField PasswordRegister;
    private javax.swing.JButton RegisterButton;
    private javax.swing.JFrame RegisterFrame;
    private javax.swing.JTextField UsernameForm;
    private javax.swing.JTextField UsernameRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    // End of variables declaration//GEN-END:variables

}
