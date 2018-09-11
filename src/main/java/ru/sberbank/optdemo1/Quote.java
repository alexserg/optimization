package ru.sberbank.optdemo1;

import java.io.Serializable;
import java.util.Date;

public class Quote implements Serializable {

    private String ticker;
    private Date date;
    private Double open;
    private Double high;
    private Double low;
    private Double close;
    private Long volume;
    private Double waprice;

    private Double maxInMonth;
    private Double maxInYear;

    public Quote(String ticker, Date date, Double open, Double high, Double low, Double close, Long volume, Double waprice) {
        this.ticker = ticker;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.waprice = waprice;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Long getVolume() {
        return volume;
    }

    public void setVolume(Long volume) {
        this.volume = volume;
    }

    public Double getWaprice() {
        return waprice;
    }

    public void setWaprice(Double waprice) {
        this.waprice = waprice;
    }

    public Double getMaxInMonth() {
        return maxInMonth;
    }

    public Quote setMaxInMonth(Double maxInMonth) {
        this.maxInMonth = maxInMonth;
        return this;
    }

    public Double getMaxInYear() {
        return maxInYear;
    }

    public Quote setMaxInYear(Double maxInYear) {
        this.maxInYear = maxInYear;
        return this;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "ticker='" + ticker + '\'' +
                ", date=" + date +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", waprice=" + waprice +
                '}';
    }
}
