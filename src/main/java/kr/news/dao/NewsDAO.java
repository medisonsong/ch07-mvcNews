package kr.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.news.vo.NewsVO;
import kr.util.DBUtil;

public class NewsDAO {
	//싱글턴 패턴
	private static NewsDAO instance = new NewsDAO();
	public static NewsDAO getInstance() {
		return instance;
	}
	private NewsDAO() {}
	
	
	//뉴스 등록
	public void registerNews(NewsVO vo)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션 풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql="INSERT INTO dailynews (num,title,writer,passwd,email,article,filename) values (dailynews_seq.nextval,?,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getPasswd());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getArticle());
			pstmt.setString(6, vo.getFilename());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}

	//뉴스의 총 개수
	public int getCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM dailynews";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문을 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	//목록
	public List<NewsVO> getList(int startRow, int endRow)
										throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NewsVO> list = null;
		String sql = null;

		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM (SELECT a.*,rownum rnum "
					+ "FROM (SELECT * FROM dailynews ORDER BY "
					+ "num DESC)a) WHERE rnum>=? AND rnum<=?";
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();

			list = new ArrayList<NewsVO>();
			while(rs.next()) {
				NewsVO vo = new NewsVO();

				vo.setNum(rs.getInt("num")); 
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setReg_date(rs.getDate("reg_date"));

				list.add(vo);
			}

		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}

		return list;
	}

	
	//뉴스 상세 정보
	public NewsVO getNews(int num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVO newsVO = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "SELECT * FROM dailynews WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				newsVO = new NewsVO();
				newsVO.setNum(rs.getInt("num"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setWriter(rs.getString("writer"));
				newsVO.setPasswd(rs.getString("passwd"));
				newsVO.setEmail(rs.getString("email"));
				newsVO.setArticle(rs.getString("article"));
				newsVO.setFilename(rs.getString("filename"));
				newsVO.setReg_date(rs.getDate("reg_date"));
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return newsVO;
	}
	
	
	//뉴스 수정
	public void updateNews(NewsVO vo)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = ""; // 상황에 따라 동적으로 처리하기 위해 sql과 분리해서 sub를 만듦	
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(vo.getFilename()!=null) { //파일이 업로드 된 경우
				sub_sql += ",filename=?";
			}
			
			sql = "UPDATE dailynews SET title=?, writer=?, email=?, article=?"
					+ sub_sql + " WHERE num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			
			//?에 데이터 바인딩 (물음표 번호가 달라지기 때문에 cnt로 자동으로 일치시켜줌)
			pstmt.setString(++cnt, vo.getTitle());
			pstmt.setString(++cnt, vo.getWriter());
			pstmt.setString(++cnt, vo.getEmail());
			pstmt.setString(++cnt, vo.getArticle());
			
			if(vo.getFilename()!=null) {
				pstmt.setString(++cnt, vo.getFilename());
			}
			pstmt.setInt(++cnt, vo.getNum());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
	
	
	//뉴스 삭제
	public void deleteNews(int num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM dailynews WHERE num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	
}
