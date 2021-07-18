/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respite.http.injector;

/**
 *
 * @author user
 */
public class Config {
    public double Version = 2020.3;
    public String Payload = ""; // base 64, encrypted
    public String Proxy = ""; 
    public String SSH_Host = ""; 
    public String SSH_Port = "";
    public String SSH_Username = "";
    public String SSH_Password = ""; 
    public String LocalProxyInterface_Host = "";
    public String LocalProxyInterface_Port = "";
    public String LocalSSHInterface_Host = "";
    public String LocalSSHInterface_Port = "";
    public boolean AllowSSH = true;
    public boolean Encrypted = false;
}
