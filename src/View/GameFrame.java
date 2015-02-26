package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.*;

import Model.GameThread;
import Model.ImageResources;
import Model.Map;
import Model.Player;


@SuppressWarnings("serial")
public class GameFrame extends JFrame {
	private static final int WINDOW_SIZE_X = 456;
	private static final int WINDOW_SIZE_Y = 651;
	private static String VERSION = "A Maze Quest " + "\n" + "Version:" + " Alpha 13.37" + "\n" + "� Jonas Brothers";
	private ImageResources res = new ImageResources();
	private JMenuBar menuBar;
	private static StatusPanel statusPanel;
	private static JPanel startMenuPanel;
	private static MapPanel mapPanel;
	private JLabel labelIcon1;
	private JLabel labelIcon2;
	private JLabel labelIcon3;
	private JLabel choiceLabel;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton resetButton1;
	private JButton resetButton2;
	private static Player loadedPlayer = null;		// only here for load/save , remove if changed
	private static Map mapRef = null;							// only here for load/save , remove if changed
	private int playerChoice;
	private GridBagConstraints gc;
	
	public GameFrame (String title){
		super(title);	
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
		setResizable(false);
		setLocationRelativeTo(null);
		mapPanel = new MapPanel();
		add(mapPanel);
		mapPanel.setVisible(false);
		startMenuPanel = new JPanel(new GridBagLayout());
		gc = new GridBagConstraints();
		GridBagConstraints gc = new GridBagConstraints();
		add(startMenuPanel,BorderLayout.CENTER);
		statusPanel = new StatusPanel();
		add(statusPanel, BorderLayout.SOUTH);
		hideStatusPanel();
		addMenu();
		createLabels();
		createButtons();
		
	}

	public void showStatusPanel(){
		statusPanel.setVisible(true);
	}

	public static void hideStatusPanel(){
		statusPanel.setVisible(false);
	}
	
	public static void hideMapPanel(){
		mapPanel.setVisible(false);
	}
	
	public static void showStartMenuPanel(){
		startMenuPanel.setVisible(true);
	}
	private void createLabels(){
		labelIcon1 = new JLabel(res.getImgIcon("test"));
		labelIcon2 = new JLabel(res.getImgIcon("test"));
		labelIcon3 = new JLabel(res.getImgIcon("test"));
		choiceLabel = new JLabel("Choose player");
		choiceLabel.setFont(choiceLabel.getFont().deriveFont(40f));
		gc.insets = new Insets(2,2,2,2);
		gc.anchor = GridBagConstraints.CENTER;
	    gc.gridwidth = 2;
	    gc.gridheight = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        startMenuPanel.add(choiceLabel, gc);
        gc.anchor = GridBagConstraints.WEST;
        gc.gridheight = 2;
	    gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        startMenuPanel.add(labelIcon1, gc);
        gc.gridx = 0;
        gc.gridy = 4;
        startMenuPanel.add(labelIcon2, gc);
        gc.gridx = 0;
        gc.gridy = 6;
        startMenuPanel.add(labelIcon3, gc);
	}
	private void addMenu(){
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		//Sub-choice of File menu
		JMenuItem backMenuItem = new JMenuItem("Back", KeyEvent.VK_B);
		backMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));
		backMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(mapPanel.isShowing() == true){
					button1.setText("Player 1");
					button2.setText("Player 2");
					hideMapPanel();
					hideStatusPanel();
					showStartMenuPanel();
					}
				else if( mapPanel.isShowing() == false) {
					button1.setText("Player 1");
					button2.setText("Player 2");
					}
			}
		});
		fileMenu.add(backMenuItem);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);

		JMenuItem versionMenuItem = new JMenuItem("Version", KeyEvent.VK_V);
		versionMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
		versionMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,VERSION);
			}
		});
		helpMenu.add(versionMenuItem);

		// Finally add bar to top of frame
		setJMenuBar(menuBar);
	}

	private void createButtons(){
		button1 = new JButton("Player 1");
		button2 = new JButton("Player 2");
		button3 = new JButton("Map 3");
		resetButton1 = new JButton("Reset player");
		resetButton2 = new JButton("Reset player");
		button1.setPreferredSize(new Dimension(150, 40));
		button2.setPreferredSize(new Dimension(150, 40));
		
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
				setResizable(true);
				setVisible(true);
				if(button1.getText().equals("Player 1")){
					button1.setText("Map 1");
					button2.setText("Map 2");
					playerChoice = 1;
					// g�r allt som ska g�ras n�r man trycker p� player 1
				}else{
					// g�r allt som ska g�ras n�r man trycker p� map 1
					mapPanel.createMap(1,playerChoice);
					mapPanel.setVisible(true);
					mapPanel.setFocusable(true);
					startMenuPanel.setVisible(false);
					showStatusPanel();
					add(mapPanel,BorderLayout.CENTER);
					(new Thread(new GameThread(mapPanel, statusPanel))).start();
				}
			}
		}); 
		

		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setSize(WINDOW_SIZE_X, WINDOW_SIZE_Y);
				setResizable(true);
				setVisible(true);
				if(button2.getText().equals("Player 2")){
					button1.setText("Map 1");
					button2.setText("Map 2");
					playerChoice = 2;
					// g�r allt som ska g�ras n�r man trycker p� player 1
				}else{
					// g�r allt som ska g�ras n�r man trycker p� map 1
					mapPanel.createMap(2,playerChoice);
					mapPanel.setVisible(true);
					mapPanel.setFocusable(true);
					startMenuPanel.setVisible(false);
					showStatusPanel();
					add(mapPanel,BorderLayout.CENTER);
					(new Thread(new GameThread(mapPanel, statusPanel))).start();
				}

			}
		});
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridheight = 1;
        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.SOUTH;
		startMenuPanel.add(button1, gc);
        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.NORTH;
        startMenuPanel.add(resetButton1, gc);
        gc.gridx = 1;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.SOUTH;
        startMenuPanel.add(button2, gc);
        gc.gridx = 1;
        gc.gridy = 5;
        gc.anchor = GridBagConstraints.NORTH;
		startMenuPanel.add(resetButton2, gc);
		
	}

	public void load(String fileName) {				// NOTE!! load-method in both GameFrame and Map
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
			loadedPlayer = (Player)in.readObject();
			in.close();
			System.out.println("Player loaded");
		}
		catch(Exception e) {
			System.out.println("LOAD FAILED \n");
			e.printStackTrace();
			System.exit(0);

		}
	}
}
