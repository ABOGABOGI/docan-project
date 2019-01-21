package fw.docan.docan;

import android.util.Base64;

/**
 * Created by Faishal Wahiduddin on 3/13/2018.
 */

public class User {

    private String id,username, email,token,phone,balance,kirim;



    public User( String username, String email, String token, String phone, String balance) {
        this.username = encrypt(username);
        this.email = encrypt(email);
        this.token = encrypt(token);
        this.phone = encrypt(phone);
        this.balance = encrypt(balance);
    }

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User(String id, String username, String email, String token, String phone, String balance) {
        this.id = encrypt(id);
        this.username = encrypt(username);
        this.email = encrypt(email);
        this.token = encrypt(token);
        this.phone = encrypt(phone);
        this.balance = encrypt(balance);
    }

    public String getId() {
        return decrypt(id);
    }

    public String getUsername() {
        return decrypt(username);
    }

    public String getEmail() {
        return decrypt(email);
    }

    public String getToken() {
        return decrypt(token);
    }

    public String getPhone() {
        return decrypt(phone);
    }

    public String getBalance() {
        return decrypt(balance);
    }
    public String getKirim() {
        return decrypt(kirim);
    }

    public void setBalance(String balance) {
        this.balance = encrypt(balance);
    }

    public void setKirim(String kirim) {
        this.kirim = encrypt(kirim);
    }

    public static String encrypt(String input) {
        // This is base64 encoding, which is not an encryption
        if(input != null && !input.isEmpty())
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
        else
            return input;
        //
    }

    public static String decrypt(String input) {
        if(input != null && !input.isEmpty())
        return new String(Base64.decode(input, Base64.DEFAULT));
        else
            return input;
        //return input;
    }

}
