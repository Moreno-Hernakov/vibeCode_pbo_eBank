/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ebank;

//import com.ebanking.view.LoginForm;
import com.ebanking.view.SignUp;
import javax.swing.SwingUtilities;

/**
 *
 * @author Reno
 */
public class EBank {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SignUp SignUpFrame = new SignUp();
            SignUpFrame.setVisible(true);
            SignUpFrame.pack();
            SignUpFrame.setLocationRelativeTo(null); 
        });
    }
}
