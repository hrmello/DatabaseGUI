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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UpdateWindow extends JFrame {

	private JPanel contentPane;
	private JTextField condValueTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateWindow frame = new UpdateWindow();
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
	public UpdateWindow() {
		try {
			System.out.println("Conectando à base de dados");
			Connection conn = DriverManager.getConnection("jdbc:postgresql://200.137.130.25/Henrique_DB",
					"HenriqueMello",
					"Cmnh$%$#$234");
			System.out.println("Conectado");
			Statement stm = conn.createStatement();
			
			setTitle("Atualiza\u00E7\u00E3o de registros");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 563, 272);
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
			
			List condColList = new List();
			condColList.setBounds(294, 59, 110, 101);
			contentPane.add(condColList);
			
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
						condColList.add(col);
					}
				}
			});
			
			JLabel lblNewLabel_2 = new JLabel("Selecione a tabela, a coluna e a condi\u00E7\u00E3o do registro que deseja atualizar.");
			lblNewLabel_2.setBounds(10, 0, 481, 25);
			contentPane.add(lblNewLabel_2);
			
			JLabel lblNewLabel_1_1 = new JLabel("Valor da atualiza\u00E7\u00E3o");
			lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_1_1.setBounds(33, 182, 191, 22);
			contentPane.add(lblNewLabel_1_1);
			
			JTextField updateValueTextField = new JTextField();
			updateValueTextField.setBounds(22, 205, 134, 20);
			contentPane.add(updateValueTextField);
			updateValueTextField.setColumns(10);
			
			JButton updateButton = new JButton("Atualizar");
			updateButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			updateButton.setBounds(191, 203, 134, 23);
			contentPane.add(updateButton);
			
			JLabel lblNewLabel_1_2 = new JLabel("Coluna da condi\u00E7\u00E3o");
			lblNewLabel_1_2.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_1_2.setBounds(294, 27, 119, 22);
			contentPane.add(lblNewLabel_1_2);
					
			
			condValueTextField = new JTextField();
			condValueTextField.setColumns(10);
			condValueTextField.setBounds(428, 115, 104, 20);
			contentPane.add(condValueTextField);
			
			JLabel lblNewLabel_1_2_1 = new JLabel("Valor da condi\u00E7\u00E3o");
			lblNewLabel_1_2_1.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			lblNewLabel_1_2_1.setBounds(428, 84, 119, 22);
			contentPane.add(lblNewLabel_1_2_1);
			
			updateButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String table = tableList.getSelectedItem();
					String col = colList.getSelectedItem();
					String updateValue = updateValueTextField.getText();
					String conditionValue = condValueTextField.getText();
					String updateQuery = "UPDATE \"Institucional\".\""+ table + "\" SET "+ col + "=" + updateValue +
							" WHERE " + condColList.getSelectedItem() + "=" + conditionValue;
					
					try {
						System.out.println("Executando a atualização");
						stm.execute(updateQuery);
						JOptionPane.showMessageDialog(updateButton, "Atualização feita");
						System.out.println("Atualização feita no banco de dados");
					
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
