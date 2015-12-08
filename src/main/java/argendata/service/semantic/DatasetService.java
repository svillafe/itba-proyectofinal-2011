/*package argendata.service.semantic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.solr.client.solrj.SolrServerException;

import argendata.model.dcat.Dataset;

public interface DatasetService {

	/**
	 * @param obj Dataset object to be stored in the store.
	 * @return The stored dataset.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SolrServerException
	
	public Dataset store(Dataset obj) throws MalformedURLException, IOException, SolrServerException;

	/**
	 * @param obj Dataset to be deleted.
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SolrServerException
	
	public void delete(Dataset obj) throws MalformedURLException, IOException, SolrServerException;

	/**
	 * @return An iterable for all datasets stored.
	
	public Iterable<Dataset> findAll();

	/**
	 * @param title The title for the dataset to be created.
	 * @return A new dataset with the given title.
	
	public Dataset newDataset(String title);

	/**
	 * @param string Publishers name.
	 * @return A list of datasets that have been published by the given publisher.
	
	public List<Dataset> getLastestFromPublisherName(String string);

	/**
	 * @return A list with the recently modified datasets.
	
	public List<Dataset> getRecentlyModified();

	/**
	 * @param string Keyword to be fetched.
	 * @param qty Query limit.
	 * @return A list with datasets that have the wanted keyword
	
	public List<Dataset> getFromKeyword(String keyword, int qty);

	/**
	 * @return A list with randomly selected datasets.
	
	public List<Dataset> getSome();

	/**
	 * @param dataset The title of the dataset.
	 * @return True or false if the dataset exists or not.
	
	public boolean existDataset(String dataset);

	/**
	 * @param name The title of the dataset.
	 * @return A dataset for the given name.
	
	public Dataset getDatasetByName(String name);
	
	/**
	 * @param qName The qualified name of a dataset.
	 * @return The dataset for the given dataset.
	
	public Dataset getDatasetByQName(String qName);

}
*/