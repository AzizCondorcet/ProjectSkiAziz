package PartieGraphique;

import javax.swing.*;

import BE.ouagueni.model.LessonTypePOJO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CreateAccreditationDialog extends JDialog {
    private JTextField txtAccreditationName;
    private JComboBox<LessonTypePOJO> cmbLessonType;
    private boolean confirmed = false;

    public CreateAccreditationDialog(List<LessonTypePOJO> lessonTypes) {
        setTitle("Create Accreditation");
        setModal(true);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Accreditation Name:"));
        txtAccreditationName = new JTextField();
        inputPanel.add(txtAccreditationName);

        inputPanel.add(new JLabel("Select Lesson Type:"));
        cmbLessonType = new JComboBox<>();
        
        // Ajouter une option par défaut
        cmbLessonType.addItem(null);  // Ajouter une valeur null au début pour forcer la sélection
        for (LessonTypePOJO lessonType : lessonTypes) {
            cmbLessonType.addItem(lessonType);
        }
        inputPanel.add(cmbLessonType);

        add(inputPanel, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnOk = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbLessonType.getSelectedItem() == null) {
                    JOptionPane.showMessageDialog(null, "Veuillez sélectionner un type de leçon.");
                    return;  // Ne pas fermer la fenêtre si aucune sélection n'est faite
                }
                confirmed = true;
                setVisible(false);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getAccreditationName() {
        return txtAccreditationName.getText();
    }

    public int getSelectedLessonTypeId() {
        LessonTypePOJO selectedLessonType = (LessonTypePOJO) cmbLessonType.getSelectedItem();
        return selectedLessonType.getId();
    }
}
