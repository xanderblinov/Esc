package net.inference.sqlite.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import net.inference.database.dto.PrimitiveAuthor;
import net.inference.database.dto.PrimitiveCoAuthorship;

/**
 * Date: 12/19/2014
 * Time: 3:15 PM
 *
 * @author xanderblinov
 */

@DatabaseTable(tableName = PrimitiveCoAuthorship.TABLE_NAME)
public class PrimitiveCoAuthorshipImpl implements PrimitiveCoAuthorship
{
	@DatabaseField(columnName = Column.id, generatedId = true)
	private int mId;
	@DatabaseField(columnName = Column.author, foreign = true)
	private PrimitiveAuthorImpl mAuthor;
	@DatabaseField(columnName = Column.coauthor, foreign = true)
	private PrimitiveAuthorImpl mCoauthor;
	@DatabaseField(columnName = Column.year)
	private int mYear;
	@DatabaseField(columnName = Column.article_id)
	private long mArticleId;

	@SuppressWarnings("unused")
	public PrimitiveCoAuthorshipImpl()
	{
		// ORMLite needs a no-arg constructor
	}

	public PrimitiveCoAuthorshipImpl(final PrimitiveAuthorImpl author, final PrimitiveAuthorImpl coauthor)
	{
		mAuthor = author;
		mCoauthor = coauthor;
	}

	public PrimitiveAuthorImpl getAuthor()
	{
		return mAuthor;
	}

	public void setAuthor(final PrimitiveAuthorImpl author)
	{
		mAuthor = author;
	}

	public PrimitiveAuthorImpl getCoauthor()
	{
		return mCoauthor;
	}

	public void setCoauthor(final PrimitiveAuthorImpl coauthor)
	{
		mCoauthor = coauthor;
	}

	public int getYear()
	{
		return mYear;
	}

	public void setYear(final int year)
	{
		mYear = year;
	}

	public long getArticleId()
	{
		return mArticleId;
	}

	public void setArticleId(final long articleId)
	{
		mArticleId = articleId;
	}

	@Override
	public long getId()
	{
		return mId;
	}

	@Override
	public String toString()
	{
		return "PrimitiveCoAuthorshipImpl{" +
		"mId=" + mId +
		", mAuthor='" + mAuthor + '\'' +
		", mCoauthor='" + mCoauthor + '\'' +
		", mYear=" + mYear +
		", mArticleId=" + mArticleId +
		'}';
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof PrimitiveCoAuthorshipImpl))
		{
			return false;
		}

		PrimitiveCoAuthorshipImpl that = (PrimitiveCoAuthorshipImpl) o;

		if (getId() != that.getId())
		{
			return false;
		}
		if (getYear() != that.getYear())
		{
			return false;
		}
		if (getArticleId() != that.getArticleId())
		{
			return false;
		}
		//noinspection SimplifiableIfStatement
		if (!getAuthor().equals(that.getAuthor()))
		{
			return false;
		}
		return getCoauthor().equals(that.getCoauthor());

	}

	@Override
	public int hashCode()
	{
		int result = (int) getId();
		result = 31 * result + getAuthor().hashCode();
		result = 31 * result + getCoauthor().hashCode();
		result = 31 * result + getYear();
		result = 31 * result + (int) (getArticleId() ^ (getArticleId() >>> 32));
		return result;
	}
}
