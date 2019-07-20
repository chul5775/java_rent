package Car_ren;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class CarMain extends JFrame implements ActionListener{
	
		static JFrame calFrame; 
		static JTextField txtRdaeil, txtRbanil;
		
		BufferedImage img = null;
		BufferedImage imgLogo = null;
		JLabel lblLogo, lblRdaeil, lblRbanil, lblSpace;
		JButton btnye;
		JButton btnRdaeil, btnRbanil;

		static String R_daeil;
		static String R_banil;
	
	public CarMain() {
		setTitle("JAVA RENTCAR - 찾아주셔서 감사합니다.");
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(dimension.width, dimension.height);
		layeredPane.setLayout(null);
		
		try {
			img = ImageIO.read(new File("C:/work/carimage/mainimage.jpeg"));
			
			imgLogo = ImageIO.read(new File("C:/work/carimage/logo.png"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
		}
		
		myPanel panel = new myPanel();
		panel.setSize(dimension.width, dimension.height);
		layeredPane.add(panel);
		
		JPanel panel2 = new JPanel();
		GridLayout layout = new GridLayout(7,1);
		panel2.setLayout(layout);
		
		lblSpace = new JLabel();
		
		//대여 일 설정
		JPanel daePn = new JPanel();
		lblRdaeil = new JLabel("     대여일시");
		lblRdaeil.setFont(lblRdaeil.getFont().deriveFont(20.0f));
		lblRdaeil.setForeground(new Color(246, 122, 66));		
		txtRdaeil = new JTextField(10);
		txtRdaeil.setFont(new Font("Fixedsys", Font.BOLD, 30));
		btnRdaeil = new JButton("설정");
		btnRdaeil.setMargin(new Insets(11, 3, 11, 3));
		daePn.setBackground(new Color(18, 53, 70));
		daePn.add(txtRdaeil);
		daePn.add(btnRdaeil);
		
		
		
		//반납 일 설정
		JPanel banPn = new JPanel();
		lblRbanil = new JLabel("     반납일시");
		
		lblRbanil.setFont(lblRbanil.getFont().deriveFont(20.0f));
		lblRbanil.setForeground(new Color(246, 122, 66));
		txtRbanil = new JTextField(10);
		txtRbanil.setFont(new Font("Fixedsys", Font.BOLD, 30));
		btnRbanil = new JButton("설정");
		btnRbanil.setMargin(new Insets(11, 3, 11, 3));
		banPn.setBackground(new Color(18, 53, 70));
		banPn.add(txtRbanil);
		banPn.add(btnRbanil);
		
		
		
		btnye = new JButton("예 약 하 기");
		btnye.setForeground(Color.white);
		btnye.setFont(btnye.getFont().deriveFont(20.0f));
		btnye.setBackground(new Color(51, 51, 255));
		
		
		

		lblLogo = new JLabel(new ImageIcon(imgLogo));
		panel2.setBounds(1300, 220, 400, 600);
		panel2.setBackground(new Color(18, 53, 70));
		panel2.setVisible(true);
		panel2.add(lblLogo);
		panel2.add(lblRdaeil);
		panel2.add(daePn);
		panel2.add(lblRbanil);
		panel2.add(banPn);
		panel2.add(lblSpace);
		panel2.add(btnye);

		add(panel2);
		
		
		
		
		add(layeredPane);
		
		this.setLocation(0, 0);
		this.setSize(dimension.width, dimension.height);
		this.setVisible(true);
		setResizable(false);
		setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addListener();
	}
	
	public void addListener() {
	      btnye.addActionListener(this);
	      btnRdaeil.addActionListener(this);
	      btnRbanil.addActionListener(this);
	   }
	
	class myPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnye)) {
			if(txtRdaeil.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "대여일을 입력하시오");
				return;
			}else if(txtRbanil.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "반납일을 입력하시오");
				return;
			}else {
				R_daeil = new String(txtRdaeil.getText());
				R_banil = new String(txtRbanil.getText());

		       

				new Car_dae();
			}

		}else if(e.getSource().equals(btnRdaeil)) {
	         DaeCal daecal = new DaeCal();
	         calFrame = new JFrame("대여일 설정");
	         calFrame.add(daecal); //DaeCal은 패널 프레임안에 패널을 넣은 것.
	         calFrame.setBounds(1650,450,250,200);
	         calFrame.setVisible(true);
			
		}else if(e.getSource().equals(btnRbanil)) {
	         BanCal bancal = new BanCal();
	         calFrame = new JFrame("반납일 설정");
	         calFrame.add(bancal); //Bancal은 패널 프레임안에 패널을 넣은 것.
	         calFrame.setBounds(1650,600,250,200);
	         calFrame.setVisible(true);
			
		}
		
	}
		
		public static void main(String[] args) {
			
			new CarMain();
			

	
	}
	
	

}
