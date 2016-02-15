import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.Client;

import it.cedacri.bean.LookingSubject;
import it.cedacri.elasticUtils.ElasticQuery;
import it.cedacri.elasticUtils.TransportClientUtils;
import it.cedacri.parsing.IndexingListsUtils;

public class MainElastic {

	private static String clusterName = "cedacri_elastic";
	private static String index = "check";
	private static String type = "clienti";

	public static void main(String[] args) throws IOException, ParseException {

		Client client = TransportClientUtils.getLocalTransportClient(clusterName, 9300);
		ElasticQuery elasticQuery = new ElasticQuery();

		boolean delete = false;
		boolean insert = false;

		if (delete) {
			elasticQuery.deleteEntireIndex(client, "check"); 
			System.exit(0);
		}

		if (insert) {
			List<File> filesToParse = new ArrayList<>();
			filesToParse.add(new File(
					"C:\\Users\\cr01046\\Documents\\WordCheck\\world-check-sanction(1).xml"));
			filesToParse.add(new File(
					"C:\\Users\\cr01046\\Documents\\WordCheck\\world-check-ITALY-PEP.xml"));

			IndexingListsUtils indexingListsUtils = new IndexingListsUtils(filesToParse);
			int number = indexingListsUtils.indexLists(client, index, type);
			System.out.println(number + " files correctly indexed in your cluster!");
		}

		LookingSubject subject = new LookingSubject.SubjectBuilder(
				"Franco Giorgio Bassanini").build();

		// elasticQuery.scoredQuery(client, "check", "clienti", subject);
		// elasticQuery.matchQuery(client, "check", "clienti", "heading",
		// "Franco Bassanini");
		// elasticQuery.test(client, "check", "clienti", "heading", "Franco
		// Giuseppe Bassanini");

		// elasticQuery.fuzzyQuery(client, "check", "clienti", "heading", "Mario
		// Monti");
		// System.out.println("**************");
		// elasticQuery.test(client, "check", "clienti", "heading", "Mario
		// Monti");
		// System.out.println("**************");
//		elasticQuery.matchQuery(client, "check", "clienti", "heading",
//				"Humam ABD-AL-GHAFUR");
		
		elasticQuery.scoredQuery(client, index, type, subject);

		// elasticQuery.fuzzyQuery(client, "check", "clienti", "fiscalCode",
		// "BSSFNC40E09F205P");

		// elasticQuery.singleInsert(client, "check", "clienti", subject);
		// elasticQuery.fuzzyQuery(client, "check", "clienti", "countries",
		// "NazioneFantastica");

		// elasticQuery.test(client, "check", "clienti", "countries", "Italy");
		// elasticQuery.matchQuery(client, "check", "clienti", "lastName",
		// "ABD-AL-GHAFUR");
		 elasticQuery.minimumMatch(client, "check", "clienti", "heading", "Franco Giorgio Bassanini");
		// elasticQuery.minimumMatch(client, "check", "clienti", "lastName",
		// "revolutionary organization");
	}

}
