JavaIntArray = Java.type("int[]");
var len0 = 1;
var len1 = 1000000000;

input0 = new JavaIntArray(len1);
input1 = new JavaIntArray(len1);
input2 = new JavaIntArray(len1);
input3 = new JavaIntArray(len1);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("selectAST", input0, input1, input2, input3);

console.time("vec");
var k = 0;
var sum = 0;
"adaptive execution";
for (var i = 0; i < len0; ++i) {
	for (var j = 0; j < len1; ++j) {
		if (input0[j] < 50 && input1[j] < 50 && input2[j] < 50 && input3[j] < 50) {
			k = k + 1;
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
		if (input0[j] < 50 && input1[j] < 50 && input2[j] < 50 && input3[j] < 50) {
			k = k + 1;
		}
	}
}
console.timeEnd("js");
console.log("k");
console.log(k);
console.log("count");
console.log(count);
