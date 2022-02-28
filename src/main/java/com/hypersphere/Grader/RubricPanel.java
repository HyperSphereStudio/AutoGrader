package com.hypersphere.Grader;

import com.hypersphere.GUI.GUIPanel;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public class RubricPanel extends GUIPanel<GradingPanel> {

    private final JTable table;
    private final AbstractTableModel tableModel;
    private final GUIPanel panel;
    private final SelectionPanel selectionPanel;
    private final Grader grader;
    private final AtomicBoolean ignoreTableChange = new AtomicBoolean(false);

    public RubricPanel(Grader g){
        this.grader = g;
        this.panel = new GUIPanel();
        this.selectionPanel = new SelectionPanel();
        tableModel = new DefaultTableModel(new String[]{"Selection", "Name", "Points", "Desc"},5);

        table = new JTable(tableModel){
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 2:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        table.setVisible(true);
    }

    private Component getComponent(int row, int col){
        return ((DefaultCellEditor) table.getCellEditor(row, col)).getComponent();
    }

    public void selectItem(int idx, boolean isSelected){
        JTextField nameLabel = (JTextField) getComponent(idx, 1);
        JTextField descLabel = (JTextField) getComponent(idx, 3);
        JCheckBox selectionLabel = (JCheckBox) getComponent(idx, 0);
        JTextField pointLabel = (JTextField) getComponent(idx, 2);
        Color c;

        if(isSelected){
            c = Color.GREEN;
        }else{
            c = UIManager.getColor("Table.gridColor");
        }

        table.setValueAt(true, idx, 0);

        pointLabel.setOpaque(true);
        pointLabel.setBackground(c);
        nameLabel.setBackground(c);
        descLabel.setBackground(c);
        selectionLabel.setBackground(c);
    }

    @Override
    public void init(JFrame frame, GradingPanel pane) {
        super.init(frame, pane);
        JScrollPane spane = new JScrollPane(table);
        spane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        spane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        selectionPanel.init(frame, panel);

        panel.setLayout(new GridLayout(2, 1));
        panel.add(spane);
        panel.add(selectionPanel);

        add(panel);

        tableModel.addTableModelListener(e -> {
            if(!ignoreTableChange.get()){
                ignoreTableChange.set(true);
                System.out.println("Table Event Occurred: [Row:" + e.getFirstRow() + " -> " + e.getLastRow() + ", Col:" + e.getColumn() + "]");
                for(int r = 0; r <= e.getLastRow(); ++r){
                    if(e.getColumn() == 0){
                        selectItem(r, (boolean) table.getValueAt(r, 0));
                    }
                }
                ignoreTableChange.set(false);
            }
        });
    }

    public SelectionPanel getSelectionPanel() {
        return selectionPanel;
    }

    @Override
    public void destroy(JFrame frame, GradingPanel panel) {
        super.destroy(frame, panel);
        selectionPanel.destroy(frame, panel);
    }


    public class SelectionPanel extends GUIPanel {

        private final JButton leftButton;
        private final JButton searchButton;
        private final JButton rightButton;
        private final JTextArea statsLabel;

        public SelectionPanel() {
            leftButton = new JButton("Left");
            searchButton = new JButton("Search");
            rightButton = new JButton("Right");
            statsLabel = new JTextArea();
        }

        public void leftButton(ActionEvent e){
            if(grader.getCurrentRubricIndex() >= 1){
                grader.setRubric(grader.getCurrentRubricIndex() - 1);
            }
        }

        public void selectionButton(ActionEvent e){

        }

        public void rightButton(ActionEvent e){
            if(grader.getCurrentRubricIndex() < grader.getRubrics().size()){
                grader.setRubric(grader.getCurrentRubricIndex() + 1);
            }
        }

        @Override
        public void init(JFrame frame, GUIPanel panel) {
            super.init(frame, panel);

            statsLabel.setEditable(false);
            statsLabel.setHighlighter(null);
            statsLabel.setOpaque(false);

            leftButton.addActionListener(this::leftButton);
            searchButton.addActionListener(this::selectionButton);
            rightButton.addActionListener(this::rightButton);

            setLayout(new GridBagLayout());

            GridBagConstraints cons = new GridBagConstraints();
            cons.fill = GridBagConstraints.BOTH;

            cons.gridx = 0;
            cons.gridy = 0;
            add(statsLabel, cons);

            cons.gridx = 0;
            cons.gridy = 1;
            add(leftButton, cons);

            cons.gridx = 1;
            cons.gridy = 1;
            add(searchButton, cons);

            cons.gridx = 2;
            cons.gridy = 1;
            add(rightButton, cons);

            grader.setRubric(-1);
        }

        @Override
        public void destroy(JFrame frame, GUIPanel panel) {
            super.destroy(frame, panel);

        }

        public JButton getLeftButton() {
            return leftButton;
        }

        public JButton getSearchButton() {
            return searchButton;
        }

        public JButton getRightButton() {
            return rightButton;
        }

        public JTextArea getStatsLabel() {
            return statsLabel;
        }
    }
}
