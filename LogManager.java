/*
    Peroxy/0.1 - Proxy server with great functionalities
    Copyright (C) 2018  Peroxy

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package proxyserver;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.regex.Pattern;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
/**
 *
 * @author rd_square
 */
public class LogManager extends javax.swing.JDialog implements Runnable{

    /**
     * Data structure to store log map temporarily
     */
    HashMap<String, File> logMap;
    
    /**
     * Pattern to show data on table
     * 
     */
    String pattern ;
    
    /**
     * Processed filenames
     */
    String[] fileNamesList;
    
    /**
     * processed data of table
     */
    String[] row; 
    
    /**
     * Creates new form LogManager
     */
    public LogManager(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setSize(parent.getWidth(), parent.getHeight());
        
        //Initial setup
        logMap = new HashMap<>();
        row = new String[12];
        selectionBox.setSelectedIndex(0);
        ipLabel.setVisible(false);
        ipEdit.setVisible(false);
        dateBox.setVisible(true);
        monthBox.setVisible(true);
        yearBox.setVisible(true);
        LocalDateTime now = LocalDateTime.now();
        dateBox.setSelectedIndex(now.getDayOfMonth());
        monthBox.setSelectedIndex(now.getMonthValue());
        int year = 2018;
        while(year <= now.getYear()) {
            yearBox.addItem(Integer.toString(year));
            year += 1;
        }
        yearBox.setSelectedIndex(year-2018);
        jTable2.getColumnModel().getColumn(11).setMinWidth(300);
        
        /**
         * Copying main log manager map to locallog manager map
         * to avoid concurrency errors in different threads
         */
        logMap = Proxy.log;
        pattern = ".*/"+now.getYear()+"/"+now.getMonth()+"/"+now.getDayOfMonth()+"/.*";
        
        //show data on table
        logButton.doClick();
    }

    /**
     * Show data on table by 
     * reading files from log Manager files
     */
    public void showDataOnTable(){
        logButton.setEnabled(false);
        delLogButton.setEnabled(false);
        tableLabel.setText("Log Table - (0) log entries");
        int rowCounter = 1;
        //delete previous rows of table
        if(jTable2.getRowCount() != 0) {
            ((DefaultTableModel)jTable2.getModel()).getDataVector().removeAllElements();
            ((DefaultTableModel)jTable2.getModel()).fireTableDataChanged();
        }
        
        //show data on table by reading files from
        ArrayList<String> logArray = new ArrayList<>();
        fileNamesList = new String[logMap.size()];
        int fileCount = 0;
        for (String fileName : logMap.keySet()) {
            if(Pattern.matches(pattern, fileName)){
                fileNamesList[fileCount] = fileName;
                fileCount++;
                try {
                    File logFile = new File(fileName);
                    FileInputStream fis = new FileInputStream(logFile);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    logArray = (ArrayList<String>) ois.readObject();
                    fis.close();
                    ois.close();
                } catch (Exception e) {
                    //No need to show error - will increase burder on terminal
                    continue;
                }
                
                //add one row to the table
                row = new String[12];
                row[0] = Integer.toString(rowCounter);
                tableLabel.setText("Log Table - ("+ Integer.toString(rowCounter) +") log entries");
                rowCounter += 1;
                int i = 1;
                for(String data : logArray) {
                    row[i] = data;
                    i += 1;
                }
                ((DefaultTableModel)jTable2.getModel()).addRow(row);
            }
        }
        
        delLogButton.setEnabled(true);
        logButton.setEnabled(true);
    }
    
    @Override
    public void run() {
        setVisible(true);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        selectionBox = new javax.swing.JComboBox<String>();
        ipLabel = new javax.swing.JLabel();
        ipEdit = new javax.swing.JTextField();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        dateBox = new javax.swing.JComboBox<String>();
        monthBox = new javax.swing.JComboBox<String>();
        yearBox = new javax.swing.JComboBox<String>();
        logButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        tableLabel = new javax.swing.JLabel();
        delLogButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Log Manager - Peroxy/0.1");

        selectionBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Acc. to Date", "Acc. to Client" }));
        selectionBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectionBoxActionPerformed(evt);
            }
        });

        ipLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ipLabel.setText("Enter IP Address of User:");

        ipEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipEditActionPerformed(evt);
            }
        });

        dateBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Date", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        dateBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateBoxActionPerformed(evt);
            }
        });

        monthBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));

        yearBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Year" }));

        logButton.setText("Show Log");
        logButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jScrollPane2ComponentRemoved(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "TimeStamp", "Client IP", "Client Name", "Rquest Type", "HTTP Version", "WebHost", "Request", "Connection", "Product", "Browser", "OS Detail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable2.setColumnSelectionAllowed(true);
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMinWidth(10);
            jTable2.getColumnModel().getColumn(1).setMinWidth(200);
            jTable2.getColumnModel().getColumn(2).setMinWidth(200);
            jTable2.getColumnModel().getColumn(3).setMinWidth(200);
            jTable2.getColumnModel().getColumn(4).setMinWidth(200);
            jTable2.getColumnModel().getColumn(5).setMinWidth(200);
            jTable2.getColumnModel().getColumn(6).setMinWidth(200);
            jTable2.getColumnModel().getColumn(7).setMinWidth(200);
            jTable2.getColumnModel().getColumn(8).setMinWidth(200);
            jTable2.getColumnModel().getColumn(9).setMinWidth(200);
            jTable2.getColumnModel().getColumn(10).setMinWidth(200);
            jTable2.getColumnModel().getColumn(11).setMinWidth(200);
        }

        tableLabel.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        tableLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tableLabel.setText("Log Table - (0) Log Entries");

        delLogButton.setText("Del Log");
        delLogButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delLogButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(539, 539, 539)
                                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(dateBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(monthBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(yearBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                                .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(selectionBox, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(110, 110, 110)
                                .addComponent(ipLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ipEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(delLogButton, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                .addGap(22, 22, 22))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(tableLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipLabel)
                    .addComponent(ipEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectionBox)
                    .addComponent(delLogButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dateBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monthBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yearBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logButton))
                .addGap(11, 11, 11)
                .addComponent(tableLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectionBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectionBoxActionPerformed
        if(selectionBox.getSelectedIndex() == 0){
            //hide ip edit
            ipLabel.setVisible(false);
            ipEdit.setVisible(false);
            dateBox.setVisible(true);
            monthBox.setVisible(true);
            yearBox.setVisible(true);
        } else {
            //hide date item
            ipLabel.setVisible(true);
            ipEdit.setVisible(true);
            dateBox.setVisible(false);
            monthBox.setVisible(false);
            yearBox.setVisible(false);
        }
    }//GEN-LAST:event_selectionBoxActionPerformed

    private void ipEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ipEditActionPerformed

    private void dateBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateBoxActionPerformed

    private void logButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logButtonActionPerformed
        // set pattern and then call show table data
        if(selectionBox.getSelectedIndex() == 0) {
            if(dateBox.getSelectedIndex() == 0) {
                if(monthBox.getSelectedIndex() == 0) {
                    if(yearBox.getSelectedIndex() == 0) {
                        JOptionPane.showMessageDialog(this, 
                                "<html><h3>No Date Selected!</h3><p>Please select any date to show log data.", 
                                "Date Selection Error - " + Proxy.APPNAME, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        pattern = ".*/"+yearBox.getItemAt(yearBox.getSelectedIndex())+"/.*";
                    }
                } else {
                    if(yearBox.getSelectedIndex() == 0) {
                        pattern = ".*/"+Integer.toString(monthBox.getSelectedIndex())+"/.*";
                    } else {
                        pattern = ".*/"+yearBox.getItemAt(yearBox.getSelectedIndex())+"/"+
                                Integer.toString(monthBox.getSelectedIndex())+"/.*";
                    }
                }
            } else {
                if(monthBox.getSelectedIndex() == 0) {
                    if(yearBox.getSelectedIndex() == 0) {
                        pattern = ".*/"+dateBox.getSelectedIndex()+"/.*";
                    } else {
                        pattern = ".*/"+yearBox.getItemAt(yearBox.getSelectedIndex())+"/.*/"+
                               Integer.toString(dateBox.getSelectedIndex())+"/.*";
                    }
                } else {
                    if(yearBox.getSelectedIndex() == 0) {
                        pattern = ".*/"+Integer.toString(monthBox.getSelectedIndex())+"/"+
                               Integer.toString(dateBox.getSelectedIndex())+"/.*";
                    } else {
                        pattern = ".*/"+yearBox.getItemAt(yearBox.getSelectedIndex())+"/"+
                                Integer.toString(monthBox.getSelectedIndex())+"/"+
                                Integer.toString(dateBox.getSelectedIndex())+"/.*";
                    }
                }
            }
        } else {
            pattern = ".*/"+ ipEdit.getText().replace(".", "0") + "/.*";
        }
        
        
        /**
         * Pattern is set now
         * call to show table data
         */
        showDataOnTable();
    }//GEN-LAST:event_logButtonActionPerformed

    private void delLogButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delLogButtonActionPerformed
        // delete log
        int status = JOptionPane.showOptionDialog(this, 
                "<html><h3>Delete Log Data?</h3><p>Are you Sure! You want to delete log data as it can not be undone?</p></html>", 
                "Delete Log Dalete? - " + Proxy.APPNAME, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, new String[] {"Yes delete log!", "No don't delete"}, 
                "No don't delete");
        if(status!=0){
            return;
        }
        
        delLogButton.setEnabled(false);
        logButton.setEnabled(false);
        
        /**
         * deleting log files
         * 
         */
        for (String fileName : fileNamesList) {
            if(fileName.equals("")){
                continue;
            }
            try{
                File file = new File(fileName);
                if(file.exists()) {
                    file.delete();
                    if(logMap.get(fileName) != null){
                        logMap.remove(fileName);
                    }
                }
            } catch (Exception e) {
                //simply continue
            }
        }
        
        logButton.setEnabled(true);
        delLogButton.setEnabled(true);
        logButton.doClick();
    }//GEN-LAST:event_delLogButtonActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // open at the center of window
        this.setLocationRelativeTo(null);
    }//GEN-LAST:event_formWindowOpened

    private void jScrollPane2ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jScrollPane2ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2ComponentRemoved

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> dateBox;
    private javax.swing.JButton delLogButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JTextField ipEdit;
    private javax.swing.JLabel ipLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton logButton;
    private javax.swing.JComboBox<String> monthBox;
    private javax.swing.JComboBox<String> selectionBox;
    private javax.swing.JLabel tableLabel;
    private javax.swing.JComboBox<String> yearBox;
    // End of variables declaration//GEN-END:variables
}
