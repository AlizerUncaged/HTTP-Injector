/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author user
 */
public class FileConfig  extends Config {
    
  private String filePath;
  
  public FileConfig()
  {
    filePath = "injector.jar.config";
  }
  
  public void save() throws FileNotFoundException, IOException {
    Properties props = new Properties();
    
    props.setProperty("Listen", getListenAddr() == null ? "" : getListenAddr());
    props.setProperty("rProxy", getrProxy() == null ? "" : getrProxy());
    props.setProperty("Payload", getPayload() == null ? "" : getPayload());
    props.setProperty("SSH_Host", getSshHost() == null ? "" : getSshHost());
    props.setProperty("SSH_Port", String.valueOf(getSshPort()));
    props.setProperty("SSH_USER", getSshUser() == null ? "" : getSshUser());
    props.setProperty("SSH_Pass", getSshPass() == null ? "" : getSshPass());
    props.setProperty("SOCKS_Port", String.valueOf(getSocksPort()));
    props.setProperty("USAR_SSH", isUseSSH() ? "1" : "0");
    props.setProperty("LOCK_SSH", isSshLocked() ? "1" : "0");
    props.setProperty("LOCK_PAYLOAD", isPayloadLocked() ? "1" : "0");
    props.setProperty("LOCK_RPROXY", isRproxyLocked() ? "1" : "0");
    

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    props.store(baos, null);
    
    FileOutputStream file = new FileOutputStream(filePath);
    file.write(Embaralhador.embaralhar(baos.toByteArray()));
    file.close();
  }
  
  public void load() throws FileNotFoundException, IOException {
    Properties props = new Properties();
    
    FileInputStream file = new FileInputStream(filePath);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeStream(file, baos, 8192);
    file.close();
    
    props.load(new ByteArrayInputStream(Embaralhador.desembaralhar(baos.toByteArray())));
    

    setListenAddr(props.getProperty("Listen", "127.0.0.1:8989"));
    setrProxy(props.getProperty("rProxy", ""));
    setPayload(props.getProperty("Payload", ""));
    setSshHost(props.getProperty("SSH_Host", ""));
    try
    {
      setSshPort(Integer.valueOf(props.getProperty("SSH_Port", String.valueOf(443))).intValue());
    } catch (NumberFormatException e) {
      setSshPort(443);
    }
    
    setSshUser(props.getProperty("SSH_USER", ""));
    setSshPass(props.getProperty("SSH_Pass", ""));
    try
    {
      setSocksPort(Integer.valueOf(props.getProperty("SOCKS_Port", String.valueOf(1080))).intValue());
    } catch (NumberFormatException e) {
      setSocksPort(1080);
    }
    
    setUseSSH(isTrue(props.getProperty("USAR_SSH"), false));
    setSshLocked(isTrue(props.getProperty("LOCK_SSH"), false));
    setRproxyLocked(isTrue(props.getProperty("LOCK_RPROXY"), false));
    setPayloadLocked(isTrue(props.getProperty("LOCK_PAYLOAD"), false));
  }
  
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }
  
  private boolean isTrue(String str, boolean defaultValue)
  {
    if ((str == null) || (str.length() == 0)) {
      return defaultValue;
    }
    return str.charAt(0) == '1';
  }
  
  private void writeStream(InputStream in, OutputStream out, int tamBuffer) throws IOException {
    byte[] buffer = new byte[tamBuffer];
    
    int len;
    while ((len = in.read(buffer)) != -1) {
      out.write(buffer, 0, len);
    }
  }
}
