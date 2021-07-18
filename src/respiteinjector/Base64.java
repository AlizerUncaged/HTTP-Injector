/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;
import java.io.UnsupportedEncodingException;
/**
 *
 * @author user
 */
public class Base64 {
    public Base64() {}
  private static final byte[] b64 = str2byte("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=");
  
  private static byte val(byte foo) {
    if (foo == 61) return 0;
    for (int j = 0; j < b64.length; j++) {
      if (foo == b64[j]) return (byte)j;
    }
    return 0;
  }
  
  public static byte[] fromBase64(byte[] buf, int start, int length) throws IllegalStateException {
    try {
      byte[] foo = new byte[length];
      int j = 0;
      for (int i = start; i < start + length; i += 4) {
        foo[j] = ((byte)(val(buf[i]) << 2 | (val(buf[(i + 1)]) & 0x30) >>> 4));
        if (buf[(i + 2)] == 61) { j++; break; }
        foo[(j + 1)] = ((byte)((val(buf[(i + 1)]) & 0xF) << 4 | (val(buf[(i + 2)]) & 0x3C) >>> 2));
        if (buf[(i + 3)] == 61) { j += 2; break; }
        foo[(j + 2)] = ((byte)((val(buf[(i + 2)]) & 0x3) << 6 | val(buf[(i + 3)]) & 0x3F));
        j += 3;
      }
      byte[] bar = new byte[j];
      System.arraycopy(foo, 0, bar, 0, j);
      return bar;
    }
    catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalStateException("fromBase64: invalid base64 data", e);
    }
  }
  
  public static byte[] toBase64(byte[] buf, int start, int length)
  {
    byte[] tmp = new byte[length * 2];
    
int j;
    int foo = length / 3 * 3 + start;
    int i = 0;
    for ( j = start; j < foo; j += 3) {
      int k = buf[j] >>> 2 & 0x3F;
      tmp[(i++)] = b64[k];
      k = (buf[j] & 0x3) << 4 | buf[(j + 1)] >>> 4 & 0xF;
      tmp[(i++)] = b64[k];
      k = (buf[(j + 1)] & 0xF) << 2 | buf[(j + 2)] >>> 6 & 0x3;
      tmp[(i++)] = b64[k];
      k = buf[(j + 2)] & 0x3F;
      tmp[(i++)] = b64[k];
    }
    
    foo = start + length - foo;
    if (foo == 1) {
      int k = buf[j] >>> 2 & 0x3F;
      tmp[(i++)] = b64[k];
      k = (buf[j] & 0x3) << 4 & 0x3F;
      tmp[(i++)] = b64[k];
      tmp[(i++)] = 61;
      tmp[(i++)] = 61;
    }
    else if (foo == 2) {
      int k = buf[j] >>> 2 & 0x3F;
      tmp[(i++)] = b64[k];
      k = (buf[j] & 0x3) << 4 | buf[(j + 1)] >>> 4 & 0xF;
      tmp[(i++)] = b64[k];
      k = (buf[(j + 1)] & 0xF) << 2 & 0x3F;
      tmp[(i++)] = b64[k];
      tmp[(i++)] = 61;
    }
    byte[] bar = new byte[i];
    System.arraycopy(tmp, 0, bar, 0, i);
    return bar;
  }
  


  public static String fromBase64(String str)
  {
    byte[] bytes = str2byte(str);
    return byte2str(fromBase64(bytes, 0, bytes.length));
  }
  
  public static String toBase64(String str) {
    byte[] bytes = str2byte(str);
    return byte2str(toBase64(bytes, 0, bytes.length));
  }
  
  public static byte[] str2byte(String str)
  {
    return str2byte(str, "UTF-8");
  }
  
  public static byte[] str2byte(String str, String encoding) {
    if (str == null)
      return null;
    try { return str.getBytes(encoding);
    } catch (UnsupportedEncodingException e) {}
    return str.getBytes();
  }
  

  public static String byte2str(byte[] str)
  {
    return byte2str(str, 0, str.length, "UTF-8");
  }
  

  public static String byte2str(byte[] str, String encoding) { return byte2str(str, 0, str.length, encoding); }
  
  public static String byte2str(byte[] str, int s, int l, String encoding) {
    try {
      return new String(str, s, l, encoding);
    } catch (UnsupportedEncodingException e) {}
    return new String(str, s, l);
  }
}
