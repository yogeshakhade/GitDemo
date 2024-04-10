package com.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Home {
	
	private static Connection conn;
	
	public static void main(String[] args) {
		conn= JdbcDemo.getConnection();
		//addEmployee(conn);
		//addEmployee1(conn);
		
		/*addEmployee(13,"Manoj",4000);
		addEmployee(14,"Manu",7000);
		addEmployee(15,"Mannohar",2000);
		addEmployee(16,"Monu",1000);
		*/
		
		//addEmployee();
		
		//addEmployee(17,"Yogesh",8000);
		
		//addEmployeeNew(10,"Viabhav",9000);
		//addEmployeeNew();
		//deleteById(1);
		
		//displayEmp();
		// List<Employee> list = getEmployeeByName("A");
		// displayList(list);
		String deleteSql = "delete emp where name like 'Z%' and rownum<=5";
		String selectSql = "select * from departments";
		String createSql = "create table e1234(id number ,name varchar2(30))";

		/*
		 * testExecuteMethod(deleteSql); testExecuteMethod(selectSql);
		 * testExecuteMethod(createSql);
		 */
		testExecuteMethod(createSql);

		/*
		 * testExecuteMethod("select * from emp");
		 * System.out.println("------------------------");
		 * testExecuteMethod("select * from employees");
		 * System.out.println("---------------------------");
		 * testExecuteMethod("select * from departments");
		 */

	

	/*
	 * Select -> executeQuery => return type is Resultset insert/update/delete ->
	 * executeUpdate => return type is int -> execute
	 */
		
				
		
	}
	
	public static void testExecuteMethod(String sql) {
		try (Statement st = conn.createStatement();) {
			boolean flag = st.execute(sql);
			System.out.println(sql + " --> " + flag);

			if (flag) {
				try (ResultSet rs = st.getResultSet();) {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					System.out.println("no of columns: " + columnCount);

					for (int i = 1; i <= columnCount; i++) {
						System.out.println(rsmd.getColumnName(i) + "    " + rsmd.getColumnTypeName(i) + "  "
								+ rsmd.getColumnClassName(i));
					}
					System.out.println("=============Data================");
					while (rs.next()) {
						for (int i = 1; i <= columnCount; i++) {
							System.out.print(rs.getObject(i) + "\t\t\t");
						}
						System.out.println();
					}
				}

			} else {
				int x = st.getUpdateCount();
				System.out.println(x + " row affected");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Employee> getEmployeeByName(String startChar) {
		String sql = "select id,name,salary from emp where substr(name,1,1) = ? order by id";
		String sql1 = "select id,name,salary from emp where name  like ? order by id";
		List<Employee> empList = new ArrayList<Employee>();
		try (PreparedStatement ps = conn.prepareStatement(sql1);) {
			ps.setString(1, '%' + startChar + '%');
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					Employee emp = new Employee();
					emp.setId(rs.getInt(1));
					emp.setName(rs.getString(2));
					emp.setSalary(rs.getDouble(3));
					empList.add(emp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empList;
	}

	
	public static List<Employee> getEmployees() {
		String sql = "select id,name,salary from emp order by id";
		List<Employee> empList = new ArrayList<Employee>();
		try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql);) {
			while (rs.next()) {
				Employee emp = new Employee();

				emp.setId(rs.getInt(1));
				emp.setName(rs.getString(2));
				emp.setSalary(rs.getDouble(3));
				empList.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empList;
	}

	
	public static void displayEmp() {
		String sql="Select * from emp order by id";
		try(Statement ps = conn.createStatement(); ResultSet rs=ps.executeQuery(sql);){
			while(rs.next()) {
				int id=rs.getInt(1);
				String name=rs.getString(2);
				double salary=rs.getDouble(3);
				System.out.println("Id: " + id + "  name: " + name + "  salary: " + salary);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void deleteFirstFiveRow() {
		
	}
	
	public static void deleteById(int id) {
		String sql="delete emp where id="+id+"";
		try(Statement ps=conn.createStatement()){
			int x=ps.executeUpdate(sql);
			System.out.println("Data Deleted.");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void addEmployeeNew(int id,String name,double salary) {
		String sql="insert into emp(id,name,salary) values (?,?,?)";
		//recommended when object created at runtime
		try(PreparedStatement ps=conn.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setDouble(3, salary);
			
			int x=ps.executeUpdate();
			System.out.println(x+" row inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	//insert 10 rows, id from 31 to 40 and name= name-id salary=id*100
	public static void addEmployeeNew() {
		String sql="insert into emp(id,name,salary) values (?,?,?)";
		try(PreparedStatement ps=conn.prepareStatement(sql)){
			for(int i=31;i<=40;i++) {
				ps.setInt(1, i);
				ps.setString(2, "name-"+i);
				ps.setDouble(3, i*100);
				int x=ps.executeUpdate();
				System.out.println(x+" row inserted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//insert 10 rows, id from 21 to 30 and name= name-id salary=id*100
	public static void addEmployee() {
		for(int i=21;i<30;i++) {
			String sql="insert into emp(id,name,salary) values ("+i+",'name-"+i+"',"+(i*100)+")";
			System.out.println(sql);
			try(Statement st=conn.createStatement()){
				int x=st.executeUpdate(sql);
				System.out.println(x+" row inserted");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void addEmployee(int id,String name,double salary) {
		String sql="insert into emp(id,name,salary) values ("+id+",'"+name+"',"+salary+")";
		System.out.println(sql);
		try(Statement st=conn.createStatement()){
			int x=st.executeUpdate(sql);
			System.out.println(x+" row inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void addEmployee(Connection conn) {
		String sql="insert into emp(id,name,salary) values (11,'Priyanka',5000)";
		try(Statement st=conn.createStatement()){
			int x=st.executeUpdate(sql);
			System.out.println(x+" row inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	
	public static void addEmployee1(Connection conn) {
		String sql="insert into emp(id,name,salary) values (12,'Priyank',5000)";
		Statement st=null;
		try{
			st=conn.createStatement();
			int x=st.executeUpdate(sql);
			System.out.println(x+" row inserted");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}

