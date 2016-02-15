package it.cedacri.elasticUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.IndexNotFoundException;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.cedacri.bean.ListedSubject;
import it.cedacri.bean.LookingSubject;
import it.cedacri.elasticUtils.CedacriQueryBuilder.CustomQueryBuilder;
import it.cedacri.utils.Utils;

/**
 * Query API's to interact with the elastic search client
 * 
 * @author cr01046
 *
 */
public class ElasticQuery {

	private ObjectMapper objectMapper = new ObjectMapper();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static final String JSON_MAPPING = "cedacri_mapping.json";

	public ElasticQuery() {
		objectMapper.setDateFormat(dateFormat);
	}

	public void mapIndex(Client client, String index) {
		CreateIndexRequestBuilder builder = null;
		try {
			builder = client.admin().indices().prepareCreate(index).addMapping("clienti",
					getIndexFieldMapping());
		} catch (IOException e) {
			System.err.println("Specify a valid path for json index mapping!");
		}
		builder.execute().actionGet();
	}

	private String getIndexFieldMapping() throws IOException {
		return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(JSON_MAPPING));
	}

	public void deleteEntireIndex(Client client, String index) {
		try {
			DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
			client.admin().indices().delete(deleteIndexRequest).actionGet();
		} catch (IndexNotFoundException exception) {
			System.err.println("You are trying to delete an index which does not exits");
		}
	}

	public void bulkInsert(Client client, String index, String type, List<ListedSubject> subjects)
			throws JsonProcessingException {

		BulkRequestBuilder builder = client.prepareBulk();

		for (ListedSubject listedSubject : subjects) {
			builder.add(client.prepareIndex(index, type)
					.setSource(objectMapper.writeValueAsString(listedSubject)));
		}
		builder.get();
	}

	public void singleInsert(Client client, String index, String type, ListedSubject subject)
			throws JsonProcessingException {
		IndexResponse response = client.prepareIndex(index, type)
				.setSource(objectMapper.writeValueAsString(subject)).execute().actionGet();

		System.out.println(response);
	}

	public void getDocument(Client client, String index, String type, String id) {
		GetResponse response = client.prepareGet(index, type, id).execute().actionGet();
		System.out.println(response.isContextEmpty());
		System.out.println(response.getField("lastName"));
	}

	public void scoredQuery(Client client, String index, String type, LookingSubject subject) {
		SearchResponse response = client.prepareSearch(index)
				.setTypes(type)
				.setQuery(new CedacriQueryBuilder.CustomQueryBuilder(subject).build().getQuery())
//				.setMinScore(1.0f)
				.execute().actionGet();

		System.out.println(response);
	}

	public void test(Client client, String index, String type, String field, String value) {
		QueryBuilder headingQuery = QueryBuilders.matchQuery(field, value).minimumShouldMatch("2")
				.fuzziness(Fuzziness.ONE).boost(5.0f).operator(MatchQueryBuilder.Operator.OR);

		SearchResponse response = client.prepareSearch(index).setTypes(type)
				.setQuery(headingQuery).setMinScore(0.5f)
				.execute().actionGet();

		System.out.println(response);

	}

	public void matchQuery(Client client, String index, String type, String field, String value) {
		QueryBuilder builder = QueryBuilders.matchQuery(field, value).minimumShouldMatch("2").fuzziness(Fuzziness.ONE);

		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(builder)
				.execute().actionGet();
		
		System.out.println(response);
	}

	public void minimumMatch(Client client, String index, String type, String field, String value) {
		// QueryBuilder builder = QueryBuilders.matchQuery(field,
		// value).minimumShouldMatch("2");
		QueryBuilder builder = QueryBuilders.matchQuery(field, value)
				.operator(MatchQueryBuilder.Operator.OR).minimumShouldMatch("2")
				.fuzziness(Fuzziness.ONE).boost(5.0f);
		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(builder)
				.execute().actionGet();
		System.out.println(response);
		
	}
	
	public void dateQuery(Client client, String index, String type, String field, String value) {
//		QueryBuilder builer = QueryBuilders.fuzzyQuery(field, value).
		FuzzyQueryBuilder queryBuilder = QueryBuilders.fuzzyQuery(field,value).fuzziness(Fuzziness.build("30d"));
		SearchResponse response = client.prepareSearch(index).setTypes(type).setQuery(queryBuilder)
				.execute().actionGet();
		System.out.println(response);
	}
	
	
}
