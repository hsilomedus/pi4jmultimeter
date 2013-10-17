function getQueryParameterByName(name) {
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results == null ? null : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function isRandomPage() {
  var randomParam = getQueryParameterByName("random");
  if (randomParam && randomParam.toLowerCase() == "true") {
    return true;
  } else {
    return false;
  }
}

function getRandomBandsValues() {
  var arr = new Array();
  for (i = 0; i< 20;i++) {
    //0 to 60 values.
    //the random fuction returns up to 1023
    arr.push(parseInt(getRandomValue() / 17));
  }
  return arr;
}

function getRandomValue() {
  return parseInt(Math.random() * 1024);
}

function generateRandomPackage() {
  return {
    ac: getRandomValue(),
    dc: getRandomValue(),
    r: getRandomValue(),
    d: getRandomBandsValues()
  };
}

function parseValueObject(valueObject) {
  var retVal = valueObject;
  if (!valueObject.hasOwnProperty("d")) {
    retVal = JSON.parse(valueObject);
  }
  return retVal;
}

function startClient() {
  if (!isRandomPage()) {
    var socket = new WebSocket("ws://" + document.domain + ":8887/");
    socket.onopen = function() { console.log("Opened socket."); };
    socket.onclose = function() { console.log("Closed socket."); };
    socket.onerror = function() { console.log("Error during transfer."); };
    socket.onmessage = function(a) {
      lastReceivedValue = a.data;
    }
  } else {
    setInterval(function(){
      lastReceivedValue = generateRandomPackage();
    }, 10)
  }
}