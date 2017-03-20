package cn.jon.shop;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ShopServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet");
		response.setContentType("text/json;charset=utf-8");
		//得到所有商品的信息
		List<ShopInfo> list=getAllShops(request);
		
		//转换json字符串
		String json=new Gson().toJson(list);
		System.out.println(json);

		//将数据写向客户端
		PrintWriter out = response.getWriter();
		out.write(json);
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}
	
	
	private List<ShopInfo> getAllShops(HttpServletRequest request)
	{
		List<ShopInfo> list=new ArrayList<ShopInfo>();
		
		//得到Images文件夹的真实路径
		String imagesPath=getServletContext().getRealPath("/images");
		//创建images文件夹File对象
		File file=new File(imagesPath);
	    File[] files=file.listFiles();
	    
	    for (int i = 0; i < files.length; i++) {
	    	//得到商品的相关信息
	    	int id=i+1;
	    	//图片名称
	    	String imageName=files[i].getName();
	    	//商品名称
	    	String name=imageName.substring(0,imageName.lastIndexOf("."))+"的商品名称";
	    	//图片路径
	    	String imagePath="http://"+request.getLocalAddr()+":"+request.getLocalPort()
	    	+"/"+request.getContextPath()+"/images/"+imageName;
	    	//图片价格
	    	float price=new Random().nextInt(20)+20;//[20,40]
	    	//封装成对象
	    	ShopInfo info=new ShopInfo(id,name,price,imagePath);
	    	//添加到集合中
	    	list.add(info);
		}//for
	    	
		
		return list;
		
	}

}
