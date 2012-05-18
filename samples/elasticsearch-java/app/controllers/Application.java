package controllers;

import indexing.Team;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.terms.TermsFacet;
import play.modules.elasticsearch.IndexQuery;
import play.modules.elasticsearch.IndexResults;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
  
  public static Result index() {

      // search All
      IndexResults<Team> all = Team.find.findAll();

      // search All + facet country
      IndexQuery<Team> queryCountry = new IndexQuery<Team>(Team.class, QueryBuilders.matchAllQuery());
      queryCountry.addFacet(FacetBuilders.termsFacet("countryF").field("country.name"));
      IndexResults<Team> allAndFacetCountry = Team.find.find(queryCountry);
      TermsFacet countryF = allAndFacetCountry.facets.facet("countryF");

      // search All + facet players.position
      IndexQuery<Team> queryPlayers = new IndexQuery<Team>(Team.class, QueryBuilders.matchAllQuery());
      queryPlayers.addFacet(FacetBuilders.termsFacet("playersF").field("players.position").nested("players"));
      IndexResults<Team> allAndFacetAge = Team.find.find(queryPlayers);


      return ok(index.render("Your new application is ready."));
  }
  
}