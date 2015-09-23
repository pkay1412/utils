package net.sf.ahtutils.interfaces.rest.survey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.survey.Survey;

@Path("/rest/survey")
public interface UtilsSurveyRest
{
	@GET @Path("/structure/{id:[1-9][0-9]*}") @Produces(MediaType.APPLICATION_XML)
	Survey surveyStructure(@PathParam("id") long id);
	
	@GET @Path("/answers/{id:[1-9][0-9]*}") @Produces(MediaType.APPLICATION_XML)
	Survey surveyAnswers(@PathParam("id") long id);
	
	@GET @Path("/question/units") @Produces(MediaType.APPLICATION_XML)
	Aht surveyQuestionUnits();
}