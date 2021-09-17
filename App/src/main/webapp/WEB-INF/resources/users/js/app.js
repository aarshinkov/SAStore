// Home Slider
App.homeSlider = el => {
  const slider = new Swiper(el, {
    loop: true,
    pagination: {
      el: '.swiper-pagination',
      clickable: true
    },
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    autoplay: {
      delay: 3000,
      disableOnInteraction: false
    },
    on: {
      init: () => {
        setTimeout(() => {
          for (const elem of document.querySelectorAll(`${el} .swiper-slide-active .animated`)) {
            elem.classList.add('visible', elem.getAttribute('data-animate'))
          }
        }, 100)
      }
    }
  })
  slider.on('slideChange', () => {
    for (const elem of document.querySelectorAll(`${el} .animated`)) {
      elem.classList.remove('visible', elem.getAttribute('data-animate'))
    }
    for (const elem of slider.slides[slider.activeIndex].querySelectorAll('.animated')) {
      elem.classList.add('visible', elem.getAttribute('data-animate'))
    }
  })
}

// Deal Slider
App.dealSlider = el => {
  new Swiper(el, {
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    slidesPerView: 1,
    breakpoints: {
      576: {// xs
        slidesPerView: 2,
      }
    }
  })
}

// Deal Slider 2
App.dealSlider2 = el => {
  new Swiper(el, {
    navigation: {
      nextEl: '.swiper-button-next',
      prevEl: '.swiper-button-prev',
    },
    slidesPerView: 1,
    breakpoints: {
      576: {// sm
        slidesPerView: 2,
        spaceBetween: 15,
      },
      768: {// md
        slidesPerView: 3,
        spaceBetween: 15,
      },
      991: {// lg
        slidesPerView: 4,
        spaceBetween: 15,
      }
    }
  })
}

// Brand Slider
App.brandSlider = el => {
  new Swiper(el, {
    pagination: {
      el: '.swiper-pagination',
      clickable: true
    },
    autoplay: {
      delay: 3000,
      disableOnInteraction: false
    },
    slidesPerView: 2,
    spaceBetween: 8,
    breakpoints: {
      767: {
        // md
        slidesPerView: 3,
        spaceBetween: 15,
      },
      991: {
        // lg
        slidesPerView: 4,
        spaceBetween: 15,
      },
      1199: {
        // xl
        slidesPerView: 5,
        spaceBetween: 15,
      }
    }
  })
}

$(() => {

  // Init template
  App.init()

  // Init apps

  // Disable dropdown dynamic positioning, so that it's easy to add animation
  $('.dropdown-toggle').dropdown({
    display: 'static'
  })

  // Run tooltip & popover
  $('[data-toggle="tooltip"]').tooltip()
  $('[data-toggle="popover"]').popover()

  // Focus to email input when signin modal shown
  $('#signinModal').on('shown.bs.modal', () => {
    document.querySelector('.showSigninContent').click()
    signInEmailInput.focus()
  })

  // Metis Menu
  const menu = $('#menu').metisMenu()
  menu.on('show.metisMenu', () => {
    $('.no-sub').removeClass('mm-active')
  })

  // Close menu on large devices
  App.resize(function () {
    if (App.lgUp()) {
      $('#menuModal').modal('hide')
    }
  })()
})