package org.kdea.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
/*@RequestMapping("/emp/")*/
public class UploadController {

	@Autowired
	private FileValidator fileValidator;
	
	@Autowired
	private UploadService svc;
	

	@RequestMapping(value = "/upload/form", method = RequestMethod.GET)
	public String getUploadForm(@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) {
		return "upload/uploadForm";
	}

	/*
	 * @ModelAttribute("desc") String desc �� ���������� ���۵Ǵ� desc ��� �Ķ���͸� Model ��ü��
	 * desc ��� �̸����� �����Ѵٴ� ���� �ǹ��Ѵ�. Model �� ����� �����ʹ� ���ο��� request ��ü�� ����ǹǷ� �信��
	 * ������ �� �ְ� �ȴ�
	 */
	@RequestMapping(value = "/upload/fileUpload", method = RequestMethod.POST)
	public ModelAndView fileUploaded(@ModelAttribute("desc") String desc,@ModelAttribute("uploadedFile") UploadedFile uploadedFile, BindingResult result) {

		InputStream inputStream = null;
		OutputStream outputStream = null;
		
		UploadVO vo = new UploadVO();

		// ���۵� �� �ʵ� ������(desc)�� �޴´�
		System.out.println("desc=" + desc);

		// ���ε�� ������ ������ ��η� �̵��Ѵ�
		MultipartFile file = uploadedFile.getFile();
		fileValidator.validate(uploadedFile, result);

		String fileName = file.getOriginalFilename();

		//������ �ȿ�����쿡 ���� ��������
		if (result.hasErrors()) {
			return new ModelAndView("upload/uploadForm");
		}
		
		System.out.println("���������̸�"+fileName);
		vo.setOrginfname(fileName); // ���⼭ ��ü�� ��¥�̸��� ����ش�.
		vo.setFdesc(desc); // ���⼭ ��ü�� ����
		
		/*boolean overlapcheck = svc.overlapfname(fileName);
		if(overlapcheck){
			// ���� ���°� �ߺ��̸��� �ֱ� �����̴� 
			
			//�� �ٿ�����.
			//�����̸��� ���̸����� �ٲ�����Ѵ�.
			fileName=fileName+""+new Date().getTime();
			System.out.println("���θ������ ���ϸ�"+fileName);
			vo.setChangefname(fileName);
		}
		//������ �׳� �����ص� �ɰŴ� ��
*/		

		try {
			inputStream = file.getInputStream();

			File newFile = new File("D:/test/upload/" + fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}
			else{
				fileName=fileName+""+new Date().getTime();
				System.out.println("���θ������ ���ϸ�"+fileName);
				vo.setChangefname(fileName);
				
				newFile = new File("D:/test/upload/" + fileName);
				
			}
			outputStream = new FileOutputStream(newFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			//����Ǹ� ������ �Ǿ����� ���⼭ insert�� ��������
			boolean check = svc.insertFileinfo(vo);
			System.out.println("��� �����̸� ������"+check);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return new ModelAndView("upload/uploadedFile", "filename", fileName);
	}

	@RequestMapping("/upload/download")
	@ResponseBody
	public byte[] getImage(HttpServletResponse response, @RequestParam String filename) throws IOException {
		
		UploadVO vo =svc.getrealfname(filename);
		
		
		File file = new File("D:/test/upload/" + filename);
		byte[] bytes = org.springframework.util.FileCopyUtils.copyToByteArray(file);

		// �ѱ��� http ����� ����� �� ���⶧���� ���ϸ��� �������� ���ڵ��Ͽ� ����� �����Ѵ�
		String fn = new String(vo.getOrginfname().getBytes(), "iso_8859_1");

		response.setHeader("Content-Disposition", "attachment; filename=\"" + fn + "\"");
		response.setContentLength(bytes.length);
		response.setContentType("image/jpeg");

		return bytes;
	}



}