package com.woongki.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.apache.ibatis.javassist.bytecode.analysis.MultiArrayType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.woongki.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("upload from");
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile) {
		
		String uploadFolder = "C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("--------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File size : " + multipartFile.getSize());
			
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(saveFile);
				
 			} catch(Exception e) {
 				log.error(e.getMessage());;
 			}
		}
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("load ajax");
	}
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch(IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	private String getFolder() { //
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-",  File.separator);
	}
	
	@PostMapping(value="/uploadAjaxAction",
			produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachFileDTO>>  uploadAjaxPost(MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<AttachFileDTO>();
		String uploadFolder = "C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload";
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, getFolder()); //파일이 생성되는 날짜에 맞춰 뉴폴더에 생성
		
		log.info("upload path : " + uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		} //make folder 폴더 생성
		
		for(MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();

			String uploadFileName = multipartFile.getOriginalFilename();
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			attachDTO.setFileName(uploadFileName); 
			
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName; //파일 이름이 중복이면 기존 파일을 삭제에 대한 제거 방지
			
			log.info("Upload File Name : " + uploadFileName);
//			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename()); 
			
			try {
				File saveFile = new File(uploadPath, uploadFileName); 
				
				log.info(saveFile);
				
				multipartFile.transferTo(saveFile); //원본 파일 저장
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				log.info(attachDTO);
				
			
				if(checkImageType(saveFile)) { //이미지 여부 저장 된 곳 변경
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(
							uploadPath, "s_" + uploadFileName)); //이미지 파일은 s_ 로 시작
					Thumbnailator.createThumbnail(multipartFile.getInputStream(),
							thumbnail, 100, 100); //새 이미지 저장 hight width 이미지 크기 조절
					thumbnail.close();
				}

				list.add(attachDTO);
				
 			} catch(Exception e) {
 				log.error(e.getMessage());;
 			}
		}
		return new ResponseEntity<List<AttachFileDTO>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.info("fileName: " + fileName);
		File file = new File("C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload\\" + fileName);
		log.info("file : " + file);
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type",  Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
			log.info(" =========================================");
			log.info("result " +result);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	

	 	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
		@ResponseBody
		public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName) {

			Resource resource = new FileSystemResource("C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload\\" + fileName);

			if (resource.exists() == false) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			String resourceName = resource.getFilename();

			// remove UUID
			String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);// _뒤 부터 리얼네임

			HttpHeaders headers = new HttpHeaders();
			try {

				String downloadName = null;

				if ( userAgent.contains("Trident")) {
					log.info("IE browser");
					downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");
				}else if(userAgent.contains("Edge")) {
					log.info("Edge browser");
					downloadName =  URLEncoder.encode(resourceOriginalName,"UTF-8");
				}else {
					log.info("Chrome browser");
					downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				}
				
				log.info("downloadName: " + downloadName);

				headers.add("Content-Disposition", "attachment; filename=" + downloadName);

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		}
		@PostMapping("/deleteFile")
		@ResponseBody
		public ResponseEntity<String> deleteFile(String fileName, String type) {

			log.info("deleteFile: " + fileName);

			File file;

			try {
				file = new File("C:\\Users\\dnr26\\eclipse-workspace\\commnunity\\src\\main\\webapp\\resources\\upload\\"+ URLDecoder.decode(fileName, "UTF-8"));

				file.delete();

				if (type.equals("image")) {

					String largeFileName = file.getAbsolutePath().replace("s_", "");

					log.info("largeFileName: " + largeFileName);

					file = new File(largeFileName);

					file.delete();
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<String>("deleted", HttpStatus.OK);

		}
		
}
