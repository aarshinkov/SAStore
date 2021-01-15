package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class OrdersController extends Base {

    private final Logger log = LoggerFactory.getLogger(getClass());

}
