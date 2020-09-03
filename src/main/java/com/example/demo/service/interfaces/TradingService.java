package com.example.demo.service.interfaces;

import com.example.demo.model.CompanyEntity;
import com.example.demo.model.CompanyTradePrice;

import java.time.LocalTime;
import java.util.List;

public interface TradingService {
    List<CompanyEntity> findAll();

    CompanyEntity findBySymbol(String symbol);

    List<CompanyTradePrice> getCompanyPrices(String symbol, LocalTime timeFrom, LocalTime timeTo);
}
