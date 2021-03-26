package com.TSTpractice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.TSTpractice.dto.Article;
import com.TSTpractice.util.Util;

@Controller
public class UsrArticleController {
	private List<Article> articles;
	private int articlesLastId;
	
	public UsrArticleController() {
		articles = new ArrayList<>();
		
		articles.add(new Article(++articlesLastId,Util.getNowDateStr(),Util.getNowDateStr(),"제목1","내용1"));
		articles.add(new Article(++articlesLastId,Util.getNowDateStr(),Util.getNowDateStr(),"제목2","내용2"));
	}
	@RequestMapping("/usr/article/detail")
	@ResponseBody
	public Article showDetail(int id){
		
		return articles.get(id - 1);
	}
	
	@RequestMapping("/usr/article/list")
	@ResponseBody
	public List<Article> showList(){
		return articles;
	}
	
	@RequestMapping("/usr/article/doAdd")
	@ResponseBody
	public Map<String, Object> doAdd(String title, String body){
		String regDate = Util.getNowDateStr();
		String updatDate = regDate;
		articles.add(new Article(++articlesLastId,regDate,updatDate,title,body));
		
		Map<String, Object> rs = new HashMap<>();
		rs.put("resultCode", "S - 1");
		rs.put("msg", "성공");
		rs.put("id", articlesLastId);
		
		return rs;
	}
	
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public Map<String, Object> doDelete(int id){
		boolean deleteArticleRs = deleteArticle(id);
		
		Map<String, Object> rs = new HashMap<>();
		if(deleteArticleRs) {
			rs.put("resultCode", "S - 1");
			rs.put("msg", "성공");
		}else {
			rs.put("resultCode", "F - 1");
			rs.put("msg", "해당 게시물은 삭제되었거나 없는 게시물입니다.");
		}
		
		rs.put("id", id);
		
		return rs;
	}
	private boolean deleteArticle(int id) {
		for(Article article : articles) {
			if(article.getId() == id) {
				articles.remove(article);
				return true;
			}
		}
		return false;
		
	}
	
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public Map<String, Object> doModify(int id,String title, String body){
		Article selArticle = null;
		
		for(Article article : articles) {
			if(article.getId() == id) {
				selArticle = article;
				break;
			}
		}
		Map<String, Object> rs = new HashMap<>();
		if(selArticle == null) {
			rs.put("resultCode", "F - 1");
			rs.put("msg", String.format("%d번째 게시물은 존재하지 않습니다", id));
			return rs;
		}
		selArticle.setUpdateDate(Util.getNowDateStr());
		selArticle.setTitle(title);
		selArticle.setBody(body);
		
		rs.put("resultCode", "S - 1");
		rs.put("msg", String.format("%d번째 게시물은 수정되었습니다.", id));
		rs.put("id", id);
		
		return rs;
	}
}
