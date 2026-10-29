package org.example.orgflow.controllers.charts;

import org.example.orgflow.services.charts.TableCountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/table-count-column-chart")
public class TableCountsController {

    @Autowired
    private TableCountsService tableCountColumnChartService;

    @GetMapping("/counts")
    public Map<String, Long> getCounts() {
        return tableCountColumnChartService.getCounts();
    }
}
