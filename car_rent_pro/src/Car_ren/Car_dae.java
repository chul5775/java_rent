package Car_ren;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.event.*;

import org.mariadb.jdbc.internal.util.PrepareStatementCache;

public class Car_dae extends JFrame implements ActionListener {
	static String R_daeil, R_banil, R_yea;

	JTabbedPane pane;
	JTextField txtYea, txtRdaeil, txtRbanil;
	String Daeyn, Irum, Ban;
	
	private JButton btnSelect;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	public Car_dae() {
		super("차량 정보 시스템");
		this.setLayout(new BorderLayout());

		pane = new JTabbedPane(JTabbedPane.LEFT);

		Car_all all = new Car_all();
		pane.addTab("전체", all);

		Car_sm small = new Car_sm();
		pane.addTab("소형차", small);

		Car_mid middle = new Car_mid();
		pane.addTab("중형차", middle);

		Car_big big = new Car_big();
		pane.addTab("대형차", big);

		Car_seung seung = new Car_seung();
		pane.addTab("승합차", seung);

		Car_suv suv = new Car_suv();
		pane.addTab("SUV", suv);

		Car_el elc = new Car_el();
		pane.addTab("전기차", elc);

		pane.setSelectedIndex(0);
		pane.requestFocus();

		this.setLayout(new BorderLayout());
		this.add("Center", pane);

		setBounds(250, 200, 900, 500);
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel cPn1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		txtRdaeil = new JTextField(15);
		txtRbanil = new JTextField(15);

		btnSelect = new JButton("예약");
		cPn1.add(new JLabel("차량 선택 (번호 입력) :	"));
		cPn1.add(txtYea = new JTextField(5));

		cPn1.add(btnSelect);
		cPn1.add(new JLabel("대여일 : "));
		cPn1.add(txtRdaeil);

		cPn1.add(new JLabel("반납일 : "));
		cPn1.add(txtRbanil);

		this.add("North", cPn1);
		btnSelect.addActionListener(this);

		txtRdaeil.setText(CarMain.R_daeil);
		txtRbanil.setText(CarMain.R_banil);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(btnSelect)) { // 번호 입력
			R_yea = new String(txtYea.getText());
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
				pstmt = conn.prepareStatement("select * from car where R_NUM = ?");
				pstmt.setString(1, R_yea);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					Daeyn = rs.getString("R_DAEYN");
					Irum = rs.getString("R_IRUM");
					Ban = rs.getString("R_BANIL");

				}

				
			} catch (Exception e2) {
				// TODO: handle exception
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (rs != null)
						pstmt.close();
					if (rs != null)
						conn.close();

				} catch (Exception e3) {
					// TODO: handle exception
				}

			}
			if (txtYea.getText().equals("")) { // 번호 입력 여부
				JOptionPane.showMessageDialog(this, "번호를 입력하세요!");

			} else if (Daeyn.equals("y")) { // 대여 여부
			
				JOptionPane.showMessageDialog(this, 
						"'" +Irum + "' 차량은 " 
						+ "현재 대여 중인 차량 입니다!\n" + "\n"
						+ "반납 예정일 :	 " + Ban);
				
			}

			else { // 예약 할 것인지 확인 여부.
				int sel = JOptionPane.showConfirmDialog(this, Irum + " 차량을 예약하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);

				if (sel == JOptionPane.YES_OPTION) { // 예 버튼 입력시.
					new CarPay();	//<--결제창 오픈.
					
				}

			}
		}
	}

	public static void main(String[] args) {
		new Car_dae();

	}
}
