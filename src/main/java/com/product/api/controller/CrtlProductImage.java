package com.product.api.controller;

import com.product.api.service.SvcProductImp;

@RestController
@RequestMapping("product/{id}/image")
public class CtrlProductImage{

    @Autowired
    SvcProductImage svc;

    @GetMapping
    public ResponsiveEntity<List>
}