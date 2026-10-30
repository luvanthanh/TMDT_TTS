(function () {
  "use strict";
  var checkoutContainer = document.getElementById('checkout-items-container');
  var checkoutSubtotalVal = document.getElementById('checkout-subtotal-val');
  var checkoutTotalVal = document.getElementById('checkout-total-val');
  
  function renderCheckout() {
      if (!checkoutContainer || !checkoutSubtotalVal || !checkoutTotalVal) return;
      
      var cart = window.getCart();
      var html = '';
      var subtotal = 0;

      if (cart.length === 0) {
          html = '<div style="padding: 20px 0; font-size: 14px;">Your cart is empty.</div>';
      } else {
          cart.forEach(function(item) {
              var itemTotal = item.price * item.quantity;
              subtotal += itemTotal;
              
              html += '<div class="order-item">';
              html += '    <div class="order-product">';
              html += '        <img src="../' + item.image + '" alt="' + item.title + '">';
              html += '        <span>' + item.title + '</span>';
              html += '    </div>';
              html += '    <span>$' + itemTotal + '</span>';
              html += '</div>';
          });
      }

      checkoutContainer.innerHTML = html;
      checkoutSubtotalVal.innerText = '$' + subtotal;
      checkoutTotalVal.innerText = '$' + subtotal;
  }

  renderCheckout();
})();
