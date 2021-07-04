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

public class InsertWindow extends JFrame {

	private JPanel contentPane;
	private JTextField orderTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InsertWindow frame = new InsertWindow();
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
	public InsertWindow() {

		try {
			System.out.println("Conectando à base de dados");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://200.137.130.25/Henrique_DB",
					"HenriqueMello",
					"Cmnh$%$#$234");
			System.out.println("Conectado");
			Statement stm = conn.createStatement();
			
			setTitle("Inser\u00E7\u00E3o de registros");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 563, 243);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Tabelas");
			lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel.setBounds(133, 24, 51, 29);
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
			
			
			
			tableList.setBounds(102, 59, 110, 127);
			contentPane.add(tableList);
			
					
			JLabel lblNewLabel_2 = new JLabel("Selecione a tabela, a coluna e a condi\u00E7\u00E3o para que seja feita a inser\u00E7\u00E3o de um novo elemento.");
			lblNewLabel_2.setBounds(10, 0, 481, 25);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblNewLabel_1_1 = new JLabel("Digite os valores na seguinte ordem");
			lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_1_1.setBounds(274, 27, 296, 22);
			contentPane.add(lblNewLabel_1_1);
			
			JTextField valuesField = new JTextField();
			valuesField.setBounds(303, 132, 134, 20);
			contentPane.add(valuesField);
			valuesField.setColumns(10);
			
			JButton selectButton = new JButton("Inserir");
			selectButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			selectButton.setBounds(303, 163, 134, 23);
			contentPane.add(selectButton);
			
			orderTextField = new JTextField();
			orderTextField.setEditable(false);
			contentPane.add(orderTextField);
			JScrollPane scroll = new JScrollPane(orderTextField);
			scroll.setBounds(274, 59, 206, 65);
			getContentPane().add(scroll);
			
			// Listener da primeira lista (tableList)
			tableList.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = tableList.getSelectedItem();
					System.out.println(table);
					
					ArrayList<String>columnsInTable = tableCols.get(table);
					String label = "";
					for (int i = 0; i < columnsInTable.size(); i++) {
						if (i == columnsInTable.size() - 1) {
							label = label + columnsInTable.get(i);
						} else {
							label = label + columnsInTable.get(i) +", " ;
						}
					}
					System.out.println(label);
					orderTextField.setText(label);
					
					
				}
			});
			
			selectButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = tableList.getSelectedItem();
					String values = valuesField.getText();
					ArrayList<String>columns_list = tableCols.get(table);
					String cols = columns_list.toString();
					String insertQuery = "INSERT INTO \"Institucional\".\"" + table + "\"("+ cols.substring(1, cols.length() - 1) + ") VALUES (" + values + ")"; 
					
					
					try {
						System.out.println("Inserindo dados");
						stm.executeQuery(insertQuery);
						System.out.println("Inserção feita no banco de dados");
						
						
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
