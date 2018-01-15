
    
        var xmlhttp;
        //helper function for sending requests
        function loadXMLDoc(url, cfunc) {
            if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp = new XMLHttpRequest();
            }
            else {// code for IE6, IE5
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function() {
        	    if (this.readyState == 4 && this.status == 200) {
        	    	cfunc(this);
        	    }
        	  };
            xmlhttp.open("GET", url, true);
            xmlhttp.send();
        }
        
        $(document).ready(function(){
        		func1();
        		func2();
        });
        
 
        function func1() {
            //actually gets response
            loadXMLDoc("/ideas/dates/complete/", function () {
               document.getElementById("completed-body").innerHTML = xmlhttp.responseText;
            });
        }
        	function func2() {
            //actually gets response
            loadXMLDoc("/ideas/dates/incomplete", function () {
                document.getElementById("incomplete-body").innerHTML = xmlhttp.responseText;
            });
         }
