package Car_ren;

import java.awt.*;

import java.awt.event.*;

import java.util.*;



class MyWindowAdapter extends WindowAdapter { 

        // implement WindowListener methods 

public void windowClosing(WindowEvent we) { 

System.exit(0); 

}

} 



public class CalendarFrame extends Frame implements ActionListener{

FlowLayout f = new FlowLayout(FlowLayout.LEFT,3,5);

GridLayout gl =  new GridLayout(1,7);

Calendar nowcal = Calendar.getInstance();

//배치관리자 중복사용을 위한 Panel

Panel selectPanel = new Panel();

Panel labelPanel = new Panel();

Panel dayPanel[] = new Panel[6];

//년월선택및 확인 컴포넌트

Choice cyear = new Choice();

Choice cmonth = new Choice();

Label lyear = new Label("년");

Label lmonth = new Label("월");

Button bt = new Button("확인");

//요일 컴포넌트

Label l[] = new Label[7];

//날자 컴포넌트

Label lday[] = new Label[32];

//생성자

public CalendarFrame() {

this("달력"); 

}

public CalendarFrame(String s){

super(s);



//년 초이스 컴포넌트

for (int i = 2005; i < 2015; i++) {

String year = " " + i;

cyear.add(year);

}

//월 초이스 컴포넌트

for (int i = 1; i < 13; i++) {

String month = " " + i;

cmonth.add(month);

}

//버튼 액션리스너 설정

bt.addActionListener(this);

//요일 컴포넌트

        String lstr[] = {"  일","월","화","수","목","금","토"};

for (int i = 0; i < 7; i++) {

l[i] = new Label(lstr[i]);

}

//날짜 컴포넌트

lday[0] = new Label(" ");

for (int i = 1; i < lday.length; i++) {

String sday = " ";
if(i < 10)
sday = "   "+ i;
else
sday = " "+ i;
lday[i] = new Label(sday);
lday[i].setSize(5,5);

} 

//현재 날짜 받기 

int year = nowcal.get(Calendar.YEAR);
int month = nowcal.get(Calendar.MONTH);
cyear.select(year-2005);
cmonth.select(month); 

//년월일및확인 컴포넌트 배치
selectPanel.setLayout(f);
selectPanel.add(cyear);
selectPanel.add(lyear);
selectPanel.add(cmonth);
selectPanel.add(lmonth);
selectPanel.add(bt);

add(selectPanel);

//요일컴포넌트배치

    labelPanel.setLayout(gl);
for (int i = 0; i < l.length; i++) {
     labelPanel.add(l[i]);
    }

add(labelPanel);
    //날짜 컴포넌트 배치 메소드 호출
daytext(year,month);
//프레임설정 
setSize(235,250);
setVisible(true);
setLayout(f);
addWindowListener(new MyWindowAdapter());

}
/**
 * 날짜 컴포넌트 배치 메소드
 * @param year 
 * @param month
 */
public void daytext(int year, int month ){
Calendar setcal = Calendar.getInstance();
setcal.set(year,month,1);
//그달의 첫번째날의 요일 받기
int firstday = setcal.get(Calendar.DAY_OF_WEEK) ;
//그달의 마지막날 받기
int lastday = setcal.getActualMaximum(Calendar.DAY_OF_MONTH);
//날짜 인덱스
int dayindex = 1;
int sunday = 1;
// int saturday;
//배치
dayPanel[0] = new Panel();
dayPanel[0].setLayout(new GridLayout(6,7));
//폰트
//Font redfont = new Font("",Color.red);
//1일이 되기전 공백 채우기
    for (int i = 1; i < firstday; i++) {
dayPanel[0].add(new Label("  "));
    sunday++;
    }

//1일 부터 날짜 채우기
for (int j = 0; j < lastday; j++) {
    if(sunday%7 == 1)lday[dayindex].setForeground(Color.red);
    else if(sunday%7 == 0)lday[dayindex].setForeground(Color.blue);
    else lday[dayindex].setForeground(Color.black);
    sunday++;
    dayPanel[0].add(lday[dayindex++]);
}

//나머지 공백채우기
while (dayPanel[0].getComponentCount() < 42) {
dayPanel[0].add(new Label("  "));
}
this.add(dayPanel[0]);
}

/**
 * 버튼 액션리스너 
 * 버튼클릭 시 달력 출력
 */

@Override

public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
int year = cyear.getSelectedIndex() + 2005;
int month = cmonth.getSelectedIndex();
System.out.println(year+" "+month);
//날짜 컴포넌트 지우기
dayPanel[0].setVisible(false);

//날짜배치 컴포넌트 호출
daytext(year,month);
    //다시 그리기
setVisible(true);
}


public static void main(String[] args) {
	new CalendarFrame();

}

}