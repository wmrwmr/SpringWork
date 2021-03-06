package com.bitwin.bangbang;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bitwin.domain.faqVO;
import com.bitwin.domain.Criteria;
import com.bitwin.domain.PageDTO;
import com.bitwin.service.faqService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/faq/*")
@AllArgsConstructor
public class faqController {
	
	private faqService service;
	
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		
		log.info("list: " + cri);
		
		
		model.addAttribute("list", service.getList(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 200));
		
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));

		
	}
	
	@GetMapping("member-list")
	public void memberList(Criteria cri, Model model) {
		
		log.info("list: " + cri);
		
		
		model.addAttribute("list", service.getList(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 200));
		
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		model.addAttribute("pageMaker", new PageDTO(cri, total));

		
	}
	
	
	
	
	@PostMapping("/register")
	public String register(faqVO faq, RedirectAttributes rttr) {
		
		log.info("register: " + faq);
		
		service.register(faq);
		
		rttr.addFlashAttribute("result", faq.getFqidx());
		
		return "redirect:/faq/list";
	}
	
	@GetMapping({"/get", "/modify", "/member-get"})
	public void get(@RequestParam("fqidx") int fqidx, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/get or modify");
		model.addAttribute("faq", service.get(fqidx));
		
	}
	
	@PostMapping("/modify")
	public String modify(faqVO faq, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("modify: " + faq);
		
		if(service.modify(faq)) {
			rttr.addFlashAttribute("result", "modify");
			
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("start", cri.getStart());
		
		return "redirect:/faq/list";
	}
	
	
	@PostMapping("/remove")
	public String remove(@RequestParam("fqidx") int fqidx, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		
		log.info("remove..." + fqidx);
		
		if(service.remove(fqidx)) {
			
			rttr.addFlashAttribute("result", "delete");
			
		}
		
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("start", cri.getStart());
		
		return "redirect:/faq/list";
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	
	 // ????????? ?????????
    @RequestMapping(value="/imageUpload", method = RequestMethod.POST)
    public void imageUpload(HttpServletRequest request,
    		HttpServletResponse response, MultipartHttpServletRequest multiFile
    		, @RequestParam MultipartFile upload) throws Exception{
    	// ?????? ?????? ??????
    	UUID uid = UUID.randomUUID();
    	
    	OutputStream out = null;
    	PrintWriter printWriter = null;
    	
    	//?????????
    	response.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	try{
    		//?????? ?????? ????????????
    		String fileName = upload.getOriginalFilename();
    		byte[] bytes = upload.getBytes();
    		
    		//????????? ?????? ??????
    		String path = "C:\\Users\\samsung\\Pictures\\Saved Pictures" + "ckImage/";	// ????????? ?????? ??????(?????? ?????? ??????)
    		String ckUploadPath = path + uid + "_" + fileName;
    		File folder = new File(path);
    		System.out.println("path:"+path);	// ????????? ???????????? console??? ??????
    		//?????? ???????????? ??????
    		if(!folder.exists()){
    			try{
    				folder.mkdirs(); // ?????? ??????
    		}catch(Exception e){
    			e.getStackTrace();
    		}
    	}
    	
    	out = new FileOutputStream(new File(ckUploadPath));
    	out.write(bytes);
    	out.flush(); // outputStram??? ????????? ???????????? ???????????? ?????????
    	
    	String callback = request.getParameter("CKEditorFuncNum");
    	printWriter = response.getWriter();
    	String fileUrl = "/bangbang/faq/ckImgSubmit?uid=" + uid + "&fileName=" + fileName; // ????????????
    	
    	// ???????????? ????????? ??????
    	printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
    	printWriter.flush();
    	
    	}catch(IOException e){
    		e.printStackTrace();
    	} finally {
    		try {
    		if(out != null) { out.close(); }
    		if(printWriter != null) { printWriter.close(); }
    	} catch(IOException e) { e.printStackTrace(); }
    	}
    	return;
    }
	
	
	
    // ????????? ????????? ????????? ????????????
    @RequestMapping(value="/ckImgSubmit")
    public void ckSubmit(@RequestParam(value="uid") String uid
    		, @RequestParam(value="fileName") String fileName
    		, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
    	
    	//????????? ????????? ????????? ??????
    	String path = "C:\\Users\\samsung\\Pictures\\Saved Pictures" + "ckImage/";	// ????????? ????????? ??????
    	System.out.println("path:"+path);
    	String sDirPath = path + uid + "_" + fileName;
    	
    	File imgFile = new File(sDirPath);
    	
    	//?????? ????????? ?????? ????????? ?????? ??????????????? ??? ????????? ????????? ????????????.
    	if(imgFile.isFile()){
    		byte[] buf = new byte[1024];
    		int readByte = 0;
    		int length = 0;
    		byte[] imgBuf = null;
    		
    		FileInputStream fileInputStream = null;
    		ByteArrayOutputStream outputStream = null;
    		ServletOutputStream out = null;
    		
    		try{
    			fileInputStream = new FileInputStream(imgFile);
    			outputStream = new ByteArrayOutputStream();
    			out = response.getOutputStream();
    			
    			while((readByte = fileInputStream.read(buf)) != -1){
    				outputStream.write(buf, 0, readByte); 
    			}
    			
    			imgBuf = outputStream.toByteArray();
    			length = imgBuf.length;
    			out.write(imgBuf, 0, length);
    			out.flush();
    			
    		}catch(IOException e){
    			e.printStackTrace();
    		}finally {
    			outputStream.close();
    			fileInputStream.close();
    			out.close();
    			}
    		}
    }
	
	
	
	
	
	
	
}
