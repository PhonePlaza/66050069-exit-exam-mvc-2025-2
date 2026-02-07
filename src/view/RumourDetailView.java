package view;

import controller.ReportController;
import controller.RumourController;
import controller.UserController;
import model.Report;
import model.Rumour;
import model.User;
import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * RumourDetailView - หน้ารายละเอียดข่าวลือ
 */
public class RumourDetailView extends JPanel {

    private JLabel idLabel, titleLabel, sourceLabel, dateLabel;
    private JLabel scoreLabel, statusLabel, reportCountLabel, verifiedLabel;
    private JComboBox<String> userComboBox;
    private JComboBox<String> reportTypeComboBox;
    private JComboBox<String> verifierComboBox;
    private JButton reportButton, backButton, verifyTrueButton, verifyFalseButton;

    private RumourController rumourController;
    private ReportController reportController;
    private UserController userController;
    private MainView mainView;

    private String currentRumourId;
    private List<User> users;
    private List<User> verifiers;

    public RumourDetailView(MainView mainView) {
        this.mainView = mainView;
        this.rumourController = new RumourController();
        this.reportController = new ReportController();
        this.userController = new UserController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButton = new JButton("<- กลับ");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.showRumourList();
            }
        });
        headerPanel.add(backButton);

        JLabel titleHeader = new JLabel("รายละเอียดข่าวลือ");
        titleHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
        headerPanel.add(titleHeader);
        add(headerPanel, BorderLayout.NORTH);

        // Detail Panel
        JPanel detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBorder(BorderFactory.createTitledBorder("ข้อมูลข่าวลือ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;

        addLabelRow(detailPanel, gbc, row++, "รหัสข่าวลือ:", idLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "หัวข้อ:", titleLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "แหล่งที่มา:", sourceLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "วันที่สร้าง:", dateLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "ความน่าเชื่อถือ:", scoreLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "สถานะ:", statusLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "จำนวนรายงาน:", reportCountLabel = new JLabel());
        addLabelRow(detailPanel, gbc, row++, "การตรวจสอบ:", verifiedLabel = new JLabel());

        // Verify Section (VERIFIER only)
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        JPanel verifyPanel = new JPanel(new GridBagLayout());
        verifyPanel.setBorder(BorderFactory.createTitledBorder("ตรวจสอบข่าว (เฉพาะผู้ตรวจสอบ)"));
        GridBagConstraints vgbc = new GridBagConstraints();
        vgbc.insets = new Insets(3, 5, 3, 5);
        vgbc.anchor = GridBagConstraints.WEST;

        // Verifier selector
        vgbc.gridx = 0;
        vgbc.gridy = 0;
        verifyPanel.add(new JLabel("ผู้ตรวจสอบ:"), vgbc);
        vgbc.gridx = 1;
        verifiers = userController.getVerifiers();
        String[] verifierNames = new String[verifiers.size()];
        for (int i = 0; i < verifiers.size(); i++) {
            verifierNames[i] = verifiers.get(i).getUserId() + " - " + verifiers.get(i).getName();
        }
        verifierComboBox = new JComboBox<>(verifierNames);
        verifyPanel.add(verifierComboBox, vgbc);

        // Verify buttons
        vgbc.gridx = 0;
        vgbc.gridy = 1;
        vgbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        verifyTrueButton = new JButton("ข่าวจริง (TRUE)");
        verifyTrueButton.setBackground(new Color(40, 167, 69));
        verifyTrueButton.addActionListener(e -> verifyRumour(Constants.VERIFY_TRUE));
        buttonPanel.add(verifyTrueButton);

        verifyFalseButton = new JButton("ข่าวเท็จ (FALSE)");
        verifyFalseButton.setBackground(new Color(220, 53, 69));
        verifyFalseButton.addActionListener(e -> verifyRumour(Constants.VERIFY_FALSE));
        buttonPanel.add(verifyFalseButton);
        verifyPanel.add(buttonPanel, vgbc);

        // Warning note
        vgbc.gridx = 0;
        vgbc.gridy = 2;
        vgbc.gridwidth = 2;
        JLabel noteLabel = new JLabel(
                "<html><font color='#856404'>* หมายเหตุ: หลังจากยืนยันผลแล้ว จะไม่สามารถส่งรายงานเพิ่มได้อีก</font></html>");
        noteLabel.setFont(new Font("Tahoma", Font.ITALIC, 11));
        verifyPanel.add(noteLabel, vgbc);

        detailPanel.add(verifyPanel, gbc);

        // Report Form Panel
        JPanel reportPanel = new JPanel(new GridBagLayout());
        reportPanel.setBorder(BorderFactory.createTitledBorder("รายงานข่าวลือนี้"));
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(5, 5, 5, 5);
        gbc2.anchor = GridBagConstraints.WEST;

        gbc2.gridx = 0;
        gbc2.gridy = 0;
        reportPanel.add(new JLabel("ผู้รายงาน:"), gbc2);
        gbc2.gridx = 1;
        users = userController.getAllUsers();
        String[] userNames = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userNames[i] = users.get(i).getUserId() + " - " + users.get(i).getName();
        }
        userComboBox = new JComboBox<>(userNames);
        reportPanel.add(userComboBox, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 1;
        reportPanel.add(new JLabel("ประเภท:"), gbc2);
        gbc2.gridx = 1;
        reportTypeComboBox = new JComboBox<>(new String[] {
                Constants.REPORT_DISTORTED + " - บิดเบือน",
                Constants.REPORT_INCITE + " - ปลุกปั่น",
                Constants.REPORT_FALSE_INFO + " - ข้อมูลเท็จ"
        });
        reportPanel.add(reportTypeComboBox, gbc2);

        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.gridwidth = 2;
        reportButton = new JButton("ส่งรายงาน");
        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitReport();
            }
        });
        reportPanel.add(reportButton, gbc2);

        // Center Panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(detailPanel, BorderLayout.CENTER);
        centerPanel.add(reportPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void addLabelRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JLabel valueLabel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        valueLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        panel.add(valueLabel, gbc);
    }

    public void loadRumour(String rumourId) {
        this.currentRumourId = rumourId;
        Rumour rumour = rumourController.getRumourById(rumourId);

        if (rumour != null) {
            idLabel.setText(rumour.getRumourId());
            titleLabel.setText(rumour.getTitle());
            sourceLabel.setText(rumour.getSource());
            dateLabel.setText(rumour.getCreatedDate());
            scoreLabel.setText(rumour.getCredibilityScore() + "%");
            statusLabel.setText(Constants.getStatusDisplay(rumour.getStatus()));
            reportCountLabel.setText(String.valueOf(rumour.getReportCount()));

            if (rumour.isVerified()) {
                verifiedLabel.setText(Constants.getVerificationDisplay(rumour.getVerificationResult()));
                reportButton.setEnabled(false);
                // Still allow re-verification (buttons stay enabled)
            } else {
                verifiedLabel.setText("ยังไม่ตรวจสอบ");
                reportButton.setEnabled(true);
            }
        }
    }

    private void submitReport() {
        if (currentRumourId == null)
            return;

        int userIndex = userComboBox.getSelectedIndex();
        if (userIndex < 0 || userIndex >= users.size())
            return;

        String userId = users.get(userIndex).getUserId();
        String reportType = ((String) reportTypeComboBox.getSelectedItem()).split(" - ")[0];

        // Check if report is allowed
        String canAdd = reportController.canAddReport(userId, currentRumourId);
        if (canAdd != null) {
            JOptionPane.showMessageDialog(this, canAdd, "Cannot Report", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Create Report
        Report report = new Report();
        report.setReporterId(userId);
        report.setRumourId(currentRumourId);
        report.setReportDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        report.setReportType(reportType);

        boolean success = reportController.addReport(report);

        if (success) {
            JOptionPane.showMessageDialog(this, "Report submitted successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            loadRumour(currentRumourId); // Refresh
        } else {
            JOptionPane.showMessageDialog(this, "An error occurred", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verifyRumour(String result) {
        if (currentRumourId == null)
            return;

        // Check if verifier is selected
        int verifierIndex = verifierComboBox.getSelectedIndex();
        if (verifierIndex < 0 || verifierIndex >= verifiers.size()) {
            JOptionPane.showMessageDialog(this, "Please select a verifier", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Rumour rumour = rumourController.getRumourById(currentRumourId);

        // If already verified, show confirmation
        if (rumour != null && rumour.isVerified()) {
            String currentResult = rumour.getVerificationResult();

            int confirm = JOptionPane.showConfirmDialog(this,
                    "This rumour is already verified as: " + currentResult + "\n" +
                            "Do you want to change it to: " + result + "?",
                    "Confirm Change",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        boolean success = rumourController.verifyRumour(currentRumourId, result);

        if (success) {
            String msg = result.equals(Constants.VERIFY_TRUE) ? "Rumour verified as TRUE (Real News)"
                    : "Rumour verified as FALSE (Fake News)";
            JOptionPane.showMessageDialog(this, msg, "Verification Complete", JOptionPane.INFORMATION_MESSAGE);
            loadRumour(currentRumourId); // Refresh
        } else {
            JOptionPane.showMessageDialog(this, "Verification failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
