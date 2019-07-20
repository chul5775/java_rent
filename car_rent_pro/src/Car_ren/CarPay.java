package Car_ren;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Formatter;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class CarPay extends JFrame implements ActionListener{
	
	static String R_daeil, R_banil, R_yea, result;
	static int don;
	Container container;
	double bohum;
	
	JPanel picPn;
	String sql, sql2, imgPath; //--- 그림 파일 경로 변수
	JLabel lblPic, lblRirum, lblRbunho, lblspace, lblRjong, lblRdaeil, lblRdaeilsu, lblRbanil,
			lblRprice, lblbo, lblResult, lblCnum, lblCbun, lblCirum, lblCjunhwa, lblCjuso, lblClic, lblRcolor, lblRdist, lblRsago;
	JTextField txtRirum, txtRbunho, txtRjong, txtRdaeil, txtRbanil, txtRdaeilsu, txtRprice, txtbo, txtResult,
	txtCnum, txtCbun, txtCirum, txtCjunhwa, txtCjuso, txtClic, txtRcolor, txtRdist, txtRsago;
	
	JRadioButton  rb1;
    JRadioButton  rb2;
    JRadioButton  rb3;
	
    int iTotal = 0;	// 자료의 갯수
	int iLast = 0; 	// 마지막 레코드 번호
    
	JButton btnGae, btnBack;
	
	Connection conn;
	PreparedStatement pstmt, pstmt2;
	ResultSet rs, rs2;
	
	
	public CarPay() {
		
		
		super("결제시스템");
		  
		design();
	    addListener();
	    accDb();

	    display();
	    displayCos();

	}
	
	
	private void design() {
		
		 this.setLayout(new GridLayout(6,1)); 
		
		//이미지 패널----------------------------------------------------

	      picPn=new JPanel();
	      picPn.setLocation(100, 0);
	      picPn.setSize(300, 240);
	      lblPic = new JLabel();
	      //lblPic.setPreferredSize(new Dimension(1000, 300));
	      picPn.add(lblPic);
	      
	      JPanel pn1 = new JPanel();
	      lblRirum = new JLabel("차이름 : ");

	      lblRbunho = new JLabel("차번호 : ");
	      lblRcolor = new JLabel("색상 : ");
	      lblRdist = new JLabel("주행거리 : " );
	      lblRsago = new JLabel("사고여부 : ");
	      txtRirum = new JTextField(7);
	      txtRbunho = new JTextField(12);
	      txtRcolor = new JTextField(6);
	      txtRdist = new JTextField(8);
	      txtRsago = new JTextField(8);
	      
	      
	      pn1.add(lblRirum);
	      pn1.add(txtRirum);
	      pn1.add(lblRbunho);
	      pn1.add(txtRbunho);
	      pn1.add(lblRcolor);
	      pn1.add(txtRcolor);
	      pn1.add(lblRdist);
	      pn1.add(txtRdist);
	      pn1.add(lblRsago);
	      pn1.add(txtRsago);
	      

	      
	      JPanel pn2 = new JPanel();
	      lblRjong = new JLabel("차종 : ");
	      lblRdaeilsu = new JLabel("대여일수 : ");
	      lblRdaeil = new JLabel("대여일 : ");
	      lblRbanil = new JLabel("반납일 : ");
	      
	      txtRjong = new JTextField(15);
	      txtRdaeilsu = new JTextField(15);
	      txtRdaeil = new JTextField(15);
	      txtRbanil = new JTextField(15);
	      
	      pn2.add(lblRjong);
	      pn2.add(txtRjong);
	      
	      pn2.add(lblRdaeilsu);
	      pn2.add(txtRdaeilsu);
	      pn2.add(lblRdaeil);
	      pn2.add(txtRdaeil);
	      
	      pn2.add(lblRbanil);
	      pn2.add(txtRbanil);
	      
	      JPanel pn3 = new JPanel();
	      lblRprice = new JLabel("대여 요금 : ");
	      lblbo = new JLabel("자기부담금 : ");
	      
	      txtRprice = new JTextField(10);
	  
	      pn3.add(lblRprice);
	      pn3.add(txtRprice);
	      pn3.add(lblbo);

	      rb1 = new JRadioButton("5만원"); //JRadioButton 생성
	      rb2 = new JRadioButton("30만원"); 
	      rb3 = new JRadioButton("70만원"); 
	      
	   // 버튼 그룹 설정
	      
	      ButtonGroup  group = new ButtonGroup();
	      group.add(rb1);
	      group.add(rb2); 
	      group.add(rb3);

	      pn3.add(rb1);  
	      pn3.add(rb2);
	      pn3.add(rb3);
	      
	      rb1.addItemListener(new MyItemListener());
	      rb2.addItemListener(new MyItemListener());
	      rb3.addItemListener(new MyItemListener());


	      lblbo = new JLabel("보험료 : ");
	      txtbo = new JTextField(10);
	      
	      pn3.add(lblbo);
	      pn3.add(txtbo);
	      
	      JLabel lbl1 = new JLabel("            ");
	      pn3.add(lbl1);	      
	      
	      lblResult = new JLabel("※총 결제 금액 : ");
	      txtResult = new JTextField(10);
	      txtResult.setFont(new Font("Fixedsys", Font.BOLD, 15));
	      txtResult.setForeground(Color.red);
	      
	      pn3.add(lblResult);
	      pn3.add(txtResult);

	      JPanel pn4 = new JPanel();
	      lblCbun = new JLabel("번호 : ");
	      lblCirum = new JLabel("이름 : ");
	      lblCjunhwa = new JLabel("전화 : ");
	      lblCjuso = new JLabel("주소 : ");
	      lblClic = new JLabel("면허 : ");
	      lblCnum = new JLabel("차량 시리얼 번호 : ");
	      
	      txtCbun = new JTextField(4);
	      txtCirum = new JTextField(9);
	      txtCjunhwa = new JTextField(16);
	      txtCjuso = new JTextField(38);
	      txtClic = new JTextField(14);
	      txtCnum = new JTextField(3);

	      pn4.add(lblCbun);
	      pn4.add(txtCbun);
	      pn4.add(lblCirum);
	      pn4.add(txtCirum);
	      pn4.add(lblCjunhwa);
	      pn4.add(txtCjunhwa);
	      pn4.add(lblCjuso);
	      pn4.add(txtCjuso);
	      pn4.add(lblClic);
	      pn4.add(txtClic);
	      JLabel lbl2 = new JLabel("                                   ");
	      pn4.add(lbl2);	  
	      pn4.add(lblCnum);
	      pn4.add(txtCnum);

	      JPanel pn5 = new JPanel();
	      btnGae = new JButton("결제");
	      btnGae.setPreferredSize(new Dimension(100, 50));

	      btnBack = new JButton("취소");
	      btnBack.setPreferredSize(new Dimension(100, 50));

	      pn5.add(btnGae);
	      
	      pn5.add(btnBack);

	      
	      add(picPn);
	      add(pn1);
	      add(pn2);
	      add(pn3);
	      add(pn4);
	      add(pn5);
	      

	      setResizable(false);
	      setBounds(900, 100, 500, 800);
	      setBackground(Color.white);
	      setVisible(true);
	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      txtRirum.setEnabled(false);
	      txtRbunho.setEnabled(false);
	      txtRcolor.setEnabled(false);
	      txtRdist.setEnabled(false);
	      txtRsago.setEnabled(false);
	      txtRjong.setEnabled(false);
	      txtRdaeil.setEnabled(false);
	      txtRbanil.setEnabled(false);
	      txtRdaeilsu.setEnabled(false);
	      txtRprice.setEnabled(false);
	      txtRdaeil.setEnabled(false);
	      txtCbun.setEditable(false);
	      txtCnum.setEditable(false);


	}
	
	private void addListener() {
		btnGae.addActionListener(this);
		btnBack.addActionListener(this);

	}
	
	private void accDb() {
			try {
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "123");
				


				} catch (Exception e) {
					System.out.println("accDb err : " + e);

				}
			}
		

	private void display() {
		
		sql = "select * from car where R_num = ?";
		//이미지 출력!!!!!! 
		try {
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, Car_dae.R_yea);
//			pstmt.setString(1, "1");
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				imgPath = rs.getString("R_image");
	        	dispImage();
	        	
			txtRirum.setText(rs.getString("r_irum"));
			txtRbunho.setText(rs.getString("R_bunho"));
			txtRcolor.setText(rs.getString("r_color"));
			txtRdist.setText(rs.getString("r_dist"));
			 if(rs.getString("r_sago").equals("y")) {
		            txtRsago.setText("사고이력 있음");
		            txtRsago.setForeground(Color.red);
		         }else {
		            txtRsago.setText("무사고차량");
		            txtRsago.setForeground(Color.black);
		         }
			 
			 txtCnum.setText(rs.getString("R_num"));
			 txtRjong.setText(rs.getString("r_jong"));
			 txtRdaeil.setText(CarMain.R_daeil);
			 txtRbanil.setText(CarMain.R_banil);
			 //대여일수 구하기
			 String d1 = CarMain.R_daeil;
			 String d2 = CarMain.R_banil;

				 SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
				 java.util.Date FirstDate = format.parse(d1);
				 java.util.Date SecondDate = format.parse(d2);
				 
				 long calDate = FirstDate.getTime() - SecondDate.getTime(); 
				 
				 long calDateDays = calDate / ( 24*60*60*1000); 
				 calDateDays = Math.abs(calDateDays);
				 
				 String aa = String.valueOf(calDateDays);
				 txtRdaeilsu.setText(aa);

			 
			 int price = rs.getInt("r_price") * Integer.valueOf(aa);
			 txtRprice.setText(String.valueOf(price));
			 
			 don = price;
			 
			 
			}
			
			
        	
		} catch (Exception e2) {
			System.out.println("display err :" + e2);
		}

	}
	public void displayCos() {
		sql2 = "select * from customer";
		try {
			pstmt2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs2 = pstmt2.executeQuery();
			rs2.last();
			iTotal = rs2.getRow();  //전체레코드 수를 알려준다
			iLast = rs2.getInt("c_bun");
			System.out.println(iTotal + " " + iLast);
			rs.first();
			
			txtCbun.setText(String.valueOf(iLast + 1));	//신규번호
			
		} catch (Exception e) {
			System.out.println("display Cos : err " + e);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnGae)) {
			if(txtCirum.getText().equals("")) {
				txtCirum.requestFocus();
				JOptionPane.showMessageDialog(this, "고객이름을 입력해주세요");
				return;
			}else if(txtCjunhwa.getText().equals("")) {
				txtCjunhwa.requestFocus();
				JOptionPane.showMessageDialog(this, "전화번호를 입력해주세요");
				return;
			}else if(txtCjuso.getText().equals("")) {
				txtCjuso.requestFocus();
				JOptionPane.showMessageDialog(this, "주소를 입력해주세요");
				return;
			}else if(txtClic.getText().equals("")) {
				txtClic.requestFocus();
				JOptionPane.showMessageDialog(this, "면허번호를 입력해주세요");
				return;
			}else {
				
				int sel = JOptionPane.showConfirmDialog(this, "결제 하시겠습니까?", "예", JOptionPane.YES_NO_OPTION);
				
				if(sel == JOptionPane.YES_OPTION) {
					
					try {
						sql = "insert into customer values(?,?,?,?,?,?,?,?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setString(1, txtCbun.getText());	//번호
						pstmt.setString(2, txtCirum.getText());	//이름
						pstmt.setString(3, txtCjunhwa.getText());	//전화
						pstmt.setString(4, txtCjuso.getText());	//주소			
						pstmt.setString(5, txtClic.getText());	//면허			
						pstmt.setString(6, CarMain.R_daeil);	//대여일
						pstmt.setString(7, CarMain.R_banil);	//반납일
						pstmt.setString(8, rs.getString("r_num"));	//차량번호
						pstmt.executeUpdate();
						
						sql2 = "update car set R_daeyn = 'y', R_daeil = ?, R_banil = ? where r_num = ?";
						pstmt2 = conn.prepareStatement(sql2);
						pstmt2.setString(1, CarMain.R_daeil);
						pstmt2.setString(2, CarMain.R_daeil);
						pstmt2.setString(3, Car_dae.R_yea);
						pstmt2.executeUpdate();
						
					
					} catch (Exception e2) {
						// TODO: handle exception
					}
					new CarBill();
					
					
					
				}else {
					
					
				}
				
			
				
				
			}
		}else if(e.getSource().equals(btnBack)) {
			
			setVisible(false); //you can't see me!
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
		}
		
	}
	
	class MyItemListener implements ItemListener{

		public void itemStateChanged(ItemEvent e) {
			if(rb1.isSelected()) {
				 bohum = don * 0.3;
				 txtbo.setText(String.valueOf(bohum));
				 double tot = don + bohum;
				 txtResult.setText(String.valueOf(tot));
				 result = String.valueOf(tot);
			 }else if(rb2.isSelected()) {
				 bohum = don * 0.2;
				 txtbo.setText(String.valueOf(bohum));
				 double tot = don + bohum;
				 txtResult.setText(String.valueOf(tot));
				 result = String.valueOf(tot);
			 }else if(rb3.isSelected()) {
				 bohum = don * 0.1;
				 txtbo.setText(String.valueOf(bohum));
				 double tot = don + bohum;
				 txtResult.setText(String.valueOf(tot));
				 result = String.valueOf(tot);
			 }
		}
	}
	
	public static void main(String[] args) {
		new CarPay();
	}

}
