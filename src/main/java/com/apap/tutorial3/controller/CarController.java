package com.apap.tutorial3.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required= true) String id,
			@RequestParam(value = "brand", required= true) String brand,
			@RequestParam(value = "type", required= true) String type,
			@RequestParam(value = "price", required= true) Long price,
			@RequestParam(value = "amount", required= true) Integer amount) {
	CarModel car = new CarModel(id, brand, type, price, amount);
	mobilService.addCar(car);
	return "add";
	}
	
	@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		model.addAttribute("car", archive);
		return "view-car";
	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List<CarModel> archive = mobilService.getCarList();
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
	@RequestMapping(value = {"/car/view", "/car/view/{id}"})
	public String viewCar (@PathVariable Optional<String> id, Model model) {
		String kosong = "ID kosong!";
		String notFound = "ID tidak ditemukan";
		if (id.isPresent()) {
			CarModel archive = mobilService.getCarDetail(id.get());
			if(archive == null) {
				model.addAttribute("msg", notFound);
				return "error-page";}
			model.addAttribute("car", archive);
			return "view-car";
		}
		else {
			model.addAttribute("msg", kosong);
			return "error-page";
		}
	
	}
	
	@RequestMapping(value = "/car/update/{id}/amount/{amount}")
	public String updateAmount (@PathVariable("id") String id, @PathVariable("amount") Integer amount, Model model) {
		mobilService.getCarDetail(id).setAmount(amount);
		String msg = "Data berhasil diubah! amount dari id " + id + " menjadi " + mobilService.getCarDetail(id).getAmount();
		CarModel archive = mobilService.getCarDetail(id);
		model.addAttribute("msg", msg);
		model.addAttribute("car", archive);
		return "update";
	}
	
	@RequestMapping(value = "/car/delete/{id}")
	public String deleteCar (@PathVariable Optional<String> id, Model model) {
		for(Iterator<CarModel> iter = mobilService.getCarList().listIterator(); iter.hasNext(); ) {
			CarModel car =  iter.next();
			if(car.getId().equalsIgnoreCase(id.get())) {
				iter.remove();
			}
		}
		return "delete";
		
	}
	
}
