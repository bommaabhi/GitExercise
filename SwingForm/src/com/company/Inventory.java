package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Vector;
/*
 * Created by JFormDesigner on Sat Aug 01 14:29:54 PDT 2020
 */



/**
 * @author unknown
 */
public class Inventory extends JFrame {
    public String catcode2;
    public Inventory() {
        initComponents();
    }
    Connection con1;
    PreparedStatement insert;


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Abhishek Reddy Bomma
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        textField2 = new JTextField();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new MigLayout(
            "hidemode 3",
            // columns
            "[fill]" +
            "[fill]" +
            "[fill]" +
            "[fill]",
            // rows
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]" +
            "[]"));

        //---- label1 ----
        label1.setText("Enter the Category Code");
        contentPane.add(label1, "cell 1 1");
        //---- label2 ----
        label2.setText("Enter the Category Desc");
        contentPane.add(label2, "cell 1 2");

        //---- textField1 ----
        textField1.setColumns(5);
        contentPane.add(textField1, "cell 3 1");




        //---- textField2 ----
        textField2.setColumns(20);
        contentPane.add(textField2, "cell 3 2");

        //======== scrollPane1 ========
        {
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    table1MouseClicked(e);
                }
            });

            scrollPane1.setViewportView(table1);
        }
        contentPane.add(scrollPane1, "cell 3 3");

        //---- button1 ----
        button1.setText("Add");
        button1.addActionListener(e -> {
            try {
                button1ActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        contentPane.add(button1, "cell 3 5");

        //---- button2 ----
        button2.setText("Edit");
        button2.addActionListener(e -> {
            try {
                btneditActionPerformed(e);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        });

        contentPane.add(button2, "cell 3 5");

        //---- button3 ----
        button3.setText("Delete");
        button3.addActionListener(e -> {
            try {
                btndeleteActionPerformed(e);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        contentPane.add(button3, "cell 3 5");
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Abhishek Reddy Bomma
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JTextField textField2;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public void updateTable() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/INVENTORY", "root", "");
        insert = con1.prepareStatement("Select * from category");
        ResultSet rs = insert.executeQuery();

        int count;

        ResultSetMetaData res = rs.getMetaData();
        count = res.getColumnCount();

        DefaultTableModel df = (DefaultTableModel) table1.getModel();

        df.setRowCount(0);

        while (rs.next()) {
            Vector v2 = new Vector();

            for (int a = 1; a <= count; a++) {
//                v2.add(rs.getString("catcode"));
//                v2.add(rs.getString("catdesc"));
                v2.add(rs.getObject(a));


            }

            df.addRow(v2);


        }
    }

    private void button1ActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        String catcode, catdesc;



        catcode = textField1.getText();
        catdesc = textField2.getText();



        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/inventory","root","");


        if(e.getSource()==button1) {


            insert = con1.prepareStatement("Select * from category where catcode = ?");

            insert.setString(1, catcode);



            ResultSet rs = insert.executeQuery();

            if(rs.isBeforeFirst()){          //res.isBeforeFirst() is true if the cursor

                JOptionPane.showMessageDialog(null,"The catcode you are trying to enter already exists ");

                textField1.setText("");
                textField2.setText("");
                textField1.requestFocus();

                return;
            }


            insert = con1.prepareStatement("insert into category values(?,?)");

            insert.setString(1, catcode);
            insert.setString(2, catdesc);

            insert.executeUpdate();

            JOptionPane.showMessageDialog(null, "Record added");

            textField1.setText("");
            textField2.setText("");
            textField1.requestFocus();

            updateTable();
        }


        if(e.getSource()==table1) {
        }
    }

    private void table1MouseClicked(MouseEvent e) {
        // TODO add your code here

        DefaultTableModel df = (DefaultTableModel)table1.getModel();

        int index1 = table1.getSelectedRow();


        textField1.setText(df.getValueAt(index1,0).toString());

        catcode2 = textField1.getText();
        textField2.setText(df.getValueAt(index1,1).toString());

    }

    private void btneditActionPerformed(ActionEvent e) throws SQLException, ClassNotFoundException {
        // TODO add your code here

        String catcode, catdesc;



        catcode = textField1.getText();
        catdesc = textField2.getText();



        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/inventory","root","");


        insert = con1.prepareStatement("update category set catcode=?,catdesc=? where catcode =?");

        insert.setString(1, catcode);
        insert.setString(2, catdesc);
        insert.setString(3, catcode2);

        insert.executeUpdate();

        JOptionPane.showMessageDialog(null, "Record edited");

        textField1.setText("");
        textField2.setText("");
        textField1.requestFocus();





        updateTable();




    }

    private void btndeleteActionPerformed(ActionEvent e) throws ClassNotFoundException, SQLException {
        // TODO add your code here

        String catcode, catdesc;



        catcode = textField1.getText();
        catdesc = textField2.getText();



        Class.forName("com.mysql.jdbc.Driver");
        con1 = DriverManager.getConnection("jdbc:mysql://localhost/inventory","root","");


        int result = JOptionPane.showConfirmDialog(null,"Are you sure you want to delete?", "Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(result == JOptionPane.YES_OPTION){

            insert = con1.prepareStatement("delete from category where catcode =?");

            insert.setString(1, catcode2);

        }


        insert.execute();

        JOptionPane.showMessageDialog(null, "Record deleted");

        textField1.setText("");
        textField2.setText("");
        textField1.requestFocus();





        updateTable();

    }

    public void sss(){


        String[] cols = {"Category Code", "Category Description"};
        String[][] data = {{"d1", "d1.1"},{"d2", "d2.1"}};
        DefaultTableModel model = new DefaultTableModel(data, cols);
        table1.setModel(model);




        //  add(table1);



    }



}
