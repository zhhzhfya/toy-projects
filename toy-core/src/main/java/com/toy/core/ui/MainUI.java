package com.toy.core.ui;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.JToolBar;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;

import com.toy.core.util.CacheUtil;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.oschina.j2cache.CacheObject;

public class MainUI extends JFrame {

	private JPanel contentPane;

	private Vertx vertx;
	private JTable table;
	private JTextField key;
	private JTextField cacheName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainUI frame = new MainUI(Vertx.vertx());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainUI(Vertx vertx) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 440,
				Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 268, Short.MAX_VALUE));

		String[] headers = {};
		Object[][] cellData = null;
		DefaultTableModel model = new DefaultTableModel(cellData, headers) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("系统管理", null, panel_1, null);

		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("软件部");
		node1.add(new DefaultMutableTreeNode("小花"));
		node1.add(new DefaultMutableTreeNode("小虎"));
		node1.add(new DefaultMutableTreeNode("小龙"));

		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("销售部");
		node2.add(new DefaultMutableTreeNode("小叶"));
		node2.add(new DefaultMutableTreeNode("小雯"));
		node2.add(new DefaultMutableTreeNode("小夏"));

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("职员管理");

		top.add(new DefaultMutableTreeNode("总经理"));
		top.add(node1);
		top.add(node2);

		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setDividerLocation(180);

		JToolBar toolBar = new JToolBar();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
						.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
					.addContainerGap())
		);

		JButton btnNewButton = new JButton("发送消息");
		btnNewButton.setIcon(new ImageIcon(MainUI.class.getResource("/com/toy/core/ui/img/application_form_add.png")));
		toolBar.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("查询");
		btnNewButton_1.setIcon(new ImageIcon(MainUI.class.getResource("/com/toy/core/ui/img/world_go.png")));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				QueryDlg queryDlg = new QueryDlg(vertx);
				queryDlg.setModal(true);
				queryDlg.setLocationRelativeTo(null);
				queryDlg.setVisible(true);
			}
		});
		toolBar.add(btnNewButton_1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventDlg dlg = new EventDlg();
				dlg.setModal(true);
				dlg.setLocationRelativeTo(null);
				dlg.setVisible(true);
			}
		});

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPane_1);

		JTree tree = new JTree(top);
		scrollPane_1.setViewportView(tree);
		panel_1.setLayout(gl_panel_1);

		JPanel panel = new JPanel();
		tabbedPane.addTab("DB查询", null, panel, null);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(113);

		JButton button = new JButton("执行");

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addGroup(
				Alignment.LEADING,
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel.createParallelGroup(Alignment.LEADING).addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
										.addComponent(button, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup().addContainerGap().addComponent(button).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE).addContainerGap()));

		JTextArea txtrSelectFrom = new JTextArea();
		txtrSelectFrom.setBackground(new Color(255, 255, 224));
		splitPane.setLeftComponent(txtrSelectFrom);
		txtrSelectFrom.setText("SELECT * FROM hotel limit 3");

		JScrollPane scrollPane = new JScrollPane();
		splitPane.setRightComponent(scrollPane);

		table = new JTable(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		panel.setLayout(gl_panel);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("缓存管理", null, panel_2, null);
		
		JToolBar toolBar_1 = new JToolBar();
		
		JTextArea cacheResult = new JTextArea();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(cacheResult, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
						.addComponent(toolBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(toolBar_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cacheResult, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JButton btnNewButton_2 = new JButton("test cache");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CacheUtil.test();
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(MainUI.class.getResource("/com/toy/core/ui/img/world_go.png")));
		toolBar_1.add(btnNewButton_2);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar_1.add(separator);
		
		JLabel lblCache = new JLabel("cache");
		toolBar_1.add(lblCache);
		
		cacheName = new JTextField();
		cacheName.setText("cache1");
		toolBar_1.add(cacheName);
		cacheName.setColumns(5);
		
		JLabel lblNewLabel = new JLabel("key:");
		toolBar_1.add(lblNewLabel);
		
		key = new JTextField();
		key.setText("key1");
		toolBar_1.add(key);
		key.setColumns(5);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon(MainUI.class.getResource("/com/toy/core/ui/img/zoom.png")));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CacheObject cache = CacheUtil.getCache(cacheName.getText(), key.getText());
				cacheResult.setText(cache.getValue().toString());
			}
		});
		toolBar_1.add(btnNewButton_3);
		panel_2.setLayout(gl_panel_2);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 清空行、列
				model.setRowCount(0);
				model.setColumnCount(0);
				vertx.eventBus().send("db.query", txtrSelectFrom.getText(), message -> {
					Runnable updateAComponent = new Runnable() {
						public void run() {
							JsonObject json = (JsonObject) message.result().body();
							JsonArray columns = (JsonArray) json.getValue("columns");
							for (Object col : columns) {
								model.addColumn(col);
							}
							JsonArray js = (JsonArray) json.getValue("data");
							for (Object j : js) {
								model.addRow(((JsonArray) j).getList().toArray());
							}
							System.out.println("-------->" + message.result().body());
						}
					};
					SwingUtilities.invokeLater(updateAComponent);
				});
			}
		});
		contentPane.setLayout(gl_contentPane);
	}
}
