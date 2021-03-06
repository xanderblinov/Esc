package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.inference.database.dto.IAuthor;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */
//TODO add many to many relationship for articles and companies @see{https://github.com/j256/ormlite-jdbc/blob/master/src/test/java/com/j256/ormlite/examples/manytomany/ManyToManyMain.java}

@DatabaseTable(tableName = IAuthor.TABLE_NAME)
public class Author implements IAuthor
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.name)
	private String mName;
	@DatabaseField(columnName = Column.surname)
	private String mSurname;
	@DatabaseField(columnName = Column.encoding)
	private String mEncoding;

	@DatabaseField(columnName = Column.click)
	private String mClick;

	public Author()
	{
		// ORMLite needs a no-arg constructor
	}

	public Author(final String name, final String Surname, final String click)
	{
		mName = name;
		mSurname = Surname;
        setClick(click);
	}

    public String getFullName() {
        return getName() + " " + getSurname();
    }

    public String getName()
	{
		return mName;
	}

	public void setName(final String name)
	{
		mName = name;
	}

	public String getSurname()
	{
		return mSurname;
	}

	public void setSurname(final String surname)
	{
		mSurname = surname;
	}

	public String getEncoding()
	{
		return mEncoding;
	}

	public void setEncoding(final String encoding)
	{
		mEncoding = encoding;
	}


	@Override
    public String getClick() {
        return mClick;
    }

    @Override
    public void setClick(String click) {
        mClick = click;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (mEncoding != null ? !mEncoding.equals(author.mEncoding) : author.mEncoding != null) return false;
        if (mName != null ? !mName.equals(author.mName) : author.mName != null) return false;
        if (mSurname != null ? !mSurname.equals(author.mSurname) : author.mSurname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mName != null ? mName.hashCode() : 0;
        result = 31 * result + (mSurname != null ? mSurname.hashCode() : 0);
        result = 31 * result + (mEncoding != null ? mEncoding.hashCode() : 0);
        return result;
    }

    @Override
    public long getId() {
        return mId;
    }


    @Override
    public String toString() {
        return "Author{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mSurname='" + mSurname + '\'' +
                ", mEncoding='" + mEncoding + '\'' +
                ", mClick='" + mClick + '\'' +
                '}';
    }
}
