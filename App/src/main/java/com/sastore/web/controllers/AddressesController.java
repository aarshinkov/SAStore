package com.sastore.web.controllers;

import com.sastore.web.base.Base;
import com.sastore.web.entities.AddressEntity;
import com.sastore.web.models.addresses.AddressCreateModel;
import com.sastore.web.models.addresses.AddressEditModel;
import com.sastore.web.services.AddressService;
import com.sastore.web.services.OrderService;
import com.sastore.web.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Controller
public class AddressesController extends Base {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private AddressService addressService;

  @Autowired
  private OrderService orderService;

  @GetMapping("/profile/addresses")
  public String getUserAddresses(HttpServletRequest request, Model model) {

    final String userId = getLoggedUserId(request);

    List<AddressEntity> addresses = addressService.getUserAddresses(userId);
    model.addAttribute("addresses", addresses);

    Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
    model.addAttribute("finishedOrders", finishedOrders);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.addresses", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "addresses");

    return "profile/addresses/addresses";
  }

  @GetMapping("/profile/addresses/new")
  public String prepareNewAddress(HttpServletRequest request, Model model) {

    AddressCreateModel acm = new AddressCreateModel();
    acm.setUserId(getLoggedUserId(request));
    acm.setIsMain(false);
    model.addAttribute("acm", acm);

    Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
    model.addAttribute("finishedOrders", finishedOrders);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.addresses", null, LocaleContextHolder.getLocale()), "/profile/addresses"));
    breadcrumbs.add(new Breadcrumb(getMessage("profile.addresses.new.title", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "addresses");

    return "profile/addresses/new";
  }

  @PostMapping("/profile/addresses/new")
  public String createAddress(@ModelAttribute("acm") @Valid AddressCreateModel acm, BindingResult bindingResult, HttpServletRequest request, Model model) {

    log.debug(acm.toString());

    if (bindingResult.hasErrors()) {

      Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
      model.addAttribute("finishedOrders", finishedOrders);

      List<Breadcrumb> breadcrumbs = new ArrayList<>();
      breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
      breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
      breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.addresses", null, LocaleContextHolder.getLocale()), "/profile/addresses"));
      breadcrumbs.add(new Breadcrumb(getMessage("profile.addresses.new.title", null, LocaleContextHolder.getLocale()), null));
      model.addAttribute("breadcrumbs", breadcrumbs);

      model.addAttribute("globalMenu", "profile");
      model.addAttribute("submenu", "addresses");

      return "profile/addresses/new";
    }

    addressService.createAddress(acm);

    return "redirect:/profile/addresses";
  }

  @GetMapping("/profile/addresses/edit")
  public String prepareEditAddress(@RequestParam(name = "id") String addressId, HttpServletRequest request, Model model) {

    AddressEntity address = addressService.getUserAddress(addressId);

    AddressEditModel aem = new AddressEditModel();
    aem.setAddressId(address.getAddressId());
    aem.setPersonName(address.getPersonName());
    aem.setPhone(address.getPhone());
    aem.setCountry(address.getCountry());
    aem.setPostCode(address.getPostCode());
    aem.setProvince(address.getProvince());
    aem.setCity(address.getCity());
    aem.setDistrict(address.getDistrict());
    aem.setStreet(address.getStreet());
    aem.setStreetNo(address.getStreetNo());
    aem.setEntrance(address.getEntrance());
    aem.setFloor(address.getFloor());
    aem.setApartmentNo(address.getApartmentNo());
    aem.setUserId(getLoggedUserId(request));
    aem.setIsMain(address.getIsMain());
    model.addAttribute("aem", aem);

    Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
    model.addAttribute("finishedOrders", finishedOrders);

    List<Breadcrumb> breadcrumbs = new ArrayList<>();
    breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
    breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.addresses", null, LocaleContextHolder.getLocale()), "/profile/addresses"));
    breadcrumbs.add(new Breadcrumb(getMessage("profile.addresses.edit.title", null, LocaleContextHolder.getLocale()), null));
    model.addAttribute("breadcrumbs", breadcrumbs);

    model.addAttribute("globalMenu", "profile");
    model.addAttribute("submenu", "addresses");

    return "profile/addresses/edit";
  }

  @PostMapping("/profile/addresses/edit")
  public String editAddress(@ModelAttribute("aem") @Valid AddressEditModel aem, BindingResult bindingResult, HttpServletRequest request, Model model) {

    if (bindingResult.hasErrors()) {

      Long finishedOrders = orderService.getUserFinishedOrdersCount(getLoggedUserId(request));
      model.addAttribute("finishedOrders", finishedOrders);

      List<Breadcrumb> breadcrumbs = new ArrayList<>();
      breadcrumbs.add(new Breadcrumb(getMessage("nav.home", null, LocaleContextHolder.getLocale()), "/"));
      breadcrumbs.add(new Breadcrumb(getMessage("nav.profile", null, LocaleContextHolder.getLocale()), "/profile"));
      breadcrumbs.add(new Breadcrumb(getMessage("nav.profile.addresses", null, LocaleContextHolder.getLocale()), "/profile/addresses"));
      breadcrumbs.add(new Breadcrumb(getMessage("profile.addresses.edit.title", null, LocaleContextHolder.getLocale()), null));
      model.addAttribute("breadcrumbs", breadcrumbs);

      model.addAttribute("globalMenu", "profile");
      model.addAttribute("submenu", "addresses");

      return "profile/addresses/edit";
    }

    try {
      addressService.editAddress(aem, request);
    } catch (Exception e) {
    }

    return "redirect:/profile/addresses";
  }

  @PostMapping("/profile/addresses/delete")
  public String deleteAddress(@RequestParam(name = "addressId") String addressId) {

    log.debug("Deleting address with ID: " + addressId);

    addressService.deleteAddress(addressId);

    return "redirect:/profile/addresses";
  }
}
