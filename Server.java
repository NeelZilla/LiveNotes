import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.text.SimpleDateFormat;

public class Server extends JFrame{

	private ServerSocket ss;
	private Poonal basey;
	private Socket s;
	private DataOutputStream dos;
	private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String color;
	
	public Server(){
		
		super("Server Painter");
		setSize(400, 400);  //setSize
		setLocation(17, 17);  //setLocation
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		basey = new Poonal(this);  //create Main JPanel instance
		setContentPane(basey);  //setContentPane
		setResizable(false);  //setResizable false
		setVisible(true);  //setVisible
		color = "black";
		
	} 
	
	public static void main(String[] args) throws Exception{
		
		Server ser = new Server();
		ser.runner();
		
	}
	
	public void runner() throws Exception{
		
		ss = new ServerSocket(7778);
		System.out.println("\nOther user has not connected yet...");
		s = ss.accept();
		System.out.println("\nUser has connected from: " + s.getInetAddress() + " at " + sdf.format(cal.getTime()));
		dos = new DataOutputStream(s.getOutputStream());
		sendIt();
		
	}
	
	public void sendIt() throws Exception{
		
		while(true){
			
			dos.writeUTF("" + basey.pad.lastX);
			dos.writeUTF("" + basey.pad.lastY);
			dos.writeUTF("" + basey.pad.currentX);
			dos.writeUTF("" + basey.pad.currentY);
			dos.writeUTF(color);
			
		}
		
	}

}

class Poonal extends JPanel{
	
	Server svr;
	Colors options;
	Panely pad;
	
	public Poonal(Server srvr){
		
		svr = srvr;
		setLayout(new BorderLayout());
		
		options = new Colors(svr);
		pad = new Panely(svr);
		
		add(options, BorderLayout.NORTH);
		add(pad, BorderLayout.CENTER);
		
	}
	
}

class Colors extends JPanel implements ActionListener{
	
	ButtonGroup bg;
	JRadioButton black;
	JRadioButton red;
	JRadioButton green;
	Server seervur;
	
	public Colors(Server srvr){
		
		seervur = srvr;
		
		setLayout(new FlowLayout());
		
		bg = new ButtonGroup();
		black = new JRadioButton("Black");
		red = new JRadioButton("Red");
		green = new JRadioButton("Green");
		
		black.addActionListener(this);
		red.addActionListener(this);
		green.addActionListener(this);
		
		bg.add(black);
		bg.add(red);
		bg.add(green);
		add(black);
		add(red);
		add(green);
		
	}

	public void actionPerformed(ActionEvent e){
		
		if(black.isSelected()){
			
			seervur.color = "black";
			
		}
		
		else if(red.isSelected()){
			
			seervur.color = "red";
			
		}
		
		else{
		
			seervur.color = "green";
			
		}
		
	}
	
}

class Panely extends JPanel implements MouseListener, MouseMotionListener{
	
	int currentX = 20;
	int currentY = 20;
	int lastX = 0;
	int lastY = 0;
	Server s;
	Graphics gra;
	
	public Panely(Server se){
		
		s = se;
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	public void paintComponent(Graphics g){
		
		gra = g;
		
		if(s.color.equals("black")){
			
			g.setColor(Color.BLACK);
			
		}
		
		else if(s.color.equals("red")){
			
			g.setColor(Color.RED);
			
		}
		
		else{
			
			g.setColor(Color.GREEN);
			
		}
		
		g.drawLine(lastX, lastY, currentX, currentY);
		
	}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		
		lastX = currentX = e.getX();
		lastY = currentY = e.getY();
		repaint();
		
	}

	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {
		
		lastX = currentX;
		lastY = currentY;
		currentX = e.getX();
		currentY = e.getY();
		repaint();
		
		
	}

	public void mouseMoved(MouseEvent e) {}
	
}

