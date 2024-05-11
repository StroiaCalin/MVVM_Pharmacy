package ViewModel.VM;

import ViewModel.ICommand;
import ViewModel.ManagerCommands.*;
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
public class VMManager {

    private Property<DefaultComboBoxModel<String>> categoryBox = PropertyFactory.createProperty("textCategoryComboBox", this, new DefaultComboBoxModel<>());
    private Property<String> valoriTextField = PropertyFactory.createProperty("valoriTextField", this, String.class);
    public ICommand filterCommandManager;
    public ICommand sortareCommandManager;
    public ICommand readCommandManager;
    public ICommand xMLCommandManager;
    public ICommand cSVCommandManager;

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

        frame.setSize(600, 250);
        frame.setLocation(450, 80);
        frame.setVisible(true);
    }

    public VMManager()
    {
        this.sortareCommandManager = new SortareCommandManager(this);
        this.filterCommandManager = new FilterCommandManager(this);
        this.readCommandManager= new ReadCommandManager(this);
        this.xMLCommandManager= new XMLCommandManager(this);
        this.cSVCommandManager= new CSVCommandManager(this);
    }

}
