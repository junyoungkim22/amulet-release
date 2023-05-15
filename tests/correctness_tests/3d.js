var JArray = Java.type('java.lang.reflect.Array');
var JInt = Java.type('int')
var len0 = 256;
var len1 = 256;
var len2 = 256;
var input0 = JArray.newInstance(JInt, [len0, len1, len2]);
var input1 = JArray.newInstance(JInt, [len0, len1, len2]);
JavaIntArray = Java.type("int[]");
var output = JArray.newInstance(JInt, [len0, len1, len2]);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("3dinit", input0);
prepare("3dinit", input1);


console.time("vec");
var k = 0;
var sum = 0;
"adaptive execution";
for (var m = 0; m < len0; m++) {
	for (var i = 0; i < len1; ++i) {
		for (var j = 0; j < len2; ++j) {
			if (input0[m][i][j] < 50 && input1[m][i][j] < 50) {
				k = k + 1;
				output[m][i][j] = 1;
			}
		}
	}
}
console.timeEnd("vec");
console.log("k");
console.log(k);

console.time("js");
var k = 0;
var count = 0;
for (var m = 0; m < len0; m++) {
	for (var i = 0; i < len1; ++i) {
		for (var j = 0; j < len2; ++j) {
			if (input0[m][i][j] < 50 && input1[m][i][j] < 50) {
				k = k + 1;
			}
			if(output[m][i][j] == 1) {
				count += 1
			}

		}
	}
}
console.timeEnd("js");
console.log("k");
console.log(k);
console.log("count");
console.log(count);
