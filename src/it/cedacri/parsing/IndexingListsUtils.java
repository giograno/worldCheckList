package it.cedacri.parsing;

import java.io.File;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.elasticsearch.client.Client;

import it.cedacri.bean.ListedSubject;
import it.cedacri.elasticUtils.ElasticQuery;

public class IndexingListsUtils {

	private List<File> listsToIndex;
	private SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	private ElasticQuery elasticQuery;

	/**
	 * Create a <code>IndexingListsUtils</code> with a lists of files to parse
	 * 
	 * @param lists
	 */
	public IndexingListsUtils(List<File> lists) {
		this.listsToIndex = lists;
		this.elasticQuery = new ElasticQuery();
	}

	/**
	 * Create a <code>IndexingListsUtils</code> with a single file to parse
	 * 
	 * @param list
	 */
	public IndexingListsUtils(File list) {
		listsToIndex.add(list);
		this.elasticQuery = new ElasticQuery();
	}

	public int indexLists(Client client, String index, String type) {

		int counter = 0;

		SAXParser saxParser;
		try {

			for (File file : listsToIndex) {

				saxParser = saxParserFactory.newSAXParser();
				XmlHandler handler = new XmlHandler();
				saxParser.parse(file, handler);

				List<ListedSubject> completeList = handler.getSubjects();
				counter += completeList.size();

				elasticQuery.bulkInsert(client, "check", "clienti", completeList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return counter;
	}
}
