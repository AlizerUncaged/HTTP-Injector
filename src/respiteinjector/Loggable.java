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
public abstract interface Loggable
{
  public static final int LOG_LEVEL_DEBUG = 2;
  public static final int LOG_LEVEL_INFO = 1;
  public static final int LOG_LEVEL_ATENTION = 0;
  public static final int LOG_LEVEL_CRITICAL = -1;
  
  public abstract void onLogReceived(String paramString, int paramInt, Exception paramException);
}
