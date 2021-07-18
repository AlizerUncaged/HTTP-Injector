/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respite.http.injector;

import java.io.IOException;
import com.trilead.ssh2.ProxyData;
import com.trilead.ssh2.HTTPProxyData;
import com.trilead.ssh2.DynamicPortForwarder;
import com.trilead.ssh2.Connection;
import com.trilead.ssh2.ConnectionInfo;
import com.trilead.ssh2.Session;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;
import respiteinjector.GlobalVars;

/**
 *
 * @author user
 */
public class SSHCon {

    private String host;
    private int port;
    private String user;
    private String pass;
    private int socksPort;
    private String proxyHost;
    private int proxyPort;
    private boolean isRunning;
    private Connection conexao;
    private DynamicPortForwarder dpf;

    public SSHCon(String host_, int port_, String user_, String pass_, String proxyhost_, int proxyport_, int openport) {
        this.host = host_;
        this.port = port_;
        this.user = user_;
        this.pass = pass_;
        this.proxyHost = proxyhost_;
        this.proxyPort = proxyport_;
        this.socksPort = openport;
        isRunning = true;
    }

    public void close() {
        conexao.close();
        isRunning = false;
    }

    public void run() {
        new GlobalVars().logThis("[SSH] SSH Starting...");
        this.conexao = new Connection(this.host, this.port);
        conexao.setProxyData(new HTTPProxyData(this.proxyHost, this.proxyPort));
        try {
            ConnectionInfo v = conexao.connect();
            new GlobalVars().logThis("[SSH] Connection Info: \nClient to server cryptography algorithm: " + v.clientToServerCryptoAlgorithm, 3);
            new GlobalVars().logThis("Client to server MAC algorithm: " + v.clientToServerMACAlgorithm, 3);
            new GlobalVars().logThis("Key exchange algorithm: " + v.keyExchangeAlgorithm, 3);
            new GlobalVars().logThis("Server Host Key algorithm: " + v.serverHostKeyAlgorithm, 3);
            new GlobalVars().logThis("Server to Client algorithm: " + v.serverToClientCryptoAlgorithm, 3);
            new GlobalVars().logThis("Server to Client MAC algorithm: " + v.serverToClientMACAlgorithm, 3);
            new GlobalVars().logThis("[SSH] Successfully connected to server, authenticating...", 1);
        } catch (Exception ex) {
            new GlobalVars().logThis("[SSH] " + ex.getMessage(), -1);
            return;
        }
        boolean success = false;
        try {
            success = this.conexao.authenticateWithPassword(this.user, this.pass);
        } catch (IOException e2) {
            new GlobalVars().logThis("[SSH] Failed authenticating (prematurely), please check if your username and password is still valid...", -1);
            this.conexao.close();
            return;
        }
        if (!success) {
            new GlobalVars().logThis("[SSH] Failed authenticating, please check if your username and password is still valid...", -1);
            this.conexao.close();
            return;
        } else if (success) {
            new GlobalVars().logThis("[SSH] Authentication success!", 2);
            
            try {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            
                            
                            
                        } catch (Exception ed) {
                            System.out.println(ed);
                        }
                        
                    }
                }).start();

            } catch (Exception ed) {
            }
        }
        if (this.socksPort != 0) {
            try {
                dpf = conexao.createDynamicPortForwarder(socksPort);
            } catch (IOException e2) {
                new GlobalVars().logThis("[SSH] Failed creating SOCKS forwarder on port " + String.valueOf(socksPort), -1);
                new GlobalVars().logThis("[SSH] Error " + e2.getMessage(), -1);
                this.conexao.close();
                return;
            }
        }

        new GlobalVars().logThis("[SSH] Success! SOCKS Forwarder opened on port " + String.valueOf(socksPort) + " of 127.0.0.1", 2);
    }
}
