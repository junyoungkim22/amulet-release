JavaIntArray = Java.type("int[]");
input0 = new JavaIntArray(100000000);
input1 = new JavaIntArray(100000000);
input2 = new JavaIntArray(100000000);
input3 = new JavaIntArray(100000000);
output = new JavaIntArray(100000000);

Polyglot.eval("vec", "");
prepare = Polyglot.import("prepare");
prepare("selectAST", input0, input1, input2, input3);

console.time("vec");
var k = 0;
var sum = 0;
var len = 100000000;
"adaptive execution";
for (var i = 0; i < len; ++i) {
    if (input0[i] < 50 && input1[i] < 50 && input2[i] < 50 && input3[i] < 50) {
		k = k + 1;
		output[i] = 1;
	}
}
console.timeEnd("vec");
console.log("k");
console.log(k);

console.time("js");
var k = 0;
var count = 0;
for (var i = 0; i < len; ++i) {
    if (input0[i] < 50 && input1[i] < 50 && input2[i] < 50 && input3[i] < 50) {
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
