package View;

import ViewModel.VM.VMManager;

import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class ManagerView extends JFrame{
    private JPanel panel1;
    private JButton closeButton;
    private JButton filtrareButton;
    private JButton readButton;
    private JButton sortareButton;
    @Bind(value = "text",target = "valoriTextField.value")
    private JTextField valoriTextField;
    @Bind(value = "model", target = "categoryBox.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> categoryBox;
    private JButton XMLButton;
    private JButton CSVButton;
    private VMManager vmManager;
    public ManagerView()
    {
        this.add(panel1);
        this.setSize(1300,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.vmManager = new VMManager();
        try {
            Binder.bind(this, vmManager);
        } catch (BindingException e) {
            System.out.println("Error binding on login view!");
        }

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    dispose();}
                 });

        filtrareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmManager.getFilterCommandManager().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        XMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmManager.getXMLCommandManager().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        CSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmManager.getCSVCommandManager().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        sortareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmManager.getSortareCommandManager().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmManager.getReadCommandManager().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
