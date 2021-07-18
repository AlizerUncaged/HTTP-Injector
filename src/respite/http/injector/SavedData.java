/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respite.http.injector;

import java.util.Hashtable;

/**
 *
 * @author user
 */
public class SavedData {
    public double Version = 2020.3;
    public String Payload = "CONNECT [host_port] HTTP/1.1[crlf][crlf]";
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
 public String PayloadHost = "";
 public Hashtable<String, Boolean> PayloadCheckBoxes = new Hashtable<String, Boolean>();
 public Hashtable<String, Boolean> RadioButtons = new Hashtable<String, Boolean>();
 public Hashtable<String, String> TextFields = new Hashtable<String, String>();
 public Hashtable<String, Integer> Comboboxes = new Hashtable<String, Integer>();
}
