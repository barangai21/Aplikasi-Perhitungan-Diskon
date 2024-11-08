package perhitungandiskonapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class PerhitunganDiskonApp {

    private JFrame frame;
    private JTextField priceField;
    private JComboBox<String> discountComboBox;
    private JButton calculateButton;
    private JLabel resultLabel;
    private JTextArea historyArea;
    private JTextField couponField;

    // Main method to run the application
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PerhitunganDiskonApp window = new PerhitunganDiskonApp();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // Constructor to initialize the GUI
    public PerhitunganDiskonApp() {
        frame = new JFrame("Aplikasi Perhitungan Diskon");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new FlowLayout());

        // Label harga asli
        JLabel priceLabel = new JLabel("Masukkan Harga Asli:");
        frame.getContentPane().add(priceLabel);

        // TextField untuk input harga asli
        priceField = new JTextField();
        priceField.setColumns(10);
        frame.getContentPane().add(priceField);

        // ComboBox untuk memilih diskon
        JLabel discountLabel = new JLabel("Pilih Persentase Diskon:");
        frame.getContentPane().add(discountLabel);

        String[] discountOptions = {"5%", "10%", "15%", "20%", "25%", "30%"};
        discountComboBox = new JComboBox<>(discountOptions);
        frame.getContentPane().add(discountComboBox);

        // Field untuk kode kupon
        JLabel couponLabel = new JLabel("Masukkan Kode Kupon (Opsional):");
        frame.getContentPane().add(couponLabel);

        couponField = new JTextField();
        couponField.setColumns(10);
        frame.getContentPane().add(couponField);

        // Tombol untuk hitung harga akhir
        calculateButton = new JButton("Hitung Harga Akhir");
        calculateButton.addActionListener(e -> calculateDiscount());
        frame.getContentPane().add(calculateButton);

        // Label untuk menampilkan hasil perhitungan
        resultLabel = new JLabel("Hasil: ");
        frame.getContentPane().add(resultLabel);

        // Area untuk menampilkan riwayat perhitungan
        historyArea = new JTextArea(5, 40);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        frame.getContentPane().add(scrollPane);
    }

    // Fungsi untuk menghitung harga akhir setelah diskon
    private void calculateDiscount() {
        String priceText = priceField.getText();
        String couponText = couponField.getText();
        String selectedDiscount = (String) discountComboBox.getSelectedItem();

        if (priceText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Harap masukkan harga asli!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Mengambil harga asli
            double price = Double.parseDouble(priceText);
            // Mengambil persentase diskon
            int discountPercentage = Integer.parseInt(selectedDiscount.replace("%", ""));
            // Menghitung jumlah diskon
            double discountAmount = price * discountPercentage / 100;
            // Menghitung harga akhir setelah diskon
            double finalPrice = price - discountAmount;

            // Menampilkan hasil
            DecimalFormat df = new DecimalFormat("#,###.00");
            String result = "Harga Asli: Rp " + df.format(price) + "\n" +
                            "Diskon: " + discountPercentage + "%\n" +
                            "Penghematan: Rp " + df.format(discountAmount) + "\n" +
                            "Harga Akhir: Rp " + df.format(finalPrice);
            
            resultLabel.setText("<html>" + result.replace("\n", "<br>") + "</html>");

            // Menambahkan riwayat perhitungan
            String history = "Harga Asli: Rp " + df.format(price) + ", Diskon: " + discountPercentage + "%, " +
                             "Harga Akhir: Rp " + df.format(finalPrice) + "\n";
            historyArea.append(history);

            // Cek apakah ada kode kupon
            if (!couponText.isEmpty()) {
                applyCoupon(couponText, finalPrice);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Harga yang dimasukkan tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Fungsi untuk menerapkan kupon diskon tambahan
    private void applyCoupon(String couponText, double finalPrice) {
        // Cek kode kupon dan diskon tambahan
        if ("DISKON50".equalsIgnoreCase(couponText)) {
            double discountCoupon = finalPrice * 0.5; // 50% diskon tambahan
            finalPrice -= discountCoupon;
            JOptionPane.showMessageDialog(frame, "Kupon berhasil digunakan! Penghematan tambahan: Rp " +
                    new DecimalFormat("#,###.00").format(discountCoupon), "Kupon Diskon", JOptionPane.INFORMATION_MESSAGE);
            resultLabel.setText("<html>Harga Akhir setelah Kupon: Rp " +
                    new DecimalFormat("#,###.00").format(finalPrice) + "</html>");
        } else if (!couponText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Kode kupon tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
