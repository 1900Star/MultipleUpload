

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class UpPic
 */
@WebServlet("/MultipartyUpload")
@MultipartConfig(location="F:\\")
public class MultipartyUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MultipartyUpload() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
		
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
 
		if (isMultipart) {
			String filePath = request.getSession().getServletContext()
					.getRealPath("/files");
//			String filePath="I:\\Pic";
			System.out.println(filePath);
 
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
 
			try {
				FileItemFactory fileItemFactory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(
						fileItemFactory);
				
				upload.setHeaderEncoding("UTF-8");
				
				List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(request));
 
				for (FileItem fileItem : fileItems) {
 
					if (fileItem.isFormField()) {
 
						String name = fileItem.getName();
 
						String value = fileItem.getString("UTF-8");
 
						System.out.println(name + "=" + value);
					} else {
 
						System.out.println("======="
								+ fileItem.getName().toString().trim());
						// 若果是文件就将文件写到本地磁盘中
				//		File file2 = new File(filePath,fileItem.getName().substring(fileItem.getName().lastIndexOf("\") + 1));  
						File file2 = new File(filePath,fileItem.getName().substring(fileItem.getName().lastIndexOf("/")+1));	
										
					
						fileItem.write(file2);
 
					}
 
				}
 
			} catch (FileUploadException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
 
		} else {
			doGet(request, response);
		}
 
	}


}
