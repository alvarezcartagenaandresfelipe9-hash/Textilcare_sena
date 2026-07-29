/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package textilecaremain;

import textilecare.controller.LoginController;
import textilecare.view.LoginView;

public class Main {

    public static void main(String[] args) {
        LoginView vista = new LoginView();
        LoginController controlador = new LoginController(vista);
        vista.setVisible(true);
    }
}
