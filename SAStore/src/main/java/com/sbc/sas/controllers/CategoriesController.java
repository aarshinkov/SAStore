package com.sbc.sas.controllers;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/categories")
public class CategoriesController
{
  private Logger log = LoggerFactory.getLogger(this.getClass());

  @GetMapping("/{category}")
  public String category(@PathVariable("category") String category, Model model)
  {
    model.addAttribute("categoryTitle", getCategoryTitle(category));
    model.addAttribute("category", category);
    return "categories/mainCategory";
  }

  private String getCategoryTitle(String category)
  {
    StringBuilder builder = new StringBuilder();

    String[] split = category.split("");

    split[0] = split[0].toUpperCase();

    for (String symbol : split)
    {
      builder.append(symbol);
    }

    String result = String.valueOf(builder);

    log.debug("Category title: " + result);

    return result;
  }
}
