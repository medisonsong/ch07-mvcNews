package kr.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;
import kr.util.FileUtil;

public class DeleteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		
		//전송된 데이터 반환
		int num = Integer.parseInt(request.getParameter("num"));
		String passwd = request.getParameter("passwd");
		
		NewsDAO dao = NewsDAO.getInstance();
		NewsVO db_news = dao.getNews(num);
		boolean check = false;
		
		if(db_news!=null) {
			//비밀번호 일치 여부 체크
			check=db_news.isCheckedPassword(passwd);
		}
		if(check) {
			//글 삭제
			dao.deleteNews(num);
			//쓰레기 파일 삭제 (쌓이는걸 방지하기 위함)
			FileUtil.removeFile(request, db_news.getFilename());
		}
		//UI처리를 위해 check 저장
		request.setAttribute("check", check);
		//JSP 경로 반환
		return "/WEB-INF/views/delete.jsp";
	}

}
