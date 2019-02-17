console.log("Injected");

var inject  = document.createElement("div");
inject.innerHTML = "<div id=\"snackbar\">Setting Username and Password Fields...</div>"
document.body.insertBefore (inject, document.body.firstChild);

chrome.runtime.onMessage.addListener(
  function(request, sender, sendResponse) {
  	if(request["type"] == "execute") {
	  	var inputs = document.getElementsByTagName("input");
	  		console.log(inputs);

	  	for (var i=0; i<inputs.length; i++) {
	    	if (inputs[i].type.toLowerCase() === "password") {
	    		console.log("Password: " + inputs[i]);
	      		inputs[i].value = request["p"].toString().slice(0, 15);
	    		console.log(request["p"].toString().slice(0, 15));
	    	}
	    	//Try to find the username field I guess ¯\_(ツ)_/¯
	    	else if((inputs[i].type.toLowerCase() === "text" || inputs[i].type.toLowerCase() === "email") && ((inputs[i].outerHTML.toLowerCase().includes("email") 
	    		|| inputs[i].outerHTML.toLowerCase().includes("user")) 
	    		|| inputs[i].outerHTML.toLowerCase().includes("login"))){
	    		console.log("Username: " + inputs[i]);
	    		inputs[i].value = request["u"];
	    	}
	  	}
  	}
	myFunction();	
  	sendResponse("Success!");
});
/*
var config = {
    apiKey: "AIzaSyCQ_Vkbw4cyac9Yc20IZXZqRR1Lv-9rHq0",
    authDomain: "yeet-6f986.firebaseapp.com",
    databaseURL: "https://yeet-6f986.firebaseio.com",
    projectId: "yeet-6f986",
    storageBucket: "yeet-6f986.appspot.com",
    messagingSenderId: "8084023471"
};
var messaging = firebase.initializeApp(config).messaging();

messaging.usePublicVapidKey("BKkohZNefl6MHnC9EvdDlu9GeqXSFF1RFhT5TWDTjNOC5jfcWvu_UuSRJrpqKrZUVXGD7VT5OOAZnYomdGxYYiMZ");

messaging.getToken().then(function(currentToken) {
  if (currentToken) {
  	console.log("Token: " + currentToken);
  } else {
    // Show permission request.
    console.log('No Instance ID token available. Request permission to generate one.');
  }
}).catch(function(err) {
  console.log('An error occurred while retrieving token. ', err);
});
*/

function myFunction() {
  // Get the snackbar DIV
  var x = document.getElementById("snackbar");

  // Add the "show" class to DIV
  x.className = "yeeeeeeet";

  // After 3 seconds, remove the show class from DIV
  setTimeout(function(){ x.className = x.className.replace("yeeeeeeet", ""); }, 3000);
}