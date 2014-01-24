package com.teama.merc.ui;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MERCuryLaunchOptions extends JFrame
{
	private static final long serialVersionUID = 1L;

	static String game_name;
	static int default_width, default_height;

	private javax.swing.JButton start;
	private javax.swing.JButton exit;
	private javax.swing.JCheckBox vsync_setting;
	private javax.swing.JCheckBox fullscreen_setting;
	private javax.swing.JLabel game_title;
	private javax.swing.JLabel width_label;
	private javax.swing.JLabel height_label;
	private javax.swing.JPanel mpanel;
	private javax.swing.JTextField width_input;
	private javax.swing.JTextField height_input;

	public boolean started = false;

	public MERCuryLaunchOptions(String game_name, int default_width, int default_height)
	{
		MERCuryLaunchOptions.game_name = game_name;
		MERCuryLaunchOptions.default_width = default_width;
		MERCuryLaunchOptions.default_height = default_height;

		initComponents(game_name, default_width, default_height);
	}

	private void initComponents(String game_name, int default_width, int default_height)
	{
		mpanel = new JPanel();
		start = new JButton();
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				started = true;
			}
		});
		
		exit = new JButton();
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		game_title = new JLabel();
		width_label = new JLabel();
		height_label = new JLabel();
		width_input = new JTextField();
		height_input = new JTextField();
		vsync_setting = new JCheckBox();
		fullscreen_setting = new JCheckBox();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("MERCury Game Engine - Launch Options");
		setAlwaysOnTop(true);
		setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

		mpanel.setBackground(new java.awt.Color(153, 153, 153));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(mpanel);

		mpanel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));

		start.setText("Start Game");

		exit.setText("Exit");

		game_title.setFont(new java.awt.Font("Tahoma", 0, 24));
		game_title.setText(game_name);

		width_label.setText("Width");

		height_label.setText("Height");
		height_label.setToolTipText("");

		width_input.setText(String.valueOf(default_width));

		height_input.setText(String.valueOf(default_height));

		vsync_setting.setText("Enable VSync");

		fullscreen_setting.setText("Enable Fullscreen");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(mpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(width_label).addComponent(height_label)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(height_input).addComponent(width_input))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(game_title).addGroup(layout.createSequentialGroup().addComponent(vsync_setting).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(fullscreen_setting))).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(mpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(game_title).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(width_label).addComponent(width_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(height_label).addComponent(height_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(vsync_setting).addComponent(fullscreen_setting)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(start).addComponent(exit)).addContainerGap()));

		pack();
	}

	public static void main(String[] a)
	{
		createWindow("Test", 800, 600);
	}

	public static void createWindow(final String name, final int width, final int height)
	{
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} 
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(MERCuryLaunchOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} 
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(MERCuryLaunchOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} 
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(MERCuryLaunchOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(MERCuryLaunchOptions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new MERCuryLaunchOptions(name, width, height).setVisible(true);
			}
		});
	}
}
