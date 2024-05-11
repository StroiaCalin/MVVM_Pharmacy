package View;

import ViewModel.VM.VMAdministrator;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class AdministratorView extends JFrame {
    private JButton vizualizareButton;
    private JButton closeButton;
    private JButton createButton;
    private JButton deleteButton;
    private JButton readButton;
    private JButton updateButton;
    @Bind(value = "text", target = "numeTextField.value")
    private JTextField numeTextField;
    @Bind(value = "text", target = "passwordTextField.value")
    private JTextField passwordTextField;
    @Bind(value = "text", target = "roleTextField.value")
    private JTextField roleTextField;
    @Bind(value = "text", target = "farmaciaTextField.value")
    private JTextField farmaciaTextField;
    private JPanel mainPanel;
    private VMAdministrator vmAdministrator;

    public AdministratorView() {
        this.add(mainPanel);
        this.setSize(1300, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.vmAdministrator = new VMAdministrator();
        try {
            Binder.bind(this, vmAdministrator);
        } catch (BindingException e) {
            System.out.println("Error binding on login view!");
        }

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        vizualizareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAdministrator.getVizualizareCommandAdministrator().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAdministrator.getCreateCommandAdministrator().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAdministrator.getReadCommandAdministrator().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAdministrator.getUpdateCommandAdministrator().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAdministrator.getDeleteCommandAdministrator().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
