package view;

import controller.RumourController;
import model.Rumour;
import util.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * SummaryView - หน้าสรุปผล
 */
public class SummaryView extends JPanel {

    private JTable panicTable;
    private JTable verifiedTable;
    private DefaultTableModel panicTableModel;
    private DefaultTableModel verifiedTableModel;

    private RumourController controller;

    public SummaryView() {
        this.controller = new RumourController();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header (no refresh button)
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("สรุปผลข่าวลือ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Split Panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5);

        // Panic Table
        JPanel panicPanel = new JPanel(new BorderLayout());
        JLabel panicLabel = new JLabel("ข่าวลือที่เข้าสู่สถานะตื่นตระหนก (PANIC)");
        panicLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        panicLabel.setForeground(new Color(220, 53, 69)); // Red
        panicPanel.add(panicLabel, BorderLayout.NORTH);

        String[] columns = { "รหัส", "หัวข้อ", "แหล่งที่มา", "จำนวนรายงาน", "สถานะ" };
        panicTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        panicTable = new JTable(panicTableModel);
        panicTable.setRowHeight(25);
        panicPanel.add(new JScrollPane(panicTable), BorderLayout.CENTER);

        splitPane.setTopComponent(panicPanel);

        // Verified Table
        JPanel verifiedPanel = new JPanel(new BorderLayout());
        JLabel verifiedLabel = new JLabel("ข่าวลือที่ถูกตรวจสอบแล้ว");
        verifiedLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        verifiedLabel.setForeground(new Color(40, 167, 69)); // Green
        verifiedPanel.add(verifiedLabel, BorderLayout.NORTH);

        String[] verifiedColumns = { "รหัส", "หัวข้อ", "แหล่งที่มา", "ผลการตรวจสอบ" };
        verifiedTableModel = new DefaultTableModel(verifiedColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        verifiedTable = new JTable(verifiedTableModel);
        verifiedTable.setRowHeight(25);
        verifiedPanel.add(new JScrollPane(verifiedTable), BorderLayout.CENTER);

        splitPane.setBottomComponent(verifiedPanel);

        add(splitPane, BorderLayout.CENTER);
    }

    public void loadData() {
        // Load Panic Rumours
        panicTableModel.setRowCount(0);
        List<Rumour> panicRumours = controller.getPanicRumours();
        for (Rumour rumour : panicRumours) {
            Object[] row = {
                    rumour.getRumourId(),
                    rumour.getTitle(),
                    rumour.getSource(),
                    rumour.getReportCount(),
                    Constants.getStatusDisplay(rumour.getStatus())
            };
            panicTableModel.addRow(row);
        }

        // Load Verified Rumours
        verifiedTableModel.setRowCount(0);
        List<Rumour> verifiedRumours = controller.getVerifiedRumours();
        for (Rumour rumour : verifiedRumours) {
            Object[] row = {
                    rumour.getRumourId(),
                    rumour.getTitle(),
                    rumour.getSource(),
                    Constants.getVerificationDisplay(rumour.getVerificationResult())
            };
            verifiedTableModel.addRow(row);
        }
    }
}
