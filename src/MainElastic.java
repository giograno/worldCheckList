import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.IndexNotFoundException;
import org.xml.sax.SAXException;

import it.cedacri.bean.ListedSubject;
import it.cedacri.bean.LookingSubject;
import it.cedacri.elasticUtils.ElasticQuery;
import it.cedacri.elasticUtils.TransportClientUtils;
import it.cedacri.parsing.IndexingListsUtils;
import it.cedacri.parsing.XmlHandler;

public class MainElastic {

	private static String clusterName = "cedacri_elastic";
	private static String index = "check";
	private static String type = "clienti";
	
	public static void main(String[] args) throws IOException, ParseException {
		
		Client client = TransportClientUtils.getLocalTransportClient(clusterName, 9300);
		
		ElasticQuery elasticQuery = new ElasticQuery();
		
//		elasticQuery.deleteEntireIndex(client, "check"); // delete the entire index
//		System.exit(0);
		
//		List<File> filesToParse = new ArrayList<>();
//		filesToParse.add(new File("C:\\Users\\cr01046\\Documents\\WordCheck\\world-check-sanction(1).xml"));
//		filesToParse.add(new File("C:\\Users\\cr01046\\Documents\\WordCheck\\world-check-ITALY-PEP.xml"));
//
//		IndexingListsUtils indexingListsUtils = new IndexingListsUtils(filesToParse);
//		int number = indexingListsUtils.indexLists(client, index, type);
//		System.out.println(number + " files correctly indexed in your cluster!");
					
		LookingSubject subject = new LookingSubject.SubjectBuilder("Franco Giuseppe Giovanni Giorno Bassanini")
				.build();
		
		
		
//		elasticQuery.scoredQuery(client, "check", "clienti", subject);
//		elasticQuery.matchQuery(client, "check", "clienti", "heading", "Franco Bassanini");
//		elasticQuery.test(client, "check", "clienti", "heading", "Franco Giuseppe Bassanini");

//		elasticQuery.fuzzyQuery(client, "check", "clienti", "heading", "Mario Monti");
//		System.out.println("**************");
//		elasticQuery.test(client, "check", "clienti", "heading", "Mario Monti");
//		System.out.println("**************");
		elasticQuery.matchQuery(client, "check", "clienti", "heading", "Humam abd-al-Khaliq ABD-AL-GHAFUR");

//		elasticQuery.fuzzyQuery(client, "check", "clienti", "fiscalCode", "BSSFNC40E09F205P");
		
		//elasticQuery.singleInsert(client, "check", "clienti", subject);
//		elasticQuery.fuzzyQuery(client, "check", "clienti", "countries", "NazioneFantastica");

//		elasticQuery.test(client, "check", "clienti", "countries", "Italy");
//		elasticQuery.matchQuery(client, "check", "clienti", "lastName", "ABD-AL-GHAFUR");
//		elasticQuery.minimumMatch(client, "check", "clienti", "heading", "Franco Bassanini");
//		elasticQuery.minimumMatch(client, "check", "clienti", "lastName", "revolutionary organization");
	}

}
