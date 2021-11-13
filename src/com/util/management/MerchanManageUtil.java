package com.util.management;

import java.util.ArrayList;

import com.vo.MPicture;
import com.vo.Merchandise;

public interface MerchanManageUtil {

	
	public boolean dropMerchan(int m_id);
	
	
	public boolean republishMerchan(int m_id);
	
	
	public Merchandise addStock(int m_id,int num);
	
	
	public Merchandise addGenre2(int m_id,String genre2);
	
	
	public Merchandise addMerchan(Merchandise m,ArrayList<MPicture> am,int price,int num,String genre1);
}