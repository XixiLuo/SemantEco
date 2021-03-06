package edu.rpi.tw.escience.semanteco;

import com.hp.hpl.jena.rdf.model.Model;

import edu.rpi.tw.escience.semanteco.query.Query;

/**
 * The QueryExecutor is responsible for executing SPARQL queries
 * on behalf of modules.
 * 
 * @author ewpatton
 *
 */
public interface QueryExecutor {
	
	/**
	 * Executes a Query using the default endpoint for this executor
	 * @param query
	 * @return The results of the query as a string
	 */
	String execute(Query query);
	
	/**
	 * Executes a Query and stores the results in the specified Model.
	 * This method only works with SPARQL queries that return RDF graphs.
	 * 
	 * @param query
	 * @param model
	 * @return
	 */
	QueryExecutor execute(Query query, Model model);
	
	/**
	 * Executes a Query against the specified endpoint
	 * @param endpoint
	 * @param query
	 * @return
	 */
	String execute(String endpoint, Query query);
	
	/**
	 * Executes a Query against the specified endpoint and stores the
	 * results in the specified Model. This method only works with 
	 * SPARQL queries that return RDF graphs.
	 * @param endpoint
	 * @param query
	 * @param model
	 * @return
	 */
	QueryExecutor execute(String endpoint, Query query, Model model);

	/**
	 * Gets the default SPARQL endpoint URI used by this SemantEco instance.
	 * @return
	 */
	String getDefaultSparqlEndpoint();

	/**
	 * Allows the caller of the executor to specify a mime type to send
	 * in the HTTP Accept header when the SPARQL query is executed. This
	 * returns a QueryExecutor so that calls can be chained.
	 * 
	 * <pre>
	 * QueryExecutor qe = ...;
	 * qe.accept("application/json").accept("application/rdf+xml").execute(...);
	 * </pre>
	 * @param mimeType
	 * @return
	 */
	QueryExecutor accept(String mimeType);

	/**
	 * Executes a SPARQL query on the combined data and ontology
	 * models for a given request.
	 * @param query SPARQL query object to execute
	 */
	String executeLocalQuery(Query query);

	/**
	 * Executes a SPARQL query on the specified model.
	 * @param query SPARQL query object to execute
	 * @param model A Jena model containing the RDF graph to evaluate the query
	 * against
	 * @return
	 */
	String executeLocalQuery(Query query, Model model);

}
