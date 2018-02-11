package com.paulz.hhb.model.wrapper;

import com.paulz.hhb.model.Salesman;
import com.paulz.hhb.model.SalesmanDetail;

import java.util.ArrayList;
import java.util.List;

public class SalesmanWraper implements BeanWraper<Salesman>{
	
	/**
	 * 
	 */
    public List<Salesman> list; //  当前页面所有的beans  order


    public int page_count=Integer.MAX_VALUE;//页码总数

    public int total;




    @Override
    public int getItemsCount(){
    	return list==null?0:list.size();
    }
    
    @Override
    public List<Salesman> getItems(){
    	if(list==null){
            list=new ArrayList<>();
    	}
    	return list;
    }
    
    @Override
    public int getTotalPage(){
    	return page_count;
    }
    
}
