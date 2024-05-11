package View;

import ViewModel.VM.VMAngajati;
import net.sds.mvvm.bindings.Bind;
import net.sds.mvvm.bindings.Binder;
import net.sds.mvvm.bindings.BindingException;
import net.sds.mvvm.bindings.BindingType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class AngajatView extends JFrame{
    private JButton closeButton;
    private JPanel mainPanel;
    private JButton filtrareButton;
    private JButton sortareButton;
    private JButton createButton;
    private JButton updateButton;
    private JButton readButton;
    private JButton deleteButton;
    private VMAngajati vmAngajati;

    @Bind(value = "model", target = "categoryBox.value", type = BindingType.BI_DIRECTIONAL)
    private JComboBox<String> categoryBox;
    @Bind(value = "text",target = "filterValueField1.value")
    private JTextField filterValueField1;

    //@Bind(value = "text",target = "pretTextField.value")
///////////////////////
    @Bind(value = "text",target = "nume.value")
    private JLabel nume;
    @Bind(value = "text",target = "pretTextField.value")
    private JTextField pretTextField;
    @Bind(value = "text",target = "producatorTextField.value")
    private JTextField producatorTextField;
    @Bind(value = "text",target = "valabilitateTextField.value")
    private JTextField valabilitateTextField;
    @Bind(value = "text",target = "disponibilitateTextField.value")
    private JTextField disponibilitateTextField;
    @Bind(value = "text",target = "denumireTextField1.value")
    private JTextField denumireTextField1;
    private JButton CSVButton;
    private JButton XMLButton;

    public AngajatView(String username)
    {
        this.nume.setText(username);
        this.add(mainPanel);
        this.setSize(1300,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.vmAngajati = new VMAngajati();
        try {
            Binder.bind(this, vmAngajati);
        } catch (BindingException e) {
            System.out.println("Error binding on login view!");
        }

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();}
        });
        CSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getCSVCommandAngajati().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        XMLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getXMLCommandAngajati().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        filtrareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getFilterCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        sortareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getSortareCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getCreateCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getReadCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getUpdateCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    vmAngajati.getDeleteCommand().Execute();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
}
