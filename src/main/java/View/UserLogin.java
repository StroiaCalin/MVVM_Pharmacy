package View;

import ViewModel.VM.VMLogin;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class UserLogin extends JFrame {
    private JButton loginButton;
    private JPanel rootJpanel;
    private JLabel textField1;
    private JLabel textField2;
    @Bind(value = "text",target = "username.value")
    private JTextField textBoxUserNameTextField;
    @Bind(value = "text",target = "password.value")
    private JPasswordField textBoxPasswordTextField;
    private VMLogin vm;
    public UserLogin() {
        this.add(rootJpanel);
        this.setSize(300,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.vm = new VMLogin();
        try {
            Binder.bind(this, vm);
        } catch (BindingException e) {
            System.out.println("Error binding on login view!");
        }
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vm.getLoginCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
