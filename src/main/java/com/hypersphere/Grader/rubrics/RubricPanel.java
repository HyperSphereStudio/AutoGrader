package com.hypersphere.Grader.rubrics;

import com.hypersphere.GUI.GUIPanel;
import com.hypersphere.Grader.Grader;
import com.hypersphere.Grader.GraderStartFrame;
import com.hypersphere.Grader.GradingPanel;

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

        private JFrame frame;
        private final JButton leftButton, searchButton, rightButton, saveButton, openButton;
        private final JTextArea statsLabel;

        public SelectionPanel() {
            leftButton = new JButton("Left");
            searchButton = new JButton("Search");
            rightButton = new JButton("Right");
            saveButton = new JButton("Save");
            openButton = new JButton("Open");
            statsLabel = new JTextArea();
        }

        public void left(ActionEvent e){
            if(grader.getCurrentRubricIndex() >= 1){
                grader.setRubric(grader.getCurrentRubricIndex() - 1);
            }
        }

        public void selection(ActionEvent e){

        }

        public void right(ActionEvent e){
            if(grader.getCurrentRubricIndex() < grader.getRubrics().size()){
                grader.setRubric(grader.getCurrentRubricIndex() + 1);
            }
        }

        public void open(ActionEvent e){
            save(null);
            frame.dispose();
            new GraderStartFrame();
        }

        public void save(ActionEvent e){
            grader.save();
        }

        @Override
        public void init(JFrame frame, GUIPanel panel) {
            super.init(frame, panel);

            this.frame = frame;

            statsLabel.setEditable(false);
            statsLabel.setHighlighter(null);
            statsLabel.setOpaque(false);

            leftButton.addActionListener(this::left);
            searchButton.addActionListener(this::selection);
            rightButton.addActionListener(this::right);
            openButton.addActionListener(this::open);
            saveButton.addActionListener(this::save);

            setLayout(new GridBagLayout());

            GridBagConstraints cons = new GridBagConstraints();
            cons.fill = GridBagConstraints.BOTH;

            cons.gridx = 0;
            cons.gridy = 0;
            cons.weightx = 3;
            add(statsLabel, cons);

            cons.gridx = 0;
            cons.gridy = 1;
            cons.weightx = 1;
            add(leftButton, cons);

            cons.gridx = 1;
            cons.gridy = 1;
            cons.weightx = 1;
            add(searchButton, cons);

            cons.gridx = 2;
            cons.gridy = 1;
            cons.weightx = 1;
            add(rightButton, cons);

            cons.gridx = 0;
            cons.gridy = 2;
            cons.weightx = 1;
            add(openButton, cons);

            cons.gridx = 1;
            cons.gridy = 2;
            cons.weightx = 1;
            add(saveButton, cons);

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
