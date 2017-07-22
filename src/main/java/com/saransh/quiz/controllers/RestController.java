package com.saransh.quiz.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.saransh.quiz.models.Login;
import com.saransh.quiz.models.Question;
import com.saransh.quiz.models.Register;
import com.saransh.quiz.models.Response;
import com.saransh.quiz.models.Survey;
import com.saransh.quiz.repository.MySQLRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@org.springframework.web.bind.annotation.RestController
public class RestController {
	@Autowired
	private MySQLRepository repository;
	
	@RequestMapping(value = "/register" , method = RequestMethod.POST )
	 public Response register(@RequestBody Register request){
		Response response = new Response();
		
		if(repository.isRegistered(request.getEmail())){
			response.setStatus("Already registered");
			
		 }else{
			 response.setStatus("Questions");
		 }
		 return response;
	 }
	
	@RequestMapping(value = "/questions" , method = RequestMethod.GET )
	 public Response questions(){
		Response response = new Response();
		
		response.setData(repository.fetchQuestion(false));
		
		return response;
	 }
	
	@RequestMapping(value = "/complete" , method = RequestMethod.POST )
	 public Response complete(@RequestBody Survey request){
		Response response = new Response();
		
		if(repository.isRegistered(request.getUser().getEmail())){
			response.setStatus("Already registered");
			
		 }else{
			List<Question> questions =  repository.fetchQuestion(true);
			int score = 0;
			for(Question question: questions){
				for(Question submittedQuestion: request.getQuestions()){
					if(submittedQuestion.getId() == question.getId()){
						if(submittedQuestion.getChoice() == question.getAnswer()){
							score++;
						}
						break;
					}
				}
			}
			
			Register user = request.getUser();
			user.setScore(score);
			user.setTotalScore(request.getQuestions().size());
			repository.save(user);
			
			response.setStatus("completed");
			response.setData(user);
		 }
		 return response;
	 }
	
	@RequestMapping(value = "/admin" , method = RequestMethod.POST )
	 public Response login(@RequestBody Login request){
		Response response = new Response();
		
		if(repository.login(request)){
			response.setStatus("Success");
			response.setData(repository.getResults());

			
		 }else{
			 response.setStatus("Failure");
		 }
		 return response;
	 }
	

	@RequestMapping(value = "/health" , method = RequestMethod.GET)
	public String health(){
		System.out.println("in");
		return "healthy";
	}
}
