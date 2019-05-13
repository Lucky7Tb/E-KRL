package e.krl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.UnsupportedEncodingException;

public class Admin {
    private String Password;
    private String Username;
   
    public void setPassword(String Password){
         try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(Password.getBytes("UTF-8"));
            //merubah byte array ke dalam String Hexadecimal
            StringBuilder sb = new StringBuilder(2*hash.length);
            for(byte b : hash){
                sb.append(String.format("%02x", b&0xff));
            }
            this.Password = sb.toString();
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException ex){
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setUsername(String Username){
        this.Username = Username;
    }
    
    public String getUsername(){
        return Username;
    }
    
    public String getPassword(){
        return Password;
    }
}
