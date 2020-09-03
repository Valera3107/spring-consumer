package com.example.demo.controller;

import com.example.demo.controller.dto.CompanyDto;
import com.example.demo.controller.dto.PriceDto;
import com.example.demo.model.CompanyEntity;
import com.example.demo.model.CompanyTradePrice;
import com.example.demo.service.interfaces.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class MainController {

    private final TradingService tradingService;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> list() {
        return ResponseEntity.ok(tradingService.findAll().stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<CompanyDto> getBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(convertToDto(tradingService.findBySymbol(symbol)));
    }

    @GetMapping("/price/{symbol}")
    public ResponseEntity<List<PriceDto>> getPriceListBySymbol(@PathVariable String symbol,
                                                               @RequestParam(required = false) LocalTime timeFrom,
                                                               @RequestParam(required = false) LocalTime timeTo) {
        return ResponseEntity.ok(tradingService.getCompanyPrices(symbol, timeFrom, timeTo).stream().map(this::convertToPriceDto).collect(Collectors.toList()));
    }

    private CompanyDto convertToDto(CompanyEntity companyEntity) {
        return CompanyDto.builder()
                .name(companyEntity.getCompanyName())
                .symbol(companyEntity.getCompanySymbol())
                .url(companyEntity.getLogoUrl())
                .build();
    }


    public PriceDto convertToPriceDto(CompanyTradePrice companyTradePrice) {
        return PriceDto.builder()
                .price(companyTradePrice.getPrice())
                .time(companyTradePrice.getTime())
                .build();
    }
}
