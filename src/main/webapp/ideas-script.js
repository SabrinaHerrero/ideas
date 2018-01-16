
    
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


        //  $.ajax({
        //     url: ajaxurl,
        //     data: "<test></test>", 
        //     type: 'POST',
        //     contentType: "text/xml",
        //     dataType: "text",
        //     success : parse,
        //     error : function (xhr, ajaxOptions, thrownError){  
        //         console.log(xhr.status);          
        //         console.log(thrownError);
        //     } 
        // }); 
        //basis for Post

        // function $.ajax({
        //     url: ajaxurl,
        //     data: "<test></test>", 
        //     type: 'POST',
        //     contentType: "text/xml",
        //     dataType: "text",
        //     success : parse,
        //     error : function (xhr, ajaxOptions, thrownError){  
        //         console.log(xhr.status);          
        //         console.log(thrownError);
        //     } 
        // }); 
$(document).ready(function(){
    $('#submitButton').on("click", function(event){
     
         let title = '' + $('#title').val();
            let description = '' + $('#description').val();
        let complete = '' + $('#completed').val();
        let fecha = '' + $('#fecha').val();
        let dataString = '<date><title>' + title + '</title><description>' + description + '</description><completed>' + complete + '</completed><fecha>' + fecha + '</fecha></date>';
         $.ajax({ type: "POST",
                        url: "ideas/dates",
                        data: dataString,
                        contentType: "application/xml",
                        dataType: "xml",
                        cache: false,
                        error: function() { alert("No data found."); },
                        success: function(xml) {
//                            alert("it works");
//                            alert($(xml).find("project")[0].attr("id"));
                        }
        });
           event.preventDefault();
    });
});

       // $("#txt_name").val()
//$(document).ready(function(){
//    $('#btnGetValue').click(function() {
//        var selValue = $('input[name=rbnNumber]:checked').val(); 
//        $('p').html('<br/>Selected Radio Button Value is : <b>' + selValue + '</b>');
//    });
//});
        
