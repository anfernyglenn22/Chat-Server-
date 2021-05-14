import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Client {

	private static JFrame frmClient;
	private static JTextField msgClient;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	static JTextArea msgArea;
	public static String uName = "";
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		
		uName = JOptionPane.showInputDialog(frmClient,"Enter Name");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client window = new Client();
					frmClient.setTitle(uName);
					Client.frmClient.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		try {
			s = new Socket("127.0.0.1", 1431);
			din = new DataInputStream(s.getInputStream());
			dout = new DataOutputStream(s.getOutputStream());
			
			
			String msgin="";
			while(!msgin.contentEquals("end")) 
			{
				msgin = din.readUTF();
				msgArea.setText(msgArea.getText().trim()+"\n" + Server.formatter.format(new Date()) + " " +"Server: " +msgin);
			}
			s.close();
			
			
			
			
		}catch (Exception ec) {
			
		}
		
		
	}

	/**
	 * Create the application.
	 */
	public Client() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 643, 509);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setForeground(Color.WHITE);
		msgArea.setBackground(Color.DARK_GRAY);
		msgArea.setBounds(10, 11, 699, 325);
		frmClient.getContentPane().add(msgArea);
		
		msgClient = new JTextField();
		msgClient.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
				try 
				{
				String msgout ="";
				msgout = msgClient.getText().trim();
				dout.writeUTF(msgout);
				msgArea.append("\n"+ Server.formatter.format(new Date()) + " " + uName + ":"+ msgout);
				msgClient.setText("");
				}catch (Exception ecc) 
				{
				}
				}
			}
		});
		msgClient.setBounds(10, 354, 426, 105);
		frmClient.getContentPane().add(msgClient);
		msgClient.setColumns(10);
		
		
		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try 
				{
				String msgout ="";
				msgout = msgClient.getText().trim();
				dout.writeUTF(msgout);
				msgArea.append("\n"+ Server.formatter.format(new Date()) + " " + uName + ":"+ msgout);
				msgClient.setText("");
				}catch (Exception ecc) 
				{
					
				}
			}
		});
		sendBtn.setBounds(446, 354, 171, 105);
		frmClient.getContentPane().add(sendBtn);
	}

	
	public static void stop()
		{
		frmClient.setVisible(false);
		}	
}
