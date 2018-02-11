package com.paulz.hhb.model.wrapper;

import com.paulz.hhb.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderWraper implements BeanWraper<Order>{
	
	/**
	 * 
	 */
    public List<Order> list; //  当前页面所有的beans  order

    public int page_count=Integer.MAX_VALUE;//页码总数


    @Override
    public int getItemsCount(){
    	return list==null?0:list.size();
    }
    
    @Override
    public List<Order> getItems(){
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