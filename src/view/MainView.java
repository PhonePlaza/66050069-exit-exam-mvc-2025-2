package view;

import javax.swing.*;
import java.awt.*;

/**
 * MainView - หน้าจอหลัก
 */
public class MainView extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JTabbedPane tabbedPane;

    private RumourListView rumourListView;
    private RumourDetailView rumourDetailView;
    private SummaryView summaryView;

    public MainView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("ระบบติดตามข่าวลือ - Rumor Tracking System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        // Main layout with tabs
        tabbedPane = new JTabbedPane();

        // Tab 1: Rumour List (with detail card)
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        rumourListView = new RumourListView(this);
        rumourDetailView = new RumourDetailView(this);

        contentPanel.add(rumourListView, "LIST");
        contentPanel.add(rumourDetailView, "DETAIL");

        tabbedPane.addTab("รายการข่าวลือ", contentPanel);

        // Tab 2: Summary View
        summaryView = new SummaryView();
        tabbedPane.addTab("สรุปผล", summaryView);

        // Refresh when tab changes
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 0) {
                rumourListView.loadData();
            } else if (tabbedPane.getSelectedIndex() == 1) {
                summaryView.loadData();
            }
        });

        add(tabbedPane);
    }

    public void showRumourList() {
        cardLayout.show(contentPanel, "LIST");
        rumourListView.loadData();
    }

    public void showRumourDetail(String rumourId) {
        rumourDetailView.loadRumour(rumourId);
        cardLayout.show(contentPanel, "DETAIL");
    }
}
