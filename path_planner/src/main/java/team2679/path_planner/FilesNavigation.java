package team2679.path_planner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FilesNavigation {

    JFileChooser fc;
    JPanel panel;

    public FilesNavigation(JPanel panel) {
        fc = new JFileChooser();
        this.panel = panel;
    }

    public File openFolderChooserDialog() {
        fc.setAcceptAllFileFilterUsed(false);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        return null;
    }

    public File openFileChooserDialog() {
        fc.setAcceptAllFileFilterUsed(true);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fc.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile();
        return null;
    }
}
