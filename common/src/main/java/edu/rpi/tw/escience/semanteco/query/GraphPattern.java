package edu.rpi.tw.escience.semanteco.query;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;

/**
 * A GraphPattern is a GraphComponent used for representing
 * a single triple (potentially with variables) in a 
 * SPARQL query.
 * 
 * @author ewpatton
 *
 */
public interface GraphPattern extends GraphComponent {
	
	/**
	 * Sets the subject for this graph pattern
	 * @param subject
	 */
	void setSubject(QueryResource subject);
	
	/**
	 * Gets the subject for this graph pattern
	 * @return
	 */
	QueryResource getSubject();
	
	/**
	 * Sets the predicate for this graph pattern
	 * @param predicate
	 */
	void setPredicate(QueryResource predicate);
	
	/**
	 * Gets the predicate for this graph pattern
	 * @return
	 */
	QueryResource getPredicate();
	
	/**
	 * Sets the object for this graph pattern
	 * @param object
	 */
	void setObject(QueryResource object);
	
	/**
	 * Gets the oject for this graph pattern
	 * @return
	 */
	QueryResource getObject();
	
	/**
	 * Sets the value for this pattern
	 * @param value
	 * @param type
	 */
	void setObject(String value, XSDDatatype type);
	
	/**
	 * Gets the value for this pattern
	 * @return
	 */
	String getValue();
	
	/**
	 * Gets the value type for this pattern
	 * @return
	 */
	XSDDatatype getValueType();

	/**
	 * Returns whether the graph pattern will
	 * use option(transitive) to enable Virtuoso's built-in
	 * transitivity inference.
	 * @return true if option(transitive) will be enabled.
	 */
	boolean isTransitive();

	/**
	 * Sets the graph pattern to be transitive. This should only
	 * be enabled for Virtuoso endpoints that support option(transitive).
	 * For endpoints supporting SPARQL 1.1, use {@link Query#createPropertyPath(String)}
	 * to create a SPARQL 1.1 property path with the + operator.
	 * @param trans true if transitivity should be enabled, otherwise false.
	 */
	void setTransitive(boolean trans);
}
