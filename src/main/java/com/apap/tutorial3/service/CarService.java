package com.apap.tutorial3.service;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial3.model.CarModel;

public interface CarService {
	void addCar(CarModel car);
	List<CarModel> getCarList();
	CarModel getCarDetail(String id);
}
