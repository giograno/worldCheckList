package it.cedacri.elasticUtils;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import it.cedacri.bean.LookingSubject;

public class CedacriQueryBuilder {

	private static final float HEADING_BOOST = 15.0f;
	private static final float ALIAS_BOOST = 0.8f;
	private static final float SPELLING_BOOST = 0.8f;
	private static final float FISCAL_CODE = 10.0f;
	private static final float BIRTH_BOOST = 5.0f;
	private static final float SEX_BOOST = 1.0f;
	private static final float COUNTRY_BOOST = 1.0f;

	private final QueryBuilder headingQuery;
	private final QueryBuilder aliasBuilder;
	private final QueryBuilder spellingBuilder;
	private final QueryBuilder fiscalBuilder;
	private final QueryBuilder countryBuilder;
	private final QueryBuilder sexBuilder;
	private final QueryBuilder birthBuilder;

	public CedacriQueryBuilder(CustomQueryBuilder customQueryBuilder) {
		this.headingQuery = customQueryBuilder.headingQuery;
		this.aliasBuilder = customQueryBuilder.aliasBuilder;
		this.spellingBuilder = customQueryBuilder.spellingBuilder;
		this.fiscalBuilder = customQueryBuilder.fiscalBuilder;
		this.countryBuilder = customQueryBuilder.countryBuilder;
		this.sexBuilder = customQueryBuilder.sexBuilder;
		this.birthBuilder = customQueryBuilder.birthBuilder;
	}

	/**
	 * Return a boolean query which is composed by should clauses only for
	 * validated fields
	 * 
	 * @return a <code>QueryBuilder</code> object
	 */
	public QueryBuilder getQuery() {
		BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery().should(this.headingQuery);
		if (aliasBuilder != null)
			booleanQueryBuilder.should(this.aliasBuilder);
		if (spellingBuilder != null)
			booleanQueryBuilder.should(this.spellingBuilder);
		if (fiscalBuilder != null)
			booleanQueryBuilder.should(this.fiscalBuilder);
		if (countryBuilder != null)
			booleanQueryBuilder.should(this.countryBuilder);
		if (sexBuilder != null)
			booleanQueryBuilder.should(this.sexBuilder);
		if (birthBuilder != null)
			booleanQueryBuilder.should(this.birthBuilder);

		return booleanQueryBuilder;
	}

	/**
	 * Builder instance for query builder
	 * 
	 * @author cr01046
	 *
	 */
	public static class CustomQueryBuilder {

		private final LookingSubject subject;
		private QueryBuilder headingQuery;
		private QueryBuilder aliasBuilder = null;
		private QueryBuilder spellingBuilder = null;
		private QueryBuilder fiscalBuilder = null;
		private QueryBuilder countryBuilder = null;
		private QueryBuilder sexBuilder = null;
		private QueryBuilder birthBuilder = null;

		/**
		 * Constructor with a mandatory heading query
		 * 
		 * @param subject
		 */
		public CustomQueryBuilder(LookingSubject subject) {
			this.subject = subject;
			this.headingQuery = QueryBuilders.matchQuery("heading", subject.getHeading())
					.minimumShouldMatch("2").fuzziness(Fuzziness.ONE).boost(HEADING_BOOST);
		}

		public CustomQueryBuilder alias() {
			this.aliasBuilder = QueryBuilders.matchQuery("alias", subject.getHeading())
					.minimumShouldMatch("2").fuzziness(Fuzziness.ONE).boost(ALIAS_BOOST);
			return this;
		}

		public CustomQueryBuilder spelling() {
			this.spellingBuilder = QueryBuilders
					.matchQuery("alternativeSpelling", subject.getHeading()).minimumShouldMatch("2")
					.fuzziness(Fuzziness.ONE).boost(SPELLING_BOOST);
			return this;
		}

		public CustomQueryBuilder fiscal() {
			this.fiscalBuilder = QueryBuilders.matchQuery("alias", subject.getHeading())
					.minimumShouldMatch("2").fuzziness(Fuzziness.ONE).boost(FISCAL_CODE);
			return this;
		}

		public CustomQueryBuilder country() {
			this.countryBuilder = QueryBuilders.matchQuery("alias", subject.getHeading())
					.minimumShouldMatch("2").fuzziness(Fuzziness.ONE).boost(COUNTRY_BOOST);
			return this;
		}

		public CustomQueryBuilder sex() {
			this.sexBuilder = QueryBuilders.matchQuery("alias", subject.getHeading())
					.minimumShouldMatch("2").fuzziness(Fuzziness.ONE).boost(SEX_BOOST);
			return this;
		}

		public CustomQueryBuilder birth() {
			this.birthBuilder = QueryBuilders.matchQuery("alias", subject.getHeading())
					.minimumShouldMatch("2").fuzziness(Fuzziness.ONE).boost(BIRTH_BOOST);
			return this;
		}

		public CedacriQueryBuilder build() {
			return new CedacriQueryBuilder(this);
		}
	}

}
