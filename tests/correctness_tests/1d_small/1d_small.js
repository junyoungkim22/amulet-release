JavaIntArray = Java.type("int[]");
var len = 10000000;
input0 = new JavaIntArray(len);
output = new JavaIntArray(len);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("selectAST", input0);

console.time("vec");
var k = 0;
var sum = 0;
"adaptive execution";
for (var i = 0; i < len; ++i) {
    if (input0[i] < 50) {
		k = k + 1;
		output[i] += 1;
	}
}
console.timeEnd("vec");
console.log("k");
console.log(k);

console.time("js");
var k = 0;
var count = 0;
for (var i = 0; i < len; ++i) {
    if (input0[i] < 50) {
		k = k + 1;
	}
	if(output[i] == 1) {
		count++;
	}
}

console.timeEnd("js");
console.log("k");
console.log(k);
console.log("count");
console.log(count);
if(k == count) {
	console.log("CORRECT");
}
else {
	console.log("WRONG");
}
