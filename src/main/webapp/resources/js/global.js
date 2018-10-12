// Through this Javascript we allow the anchor tag in our logout form to perform logout POST request via a hidden form
$(document).ready(function () {

   $("#logout").click(function(e) {
       e.preventDefault();
       $("#logout-form").submit();
    });

});