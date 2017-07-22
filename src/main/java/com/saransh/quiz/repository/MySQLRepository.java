package com.saransh.quiz.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.saransh.quiz.models.Login;
import com.saransh.quiz.models.Question;
import com.saransh.quiz.models.Register;

@Repository
public class MySQLRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate ;
	
	public boolean isRegistered(String email){
		String sql = "SELECT count(*) as COUNT FROM USER where EMAIL = ?";
		List<Map<String, Object>> resultlist = jdbcTemplate.queryForList(sql, new Object[]{email});
		
		return Integer.parseInt(resultlist.get(0).get("COUNT").toString()) > 0 ;
	}
	public void save(Register user){
		 String sql = "INSERT INTO `quizeria`.`USER` (`F_NAME`, `L_NAME`, `EMAIL`, `SCORE`, `TOTAL_SCORE`) VALUES (?,?,?,?,?)";
		 jdbcTemplate.update(sql, user.getFname(), user.getLname(), user.getEmail(), user.getScore(), user.getTotalScore());
	}
	
	public List<Question> fetchQuestion(boolean withAnswers){
		String sql = "SELECT * FROM QUESTIONS";
		List<Map<String, Object>> resultlist = jdbcTemplate.queryForList(sql);
		List<Question> output = new ArrayList<>();
		
		for(Map<String,Object> row : resultlist ){
			Question question = new Question();
			
			question.setQuestion(row.get("QUESTION").toString());
			question.setId(Integer.parseInt(row.get("ID").toString()));
			
			List<String> options = new ArrayList<>();
			
			options.add(row.get("OPTION_1").toString());
			options.add(row.get("OPTION_2").toString());
			options.add(row.get("OPTION_3").toString());
			options.add(row.get("OPTION_4").toString());
			
			question.setOptions(options);
			
			if(withAnswers){
				question.setAnswer(Integer.parseInt(row.get("ANSWER").toString()));
			}
			
			output.add(question);
		}
		return output;
	}
	
	public boolean login(Login login){
		String sql = "SELECT count(*) as COUNT FROM AUTH where USERNAME = ? AND PASSWORD = ?";
		List<Map<String, Object>> resultlist = 
				jdbcTemplate.queryForList(sql, new Object[]{login.getUsername(), login.getPassword()});
		
		return Integer.parseInt(resultlist.get(0).get("COUNT").toString()) > 0 ;
	}
	
	public List<Register> getResults(){
		String sql = "SELECT * FROM USER";
		List<Map<String, Object>> resultlist = jdbcTemplate.queryForList(sql);
		List<Register> output = new ArrayList<>();
		
		for(Map<String,Object> row : resultlist ){
			Register user = new Register();
			
			user.setFname(row.get("F_NAME").toString());
			user.setLname(row.get("L_NAME").toString());
			user.setEmail(row.get("EMAIL").toString());
			user.setScore(Integer.parseInt(row.get("SCORE").toString()));
			user.setTotalScore(Integer.parseInt(row.get("TOTAL_SCORE").toString()));

			output.add(user);
		}
		return output;
	}
	
}
