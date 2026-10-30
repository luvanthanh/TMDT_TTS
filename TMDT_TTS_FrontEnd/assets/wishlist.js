(function () {
  "use strict";
  var wishlistContainer = document.getElementById('wishlist-items-container');
  var wishlistCount = document.getElementById('wishlist-count');
  
  function renderWishlist() {
      if (!wishlistContainer) return;
      
      var wishlist = window.getWishlist();
      var html = '';

      if (wishlistCount) {
          wishlistCount.innerText = 'Wishlist (' + wishlist.length + ')';
      }

      if (wishlist.length === 0) {
          html = '<div style="grid-column: 1 / -1; padding: 20px; text-align: center;">Your wishlist is empty.</div>';
      } else {
          wishlist.forEach(function(item, index) {
              html += '<div class="product-card" data-index="' + index + '">';
              html += '    <div class="product-img">';
              html += '        <div class="action-icons">';
              html += '            <div class="icon remove-btn" style="cursor: pointer;"><i class="fa-solid fa-trash-can" style="pointer-events: none;"></i></div>';
              html += '        </div>';
              html += '        <img src="../' + item.image + '" alt="' + item.title + '">';
              html += '        <button class="add-to-cart-btn"><i class="fa-solid fa-cart-shopping"></i> Add To Cart</button>';
              html += '    </div>';
              html += '    <div class="product-info">';
              html += '        <h3 class="product-title">' + item.title + '</h3>';
              html += '        <div class="product-price">';
              html += '            <span class="current-price">$' + item.price + '</span>';
              html += '        </div>';
              html += '    </div>';
              html += '</div>';
          });
      }

      wishlistContainer.innerHTML = html;
  }

  renderWishlist();

  if (wishlistContainer) {
      wishlistContainer.addEventListener('click', function(e) {
          if (e.target.closest('.remove-btn')) {
              var itemRow = e.target.closest('.product-card');
              var index = parseInt(itemRow.getAttribute('data-index'));
              var wishlist = window.getWishlist();
              wishlist.splice(index, 1);
              window.saveWishlist(wishlist);
              renderWishlist();
              window.updateBadges();
          }
      });
  }
})();
