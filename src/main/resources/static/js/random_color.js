
var randomProperty = function (object) {
  var keys = Object.keys(object);
  return object[keys[Math.floor(keys.length * Math.random())]];
};


var colorGraduation = function(total,current){
	var percent =  current*100/total;
	return( Math.floor(percent*255/100)).toString(16);
}