(function () {
  "use strict";
  var cartContainer = document.getElementById('cart-items-container');
  var subtotalVal = document.getElementById('cart-subtotal-val');
  var totalVal = document.getElementById('cart-total-val');
  
  function renderCart() {
      if (!cartContainer || !subtotalVal || !totalVal) return;
      
      var cart = window.getCart();
      var html = '';
      var subtotal = 0;

      if (cart.length === 0) {
          html = '<div style="padding: 20px; text-align: center;">Your cart is empty.</div>';
      } else {
          cart.forEach(function(item, index) {
              var itemTotal = item.price * item.quantity;
              subtotal += itemTotal;
              
              html += '<div class="cart-item" data-index="' + index + '">';
              html += '    <div class="cart-product">';
              html += '        <div class="product-img-wrap">';
              html += '            <i class="fa-solid fa-circle-xmark remove-btn" aria-hidden="true"></i>';
              html += '            <img src="../' + item.image + '" alt="' + item.title + '">';
              html += '        </div>';
              html += '        <span>' + item.title + '</span>';
              html += '    </div>';
              html += '    <div class="cart-price">$' + item.price + '</div>';
              html += '    <div class="cart-quantity">';
              html += '        <label class="sr-only">Quantity</label>';
              html += '        <input type="number" class="qty-input" value="' + item.quantity + '" min="1">';
              html += '    </div>';
              html += '    <div class="cart-subtotal">$' + itemTotal + '</div>';
              html += '</div>';
          });
      }

      cartContainer.innerHTML = html;
      subtotalVal.innerText = '$' + subtotal;
      totalVal.innerText = '$' + subtotal;
  }

  renderCart();

  // Handle remove and quantity changes in cart
  if (cartContainer) {
      cartContainer.addEventListener('click', function(e) {
          if (e.target.classList.contains('remove-btn')) {
              var itemRow = e.target.closest('.cart-item');
              var index = parseInt(itemRow.getAttribute('data-index'));
              var cart = window.getCart();
              cart.splice(index, 1);
              window.saveCart(cart);
              renderCart();
              window.updateBadges();
          }
      });

      cartContainer.addEventListener('change', function(e) {
          if (e.target.classList.contains('qty-input')) {
              var itemRow = e.target.closest('.cart-item');
              var index = parseInt(itemRow.getAttribute('data-index'));
              var newQty = parseInt(e.target.value);
              
              if (newQty > 0) {
                  var cart = window.getCart();
                  cart[index].quantity = newQty;
                  window.saveCart(cart);
                  renderCart();
                  window.updateBadges();
              }
          }
      });
  }
})();
