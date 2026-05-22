/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.ebank;

import com.ebanking.view.LoginForm;
import javax.swing.SwingUtilities;

/**
 *
 * @author Reno
 */
public class EBank {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
