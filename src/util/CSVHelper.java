package util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CSVHelper - อ่าน/เขียนไฟล์ CSV
 * ใช้ synchronized เพื่อป้องกัน data corruption
 */
public class CSVHelper {

    private static final CSVHelper instance = new CSVHelper();

    private CSVHelper() {
    }

    public static CSVHelper getInstance() {
        return instance;
    }

    /**
     * อ่านไฟล์ CSV ทั้งหมด
     * 
     * @param filePath path ของไฟล์
     * @return List ของ String[] (แต่ละ row)
     */
    public synchronized List<String[]> readCSV(String filePath) {
        List<String[]> data = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return data;
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // ข้าม header
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                // แยก column ด้วย comma
                String[] values = line.split(",", -1); // -1 เพื่อเก็บ empty values
                data.add(values);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }

        return data;
    }

    // อ่าน header ของไฟล์ CSV
    public synchronized String[] readHeader(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            return new String[0];
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String line = br.readLine();
            if (line != null) {
                return line.split(",", -1);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV header: " + e.getMessage());
        }

        return new String[0];
    }

    // เขียนไฟล์ CSV ใหม่ทั้งหมด (พร้อม header)
    public synchronized void writeCSV(String filePath, String[] header, List<String[]> data) {
        // สร้าง directory ถ้ายังไม่มี
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))) {

            // เขียน header
            bw.write(String.join(",", header));
            bw.newLine();

            // เขียน data
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }

        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }
    }

    // เพิ่ม row ใหม่ต่อท้ายไฟล์ CSV
    public synchronized void appendCSV(String filePath, String[] row) {
        File file = new File(filePath);

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"))) {

            bw.write(String.join(",", row));
            bw.newLine();

        } catch (IOException e) {
            System.err.println("Error appending CSV: " + e.getMessage());
        }
    }
}
