package SPA.dev.Stock.controller;

import SPA.dev.Stock.service.RecyclageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/perte")
@RequiredArgsConstructor
public class PerteController {
    private final RecyclageService recyclageService;


}
