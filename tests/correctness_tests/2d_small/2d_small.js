var JArray = Java.type('java.lang.reflect.Array');
var JInt = Java.type('int')
var len0 = 9775;
var len1 = 1234;
var input0 = JArray.newInstance(JInt, [len0, len1]);
JavaIntArray = Java.type("int[]");
var output = JArray.newInstance(JInt, [len0, len1]);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("2dinit", input0);

console.time("vec");
var k = 0;

"adaptive execution";
for (var i = 0; i < len0; ++i) {
	for (var j = 0; j < len1; ++j) {
		if (input0[i][j] < 50) {
			k = k + 1;
			output[i][j] += 1;
		}
	}
}
console.timeEnd("vec");

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
console.log(k)
console.log(count)
if(k == count) {
	console.log("CORRECT");
}
else {
	console.log("WRONG");
}