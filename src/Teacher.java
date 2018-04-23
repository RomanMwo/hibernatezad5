import java.util.HashSet;
import java.util.Set;

public class Teacher
{
	private long id;
	private String name;
	private String surname;
	private String pesel;
	private Set<SchoolClass> fill;
	 
	public Set<SchoolClass> getFill() {
        return fill;
    }

    public void setFill(Set<SchoolClass> lectores) {
        if (fill == null) {
            fill=new HashSet<SchoolClass>();
        }
        this.fill = lectores;
    }

    public void addFill(SchoolClass schoolClass) {
        if (fill == null) {
            fill=new HashSet<SchoolClass>();
        }
        fill.add(schoolClass);
}

	public Teacher(String name, String surname, String title, String pesel)
	{
		this();
		this.name = name;
		this.surname = surname;
		this.pesel = pesel;
	}
	

    public Teacher() {
        super();
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
		return "Teacher: " + getName() + " " + getSurname() + " , pesel: "
				+ getPesel();
	}

}
