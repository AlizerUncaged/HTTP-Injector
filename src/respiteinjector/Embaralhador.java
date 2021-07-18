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
public class Embaralhador {
     public Embaralhador() {}
  
  public static byte[] embaralhar(byte[] bytes) { int deslocEsquerda = generateDeslocamentoEsquerda();
    int deslocDireita = generateDeslocamentoDireita();
    
    byte[] base64 = Base64.toBase64(bytes, 0, bytes.length);
    
    int meio = base64.length / 2;
    
    StringBuilder builder = new StringBuilder();
    
    builder.append(String.format("%03d", new Object[] { Integer.valueOf(deslocEsquerda) }));
    builder.append(CesarCifer.criptografa(new String(base64, 0, meio), deslocEsquerda, CesarCifer.ALFABETO_BASE64));
    builder.append('-');
    builder.append(CesarCifer.criptografa(new String(base64, meio, base64.length - meio), deslocDireita, CesarCifer.ALFABETO_BASE64));
    builder.append(String.format("%03d", new Object[] { Integer.valueOf(deslocDireita) }));
    
    return builder.toString().getBytes();
  }
  
  public static byte[] desembaralhar(byte[] bytes) {
    String str = new String(bytes);
    
    int deslocEsquerda = Integer.parseInt(str.substring(0, 3));
    int deslocDireita = Integer.parseInt(str.substring(str.length() - 3, str.length()));
    
    String[] lados = str.substring(3, str.length() - 3).split("-");
    
    StringBuilder builder = new StringBuilder();
    builder.append(CesarCifer.descriptografa(lados[0], deslocEsquerda, CesarCifer.ALFABETO_BASE64));
    builder.append(CesarCifer.descriptografa(lados[1], deslocDireita, CesarCifer.ALFABETO_BASE64));
    
    return Base64.fromBase64(builder.toString()).getBytes();
  }
  
  private static int generateDeslocamentoEsquerda()
  {
    String str =Long.toString( System.currentTimeMillis()) ;
    
    int val = parseInt(str, str.length() - 4, str.length() - 1);
    
    return val == 0 ? 1 : val;
  }
  
  private static int generateDeslocamentoDireita() {
    String str =Double.toString( Math.random());
    
    int val = parseInt(str, str.length() - 4, str.length() - 1);
    
    return val == 0 ? 1 : val;
  }
  
  private static int parseInt(String str, int indexI, int indexF) {
    return Integer.parseInt(str.substring(indexI, indexF));
  }
}
