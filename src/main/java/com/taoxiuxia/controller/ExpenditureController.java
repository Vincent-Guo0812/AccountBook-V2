package com.taoxiuxia.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taoxiuxia.model.Expenditure;
import com.taoxiuxia.model.Item;
import com.taoxiuxia.service.IExpenditureService;
import com.taoxiuxia.service.IItemService;
import com.taoxiuxia.util.NumberFormat;

@Controller
@RequestMapping("/expenditureController")
public class ExpenditureController {

	private IExpenditureService expenditureService;
	private IItemService itemService;

	public IExpenditureService getExpenditureService() {
		return expenditureService;
	}

	@Autowired
	public void setExpenditureService(IExpenditureService expenditureService) {
		this.expenditureService = expenditureService;
	}

	public IItemService getItemService() {
		return itemService;
	}

	@Autowired
	public void setItemService(IItemService itemService) {
		this.itemService = itemService;
	}

	/**
	 * expenditure页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/showExpenditure")
	public String showExpenditures(Model model) {

		// Expenditure
		List<Expenditure> expenditures = expenditureService.loadExpenditures();

		float totalExpenditure = 0;
		float averageExpenditure = 0;
		for (Expenditure expenditure : expenditures) {
			totalExpenditure += expenditure.getMoney();
		}
		Calendar ca = Calendar.getInstance();
		averageExpenditure = NumberFormat.to2Decimals(totalExpenditure / ca.get(Calendar.DAY_OF_MONTH) * 100);
		model.addAttribute("expenditures", expenditures);
		model.addAttribute("totalExpenditure", totalExpenditure);
		model.addAttribute("averageExpenditure", averageExpenditure);

		// Expenditure列表项
		List<Item> items = itemService.loadExpenditureItems(2); // 目前只有用户2
		model.addAttribute("items", items);

		return "pages/expenditure";
	}

	/**
	 * 增加Expenditures
	 * 
	 * @param item
	 * @param money
	 * @param remark
	 */
	@RequestMapping("/addExpenditure")
	public void addExpenditures(int item, float money, String remark) {
		expenditureService.addExpenditure(item, money, remark);
	}

	/**
	 * 修改Expenditures
	 * 
	 * @param ExpendituresId
	 * @param money
	 * @param itemId
	 * @param remark
	 */
	@RequestMapping("/changeExpenditure")
	public void changeExpenditures(int expenditureId, float money, int itemId, String remark) {
		expenditureService.changeExpenditure(expenditureId, money, itemId, remark);
	}

	/**
	 * 删除Expenditures
	 * 
	 * @param ExpenditureId
	 * @param itemId
	 */
	@RequestMapping("/deleExpenditure")
	public void deleExpenditure(int expenditureId, int itemId) {
		expenditureService.deleExpenditure(expenditureId, itemId);
	}
}