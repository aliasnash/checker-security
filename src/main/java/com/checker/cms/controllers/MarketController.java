package com.checker.cms.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.checker.core.dao.service.CityService;
import com.checker.core.dao.service.MarketPointService;
import com.checker.core.dao.service.MarketService;
import com.checker.core.entity.City;
import com.checker.core.entity.Market;
import com.checker.core.entity.MarketPoint;
import com.checker.core.utilz.Transformer;

@Slf4j
@Controller
@RequestMapping("market")
public class MarketController {
    
    @Resource
    private Transformer        transformer;
    @Resource
    private CityService        cityService;
    @Resource
    private MarketService      marketService;
    @Resource
    private MarketPointService marketPointService;
    
    private Integer            idCompany = 1;
    
    @RequestMapping("list")
    public ModelAndView marketList() {
        log.info("#MarketList method(idCompany:" + idCompany + ")#");
        List<Market> marketList = marketService.findMarketByIdCompany(idCompany);
        ModelAndView m = new ModelAndView("market");
        m.addObject("pageName", "market");
        m.addObject("marketList", marketList);
        return m;
    }
    
    @RequestMapping("{id}/delete")
    public String marketDelete(@PathVariable("id") Long idMarket) {
        log.info("#MarketDelete method(idCompany:" + idCompany + ",idMarket:" + idMarket + ")#");
        if (idMarket != null && idMarket > 0)
            marketService.deleteMarket(idCompany, idMarket);
        return "redirect:/market/list";
    }
    
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String marketUpdate(@RequestParam("id") Long idMarket, @RequestParam("name") String caption, @RequestParam(value = "owner", required = false) Boolean owner) {
        log.info("#MarketUpdate method(idCompany:" + idCompany + ",idMarket:" + idMarket + ",caption:" + caption + ",owner:" + owner + ")#");
        if (owner == null)
            owner = false;
        if (StringUtils.isNotEmpty(caption)) {
            if (idMarket != null && idMarket > 0) {
                marketService.updateMarket(idCompany, idMarket, caption, owner);
            } else {
                Market market = new Market();
                market.setActive(Boolean.TRUE);
                market.setIdCompany(idCompany);
                market.setCaption(caption);
                market.setOwner(owner);
                market.setDateAdded(DateTime.now());
                marketService.saveMarket(market);
            }
        }
        return "redirect:/market/list";
    }
    
    @RequestMapping("{id}/point/list")
    public ModelAndView marketPointsList(@PathVariable("id") Long idMarket) {
        log.info("#MarketPointsList method(idCompany:" + idCompany + ",idMarket:" + idMarket + ")#");
        List<MarketPoint> pointsList = marketPointService.findMarketPointByIdMarket(idMarket);
        Market market = marketService.findMarketByIdAndIdCompany(idCompany, idMarket);
        Map<String, Collection<City>> cityMap = transformer.doCityTransformer(cityService.findCitiesByIdCompany(idCompany));
        ModelAndView m = new ModelAndView("marketpoint");
        m.addObject("pageName", "marketpoint");
        m.addObject("market", market);
        m.addObject("pointsList", pointsList);
        m.addObject("cityMap", cityMap);
        return m;
    }
    
    @RequestMapping("{idm}/point/{idmp}/delete")
    public String marketPointDelete(@PathVariable("idm") Long idMarket, @PathVariable("idmp") Long idMarketPoint) {
        log.info("#MarketPointDelete method(idCompany:" + idCompany + ",idMarket:" + idMarket + ",idMarketPoint:" + idMarketPoint + ")#");
        if (idMarket != null && idMarket > 0 && idMarketPoint != null && idMarketPoint > 0) {
            marketPointService.deleteMarketPoint(idCompany, idMarket, idMarketPoint);
            return "redirect:/market/{idm}/point/list";
        } else {
            return "redirect:/market/list";
        }
    }
    
    @RequestMapping(value = "{idm}/point/update", method = RequestMethod.POST)
    public String marketUpdate(@PathVariable("idm") Long idMarket, @RequestParam("idcity") Integer idCity, @RequestParam("id") Long idMarketPoint, @RequestParam("name") String description) {
        log.info("#MarketUpdate method(idCompany:" + idCompany + ",idCity:" + idCity + ",idMarketPoint:" + idMarketPoint + ",description:" + description + ")#");
        if (idCity != null && idCity > 0 && StringUtils.isNotEmpty(description)) {
            if (idMarketPoint != null && idMarketPoint > 0) {
                marketPointService.updateMarketPoint(idCompany, idCity, idMarketPoint, description);
            } else {
                MarketPoint marketPoint = new MarketPoint();
                marketPoint.setActive(Boolean.TRUE);
                marketPoint.setIdMarket(idMarket);
                marketPoint.setIdCity(idCity);
                marketPoint.setDescription(description);
                marketPoint.setDateAdded(DateTime.now());
                marketPointService.saveMarketPoint(marketPoint);
            }
        }
        return "redirect:/market/{idm}/point/list";
    }
    
}
