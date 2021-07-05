import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import javax.swing.JMenuBar;
import java.awt.Button;
import javax.swing.JToolBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.GridLayout;
import java.awt.Choice;
import java.awt.List;

public class DatabaseGUI {

	private JFrame frmDatabaseGui;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatabaseGUI window = new DatabaseGUI();
					window.frmDatabaseGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DatabaseGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDatabaseGui = new JFrame();
		frmDatabaseGui.setTitle("Database GUI");
		frmDatabaseGui.setBounds(100, 100, 594, 93);
		frmDatabaseGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		
		JPanel panel = new JPanel();
//		panel.setBounds(10, 11, 547, 30);
		
		JMenuBar menuBar = new JMenuBar();
		frmDatabaseGui.setJMenuBar(menuBar);
		
		JMenu fileMenu = new JMenu("Arquivo");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		// Alguns exemplos de consultas que podem ser feitas no banco de dados
		// são exibidos na forma de uma mensagem no item Exemplos de consultas
		JMenuItem consultasMenu = new JMenuItem("Exemplos de consultas");
		fileMenu.add(consultasMenu);
		
		consultasMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextArea ta = new JTextArea();
				JOptionPane.showMessageDialog(consultasMenu,
						"Exemplos de consulta \n\n"
						+ "Para selecionar dados: SELECT * FROM \"Institucional\".\"CURSOS\"\n"
						+ "Para inserir dados: INSERT INTO \"Institucional\".\"NOTAS\"(estudanteid, nota, disciplinaid) VALUES ('5683914', 95, '4'); \n"
						+ "Para atualizar dados: UPDATE \"Institucional\".\"NOTAS\" SET nota = 98 WHERE estudanteid = '5683914' \n"
						+ "Para deletar dados: DELETE FROM \"Institucional\".\"CURSOS\" WHERE cursoid = '1'");
			}
		});
		frmDatabaseGui.getContentPane().setLayout(null);
		
		
		
		JButton selectButton = new JButton("Selecionar");
		selectButton.setBounds(37, 5, 114, 23);
		frmDatabaseGui.getContentPane().add(selectButton);
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectWindow slcWindow = new SelectWindow();
				slcWindow.setVisible(true);
				slcWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		JButton insertButton = new JButton("Inserir");
		insertButton.setBounds(163, 5, 114, 23);
		frmDatabaseGui.getContentPane().add(insertButton);
		insertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InsertWindow istWindow = new InsertWindow();
				istWindow.setVisible(true);
				istWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		
		JButton updateButton = new JButton("Atualizar");
		updateButton.setBounds(287, 5, 114, 23);
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateWindow upWindow = new UpdateWindow();
				upWindow.setVisible(true);
				upWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		frmDatabaseGui.getContentPane().add(updateButton);
		
		JButton removeButton = new JButton("Remover");
		removeButton.setBounds(411, 5, 129, 23);
		frmDatabaseGui.getContentPane().add(removeButton);
		
		String[] items = { "CURSOS", "ESTUDANTES", "DISCIPLINAS", "NOTAS", "TURMAS"};
		
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
}
