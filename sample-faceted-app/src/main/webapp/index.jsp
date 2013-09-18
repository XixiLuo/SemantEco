<%@ taglib uri="/WEB-INF/semanteco-core.tld" prefix="core" %>
<%@ taglib uri="/WEB-INF/semanteco-module.tld" prefix="module" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>FacetedApp</title>
    <core:styles />
    <module:styles />

 <link rel="stylesheet" type="text/css" href="js/jstree/themes/default/style.css" />
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
        <link rel="stylesheet" type="text/css" href="css/dropdownchecklist/ui.dropdownchecklist.standalone.css" />
        <link rel="stylesheet" type="text/css" href="css/dropdownchecklist/ui.dropdownchecklist.themeroller.css" />
        <link rel="stylesheet" type="text/css" href="js/jqplot/jquery.jqplot.min.css" />
        <link rel="stylesheet" type="text/css" href="css/annotator/annotator-css-core.css" />
        <link rel="stylesheet" type="text/css" href="//qtip2.com/v/stable/jquery.qtip.min.css" />

  </head>
  <body onload="SemantEco.initialize()">
    <div id="header">
      <div class="header-text">
        <img src="images/header.png" alt="FacetedApp" />
      </div>
    </div>
    <div id="content">
    
    
    <div id="sidebar" class="sidebar">
                <div id="facets">
                    <!-- classes view -->
                    <div id="classes-module" class="module-facet-container">
                        <h3>Classes</h3>
                        <div id="classesFacet" class="facet">
                            <div id="ClassTree" class="hierarchy"></div>
                            <div class="show-annotations-link inactive-text-link">Show Annotations</div>
                            <div id="ClassesDescription" class="description">
                                <div id="ClassBox"></div>
                            </div>
                        </div>
                    </div>
                    </div>
                    </div>
    
    
    
    
    </div>

blah2
    <core:scripts />
    <module:scripts />
        <script type="text/javascript" src="js/test.js"></script>
  </body>
</html>
