/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;

/**
 *
 * @author user
 */
public class Config {
    
  public static final String FILE_PATCH = "injector.jar.config";
  public static final String PAYLOAD = "Payload";
  public static final String RPROXY = "rProxy";
  public static final String LISTEN = "Listen";
  public static final String SSH_HOST = "SSH_Host";
  public static final String SSH_PORT = "SSH_Port";
  public static final String SSH_USER = "SSH_USER";
  public static final String SSH_PASS = "SSH_Pass";
  public static final String USAR_SSH = "USAR_SSH";
  public static final String SOCKS_PORT = "SOCKS_Port";
  public static final String LOCK_SSH = "LOCK_SSH";
  public static final String LOCK_PAYLOAD = "LOCK_PAYLOAD";
  public static final String LOCK_RPROXY = "LOCK_RPROXY";
  public static final String DEFAULT_LISTEN = "127.0.0.1:8989";
  public static final String DEFAULT_RPROXY = "";
  public static final String DEFAULT_PAYLOAD = "";
  public static final String DEFAULT_SSH_HOST = "";
  public static final int DEFAULT_SSH_PORT = 443;
  public static final String DEFAULT_SSH_USER = "";
  public static final String DEFAULT_SSH_PASS = "";
  public static final int DEFAULT_SOCKS_PORT = 1080;
  public static final boolean DEFAULT_USAR_SSH = false;
  public static final boolean DEFAULT_LOCK_SSH = false;
  public static final boolean DEFAULT_LOCK_PAYLOAD = false;
  public static final boolean DEFAULT_LOCK_RPROXY = false;
  private String listenAddr;
  private String rProxy;
  private String payload;
  private String sshHost;
  private int sshPort;
  private String sshUser;
  private String sshPass;
  private int socksPort;
  private boolean useSSH;
  private boolean sshLocked;
  private boolean payloadLocked;
  private boolean rproxyLocked;
  
  public Config()
  {
    listenAddr = "127.0.0.1:8989";
    rProxy = "";
    payload = "";
    sshHost = "";
    sshPort = 443;
    sshUser = "";
    sshPass = "";
    socksPort = 1080;
    useSSH = false;
    sshLocked = false;
    rproxyLocked = false;
    payloadLocked = false;
  }
  
  public String getListenAddr() {
    return listenAddr;
  }
  
  public void setListenAddr(String listenAddr) {
    this.listenAddr = listenAddr;
  }
  
  public String getrProxy() {
    return rProxy;
  }
  
  public void setrProxy(String rProxy) {
    this.rProxy = rProxy;
  }
  
  public String getPayload() {
    return payload;
  }
  
  public void setPayload(String payload) {
    this.payload = (payload != null ? payload.replaceAll("\n", "") : null);
  }
  
  public String getSshHost() {
    return sshHost;
  }
  
  public void setSshHost(String sshHost) {
    this.sshHost = sshHost;
  }
  
  public int getSshPort() {
    return sshPort;
  }
  
  public void setSshPort(int sshPort) {
    this.sshPort = sshPort;
  }
  
  public String getSshUser() {
    return sshUser;
  }
  
  public void setSshUser(String sshUser) {
    this.sshUser = sshUser;
  }
  
  public String getSshPass() {
    return sshPass;
  }
  
  public void setSshPass(String sshPass) {
    this.sshPass = sshPass;
  }
  
  public int getSocksPort() {
    return socksPort;
  }
  
  public void setSocksPort(int socksPort) {
    this.socksPort = socksPort;
  }
  
  public String getListenHost() {
    return getHost(listenAddr);
  }
  
  public int getListenPort() {
    return getPort(listenAddr);
  }
  
  public String getProxyHost() {
    return getHost(rProxy);
  }
  
  public int getProxyPort() {
    return getPort(rProxy);
  }
  
  public boolean isUseSSH() {
    return useSSH;
  }
  
  public void setUseSSH(boolean useSSH) {
    this.useSSH = useSSH;
  }
  
  public boolean isSshLocked() {
    return sshLocked;
  }
  
  public void setSshLocked(boolean sshLocked) {
    this.sshLocked = sshLocked;
  }
  
  public boolean isPayloadLocked() {
    return payloadLocked;
  }
  
  public void setPayloadLocked(boolean payloadLocked) {
    this.payloadLocked = payloadLocked;
  }
  
  public boolean isRproxyLocked() {
    return rproxyLocked;
  }
  
  public void setRproxyLocked(boolean rproxyLocked) {
    this.rproxyLocked = rproxyLocked;
  }
  
  private String getHost(String hostPort)
  {
    return hostPort.substring(0, hostPort.indexOf(":"));
  }
  
  private int getPort(String hostPort) throws NumberFormatException {
    String str = hostPort.substring(hostPort.indexOf(":") + 1);
    
    return Integer.parseInt(str);
  }
}
