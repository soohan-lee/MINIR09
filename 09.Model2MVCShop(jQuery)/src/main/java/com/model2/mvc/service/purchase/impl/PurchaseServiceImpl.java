package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.domain.*;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDAO;
	public void setPurchaseDao(PurchaseDao purchaseDAO) {
		this.purchaseDAO=purchaseDAO;
	}
	public PurchaseServiceImpl() {
		
		System.out.println("purServiceImpl::"+getClass());
	}
	
	public void addPurchase(Purchase purchase) throws Exception {
		
		purchaseDAO.insertPurchase(purchase);
		
	}
	
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception{
		
		List<Purchase> list = purchaseDAO.getPurchaseList(search,buyerId);
		int totalCount=purchaseDAO.getTotalCount(buyerId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public Purchase getPurchase(int tranNo) throws Exception{
		
		return purchaseDAO.findPurchase(tranNo);
	}
	
	public void updatePurcahse(Purchase purchase) throws Exception {
		
		purchaseDAO.updatePurchase(purchase);
	}
	public void updateTranCode(Purchase purchase) throws Exception {
		
		purchaseDAO.updateTranCode(purchase);
	}

}
