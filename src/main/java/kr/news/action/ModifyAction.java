package kr.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;

public class ModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//자바빈 객체 생성
		NewsVO vo = new NewsVO();
		vo.setNum(Integer.parseInt(request.getParameter("num")));
		vo.setTitle(request.getParameter("title"));
		vo.setWriter(request.getParameter("writer"));
		vo.setPasswd(request.getParameter("passwd"));
		vo.setEmail(request.getParameter("email"));
		vo.setArticle(request.getParameter("article"));
		vo.setFilename(request.getParameter("filename"));
		
		NewsDAO dao = NewsDAO.getInstance();
		NewsVO db_news = dao.getNews(vo.getNum());
		boolean check = false;
		if(db_news!=null) {
			check = db_news.isCheckedPassword(vo.getPasswd());
		}
		if(check) {
			dao.updateNews(vo);
			request.setAttribute("num", vo.getNum());
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/modify.jsp";
	}

}
