package view;

import controller.RumourController;
import model.Rumour;
import util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * RumourListView - หน้ารวมข่าวลือ
 */
public class RumourListView extends JPanel {

    private JTable rumourTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> sortComboBox;

    private RumourController controller;
    private MainView mainView;

    public RumourListView(MainView mainView) {
        this.mainView = mainView;
        this.controller = new RumourController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("รายการข่าวลือทั้งหมด");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        // Sort Panel (no refresh button)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JLabel sortLabel = new JLabel("เรียงตาม:");
        sortComboBox = new JComboBox<>(new String[] {
                "จำนวนรายงาน (มาก→น้อย)",
                "จำนวนรายงาน (น้อย→มาก)",
                "ความน่าเชื่อถือ (น้อย→มาก)",
                "ความน่าเชื่อถือ (มาก→น้อย)"
        });
        sortComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        controlPanel.add(sortLabel);
        controlPanel.add(sortComboBox);
        headerPanel.add(controlPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "รหัส", "หัวข้อข่าว", "แหล่งที่มา", "วันที่สร้าง",
                "ความน่าเชื่อถือ", "สถานะ", "จำนวนรายงาน" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rumourTable = new JTable(tableModel);
        rumourTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rumourTable.setRowHeight(25);

        // Double-click to view detail
        rumourTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = rumourTable.getSelectedRow();
                    if (row >= 0) {
                        String rumourId = (String) tableModel.getValueAt(row, 0);
                        mainView.showRumourDetail(rumourId);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(rumourTable);
        add(scrollPane, BorderLayout.CENTER);

        // Footer - BIGGER and more visible instruction
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(255, 243, 205)); // light yellow
        footerPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 2));

        JLabel infoLabel = new JLabel("*** ดับเบิลคลิกที่แถวเพื่อดูรายละเอียดและรายงานข่าวลือ ***");
        infoLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        infoLabel.setForeground(new Color(133, 100, 4)); // dark yellow/brown
        footerPanel.add(infoLabel);

        add(footerPanel, BorderLayout.SOUTH);
    }

    public void loadData() {
        tableModel.setRowCount(0);

        List<Rumour> rumours;
        int sortIndex = sortComboBox.getSelectedIndex();

        switch (sortIndex) {
            case 0:
                rumours = controller.getRumoursSortedByReportCountDesc();
                break;
            case 1:
                rumours = controller.getRumoursSortedByReportCountAsc();
                break;
            case 2:
                rumours = controller.getRumoursSortedByCredibilityAsc();
                break;
            case 3:
                rumours = controller.getRumoursSortedByCredibilityDesc();
                break;
            default:
                rumours = controller.getRumoursSortedByReportCountDesc();
        }

        for (Rumour rumour : rumours) {
            Object[] row = {
                    rumour.getRumourId(),
                    rumour.getTitle(),
                    rumour.getSource(),
                    rumour.getCreatedDate(),
                    rumour.getCredibilityScore() + "%",
                    Constants.getStatusDisplay(rumour.getStatus()),
                    rumour.getReportCount()
            };
            tableModel.addRow(row);
        }
    }
}
