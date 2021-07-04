import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class DatabaseInteraction {
	public static void main(String[] args) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:postgresql://200.137.130.25/Henrique_DB",
					"HenriqueMello",
					"Cmnh$%$#$234");
			boolean programa_ativo = true;
			Map <Integer, String> tablesDictionary = new HashMap<Integer, String>();
			tablesDictionary.put(1, "CURSOS");
			tablesDictionary.put(2, "ESTUDANTES");
			tablesDictionary.put(3, "DISCIPLINAS");
			tablesDictionary.put(4, "NOTAS");
			tablesDictionary.put(5, "TURMAS");
			
			while (programa_ativo) {
				System.out.println("Qual tipo de opera��o deve ser feita? Digite o n�mero correspondente");
				System.out.println("1 - Selection, 2 - Insert, 3 - Update, 4 - Remove, 5 - Sair");
				
				Scanner keyScan = new Scanner(System.in);
				int option = keyScan.nextInt();
				
				if (option == 5) {
					programa_ativo = false;
				} else {
					Statement stm = conn.createStatement();
//					Scanner queryScan = new Scanner(System.in);
//					System.out.println("Digite a consulta em SQL:");
//					String query = queryScan.nextLine();
					String query = "bla";
					if (option == 1) {
						System.out.println("Qual tabela deseja visualizar? ");
						System.out.println("1 - CURSOS, 2 - ESTUDANTES, 3 - DISCIPLINAS, 4 - NOTAS, 5 - TURMAS");
						Scanner tableScan = new Scanner(System.in);
						int tableOption = tableScan.nextInt();
						
						String tabela = tablesDictionary.get(tableOption);
						
						String selectQuery = "SELECT * FROM 	\"Institucional\".\"" + tabela + "\";";
						ResultSet result_select = stm.executeQuery(selectQuery);
						System.out.println(selectQuery);
						// acha quais colunas foram selecionadas pela query
						ResultSetMetaData rsmd = result_select.getMetaData();
						
						// n�mero de colunas 
						int columnCount = rsmd.getColumnCount();
						
	
						while (result_select.next()) {
				            // imprime cada nome da coluna e sua respectiva entrada
							for (int i = 1; i <= columnCount; i++ ) {
								  String name = rsmd.getColumnName(i);
								  System.out.println(name + ": "+ result_select.getString(name));
							}
								
				            System.out.println("\n----------------------------\n");
				         }
						
					} else if (option == 2){
						System.out.println("Em qual tabela deseja inserir novos dados? ");
						System.out.println("1 - CURSOS, 2 - ESTUDANTES, 3 - DISCIPLINAS, 4 - NOTAS, 5 - TURMAS");
						Scanner tableScan = new Scanner(System.in);
						int tableOption = keyScan.nextInt();
						
						String tabela = tablesDictionary.get(tableOption);
						
						// obtendo a lista de colunas, em ordem, da tabela selecionada
						
						String columnQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tabela + "'";
						ResultSet columns = stm.executeQuery(columnQuery);
						List<String> columns_list = new ArrayList<String>();
						
						while (columns.next()) {
							String col = columns.getString("column_name");
							columns_list.add(col);
						}
						
						System.out.println("Digite os valores que ser�o inseridos na seguinte ordem: ");
						System.out.println(columns_list);
						Scanner valuesScan = new Scanner(System.in);
						String valuesCols = valuesScan.nextLine();
						
						String cols = columns_list.toString();
						
						query = "INSERT INTO \"Institucional\".\"" + tabela + "\"("+ cols.substring(1, cols.length() - 1) + ") VALUES (" + valuesCols + ")"; 
						System.out.println(query);
						stm.execute(query);
						System.out.println("Inser��o feita no banco de dados");
						 
					} else if (option == 3) {
						System.out.println("Em qual tabela deseja atualizar os dados? ");
						System.out.println("1 - CURSOS, 2 - ESTUDANTES, 3 - DISCIPLINAS, 4 - NOTAS, 5 - TURMAS");
						Scanner tableScan = new Scanner(System.in);
						int tableOption = keyScan.nextInt();
						
						String tabela = tablesDictionary.get(tableOption);
						
						// colunas da tabela selecionada
						String columnQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tabela + "'";
						ResultSet columns = stm.executeQuery(columnQuery);
						List<String> columns_list = new ArrayList<String>();
						
						while (columns.next()) {
							String col = columns.getString("column_name");
							columns_list.add(col);
						}
						
						System.out.println("Digite qual das seguintes colunas deve ser atualizada: ");
						System.out.println(columns_list);
						
						Scanner colScan = new Scanner(System.in);
						String colToUpdate = colScan.next();
						
						System.out.println("Digite o valor que deve ser atualizado: ");
						Scanner updateScan = new Scanner(System.in);
						String updateValue = updateScan.next();
						
						System.out.println("Digite a condi��o na forma nome_da_coluna = valor");
						System.out.println("Colunas dispon�veis: ");
						System.out.println(columns_list);
						
						Scanner conditionScan = new Scanner(System.in);
						String condition = conditionScan.nextLine();
						
						String updateQuery = "UPDATE \"Institucional\".\""+ tabela + "\" SET "+ colToUpdate + "=" + updateValue +" WHERE " + condition;
						
						stm.execute(updateQuery);
						System.out.println("Atualiza��o feita no banco de dados");
						 
					} else if (option == 4) {
						System.out.println("De qual tabela deseja remover os dados? ");
						System.out.println("1 - CURSOS, 2 - ESTUDANTES, 3 - DISCIPLINAS, 4 - NOTAS, 5 - TURMAS");
						Scanner tableScan = new Scanner(System.in);
						int tableOption = keyScan.nextInt();
						
						String tabela = tablesDictionary.get(tableOption);
						
						// colunas da tabela selecionada
						String columnQuery = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + tabela + "'";
						ResultSet columns = stm.executeQuery(columnQuery);
						List<String> columns_list = new ArrayList<String>();
						
						while (columns.next()) {
							String col = columns.getString("column_name");
							columns_list.add(col);
						}
						
						System.out.println("Digite a condi��o para a remo��o na forma nome_da_coluna = valor");
						System.out.println("Colunas dispon�veis: ");
						System.out.println(columns_list);
						
						Scanner conditionScan = new Scanner(System.in);
						String condition = conditionScan.nextLine();
						
						String removeQuery = "DELETE FROM \"Institucional\".\""+ tabela +"\" WHERE " + condition;
						
						stm.execute(removeQuery);
						System.out.println("Remo��o feita no banco de dados");
						 
					} else {
						stm.execute(query);
						System.out.println("Por favor, digita uma op��o v�lida.");
					};
				}
			}
		} catch (Exception e){
			System.out.println("Exception: " + e);
		}
	};
	
}
