import java.util.Set;

public class Student implements java.io.Serializable
{
	int class_id;
	private long id;
	private String name;
	private String surname;
	private String pesel;

	public int getClass_id()
	{
		return class_id;
	}

	public void setClass_id(int class_id)
	{
		this.class_id = class_id;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getPesel()
	{
		return pesel;
	}

	public void setPesel(String pesel)
	{
		this.pesel = pesel;
	}

	public String toString()
	{
		return "Student: " + name + " " + surname + " pesel " + pesel;
	}
}
