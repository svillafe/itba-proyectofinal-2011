package argendata.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import junit.framework.Assert;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.model.foaf.Organization;
import argendata.model.relational.PreDataset;
import argendata.utils.DatasetTestFactory;



@RunWith(SpringJUnit4ClassRunner.class)
public class DatasetServiceImplTest extends AbstractServiceTests{

	
	@Autowired
	private DatasetService datasetService;
	
	
	@Test
	public void datasetStoreTest(){
		try {
			
			
			Dataset dataset =  DatasetTestFactory.getDataset();
			datasetService.store( dataset );
			Dataset theDataset = datasetService.getApprovedDatasetByName(dataset.getTitleId());
			
			Assert.assertTrue( dataset.getTitle().equals(theDataset.getTitle()));
			Assert.assertTrue( dataset.getTitleId().equals(theDataset.getTitleId()));
			Assert.assertTrue( dataset.getLicense().equals(theDataset.getLicense()));
			Assert.assertTrue( dataset.getAccessURL().equals(theDataset.getAccessURL()));
			Assert.assertTrue( dataset.getDataQuality().equals(theDataset.getDataQuality()));
			Assert.assertTrue( dataset.getLocation().equals(theDataset.getLocation()));
			Assert.assertTrue( dataset.getModified().equals(theDataset.getModified()));
			Assert.assertTrue( dataset.getDescription().equals(theDataset.getDescription()));
			Assert.assertTrue( dataset.getSpatial().equals(theDataset.getSpatial()));
			Assert.assertTrue( dataset.getTemporal().equals(theDataset.getTemporal()));
			Assert.assertTrue( dataset.getFormat().equals(theDataset.getFormat()));
			Assert.assertTrue( dataset.getPublisherName().equals(theDataset.getPublisherName()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void preDatasetStoreTest(){
			
		PreDataset dataset =  DatasetTestFactory.getPreDataset();
		System.out.println(dataset.toString());
		datasetService.store( dataset );

		PreDataset theDataset = datasetService.asyncGetPreDataset(dataset.getTitleId());
		System.out.println(theDataset);
		
		Assert.assertTrue( dataset.getTitle().equals(theDataset.getTitle()));
		Assert.assertTrue( dataset.getTitleId().equals(theDataset.getTitleId()));
		Assert.assertTrue( dataset.getLicense().equals(theDataset.getLicense()));
		Assert.assertTrue( dataset.getAccessURL().equals(theDataset.getAccessURL()));
		Assert.assertTrue( dataset.getDataQuality().equals(theDataset.getDataQuality()));
		Assert.assertTrue( dataset.getLocation().equals(theDataset.getLocation()));
		Assert.assertTrue( dataset.getModified().equals(theDataset.getModified()));
		Assert.assertTrue( dataset.getDescription().equals(theDataset.getDescription()));
		Assert.assertTrue( dataset.getSpatial().equals(theDataset.getSpatial()));
		Assert.assertTrue( dataset.getTemporal().equals(theDataset.getTemporal()));
		Assert.assertTrue( dataset.getFormat().equals(theDataset.getFormat()));
		Assert.assertTrue( dataset.getPublisher().equals(theDataset.getPublisher()));
		
	}
}
