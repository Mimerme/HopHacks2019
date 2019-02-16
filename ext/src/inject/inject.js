console.log("Injected");

chrome.runtime.onMessage.addListener(
  function(request, sender, sendResponse) {
  	console.log(request);

  	var inputs = document.getElementsByTagName("input");
  	for (var i=0; i<inputs.length; i++) {
    	if (inputs[i].type.toLowerCase() === "password") {
      		inputs[i].value = request["p"];
    	}
    	//Try to find the username field I guess ¯\_(ツ)_/¯
    	else if(inputs[i].outerHTML.contains("email") || inputs[i].outerHTML.contains("user")){
    		inputs[i].value = request["u"];
    	}
  	}

  	sendResponse("Success!");
});