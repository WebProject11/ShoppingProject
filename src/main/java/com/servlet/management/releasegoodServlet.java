package com.servlet.management;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.util.management.MerchanManage;
import com.vo.MPicture;
import com.vo.Merchandise;



@WebServlet("/releasegoodServlet")
public class releasegoodServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");	
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String goodname = null;
		Double goodprice = 0.0 ;
		int goodnum = 0 ;
		String goodinf = null;
		String fileName = null;
		HttpSession session=request.getSession();	
		session.setAttribute("msg1", "false");
		//�ϴ�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		try {
			if(isMultipart) {//�ж�ǰ̨��form�Ƿ���multipart������
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iter = items.iterator();
				ArrayList<MPicture> am = new ArrayList<MPicture>();
				while(iter.hasNext()) {
					FileItem item = iter.next();
					String itemName = item.getFieldName();
					//�ж�ǰ̨�ֶ�����ͨform�����ֶλ��ǣ��ļ��ֶ�
					if(item.isFormField()) {
						if(itemName.equals("goodname") && !item.getString().equals("")) {
							goodname = item.getString("utf-8");
						}else if(itemName.equals("goodprice") && !item.getString().equals("")){
								goodprice = Double.parseDouble(item.getString());
						}else if(itemName.equals("goodinf") && !item.getString().equals("")) {
							goodinf = item.getString("utf-8");
						}else if(itemName.equals("goodnum") && !item.getString().equals("")) {
							if(!item.getString().equals(""))
								goodnum = Integer.parseInt(item.getString());
						}
						}else {
							//�ļ��ϴ�
							//�ļ��� getFieldName�ǻ�ȡ��ͨ�����ֶε�Nameֵ
							//getName������ȡ �ļ���
							fileName = (new File(item.getName())).getName();
							MPicture mp = new MPicture(fileName);	
							am.add(mp);
							//��ȡ�ļ����ݲ��ϴ�
							//�����ļ�·����ָ���ϴ���λ��
							//String path = System.getProperty("user.dir").replace("bin", "webapps") + "wp/imgs";
							//System.out.println(path);
							String path = request.getSession().getServletContext().getRealPath("imgs");
							File file = new File(path,fileName);
							item.write(file);
				}
				}
				if(goodname == null || goodinf == null || goodprice <= 0|| goodnum <= 0 ) {
					response.sendRedirect("management/seller_releasegood/releasegood.jsp");
				}
				else{											   //********��Ҫ��д
			Merchandise m = new Merchandise(goodname,goodinf);   //����id����Ʒ���ƣ���Ʒ��Ϣ
			MerchanManage mm = new MerchanManage();					   //��ȡ��Ʒ��Ϣ����ȡͼƬ��Ϣ������ͼƬֻ��p_ads���ԣ���֪������᲻�����
			//ArrayList<MPicture> am = new ArrayList<MPicture>();
			//MPicture mp = new MPicture(fileName);				   //��������p_ads���Ե�mpicture
			//am.add(mp);
			mm.addMerchan(m, am, goodprice, goodnum);
			session.setAttribute("msg1", "true");
			response.sendRedirect("management/seller_releasegood/releasegood.jsp");}
		}
		}catch (FileUploadException e) {
			e.printStackTrace();
			response.sendRedirect("management/seller_releasegood/releasegood.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("management/seller_releasegood/releasegood.jsp");
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}