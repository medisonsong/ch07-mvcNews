package kr.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class FileUtil {
	//인코딩 타입
	private static final String ENCODING_TYPE = "utf-8";
	//최대 업로드 사이즈
	private static final int MAX_SIZE = 50*1024*1024;
	//업로드 상대 경로
	private static final String UPLOAD_PATH = "/upload";
	
	//파일 업로드를 실제로 처리하는 메서드를 만들거임
	//파일 업로드
	public static MultipartRequest createFile(HttpServletRequest request)throws IOException{
		//업로드 절대 경로
		String upload = request.getServletContext().getRealPath(UPLOAD_PATH); // 상대경로를 주소에 넣기
		return new MultipartRequest(request, upload, MAX_SIZE, ENCODING_TYPE, new DefaultFileRenamePolicy());
	}
	
	//파일 삭제
	public static void removeFile(HttpServletRequest request, String filename) {
		if(filename!=null) { // 파일명이 존재할 때
			//업로드 절대 경로
			String upload = request.getServletContext().getRealPath(UPLOAD_PATH);
			File file = new File(upload + "/" + filename); // file안에 file을 삭제하는 기능이 있음
			if(file.exists()) file.delete(); //존재할 경우에만 (true) 삭제
		}
	}
}
