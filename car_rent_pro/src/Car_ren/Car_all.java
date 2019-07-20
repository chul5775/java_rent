package Car_ren;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class Car_all extends JPanel {
	
	private DefaultTableModel mod;

	String sql;
	
	Connection conn;
	PreparedStatement pstmt, pstmt2;
	ResultSet rs, rs2;
	

	public Car_all() {

		design();	
		
	}

	
	public void design() {

		this.setLayout(new BorderLayout());
		this.setBorder(
		new TitledBorder(new TitledBorder("전체 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.LEFT));


		String[][] data = new String[0][7];
		String[] cols = { "번호", "이름", "종류", "대여 가능 여부", "대여일", "반납일", "색상", "가격" };
		
		mod = new DefaultTableModel(data, cols);
		JTable tab = new JTable(mod);

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
			pstmt = conn.prepareStatement("select * from car");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String r_bun = rs.getString("R_NUM");
				String r_irum = rs.getString("R_IRUM");
				String r_jong = rs.getString("R_JONG");
				String r_daeyn = rs.getString("R_DAEYN");

				String r_daeil = rs.getString("R_DAEIL");
				String r_banil = rs.getString("R_BANIL");
				String r_color = rs.getString("R_COLOR");
				String r_price = rs.getString("R_PRICE");

				String[] imsi = {r_bun, r_irum, r_jong, r_daeyn, r_daeil, r_banil, r_color, r_price};
				mod.addRow(imsi);
			
				
			}
		} catch (Exception e) {
			System.out.println("connect err : " + e);
		}
		tab.setPreferredScrollableViewportSize(new Dimension(10, 120));
		tab.setSelectionBackground(Color.green);
		tab.setEnabled(false);
		JScrollPane scroll = new JScrollPane(tab);
		this.add("Center", scroll);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));		
		
	}

	
	public void customer() {
		
	}
	
	public static void main(String[] args) {
		Car_all car_ca = new Car_all();
		JFrame frame = new JFrame();

		frame.getContentPane().add(car_ca);
		frame.setBounds(200, 200, 580, 400);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}