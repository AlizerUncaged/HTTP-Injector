/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author user
 */
public class ConnectMaker implements Loggable
{
  private int id;
  private int tamBufferEnvio = 4096;
  private int tamBufferRecepcao = 4096;
  private Host destino;
  private Host cliente;
  private boolean isThread1Closed;
  private boolean isThread2Closed;
  
  public ConnectMaker() {}
  
  public ConnectMaker(Host destino, Host cliente) throws IOException
  {
    this.cliente = cliente;
    this.destino = destino;
  }
  
  public int getTamBufferEnvio() {
    return tamBufferEnvio;
  }
  
  public void setTamBufferEnvio(int tamBufferEnvio) {
    this.tamBufferEnvio = tamBufferEnvio;
  }
  
  public int getTamBufferRecepcao() {
    return tamBufferRecepcao;
  }
  
  public void setTamBufferRecepcao(int tamBufferRecepcao) {
    this.tamBufferRecepcao = tamBufferRecepcao;
  }
  
  public Host getDestino() {
    return destino;
  }
  
  public void setDestino(Host destino) {
    this.destino = destino;
  }
  
  public Host getCliente() {
    return cliente;
  }
  
  public void setCliente(Host cliente) {
    this.cliente = cliente;
  }
  
  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  

  public void run()
  {
    run(cliente, destino, tamBufferEnvio);
    
    run(destino, cliente, tamBufferRecepcao);
  }
  private void run(final Host hostIn, final Host hostOut, final int tamBuffer)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          InputStream in = hostIn.getIn();
          OutputStream out = hostOut.getOut();
          Socket inSocket = hostIn.getSocket();
          Socket outSocket = hostOut.getSocket();
          
          byte[] buffer = new byte[tamBuffer];
          int len = in.read(buffer);
          
          while ((len != -1) && (!inSocket.isOutputShutdown())) {
            out.write(buffer, 0, len);
            out.flush();
            len = in.read(buffer);
          }
          
          if ((len == -1) || (inSocket.isOutputShutdown())) {
            if (!isThread1Closed) {
              isThread1Closed = true;
            } else {
              isThread2Closed = true;
            }
            inSocket.shutdownInput();
            outSocket.shutdownOutput();
          }
        } catch (IOException e) {
             new  GlobalVars().logThis("[INJ] Thread " + getId() + ": error in data transfer. " + e.getMessage(), -1);
      
        } finally {
          if (isThread2Closed) {
            try {
              destino.close();
              cliente.close();
            } catch (IOException e) {
                     new  GlobalVars().logThis("[INJ] Thread " + getId() + ": error in closing communication. " + e.getMessage(), -1);
    
            }
              new  GlobalVars().logThis("<-> Thread " + getId() + ": connection terminated.");
         
          }
        }
      }
    })
    







































      .start();
  }
  
  public void onLogReceived(String log, int level, Exception e) {}
}
