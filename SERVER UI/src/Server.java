import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Server {

	private static JFrame frmServer;
	private static JTextField msgServer;
	static ServerSocket cs;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	static JTextArea msgArea;
	public static SimpleDateFormat formatter = new SimpleDateFormat("[hh:mm a]");
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		String name = Client.uName;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server window = new Server();
					Server.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	String msgin = "";
	
	try 
	{
		cs = new ServerSocket(1431);
		s = cs.accept();
		
		din = new DataInputStream(s.getInputStream());
		dout = new DataOutputStream(s.getOutputStream());

		
		
		while(!msgin.contentEquals("end")) 
		{
			
			msgin = din.readUTF();
			msgArea.setText(msgArea.getText().trim() + "\n" + formatter.format(new Date()) + " " + name + ": " +msgin);
		}
		cs.close();
		s.close();
		
	}
	catch (Exception e)
	{
		
	}
	}
	//main
	
	
	

	/**
	 * Create the application.
	 */
	public Server() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 643, 509);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setForeground(Color.GREEN);
		msgArea.setLineWrap(true);
		msgArea.setWrapStyleWord(true);
		msgArea.setEditable(false);
		msgArea.setBackground(Color.DARK_GRAY);
		msgArea.setBounds(10, 11, 607, 305);
		frmServer.getContentPane().add(msgArea);
		
		
		
		msgServer = new JTextField();
		msgServer.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) 
				{
					try 
					{
					String msgout = "";
					msgout = msgServer.getText().trim();
					dout.writeUTF(msgout);
					msgArea.append("\n" + formatter.format(new Date()) + " " +"Server:"+ msgout);
					msgServer.setText("");
					}catch(Exception es) 
					{
						
					}
				}
			}
		});
		msgServer.setBounds(10, 354, 426, 105);
		frmServer.getContentPane().add(msgServer);
		msgServer.setColumns(10);
		
		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
				String msgout = "";
				msgout = msgServer.getText().trim();
				dout.writeUTF(msgout);
				msgArea.append("\n" + formatter.format(new Date()) + " " +"Server:"+ msgout);
				msgServer.setText("");
				}catch(Exception es) 
				{
					
				}
			}
		});
		sendBtn.setBounds(446, 354, 171, 105);
		frmServer.getContentPane().add(sendBtn);
	}
	
	public static void stop() {
		Server.frmServer.setVisible(false);
	}

}
