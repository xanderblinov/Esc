package net.inference.sqlite;

import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;

import net.inference.database.IArticleApi;
import net.inference.database.dto.IArticle;
import net.inference.sqlite.dto.Article;

import java.sql.SQLException;

/**
 * Date: 12/23/2014
 * Time: 11:25 PM
 *
 * @author xanderblinov
 */
public class ArticleApi extends BaseApi<Article, Integer> implements IArticleApi
{
	private final DatabaseApi mDatabaseApi;
	private static Logger logger = LoggerFactory.getLogger(Article.class);

	public ArticleApi(final DatabaseApi databaseApi)
	{
		super(databaseApi, Article.class);
		mDatabaseApi = databaseApi;
	}

	@Override
	public Article addArticle(Article article) throws SQLException
	{
		return getDao().createIfNotExists(article);
	}

	@Override
	public boolean exists(Article article) throws SQLException
	{
		return
		getDao().queryForFirst(getDao().queryBuilder().where().eq(IArticle.Column.source, article.getArticleSource()).and().eq(IArticle.Column.id_in_source, article.getIdInSource()).prepare()) !=
		null;
	}
}
