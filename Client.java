import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;

public class Client{
	
	static Client cli;
	private Socket s;
	private DataInputStream dis;
	private JFrame frame;
	private int ly, lx, cx, cy;
	Panel base;
	private String color;
	
	public static void main(String[] args) throws Exception{
		
		cli = new Client();
		cli.runner();

	}
	
	public void runner() throws Exception{
		
		try{
			
			color = "black";
			s = new Socket("localhost", 7778);
			dis = new DataInputStream(s.getInputStream());
			lx = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			ly = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			cx = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			cy = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			color = dis.readUTF();
			opener(lx, ly, cx, cy, color);
			updatePoints();
			
		}catch(SocketException e){
			
			System.out.println("The tutor has left the connection.");
			
		}
		
	}
	
	public void updatePoints() throws Exception{
		
		while(true){
			
			dis = new DataInputStream(s.getInputStream());
			base.lastX = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			base.lastY = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			base.currX = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			base.currY = Integer.parseInt(dis.readUTF());
			dis = new DataInputStream(s.getInputStream());
			base.color = dis.readUTF();
			base.repaint();
			
		}
		
	}
	
	public void opener(int lastX, int lastY, int currX, int currY, String color){
		
		frame = new JFrame("Client Painter");
		frame.setSize(400, 400);  //setSize
		frame.setLocation(320, 17);  //setLocation
		frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
		base = new Panel(lastX, lastY, currX, currY, color);  //create Main JPanel instance
		frame.setContentPane(base);  //setContentPane
		frame.setResizable(false);  //setResizable false
		frame.setVisible(true);  //setVisible
		
	}

}

class Panel extends JPanel{

	int lastX;
	int lastY;
	int currX;
	int currY;
	String color;
	
	public Panel(int lastX, int lastY, int currX, int currY, String color){
		
		this.lastX = lastX;
		this.lastY = lastY;
		this.currX = currX;
		this.currY = currY;
		this.color = color;
		repaint();
		
	}
	
	public void paintComponent(Graphics g){
		
		if(color.equals("black")){
			
			g.setColor(Color.BLACK);
			
		}
		
		else if(color.equals("red")){
			
			g.setColor(Color.RED);
			
		}
		
		else{
			
			g.setColor(Color.GREEN);
			
		}
		
		g.drawLine(lastX, lastY, currX, currY);
		
	}
	
}
