var JArray = Java.type('java.lang.reflect.Array');
var JInt = Java.type('int')
var len0 = 8;
var len1 = 7;
var len2 = 2;
var input0 = JArray.newInstance(JInt, [len0, len1]);
JavaIntArray = Java.type("int[]");
var output = JArray.newInstance(JInt, [len0, len1]);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("2dinit", input0);



console.time("vec");
var k = 0;
var sum = 0;
"adaptive execution";
for (var m = 0; m < len2; m++) {
	for (var i = 0; i < len0; ++i) {
		for (var j = 0; j < len1; ++j) {
			if (input0[i][j] < 50) {
				k = k + 1;
				output[i][j] = 1;
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
for (var m = 0; m < len2; m++) {
	for (var i = 0; i < len0; ++i) {
		for (var j = 0; j < len1; ++j) {
			if (input0[i][j] < 50) {
				k = k + 1;
			}
			if(output[i][j] == 1) {
				count += 1;
			}
		}
	}
}
console.timeEnd("js");
console.log("k");
console.log(k);
console.log("count");
console.log(count);
