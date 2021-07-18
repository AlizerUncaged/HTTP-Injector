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
public class Host {

    public static final String SPLIT_MACRO = "\\[split\\]";
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private String addr;
    private int port;

    public Host(Socket socket) {
        this.socket = socket;
        addr = socket.getInetAddress().getHostAddress();
        port = socket.getPort();
    }

    public Host(String addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getIn() throws IOException {
        connect();
        return in;
    }

    public OutputStream getOut() throws IOException {
        connect();
        return out;
    }

    public void connect() throws IOException {
        if (socket == null) {
            socket = new Socket(addr, port);
        }
        if (in == null) {
            in = socket.getInputStream();
        }
        if (out == null) {
            out = socket.getOutputStream();
        }
    }

    public void close() throws IOException {
        try {
            if (in != null) {
                in.close();
            }
            in = null;
            if (out != null) {
                out.close();
            }
            out = null;
        } finally {
            if (socket != null) {
                socket.close();
            }
            socket = null;
        }
    }

    public String getHttpHead() throws IOException {
        return getHttpHead(getIn());
    }

    public void writeStream(String str) throws IOException {
        getOut().write(str.getBytes());
    }

    public void writeStreamSplited(String str, String macro) throws IOException {
        if (str.contains(macro)) {
            String[] splits = str.split(macro);
            for (String s : splits) {
                if (s.length() >= 1) {
                    getOut().write(s.getBytes());
                }
            }
        } else {
            getOut().write(str.getBytes());
        }
    }

    public void writeStream(InputStream input, int tamBuffer) throws IOException {
        writeStream(input, getOut(), tamBuffer);
    }

    public void writeStreamQtdBytes(InputStream input, int qtdBytes, int tamBuffer) throws IOException {
        writeStreamQtdBytes(input, getOut(), qtdBytes, tamBuffer);
    }

    private void writeStream(InputStream input, OutputStream out, int tamBuffer) throws IOException {
        byte[] buffer = new byte[tamBuffer];

        int len;
        while ((len = input.read(buffer)) != -1) {

            out.write(buffer, 0, len);
        }
    }

    private void writeStreamQtdBytes(InputStream input, OutputStream out, int qtdBytes, int tamBuffer) throws IOException {
        byte[] buffer = new byte[tamBuffer];
        int count = 0;
        int len;
        if ((count < qtdBytes) && ((len = input.read(buffer)) != -1)) {
            out.write(buffer, 0, len);
            count += len;
        }
    }

    private String getHttpHead(InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();
        String linha = "";

        while (!linha.equals("\r\n")) {
            linha = getLinha(in);

            if (linha == null) {
                break;
            }
            builder.append(linha);
        }

        return builder.toString();
    }

    private String getLinha(InputStream in) throws IOException {
        StringBuilder builder = new StringBuilder();
        int b = 0;

        while (-1 != (b = in.read())) {
            builder.append((char) b);

            if (b == 13) {
                b = in.read();

                if (b == -1) {
                    break;
                }
                builder.append((char) b);

                if (b == 10) {
                    break;
                }
            }
        }
        return b == -1 ? null : builder.toString();
    }

    public String toString() {
        return addr + ":" + port;
    }
}
