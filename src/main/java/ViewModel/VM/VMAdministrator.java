package ViewModel.VM;

import ViewModel.AdministratorCommands.*;
import ViewModel.ICommand;
import lombok.Setter;
import lombok.Getter;
import net.sds.mvvm.properties.Property;
import net.sds.mvvm.properties.PropertyFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Getter
@Setter
public class VMAdministrator {

    private Property<String> numeTextField = PropertyFactory.createProperty("numeTextField", this, String.class);
    private Property<String> passwordTextField = PropertyFactory.createProperty("passwordTextField", this, String.class);
    private Property<String> roleTextField = PropertyFactory.createProperty("roleTextField", this, String.class);
    private Property<String> farmaciaTextField = PropertyFactory.createProperty("farmaciaTextField", this, String.class);

    public ICommand vizualizareCommandAdministrator;
    public ICommand createCommandAdministrator;
    public ICommand readCommandAdministrator;
    public ICommand updateCommandAdministrator;
    public ICommand deleteCommandAdministrator;

    public void Tabelare(StringBuilder sb) {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(sb.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        JFrame frame = new JFrame("Table Data");
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Create close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Close the frame
            }
        });
        frame.getContentPane().add(closeButton, BorderLayout.SOUTH); // Add close button to the bottom of the frame

        frame.setSize(600, 300);
        frame.setLocation(680, 40);
        frame.setVisible(true);
    }

    public VMAdministrator()
    {
        this.vizualizareCommandAdministrator= new VizualizareCommandAdministrator(this);
        this.createCommandAdministrator = new CreateCommandAdministrator(this);
        this.readCommandAdministrator = new ReadCommandAdministrator(this);
        this.updateCommandAdministrator = new UpdateCommandAdministrator(this);
        this.deleteCommandAdministrator = new DeleteCommandAdministrator(this);

    }

}
