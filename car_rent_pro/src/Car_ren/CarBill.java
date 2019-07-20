package Car_ren;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CarBill extends JFrame implements ActionListener{
	
	static String R_daeil, Rbanil ,R_yae, result;
	JLabel lblgume, lblPic, lblRirum, lblResult, lblRdaeil,lblRbanil ;
	JPanel picPn;
	String sql, sql2, imgPath;
	Connection conn;
	PreparedStatement pstmt, pstmt2;
	ResultSet rs, rs2;
	JButton btnOk;

	
	public CarBill() {
		super("결제완료 영수증");
		
		design();
		
		accdb();
		display();
		
		
	}
	
	public void design() {
		JPanel pn1 = new JPanel();
		lblgume = new JLabel("예약이 정상적으로 완료되었습니다");
		lblgume.setFont(lblgume.getFont().deriveFont(40.0f));
		pn1.setBounds(0,50,800,100);
		pn1.add(lblgume);
		add(pn1);
		
		picPn=new JPanel();
		lblPic = new JLabel();
		//lblPic.setPreferredSize(new Dimension(1000, 300));
		picPn.setBounds(170,150,200,200);
		picPn.add(lblPic);
		add(picPn);
		
		JPanel pn2 = new JPanel();
		
		JLabel lbls = new JLabel("주문번호 : 2165421");
		lbls.setFont(lbls.getFont().deriveFont(20.0f));
		lblRirum = new JLabel();
		lblRirum.setFont(lblRirum.getFont().deriveFont(20.0f));
		lblRdaeil = new JLabel("대여일 : " + CarMain.R_daeil);
		lblRdaeil.setFont(lblRdaeil.getFont().deriveFont(20.0f));
		lblRbanil = new JLabel("반납일 : " + CarMain.R_banil);
		lblRbanil.setFont(lblRbanil.getFont().deriveFont(20.0f));
		lblResult = new JLabel("결제금액 : " + CarPay.result + "원");
		lblResult.setFont(lblResult.getFont().deriveFont(20.0f));
		
		btnOk = new JButton("닫기");

		pn2.setBounds(400,150,200,200);
		pn2.add(lbls);
		pn2.add(lblRirum);
		pn2.add(lblRdaeil);
		pn2.add(lblRbanil);
		pn2.add(lblResult);
		pn2.add(btnOk);
		add(pn2);
		
		JPanel pn3 = new JPanel();
		add(pn3);
		
		
		
		setResizable(false);
		setBounds(500, 200, 800, 450);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnOk.addActionListener(this);
	}
	
	public void display() {
		
		sql = "select * from car where R_num = ?";
		//이미지 출력!!!!!! 
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, Car_dae.R_yea);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				imgPath = rs.getString("R_image");
	        	dispImage();
	        	
	        	lblRirum.setText("차종 : " + rs.getString("r_irum"));
	        
	        	
	        	
				
			}
			
			
		}catch (Exception e) {
			System.out.println("display err : " + e);
		}
		


	}
	
	private void dispImage() {
	      if(imgPath != null) {
	         String dir = "C:/work/carimage/";   //Linux방식
//	         String dir = "C:\\work\\carimage";  //window방식
	         ImageIcon icon = new ImageIcon(dir + imgPath);
	         lblPic.setIcon(icon);
	         lblPic.setToolTipText("경로는 " + imgPath.toLowerCase()); //툴팁
	         if(icon != null )
	            lblPic.setText(null);
	         
	         else
	            lblPic.setText("그림없음");
	      }
	   }
	
	private void accdb() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
			


			} catch (Exception e) {
				System.out.println("accDb err : " + e);

			}
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOk)) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new CarBill();

	}

}
