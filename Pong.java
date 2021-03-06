import java.awt.*;
import java.awt.geom.*;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;

import javax.swing.JPanel;

public class Pong extends Canvas
{
	Point delta;
	Ellipse2D.Double ball;
	Rectangle paddle1;
	Rectangle paddle2;
	int leftScore = 0;
	int rightScore = 0;
	boolean status;
	String title = "SCORE";
	String announcement;
	
	public static void main( String[] args )
	{
		JFrame win = new JFrame("Pong");
		win.setSize(1010,735);
		win.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		win.add( new Pong() );
		win.setVisible(true);
	}

	public boolean start(){
		 return status = true;
	}

	public boolean stop(){
		return status = false;
	}

	public String winner(){
		if (leftScore > rightScore){
			announcement = "Left Wins!";
			title = announcement;
		}else if (rightScore > leftScore){
			announcement = "Right Wins!";
			title = announcement;

		}
		//  else if (rightScore == leftScore){
		// 	announcement ="It's a tie!";
		// 	title = "GAME OVER. " + announcement;
		// }  
		return announcement;
	}
	
	
	public Pong()
	{
		enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
		requestFocus();
		
		ball = new Ellipse2D.Double(500,350,20,20);
		delta = new Point(-5,5);
		paddle1 = new Rectangle(50,250,20,200);
		paddle2 = new Rectangle(900,250,20,200);
		
		Timer t = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				doStuff();
				repaint();
			}
		};
		
		start();		
		//t.schedule( new java.util.TimerTask()
		// if(status=true){
			t.schedule(tt,10,10);
		// } else {
		// 	tt.cancel(); 
		// 	leftScore = 0;
		// 	rightScore = 0;
		// 	status = true;
		// }
		
		// {
		// 	public void run()
		// 	{
		// 		doStuff();
		// 		repaint();
		// 	}
		// }, 10, 10);	
		

	}

	public void paint( Graphics g )
	{
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.black);
		g2.fill(ball);
		
		g2.setColor(Color.blue);
		g2.fill(paddle1);
		g2.fill(paddle2);

		Graphics2D g2d = (Graphics2D) g;

		Font font = new Font("Serif", Font.BOLD, 50);
		g2d.setFont(font);
		g2d.setColor(Color.RED);

		g.drawString(title, 400, 100);

		Font font2 = new Font("Serif", Font.PLAIN, 22);


		Graphics2D g2e = (Graphics2D) g;
		g2e.setFont(font2);
		g2e.setColor(Color.BLUE);
		g.drawString("Left: "+leftScore, 400, 150);
		g.drawString("Right: "+rightScore, 500, 150);
	}

	public void processKeyEvent(KeyEvent e)
	{
		if ( e.getID() == KeyEvent.KEY_PRESSED )
		{
			if ( e.getKeyCode() == KeyEvent.VK_W )
			{
				paddle1.y -= 40; 
			}
			if ( e.getKeyCode() == KeyEvent.VK_S )
			{
				paddle1.y += 40;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP )
			{
				paddle2.y -= 40;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN )
			{
				paddle2.y += 40;
			}
		}
	}
	
	public void doStuff()
	{
		ball.x += delta.x;
		ball.y += delta.y;

		// and bounce if we hit a wall
		if ( ball.y < 0 || ball.y+20 > 700 )
			delta.y = -delta.y;
		if ( ball.x < 0 )
			delta.x = -delta.x;
			
		// check if the ball is hitting the paddle
		if ( ball.intersects(paddle1) )
			delta.x = -delta.x;
		
		if ( ball.intersects(paddle2))
			delta.x = -delta.x;	
			
		// check for scoring
		if ( ball.x > 1000 )
		{
			ball.x = 500;
			ball.y = 350;
			delta = new Point(5,-5);// x<10 has reversed new Point to x>1000 so the serve reverses on point
			//win
			leftScore++;
		}

		if ( ball.x < 10 )
		{
			ball.x = 500;
			ball.y = 350;
			delta = new Point(-5,5);
			rightScore++;
		}

		if (leftScore == 10){
			status = false;
			//rough way to stop the game on win. timer runs till complete. can't timer.cancel() if running
			//already
			ball.x = 500;
			ball.y = 350;
			//delta = null;
			winner();
		}

		if (rightScore == 10){
			status = false;
			//rough way to stop the game on win. timer runs till complete. can't timer.cancel() if running
			//already
			ball.x = 500;
			ball.y = 350;
			//delta = null;
			winner();
		}
		
		
	}
	
	public boolean isFocusable() { return true;	}
}