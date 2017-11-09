package com.free.test.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.free.test.file.model.FileDto;
import com.free.test.file.model.MemberDto;
import com.free.test.file.service.CommonService;
import com.free.test.file.service.FileService;

@Controller
@RequestMapping("/file")
public class FileController {
    private Logger log = Logger.getLogger(getClass());

	@Autowired
	private FileService fileService;
	
	@Autowired
	private CommonService commonService;
	
	private static final String FILEPATH = "X:/javadata/workSpace/myproject/testfileupload/WebContent/doc/stored/";

/////////////////////////// 파일 삭제 리스트에서 삭제 => deleted >> 1로 업데이트
	@RequestMapping(value="/deleteupdatetoone", method=RequestMethod.GET, headers={"Content-Type=application/json"})
	@ResponseBody
	public Map<String, String> deleteUpdatedToOne (@RequestParam("fileSeq") int fileSeq) {
		fileService.deleteUpdatedToOne(fileSeq);
		Map<String, String> map = new HashMap<>();
		map.put("bcode", "1");
		
		return map;
	}
	
//	파일 이름 실시간으로 보여주기
	@RequestMapping(value="/returnfilename", 
			produces="application/json;charset=UTF-8", 
			method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> returnFileName(@RequestParam Map<String, String> map) {
		
		return map;
	}
	
//	파일 list 뿌리기
	@RequestMapping(value="/list", produces="application/json;charset=UTF-8", method=RequestMethod.GET)
	@ResponseBody
	public List<FileDto> list(@RequestParam("bcode") int bcode, HttpServletRequest request) {
		List<FileDto> list = fileService.list(bcode);
		return list;
	}

/////////////////////파일 수정
	@RequestMapping(value="/modify.html", method=RequestMethod.POST)
	@ResponseBody
	public ModelAndView modify(HttpSession session, @RequestParam("fileSeq") int originalFileSeq,
				@RequestParam("file") MultipartFile data, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		FileDto infoForFileDtoBeingModified = returnFileDtoBeingStored(session, data, request);
		if (infoForFileDtoBeingModified != null) {
	
			//DB에 저장하기
			FileDto updatedFileDto = fileService.modify(originalFileSeq, infoForFileDtoBeingModified);
			
			//저장한 파일 정보를 저장해서 페이지 이동
			mav.addObject("storedFile", updatedFileDto);
			mav.setViewName("/updownload/uploadok");
		}
		return mav;
	}
	
////////////////////////////////////////// upload ///////////////////////////////////////////	
	@RequestMapping(value="/upload.html", 
			method=RequestMethod.POST)
	public ModelAndView store(HttpSession session,
				@RequestParam("file") MultipartFile data, 
				HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		FileDto fileDto = this.returnFileDtoBeingStored(session, data, request);
		if (fileDto != null) {
			fileDto.setDeleted(0);
			fileDto.setOriginalFileSeq(fileDto.getFileSeq());
			
			//DB에 저장하기
			fileService.store(fileDto);
			
			//저장한 파일 정보를 저장해서 페이지 이동
			mav.addObject("storedFile", fileDto);
			mav.setViewName("/updownload/uploadok");
		}
		return mav;
	}
	
    public static String getRandomString(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
	private FileDto returnFileDtoBeingStored (HttpSession session, 
			MultipartFile data, 
			HttpServletRequest request) {
		
		MemberDto loginInfo = (MemberDto) session.getAttribute("loginInfo");
		
		int fileSeq = commonService.getNextFileSeq();
		
		FileDto fileDto = new FileDto();
		
        if(data.isEmpty() == false){
            log.debug("------------- file start -------------");
            log.debug("name : "+ data.getName());
            log.debug("filename : "+data.getOriginalFilename());
            log.debug("size : "+ data.getSize());
            log.debug("-------------- file end --------------\n");
        }

		if(!data.isEmpty()) {
			String originalFileName = data.getOriginalFilename();//a.b.c.jpg
			String originalFileNameExt = originalFileName.substring(originalFileName.lastIndexOf('.'));
		
			
			//DateFormat df = new SimpleDateFormat("yyMMdd");
			//String today = df.format(new Date());
			String storedPath = request.getServletContext().getRealPath("/upload");// + File.separator + today;
			// 위 대로 하면 실제로 파일이 저장되는 위치!!!
			//"X:/javadata/workSpace/myproject/.metadata/.plugins/org.eclipse.wst.server.core/tmp1/wtpwebapps/testfilupload/upload/170813"
						
			/*
			DateFormat df = new SimpleDateFormat("yyMMdd");
			String today = df.format(new Date());
			
			String storedPath = FILEPATH + today;
			*/
			
			// 저장위치에 위치할 폴더를 만든다.
			File dir = new File(storedPath);
			
			// 저장위치에 위치할 폴더가 존재하지 않으면 그 위치에 폴더를 생성한다.
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			//저장할 파일이름을 만든다.
			String storedFileName = getRandomString() + originalFileNameExt;//170803657654687984.jpg
			
			
			try {
				//dir에 생성된 폴더 안에 storedFileName으로 된 파일을 저장한다.
				data.transferTo(new File(storedPath, storedFileName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			//DB에 파일에 관한 정보를 저장한다.
			long fileSize = (long) data.getSize();
			
			fileDto.setBcode(1);
			fileDto.setEmail(loginInfo.getEmail());
			fileDto.setFileSeq(fileSeq);
			fileDto.setFileSize(fileSize);
			fileDto.setOriginalFileName(originalFileName);
			fileDto.setStoredFileName(storedFileName);
			fileDto.setStoredPath(storedPath);
			
			if (checkIsImage (new File(storedPath, storedFileName))) {
				//Thumbnail 이미지 만드는 함수를 호출 후 썸네일 파일 이름이랑 패스 저장
				Map <String, String> thumbMap = saveScaledImage(storedPath, storedFileName);
				fileDto.setThumbStoredFileName(thumbMap.get("thumbStoredFileName"));
				fileDto.setThumbStoredPath(thumbMap.get("thumbStoredPath"));				
			} else {
				fileDto.setThumbStoredFileName("");
				fileDto.setThumbStoredPath("");					
			}
		} else {
			fileDto = null;
		}
		return fileDto;
	}
	
	
	private  Map<String, String> saveScaledImage(String storedPath, String storedFileName){
		Map<String, String> map = new HashMap<>();
		String thumbStoredFileName = "thumb-" + storedFileName;
		String thumbStoredPath = storedPath + File.separator + "thumb";
		map.put("thumbStoredFileName", thumbStoredFileName);
		map.put("thumbStoredPath", thumbStoredPath);

	    try {
	    	// 저장위치에 위치할 폴더를 만든다.
	    	File dir = new File(thumbStoredPath);
	    	
	    	// 저장위치에 위치할 폴더가 존재하지 않으면 그 위치에 폴더를 생성한다.
	    	if(!dir.exists()) {
	    		dir.mkdirs();
	    	}

	    	String imagePath = storedPath + File.separator + storedFileName;
	    	BufferedImage sourceImage = ImageIO.read(new File(imagePath));
	        int width = sourceImage.getWidth();
	        int height = sourceImage.getHeight();
	        
			double extraSize = 0;
			double percentHeight = 0;
			double percentWidth = 0;
			BufferedImage img = null;
			Image scaledImage = null;
			BufferedImage img2 = null;
	        
			if(width>height){
	        	extraSize=  height-100;
	            percentHeight = (extraSize/height)*100;
	            percentWidth = width - ((width/100)*percentHeight);
	            img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
	            scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, Image.SCALE_SMOOTH);
		        img.createGraphics().drawImage(scaledImage, 0, 0, null);
		        img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);
			} else if (width == height) {
//	        	extraSize=  height-100;
//	            percentHeight = (extraSize/height)*100;
//	            percentWidth = width - ((width/100)*percentHeight);
//	            img = new BufferedImage((int)percentWidth, 100, BufferedImage.TYPE_INT_RGB);
//	            scaledImage = sourceImage.getScaledInstance((int)percentWidth, 100, Image.SCALE_SMOOTH);
//		        img.createGraphics().drawImage(scaledImage, 0, 0, null);
//		        img2 = img.getSubimage((int)((percentWidth-100)/2), 0, 100, 100);		
				
				// java image scaling library를 이용한 썸네일 만들기
				img2 = Scalr.resize(sourceImage, Method.QUALITY, 100, 100, Scalr.OP_ANTIALIAS);
			} else{
	            extraSize = width-100;
	            percentWidth = (extraSize/width)*100;
	            percentHeight = height - ((height/100)*percentWidth);
	            img = new BufferedImage(100, (int)percentHeight, BufferedImage.TYPE_INT_RGB);
	            scaledImage = sourceImage.getScaledInstance(100,(int)percentHeight, Image.SCALE_SMOOTH);
	            img.createGraphics().drawImage(scaledImage, 0, 0, null);
	            img2 = img.getSubimage(0, (int)((percentHeight-100)/2), 100, 100);
			}
	        
	        ImageIO.write(img2, "jpg", new File(thumbStoredPath, thumbStoredFileName));    
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return map;
	}
	

	
////////////////////////////////////////////////////////////
//
/*	@RequestMapping(value="/file_uploader_html5.html", method=RequestMethod.POST)
	@ResponseBody
	public String multiUpload(HttpServletRequest request) {
		StringBuffer sb = null;
		try {
			String filename = request.getHeader("file-name");
			String ext = filename.substring(filename.lastIndexOf('.'));//.jpg
			String realPath = request.getServletContext().getRealPath("/SE2");
			String saveDirectory = realPath + File.separator + "upload" + File.separator;
			File dir = new File(saveDirectory);
			if(!dir.exists())
				dir.mkdirs();
			String savename = UUID.randomUUID().toString() + ext;
			
			InputStream is = request.getInputStream();
			OutputStream os = new FileOutputStream(saveDirectory + savename);
			int read = 0;
			byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
			while((read = is.read(b, 0, b.length)) != -1) {
				os.write(b, 0, read);
			}
			
			if(is != null)
				is.close();
			if(os != null) {
				os.flush();
				os.close();
			}
			
			sb = new StringBuffer();
			sb.append("&bNewLine=true")
			.append("&sFileName=" + filename)
			.append("&sFileURL=")
			.append(request.getContextPath() + "/SE2/upload/" + savename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}*/
	
	//다운로드
	@RequestMapping(value="/download", method=RequestMethod.GET)//, produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadFile(@RequestParam("fileSeq") int fileSeq, HttpServletResponse response) {
		
		FileDto fileDto = fileService.getFileInfo(fileSeq);
		String file = fileDto.getStoredPath() + File.separator + fileDto.getStoredFileName();
		
		String downloadName = "";
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>> " + fileDto.getOriginalFileName());
		try {
//			downloadName = encodeURI(URLEncoder.encode(fileDto.getOriginalFileName(), "utf-8"));
			downloadName = encodeURI(URLEncoder.encode(fileDto.getOriginalFileName().replaceAll(" ", "|"), "utf-8"));
			byte fileByte[] = FileUtils.readFileToByteArray(new File(file));
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setContentLength(fileByte.length);
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + downloadName + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			
			response.getOutputStream().write(fileByte);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//URLEncoder.encode 했을 때 변환 된 특수 문자 코드를 원래 특수문자로 바꿔주기 // 인코더 안 해주면 다운로드 시 한글 깨짐 
	public static synchronized String encodeURI(String s) {
	    String[] findList = {"#", "+", "&", "%", " ", "@", "(", ")", " "};// %7C >> |
	    String[] replList = {"%23", "%2B", "%26", "%25", "%20", "%40", "%28", "%29", "%7C"};
	    return StringUtils.replaceEach(s, replList, findList);
	}
	
	//파일 확장자 체크하기
	public static boolean checkIsImage (File file){
		String fileName = file.getName();
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + fileName);
		
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		//final String[] BAD_EXTENSION = { "jsp", "php", "asp", "html","perl"};
		final String[] IMG_EXTENTION = { "jpg", "jpeg", "bmp", "png","gif"};
		
		int len = IMG_EXTENTION.length;
		for(int i = 0; i < len; i++) {
			if(ext.equalsIgnoreCase(IMG_EXTENTION[i])) {
				return true; // 이미지일 때!!!
			}
		}
		return false;
	}

}
