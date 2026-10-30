(function () {
  "use strict";

  // 0. Parse URL Parameters for Dynamic Product Info
  var urlParams = new URLSearchParams(window.location.search);
  var dynamicTitle = urlParams.get('title');
  var dynamicPrice = urlParams.get('price');
  var dynamicImage = urlParams.get('image');

  if (dynamicTitle && dynamicPrice && dynamicImage) {
      var nameEl = document.querySelector('.product-name');
      var priceEl = document.querySelector('.product-price-large');
      var mainImgEl = document.querySelector('.product-main-img img');
      var breadcrumbSpan = document.querySelector('.breadcrumb span');
      var firstThumb = document.querySelector('.product-thumbnails img');

      if (nameEl) nameEl.innerText = dynamicTitle;
      if (priceEl) priceEl.innerText = '$' + dynamicPrice;
      if (mainImgEl) mainImgEl.src = '../' + dynamicImage;
      if (breadcrumbSpan) breadcrumbSpan.innerText = dynamicTitle;
      if (firstThumb) firstThumb.src = '../' + dynamicImage;
  }

  // 1. Thumbnail Swapping
  var thumbnails = document.querySelectorAll('.product-thumbnails img');
  var mainImage = document.querySelector('.product-main-img img');
  if (thumbnails.length > 0 && mainImage) {
      thumbnails.forEach(function(thumb) {
          thumb.style.cursor = 'pointer';
          thumb.addEventListener('click', function() {
              mainImage.src = this.src;
          });
      });
  }

  // 1. Quantity Selector
  var qtyInput = document.getElementById('qty-product');
  var qtyBtns = document.querySelectorAll('.quantity-selector .qty-btn');

  if (qtyInput && qtyBtns.length === 2) {
      var minusBtn = qtyBtns[0];
      var plusBtn = qtyBtns[1];

      minusBtn.addEventListener('click', function() {
          var currentVal = parseInt(qtyInput.value) || 1;
          if (currentVal > 1) {
              qtyInput.value = currentVal - 1;
          }
      });

      plusBtn.addEventListener('click', function() {
          var currentVal = parseInt(qtyInput.value) || 1;
          qtyInput.value = currentVal + 1;
      });
  }

  // 2. Size Selection
  var sizeBoxes = document.querySelectorAll('.sizes .size-box');
  sizeBoxes.forEach(function(box) {
      box.addEventListener('click', function() {
          sizeBoxes.forEach(function(b) { b.classList.remove('active'); });
          this.classList.add('active');
          // In real app, you would save this selected size to variable
      });
  });

  // 3. Color Selection
  var colorCircles = document.querySelectorAll('.colors .color-circle');
  colorCircles.forEach(function(circle) {
      circle.addEventListener('click', function() {
          colorCircles.forEach(function(c) { c.style.border = ''; });
          // Add border to selected color to highlight it
          this.style.border = '2px solid black';
      });
  });

  // 4. Wishlist Logic
  var wishlistBtn = document.querySelector('.wishlist-btn');
  if (wishlistBtn) {
      wishlistBtn.addEventListener('click', function(e) {
          e.preventDefault();
          var titleEl = document.querySelector('.product-name');
          var priceEl = document.querySelector('.product-price-large');
          var imgEl = document.querySelector('.product-main-img img');

          if (titleEl && priceEl && imgEl) {
              var title = titleEl.innerText.trim();
              var priceText = priceEl.innerText.trim().replace('$', '');
              var price = parseFloat(priceText);
              
              var imgSrcRaw = imgEl.getAttribute('src');
              var cleanImgSrc = imgSrcRaw.replace(/^(\.\.\/|\.\/)/, '');

              var wishlist = window.getWishlist();
              var existingIndex = wishlist.findIndex(function(item) { return item.title === title; });
              
              var icon = this.querySelector('i');
              if (existingIndex > -1) {
                  wishlist.splice(existingIndex, 1);
                  icon.classList.remove('fa-solid');
                  icon.classList.add('fa-regular');
                  icon.style.color = '';
              } else {
                  wishlist.push({ title: title, price: price, image: cleanImgSrc });
                  icon.classList.remove('fa-regular');
                  icon.classList.add('fa-solid');
                  icon.style.color = 'var(--primary-color)';
              }
              window.saveWishlist(wishlist);
              window.updateBadges();
          }
      });
  }

  // 5. Buy Now / Add to Cart Logic
  var buyNowBtn = document.querySelector('.buy-now-btn');
  if (buyNowBtn) {
      buyNowBtn.addEventListener('click', function(e) {
          e.preventDefault();
          var titleEl = document.querySelector('.product-name');
          var priceEl = document.querySelector('.product-price-large');
          var imgEl = document.querySelector('.product-main-img img');
          var qty = parseInt(qtyInput ? qtyInput.value : 1) || 1;

          if (titleEl && priceEl && imgEl) {
              var title = titleEl.innerText.trim();
              var priceText = priceEl.innerText.trim().replace('$', '');
              var price = parseFloat(priceText);
              
              var imgSrcRaw = imgEl.getAttribute('src');
              var cleanImgSrc = imgSrcRaw.replace(/^(\.\.\/|\.\/)/, '');

              var cart = window.getCart();
              var existingItem = cart.find(function(item) { return item.title === title; });
              if (existingItem) {
                  existingItem.quantity += qty;
              } else {
                  cart.push({ title: title, price: price, image: cleanImgSrc, quantity: qty });
              }
              window.saveCart(cart);
              window.updateBadges();
              
              // Redirect to cart
              window.location.href = 'cart.html';
          }
      });
  }

  // Set initial wishlist icon state on load
  function checkInitialWishlist() {
      var titleEl = document.querySelector('.product-name');
      if (titleEl && wishlistBtn) {
          var title = titleEl.innerText.trim();
          var wishlist = window.getWishlist();
          var exists = wishlist.some(function(item) { return item.title === title; });
          if (exists) {
              var icon = wishlistBtn.querySelector('i');
              icon.classList.remove('fa-regular');
              icon.classList.add('fa-solid');
              icon.style.color = 'var(--primary-color)';
          }
      }
  }
  checkInitialWishlist();

})();
