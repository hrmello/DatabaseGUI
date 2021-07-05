package MatrixOps;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class DeleteWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteWindow frame = new DeleteWindow();
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
	public DeleteWindow() {
		try {
			System.out.println("Conectando à base de dados");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://200.137.130.25/Henrique_DB",
					"HenriqueMello",
					"Cmnh$%$#$234");
			System.out.println("Conectado");
			Statement stm = conn.createStatement();
			
			setTitle("Sele\u00E7\u00E3o de registros");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 563, 528);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Tabelas");
			lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel.setBounds(65, 24, 51, 29);
			contentPane.add(lblNewLabel);
			
				
				
			
			// colocando os nomes das tabelas na lista que será exibida no jframe
			ArrayList<String> tableListForCols = new ArrayList<String>();
			tableListForCols.add("CURSOS");
			tableListForCols.add("ESTUDANTES");
			tableListForCols.add("DISCIPLINAS");
			tableListForCols.add("NOTAS");
			tableListForCols.add("TURMAS");
			
			List tableList = new List();
			tableList.add("CURSOS");
			tableList.add("ESTUDANTES");
			tableList.add("DISCIPLINAS");
			tableList.add("NOTAS");
			tableList.add("TURMAS");
			
			Map <String, ArrayList<String>> tableCols= new HashMap<String, ArrayList<String>>();
			
			// determinando as colunas de cada tabela
			for (String table : tableListForCols) {
				String columnQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + table + "'";
				ResultSet columns = stm.executeQuery(columnQuery);
				ArrayList<String> columns_list = new ArrayList<String>();
				
				while (columns.next()) {
					String col = columns.getString("column_name");
					columns_list.add(col);
				}
				
				tableCols.put(table, columns_list);
			}
			
			
			
			tableList.setBounds(33, 59, 110, 101);
			contentPane.add(tableList);
			
			
			JLabel lblNewLabel_1 = new JLabel("Colunas");
			lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_1.setBounds(194, 27, 78, 22);
			contentPane.add(lblNewLabel_1);
			
			List colList = new List();
			colList.setBounds(167, 59, 110, 101);
			contentPane.add(colList);
			
			// Listener da primeira lista (tableList)
			tableList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = tableList.getSelectedItem();
					System.out.println(table);
					
					// Limpando a lista de colunas anteriormente em colList
					colList.removeAll();
					ArrayList<String>columnsInTable = tableCols.get(table);
					
					for (String col : columnsInTable) {
						colList.add(col);
					}
				}
			});
			
			JLabel lblNewLabel_2 = new JLabel("Selecione a tabela e, opcionalmente, a coluna e a condi\u00E7\u00E3o que deseja visualizar.");
			lblNewLabel_2.setBounds(10, 0, 481, 25);
			contentPane.add(lblNewLabel_2);
			
			JTextArea textArea = new JTextArea();
			// área de texto não pode ser editada
			textArea.setEditable(false);
			
			contentPane.add(textArea);
			JScrollPane scroll = new JScrollPane(textArea);
			scroll.setBounds(92, 235, 346, 243);                  
	        getContentPane().add(scroll);
			
			JLabel lblNewLabel_3 = new JLabel("Resultado da consulta");
			lblNewLabel_3.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_3.setBounds(194, 210, 197, 14);
			contentPane.add(lblNewLabel_3);
			
			JLabel lblNewLabel_1_1 = new JLabel("Condi\u00E7\u00E3o para remo\u00E7\u00E3o");
			lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_1_1.setBounds(319, 27, 191, 22);
			contentPane.add(lblNewLabel_1_1);
			
			JTextField conditionField = new JTextField();
			conditionField.setBounds(342, 59, 134, 20);
			contentPane.add(conditionField);
			conditionField.setColumns(10);
			
			JButton deleteButton = new JButton("Remover");
			deleteButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			deleteButton.setBounds(342, 95, 134, 23);
			contentPane.add(deleteButton);
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = tableList.getSelectedItem();
					String col = colList.getSelectedItem();
					String condition = conditionField.getText();
					String deleteQuery = "DELETE  * FROM \"Institucional\".\"" + table + "\" WHERE "+ col + "="+ condition;
					
					try {
						System.out.println("Executando a consulta");
						stm.execute(deleteQuery);	
			         
					}catch (Exception selectionException){
						System.out.print(selectionException);
					};
				}
			});
		
		} catch (Exception e){
			System.out.println(e);
		}
	}

}
