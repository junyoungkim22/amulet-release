var JArray = Java.type('java.lang.reflect.Array');
var JDouble = Java.type('double');
var len = 512;
var mat0 = JArray.newInstance(JDouble, [len, len]);
var mat1 = JArray.newInstance(JDouble, [len, len]);
var result = JArray.newInstance(JDouble, [len, len]);
var threshold = JArray.newInstance(JDouble, [len]);
var discount = JArray.newInstance(JDouble, [len]);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("double2dinit", mat0);
prepare("double2dinit", mat1);

console.time("vec");
count = 0;
"amulet";
for (var i = 0; i < len; ++i) {
	for (var k = 0; k < len; ++k) {
		for (var j = 0; j < len; ++j) {
			result[i][j] += mat0[i][k]*mat1[k][j];
		}
	}
}

// result[i][j] += mat0[i][k]*mat1[k][j]+(mat0[i][k]*mat1[k][j]>threshold[j])*((mat0[i][k]*mat1[k][j])*discount[j]);

console.log(count);
console.timeEnd("vec");

check = true;
if(check) {
	var epsilon = 0.000001;
	correct = true;
	console.time("js");
	var correct_result = JArray.newInstance(JDouble, [len, len]);
	for (var i = 0; i < len; ++i) {
		for (var k = 0; k < len; ++k) {
			for (var j = 0; j < len; ++j) {
				correct_result[i][j] += mat0[i][k] * mat1[k][j];
			}
		}
	}
	for (var i = 0; i < len; i++) {
		for (var j = 0; j < len; j++) {
			var diff = correct_result[i][j] - result[i][j];
			if (diff > epsilon || diff < (-1*epsilon)) {
				correct = false;
				console.log(correct_result[i][j]);
				console.log(result[i][j]);
				break;
			}
			if (!correct) {
				break;
			}
		}
	}
	console.timeEnd("js");
	if (correct) {
		console.log("CORRECT");
	} else {
		console.log("WRONG");
	}
}
