package ViewModel;

import Model.Utilizator;
import Model.UtilizatorRepository;
import View.AdministratorView;
import View.ManagerView;
import View.AngajatView;
import ViewModel.VM.VMLogin;

import javax.swing.*;

public class LoginCommand implements ICommand {
    private VMLogin vmLog;
    private UtilizatorRepository userService;

    public LoginCommand(VMLogin vmLog) {
        this.vmLog = vmLog;
        this.userService = new UtilizatorRepository();
    }

    @Override
    public void Execute() {
        String userName, password;
        userName = vmLog.getUsername().get();
        password = vmLog.getPassword().get();
        Utilizator user = userService.getUserByUsername(userName);

        if (user != null) {
            if (user.getPassword().equals(password)) {
                if(user.getRole().equals("Angajat")){
                    new AngajatView(userName);
                } if(user.getRole().equals("Manager")){
                    new ManagerView();
                }
                if(user.getRole().equals("Administrator")){
                    new AdministratorView();
                }
                //this.userLogin.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Parola gresita!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nu exista acest username!");
        }
        System.out.println("DA");
    }


}
