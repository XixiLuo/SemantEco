package edu.rpi.tw.escience.semanteco.squirrel;

import static edu.rpi.tw.escience.semanteco.query.Query.VAR_NS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;


import edu.rpi.tw.escience.semanteco.Module;
import edu.rpi.tw.escience.semanteco.ModuleConfiguration;
import edu.rpi.tw.escience.semanteco.QueryMethod;
import edu.rpi.tw.escience.semanteco.Request;
import edu.rpi.tw.escience.semanteco.Resource;
import edu.rpi.tw.escience.semanteco.SemantEcoUI;
import edu.rpi.tw.escience.semanteco.query.GraphComponentCollection;
import edu.rpi.tw.escience.semanteco.query.NamedGraphComponent;
import edu.rpi.tw.escience.semanteco.query.Query;
import edu.rpi.tw.escience.semanteco.query.QueryResource;
import edu.rpi.tw.escience.semanteco.query.Variable;
import edu.rpi.tw.escience.semanteco.query.Query.Type;
import edu.rpi.tw.escience.semanteco.Domain;
import edu.rpi.tw.escience.semanteco.ProvidesDomain;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SquirrelModule implements Module {

	private ModuleConfiguration config = null;
	private static final String FAILURE = "{\"success\":false}";
	private static final Logger log = Logger.getLogger(SquirrelModule.class);
	
	//Endpoint
	private static final String LOGD_ENDPOINT = "http://hercules.tw.rpi.edu:8083/parliament/sparql";
	
	//Prefixes
	private static final String SF = "http://www.opengis.net/ont/sf#";
    private static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
    private static final String GEO = "http://www.opengis.net/ont/geosparql#";
    private static final String GEOF = "http://www.opengis.net/def/function/geosparql/"; 
	
    //Dataset
    private static final String NHD = "http://nhd.usgs.gov/";
   
    //NHD feature
    private static final String NHDF = "http://cegis.usgs.gov/rdf/nhd/Features/";
    
    //TEST METHOD
    //All features within in NYS polygon
	@QueryMethod
	public String testMethod(final Request request) 
	{
		//Build query
		final Query query = config.getQueryFactory().newQuery(Type.SELECT);	
		final NamedGraphComponent graph = query.getNamedGraph("http://nhd.usgs.gov/");
		
		//?f geo:hasGeometry ?fGeom .
		final QueryResource hasGeometry = query.getResource(GEO + "hasGeometry");
		final Variable fGeometry = query.getVariable(VAR_NS + "fGeom");
		final Variable feature = query.getVariable(VAR_NS + "f");
		graph.addPattern(feature, hasGeometry, fGeometry);
		
		//?fGeom geo:asWKT ?fWKT .
		final QueryResource asWKT = query.getResource(GEO + "asWKT");
		final Variable featureWKT = query.getVariable(VAR_NS + "fWKT");
		graph.addPattern(fGeometry, asWKT, featureWKT);
		
		//FILTER(geof:sfWithin(?fWKT, "POLYGON((-81.587906 45.336702, -81.148453 39.774769,    -69.964371 39.30029, -70.403824 45.58329, -81.587906 45.336702))"^^geo:wktLiteral))
		graph.addFilter("<" + GEOF + "sfWithin>(?fWKT, \"POLYGON((-81.587906 45.336702, -81.148453 39.774769,    -69.964371 39.30029, -70.403824 45.58329, -81.587906 45.336702))\"^^" + "<" + GEO + "wktLiteral>)");
		
		// Execute query
		String responseStr = FAILURE;
		String resultStr = config.getQueryExecutor(request).execute(LOGD_ENDPOINT, query);	
			
		// DEBUGGING
		log.debug("Results: " + resultStr);
		
		// If there was a failure to query
		if(resultStr == null) { return responseStr; }
			System.out.println(resultStr);
		 return resultStr;	

	}
	
	//FEATURES WITHIN POLYGON
	//All features within a user drawn polygon
	@QueryMethod
	public String featuresWithinPolygon(final Request request) 
	{
		//Build query
		final Query query = config.getQueryFactory().newQuery(Type.SELECT);	
		final NamedGraphComponent graph = query.getNamedGraph("http://nhd.usgs.gov/");
		
		//?f geo:hasGeometry ?fGeom .
		final QueryResource hasGeometry = query.getResource(GEO + "hasGeometry");
		final Variable fGeometry = query.getVariable(VAR_NS + "fGeom");
		final Variable feature = query.getVariable(VAR_NS + "f");
		graph.addPattern(feature, hasGeometry, fGeometry);
		
		//?fGeom geo:asWKT ?fWKT .
		final QueryResource asWKT = query.getResource(GEO + "asWKT");
		final Variable featureWKT = query.getVariable(VAR_NS + "fWKT");
		graph.addPattern(fGeometry, asWKT, featureWKT);
		
		 //Get polygon coordinates
		 String[] coords = (String[])(request.getParam("UserDrawnMapPolygon"));
		 String[][] longlat = new String[coords.length][2];
		
		 //Parse out () and ,
		 //Separate lat and long b/c polygon takes long lat, not lat long
		 for(int a = 0; a < coords.length; a++)
		 {
		  coords[a] = coords[a].replace("(", "");
		  coords[a] = coords[a].replace(")", "");
		  coords[a] = coords[a].replace(",", "");
		  longlat[a][0] = (coords[a].split("\\s+"))[1];
		  longlat[a][1] = (coords[a].split("\\s+"))[0];
		 }
		 
		 //Construct condition
		 String filterCondition = "<" + GEOF + "sfWithin>(?fWKT, \"POLYGON((" ;
		 for(int b = 0; b < coords.length; b++)
		  filterCondition += longlat[b][0] + " " + longlat[b][1] + "," ;
		 filterCondition = filterCondition.substring(0, filterCondition.length() - 1);
		 filterCondition += "))\"^^" + "<" + GEO + "wktLiteral>)" ;
		 
		 //Filter for features in polygon
		 graph.addFilter(filterCondition);

		// Execute query
		String responseStr = FAILURE;
		String resultStr = config.getQueryExecutor(request).execute(LOGD_ENDPOINT, query);	
		
		// DEBUGGING
		log.debug("Results: " + resultStr);
		
		// If there was a failure to query
		if(resultStr == null) { return responseStr; }
			System.out.println(resultStr);
		 return resultStr;	
	}
	
	@Override
	public void visit(final Model model, final Request request, final Domain domain) {
		// TODO populate data model
	}

	@Override
	public void visit(final OntModel model, final Request request, final Domain domain) {
		// TODO populate ontology model
	}

	@Override
	public void visit(final Query query, final Request request) {
		// TODO modify queries
	}
	
	@Override
	public void visit(final SemantEcoUI ui, final Request request) {
		// TODO add resources to display
	}

	@Override
	public String getName() {
		return "Squirrel";
	}

	@Override
	public int getMajorVersion() {
		return 1;
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public String getExtraVersion() {
		return null;
	}

	@Override
	public void setModuleConfiguration(final ModuleConfiguration config) {
		this.config = config;
	}
}
