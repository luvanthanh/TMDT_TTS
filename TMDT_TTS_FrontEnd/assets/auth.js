(function () {
  "use strict";
  var authForm = document.querySelector('.auth-form');
  if (authForm) {
      authForm.addEventListener('submit', function(e) {
          e.preventDefault();
          localStorage.setItem('isLoggedIn', 'true');
          alert('Success! Redirecting to home...');
          window.location.href = '../index.html';
      });
  }
})();
