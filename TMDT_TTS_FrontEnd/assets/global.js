(function () {
  "use strict";

  var page = document.body.getAttribute("data-page") || "";

  
  var scriptTag = document.querySelector('script[src*="global.js"]');
  var srcAttr = scriptTag.getAttribute('src');
  var basePath = srcAttr.split('assets/global.js')[0];
  var pagePath = (basePath === '../' || basePath === '..\\') ? '' : 'pages/';

  var headerHTML =
    '\
    <div class="top-banner" role="banner">\
        <div class="container">\
            <div class="top-banner-text">\
                <span>Summer Sale For All Swim Suits And Free Express Delivery - OFF 50%!</span>\
                <a href="#">ShopNow</a>\
            </div>\
            <div class="language-selector">\
                <label for="header-language" class="sr-only">Select language</label>\
                <select id="header-language">\
                    <option value="en">English</option>\
                </select>\
            </div>\
        </div>\
    </div>\
    <header class="header" role="banner">\
        <div class="container">\
            <a href="' + basePath + 'index.html" class="logo" aria-label="Exclusive Home">Exclusive</a>\
            <nav class="nav-links" aria-label="Main navigation">\
                <a href="' + basePath + 'index.html" data-nav="home">Home</a>\
                <a href="' + pagePath + 'contact.html" data-nav="contact">Contact</a>\
                <a href="' + pagePath + 'about.html" data-nav="about">About</a>\
                <a href="' + pagePath + 'signup.html" data-nav="signup">Sign Up</a>\
            </nav>\
            <div class="header-right">\
                <div class="search-box" role="search">\
                    <label for="header-search" class="sr-only">Search for products</label>\
                    <input type="text" id="header-search" placeholder="What are you looking for?">\
                    <i class="fa-solid fa-magnifying-glass" aria-hidden="true"></i>\
                </div>\
                <div class="header-icons">\
                    <a href="' + pagePath + 'wishlist.html" class="icon-badge-wrap" aria-label="Wishlist"><i class="fa-regular fa-heart" aria-hidden="true"></i><span class="badge" id="wishlist-badge" aria-hidden="true">0</span></a>\
                    <a href="' + pagePath + 'cart.html" class="icon-badge-wrap" aria-label="Shopping cart"><i class="fa-solid fa-cart-shopping" aria-hidden="true"></i><span class="badge" id="cart-badge" aria-hidden="true">0</span></a>\
                    <div class="user-dropdown-wrap">\
                        <a href="' + pagePath + 'account.html" aria-label="My Account"><i class="fa-regular fa-user" aria-hidden="true"></i></a>\
                        <div class="user-dropdown">\
                            <a href="' + pagePath + 'account.html"><i class="fa-regular fa-user" aria-hidden="true"></i> Manage My Account</a>\
                            <a href="#"><i class="fa-solid fa-bag-shopping" aria-hidden="true"></i> My Order</a>\
                            <a href="#"><i class="fa-regular fa-circle-xmark" aria-hidden="true"></i> My Cancellations</a>\
                            <a href="#"><i class="fa-regular fa-star" aria-hidden="true"></i> My Reviews</a>\
                            <a href="#" class="logout-btn"><i class="fa-solid fa-arrow-right-from-bracket" aria-hidden="true"></i> Logout</a>\
                        </div>\
                    </div>\
                </div>\
            </div>\
        </div>\
    </header>';

  var footerHTML =
    '\
    <footer class="footer" role="contentinfo">\
        <div class="container footer-content">\
            <div class="footer-col">\
                <div class="footer-logo">Exclusive</div>\
                <h3>Subscribe</h3>\
                <p>Get 10% off your first order</p>\
                <div class="subscribe-input">\
                    <label for="footer-subscribe" class="sr-only">Email address</label>\
                    <input type="email" id="footer-subscribe" placeholder="Enter your email">\
                    <i class="fa-regular fa-paper-plane" aria-hidden="true"></i>\
                </div>\
            </div>\
            <div class="footer-col">\
                <h3>Support</h3>\
                <p>111 Bijoy sarani, Dhaka,<br>DH 1515, Bangladesh.</p>\
                <p>exclusive@gmail.com</p>\
                <p>+88015-88888-9999</p>\
            </div>\
            <div class="footer-col">\
                <h3>Account</h3>\
                <ul>\
                    <li><a href="' + pagePath + 'account.html">My Account</a></li>\
                    <li><a href="' + pagePath + 'login.html">Login / Register</a></li>\
                    <li><a href="' + pagePath + 'cart.html">Cart</a></li>\
                    <li><a href="' + pagePath + 'wishlist.html">Wishlist</a></li>\
                    <li><a href="' + basePath + 'index.html">Shop</a></li>\
                </ul>\
            </div>\
            <div class="footer-col">\
                <h3>Quick Link</h3>\
                <ul>\
                    <li><a href="#">Privacy Policy</a></li>\
                    <li><a href="#">Terms Of Use</a></li>\
                    <li><a href="#">FAQ</a></li>\
                    <li><a href="' + pagePath + 'contact.html">Contact</a></li>\
                </ul>\
            </div>\
            <div class="footer-col">\
                <h3>Download App</h3>\
                <p class="footer-download-text">Save $3 with App New User Only</p>\
                <div class="app-links">\
                    <img src="' + basePath + 'images/Qrcode 1.png" alt="QR Code to download app" style="background:white; padding:2px;" loading="lazy">\
                    <div class="app-stores">\
                        <img src="' + basePath + 'images/png-transparent-google-play-store-logo-google-play-app-store-android-wallets-text-label-logo.png" alt="Get it on Google Play" style="background:black; border:1px solid white;" loading="lazy">\
                        <img src="' + basePath + 'images/download-appstore.png" alt="Download on the App Store" style="background:black; border:1px solid white;" loading="lazy">\
                    </div>\
                </div>\
                <div class="social-links" aria-label="Social media links">\
                    <a href="#" aria-label="Facebook"><i class="fa-brands fa-facebook-f" aria-hidden="true"></i></a>\
                    <a href="#" aria-label="Twitter"><i class="fa-brands fa-twitter" aria-hidden="true"></i></a>\
                    <a href="#" aria-label="Instagram"><i class="fa-brands fa-instagram" aria-hidden="true"></i></a>\
                    <a href="#" aria-label="LinkedIn"><i class="fa-brands fa-linkedin-in" aria-hidden="true"></i></a>\
                </div>\
            </div>\
        </div>\
        <div class="copyright">\
            <p>&copy; Copyright Exclusive 2024. All right reserved</p>\
        </div>\
    </footer>';

  var headerTarget = document.getElementById("site-header");
  if (headerTarget) {
    headerTarget.innerHTML = headerHTML;
    if (page) {
      var link = headerTarget.querySelector('.nav-links a[data-nav="' + page + '"]');
      if (link) {
        link.classList.add("active");
        link.setAttribute("aria-current", "page");
      }
    }
  }

  var footerTarget = document.getElementById("site-footer");
  if (footerTarget) {
    footerTarget.innerHTML = footerHTML;
  }

  // 1. STATE MANAGEMENT
  window.getCart = function() {
    return JSON.parse(localStorage.getItem('cart')) || [];
  };
  window.getWishlist = function() {
    return JSON.parse(localStorage.getItem('wishlist')) || [];
  };
  window.saveCart = function(cart) {
    localStorage.setItem('cart', JSON.stringify(cart));
  };
  window.saveWishlist = function(wishlist) {
    localStorage.setItem('wishlist', JSON.stringify(wishlist));
  };

  // 2. HEADER UPDATES
  window.updateBadges = function() {
    var cartBadge = document.getElementById('cart-badge');
    var wishlistBadge = document.getElementById('wishlist-badge');
    if (cartBadge) {
        var cart = getCart();
        var totalQty = cart.reduce(function(sum, item) { return sum + item.quantity; }, 0);
        cartBadge.innerText = totalQty;
        cartBadge.style.display = totalQty > 0 ? 'flex' : 'none';
    }
    if (wishlistBadge) {
        var wishlist = getWishlist();
        wishlistBadge.innerText = wishlist.length;
        wishlistBadge.style.display = wishlist.length > 0 ? 'flex' : 'none';
    }
    
    // Auth logic
    var isLoggedIn = localStorage.getItem('isLoggedIn') === 'true';
    var userIcon = document.querySelector('.user-dropdown-wrap > a > i');
    if (userIcon) {
        if (isLoggedIn) {
            userIcon.parentElement.classList.add('user-icon-active');
        } else {
            userIcon.parentElement.classList.remove('user-icon-active');
        }
    }
  };

  // Initial update
  updateBadges();

  // Handle Logout
  document.addEventListener('click', function(e) {
      if (e.target.closest('.logout-btn')) {
          e.preventDefault();
          localStorage.setItem('isLoggedIn', 'false');
          alert('Logged out successfully!');
          updateBadges();
      }
  });

  // 3. GLOBAL ADD TO CART & WISHLIST
  document.addEventListener('click', function(e) {

      // Navigate to product details
      var clickedCard = e.target.closest('.product-card');
      if (clickedCard && !e.target.closest('.add-to-cart-btn') && !e.target.closest('.action-icons') && !e.target.closest('.remove-btn')) {
          if (!e.target.closest('a')) {
              var titleEl = clickedCard.querySelector('.product-title');
              var priceEl = clickedCard.querySelector('.price-current') || clickedCard.querySelector('.current-price');
              var imgEl = clickedCard.querySelector('.product-image-wrap img') || clickedCard.querySelector('.product-img img');
              
              if (titleEl && imgEl && priceEl) {
                  var title = encodeURIComponent(titleEl.innerText.trim());
                  var price = encodeURIComponent(priceEl.innerText.trim());
                  
                  var imgSrcRaw = imgEl.getAttribute('src');
                  var cleanImgSrc = imgSrcRaw.replace(/^(\.\.\/|\.\/)/, '');
                  var image = encodeURIComponent(cleanImgSrc);
                  
                  window.location.href = pagePath + 'product_details.html?title=' + title + '&price=' + price + '&image=' + image;
              } else {
                  window.location.href = pagePath + 'product_details.html';
              }
          }
      }

      // Add to Cart
      if (e.target.closest('.add-to-cart-btn')) {
          e.preventDefault();
          var btn = e.target.closest('.add-to-cart-btn');
          var card = btn.closest('.product-card') || btn.closest('.product-img-wrap');
          
          // In product details page it might not be a card, so we provide fallback selectors
          var titleEl = card ? card.querySelector('.product-title') : null;
          var priceEl = card ? (card.querySelector('.price-current') || card.querySelector('.current-price')) : null;
          var imgEl = card ? card.querySelector('.product-image-wrap img') || card.querySelector('.product-img img') || card.querySelector('img') : null;

          if (titleEl && priceEl && imgEl) {
              var title = titleEl.innerText.trim();
              var priceText = priceEl.innerText.trim().replace('$', '');
              var price = parseFloat(priceText);
              var imgSrcRaw = imgEl.getAttribute('src');
              var cleanImgSrc = imgSrcRaw.replace(/^(\.\.\/|\.\/)/, '');

              var cart = getCart();
              var existingItem = cart.find(function(item) { return item.title === title; });
              if (existingItem) {
                  existingItem.quantity += 1;
              } else {
                  cart.push({ title: title, price: price, image: cleanImgSrc, quantity: 1 });
              }
              saveCart(cart);
              updateBadges();
              alert(title + ' added to cart!');
          }
      }

      // Add to Wishlist
      if (e.target.closest('.fa-heart') && !e.target.closest('.icon-badge-wrap')) {
          e.preventDefault();
          var btn = e.target.closest('.fa-heart');
          var card = btn.closest('.product-card');
          if (!card) return;

          var titleEl = card.querySelector('.product-title');
          var priceEl = card.querySelector('.price-current') || card.querySelector('.current-price');
          var imgEl = card.querySelector('.product-image-wrap img') || card.querySelector('.product-img img');

          if (titleEl && priceEl && imgEl) {
              var title = titleEl.innerText.trim();
              var priceText = priceEl.innerText.trim().replace('$', '');
              var price = parseFloat(priceText);
              var imgSrcRaw = imgEl.getAttribute('src');
              var cleanImgSrc = imgSrcRaw.replace(/^(\.\.\/|\.\/)/, '');

              var wishlist = getWishlist();
              var existingIndex = wishlist.findIndex(function(item) { return item.title === title; });
              
              if (existingIndex > -1) {
                  wishlist.splice(existingIndex, 1);
                  btn.classList.remove('fa-solid');
                  btn.classList.add('fa-regular');
                  btn.style.color = '';
              } else {
                  wishlist.push({ title: title, price: price, image: cleanImgSrc });
                  btn.classList.remove('fa-regular');
                  btn.classList.add('fa-solid');
                  btn.style.color = 'var(--primary-color)';
              }
              saveWishlist(wishlist);
              updateBadges();
          }
      }
  });

})();
