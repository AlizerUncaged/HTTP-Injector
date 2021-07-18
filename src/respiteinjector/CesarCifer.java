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
public class CesarCifer {
    
  public static final String ALFABETO = "abcdefghijklmnopqrstuvwxyz";
  public static final String ALFABETO_BASE64 = new String();
  


  public CesarCifer() {}
  

  public static String criptografa(String mensagem, int deslocamento, String alfabeto)
  {
    char[] array = new char[mensagem.length()];
    mensagem.getChars(0, mensagem.length(), array, 0);
    
    for (int cont = 0; cont < array.length; cont++) { char c;
      if ((c = getCharDeslocamento(deslocamento, array[cont], alfabeto)) != 0)
        array[cont] = c;
    }
    return String.valueOf(array);
  }
  




  public static String descriptografa(String mensagem, int deslocamento, String alfabeto)
  {
    return criptografa(mensagem, deslocamento * -1, alfabeto);
  }
  


  private static char getCharDeslocamento(int deslocamento, char caracter, String alfabeto)
  {
    int posicao;
    

    int tam = 65;
    
    
    if (alfabeto == ALFABETO_BASE64) {
    
      posicao = getPosicaoCharAlfabetoBase64(caracter);
    } else {
      tam = alfabeto.length();
      if ((posicao = getPosicaoChar(caracter, alfabeto)) == -1) {
        return '\000';
      }
    }
    if ((deslocamento > tam) || (deslocamento < tam * -1)) {
      deslocamento %= tam;
    }
    posicao += deslocamento;
    
    if (posicao > tam - 1) {
      posicao -= tam;
    }
    else if (posicao < 0) {
      posicao += tam;
    }
    return alfabeto == ALFABETO_BASE64 ? getCharAlfabetoBase64(posicao) : alfabeto.charAt(posicao);
  }
  



  private static int getPosicaoChar(char c, String str)
  {
    return str.indexOf(c);
  }
  
  private static int getPosicaoCharAlfabetoBase64(char c)
  {
    if (Character.isLetter(c))
      return Character.isUpperCase(c) ? c - 'A' : c - 'a' + 26;
    if (Character.isDigit(c))
      return c - '0' + 52;
    if (c == '/')
      return 62;
    if (c == '+') {
      return 63;
    }
    return 64;
  }
  

  private static char getCharAlfabetoBase64(int posicao)
  {
    if (posicao < 26)
      return (char)(65 + posicao);
    if (posicao < 52)
      return (char)(97 + posicao - 26);
    if (posicao < 62)
      return (char)(48 + posicao - 52);
    if (posicao == 62)
      return '/';
    if (posicao == 63) {
      return '+';
    }
    return '=';
  }
}
