/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 *
 * @author user
 */
public class Requisicao {
    
  private static final String NETDATA = "[netData]";
  private static final String HOST = "[host]";
  private static final String PORT = "[port]";
  private static final String HOST_PORT = "[host_port]";
  private static final String PROTOCOL = "[protocol]";
  private static final String NEW_LINE = "[crlf]";
  private String metodo;
  private String host;
  private String port;
  private String hostPort;
  private String protocolo;
  private Map<String, String> headers;
  private String payload;
  private String strRequisicao;
  
  public Requisicao() {}
  
  public String getMetodo()
  {
    return metodo;
  }
  
  public void setMetodo(String metodo) {
    this.metodo = metodo;
  }
  
  public String getHost() {
    return host;
  }
  
  public void setHost(String host) {
    this.host = host;
  }
  
  public String getPort() {
    return port;
  }
  
  public void setPort(String port) {
    this.port = port;
  }
  
  public String getProtocolo() {
    return protocolo;
  }
  
  public void setProtocolo(String protocolo) {
    this.protocolo = protocolo;
  }
  
  public Map<String, String> getHeaders() {
    return headers;
  }
  
  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }
  
  public String getPayload() {
    return payload;
  }
  
  public void setPayload(String payload) {
    this.payload = payload;
  }
  
  public String getHeaderVal(String header) {
    String val = null;
    
    for (String h : headers.keySet()) {
      if (h.equalsIgnoreCase(header)) {
        val = (String)headers.get(h);
        break;
      }
    }
    
    return val;
  }
  
  public String getStrRequisicao() {
    if (payload != null) {
      parsePayload(payload);
    }
    
    return strRequisicao;
  }
  
  public void parsePayload(String payload) {
    if ((metodo != null) && (host != null) && (port != null) && (protocolo != null)) {
      payload = payload.replace("[netData]", String.format("%s %s %s", new Object[] { metodo, hostPort, protocolo }));
    }
    if (host != null) {
      payload = payload.replace("[host]", host);
    }
    if (port != null) {
      payload = payload.replace("[port]", port);
    }
    if ((host != null) && (port != null)) {
      payload = payload.replace("[host_port]", getHostWithPort());
    }
    if (protocolo != null) {
      payload = payload.replace("[protocol]", protocolo);
    }
    payload = payload.replace("[crlf]", "\r\n");
    
    strRequisicao = payload;
  }
  
  public void parseRequisicaoStr(String strReq) {
    if (strReq.length() == 0) {
      return;
    }
    Scanner scanner = new Scanner(strReq);
    metodo = scanner.next();
    hostPort = scanner.next();
    String[] hostAndPort = getHostAndPort();
    host = hostAndPort[0];
    port = hostAndPort[1];
    protocolo = scanner.next();
    headers = new HashMap();
    

    while (scanner.hasNext()) {
      String headerKey = scanner.next();
      headerKey = headerKey.substring(0, headerKey.length() - 1);
      
      if (!scanner.hasNextLine()) break;
      String headerValue = scanner.nextLine().substring(1);
      

      headers.put(headerKey, headerValue);
    }
    
    strRequisicao = strReq;
    
    scanner.close();
  }
  
  public String makeRequisicao() {
    StringBuilder builder = new StringBuilder();
    
    builder.append(String.format("%s %s %s\r\n", new Object[] { metodo, hostPort, protocolo }));
    
    for (String headerKey : headers.keySet()) {
      builder.append(String.format("%s: %s\r\n", new Object[] { headerKey, headers.get(headerKey) }));
    }
    builder.append("\r\n");
    
    strRequisicao = builder.toString();
    
    return strRequisicao;
  }
  
  private String[] getHostAndPort()
  {
    String[] ret = new String[2];
    
    if ((hostPort.length() > 7) && (hostPort.substring(0, 4).equals("http"))) {
      ret[0] = hostPort.substring(hostPort.indexOf(47) + 2);
    } else {
      ret[0] = hostPort;
    }
    
    if (ret[0].contains(":")) {
      String str = ret[0];
      ret[0] = ret[0].substring(0, ret[0].indexOf(58));
      ret[1] = str.substring(str.indexOf(58) + 1);
      if (ret[1].contains("/")) {
        ret[1] = ret[1].substring(0, ret[1].indexOf(47));
      }
    }
    
    return ret;
  }
  
  private String getHostWithPort() {
    if (port != null) {
      return String.format("%s:%s", new Object[] { host, port });
    }
    
    return host;
  }
}
