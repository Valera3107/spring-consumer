package com.example.demo.service.impl;

import com.example.demo.config.IextradingConfig;
import com.example.demo.model.CompanyEntity;
import com.example.demo.model.CompanyTradePrice;
import com.example.demo.repo.CompanyRepo;
import com.example.demo.service.interfaces.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.stocks.LargestTrade;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.rest.request.stocks.LargestTradeRequestBuilder;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradingServiceImpl implements TradingService {
    private final CompanyRepo companyRepo;
    private final IextradingConfig iextradingConfig;

    @Override
    public List<CompanyEntity> findAll() {
        return companyRepo.findAll();
    }

    @Override
    public CompanyEntity findBySymbol(String symbol) {
        return companyRepo.findCompanyEntityByCompanySymbol(symbol);
    }

    @Override
    public List<CompanyTradePrice> getCompanyPrices(String symbol, LocalTime timeFrom, LocalTime timeTo) {
        IEXCloudClient cloudClient = iextradingConfig.getConnection();
        List<LargestTrade> largestTrades = cloudClient.executeRequest(new LargestTradeRequestBuilder()
                .withSymbol(symbol)
                .build());

        return largestTrades.stream().filter(e -> filtrateByTime(e, timeFrom, timeTo)).map(convertToCompanyTradePrice()).collect(Collectors.toList());
    }

    private boolean filtrateByTime(LargestTrade trade, LocalTime timeFrom, LocalTime timeTo) {
        if (Objects.nonNull(timeFrom) && Objects.nonNull(timeTo)) {
            return trade.getTimeLabel().isAfter(timeFrom) && trade.getTimeLabel().isBefore(timeTo);
        }

        if (Objects.nonNull(timeFrom)) {
            return trade.getTimeLabel().isAfter(timeFrom);
        }

        if (Objects.nonNull(timeTo)) {
            return trade.getTimeLabel().isBefore(timeTo);
        }

        return true;
    }

    private Function<LargestTrade, CompanyTradePrice> convertToCompanyTradePrice() {
        return t -> CompanyTradePrice.builder().price(t.getPrice()).time(t.getTimeLabel()).build();
    }

}
