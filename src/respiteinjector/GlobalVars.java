/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package respiteinjector;

import respite.http.injector.MainForm;

/**
 *
 * @author user
 */
public class GlobalVars {

    public void logThis(String log) {
        MainForm.Log(log, 0);
    }

    public void logThis(String log, int verb) {
        MainForm.Log(log, verb);
    }
}
