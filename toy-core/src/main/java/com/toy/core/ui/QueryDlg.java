package com.toy.core.ui;

import io.netty.util.internal.StringUtil;
import io.vertx.core.Vertx;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class QueryDlg extends JDialog {

	private final JPanel contentPanel = new JPanel();
	Vertx vertx;

	/**
	 * Create the dialog.
	 */
	public QueryDlg(Vertx vertx) {
		this.vertx = vertx;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE))
		);
		
		JTextArea txtrtablehotelwhere = new JTextArea();
		splitPane.setLeftComponent(txtrtablehotelwhere);
		txtrtablehotelwhere.setText("{\n    \"_TABLE_\": \"hotel\",\n    \"_WHERE_\": \"id=399\",\n    \"RESOURCE_TYPE\": \"MYSQL\"\n}");
		
		JTextArea txtResult = new JTextArea();
		splitPane.setRightComponent(txtResult);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("发送");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String str = txtrtablehotelwhere.getText();
						if (StringUtil.isNullOrEmpty(str)) {
							JOptionPane.showMessageDialog(null, "请输入请求的参数，格式json", "", JOptionPane.ERROR_MESSAGE);
							return;
						}
						vertx.eventBus().send("data.query", str, message -> {
							txtResult.append(message.result().body().toString()+"\n");
						});
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("关闭");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
