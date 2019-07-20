package org.haxwell.dtim.techprofile.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.haxwell.dtim.techprofile.entities.Question;
import org.haxwell.dtim.techprofile.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@RestController
public class QuestionAPIController {

	@Autowired
	QuestionService questionService;
	
	QuestionAPIController() {
		
	}
	
	@RequestMapping(value = { "/api/question/{lineItemLevelId}/{lineItemLevelIndex}" }, method=RequestMethod.GET)
	public Iterable<Question> get(@PathVariable Long lineItemLevelId, @PathVariable Long lineItemLevelIndex) {
		return questionService.getByLineItemAndLevelNumber(lineItemLevelId, lineItemLevelIndex);
	}
	
	@RequestMapping(value = { "/api/question/{questionId}/lineitem/levels" }, method=RequestMethod.GET)
	public Iterable<Question> getAllLineItemLevelsAssociatedWithTheGivenQuestion(@PathVariable Long questionId) {
		return questionService.getAllLineItemAndLevelsFor(questionId);
	}
	
	@RequestMapping(value = { "/api/question/{questionId}/lineitem/levels" }, method=RequestMethod.POST)
	public boolean setAllLineItemLevelsAssociatedWithTheGivenQuestion(HttpServletRequest request, @PathVariable Long questionId) {
		String count = request.getParameter("count");
		
		if (count != null) {
			int[][] arr = new int[Integer.parseInt(count)][2];
			
			for (int x = 0; x < Integer.parseInt(count); x++) {
				arr[x][0] = Integer.parseInt(request.getParameter("liId" + x));
				arr[x][1] = Integer.parseInt(request.getParameter("liVal" + x));
			}
			
			questionService.setAllLineItemAndLevelsFor(questionId, arr);
			
			return true;
		}
		
		return false;
	}
	
	@RequestMapping(value = { "/api/question/{id}"}, method=RequestMethod.GET)
	public Iterable<Question> getById(@PathVariable String id) {
		if ("all".equals(id)) {
			return questionService.getAll();
		} else {
			ArrayList<Question> rtn = new ArrayList<Question>();
			rtn.add(questionService.getById(Long.parseLong(id)).get());
			
			return rtn;
		}
	}
	
	@RequestMapping(value = { "/api/question/save"}, method=RequestMethod.POST)
	public Question save(HttpServletRequest request) {
		Question rtn = null;
		
		try {
			String str = request.getReader().lines().collect(Collectors.joining());
			net.minidev.json.parser.JSONParser parser = new JSONParser();
			
			JSONObject obj = (JSONObject)parser.parse(str);
			
			// todo... look for a question.. use it.. if not, this is a new thing, create a new question. save the object. save the lilvassocaitionssssssz and return.
			JSONObject q = ((JSONObject)obj.get("question"));
			JSONArray llla = ((JSONArray)obj.get("lilvassociations"));

			int[][] lilvassociations = new int[llla.size()][];
			
			for (int i = 0; i < llla.size(); i++) {
				int[] tArr = new int[2];
				
				tArr[0] = Integer.parseInt(((JSONArray)llla.get(i)).get(0).toString());
				tArr[1] = Integer.parseInt(((JSONArray)llla.get(i)).get(1).toString());
				
				lilvassociations[i] = tArr;
			}
			
			rtn = questionService.save(
					Long.parseLong(q.getAsString("id")),
					q.getAsString("text"),
					lilvassociations);
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rtn;
	}
}
