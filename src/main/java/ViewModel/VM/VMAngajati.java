package ViewModel.VM;

import ViewModel.AngajatiCommands.*;
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
public class VMAngajati {
    private Property<String> filterValueField1 = PropertyFactory.createProperty("filterValueField1", this, String.class);
    private Property<DefaultComboBoxModel<String>> categoryBox = PropertyFactory.createProperty("textCategoryComboBox", this, new DefaultComboBoxModel<>());
    private Property<String> pretTextField = PropertyFactory.createProperty("pretTextField", this, String.class);
    private Property<String> producatorTextField = PropertyFactory.createProperty("producatorTextField", this, String.class);
    private Property<String> valabilitateTextField = PropertyFactory.createProperty("valabilitateTextField", this, String.class);
    private Property<String> disponibilitateTextField = PropertyFactory.createProperty("disponibilitateTextField", this, String.class);
    private Property<String> denumireTextField1 = PropertyFactory.createProperty("denumireTextField1", this, String.class);
    private Property<String> nume = PropertyFactory.createProperty("nume", this, String.class);
    public ICommand filterCommand;
    public ICommand sortareCommand;
    public ICommand createCommand;
    public ICommand readCommand;
    public ICommand updateCommand;
    public ICommand deleteCommand;
    public ICommand cSVCommandAngajati;
    public ICommand xMLCommandAngajati;

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
        frame.setLocation(650, 30);
        frame.setVisible(true);
    }

    public VMAngajati()
    {
        this.filterCommand = new FilterCommand(this);
        this.sortareCommand= new SortareCommand(this);
        this.createCommand= new CreateCommand(this);
        this.readCommand= new ReadCommand(this);
        this.updateCommand= new UpdateCommand(this);
        this.deleteCommand= new DeleteCommand(this);
        this.cSVCommandAngajati= new CSVCommandAngajati(this);
        this.xMLCommandAngajati= new XMLCommandAngajati(this);
    }

}
