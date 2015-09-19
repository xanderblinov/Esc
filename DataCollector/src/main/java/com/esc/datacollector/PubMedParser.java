package com.esc.datacollector;

import com.esc.datacollector.data.PubmedCard;
import com.esc.datacollector.medline.MedlineSource;
import com.esc.datacollector.medline.Medliner;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class PubMedParser extends AbsParserEngine
{
	private static final String PUBMED_START_LINE_REGEX = "(....)- (.*)";

	private volatile boolean mFileReadingCompleated = false;

	private LinkedBlockingQueue<PubmedCard> mPubmedCards = new LinkedBlockingQueue<>();

	private IPubmedCardProcessor mPubmedCardProcessor = new PubmedCardProcessor();

	@Override
	protected ExecutorService newExecutorService()
	{
		return Executors.newFixedThreadPool(2);
	}

	public PubMedParser(String filename)
	{
		super(filename);
	}

	@Override
	protected void execute(BufferedReader reader)
	{

		getExecutorService().execute(new AddPubmedCardRunnable());

		String currentString;
		StringBuilder builder = new StringBuilder();
		MedlineSource source = null;
		String currentField = null;
		try
		{
			while ((currentString = reader.readLine()) != null)
			{
				if (currentString.isEmpty())
				{
					if (source != null)
					{
						mPubmedCards.offer(Medliner.readMedline(source, PubmedCard.class));
					}
					source = new MedlineSource();
					currentField = null;
				}
				else
				{
					if (currentString.matches(PUBMED_START_LINE_REGEX))
					{
						if (source != null && currentField != null)
						{
							source.addField(currentField, builder.toString());
						}
						builder = new StringBuilder(currentString.replaceAll(PUBMED_START_LINE_REGEX, "$2"));
						currentField = currentString.replaceAll(PUBMED_START_LINE_REGEX, "$1").trim();
					}
					else
					{
						builder.append(" ").append(currentString.trim());
					}
				}
			}
			mFileReadingCompleated = true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


	}

	private class AddPubmedCardRunnable implements Runnable
	{

		@Override
		public void run()
		{
			while (true)
			{
				if (mFileReadingCompleated && mPubmedCards.size() == 0)
				{
					getExecutorService().shutdown();
					return;
				}

				final PubmedCard pubmedCard;
				try
				{
					pubmedCard = mPubmedCards.take();
				}
				catch (InterruptedException ignore)
				{
					continue;
				}
				mPubmedCardProcessor.execute(pubmedCard);

			}
		}
	}
/*
	@SuppressWarnings("unused")
	private void addArticleToList(PubmedArticle pubMedArticle, List<PubmedCard> list)
	{
		if (pubMedArticle != null && needToAddArticle(pubMedArticle))
		{
			if (false)
			{
				list.add(pubMedArticle);
			}
			else if (mStorageApi != null && mConnection != null)
			{
				try
				{
					mStorageApi.addPubMedArticleToTempTable(mConnection, pubMedArticle);
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

	}*/

	@Override
	protected void onPostExecute()
	{
		super.onPostExecute();
	}

	/**
	 * override this method to customize adding articles to list
	 *
	 * @param pubMedArticle
	 * @return true
	 */
	protected boolean needToAddArticle(PubmedCard pubMedArticle)
	{
		return true;
	}
}
