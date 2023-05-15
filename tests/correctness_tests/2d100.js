var JArray = Java.type('java.lang.reflect.Array');
var JInt = Java.type('int')
var input0 = JArray.newInstance(JInt, [10000, 10000]);
JavaIntArray = Java.type("int[]");
var output = JArray.newInstance(JInt, [10000, 10000]);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("2dinit", input0);

console.time("vec");
var k = 0;
var sum = 0;
var len0 = 10000;
var len1 = 10000;
"adaptive execution";
for (var i = 0; i < len0; ++i) {
	for (var j = 0; j < len1; ++j) {
		if (input0[i][j] < 50) {
			k = k + 1;
			output[i][j] = 1;
		}
	}
}
console.timeEnd("vec");
console.log("k");
console.log(k);

console.time("js");
var k = 0;
var count = 0;
for (var i = 0; i < len0; ++i) {
	for (var j = 0; j < len1; ++j) {
		if (input0[i][j] < 50) {
			k = k + 1;
		}
		if (output[i][j] == 1) {
			count++;
		}
	}
}
console.timeEnd("js");
console.log("k");
console.log(k);
console.log("count");
console.log(count);
