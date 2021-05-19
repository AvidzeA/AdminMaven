/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author wtfav
 */
public class Admin extends javax.swing.JFrame {
    Connection con;
    public Admin() {
        initComponents();
        AdminAuto aa =new AdminAuto(this);
        Thread t =new Thread(aa);
        t.start();

    }
    void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        jTextArea1.setText("");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Ingredient");
            while(rs.next()) {
                jTextArea1.append(rs.getString(1)+"   name: "+rs.getString(2)+"   type: "+rs.getString(3)+"\n");
            }
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        jTextArea2.setText("");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Dish");
            while(rs.next()) {
                jTextArea2.append(rs.getString(1)+"   name: "+rs.getString(2)+"   en.value: "+rs.getString(3)+"   price: "+rs.getString(4)+"\n");
            }
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        jTextArea3.setText("");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Client");
            while(rs.next()) {
                jTextArea3.append(rs.getString(1)+"   name: "+rs.getString(2)+"\n");
            }
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        jTextArea4.setText("");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Storage");
            while(rs.next()) {
                jTextArea4.append(rs.getString(1)+"   type: "+rs.getString(2)+"   capacity: "+rs.getString(3)+"   cur.amount: "+rs.getString(4)+"   ingredient: "+rs.getString(5)+"\n");
            }
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        jTextArea5.setText("");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Composition");
            while(rs.next()) {
                jTextArea5.append("dish: "+rs.getString(1)+"   ingredient: "+rs.getString(2)+"   num: "+rs.getString(3)+"\n");
            }
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        jTextArea6.setText("");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String tempTime = dtf.format(now);
        String[] tempTime1 = tempTime.split(" ");
        String[] tempTime2 = tempTime1[1].split(":");
        int currentTime =  Integer.parseInt(tempTime2[0])*3600+Integer.parseInt(tempTime2[1])*60+Integer.parseInt(tempTime2[2]);
        //доделать время
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Rest.Order");
            ArrayList<int[]> toDelete = new ArrayList<>();
            while(rs.next()) {
                String[] time1 = rs.getString(4).split(" ");
                String[] time2 = time1[1].split(":");
                int orderTime = Integer.parseInt(time2[0])*3600+Integer.parseInt(time2[1])*60+Integer.parseInt(time2[2]);
                int dif = Math.abs(currentTime-orderTime);
                if (dif >= 120) {
                    int[] elem = new int[2];
                    elem[0]=rs.getInt(1);
                    elem[1]=rs.getInt(2);
                    toDelete.add(elem);
                } else {
                    jTextArea6.append("Client: " + rs.getInt(1) + "   DIsh: " + rs.getInt(2) + "   num: " + rs.getInt(3) + "   time: " + rs.getString(4) + "\n");
                }
            }
            for (int[] elem : toDelete) {
                PreparedStatement preparedStatement = con.prepareStatement("delete from Rest.Order where Client = ? AND Dish = ?;");
                preparedStatement.setInt(1, elem[0]);
                preparedStatement.setInt(2, elem[1]);
                preparedStatement.executeUpdate();
            }
            con.close();
        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Input ingredient as: name:type",
                "Add ingredient", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length == 2) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");
                PreparedStatement preparedStatement = con.prepareStatement("insert into Ingredient(name, type) values(?,?);");
                preparedStatement.setString(1, input[0]);
                preparedStatement.setString(2, input[1]);
                preparedStatement.executeUpdate();
                con.close();
            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }

    }
    void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Ingredient to delete (id)",
                "Delete ingredient", JOptionPane.PLAIN_MESSAGE);
        int input = Integer.parseInt(m);
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            PreparedStatement preparedStatement = con.prepareStatement("delete from Ingredient where idIngredient = ?");
            preparedStatement.setInt(1, input);
            preparedStatement.executeUpdate();
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Dish to add: name:energy value:price",
                "Add dish", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length == 3) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");

                PreparedStatement preparedStatement = con.prepareStatement("insert into Dish(name, energyValue, price) values(?,?,?);");
                preparedStatement.setString(1, input[0]);
                preparedStatement.setInt(2, Integer.parseInt(input[1]));
                preparedStatement.setInt(3, Integer.parseInt(input[2]));
                preparedStatement.executeUpdate();

                con.close();

            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }
    }
    void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Dish to delete (id)",
                "Delete dish", JOptionPane.PLAIN_MESSAGE);
        int input = Integer.parseInt(m);
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            PreparedStatement preparedStatement = con.prepareStatement("delete from Dish where idDish = ?");
            preparedStatement.setInt(1, input);
            preparedStatement.executeUpdate();
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Input cell parameters: type:capacity:amount:idIngredient",
                "Add cell", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length == 4) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");

                PreparedStatement preparedStatement = con.prepareStatement("insert into Storage(type, capacity, currentAmount, Ingredient) values(?,?,?,?);");
                preparedStatement.setString(1, input[0]);
                preparedStatement.setInt(2, Integer.parseInt(input[1]));
                preparedStatement.setInt(3, Integer.parseInt(input[2]));
                preparedStatement.setInt(4, Integer.parseInt(input[3]));
                preparedStatement.executeUpdate();

                con.close();

            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }
    }
    void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Cell to delete (id)",
                "Delete cell", JOptionPane.PLAIN_MESSAGE);
        int input = Integer.parseInt(m);
        String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(url,"root","password");
            PreparedStatement preparedStatement = con.prepareStatement("delete from Storage where idStorageCell = ?");
            preparedStatement.setInt(1, input);
            preparedStatement.executeUpdate();
            con.close();

        }
        catch(Exception e){jTextArea1.setText("Could not connect to database");}
    }
    void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Input link to add: dish:ingredient:amount",
                "Add link", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length == 3) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");

                PreparedStatement preparedStatement = con.prepareStatement("insert into Composition(Dish, Ingredient, num) values(?,?,?);");
                preparedStatement.setInt(1, Integer.parseInt(input[0]));
                preparedStatement.setInt(2, Integer.parseInt(input[1]));
                preparedStatement.setInt(3, Integer.parseInt(input[2]));
                preparedStatement.executeUpdate();

                con.close();

            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }
    }
    void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Input link to delete: dish:ingredient" ,
                "Delete link", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length==2) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");

                PreparedStatement preparedStatement = con.prepareStatement("delete from Composition where Dish = ? AND Ingredient = ?");
                preparedStatement.setInt(1, Integer.parseInt(input[0]));
                preparedStatement.setInt(2, Integer.parseInt(input[1]));
                preparedStatement.executeUpdate();

                con.close();

            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }
    }
    void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Amount and cell id: amount:idStorageCell",
                "But ingredients", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length == 2) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");
                String query = "select Capacity, currentAmount from Storage where idStorageCell = "+input[1];
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                int max = 0;
                int current = 0;
                while (rs.next()) {
                    max = rs.getInt(1);
                    current = rs.getInt(2);
                }
                current+=Integer.parseInt(input[0]);
                if (current > max) {
                    current = max;
                }
                PreparedStatement preparedStatement = con.prepareStatement("update Storage set currentAmount = ? where idStorageCell = ?;");
                preparedStatement.setInt(1, current);
                preparedStatement.setInt(2, Integer.parseInt(input[1]));
                preparedStatement.executeUpdate();

                con.close();

            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }
    }
    void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {
        String m = JOptionPane.showInputDialog(this, "Amount and cell id: amount:idStorageCell" ,
                "Dispose of ingredients", JOptionPane.PLAIN_MESSAGE);
        String[] input = m.split(":");
        if (input.length==2) {
            String url="jdbc:mysql://localhost:3306/Rest?characterEncoding=latin1&useConfigs=maxPerformance";
            try{
                Class.forName("com.mysql.jdbc.Driver");
                con= DriverManager.getConnection(url,"root","password");
                String query = "select Capacity, currentAmount from Storage where idStorageCell = "+input[1];
                Statement stmt=con.createStatement();
                ResultSet rs=stmt.executeQuery(query);
                int max = 0;
                int current = 0;
                while (rs.next()) {
                    max = rs.getInt(1);
                    current = rs.getInt(2);
                }
                current-=Integer.parseInt(input[0]);
                if (current<0) {
                    current=0;
                }
                PreparedStatement preparedStatement = con.prepareStatement("update Storage set currentAmount = ? where idStorageCell = ?;");
                preparedStatement.setInt(1, current);
                preparedStatement.setInt(2, Integer.parseInt(input[1]));
                preparedStatement.executeUpdate();

                con.close();

            }
            catch(Exception e){jTextArea1.setText("Could not connect to database");}
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        jTextArea4.setColumns(20);
        jTextArea4.setRows(5);
        jScrollPane4.setViewportView(jTextArea4);

        jTextArea5.setColumns(20);
        jTextArea5.setRows(5);
        jScrollPane5.setViewportView(jTextArea5);

        jTextArea6.setColumns(20);
        jTextArea6.setRows(5);
        jScrollPane6.setViewportView(jTextArea6);

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel1.setText("Ingredient ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel2.setText("Dish ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel3.setText("Client ");

        jLabel4.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel4.setText("Storage ");

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel5.setText("Composition ");

        jLabel6.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel6.setText("Order ");

        jButton1.setText("O");

        jButton2.setText("O");

        jButton3.setText("O");

        jButton4.setText("O");

        jButton5.setText("O");

        jButton6.setText("O");
        jButton6.setActionCommand("O");

        jButton7.setText("Add ingredient ");

        jButton8.setText("Delete ingredient ");

        jButton9.setText("Add dish ");

        jButton10.setText("Delete dish ");

        jButton11.setText("Add cell ");

        jButton12.setText("Remove cell ");

        jButton13.setText("Add link ");

        jButton14.setText("Delete link ");

        jButton15.setText("Buy ingredients ");

        jButton16.setText("Dispose of ingredients ");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });




        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(190, 190, 190)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton6))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(182, 182, 182)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton3))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane6)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jButton2)
                                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(jButton5))
                                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(209, 209, 209)
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(98, 98, 98)
                                                .addComponent(jButton1))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addGap(175, 175, 175)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(106, 106, 106)
                                                .addComponent(jButton4)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel3)
                                                .addComponent(jButton3))
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton8)
                                        .addComponent(jButton7)
                                        .addComponent(jButton9)
                                        .addComponent(jButton10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel6)
                                                .addComponent(jButton5)
                                                .addComponent(jButton6))
                                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                        .addComponent(jScrollPane5)
                                        .addComponent(jScrollPane6))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton11)
                                        .addComponent(jButton12)
                                        .addComponent(jButton13)
                                        .addComponent(jButton14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton15)
                                        .addComponent(jButton16))
                                .addGap(77, 77, 77))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    // End of variables declaration
}
