package com.model2.mvc.web.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

@Controller
@RequestMapping("/purchase/*")
public class PurchaseController {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	//@RequestMapping("/addPurchaseView.do")
	@RequestMapping(value="addPurchase", method=RequestMethod.GET)
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo, @RequestParam("userId") String userId, Model model) throws Exception {
		
		System.out.println("/addPurchaseView.do");
		
		System.out.println(prodNo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		Product product = productService.getProduct(prodNo);
		User user = userService.getUser(userId);
		
		Purchase purchase = new Purchase();
		
		purchase.setPurchaseProd(product);
		purchase.setBuyer(user);
		
		modelAndView.setViewName("/purchase/addPurchaseView.jsp");
		modelAndView.addObject("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping(value="addPurchase", method=RequestMethod.POST)
	public ModelAndView addPurchase(@ModelAttribute("purchase") Purchase purchase) throws Exception {
		
		System.out.println(purchase);
		
		purchaseService.addPurchase(purchase);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName("/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	//@RequestMapping("/getPurchase.do")
	@RequestMapping(value="getPurchase", method=RequestMethod.GET)
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo, Model model) throws Exception{
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/getPurchase.jsp");
		
		return modelAndView;
		
	}
	
	//@RequestMapping("/listPurchase.do")
	@RequestMapping(value="listPurchase")
	public ModelAndView listPurchase(@ModelAttribute("search") Search search, HttpServletRequest request, Model model) throws Exception{
		
		System.out.println("/listPurchase.do");
		
		if(search.getCurrentPage()==0) {
			search.setCurrentPage(1);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		
		search.setPageSize(3);
		
		HttpSession session=request.getSession(true);
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		Map <String,Object> map = purchaseService.getPurchaseList(search, buyerId);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(),pageUnit,pageSize);
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage",resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updatePurchaseView")
	public ModelAndView updatePurchaseView(@RequestParam("tranNo") int tranNo, Model model ) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		System.out.println(purchase);
		
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/updatePurchaseView.jsp");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updatePurchase")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase, @RequestParam("buyerId") String buyerId, @RequestParam("tranNo") int tranNo, Model model) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		System.out.println("/updatePurchase.do");
		User user = new User();
		user.setUserId(buyerId);
		purchase.setBuyer(user);
		Product product = purchaseService.getPurchase(tranNo).getPurchaseProd();
		
		purchase.setPurchaseProd(product);
		
		purchaseService.updatePurcahse(purchase);
		
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/purchase/updatePurchase.jsp");
		
		return modelAndView;
		
	}
	
	@RequestMapping(value="updateTranCode")
	public ModelAndView updateTranCode(@ModelAttribute("purchase") Purchase purchase, @RequestParam("tranCode") String tranCode, Model model) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("listPurchase");
		
		return modelAndView;
	}
	
	@RequestMapping(value="updateTranCodeByProd")
	public ModelAndView updateTranCodeByProd(@ModelAttribute("product") Product product, @ModelAttribute("purchase") Purchase purchase, @RequestParam("prodNo") int prodNo, @RequestParam("tranCode") String tranCode) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		product.setProdNo(prodNo);
		
		purchase.setPurchaseProd(product);
		purchase.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchase);
		
		modelAndView.addObject("purchase", purchase);
		modelAndView.setViewName("/product/listProduct?menu=manage");
		
		return modelAndView;
	}
}
