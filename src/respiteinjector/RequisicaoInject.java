/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 *
 * @author user
 */
public class RequisicaoInject implements Runnable, Loggable {

    public static final int TAM_BUFFER_RECEPCAO = 4096;
    public static final int TAM_BUFFER_ENVIO = 4096;
    private static final String CONNECT_ESTABLISHED = "200";
    public static final String CONTENT_LENGTH = "Content-Length";
    private int id;
    private Host hostDest;
    private Host hostCliente;
    private String payload;

    public RequisicaoInject() {
    }

    public RequisicaoInject(Host hostDest, Host hostCliente) {
        this.hostDest = hostDest;
        this.hostCliente = hostCliente;
    }

    public RequisicaoInject(Host hostDest, Host hostCliente, int id) {
        this(hostDest, hostCliente);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Host getHostDest() {
        return hostDest;
    }

    public void setHostDest(Host hostDest) {
        this.hostDest = hostDest;
    }

    public Host getHostCliente() {
        return hostCliente;
    }

    public void setHostCliente(Host hostCliente) {
        this.hostCliente = hostCliente;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public void run() {
        ConnectMaker connect = null;
        try {
            //hostDest = PROXY SERVER
            //hostCliente = Local Server Interface
            try {
                Requisicao reqCliente = getRequisicao(hostCliente);
                reqCliente.setPayload(payload);
                new GlobalVars().logThis("[INJ] Opening proxy communication.");

                hostDest.writeStreamSplited(reqCliente.getStrRequisicao(), "[split]");

                int bodyLen;
                if ((bodyLen = getContentLength(reqCliente)) > 0) {
                    hostDest.writeStreamQtdBytes(hostCliente.getIn(), bodyLen, 4096);
                }
                String respostaDestino = hostDest.getHttpHead();
                String statusLine = respostaDestino.substring(0, respostaDestino.indexOf('\r'));
                try {
                    int StatusLine = Integer.parseInt(statusLine.split(" ")[1]);
                    if (StatusLine >= 200 && StatusLine <=299)
                    {
                    new GlobalVars().logThis("[INJ] Status line: " + statusLine, 2);
                    }
                    else 
                    {
                    new GlobalVars().logThis("[INJ] Status line: " + statusLine, -1);
                    new GlobalVars().logThis("[INJ] Sending HTTP/1.0 200 OK", 0);
                    }
                } catch (Exception ex2) {
                    new GlobalVars().logThis("[INJ] Status line: " + statusLine, 1);
                }
                hostCliente.writeStream("HTTP/1.0 200 OK\r\n\r\n");

                if (true) { // HTTP PROXY SENT A 200, PAYLOAD HAS BEEN SENT, BEGIN READ WRITE

                    new GlobalVars().logThis("[INJ] Server connected on Thread ID " + id);
                    connect = new ConnectMaker(hostDest, hostCliente) {
                        public void onLogReceived(String log, int level, Exception e) {
                            RequisicaoInject.this.onLogReceived(log, level, e);
                        }
                    };
                    connect.setId(id);
                    connect.setTamBufferEnvio(4096);
                    connect.setTamBufferRecepcao(4096);
                    connect.run();
                } else if ((bodyLen = getContentLength(respostaDestino)) > 0) {
                    hostCliente.writeStreamQtdBytes(hostDest.getIn(), bodyLen, 4096);
                }
            } finally {
                if (connect == null) {
                    hostDest.close();
                    hostCliente.close();
                    new GlobalVars().logThis("[INJ] Connection died.", -1);

                }
            }
        } catch (UnknownHostException e) {
            new GlobalVars().logThis("[INJ] Unknown host error (check DNS or internet?).", -1);

        } catch (Exception e) {
            new GlobalVars().logThis("[INJ] " + e.getMessage(), -1);

        }
    }

    private Requisicao getRequisicao(Host host) throws IOException {
        Requisicao reqCliente = new Requisicao();
        try {

            reqCliente.parseRequisicaoStr(host.getHttpHead());
            reqCliente.setPayload(payload);
        } catch (IOException e) {

            new GlobalVars().logThis("Error while recieving client request: " + e.getMessage());
        }

        return reqCliente;
    }

    private int getContentLength(Requisicao req) {
        String str = req.getHeaderVal("Content-Length");

        if (str == null) {
            return -1;
        }
        try {
            return Integer.valueOf(str).intValue();
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    private int getContentLength(String str) {
        String cl = String.format("\r\n%s: ", new Object[]{"Content-Length"});

        int i = str.indexOf(cl);

        if (i == -1) {
            return -1;
        }
        int f = str.indexOf("\r\n", i + 2);

        if (f == -1) {
            return -1;
        }
        try {
            return Integer.valueOf(str.substring(i + cl.length(), f)).intValue();
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    public void onLogReceived(String log, int level, Exception e) {
    }
}
