Polyglot.eval("vec", "");
console.time("vec");
var count = 0;
var len0 = 93;
var len1 = 77;
var len2 = 2345;
"adaptive execution";
for (var i = 0; i < len0; ++i) {
    for (var j = 0; j < len1; j++) {
    	for (var k = 0; k < len2; k++) {
			count += 1;
		}
	}
}
console.timeEnd("vec");
console.log("count");
console.log(count);