import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main
{

	Session session;

	public static void main(String[] args)
	{
		Main main = new Main();
		// main.printSchools();
		// main.addNewData();
		// main.executeQueries1();
		// main.executeQueries2();
		// main.executeQueries3();
		// main.executeQueries4();
		// main.executeQueries5();
		 //main.executeQueries6();
		//main.searchThenUpgradeSchools();
		main.addTeachers();
		main.close();
	}

	public Main()
	{
		session = HibernateUtil.getSessionFactory().openSession();
	}

	public void close()
	{
		session.close();
		HibernateUtil.shutdown();
	}

	private void printSchools()
	{
		Criteria crit = session.createCriteria(School.class);
		List<School> schools = crit.list();

		System.out.println("### Schools and classes");
		for (School s : schools)
		{
			System.out.println(s);
			System.out.println("           KLASY:");
			for (SchoolClass schoolClass : s.getClasses())
			{
				System.out.println("      " + schoolClass);
				System.out.println("           STUDENTS:");
				for (Student student : schoolClass.getStudents())
				{
					System.out.println("              " + student);
				}
			}
		}
	}

	private void addNewData()
	{

		School newSchool = new School();
		newSchool.setName("AE");
		newSchool.setAddress("Retoryka");
		// newSchool.setId(1);
		Set<SchoolClass> klasy = new HashSet<SchoolClass>();
		newSchool.setClasses(klasy);

		Transaction transaction = session.beginTransaction();
		session.save(newSchool); // gdzie newSchool to instancja nowej szkoly
		transaction.commit();
		// session.saveOrUpdate(newSchool); // mozna zasewoac i updatetowac
		// metota ale w pliku xml trzeba usunac <generator
		// class="native"></generator>
	}

	private void executeQueries1()
	{
		String hql = "FROM School school WHERE school.name='AE'";
		Query query = session.createQuery(hql);
		List results = query.list();
		System.out.println(results);
	}

	private void executeQueries2()
	{
		String hql = "FROM School as school WHERE school.name='AE'";
		Query query = session.createQuery(hql);
		List<School> results = query.list();
		System.out.println(results);
		Transaction transaction = session.beginTransaction();
		for (School s : results)
		{
			session.delete(s);
		}
		transaction.commit();

	}

	private void executeQueries3()
	{
		String hql = "SELECT count(school.name) from School as school";
		Query query = session.createQuery(hql);
		List<Integer> results = query.list();

		for (int s : results)
		{
			System.out.println("Ilosc szkol w bazie to " + s);
		}

	}

	private void executeQueries4()
	{
		String hql = "SELECT count(students.surname) from Student as students";
		Query query = session.createQuery(hql);
		List<Integer> results = query.list();

		for (int s : results)
		{
			System.out.println("Ilosc studentow w bazie to " + s);
		}
	}

	private void executeQueries5()
	{
		// Select schools.name from schools as s INNER JOIN schoolClasses as sc
		// on s.id = sc.school_id GROUP by s.name HAVING count(sc.school_id) >1
		String hql = "Select s.name from School as s INNER JOIN SchoolClass as sc on s.id = sc.school_id GROUP by s.name HAVING count(sc.school_id) >1 ";
		Query query = session.createQuery(hql);
		List results = query.list();

		System.out.println(results);
	}

	private void executeQueries6()
	{

//		String hql = "SELECT s from School as s INNER JOIN s.classes classes WHERE classes.profile='mat-fiz' AND classes.currentYear > 1";

		String hql = "SELECT s FROM School s INNER JOIN s.classes classes WHERE classes.profile = 'mat-fiz' AND classes.currentYear>=2";
		Query query = session.createQuery(hql);
		List results = query.list();
		System.out.println(results);

	}s

	private void searchThenUpgradeSchools()
	{
		Query query = session.createQuery("from School where id= :id");
		query.setLong("id", 2);
		School school = (School) query.uniqueResult();
		// List results = query.list();
		// System.out.println(results);
		Transaction transaction = session.beginTransaction();

		school.setAddress("Kubusia Puchatka 1410");
		session.saveOrUpdate(school);
		transaction.commit();
	}

	private void addTeachers()
	{
		Teacher teacherOne = new Teacher("bole", "pok", "dr", "78976");
		Teacher teacherTwo = new Teacher("lk", "ak", "mgr", "9898");
		String hql = "FROM SchoolClass as classes";
		Query query = session.createQuery(hql);
		List<SchoolClass> results = query.list();
		for (SchoolClass s : results)
		{
			System.out.println("Add: " + s + " to: " + teacherOne);
			s.addTeacher(teacherOne);
			System.out.println("Add: " + s + " to: " + teacherTwo);
			s.addTeacher(teacherTwo);
		}
		Transaction transaction = session.beginTransaction();
		session.save(teacherOne);
		session.save(teacherTwo);
		transaction.commit();

	}

	private void jdbcTest()
	{
		Connection conn = null;
		Statement stmt = null;
		try
		{
			// STEP 2: Register JDBC driver
			Class.forName("org.sqlite.JDBC");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection("jdbc:sqlite:school.db", "", "");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM schools";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next())
			{
				// Retrieve by column name
				String name = rs.getString("name");
				String address = rs.getString("address");

				// Display values
				System.out.println("Name: " + name);
				System.out.println(" address: " + address);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se)
		{
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e)
		{
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally
		{
			// finally block used to close resources
			try
			{
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2)
			{
			} // nothing we can do
			try
			{
				if (conn != null)
					conn.close();
			} catch (SQLException se)
			{
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}// end jdbcTest

}
