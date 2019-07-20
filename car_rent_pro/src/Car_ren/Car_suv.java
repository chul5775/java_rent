package Car_ren;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class Car_suv extends JPanel{
	private DefaultTableModel mod;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public Car_suv() {
		design();

	}

	public void design() {
		this.setLayout(new BorderLayout());
		this.setBorder(new TitledBorder(new TitledBorder("SUV 정보"), "", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.LEFT));
		JPanel cPn1 = new JPanel(new FlowLayout(FlowLayout.LEFT));

		cPn1.add(new JLabel("SUV 전체"));
		this.add("North", cPn1);

		String[][] data = new String[0][7];
		String[] cols = {"번호", "이름", "종류","대여 가능 여부", "대여일", "반납일", "색상", "가격" };
		mod = new DefaultTableModel(data, cols);
		JTable tab = new JTable(mod);

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
			pstmt = conn.prepareStatement("select * from car where R_JONG='suv'");
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
		tab.setEnabled(false);
		tab.setSelectionBackground(Color.green);
		JScrollPane scroll = new JScrollPane(tab);
		this.add("Center", scroll);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	public static void main(String[] args) {
		Car_suv car_ca = new Car_suv();
		JFrame frame = new JFrame();
		frame.getContentPane().add(car_ca);
		frame.setBounds(200, 200, 580, 400);
		frame.setVisible(true);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
