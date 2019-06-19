package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainForm extends JFrame{
    private JButton button1;
    private JTextField adressField;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JPanel mainPanel;
    private JList list1;
    private JButton back;
    private ArrayList <String> dirscache = new ArrayList();

    public MainForm(){
        super("Проводник");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        add(mainPanel);
        JDialog dirDialog = new JDialog(MainForm.this,"Создание папки", true);
        File discs[] = File.listRoots();
        list1.setListData(discs);

        list1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                DefaultListModel model = new DefaultListModel();
                String selectedObject = list1.getSelectedValue().toString();
                String fullPath = tuFullPath(dirscache);
                File selectedF;
                if(dirscache.size() > 1) selectedF = new File(fullPath,selectedObject);
                else {selectedF = new File(fullPath+selectedObject);}

                if(selectedF.isDirectory()){
                    String[] rootStr = selectedF.list();
                    for(String str: rootStr){
                        File checkObject = new File(selectedF.getPath(), str);
                        if(checkObject.isHidden()){
                            if (checkObject.isDirectory()){
                                model.addElement(str);
                            }
                            else {model.addElement("file -" + str);
                            }
                        }
                    }
                    dirscache.add(selectedObject);
                    list1.setModel(model);
                }

            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) { }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) { }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) { }

            @Override
            public void mouseExited(MouseEvent mouseEvent) { }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(dirscache.size() > 1){
                    dirscache.remove(dirscache.size()-1);
                    String backDir = tuFullPath(dirscache);
                    String[] objects = new File(backDir).list();
                    DefaultListModel backRootModel = new DefaultListModel();

                    for(String str: objects){
                        File checkObject = new File(backDir, str);
                        if(checkObject.isHidden()){
                            if (checkObject.isDirectory()){
                                backRootModel.addElement(str);
                            }
                            else {backRootModel.addElement("file -" + str);
                            }
                        }
                    }
                    list1.setModel(backRootModel);
                }
                else {
                    dirscache.removeAll(dirscache);
                    list1.setListData(discs);
                }
            }
        });

        getContentPane().add(mainPanel);
        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public String tuFullPath(List<String> file){
        String listPart = "";
        for(String str: file)
            listPart = listPart+str;

        return  listPart;

    }
}
