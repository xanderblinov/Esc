package net.inference.sqlite;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import net.inference.database.ITermApi;

import net.inference.sqlite.dto.PrimitiveTerm;
import net.inference.sqlite.dto.Term;

/**
 * Created by Мария on 03.11.15.
 */
public class TermApi extends BaseApi<Term, Integer> implements ITermApi
{
	private static Logger logger = LoggerFactory.getLogger(Term.class);
	private final DatabaseApi mDatabaseApi;

	public TermApi(DatabaseApi databaseApi)
	{
		super(databaseApi, Term.class);
		this.mDatabaseApi = databaseApi;
	}

	@Override
	public List<Term> addTerms (List<Term> terms) throws Exception
	{
		final Dao<Term, Integer> dao = getDao();

		//noinspection UnnecessaryLocalVariable
		final ArrayList<Term> arrTerms = dao.callBatchTasks(() -> {

			ArrayList<Term> createdTerms = new ArrayList<>();


			for (Term term : terms)
			{
				final Term termForCreation = dao.createIfNotExists(term);
				createdTerms.add(term);
			}
			return createdTerms;
		});
		return terms;
	}
}
